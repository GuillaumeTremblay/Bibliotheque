// Fichier PretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IPretService;
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
    public void commencerPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingLoanException,
        InvalidLoanLimitException,
        ExistingReservationException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        final LivreDTO livreDTO = pretDTO.getLivreDTO();
        final List<PretDTO> prets = new ArrayList<>(livreDTO.getPrets());
        if(!prets.isEmpty()) {
            final PretDTO unPretDTO = prets.get(0);
            final MembreDTO emprunteur = unPretDTO.getMembreDTO();
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
        final MembreDTO membreDTO = pretDTO.getMembreDTO();
        if(membreDTO.getNbPret().equals(membreDTO.getLimitePret())) {
            throw new InvalidLoanLimitException("Le membre "
                + membreDTO.getNom()
                + " (ID de membre : "
                + membreDTO.getIdMembre()
                + ") a atteint sa limite de prêt ("
                + membreDTO.getLimitePret()
                + " emprunt(s) maximum)");
        }
        final List<ReservationDTO> reservations = new ArrayList<>(livreDTO.getReservations());
        if(!reservations.isEmpty()) {
            final ReservationDTO uneReservationDTO = reservations.get(0);
            final MembreDTO booker = uneReservationDTO.getMembreDTO();
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
        addPret(session,
            pretDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renouvelerPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        ExistingReservationException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        if(pretDTO.getDateRetour() != null) {
            throw new MissingLoanException("Le livre "
                + pretDTO.getLivreDTO().getTitre()
                + " (ID de livre : "
                + pretDTO.getLivreDTO().getIdLivre()
                + ") a déjà été retourné");
        }
        final List<ReservationDTO> reservations = new ArrayList<>(pretDTO.getLivreDTO().getReservations());
        if(!reservations.isEmpty()) {
            final ReservationDTO uneReservationDTO = reservations.get(0);
            throw new ExistingReservationException("Le livre "
                + pretDTO.getLivreDTO().getTitre()
                + " (ID de livre : "
                + pretDTO.getLivreDTO().getIdLivre()
                + ") est réservé pour "
                + uneReservationDTO.getMembreDTO().getNom()
                + " (ID de membre : "
                + uneReservationDTO.getMembreDTO().getIdMembre()
                + ")");
        }
        pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        pretDTO.setDateRetour(null);
        updatePret(session,
            pretDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminerPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        final MembreDTO membreDTO = pretDTO.getMembreDTO();
        final LivreDTO livreDTO = pretDTO.getLivreDTO();
        final List<PretDTO> prets = new ArrayList<>(livreDTO.getPrets());
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
            throw new ServiceException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") n'a pas été prêté à "
                + membreDTO.getNom()
                + " (ID de membre : "
                + membreDTO.getIdMembre()
                + ")");
        }
        updatePret(session,
            pretDTO);
    }
}
