import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Permet d'effectuer les accès à la table livre.
 */

public class Livre {

    private PreparedStatement stmtExiste;

    private PreparedStatement stmtInsert;

    private PreparedStatement stmtUpdate;

    private PreparedStatement stmtDelete;

    private Connexion cx;

    /**
      * Creation d'une instance. Des énoncés SQL pour chaque requête sont précompilés.
      */
    public Livre(Connexion cx) throws SQLException {

        this.cx = cx;
        this.stmtExiste = cx.getConnection()
            .prepareStatement("select idlivre, titre, auteur, dateAcquisition, idMembre, datePret from livre where idlivre = ?");
        this.stmtInsert = cx.getConnection().prepareStatement("insert into livre (idLivre, titre, auteur, dateAcquisition, idMembre, datePret) "
            + "values (?,?,?,?,null,null)");
        this.stmtUpdate = cx.getConnection().prepareStatement("update livre set idMembre = ?, datePret = ? "
            + "where idLivre = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from livre where idlivre = ?");
    }

    /**
      * Retourner la connexion associée.
      */
    public Connexion getConnexion() {

        return this.cx;
    }

    /**
      * Verifie si un livre existe.
      */
    public boolean existe(int idLivre) throws SQLException {

        this.stmtExiste.setInt(1,
            idLivre);

        try(
            ResultSet rset = this.stmtExiste.executeQuery()) {

            boolean livreExiste = rset.next();
            return livreExiste;
        }

    }

    /**
      * Lecture d'un livre.
      */
    public TupleLivre getLivre(int idLivre) throws SQLException {

        this.stmtExiste.setInt(1,
            idLivre);
        try(
            ResultSet rset = this.stmtExiste.executeQuery()) {

            if(rset.next()) {
                TupleLivre tupleLivre = new TupleLivre();
                tupleLivre.idLivre = idLivre;
                tupleLivre.titre = rset.getString(2);
                tupleLivre.auteur = rset.getString(3);
                tupleLivre.dateAcquisition = rset.getDate(4);
                tupleLivre.idMembre = rset.getInt(5);
                tupleLivre.datePret = rset.getDate(6);
                return tupleLivre;
            }
        }

        return null;
    }

    /**
      * Ajout d'un nouveau livre dans la base de donnees.
      */
    public void acquerir(int idLivre,
        String titre,
        String auteur,
        String dateAcquisition) throws SQLException {
        /* Ajout du livre. */
        this.stmtInsert.setInt(1,
            idLivre);
        this.stmtInsert.setString(2,
            titre);
        this.stmtInsert.setString(3,
            auteur);
        this.stmtInsert.setDate(4,
            Date.valueOf(dateAcquisition));
        this.stmtInsert.executeUpdate();
    }

    /**
      * Enregistrement de l'emprunteur d'un livre.
      */
    public int preter(int idLivre,
        int idMembre,
        String datePret) throws SQLException {
        /* Enregistrement du pret. */
        this.stmtUpdate.setInt(1,
            idMembre);
        this.stmtUpdate.setDate(2,
            Date.valueOf(datePret));
        this.stmtUpdate.setInt(3,
            idLivre);
        return this.stmtUpdate.executeUpdate();
    }

    /**
      * Rendre le livre disponible (non-prêté)
      */
    public int retourner(int idLivre) throws SQLException {
        /* Enregistrement du pret. */
        this.stmtUpdate.setNull(1,
            Types.INTEGER);
        this.stmtUpdate.setNull(2,
            Types.DATE);
        this.stmtUpdate.setInt(3,
            idLivre);
        return this.stmtUpdate.executeUpdate();
    }

    /**
      * Suppression d'un livre.
      */
    public int vendre(int idLivre) throws SQLException {
        /* Suppression du livre. */
        this.stmtDelete.setInt(1,
            idLivre);
        return this.stmtDelete.executeUpdate();
    }
}
