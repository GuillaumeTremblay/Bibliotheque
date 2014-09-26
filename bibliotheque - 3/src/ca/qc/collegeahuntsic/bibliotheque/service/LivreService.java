// Fichier LivreService.java
// Auteur : Chou Huynh
// Date de création : 2014-09-23

package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
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

    private MembreDAO membreDAO;

    private ReservationDAO reservationDAO;

    /**
     * Crée un service à partir des DAOs de livre, member et réservation
     *
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public LivreService(LivreDAO livreDAO,
        MembreDAO membreDAO,
        ReservationDAO reservationDAO) {
        super();
        setLivreDAO(livreDAO);
        setMembreDAO(membreDAO);
        setReservationDAO(reservationDAO);
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
     * Ajoute un nouveau livre.
     *
     * @param livreDTO Le livre à ajouter
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void add(LivreDTO livreDTO) throws ServiceException {
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
    public LivreDTO read(int idLivre) throws ServiceException {
        try {
            return getLivreDAO().read(idLivre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Met à jour un livre.
     *
     * @param livreDTO Le livre à mettre à jour
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void update(LivreDTO livreDTO) throws ServiceException {
        try {
            getLivreDAO().update(livreDTO);
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
    public void delete(LivreDTO livreDTO) throws ServiceException {
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
    public List<LivreDTO> findByTitre(String titre) throws ServiceException {
        try {
            return getLivreDAO().findByTitre(titre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les livres à partir d'un membre.
     *
     * @param membreDTO Le membre à utiliser
     * @return Le livre correspondant ; null sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public LivreDTO findByMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            return getLivreDAO().findByMembre(membreDTO);
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
        if(read(livreDTO.getIdLivre()) != null) {
            throw new ServiceException("Le livre "
                + livreDTO.getIdLivre()
                + " existe déjà");
        }
        add(livreDTO);
    }

    /**
     * Emprunte un livre.
     *
     * @param membreDTO Le membre qui emprunte
     * @param livreDTO Le livre à emprunter
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void emprunter(MembreDTO membreDTO,
        // On voit le manque de la table prêt avec le décalage illogique (bancal) entre MembreService.emprunte et cette méthode
        LivreDTO livreDTO) throws ServiceException {
        try {
            livreDTO.setIdMembre(membreDTO.getIdMembre());
            getLivreDAO().emprunter(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Retourne un livre.
     *
     * @param membreDTO Le membre qui retourne le livre
     * @param livreDTO Le livre à retourner
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void retourner(MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        // On voit le manque de la table prêt avec le décalage illogique (bancal) entre MembreService.emprunte et cette méthode
        try {
            livreDTO.setIdMembre(membreDTO.getIdMembre());
            getLivreDAO().retourner(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
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
            LivreDTO unLivreDTO = read(livreDTO.getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }
            MembreDTO membreDTO = getMembreDAO().read(unLivreDTO.getIdMembre());
            if(getLivreDAO().findByMembre(membreDTO) != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a été prêté à "
                    + membreDTO.getNom()
                    + " (ID de membre : "
                    + membreDTO.getIdMembre()
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