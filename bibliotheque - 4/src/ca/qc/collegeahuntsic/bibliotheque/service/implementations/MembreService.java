// Fichier MembreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;

import java.util.List;

import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.Service;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IMembreService;

/**
 * Service de la table <code>membre</code>.
 * 
 * @author Gilles Benichou
 */
public class MembreService extends Service implements IMembreService {
    private static final long serialVersionUID = 1L;

    private IMembreDAO membreDAO;

    private IReservationDAO reservationDAO;

    /**
     * Crée le service de la table <code>membre</code>.
     * 
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public MembreService(MembreDAO membreDAO,
        IReservationDAO reservationDAO) {
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
    private IMembreDAO getMembreDAO() {
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
    private IReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO La valeur à utiliser pour la variable d'instance <code>this.reservationDAO</code>
     */
    private void setReservationDAO(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    // EndRegion Getters and Setters
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Connexion connexion,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException {
        try {
            getMembreDAO().add(connexion,
                membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembreDTO get(Connexion connexion,
        String idMembre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (MembreDTO) getMembreDAO().get(connexion,
                idMembre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Connexion connexion,
    	MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getMembreDAO().update(connexion,
            	membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Connexion connexion,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getMembreDAO().delete(connexion,
                membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<MembreDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<MembreDTO>) getMembreDAO().getAll(connexion,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void inscrire(Connexion connexion,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException {
        add(connexion,
            membreDTO);
    }

    /**
     * Désincrit un membre.
     * 
     * @param membreDTO Le membre à désinscrire
     * @throws ServiceException Si le membre n'existe pas, si le membre a encore des prêts, s'il a des réservations ou s'il y a une erreur avec
     *         la base de données
     */
    	
    public void desincrire(Connexion connexion ,MembreDTO membreDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = get(connexion,membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            if(Integer.parseInt(unMembreDTO.getNbPret()) > 0) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a encore des prêts");
            }
            List<ReservationDTO> reservationList =getReservationDAO().findByMembre(connexion,
                    unMembreDTO.getIdMembre(),
                    ReservationDTO.DATE_RESERVATION_COLUMN_NAME);
            if(!reservationList.isEmpty()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a des réservations");
            }
            delete(connexion,unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
