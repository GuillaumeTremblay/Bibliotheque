// Fichier MembreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.util.ArrayList;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IMembreService;
import org.hibernate.Session;

/**
 * Service de la table <code>membre</code>.
 *
 * @author Gilles Benichou
 */
public class MembreService extends Service implements IMembreService {
    private IMembreDAO membreDAO;

    /**
     * Crée le service de la table <code>membre</code>.
     *
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @throws InvalidDAOException Si le DAO de membre est <code>null</code> ou si le DAO de réservation est <code>null</code>
     */
    MembreService(IMembreDAO membreDAO) throws InvalidDAOException {
        super();
        if(membreDAO == null) {
            throw new InvalidDAOException("Le DAO de membre ne peut être null");
        }
        setMembreDAO(membreDAO);
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
    private void setMembreDAO(IMembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getMembreDAO().add(session,
                membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembreDTO getMembre(Session session,
        String idMembre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (MembreDTO) getMembreDAO().get(session,
                idMembre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getMembreDAO().update(session,
                membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getMembreDAO().delete(session,
                membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MembreDTO> getAllMembres(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<MembreDTO>) getMembreDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MembreDTO> findMembreByNom(Session session,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getMembreDAO().findByNom(session,
                nom,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inscrire(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        addMembre(session,
            membreDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void desinscrire(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        ExistingLoanException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(membreDTO == null) {
            throw new InvalidDTOException("Le membre ne peut être null");
        }
        try {
            final MembreDTO unMembreDTO = getMembre(session,
                membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            if(Integer.parseInt(unMembreDTO.getNbPret()) > 0) {
                throw new ExistingLoanException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a encore des prêts");
            }
            final List<ReservationDTO> reservations = new ArrayList<>(unMembreDTO.getReservations());
            if(!reservations.isEmpty()) {
                throw new ExistingReservationException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a des réservations");
            }
            deleteMembre(session,
                unMembreDTO);
        } catch(NumberFormatException numberFormatException) {
            throw new ServiceException(numberFormatException);
        }
    }
}
