import java.sql.*;

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
stmtExiste = cx.getConnection().prepareStatement
    ("select idReservation, idLivre, idMembre, dateReservation " +
      "from reservation where idReservation = ?");
stmtExisteLivre = cx.getConnection().prepareStatement
    ("select idReservation, idLivre, idMembre, dateReservation " +
      "from reservation where idLivre = ? " +
      "order by dateReservation");
stmtExisteMembre = cx.getConnection().prepareStatement
("select idReservation, idLivre, idMembre, dateReservation " +
  "from reservation where idMembre = ? ");
stmtInsert = cx.getConnection().prepareStatement
    ("insert into reservation (idReservation, idlivre, idMembre, dateReservation) " +
      "values (?,?,?,to_date(?,'YYYY-MM-DD'))");
stmtDelete = cx.getConnection().prepareStatement
    ("delete from reservation where idReservation = ?");
}

/**
  * Retourner la connexion associée.
  */
public Connexion getConnexion() {

return cx;
}

/**
  * Verifie si une reservation existe.
  */
public boolean existe(int idReservation) throws SQLException {

stmtExiste.setInt(1,idReservation);
ResultSet rset = stmtExiste.executeQuery();
boolean reservationExiste = rset.next();
rset.close();
return reservationExiste;
}

/**
  * Lecture d'une reservation.
  */
public TupleReservation getReservation(int idReservation) throws SQLException {

stmtExiste.setInt(1,idReservation);
ResultSet rset = stmtExiste.executeQuery();
if (rset.next())
    {
    TupleReservation tupleReservation = new TupleReservation();
    tupleReservation.idReservation = rset.getInt(1);
    tupleReservation.idLivre = rset.getInt(2);;
    tupleReservation.idMembre = rset.getInt(3);
    tupleReservation.dateReservation = rset.getDate(4);
    return tupleReservation;
    }
else
    return null;
}

/**
  * Lecture de la première reservation d'un livre.
  */
public TupleReservation getReservationLivre(int idLivre) throws SQLException {

stmtExisteLivre.setInt(1,idLivre);
ResultSet rset = stmtExisteLivre.executeQuery();
if (rset.next())
    {
    TupleReservation tupleReservation = new TupleReservation();
    tupleReservation.idReservation = rset.getInt(1);
    tupleReservation.idLivre = rset.getInt(2);;
    tupleReservation.idMembre = rset.getInt(3);
    tupleReservation.dateReservation = rset.getDate(4);
    return tupleReservation;
    }
else
    return null;
}

/**
  * Lecture de la première reservation d'un livre.
  */
public TupleReservation getReservationMembre(int idMembre) throws SQLException {

stmtExisteMembre.setInt(1,idMembre);
ResultSet rset = stmtExisteMembre.executeQuery();
if (rset.next())
    {
    TupleReservation tupleReservation = new TupleReservation();
    tupleReservation.idReservation = rset.getInt(1);
    tupleReservation.idLivre = rset.getInt(2);;
    tupleReservation.idMembre = rset.getInt(3);
    tupleReservation.dateReservation = rset.getDate(4);
    return tupleReservation;
    }
else
    return null;
}

/**
  * Réservation d'un livre.
  */
public void reserver(int idReservation, int idLivre, int idMembre,  String dateReservation)
  throws SQLException
{
stmtInsert.setInt(1,idReservation);
stmtInsert.setInt(2,idLivre);
stmtInsert.setInt(3,idMembre);
stmtInsert.setString(4,dateReservation);
stmtInsert.executeUpdate();
}

/**
  * Suppression d'une reservation.
  */
public int annulerRes(int idReservation)
  throws SQLException
{
stmtDelete.setInt(1,idReservation);
return stmtDelete.executeUpdate();
}
}
