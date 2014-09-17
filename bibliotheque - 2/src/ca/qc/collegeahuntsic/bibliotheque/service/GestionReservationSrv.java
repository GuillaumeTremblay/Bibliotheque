package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.Date;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.DB.ConnexionDb;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDao;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDao;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDao;
import ca.qc.collegeahuntsic.bibliotheque.dto.TupleLivreDto;
import ca.qc.collegeahuntsic.bibliotheque.dto.TupleMembreDto;
import ca.qc.collegeahuntsic.bibliotheque.dto.TupleReservationDto;
import ca.qc.collegeahuntsic.bibliotheque.exception.BiblioException;

// TODO: Auto-generated Javadoc
/**
 * Gestion des transactions de reliées aux réservations de livres
 * par les membres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transactions réserver,
 * prendre et annuler.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class GestionReservationSrv {

    /** The livre. */
    private LivreDao livre;

    /** The membre. */
    private MembreDao membre;

    /** The reservation. */
    private ReservationDao reservation;

    /** The cx. */
    private ConnexionDb cx;

    /**
     * Creation d'une instance.
     * La connection de l'instance de livre et de membre doit être la même que cx,
     * afin d'assurer l'intégrité des transactions.
     *
     * @param livre the livre
     * @param membre the membre
     * @param reservation the reservation
     * @throws BiblioException the biblio exception
     */
    public GestionReservationSrv(LivreDao livre,
        MembreDao membre,
        ReservationDao reservation) throws BiblioException {
        if(livre.getConnexion() != membre.getConnexion()
            || reservation.getConnexion() != membre.getConnexion()) {
            throw new BiblioException("Les instances de livre, de membre et de reservation n'utilisent pas la même connexion au serveur");
        }
        this.cx = livre.getConnexion();
        this.livre = livre;
        this.membre = membre;
        this.reservation = reservation;
    }

    /**
     * Réservation d'un livre par un membre.
     * Le livre doit être prêté.
     *
     * @param idReservation the id reservation
     * @param idLivre the id livre
     * @param idMembre the id membre
     * @param dateReservation the date reservation
     * @throws SQLException the SQL exception
     * @throws BiblioException the biblio exception
     * @throws Exception the exception
     */
    public void reserver(int idReservation,
        int idLivre,
        int idMembre,
        String dateReservation) throws SQLException,
        BiblioException,
        Exception {
        try {
            /* Verifier que le livre est preté */
            TupleLivreDto tupleLivre = this.livre.getLivre(idLivre);
            if(tupleLivre == null) {
                throw new BiblioException("Livre inexistant: "
                    + idLivre);
            }
            if(tupleLivre.idMembre == 0) {
                throw new BiblioException("Livre "
                    + idLivre
                    + " n'est pas prete");
            }
            if(tupleLivre.idMembre == idMembre) {
                throw new BiblioException("Livre "
                    + idLivre
                    + " deja prete a ce membre");
            }

            /* Vérifier que le membre existe */
            TupleMembreDto tupleMembre = this.membre.getMembre(idMembre);
            if(tupleMembre == null) {
                throw new BiblioException("Membre inexistant: "
                    + idMembre);
            }

            /* Verifier si date reservation >= datePret */
            if(Date.valueOf(dateReservation).before(tupleLivre.datePret)) {
                throw new BiblioException("Date de reservation inferieure à la date de pret");
            }

            /* Vérifier que la réservation n'existe pas */
            if(this.reservation.existe(idReservation)) {
                throw new BiblioException("Réservation "
                    + idReservation
                    + " existe deja");
            }

            /* Creation de la reservation */
            this.reservation.reserver(idReservation,
                idLivre,
                idMembre,
                dateReservation);
            this.cx.commit();
        } catch(Exception e) {
            this.cx.rollback();
            throw e;
        }
    }

    /**
     * Prise d'une réservation.
     * Le livre ne doit pas être prêté.
     * Le membre ne doit pas avoir dépassé sa limite de pret.
     * La réservation doit la être la première en liste.
     *
     * @param idReservation the id reservation
     * @param datePret the date pret
     * @throws SQLException the SQL exception
     * @throws BiblioException the biblio exception
     * @throws Exception the exception
     */
    public void prendreRes(int idReservation,
        String datePret) throws SQLException,
        BiblioException,
        Exception {
        try {
            /* Vérifie s'il existe une réservation pour le livre */
            TupleReservationDto tupleReservation = this.reservation.getReservation(idReservation);
            if(tupleReservation == null) {
                throw new BiblioException("Réservation inexistante : "
                    + idReservation);
            }

            /* Vérifie que c'est la première réservation pour le livre */
            TupleReservationDto tupleReservationPremiere = this.reservation.getReservationLivre(tupleReservation.idLivre);
            if(tupleReservation.idReservation != tupleReservationPremiere.idReservation) {
                throw new BiblioException("La réservation n'est pas la première de la liste "
                    + "pour ce livre; la premiere est "
                    + tupleReservationPremiere.idReservation);
            }

            /* Verifier si le livre est disponible */
            TupleLivreDto tupleLivre = this.livre.getLivre(tupleReservation.idLivre);
            if(tupleLivre == null) {
                throw new BiblioException("Livre inexistant: "
                    + tupleReservation.idLivre);
            }
            if(tupleLivre.idMembre != 0) {
                throw new BiblioException("Livre "
                    + tupleLivre.idLivre
                    + " deja prêté à "
                    + tupleLivre.idMembre);
            }

            /* Vérifie si le membre existe et sa limite de pret */
            TupleMembreDto tupleMembre = this.membre.getMembre(tupleReservation.idMembre);
            if(tupleMembre == null) {
                throw new BiblioException("Membre inexistant: "
                    + tupleReservation.idMembre);
            }
            if(tupleMembre.nbPret >= tupleMembre.limitePret) {
                throw new BiblioException("Limite de prêt du membre "
                    + tupleReservation.idMembre
                    + " atteinte");
            }

            /* Verifier si datePret >= tupleReservation.dateReservation */
            if(Date.valueOf(datePret).before(tupleReservation.dateReservation)) {
                throw new BiblioException("Date de prêt inférieure à la date de réservation");
            }

            /* Enregistrement du pret. */
            if(this.livre.preter(tupleReservation.idLivre,
                tupleReservation.idMembre,
                datePret) == 0) {
                throw new BiblioException("Livre supprimé par une autre transaction");
            }
            if(this.membre.preter(tupleReservation.idMembre) == 0) {
                throw new BiblioException("Membre supprimé par une autre transaction");
            }
            /* Eliminer la réservation */
            this.reservation.annulerRes(idReservation);
            this.cx.commit();
        } catch(Exception e) {
            this.cx.rollback();
            throw e;
        }
    }

    /**
     * Annulation d'une réservation.
     * La réservation doit exister.
     *
     * @param idReservation the id reservation
     * @throws SQLException the SQL exception
     * @throws BiblioException the biblio exception
     * @throws Exception the exception
     */
    public void annulerRes(int idReservation) throws SQLException,
        BiblioException,
        Exception {
        try {

            /* Vérifier que la réservation existe */
            if(this.reservation.annulerRes(idReservation) == 0) {
                throw new BiblioException("Réservation "
                    + idReservation
                    + " n'existe pas");
            }

            this.cx.commit();
        } catch(Exception e) {
            this.cx.rollback();
            throw e;
        }
    }
}
