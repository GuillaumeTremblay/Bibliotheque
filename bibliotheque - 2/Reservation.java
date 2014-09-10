import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Permet d'effectuer les accès à la table reservation.
 *<pre>
 *
 * Cette classe gère tous les accès à la table reservation.
 *
 *</pre>
 */

public class Reservation {

    private PreparedStatement stmtExiste;

    private PreparedStatement stmtExisteLivre;

    private PreparedStatement stmtExisteMembre;

    private PreparedStatement stmtInsert;

    private PreparedStatement stmtDelete;

    private Connexion cx;

    /**
      * Creation d'une instance.
      */
    public Reservation(Connexion cx) throws SQLException {

        this.cx = cx;
        this.stmtExiste = cx.getConnection().prepareStatement("select idReservation, idLivre, idMembre, dateReservation "
            + "from reservation where idReservation = ?");
        this.stmtExisteLivre = cx.getConnection().prepareStatement("select idReservation, idLivre, idMembre, dateReservation "
            + "from reservation where idLivre = ? "
            + "order by dateReservation");
        this.stmtExisteMembre = cx.getConnection().prepareStatement("select idReservation, idLivre, idMembre, dateReservation "
            + "from reservation where idMembre = ? ");
        this.stmtInsert = cx.getConnection().prepareStatement("insert into reservation (idReservation, idlivre, idMembre, dateReservation) "
            + "values (?,?,?,to_date(?,'YYYY-MM-DD'))");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from reservation where idReservation = ?");
    }

    /**
      * Retourner la connexion associée.
      */
    public Connexion getConnexion() {

        return this.cx;
    }

    /**
      * Verifie si une reservation existe.
      */
    public boolean existe(int idReservation) throws SQLException {

        this.stmtExiste.setInt(1,
            idReservation);
        try(
            ResultSet rset = this.stmtExiste.executeQuery()) {

            boolean livreExiste = rset.next();
            return livreExiste;
        }
    }

    /**
      * Lecture d'une reservation.
      */
    public TupleReservation getReservation(int idReservation) throws SQLException {

        this.stmtExiste.setInt(1,
            idReservation);
        try(
            ResultSet rset = this.stmtExiste.executeQuery()) {

            if(rset.next()) {
                TupleReservation tupleReservation = new TupleReservation();
                tupleReservation.idReservation = rset.getInt(1);
                tupleReservation.idLivre = rset.getInt(2);
                tupleReservation.idMembre = rset.getInt(3);
                tupleReservation.dateReservation = rset.getDate(4);
                return tupleReservation;
            }
        }

        return null;
    }

    /**
      * Lecture de la première reservation d'un livre.
      */
    public TupleReservation getReservationLivre(int idLivre) throws SQLException {

        this.stmtExisteLivre.setInt(1,
            idLivre);
        try(
            ResultSet rset = this.stmtExisteLivre.executeQuery()) {

            if(rset.next()) {
                TupleReservation tupleReservation = new TupleReservation();
                tupleReservation.idReservation = rset.getInt(1);
                tupleReservation.idLivre = rset.getInt(2);

                tupleReservation.idMembre = rset.getInt(3);
                tupleReservation.dateReservation = rset.getDate(4);
                return tupleReservation;
            }
        }

        return null;
    }

    /**
      * Lecture de la première reservation d'un livre.
      */
    public TupleReservation getReservationMembre(int idMembre) throws SQLException {

        this.stmtExisteMembre.setInt(1,
            idMembre);
        try(
            ResultSet rset = this.stmtExisteMembre.executeQuery()) {

            if(rset.next()) {
                TupleReservation tupleReservation = new TupleReservation();
                tupleReservation.idReservation = rset.getInt(1);
                tupleReservation.idLivre = rset.getInt(2);

                tupleReservation.idMembre = rset.getInt(3);
                tupleReservation.dateReservation = rset.getDate(4);
                return tupleReservation;
            }
        }

        return null;
    }

    /**
      * Réservation d'un livre.
      */
    public void reserver(int idReservation,
        int idLivre,
        int idMembre,
        String dateReservation) throws SQLException {
        this.stmtInsert.setInt(1,
            idReservation);
        this.stmtInsert.setInt(2,
            idLivre);
        this.stmtInsert.setInt(3,
            idMembre);
        this.stmtInsert.setString(4,
            dateReservation);
        this.stmtInsert.executeUpdate();
    }

    /**
      * Suppression d'une reservation.
      */
    public int annulerRes(int idReservation) throws SQLException {
        this.stmtDelete.setInt(1,
            idReservation);
        return this.stmtDelete.executeUpdate();
    }
}
