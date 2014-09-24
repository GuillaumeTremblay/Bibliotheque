
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.sql.SQLException;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
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

    public void reserver(ReservationDTO reservationDTO) throws ServiceException {
        try {
            LivreDTO livreDTO = getLivreDAO().read(reservationDTO.getIdLivre());
            MembreDTO membreDTO = getMembreDAO().read(reservationDTO.getIdMembre());

            // vérifie si le livre à réserver existe
            if(getLivreDAO().read(reservationDTO.getIdLivre()) == null) {
                throw new ServiceException("Le livre "
                    + reservationDTO.getIdLivre()
                    + " n'existe pas");
            }

            // vérifie si le livre à réserver est libre
            if(livreDTO.getIdMembre() == reservationDTO.getIdMembre()) {
                throw new ServiceException("Le livre "
                    + reservationDTO.getIdLivre()
                    + " est deja prete a se membre");
            }

            // vérifie si le membre déisrant faire le prêt existe
            if(membreDTO == null) {
                throw new ServiceException("Le membre "
                    + reservationDTO.getIdMembre()
                    + " n'existe pas");
            }

            // vérifie si le livre n'est pas déjà prèté
            if(membreDTO.getIdMembre() == 0) {
                throw new ServiceException("Livre "
                    + reservationDTO.getIdLivre()
                    + " n'est pas prete");
            }

            // vérifie si la date de la réservation est avant la date où le livre libre
            if(reservationDTO.getDateReservation().before(livreDTO.getDatePret())) {
                throw new ServiceException("La date de reservation "
                    + reservationDTO.getDateReservation()
                    + " est avant la date de pret du livre");
            }

            getReservationDAO().add(reservationDTO);
        } catch(DAOException e) {
            e.printStackTrace();
        }

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
     * @throws BibliothequeException the biblio exception
     * @throws Exception the exception
     */

    /**
     * Prise d'une réservation.
     * Le livre ne doit pas être prêté.
     * Le membre ne doit pas avoir dépassé sa limite de pret.
     * La réservation doit la être la première en liste.
     *
     * @param idReservation the id reservation
     * @param datePret the date pret
     * @throws SQLException the SQL exception
     * @throws BibliothequeException the biblio exception
     * @throws Exception the exception
     */
    public void prendreRes(ReservationDTO reservationDTO) throws ServiceException {
        try {
            LivreDTO livreDTO = getLivreDAO().read(reservationDTO.getIdLivre());
            // Vérifie s'il existe une réservation pour le livre
            if(getReservationDAO().read(reservationDTO.getIdReservation()) == null) {
                throw new ServiceException("Réservation inexistante : "
                    + getReservationDAO().read(reservationDTO.getIdReservation()).getIdReservation());
            }

            // Vérifie que c'est la première réservation pour le livre
            ReservationDTO premiereReservation = getReservationDAO().read(reservationDTO.getIdLivre());
            if(premiereReservation.getIdReservation() != getReservationDAO().read(reservationDTO.getIdLivre()).getIdReservation()) {
                throw new ServiceException("La réservation n'est pas la première de la liste "
                    + "pour ce livre; la premiere est "
                    + premiereReservation.getIdReservation());
            }

            // vérifie si le livre existe
            if(livreDTO == null) {
                throw new ServiceException("Livre inexistant: "
                    + reservationDTO.getIdLivre());
            }
            if(livreDTO.getIdMembre() != 0) {
                throw new ServiceException("Livre "
                    + livreDTO.getIdLivre()
                    + " deja prêté à "
                    + livreDTO.getIdMembre());
            }

            // vérifie si le membre existe et respecte sa limite de prêts
            MembreDTO membreDTO = getMembreDAO().read(reservationDTO.getIdMembre());
            if(membreDTO == null) {
                throw new ServiceException("Membre inexistant: "
                    + reservationDTO.getIdMembre());
            }
            if(membreDTO.getNbPret() >= membreDTO.getLimitePret()) {
                throw new ServiceException("Limite de prêt du membre "
                    + reservationDTO.getIdMembre()
                    + " atteinte");
            }

            // vérifie si la date de prêt est suppérieure à la date de réservation
            if(livreDTO.getDatePret().before(reservationDTO.getDateReservation())) {
                throw new ServiceException("Date de prêt inférieure à la date de réservation");
            }

            // vérifie si le livre et le membre qui sont concernés existent
            /*if(this.livre.preter(tupleReservation.idLivre, tupleReservation.idMembre, datePret) == 0) {
                throw new ServiceException("Livre supprimé par une autre transaction");
            }
            if(this.membre.preter(tupleReservation.idMembre) == 0) {
                throw new ServiceException("Membre supprimé par une autre transaction");
            }*/// IMPLÉMENTATION PLUS TARD, REQUIERT PrêtDAO
            /* Eliminer la réservation */
            annulerRes(reservationDTO);
        } catch(DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Annulation d'une réservation.
     * La réservation doit exister.
     *
     * @param idReservation the id reservation
     * @throws SQLException the SQL exception
     * @throws BibliothequeException the biblio exception
     * @throws Exception the exception
     */

    // annule une réservation si elle existe
    public void annulerRes(ReservationDTO reservationDTO) throws ServiceException {

        try {
            if(getReservationDAO().read(reservationDTO.getIdReservation()) != null) {
                throw new ServiceException("La réservaion "
                    + reservationDTO.getIdReservation()
                    + " n'existe pas");
            }

            getReservationDAO().delete(reservationDTO);
        } catch(DAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
