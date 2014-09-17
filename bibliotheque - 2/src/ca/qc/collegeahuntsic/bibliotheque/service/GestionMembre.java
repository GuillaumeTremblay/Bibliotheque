package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.DB.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dao.Membre;
import ca.qc.collegeahuntsic.bibliotheque.dao.Reservation;
import ca.qc.collegeahuntsic.bibliotheque.dto.TupleMembre;
import ca.qc.collegeahuntsic.bibliotheque.exception.BiblioException;

// TODO: Auto-generated Javadoc
/**
 * Gestion des transactions de reliées à la création et
 * suppresion de membres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transaction reliées à la 
 * création et suppresion de membres.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class GestionMembre {

    /** The cx. */
    private Connexion cx;

    /** The membre. */
    private Membre membre;

    /** The reservation. */
    private Reservation reservation;

    /**
     * Creation d'une instance.
     *
     * @param membre the membre
     * @param reservation the reservation
     */
    public GestionMembre(Membre membre,
        Reservation reservation) {

        this.cx = membre.getConnexion();
        this.membre = membre;
        this.reservation = reservation;
    }

    /**
     * Ajout d'un nouveau membre dans la base de donnees.
     * S'il existe deja, une exception est levee.
     *
     * @param idMembre the id membre
     * @param nom the nom
     * @param telephone the telephone
     * @param limitePret the limite pret
     * @throws SQLException the SQL exception
     * @throws BiblioException the biblio exception
     * @throws Exception the exception
     */
    public void inscrire(int idMembre,
        String nom,
        long telephone,
        int limitePret) throws SQLException,
        BiblioException,
        Exception {
        try {
            /* Vérifie si le membre existe déja */
            if(this.membre.existe(idMembre)) {
                throw new BiblioException("Membre existe deja: "
                    + idMembre);
            }

            /* Ajout du membre. */
            this.membre.inscrire(idMembre,
                nom,
                telephone,
                limitePret);
            this.cx.commit();
        } catch(Exception e) {
            this.cx.rollback();
            throw e;
        }
    }

    /**
     * Suppression d'un membre de la base de donnees.
     *
     * @param idMembre the id membre
     * @throws SQLException the SQL exception
     * @throws BiblioException the biblio exception
     * @throws Exception the exception
     */
    public void desinscrire(int idMembre) throws SQLException,
        BiblioException,
        Exception {
        try {
            /* Vérifie si le membre existe et son nombre de pret en cours */
            TupleMembre tupleMembre = this.membre.getMembre(idMembre);
            if(tupleMembre == null) {
                throw new BiblioException("Membre inexistant: "
                    + idMembre);
            }
            if(tupleMembre.nbPret > 0) {
                throw new BiblioException("Le membre "
                    + idMembre
                    + " a encore des prets.");
            }
            if(this.reservation.getReservationMembre(idMembre) != null) {
                throw new BiblioException("Membre "
                    + idMembre
                    + " a des réservations");
            }

            /* Suppression du membre */
            int nb = this.membre.desinscrire(idMembre);
            if(nb == 0) {
                throw new BiblioException("Membre "
                    + idMembre
                    + " inexistant");
            }
            this.cx.commit();
        } catch(Exception e) {
            this.cx.rollback();
            throw e;
        }
    }
}//class
