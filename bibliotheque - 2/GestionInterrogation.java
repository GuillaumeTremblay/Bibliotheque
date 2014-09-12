import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gestion des transactions d'interrogation dans une bibliothèque.
 * 
 * <pre>
 * 
 *   Ce programme permet de faire diverses interrogations
 *   sur l'état de la bibliothèque.
 *  
 *   Pré-condition
 *     la base de données de la bibliothèque doit exister
 *  
 *   Post-condition
 *     le programme effectue les maj associées à chaque
 *     transaction
 *   
 *  
 * </pre>
 */

public class GestionInterrogation {

    private PreparedStatement stmtLivresTitreMot;

    private PreparedStatement stmtListeTousLivres;

    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionInterrogation(Connexion cx) throws SQLException {

        this.cx = cx;
        this.stmtLivresTitreMot = cx.getConnection().prepareStatement("select t1.idLivre, t1.titre, t1.auteur, t1.idmembre, t1.datePret + 14 "
            + "from livre t1 "
            + "where lower(titre) like ?");

        this.stmtListeTousLivres = cx.getConnection().prepareStatement("select t1.idLivre, t1.titre, t1.auteur, t1.idmembre, t1.datePret "
            + "from livre t1");
    }

    /**
     * Affiche les livres contenu un mot dans le titre
     */
    public void listerLivresTitre(String mot) throws SQLException {

        this.stmtLivresTitreMot.setString(1,
            "%"
                + mot
                + "%");

        int idMembre;
        System.out.println("idLivre titre auteur idMembre dateRetour");

        try(
            ResultSet rset = this.stmtLivresTitreMot.executeQuery()) {

            while(rset.next()) {
                System.out.print(rset.getInt(1)
                    + " "
                    + rset.getString(2)
                    + " "
                    + rset.getString(3));
                idMembre = rset.getInt(4);
                if(!rset.wasNull()) {
                    System.out.print(" "
                        + idMembre
                        + " "
                        + rset.getDate(5));
                }
                System.out.println();
            }
        }

        this.cx.commit();
    }

    /**
     * Affiche tous les livres de la BD
     */
    public void listerLivres() throws SQLException {

        System.out.println("idLivre titre auteur idMembre datePret");
        int idMembre;

        try(
            ResultSet rset = this.stmtListeTousLivres.executeQuery()) {

            while(rset.next()) {
                System.out.print(rset.getInt("idLivre")
                    + " "
                    + rset.getString("titre")
                    + " "
                    + rset.getString("auteur"));
                idMembre = rset.getInt("idMembre");
                if(!rset.wasNull()) {
                    System.out.print(" "
                        + idMembre
                        + " "
                        + rset.getDate("datePret"));
                }
                System.out.println();
            }
        }

        this.cx.commit();
    }
}
