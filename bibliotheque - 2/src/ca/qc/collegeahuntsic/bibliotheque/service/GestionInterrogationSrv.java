package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;

// TODO: Auto-generated Javadoc
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

public class GestionInterrogationSrv {

    /** The stmt livres titre mot. */
    private PreparedStatement stmtLivresTitreMot;

    /** The stmt liste tous livres. */
    private PreparedStatement stmtListeTousLivres;

    /** The cx. */
    private Connexion cx;

    /**
     * Creation d'une instance.
     *
     * @param cx the cx
     * @throws SQLException the SQL exception
     */
    public GestionInterrogationSrv(Connexion cx) throws SQLException {

        this.cx = cx;
        this.stmtLivresTitreMot = cx.getConnection().prepareStatement("select t1.idLivre, t1.titre, t1.auteur, t1.idmembre, t1.datePret + 14 "
            + "from livre t1 "
            + "where lower(titre) like ?");

        this.stmtListeTousLivres = cx.getConnection().prepareStatement("select t1.idLivre, t1.titre, t1.auteur, t1.idmembre, t1.datePret "
            + "from livre t1");
    }

    /**
     * Affiche les livres contenu un mot dans le titre.
     *
     * @param mot the mot
     * @throws SQLException the SQL exception
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
     * Affiche tous les livres de la BD.
     *
     * @throws SQLException the SQL exception
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
