
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
     * Crée le service de la table <code>reservation</code>.
     *
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param livreDAO Le DAO de la table <code>livre</code>
     */
    public ReservationService(ReservationDAO reservationDAO,
        LivreDAO livreDAO,
        MembreDAO membreDAO) {
        super();
        setReservationDAO(reservationDAO);
        setMembreDAO(membreDAO);
        setLivreDAO(livreDAO);
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
    public ReservationDTO read(ReservationDTO reservationDTO) throws ServiceException {
        try {
            return getReservationDAO().read(reservationDTO);
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

    /**
     * Réserve un livre.
     *
     * @param reservationDTO La réservation à créer
     * @param membreDTO Le membre qui réserve
     * @param livreDTO Le livre à réserver
     * @throws ServiceException Si la réservation existe déjà, si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore
     *         été prêté, si le livre est déjà prêté au membre, si le membre a déjà réservé ce livre ou s'il y a une erreur avec la base de
     *         données
     */
    public void reserver(ReservationDTO reservationDTO,
        MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
            ReservationDTO uneReservationDTO = read(reservationDTO);
            if(uneReservationDTO != null) {
                throw new ServiceException("La réservation "
                    + reservationDTO.getIdReservation()
                    + " existe déjà");
            }
            MembreDTO unMembreDTO = getMembreDAO().read(membreDTO);
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(livreDTO);
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }
            //            MembreDTO emprunteur = getMembreDAO().read(unLivreDTO.getIdMembre());
            //            if(emprunteur == null) {
            //                throw new ServiceException("Le livre "
            //                    + unLivreDTO.getTitre()
            //                    + " (ID de livre : "
            //                    + unLivreDTO.getIdLivre()
            //                    + ") n'est pas encore prêté");
            //            }
            //            if(unMembreDTO.getIdMembre() == emprunteur.getIdMembre()) {
            //                throw new ServiceException("Le livre "
            //                    + unLivreDTO.getTitre()
            //                    + " (ID de livre : "
            //                    + unLivreDTO.getIdLivre()
            //                    + ") est déjà prêté à "
            //                    + emprunteur.getNom()
            //                    + " (ID de membre : "
            //                    + emprunteur.getIdMembre()
            //                    + ")");
            //            }
            //
            //            // Cas éliminé en utilisant la date de réservation comme étant la date système de la base de données
            //
            //            /* Verifier si date reservation >= datePret */
            //            //          if(Date.valueOf(dateReservation).before(tupleLivre.getDatePret())) {
            //            //              throw new BibliothequeException("Date de réservation inférieure à la date de prêt");
            //            //          }
            //
            //            List<ReservationDTO> reservations = getReservationDAO().findByMembre(unMembreDTO);
            //            for(ReservationDTO uneAutreReservationDTO : reservations) {
            //                if(uneAutreReservationDTO.getIdLivre() == unLivreDTO.getIdLivre()) {
            //                    throw new ServiceException("Le livre "
            //                        + unLivreDTO.getTitre()
            //                        + " (ID de livre : "
            //                        + unLivreDTO.getIdLivre()
            //                        + ") est déjà réservé à "
            //                        + emprunteur.getNom()
            //                        + " (ID de membre : "
            //                        + emprunteur.getIdMembre()
            //                        + ")");
            //                }
            //            }
            add(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Utilise une réservation.
     *
     * @param reservationDTO La réservation à utiliser
     * @param membreDTO Le membre qui utilise sa réservation
     * @param livreDTO Le livre à emprunter
     * @throws ServiceException Si la réservation n'existe pas, si le membre n'existe pas, si le livre n'existe pas, si la réservation n'est pas
     *         la première de la liste, si le livre est déjà prété, si le membre a atteint sa limite de prêt ou s'il y a une erreur avec la base
     *         de données
     */
    public void utiliser(ReservationDTO reservationDTO,
        MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
            ReservationDTO uneReservationDTO = read(reservationDTO);
            if(uneReservationDTO == null) {
                throw new ServiceException("La réservation "
                    + reservationDTO.getIdReservation()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = getMembreDAO().read(membreDTO);
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(livreDTO);
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(unLivreDTO);
            if(!reservations.isEmpty()) {
                uneReservationDTO = reservations.get(0);
                if(uneReservationDTO.getMembreDTO().getIdMembre() != unMembreDTO.getIdMembre()) {
                    MembreDTO booker = getMembreDAO().read(uneReservationDTO.getMembreDTO());
                    throw new ServiceException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est réservé pour "
                        + booker.getNom()
                        + " (ID de membre : "
                        + booker.getIdMembre()
                        + ")");
                }
            }
            //            MembreDTO emprunteur = getMembreDAO().read(livreDTO.getIdMembre());
            //            if(emprunteur != null) {
            //                throw new ServiceException("Le livre "
            //                    + unLivreDTO.getTitre()
            //                    + " (ID de livre : "
            //                    + unLivreDTO.getIdLivre()
            //                    + ") a été prêté à "
            //                    + emprunteur.getNom()
            //                    + " (ID de membre : "
            //                    + emprunteur.getIdMembre()
            //                    + ")");
            //            }
            if(unMembreDTO.getNbPret() == unMembreDTO.getLimitePret()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }

            // Cas éliminé en utilisant la date de prêt et de réservation comme étant la date système de la base de données

            /* Verifier si datePret >= tupleReservation.dateReservation */
            //          if(Date.valueOf(datePret).before(tupleReservation.getDateReservation())) {
            //              throw new BibliothequeException("Date de prêt inférieure à la date de réservation");
            //          }

            annuler(uneReservationDTO);
            //            // On voit le manque de la table prêt simulée en ce moment par les deux tables
            //            unLivreDTO.setIdMembre(unMembreDTO.getIdMembre());
            //            getLivreDAO().emprunter(unLivreDTO);
            //            getMembreDAO().emprunter(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Annule une réservation.
     *
     * @param reservationDTO Le reservation à annuler
     * @throws ServiceException Si la réservation n'existe pas ou s'il y a une erreur avec la base de données
     */
    public void annuler(ReservationDTO reservationDTO) throws ServiceException {
        ReservationDTO uneReservationDTO = read(reservationDTO);
        if(uneReservationDTO == null) {
            throw new ServiceException("La réservation "
                + reservationDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }
        delete(uneReservationDTO);
    }
}
