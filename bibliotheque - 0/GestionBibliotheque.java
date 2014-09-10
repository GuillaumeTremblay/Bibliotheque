import java.sql.*;

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
public class GestionBibliotheque
{
public Connexion cx;
public Livre livre;
public Membre membre;
public Reservation reservation;
public GestionLivre gestionLivre;
public GestionMembre gestionMembre;
public GestionPret gestionPret;
public GestionReservation gestionReservation;
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
  *</pre>
  */
public GestionBibliotheque(String serveur, String bd, String user, String password)
  throws BiblioException, SQLException
{
// allocation des objets pour le traitement des transactions
cx = new Connexion(serveur, bd, user, password);
livre = new Livre(cx);
membre = new Membre(cx);
reservation = new Reservation(cx);
gestionLivre = new GestionLivre(livre,reservation);
gestionMembre = new GestionMembre(membre,reservation);
gestionPret = new GestionPret(livre, membre, reservation);
gestionReservation = new GestionReservation(livre, membre, reservation);
gestionInterrogation = new GestionInterrogation(cx);
}

public void fermer()
  throws SQLException
{
// fermeture de la connexion
cx.fermer();
}
}
