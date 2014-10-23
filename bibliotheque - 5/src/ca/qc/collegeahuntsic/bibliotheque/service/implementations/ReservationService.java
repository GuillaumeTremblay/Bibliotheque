// Fichier ReservationService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
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
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IReservationService;
import org.hibernate.Session;

/**
 * Service de la table <code>reservation</code>.
 *
 * @author Gilles Benichou
 */
public class ReservationService extends Service implements IReservationService {
    private IReservationDAO reservationDAO;

    /**
     * Crée le service de la table <code>reservation</code>.
     *
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param pretDAO Le DAO de la table <code>pret</code>
     * @throws InvalidDAOException Si le DAO de réservation est <code>null</code>, si le DAO de membre est <code>null</code>, si le DAO de livre
     *         est <code>null</code> ou si le DAO de prêt est <code>null</code>
     */
    ReservationService(IReservationDAO reservationDAO) throws InvalidDAOException {
        super();
        if(reservationDAO == null) {
            throw new InvalidDAOException("Le DAO de réservation ne peut être null");
        }
        setReservationDAO(reservationDAO);
    }

    // Region Getters and Setters
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
    public void addReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getReservationDAO().add(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReservationDTO getReservation(Session session,
        String idReservation) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (ReservationDTO) getReservationDAO().get(session,
                idReservation);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getReservationDAO().update(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getReservationDAO().delete(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservationDTO> getAllReservations(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<ReservationDTO>) getReservationDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placer(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
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
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        MembreDTO membreDTO = reservationDTO.getMembreDTO();
        LivreDTO livreDTO = reservationDTO.getLivreDTO();
        List<PretDTO> prets = new ArrayList<>(livreDTO.getPrets());
        if(prets.isEmpty()) {
            throw new MissingLoanException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") n'est pas encore prêté");
        }

        boolean aEteEmprunteParMembre = false;
        for(PretDTO pretDTO : prets) {
            aEteEmprunteParMembre = membreDTO.equals(pretDTO.getMembreDTO());
        }
        if(aEteEmprunteParMembre) {
            throw new ExistingLoanException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") est déjà prêté à "
                + membreDTO.getNom()
                + " (ID de membre : "
                + membreDTO.getIdMembre()
                + ")");
        }
        List<ReservationDTO> reservations = new ArrayList<>(membreDTO.getReservations());
        for(ReservationDTO uneAutreReservationDTO : reservations) {
            if(livreDTO.equals(uneAutreReservationDTO.getLivreDTO())) {
                throw new ExistingReservationException("Le livre "
                    + livreDTO.getTitre()
                    + " (ID de livre : "
                    + livreDTO.getIdLivre()
                    + ") est déjà réservé pour quelqu'un d'autre");
            }
        }
        reservationDTO.setDateReservation(new Timestamp(System.currentTimeMillis()));
        addReservation(session,
            reservationDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void utiliser(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        try {
            ReservationDTO uneReservationDTO = getReservation(session,
                reservationDTO.getIdReservation());
            if(uneReservationDTO == null) {
                throw new MissingDTOException("La réservation "
                    + reservationDTO.getIdReservation()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = uneReservationDTO.getLivreDTO();
            MembreDTO unMembreDTO = uneReservationDTO.getMembreDTO();
            List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
            if(!reservations.isEmpty()) {
                uneReservationDTO = reservations.get(0);
                if(!unMembreDTO.equals(uneReservationDTO.getMembreDTO())) {
                    MembreDTO booker = uneReservationDTO.getMembreDTO();
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
            }
            List<PretDTO> prets = new ArrayList<>(unLivreDTO.getPrets());
            if(!prets.isEmpty()) {
                PretDTO unPretDTO = prets.get(0);
                MembreDTO emprunteur = unPretDTO.getMembreDTO();
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
            annuler(session,
                uneReservationDTO);
            unMembreDTO.setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) + 1));
            getReservationDAO().update(session,
                unMembreDTO);
            PretDTO unPretDTO = new PretDTO();
            unPretDTO.setMembreDTO(unMembreDTO);
            unPretDTO.setLivreDTO(unLivreDTO);
            unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            unPretDTO.setDateRetour(null);
            getReservationDAO().add(session,
                unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void annuler(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidDTOClassException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        ReservationDTO uneReservationDTO = getReservation(session,
            reservationDTO.getIdReservation());
        if(uneReservationDTO == null) {
            throw new MissingDTOException("La réservation "
                + reservationDTO.getIdReservation()
                + " n'existe pas");
        }
        deleteReservation(session,
            uneReservationDTO);
    }
}
