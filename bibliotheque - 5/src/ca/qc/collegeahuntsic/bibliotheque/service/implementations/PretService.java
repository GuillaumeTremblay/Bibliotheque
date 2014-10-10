// Fichier PretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;

/**
 * Service de la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public class PretService extends Service implements IPretService {
    private IPretDAO pretDAO;

    private IMembreDAO membreDAO;

    private ILivreDAO livreDAO;

    private IReservationDAO reservationDAO;

    /**
     * Crée le service de la table <code>pret</code>.
     * 
     * @param pretDAO Le DAO de la table <code>pret</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     * @throws InvalidDAOException Si le DAO de prêt est <code>null</code>, si le DAO de membre est <code>null</code>, si le DAO de livre est
     *         <code>null</code> ou si le DAO de réservation est <code>null</code>
     */
    public PretService(IPretDAO pretDAO, // TODO: Change to package when switching to Spring
        IMembreDAO membreDAO,
        ILivreDAO livreDAO,
        IReservationDAO reservationDAO) throws InvalidDAOException {
        super();
        if(pretDAO == null) {
            throw new InvalidDAOException("Le DAO de prêt ne peut être null");
        }
        if(membreDAO == null) {
            throw new InvalidDAOException("Le DAO de membre ne peut être null");
        }
        if(livreDAO == null) {
            throw new InvalidDAOException("Le DAO de livre ne peut être null");
        }
        if(reservationDAO == null) {
            throw new InvalidDAOException("Le DAO de réservation ne peut être null");
        }
        setPretDAO(pretDAO);
        setMembreDAO(membreDAO);
        setLivreDAO(livreDAO);
        setReservationDAO(reservationDAO);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @return La variable d'instance <code>this.pretDAO</code>
     */
    private IPretDAO getPretDAO() {
        return this.pretDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @param pretDAO La valeur à utiliser pour la variable d'instance <code>this.pretDAO</code>
     */
    private void setPretDAO(IPretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

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
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException {
        try {
            getPretDAO().add(connexion,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PretDTO get(Connexion connexion,
        String idPret) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (PretDTO) getPretDAO().get(connexion,
                idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().update(connexion,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().delete(connexion,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<PretDTO>) getPretDAO().getAll(connexion,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getPretDAO().findByMembre(connexion,
                idMembre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getPretDAO().findByLivre(connexion,
                idLivre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getPretDAO().findByDatePret(connexion,
                datePret,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getPretDAO().findByDateRetour(connexion,
                dateRetour,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commencer(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        InvalidLoanLimitException,
        ExistingReservationException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        try {
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(connexion,
                pretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + pretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion,
                pretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + pretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByLivre(connexion,
                unLivreDTO.getIdLivre(),
                PretDTO.DATE_PRET_COLUMN_NAME);
            if(!prets.isEmpty()) {
                PretDTO unPretDTO = prets.get(0);
                MembreDTO emprunteur = (MembreDTO) getMembreDAO().get(connexion,
                    unPretDTO.getMembreDTO().getIdMembre());
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
            if(unMembreDTO.getNbPret().equals(unMembreDTO.getLimitePret())) {
                throw new InvalidLoanLimitException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(connexion,
                unLivreDTO.getIdLivre(),
                ReservationDTO.DATE_RESERVATION_COLUMN_NAME);
            if(!reservations.isEmpty()) {
                ReservationDTO uneReservationDTO = reservations.get(0);
                MembreDTO booker = (MembreDTO) getMembreDAO().get(connexion,
                    uneReservationDTO.getMembreDTO().getIdMembre());
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
            unMembreDTO.setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) + 1));
            getMembreDAO().update(connexion,
                unMembreDTO);
            pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            pretDTO.setDateRetour(null);
            add(connexion,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renouveler(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidDTOClassException,
        ServiceException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        try {
            PretDTO unPretDTO = get(connexion,
                pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new MissingDTOException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(connexion,
                unPretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion,
                unPretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + unPretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByMembre(connexion,
                unMembreDTO.getIdMembre(),
                PretDTO.DATE_PRET_COLUMN_NAME);
            if(prets.isEmpty()) {
                throw new MissingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            boolean aEteEmprunteParMembre = false;
            for(PretDTO unAutrePretDTO : prets) {
                aEteEmprunteParMembre = unMembreDTO.equals(unAutrePretDTO.getMembreDTO());
            }
            if(!aEteEmprunteParMembre) {
                throw new ExistingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'a pas été prêté à "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ")");
            }
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(connexion,
                unLivreDTO.getIdLivre(),
                ReservationDTO.DATE_RESERVATION_COLUMN_NAME);
            if(!reservations.isEmpty()) {
                ReservationDTO uneReservationDTO = reservations.get(0);
                MembreDTO booker = (MembreDTO) getMembreDAO().get(connexion,
                    uneReservationDTO.getMembreDTO().getIdMembre());
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
            unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            unPretDTO.setDateRetour(null);
            update(connexion,
                unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminer(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        InvalidDTOClassException,
        ServiceException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        try {
            PretDTO unPretDTO = get(connexion,
                pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new MissingDTOException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(connexion,
                unPretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion,
                unPretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + unPretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByMembre(connexion,
                unMembreDTO.getIdMembre(),
                PretDTO.DATE_PRET_COLUMN_NAME);
            if(prets.isEmpty()) {
                throw new MissingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            boolean aEteEmprunteParMembre = false;
            for(PretDTO unAutrePretDTO : prets) {
                aEteEmprunteParMembre = unMembreDTO.equals(unAutrePretDTO.getMembreDTO());
            }
            if(!aEteEmprunteParMembre) {
                throw new ExistingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'a pas été prêté à "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ")");
            }
            unMembreDTO.setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) - 1));
            getMembreDAO().update(connexion,
                unMembreDTO);
            unPretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));
            update(connexion,
                unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
