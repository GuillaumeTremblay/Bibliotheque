// Fichier LivreService.java
// Auteur : Chou Huynh
// Date de création : 2014-09-23

package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.PretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

/**
 * Service de la table <code>livre</code>.
 *
 * @author Chou Huynh
 */
public class LivreService extends Service {
    private static final long serialVersionUID = 1L;

    private LivreDAO livreDAO;

    private ReservationDAO reservationDAO;

    private PretDAO pretDAO;

    /**
     * Crée un service à partir des DAOs de livre, member et réservation
     *
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public LivreService(LivreDAO livreDAO,
        MembreDAO membreDAO,
        ReservationDAO reservationDAO,
        PretDAO pretDAO) {
        super();
        setLivreDAO(livreDAO);
        setReservationDAO(reservationDAO);
        setPretDAO(pretDAO);
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

    /**
     * Getter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @return La variable d'instance <code>this.pretDAO</code>
     */
    public PretDAO getPretDAO() {
        return this.pretDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @param pretDAO La valeur à utiliser pour la variable d'instance <code>this.pretDAO</code>
     */
    public void setPretDAO(PretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    /**
     * Ajoute un nouveau livre.
     *
     * @param livreDTO Le livre à ajouter
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    private void add(LivreDTO livreDTO) throws ServiceException {
        try {
            getLivreDAO().add(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Lit un livre.
     *
     * @param idLivre L'ID du livre à lire
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    private LivreDTO read(LivreDTO livreDTO) throws ServiceException {
        try {
            return getLivreDAO().read(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Supprime un livre.
     *
     * @param livreDTO Le livre à supprimer
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    private void delete(LivreDTO livreDTO) throws ServiceException {
        try {
            getLivreDAO().delete(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve tous les livres.
     *
     * @return La liste des livres ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public List<LivreDTO> getAll() throws ServiceException {
        try {
            return getLivreDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les livres à partir d'un titre.
     *
     * @param titre Le titre à utiliser
     * @return La liste des livres correspondants ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    @SuppressWarnings("unused")
    private List<LivreDTO> findByTitre(LivreDTO livreDTO) throws ServiceException {
        try {
            return getLivreDAO().findByTitre(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Acquiert un livre.
     *
     * @param livreDTO Le livre à ajouter
     * @throws ServiceException Si le livre existe déjà ou s'il y a une erreur avec la base de données
     */
    public void acquerir(LivreDTO livreDTO) throws ServiceException {
        add(livreDTO);
    }

    /**
     * Vendre un livre.
     *
     * @param livreDTO Le livre à vendre
     * @throws ServiceException Si le livre n'existe pas, si le livre a été prêté, si le livre a été réservé ou s'il y a une erreur avec la base
     *         de données
     */
    public void vendre(LivreDTO livreDTO) throws ServiceException {
        try {
            LivreDTO unLivreDTO = read(livreDTO);
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }
            PretDTO pretDTO = getPretDAO().findByLivre(unLivreDTO);
            if(pretDTO != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a été prêté à "
                    + pretDTO.getMembreDTO().getNom()
                    + " (ID de membre : "
                    + pretDTO.getMembreDTO().getIdMembre()
                    + ")");
            }
            if(!getReservationDAO().findByLivre(unLivreDTO).isEmpty()) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des réservations");
            }
            delete(unLivreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}