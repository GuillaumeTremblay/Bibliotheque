import java.sql.*;
import java.io.*;

/**
 *<pre>
 *
 *Permet de créer la BD utilisée par Biblio.java.
 *
 *Paramètres:0- serveur SQL 
 *           1- bd nom de la BD 
 *           2- user id pour établir une connexion avec le serveur SQL
 *           3- mot de passe pour le user id
 *</pre>
 */
class CreerBD {
  public static void main (String args [])
      throws Exception, SQLException, IOException {

    if (args.length < 3)
        {
        System.out.println("Usage: java CreerBD <serveur> <bd> <user> <password>");
        return;
        }

    Connexion cx = new Connexion(args[0],args[1],args[2],args[3]);

    Statement stmt = cx.getConnection().createStatement ();

    stmt.executeUpdate
("DROP TABLE membre CASCADE CONSTRAINTS");
    stmt.executeUpdate
("CREATE TABLE membre ( " +
"idMembre        number(3) check(idMembre > 0), " +
"nom             varchar(10) NOT NULL, " +
"telephone       number(10) , " +
"limitePret      number(2) check(limitePret > 0 and limitePret <= 10) , " +
"nbpret          number(2) default 0 check(nbpret >= 0) , " +
"CONSTRAINT cleMembre PRIMARY KEY (idMembre), " +
"CONSTRAINT limiteNbPret check(nbpret <= limitePret) " +
")");

    stmt.executeUpdate
("DROP TABLE livre CASCADE CONSTRAINTS");
    stmt.executeUpdate
("CREATE TABLE livre ( " +
"idLivre         number(3) check(idLivre > 0) , " +
"titre           varchar(10) NOT NULL, " +
"auteur          varchar(10) NOT NULL, " +
"dateAcquisition date not null, " +
"idMembre        number(3) , " +
"datePret        date , " +
"CONSTRAINT cleLivre PRIMARY KEY (idLivre), " +
"CONSTRAINT refPretMembre FOREIGN KEY (idMembre) REFERENCES membre " +
")");

    stmt.executeUpdate
("DROP TABLE reservation CASCADE CONSTRAINTS");
    stmt.executeUpdate
("CREATE TABLE reservation ( " +
"idReservation   number(3) , " +
"idMembre        number(3) , " +
"idLivre         number(3) , " +
"dateReservation date , " +
"CONSTRAINT cleReservation PRIMARY KEY (idReservation) , " +
"CONSTRAINT cleCandidateReservation UNIQUE (idMembre,idLivre) , " +
"CONSTRAINT refReservationMembre FOREIGN KEY (idMembre) REFERENCES membre " +
"  ON DELETE CASCADE , " +
"CONSTRAINT refReservationLivre FOREIGN KEY (idLivre) REFERENCES livre " +
"  ON DELETE CASCADE " +
")");

    stmt.close();
    cx.fermer();
  }
}
