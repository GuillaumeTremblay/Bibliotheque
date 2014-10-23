// Fichier PretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
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
import org.hibernate.Session;

/**
 * Service de la table <code>pret</code>.
 *
 * @author Gilles Benichou
 */
public class PretService extends Service implements IPretService {
    private IPretDAO pretDAO;

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
    PretService(IPretDAO pretDAO) throws InvalidDAOException {
        super();
        if(pretDAO == null) {
            throw new InvalidDAOException("Le DAO de prêt ne peut être null");
        }
        setPretDAO(pretDAO);
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

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().add(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PretDTO getPret(Session session,
        String idPret) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (PretDTO) getPretDAO().get(session,
                idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().update(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().delete(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> getAllPrets(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<PretDTO>) getPretDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findPretByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getPretDAO().findPretByDatePret(session,
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
    public List<PretDTO> findPretByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getPretDAO().findPretByDateRetour(session,
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
    public void commencer(Session session,
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
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        LivreDTO livreDTO = pretDTO.getLivreDTO();
        List<PretDTO> prets = new ArrayList<>(livreDTO.getPrets());
        if(!prets.isEmpty()) {
            PretDTO unPretDTO = prets.get(0);
            MembreDTO emprunteur = unPretDTO.getMembreDTO();
            throw new ExistingLoanException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") a été prêté à "
                + emprunteur.getNom()
                + " (ID de membre : "
                + emprunteur.getIdMembre()
                + ")");
        }
        MembreDTO membreDTO = pretDTO.getMembreDTO();
        if(membreDTO.getNbPret().equals(membreDTO.getLimitePret())) {
            throw new InvalidLoanLimitException("Le membre "
                + membreDTO.getNom()
                + " (ID de membre : "
                + membreDTO.getIdMembre()
                + ") a atteint sa limite de prêt ("
                + membreDTO.getLimitePret()
                + " emprunt(s) maximum)");
        }
        List<ReservationDTO> reservations = new ArrayList<>(livreDTO.getReservations());
        if(!reservations.isEmpty()) {
            ReservationDTO uneReservationDTO = reservations.get(0);
            MembreDTO booker = uneReservationDTO.getMembreDTO();
            throw new ExistingReservationException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") est réservé pour "
                + booker.getNom()
                + " (ID de membre : "
                + booker.getIdMembre()
                + ")");
        }
        pretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(membreDTO.getNbPret()) + 1));
        pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        pretDTO.setDateRetour(null);
        addPret(session,
            pretDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renouveler(Session session,
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
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        PretDTO unPretDTO = getPret(session,
            pretDTO.getIdPret());
        if(unPretDTO == null) {
            throw new MissingDTOException("Le prêt "
                + pretDTO.getIdPret()
                + " n'existe pas");
        }
        MembreDTO membreDTO = unPretDTO.getMembreDTO();
        LivreDTO livreDTO = unPretDTO.getLivreDTO();
        List<PretDTO> prets = new ArrayList<>(livreDTO.getPrets());
        if(prets.isEmpty()) {
            throw new MissingLoanException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") n'est pas encore prêté");
        }
        boolean aEteEmprunteParMembre = false;
        for(PretDTO unAutrePretDTO : prets) {
            aEteEmprunteParMembre = membreDTO.equals(unAutrePretDTO.getMembreDTO());
        }
        if(!aEteEmprunteParMembre) {
            throw new ExistingLoanException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") n'a pas été prêté à "
                + membreDTO.getNom()
                + " (ID de membre : "
                + membreDTO.getIdMembre()
                + ")");
        }
        List<ReservationDTO> reservations = new ArrayList<>(livreDTO.getReservations());
        if(!reservations.isEmpty()) {
            ReservationDTO uneReservationDTO = reservations.get(0);
            MembreDTO booker = uneReservationDTO.getMembreDTO();
            throw new ExistingReservationException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") est réservé pour "
                + booker.getNom()
                + " (ID de membre : "
                + booker.getIdMembre()
                + ")");
        }
        unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        unPretDTO.setDateRetour(null);
        updatePret(session,
            unPretDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminer(Session session,
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
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        PretDTO unPretDTO = getPret(session,
            pretDTO.getIdPret());
        if(unPretDTO == null) {
            throw new MissingDTOException("Le prêt "
                + pretDTO.getIdPret()
                + " n'existe pas");
        }
        MembreDTO membreDTO = unPretDTO.getMembreDTO();
        LivreDTO livreDTO = unPretDTO.getLivreDTO();
        List<PretDTO> prets = new ArrayList<>(livreDTO.getPrets());
        if(prets.isEmpty()) {
            throw new MissingLoanException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") n'est pas encore prêté");
        }
        boolean aEteEmprunteParMembre = false;
        for(PretDTO unAutrePretDTO : prets) {
            aEteEmprunteParMembre = membreDTO.equals(unAutrePretDTO.getMembreDTO());
        }
        if(!aEteEmprunteParMembre) {
            throw new ExistingLoanException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") n'a pas été prêté à "
                + membreDTO.getNom()
                + " (ID de membre : "
                + membreDTO.getIdMembre()
                + ")");
        }
        unPretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(unPretDTO.getMembreDTO().getNbPret()) - 1));
        unPretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));
        updatePret(session,
            unPretDTO);
    }
}
