package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.DB.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dao.Livre;
import ca.qc.collegeahuntsic.bibliotheque.dao.Membre;
import ca.qc.collegeahuntsic.bibliotheque.dao.Reservation;
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
public class GestionBibliotheque {
    
    /** The cx. */
    public Connexion cx;

    /** The livre. */
    public Livre livre;

    /** The membre. */
    public Membre membre;

    /** The reservation. */
    public Reservation reservation;

    /** The gestion livre. */
    public GestionLivre gestionLivre;

    /** The gestion membre. */
    public GestionMembre gestionMembre;

    /** The gestion pret. */
    public GestionPret gestionPret;

    /** The gestion reservation. */
    public GestionReservation gestionReservation;

    /** The gestion interrogation. */
    public GestionInterrogation gestionInterrogation;

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
    public GestionBibliotheque(String serveur,
        String bd,
        String user,
        String password) throws BiblioException,
        SQLException {
        // allocation des objets pour le traitement des transactions
        this.cx = new Connexion(serveur,
            bd,
            user,
            password);
        this.livre = new Livre(this.cx);
        this.membre = new Membre(this.cx);
        this.reservation = new Reservation(this.cx);
        this.gestionLivre = new GestionLivre(this.livre,
            this.reservation);
        this.gestionMembre = new GestionMembre(this.membre,
            this.reservation);
        this.gestionPret = new GestionPret(this.livre,
            this.membre,
            this.reservation);
        this.gestionReservation = new GestionReservation(this.livre,
            this.membre,
            this.reservation);
        this.gestionInterrogation = new GestionInterrogation(this.cx);
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