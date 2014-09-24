
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
    public Connexion cx;

    /** The livre. */
    public LivreDAO livreDAO;

    /** The membre. */
    public MembreDAO membreDAO;

    /** The reservation. */
    public ReservationDAO reservationDAO;

    /** The gestion livre. */
    public LivreService livreService;

    /** The gestion membre. */
    public MembreService membreService;

    /** The gestion pret. */
    public PretService PretService;

    /** The gestion reservation. */
    public ReservationService reservationService;

    /** The gestion interrogation. */
    public GestionInterrogationSrv gestionInterrogation;

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
