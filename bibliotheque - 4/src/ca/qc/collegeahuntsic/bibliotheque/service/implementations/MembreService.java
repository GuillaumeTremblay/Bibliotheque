// Fichier MembreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.Service;

/**
 * Service de la table <code>membre</code>.
 * 
 * @author Gilles Benichou
 */
public class MembreService extends Service {
    private static final long serialVersionUID = 1L;

    private MembreDAO membreDAO;

    private ReservationDAO reservationDAO;

    /**
     * Crée le service de la table <code>membre</code>.
     * 
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public MembreService(MembreDAO membreDAO,
        ReservationDAO reservationDAO) {
        super();
        setMembreDAO(membreDAO);
        setReservationDAO(reservationDAO);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @return La variable d'instance <code>this.membreDAO</code>
     */
    private MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @param membreDAO La valeur à utiliser pour la variable d'instance <code>this.membreDAO</code>
     */
    private void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    private ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO La valeur à utiliser pour la variable d'instance <code>this.reservationDAO</code>
     */
    private void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    // EndRegion Getters and Setters

    /**
     * Ajoute un nouveau membre.
     * 
     * @param membreDTO Le membre à ajouter
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void add(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().add(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Lit un membre.
     * 
     * @param idMembre L'ID du membre à lire
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public MembreDTO read(int idMembre) throws ServiceException {
        try {
            return getMembreDAO().read(idMembre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Met à jour un membre.
     * 
     * @param membreDTO Le membre à mettre à jour
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void update(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().update(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Supprime un membre.
     * 
     * @param membreDTO Le membre à supprimer
     * @throws ServiceException Si le membre a encore des prêts, s'il a des réservations ou s'il y a une erreur avec la base de données
     */
    public void delete(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().delete(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve tous les membres.
     * 
     * @return La liste des membres ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public List<MembreDTO> getAll() throws ServiceException {
        try {
            return getMembreDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Inscrit un membre.
     * 
     * @param membreDTO Le membre à inscrire
     * @throws ServiceException Si le membre existe déjà ou s'il y a une erreur avec la base de données
     */
    public void inscrire(MembreDTO membreDTO) throws ServiceException {
        add(membreDTO);
    }

    /**
     * Désincrit un membre.
     * 
     * @param membreDTO Le membre à désinscrire
     * @throws ServiceException Si le membre n'existe pas, si le membre a encore des prêts, s'il a des réservations ou s'il y a une erreur avec
     *         la base de données
     */
    public void desincrire(MembreDTO membreDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = read(membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            if(unMembreDTO.getNbPret() > 0) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a encore des prêts");
            }
            if(!getReservationDAO().findByMembre(unMembreDTO.getIdMembre()).isEmpty()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a des réservations");
            }
            delete(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
