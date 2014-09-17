package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.DB.ConnexionDb;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDao;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDao;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDao;
import ca.qc.collegeahuntsic.bibliotheque.exception.BiblioException;

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
    public ConnexionDb cx;

    /** The livre. */
    public LivreDao livre;

    /** The membre. */
    public MembreDao membre;

    /** The reservation. */
    public ReservationDao reservation;

    /** The gestion livre. */
    public GestionLivreSrv gestionLivre;

    /** The gestion membre. */
    public GestionMembreSrv gestionMembre;

    /** The gestion pret. */
    public GestionPretSrv gestionPret;

    /** The gestion reservation. */
    public GestionReservationSrv gestionReservation;

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
     * @throws BiblioException the biblio exception
     * @throws SQLException the SQL exception
     */
    public GestionBibliothequeSrv(String serveur,
        String bd,
        String user,
        String password) throws BiblioException,
        SQLException {
        // allocation des objets pour le traitement des transactions
        this.cx = new ConnexionDb(serveur,
            bd,
            user,
            password);
        this.livre = new LivreDao(this.cx);
        this.membre = new MembreDao(this.cx);
        this.reservation = new ReservationDao(this.cx);
        this.gestionLivre = new GestionLivreSrv(this.livre,
            this.reservation);
        this.gestionMembre = new GestionMembreSrv(this.membre,
            this.reservation);
        this.gestionPret = new GestionPretSrv(this.livre,
            this.membre,
            this.reservation);
        this.gestionReservation = new GestionReservationSrv(this.livre,
            this.membre,
            this.reservation);
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
