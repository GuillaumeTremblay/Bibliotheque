//package ca.qc.collegeahuntsic.bibliotheque.service;
//import java.sql.SQLException;
//import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
//import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
//import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
//import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
//import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
//
//// TODO: Auto-generated Javadoc
///**
// * Système de gestion d'une bibliothèque
// *
// *<pre>
// * Ce programme permet de gérer les transaction de base d'une
// * bibliothèque.  Il gère des livres, des membres et des
// * réservations. Les données sont conservées dans une base de
// * données relationnelles accédée avec JDBC.
// *
// * Pré-condition
// *   la base de données de la bibliothèque doit exister
// *
// * Post-condition
// *   le programme effectue les maj associées à chaque
// *   transaction
// * </pre>
// */
//public class GestionBibliothequeSrv {
//    
//    /** The cx. */
//    public Connexion cx;
//
//    /** The livre. */
//    public LivreDAO livre;
//
//    /** The membre. */
//    public MembreDAO membre;
//
//    /** The reservation. */
//    public ReservationDAO reservation;
//
//    /** The gestion livre. */
//    public LivreService gestionLivre;
//
//    /** The gestion membre. */
//    public MembreService gestionMembre;
//
//    /** The gestion pret. */
//    public PretService gestionPret;
//
//    /** The gestion reservation. */
//    public ReservationService gestionReservation;
//
//    /** The gestion interrogation. */
//    public GestionInterrogationSrv gestionInterrogation;
//
//    /**
//     * Ouvre une connexion avec la BD relationnelle et
//     * alloue les gestionnaires de transactions et de tables.
//     * <pre>
//     *
//     * @param serveur SQL
//     * @param bd nom de la bade de données
//     * @param user user id pour établir une connexion avec le serveur SQL
//     * @param password mot de passe pour le user id
//     * </pre>
//     * @throws BibliothequeException the biblio exception
//     * @throws SQLException the SQL exception
//     */
//    public GestionBibliothequeSrv(String serveur,
//        String bd,
//        String user,
//        String password) throws BibliothequeException,
//        SQLException {
//        // allocation des objets pour le traitement des transactions
//        this.cx = new Connexion(serveur,
//            bd,
//            user,
//            password);
//        this.livre = new LivreDAO(this.cx);
//        this.membre = new MembreDAO(this.cx);
//        this.reservation = new ReservationDAO(this.cx);
//        this.gestionLivre = new LivreService(this.livre,
//            this.reservation);
//        this.gestionMembre = new MembreService(this.membre,
//            this.reservation);
//        this.gestionPret = new PretService(this.livre,
//            this.membre,
//            this.reservation);
//        this.gestionReservation = new ReservationService(this.livre,
//            this.membre,
//            this.reservation);
//        this.gestionInterrogation = new GestionInterrogationSrv(this.cx);
//    }
//
//    /**
//     * Fermer.
//     *
//     * @throws SQLException the SQL exception
//     */
//    public void fermer() throws SQLException {
//        // fermeture de la connexion
//        this.cx.fermer();
//    }
//}
