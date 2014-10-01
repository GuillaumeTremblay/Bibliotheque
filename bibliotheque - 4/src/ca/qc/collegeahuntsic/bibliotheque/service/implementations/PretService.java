// Fichier PretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.PretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.Service;

/**
 * Service de la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public class PretService extends Service {
    private static final long serialVersionUID = 1L;

    private PretDAO pretDAO;

    private MembreDAO membreDAO;

    private LivreDAO livreDAO;

    private ReservationDAO reservationDAO;

    /**
     * Crée le service de la table <code>pret</code>.
     * 
     * @param pretDAO Le DAO de la table <code>pret</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public PretService(PretDAO pretDAO,
        MembreDAO membreDAO,
        LivreDAO livreDAO,
        ReservationDAO reservationDAO) {
        super();
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
    private PretDAO getPretDAO() {
        return this.pretDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @param pretDAO La valeur à utiliser pour la variable d'instance <code>this.pretDAO</code>
     */
    private void setPretDAO(PretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

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
     * Getter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @return La variable d'instance <code>this.livreDAO</code>
     */
    private LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @param livreDAO La valeur à utiliser pour la variable d'instance <code>this.livreDAO</code>
     */
    private void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
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
     * Ajoute un nouveau prêt.
     * 
     * @param pretDTO Le prêt à ajouter
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void add(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().add(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Lit un prêt.
     * 
     * @param idPret L'ID du prêt à lire
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public PretDTO read(int idPret) throws ServiceException {
        try {
            return getPretDAO().read(idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Met à jour un prêt.
     * 
     * @param pretDTO Le prêt à mettre à jour
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void update(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().update(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Supprime un prêt.
     * 
     * @param pretDTO Le prêt à supprimer
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void delete(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().delete(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve tous les prêts.
     * 
     * @return La liste des prêts ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> getAll() throws ServiceException {
        try {
            return getPretDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les prêts non terminés d'un membre.
     * 
     * @param idMembre L'ID du membre à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByMembre(int idMembre) throws ServiceException {
        try {
            return getPretDAO().findByMembre(idMembre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les livres en cours d'emprunt.
     * 
     * @param idLivre L'ID du livre à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByLivre(int idLivre) throws ServiceException {
        try {
            return getPretDAO().findByLivre(idLivre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les prêts à partir d'une date de prêt.
     * 
     * @param datePret La date de prêt à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByDatePret(Timestamp datePret) throws ServiceException {
        try {
            return getPretDAO().findByDatePret(datePret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les prêts à partir d'une date de retour.
     * 
     * @param dateRetour La date de retour à trouver
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<PretDTO> findByDateRetour(Timestamp dateRetour) throws ServiceException {
        try {
            return getPretDAO().findByDateRetour(dateRetour);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Commence un prêt.
     * 
     * @param pretDTO Le prêt à commencer
     * @throws ServiceException Si le membre n'existe pas, si le livre n'existe pas, si le livre a été prêté, si le livre a été réservé, si le
     *         membre a atteint sa limite de prêt ou s'il y a une erreur avec la base de données
     */
    public void commencer(PretDTO pretDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = getMembreDAO().read(pretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + pretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(pretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + pretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByLivre(unLivreDTO.getIdLivre());
            if(!prets.isEmpty()) {
                PretDTO unPretDTO = prets.get(0);
                MembreDTO emprunteur = getMembreDAO().read(unPretDTO.getMembreDTO().getIdMembre());
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
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(unLivreDTO.getIdLivre());
            if(!reservations.isEmpty()) {
                ReservationDTO uneReservationDTO = reservations.get(0);
                MembreDTO booker = getMembreDAO().read(uneReservationDTO.getMembreDTO().getIdMembre());
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
            getMembreDAO().update(unMembreDTO);
            pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            pretDTO.setDateRetour(null);
            add(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Renouvelle le prêt d'un livre.
     * 
     * @param pretDTO Le prêt à renouveler
     * @throws ServiceException Si le prêt n'existe pas, si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore été
     *         prêté, si le livre a été prêté à quelqu'un d'autre, si le livre a été réservé ou s'il y a une erreur avec la base de données
     */
    public void renouveler(PretDTO pretDTO) throws ServiceException {
        try {
            PretDTO unPretDTO = read(pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new ServiceException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = getMembreDAO().read(unPretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(unPretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + unPretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByMembre(unMembreDTO.getIdMembre());
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
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(unLivreDTO.getIdLivre());
            if(!reservations.isEmpty()) {
                ReservationDTO uneReservationDTO = reservations.get(0);
                MembreDTO booker = getMembreDAO().read(uneReservationDTO.getMembreDTO().getIdMembre());
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
            update(unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Retourne un livre.
     * 
     * @param pretDTO Le prêt à terminer
     * @throws ServiceException Si le prêt n'existe pas, si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore été
     *         prêté, si le livre a été prêté à quelqu'un d'autre ou s'il y a une erreur avec la base de données
     */
    public void retourner(PretDTO pretDTO) throws ServiceException {
        try {
            PretDTO unPretDTO = read(pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new ServiceException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = getMembreDAO().read(unPretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(unPretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + unPretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByMembre(unMembreDTO.getIdMembre());
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
            unMembreDTO.setNbPret(unMembreDTO.getNbPret() - 1);
            getMembreDAO().update(unMembreDTO);
            unPretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));
            update(unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
