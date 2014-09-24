
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

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

public class ReservationService extends Service {

    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    /** The livre. */
    private LivreDAO livreDAO;

    /** The membre. */
    private MembreDAO membreDAO;

    /** The reservation. */
    private ReservationDAO reservationDAO;

    /**
     * Crée un service à partir des DAOs de livre, member et réservation
     *
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public ReservationService(LivreDAO livreDAO,
        MembreDAO membreDAO,
        ReservationDAO reservationDAO) {
        super();
        setLivreDAO(livreDAO);
        setMembreDAO(membreDAO);
        setReservationDAO(reservationDAO);
    }

    /**
     * Getter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @return La variable d'instance <code>this.livreDAO</code>
     */
    public LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @param livreDAO La valeur à utiliser pour la variable d'instance <code>this.livreDAO</code>
     */
    public void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @return La variable d'instance <code>this.membreDAO</code>
     */
    public MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @param membreDAO La valeur à utiliser pour la variable d'instance <code>this.membreDAO</code>
     */
    public void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    public ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO La valeur à utiliser pour la variable d'instance <code>this.reservationDAO</code>
     */
    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Ajoute une nouvelle reservation.
     *
     * @param reservationDTO La reservation à ajouter
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void add(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().add(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Lit une reservation.
     *
     * @param idMembre L'ID de la reservation à lire
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public ReservationDTO read(int idReservation) throws ServiceException {
        try {
            return getReservationDAO().read(idReservation);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Met à jour une reservation.
     *
     * @param ReservationDTO La reservation à mettre à jour
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void update(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().update(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Supprime une reservation.
     *
     * @param reservationDTO La reservation à supprimer
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void delete(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().delete(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve tous les reservations.
     *
     * @return La liste des reservations ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public List<ReservationDTO> getAll() throws ServiceException {
        try {
            return getReservationDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les reservations à partir d'un membre.
     *
     * @param membreDTO Le membre à utiliser
     * @return La liste des reservations correspondants ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public List<ReservationDTO> findByMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            return getReservationDAO().findByMembre(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les reservations à partir d'un livre.
     *
     * @param livreDTO Le livre à utiliser
     * @return La liste des reservations correspondants ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public List<ReservationDTO> findByLivre(LivreDTO livreDTO) throws ServiceException {
        try {
            return getReservationDAO().findByLivre(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //    public void reserver(ReservationDTO reservationDTO, LivreDTO livreDTO, MembreDTO membreDTO) throws ServiceException{
    //        if (findBy)
    //    }
    //        /**
    //         * Réservation d'un livre par un membre.
    //         * Le livre doit être prêté.
    //         *
    //         * @param idReservation the id reservation
    //         * @param idLivre the id livre
    //         * @param idMembre the id membre
    //         * @param dateReservation the date reservation
    //         * @throws SQLException the SQL exception
    //         * @throws BibliothequeException the biblio exception
    //         * @throws Exception the exception
    //         */
    //        public void reserver(int idReservation,
    //            int idLivre,
    //            int idMembre,
    //            String dateReservation) throws SQLException,
    //            BibliothequeException,
    //            Exception {
    //            try {
    //                /* Verifier que le livre est preté */
    //                LivreDTO tupleLivre = this.livre.getLivre(idLivre);
    //                if(tupleLivre == null) {
    //                    throw new BibliothequeException("Livre inexistant: "
    //                        + idLivre);
    //                }
    //                if(tupleLivre.idMembre == 0) {
    //                    throw new BibliothequeException("Livre "
    //                        + idLivre
    //                        + " n'est pas prete");
    //                }
    //                if(tupleLivre.idMembre == idMembre) {
    //                    throw new BibliothequeException("Livre "
    //                        + idLivre
    //                        + " deja prete a ce membre");
    //                }
    //
    //                /* Vérifier que le membre existe */
    //                MembreDTO tupleMembre = this.membre.getMembre(idMembre);
    //                if(tupleMembre == null) {
    //                    throw new BibliothequeException("Membre inexistant: "
    //                        + idMembre);
    //                }
    //
    //                /* Verifier si date reservation >= datePret */
    //                if(Date.valueOf(dateReservation).before(tupleLivre.datePret)) {
    //                    throw new BibliothequeException("Date de reservation inferieure à la date de pret");
    //                }
    //
    //                /* Vérifier que la réservation n'existe pas */
    //                if(this.reservation.existe(idReservation)) {
    //                    throw new BibliothequeException("Réservation "
    //                        + idReservation
    //                        + " existe deja");
    //                }
    //
    //                /* Creation de la reservation */
    //                this.reservation.reserver(idReservation,
    //                    idLivre,
    //                    idMembre,
    //                    dateReservation);
    //                this.cx.commit();
    //            } catch(Exception e) {
    //                this.cx.rollback();
    //                throw e;
    //            }
    //        }
    //
    //    /**
    //     * Prise d'une réservation.
    //     * Le livre ne doit pas être prêté.
    //     * Le membre ne doit pas avoir dépassé sa limite de pret.
    //     * La réservation doit la être la première en liste.
    //     *
    //     * @param idReservation the id reservation
    //     * @param datePret the date pret
    //     * @throws SQLException the SQL exception
    //     * @throws BibliothequeException the biblio exception
    //     * @throws Exception the exception
    //     */
    //    public void prendreRes(int idReservation,
    //        String datePret) throws SQLException,
    //        BibliothequeException,
    //        Exception {
    //        try {
    //            /* Vérifie s'il existe une réservation pour le livre */
    //            ReservationDTO tupleReservation = this.reservation.getReservation(idReservation);
    //            if(tupleReservation == null) {
    //                throw new BibliothequeException("Réservation inexistante : "
    //                    + idReservation);
    //            }
    //
    //            /* Vérifie que c'est la première réservation pour le livre */
    //            ReservationDTO tupleReservationPremiere = this.reservation.getReservationLivre(tupleReservation.idLivre);
    //            if(tupleReservation.idReservation != tupleReservationPremiere.idReservation) {
    //                throw new BibliothequeException("La réservation n'est pas la première de la liste "
    //                    + "pour ce livre; la premiere est "
    //                    + tupleReservationPremiere.idReservation);
    //            }
    //
    //            /* Verifier si le livre est disponible */
    //            LivreDTO tupleLivre = this.livre.getLivre(tupleReservation.idLivre);
    //            if(tupleLivre == null) {
    //                throw new BibliothequeException("Livre inexistant: "
    //                    + tupleReservation.idLivre);
    //            }
    //            if(tupleLivre.idMembre != 0) {
    //                throw new BibliothequeException("Livre "
    //                    + tupleLivre.idLivre
    //                    + " deja prêté à "
    //                    + tupleLivre.idMembre);
    //            }
    //
    //            /* Vérifie si le membre existe et sa limite de pret */
    //            MembreDTO tupleMembre = this.membre.getMembre(tupleReservation.idMembre);
    //            if(tupleMembre == null) {
    //                throw new BibliothequeException("Membre inexistant: "
    //                    + tupleReservation.idMembre);
    //            }
    //            if(tupleMembre.nbPret >= tupleMembre.limitePret) {
    //                throw new BibliothequeException("Limite de prêt du membre "
    //                    + tupleReservation.idMembre
    //                    + " atteinte");
    //            }
    //
    //            /* Verifier si datePret >= tupleReservation.dateReservation */
    //            if(Date.valueOf(datePret).before(tupleReservation.dateReservation)) {
    //                throw new BibliothequeException("Date de prêt inférieure à la date de réservation");
    //            }
    //
    //            /* Enregistrement du pret. */
    //            if(this.livre.preter(tupleReservation.idLivre,
    //                tupleReservation.idMembre,
    //                datePret) == 0) {
    //                throw new BibliothequeException("Livre supprimé par une autre transaction");
    //            }
    //            if(this.membre.preter(tupleReservation.idMembre) == 0) {
    //                throw new BibliothequeException("Membre supprimé par une autre transaction");
    //            }
    //            /* Eliminer la réservation */
    //            this.reservation.annulerRes(idReservation);
    //            this.cx.commit();
    //        } catch(Exception e) {
    //            this.cx.rollback();
    //            throw e;
    //        }
    //    }
    //
    //    /**
    //     * Annulation d'une réservation.
    //     * La réservation doit exister.
    //     *
    //     * @param idReservation the id reservation
    //     * @throws SQLException the SQL exception
    //     * @throws BibliothequeException the biblio exception
    //     * @throws Exception the exception
    //     */
    //    public void annulerRes(int idReservation) throws SQLException,
    //        BibliothequeException,
    //        Exception {
    //        try {
    //
    //            /* Vérifier que la réservation existe */
    //            if(this.reservation.annulerRes(idReservation) == 0) {
    //                throw new BibliothequeException("Réservation "
    //                    + idReservation
    //                    + " n'existe pas");
    //            }
    //
    //            this.cx.commit();
    //        } catch(Exception e) {
    //            this.cx.rollback();
    //            throw e;
    //        }
    //    }
}
