import java.sql.*;

/**
 * Permet de représenter un tuple de la table livre.
 * 
*/

public class TupleLivre {

  public int    idLivre;
  public String titre;
  public String auteur;
  public Date   dateAcquisition;
  public int    idMembre;
  public Date   datePret;
}
