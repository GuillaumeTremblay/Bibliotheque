// Fichier BibliothequeCreateur.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.util;

import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.PretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.db.ConnexionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.LivreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.MembreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.PretFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.ReservationFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IMembreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IReservationFacade;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.LivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.MembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.PretService;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.ReservationService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.ILivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IMembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IReservationService;

/**
 * Utilitaire de création des outils de la bibliothèque.
 *
 * @author Gilles Benichou
 */
public class BibliothequeCreateur {
    private Connexion connexion;

    private ILivreFacade iLivreFacade;

    private IMembreFacade iMembreFacade;

    private IReservationFacade iReservationFacade;

    private IPretFacade iPretFacade;

    /**
     * Crée les services nécessaires à l'application bibliothèque.<br /><br />
     *
     * @param typeServeur Type de serveur SQL de la BD
     * @param schema Nom du schéma de la base de données
     * @param nomUtilisateur Nom d'utilisateur sur le serveur SQL
     * @param motPasse Mot de passe sur le serveur SQL
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */

    @SuppressWarnings("resource")
    public BibliothequeCreateur(String typeServeur,
        String schema,
        String nomUtilisateur,
        String motPasse) throws BibliothequeException {
        try {
            setConnexion(new Connexion(typeServeur,
                schema,
                nomUtilisateur,
                motPasse));

            ILivreDAO livreDAO = new LivreDAO(LivreDTO.class);
            IMembreDAO membreDAO = new MembreDAO(MembreDTO.class);
            IReservationDAO reservationDAO = new ReservationDAO(ReservationDTO.class);
            IPretDAO pretDAO = new PretDAO(PretDTO.class);

            LivreService livreService = new LivreService(livreDAO,
                membreDAO,
                pretDAO,
                reservationDAO);
            MembreService membreService = new MembreService(membreDAO,
                reservationDAO);
            ReservationService reservationService = new ReservationService(reservationDAO,
                membreDAO,
                livreDAO,
                pretDAO);
            PretService pretService = new PretService(pretDAO,
                membreDAO,
                livreDAO,
                reservationDAO);

            ILivreService iLivreService = livreService;
            IMembreService iMembreService = membreService;
            IReservationService iReservationService = reservationService;
            IPretService iPretService = pretService;

            LivreFacade livreFacade = new LivreFacade(iLivreService);
            MembreFacade membreFacade = new MembreFacade(iMembreService);
            ReservationFacade reservationFacade = new ReservationFacade(iReservationService);
            PretFacade pretFacade = new PretFacade(iPretService);

            setiLivreFacade(livreFacade);
            setiMembreFacade(membreFacade);
            setiReservationFacade(reservationFacade);
            setiPretFacade(pretFacade);

        } catch(ConnexionException connexionException) {
            throw new BibliothequeException(connexionException);
        } catch(InvalidDTOClassException e) {
            throw new BibliothequeException(e);
        } catch(InvalidDAOException e) {
            throw new BibliothequeException(e);
        } catch(InvalidServiceException e) {
            throw new BibliothequeException(e);
        }
    }

    /**
     * Effectue un commit sur la connexion.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void commit() throws ConnexionException {
        try {
            getConnexion().commit();
        } catch(Exception exception) {
            throw new ConnexionException(exception);
        }
    }

    // Region Getters and Setters

    /**
     * Getter de la variable d'instance <code>this.connexion</code>.
     *
     * @return La variable d'instance <code>this.connexion</code>
     */
    public Connexion getConnexion() {
        return this.connexion;
    }

    /**
     * Setter de la variable d'instance <code>this.connexion</code>.
     *
     * @param connexion La valeur à utiliser pour la variable d'instance <code>this.connexion</code>
     */
    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }

    /**
     * Getter de la variable d'instance <code>this.iLivreFacade</code>.
     *
     * @return La variable d'instance <code>this.iLivreFacade</code>
     */
    public ILivreFacade getiLivreFacade() {
        return this.iLivreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.iLivreFacade</code>.
     *
     * @param iLivreFacade La valeur à utiliser pour la variable d'instance <code>this.iLivreFacade</code>
     */
    public void setiLivreFacade(ILivreFacade iLivreFacade) {
        this.iLivreFacade = iLivreFacade;
    }

    /**
     * Getter de la variable d'instance <code>this.iMembreFacade</code>.
     *
     * @return La variable d'instance <code>this.iMembreFacade</code>
     */
    public IMembreFacade getiMembreFacade() {
        return this.iMembreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.iMembreFacade</code>.
     *
     * @param iMembreFacade La valeur à utiliser pour la variable d'instance <code>this.iMembreFacade</code>
     */
    public void setiMembreFacade(IMembreFacade iMembreFacade) {
        this.iMembreFacade = iMembreFacade;
    }

    /**
     * Getter de la variable d'instance <code>this.iReservationFacade</code>.
     *
     * @return La variable d'instance <code>this.iReservationFacade</code>
     */
    public IReservationFacade getiReservationFacade() {
        return this.iReservationFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.iReservationFacade</code>.
     *
     * @param iReservationFacade La valeur à utiliser pour la variable d'instance <code>this.iReservationFacade</code>
     */
    public void setiReservationFacade(IReservationFacade iReservationFacade) {
        this.iReservationFacade = iReservationFacade;
    }

    /**
     * Getter de la variable d'instance <code>this.iPretFacade</code>.
     *
     * @return La variable d'instance <code>this.iPretFacade</code>
     */
    public IPretFacade getiPretFacade() {
        return this.iPretFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.iPretFacade</code>.
     *
     * @param iPretFacade La valeur à utiliser pour la variable d'instance <code>this.iPretFacade</code>
     */
    public void setiPretFacade(IPretFacade iPretFacade) {
        this.iPretFacade = iPretFacade;
    }

    // EndRegion Getters and Setters
    /**
     * Effectue un rollback sur la connexion.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void rollback() throws ConnexionException {
        try {
            getConnexion().rollback();
        } catch(Exception exception) {
            throw new ConnexionException(exception);
        }
    }

    /**
     * Ferme la connexion.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void close() throws ConnexionException {
        try {
            getConnexion().close();
        } catch(Exception exception) {
            throw new ConnexionException(exception);
        }
    }
}