// Fichier PretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

//[region] Importations
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.PretDAO;
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
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.DTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.Service;
//[endRegion] Fin package, importations
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;

/**
 * Service de la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public class PretService extends Service implements IPretService{
	
//[region] Membres
	
    private static final long serialVersionUID = 1L;

    private IPretDAO pretDAO;

    private IMembreDAO membreDAO;

    private ILivreDAO livreDAO;

    private IReservationDAO reservationDAO;
    
//[endRegion] Fin membres

//[region] Constructeur
    
    /**
     * Crée le service de la table <code>pret</code>.
     * 
     * @param pretDAO Le DAO de la table <code>pret</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public PretService(IPretDAO pretDAO, IMembreDAO membreDAO, ILivreDAO livreDAO, IReservationDAO reservationDAO) {
    	
        super();
        setPretDAO(pretDAO);
        setMembreDAO(membreDAO);
        setLivreDAO(livreDAO);
        setReservationDAO(reservationDAO);
        
    }
    
//[endRegion] Fin constructeur
    
//[region] Accesseurs et mutateurs
    
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
    
//[endRegion] Fin accesseurs et mutateurs

//[region] Méthodes CRUD
    
    /**
     * Ajoute un nouveau prêt.
     * 
     * @param pretDTO Le prêt à ajouter
     * @throws ServiceException S'il y a une erreur avec la base de données
     * @throws InvalidPrimaryKeyRequestException 
     * @throws InvalidDTOClassException 
     * @throws InvalidDTOException 
     * @throws InvalidHibernateSessionException 
     */
    public void add(Connexion connexion, PretDTO pretDTO) throws 
    ServiceException, 
    InvalidHibernateSessionException, 
    InvalidDTOException, 
    InvalidDTOClassException, 
    InvalidPrimaryKeyRequestException
    {
    	
        try {
            getPretDAO().add(connexion, pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
        
    }

    /**
     * Lit un prêt.
     * 
     * @param idPret L'ID du prêt à lire
     * @throws ServiceException S'il y a une erreur avec la base de données
     * @throws InvalidPrimaryKeyException 
     * @throws InvalidHibernateSessionException 
     */
    public PretDTO get(Connexion connexion, String idPret) throws 
    ServiceException, 
    InvalidHibernateSessionException, 
    InvalidPrimaryKeyException 
    {
    	
        try {
			return (PretDTO) getPretDAO().get(connexion, idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
        
    }

    /**
     * Met à jour un prêt.
     * 
     * @param pretDTO Le prêt à mettre à jour
     * @throws ServiceException S'il y a une erreur avec la base de données
     * @throws InvalidDTOClassException 
     * @throws InvalidDTOException 
     * @throws InvalidHibernateSessionException 
     */
    public void update(Connexion connexion, PretDTO pretDTO) throws 
    	ServiceException, 
    	InvalidHibernateSessionException,
    	InvalidDTOException, 
    	InvalidDTOClassException 
    {
    	
        try {
            getPretDAO().update(connexion, pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
        
    }

    /**
     * Supprime un prêt.
     * 
     * @param pretDTO Le prêt à supprimer
     * @throws ServiceException S'il y a une erreur avec la base de données
     * @throws InvalidDTOClassException 
     * @throws InvalidDTOException 
     * @throws InvalidHibernateSessionException 
     */
    public void delete(Connexion connexion, PretDTO pretDTO) throws 
    	ServiceException, 
    	InvalidHibernateSessionException, 
    	InvalidDTOException, 
    	InvalidDTOClassException 
    	{
    	
        try {
            getPretDAO().delete(connexion, pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
        
    }
//[endRegion] Fin méthodes CRUD
  
//[region] Méthodes de recherche
    
    /**
     * Trouve tous les prêts.
     * 
     * @return La liste des prêts ; une liste vide sinon
     * @throws InvalidSortByPropertyException 
     * @throws InvalidHibernateSessionException 
     * @throws ServiceException 
     */
    public List<PretDTO> getAll(Connexion connexion, String sortByPropertyName) throws InvalidHibernateSessionException, InvalidSortByPropertyException, ServiceException 
    	{
    	
        try {
            return (List<PretDTO>) getPretDAO().getAll(connexion, sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
        
    }

    /**
     * Trouve les prêts non terminés d'un membre.
     * 
     * @param idMembre L'ID du membre à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidSortByPropertyException 
     * @throws InvalidCriterionException 
     * @throws InvalidHibernateSessionException 
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByMembre(Connexion connexion, String idMembre, String sortByPropertyName) throws 
    	ServiceException, 
    	InvalidHibernateSessionException, 
    	InvalidCriterionException, 
    	InvalidSortByPropertyException 
    	{
    	
        try {
            return getPretDAO().findByMembre(connexion, idMembre, sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
        
    }

    /**
     * Trouve les livres en cours d'emprunt.
     * 
     * @param idLivre L'ID du livre à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidSortByPropertyException 
     * @throws InvalidCriterionException 
     * @throws InvalidHibernateSessionException 
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByLivre(Connexion connexion, String idLivre, String sortByPropertyName) throws 
    	ServiceException, 
    	InvalidHibernateSessionException, 
    	InvalidCriterionException, 
    	InvalidSortByPropertyException {
        try {
            return getPretDAO().findByLivre(connexion, idLivre, sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les prêts à partir d'une date de prêt.
     * 
     * @param datePret La date de prêt à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidSortByPropertyException 
     * @throws InvalidCriterionException 
     * @throws InvalidHibernateSessionException 
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByDatePret(Connexion connexion, Timestamp datePret) throws 
    	ServiceException, InvalidHibernateSessionException, 
    	InvalidCriterionException, 
    	InvalidSortByPropertyException {
        try {
            return getPretDAO().findByDatePret(connexion, datePret, null);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les prêts à partir d'une date de retour.
     * 
     * @param dateRetour La date de retour à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidSortByPropertyException 
     * @throws InvalidCriterionException 
     * @throws InvalidHibernateSessionException 
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByDateRetour(Connexion connexion, Timestamp dateRetour, String sortByPropertyName) throws 
    	ServiceException, 
    	InvalidHibernateSessionException, 
    	InvalidCriterionException, 
    	InvalidSortByPropertyException {
        try {
            return getPretDAO().findByDateRetour(connexion, dateRetour, sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
//[endRegion] Fin méthodes de recherche

//[region] Méthodes de commande
    /**
     * Commence un prêt.
     * 
     * @param pretDTO Le prêt à commencer
     * @throws ServiceException Si le membre n'existe pas, si le livre n'existe pas, si le livre a été prêté, si le livre a été réservé, si le
     *         membre a atteint sa limite de prêt ou s'il y a une erreur avec la base de données
     * @throws InvalidPrimaryKeyException 
     * @throws InvalidHibernateSessionException 
     * @throws DTOException 
     * @throws InvalidSortByPropertyException 
     * @throws InvalidCriterionException 
     * @throws InvalidPrimaryKeyRequestException 
     * @throws InvalidDTOClassException 
     * @throws InvalidDTOException 
     */
    public void commencer(Connexion connexion, PretDTO pretDTO, String sortByPropertyName) throws 
    	ServiceException, 
    	InvalidHibernateSessionException, 
    	InvalidPrimaryKeyException, DTOException, InvalidCriterionException, InvalidSortByPropertyException, InvalidDTOException, InvalidDTOClassException, InvalidPrimaryKeyRequestException {
        try {
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(connexion, pretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + pretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion, pretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + pretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByLivre(connexion, unLivreDTO.getIdLivre(), sortByPropertyName);
            if(!prets.isEmpty()) {
                PretDTO unPretDTO = prets.get(0);
                MembreDTO emprunteur = (MembreDTO) getMembreDAO().get(connexion, unPretDTO.getMembreDTO().getIdMembre());
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a été prêté à "
                    + emprunteur.getNom()
                    + " (ID de membre : "
                    + emprunteur.getIdMembre()
                    + ")");
            }
            if(unMembreDTO.getNbPret() == unMembreDTO.getLimitePret()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(connexion, unLivreDTO.getIdLivre(), sortByPropertyName);
            if(!reservations.isEmpty()) {
                ReservationDTO uneReservationDTO = reservations.get(0);
                MembreDTO booker = (MembreDTO) getMembreDAO().get(connexion, uneReservationDTO.getMembreDTO().getIdMembre());
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
            unMembreDTO.setNbPret(unMembreDTO.getNbPret() + 1);
            getMembreDAO().update(connexion, unMembreDTO);
            pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            pretDTO.setDateRetour(null);
            add(connexion, pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Renouvelle le prêt d'un livre.
     * 
     * @param pretDTO Le prêt à renouveler
     * @throws ServiceException 
     * @throws InvalidPrimaryKeyException 
     * @throws InvalidHibernateSessionException 
     * @throws DTOException 
     * @throws InvalidSortByPropertyException 
     * @throws InvalidCriterionException 
     * @throws InvalidDTOClassException 
     * @throws InvalidDTOException 
     */
    public void renouveler(Connexion connexion, PretDTO pretDTO, String sortByPropertyName) throws 
	    ServiceException, 
	    InvalidHibernateSessionException, 
	    InvalidPrimaryKeyException, 
	    DTOException, InvalidCriterionException, 
	    InvalidSortByPropertyException, 
	    InvalidDTOException, 
	    InvalidDTOClassException {
        try {
            PretDTO unPretDTO = get(connexion, pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new ServiceException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(connexion, unPretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion, unPretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + unPretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByMembre(connexion, unMembreDTO.getIdMembre(), sortByPropertyName);
            if(prets.isEmpty()) {
                throw new ServiceException("Le livre "
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
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'a pas été prêté à "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ")");
            }
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(connexion, unLivreDTO.getIdLivre(), sortByPropertyName);
            if(!reservations.isEmpty()) {
                ReservationDTO uneReservationDTO = reservations.get(0);
                MembreDTO booker = (MembreDTO) getMembreDAO().get(connexion, uneReservationDTO.getMembreDTO().getIdMembre());
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
            unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            unPretDTO.setDateRetour(null);
            update(connexion, unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Retourne un livre.
     * 
     * @param pretDTO Le prêt à terminer
     * @throws InvalidPrimaryKeyException 
     * @throws InvalidHibernateSessionException 
     * @throws ServiceException 
     * @throws DTOException 
     * @throws InvalidDTOClassException 
     * @throws InvalidDTOException 
     * @throws InvalidSortByPropertyException 
     * @throws InvalidCriterionException 
     */
    public void retourner(Connexion connexion, PretDTO pretDTO, String sortByPropertyName) throws 
    ServiceException, 
    InvalidHibernateSessionException, 
    InvalidPrimaryKeyException, 
    DTOException, 
    InvalidDTOException, 
    InvalidDTOClassException, 
    InvalidCriterionException, 
    InvalidSortByPropertyException {
        try {
            PretDTO unPretDTO = get(connexion, pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new ServiceException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(connexion, unPretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion, unPretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + unPretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByMembre(connexion, unMembreDTO.getIdMembre(), sortByPropertyName);
            if(prets.isEmpty()) {
                throw new ServiceException("Le livre "
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
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'a pas été prêté à "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ")");
            }
            unMembreDTO.setNbPret(/*unMembreDTO.getNbPret() - 1*/ "deux");
            getMembreDAO().update(connexion, unMembreDTO);
            unPretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));
            update(connexion, unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
//[endRegion] Fin méthodes de commande
}
