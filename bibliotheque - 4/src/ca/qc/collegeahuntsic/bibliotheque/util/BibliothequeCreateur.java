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
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.LivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.MembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.ILivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IMembreService;

/**
 * Utilitaire de création des outils de la bibliothèque.
 * 
 * @author Gilles Benichou
 */
public class BibliothequeCreateur {
    private Connexion connexion;

    /**
     * Crée les services nécessaires à l'application bibliothèque.<br /><br />
     *
     * @param typeServeur Type de serveur SQL de la BD
     * @param schema Nom du schéma de la base de données
     * @param nomUtilisateur Nom d'utilisateur sur le serveur SQL
     * @param motPasse Mot de passe sur le serveur SQL
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */

    @SuppressWarnings({"unused",
        "resource"})
    public BibliothequeCreateur(String typeServeur,
        String schema,
        String nomUtilisateur,
        String motPasse) throws BibliothequeException {
        try {
            setConnexion(new Connexion(typeServeur,
                schema,
                nomUtilisateur,
                motPasse));
            LivreDAO livreDAO = new LivreDAO(LivreDTO.class);
            ILivreDAO iLivreDAO = livreDAO;
            MembreDAO membreDAO = new MembreDAO(MembreDTO.class);
            IMembreDAO iMembreDAO = membreDAO;
            ReservationDAO reservationDAO = new ReservationDAO(ReservationDTO.class);
            IReservationDAO iReservationDAO = reservationDAO;
            PretDAO pretDAO = new PretDAO(PretDTO.class);
            IPretDAO iPretDAO = pretDAO;
            LivreService livreService = new LivreService(iLivreDAO,
                iMembreDAO,
                pretDAO,
                iReservationDAO);
            ILivreService iLivreService = livreService;
            MembreService membreService = new MembreService(iMembreDAO,
                iReservationDAO);
            IMembreService iMembreService = membreService;
        } catch(ConnexionException connexionException) {
            throw new BibliothequeException(connexionException);
        } catch(InvalidDTOClassException e) {
            throw new BibliothequeException(e);
        } catch(InvalidDAOException e) {
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