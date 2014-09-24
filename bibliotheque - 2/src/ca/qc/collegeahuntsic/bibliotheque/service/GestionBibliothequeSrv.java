
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;

// TODO: Auto-generated Javadoc
/**
 * Système de gestion d'une bibliothèque
 *
 *<pre>
 * Ce programme permet de gérer les transaction de base d'une
 * bibliothèque.  Il gère des livres, des membres et des
 * réservations. Les données sont conservées dans une base de
 * données relationnelles accédée avec JDBC.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */
public class GestionBibliothequeSrv {

    /** The cx. */
    private Connexion cx;

    /** The livre. */
    private LivreDAO livreDAO;

    /** The membre. */
    private MembreDAO membreDAO;

    /** The reservation. */
    private ReservationDAO reservationDAO;

    /** The gestion livre. */
    private LivreService livreService;

    /** The gestion membre. */
    private MembreService membreService;

    /** The gestion pret. */
    private PretService PretService;

    /** The gestion reservation. */
    private ReservationService reservationService;

    /** The gestion interrogation. */
    private GestionInterrogationSrv gestionInterrogation;

    /**
     * Getter de la variable d'instance <code>this.cx</code>.
     *
     * @return La variable d'instance <code>this.cx</code>
     */
    public Connexion getCx() {
        return this.cx;
    }

    /**
     * Setter de la variable d'instance <code>this.cx</code>.
     *
     * @param cx La valeur à utiliser pour la variable d'instance <code>this.cx</code>
     */
    public void setCx(Connexion cx) {
        this.cx = cx;
    }

    /**
     * Getter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @return La variable d'instance <code>this.livreDAO</code>
     */
    public LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @param livreDAO La valeur à utiliser pour la variable d'instance <code>this.livreDAO</code>
     */
    public void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @return La variable d'instance <code>this.membreDAO</code>
     */
    public MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @param membreDAO La valeur à utiliser pour la variable d'instance <code>this.membreDAO</code>
     */
    public void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    public ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO La valeur à utiliser pour la variable d'instance <code>this.reservationDAO</code>
     */
    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.livreService</code>.
     *
     * @return La variable d'instance <code>this.livreService</code>
     */
    public LivreService getLivreService() {
        return this.livreService;
    }

    /**
     * Setter de la variable d'instance <code>this.livreService</code>.
     *
     * @param livreService La valeur à utiliser pour la variable d'instance <code>this.livreService</code>
     */
    public void setLivreService(LivreService livreService) {
        this.livreService = livreService;
    }

    /**
     * Getter de la variable d'instance <code>this.membreService</code>.
     *
     * @return La variable d'instance <code>this.membreService</code>
     */
    public MembreService getMembreService() {
        return this.membreService;
    }

    /**
     * Setter de la variable d'instance <code>this.membreService</code>.
     *
     * @param membreService La valeur à utiliser pour la variable d'instance <code>this.membreService</code>
     */
    public void setMembreService(MembreService membreService) {
        this.membreService = membreService;
    }

    /**
     * Getter de la variable d'instance <code>this.pretService</code>.
     *
     * @return La variable d'instance <code>this.pretService</code>
     */
    public PretService getPretService() {
        return this.PretService;
    }

    /**
     * Setter de la variable d'instance <code>this.pretService</code>.
     *
     * @param pretService La valeur à utiliser pour la variable d'instance <code>this.pretService</code>
     */
    public void setPretService(PretService pretService) {
        this.PretService = pretService;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationService</code>.
     *
     * @return La variable d'instance <code>this.reservationService</code>
     */
    public ReservationService getReservationService() {
        return this.reservationService;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationService</code>.
     *
     * @param reservationService La valeur à utiliser pour la variable d'instance <code>this.reservationService</code>
     */
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Getter de la variable d'instance <code>this.gestionInterrogation</code>.
     *
     * @return La variable d'instance <code>this.gestionInterrogation</code>
     */
    public GestionInterrogationSrv getGestionInterrogation() {
        return this.gestionInterrogation;
    }

    /**
     * Setter de la variable d'instance <code>this.gestionInterrogation</code>.
     *
     * @param gestionInterrogation La valeur à utiliser pour la variable d'instance <code>this.gestionInterrogation</code>
     */
    public void setGestionInterrogation(GestionInterrogationSrv gestionInterrogation) {
        this.gestionInterrogation = gestionInterrogation;
    }

    /**
     * Ouvre une connexion avec la BD relationnelle et
     * alloue les gestionnaires de transactions et de tables.
     * <pre>
     *
     * @param serveur SQL
     * @param bd nom de la bade de données
     * @param user user id pour établir une connexion avec le serveur SQL
     * @param password mot de passe pour le user id
     * </pre>
     * @throws BibliothequeException the biblio exception
     * @throws SQLException the SQL exception
     */
    public GestionBibliothequeSrv(String serveur,
        String bd,
        String user,
        String password) throws BibliothequeException,
        SQLException {
        // allocation des objets pour le traitement des transactions
        this.cx = new Connexion(serveur,
            bd,
            user,
            password);
        this.livreDAO = new LivreDAO(this.cx);
        this.membreDAO = new MembreDAO(this.cx);
        this.reservationDAO = new ReservationDAO(this.cx);
        this.livreService = new LivreService(this.livreDAO,
            this.membreDAO,
            this.reservationDAO);
        this.membreService = new MembreService(this.membreDAO);
        this.PretService = new PretService(this.livreDAO,
            this.membreDAO,
            this.reservationDAO);
        this.reservationService = new ReservationService(this.livreDAO,
            this.membreDAO,
            this.reservationDAO);
        this.gestionInterrogation = new GestionInterrogationSrv(this.cx);
    }

    /**
     * Fermer.
     *
     * @throws SQLException the SQL exception
     */
    public void fermer() throws SQLException {
        // fermeture de la connexion
        this.cx.fermer();
    }
}
