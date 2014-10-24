// Fichier LivreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque_backend.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ca.qc.collegeahuntsic.bibliotheque_backend.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque_backend.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque_backend.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque_backend.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque_backend.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque_backend.service.interfaces.ILivreService;
import org.hibernate.Session;

/**
 * Service de la table <code>livre</code>.
 *
 * @author Gilles Benichou
 */
public class LivreService extends Service implements ILivreService {
    private ILivreDAO livreDAO;

    /**
     * Crée le service de la table <code>livre</code>.
     *
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param pretDAO Le DAO de la table <code>pret</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     * @throws InvalidDAOException Si le DAO de livre est <code>null</code>, si le DAO de membre est <code>null</code>, si le DAO de prêt est
     *         <code>null</code> ou si le DAO de réservation est <code>null</code>
     */
    public LivreService(ILivreDAO livreDAO) throws InvalidDAOException {
        super();
        if(livreDAO == null) {
            throw new InvalidDAOException("Le DAO de livre ne peut être null");
        }
        setLivreDAO(livreDAO);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @return La variable d'instance <code>this.livreDAO</code>
     */
    private ILivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @param livreDAO La valeur à utiliser pour la variable d'instance <code>this.livreDAO</code>
     */
    private void setLivreDAO(ILivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getLivreDAO().add(session,
                livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LivreDTO getLivre(Session session,
        String idLivre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (LivreDTO) getLivreDAO().get(session,
                idLivre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getLivreDAO().update(session,
                livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getLivreDAO().delete(session,
                livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LivreDTO> getAllLivres(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<LivreDTO>) getLivreDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LivreDTO> findLivreByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getLivreDAO().findByTitre(session,
                titre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acquerir(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        addLivre(session,
            livreDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vendre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(livreDTO == null) {
            throw new InvalidDTOException("Le livre ne peut être null");
        }
        LivreDTO unLivreDTO = getLivre(session,
            livreDTO.getIdLivre());
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + livreDTO.getIdLivre()
                + " n'existe pas");
        }
        Set<PretDTO> prets = unLivreDTO.getPrets();
        if(!prets.isEmpty()) {
            for(PretDTO pretDTO : prets) {
                if(pretDTO.getDateRetour() == null) {
                    MembreDTO emprunteur = pretDTO.getMembreDTO();
                    throw new ExistingLoanException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") a été prêté à "
                        + emprunteur.getNom()
                        + " (ID de membre : "
                        + emprunteur.getIdMembre()
                        + ")");
                }
            }
        }
        List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
        if(!reservations.isEmpty()) {
            ReservationDTO reservationDTO = reservations.get(0);
            MembreDTO booker = reservationDTO.getMembreDTO();
            throw new ExistingReservationException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") est réservé pour "
                + booker.getNom()
                + " (ID de membre : "
                + booker.getIdMembre()
                + ")");
        }
        deleteLivre(session,
            unLivreDTO);
    }
}
