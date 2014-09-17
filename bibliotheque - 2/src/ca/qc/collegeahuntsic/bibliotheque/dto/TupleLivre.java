package ca.qc.collegeahuntsic.bibliotheque.dto;
import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * Permet de représenter un tuple de la table livre.
 * 
*/

public class TupleLivre {

  /** The id livre. */
  public int    idLivre;
  
  /** The titre. */
  public String titre;
  
  /** The auteur. */
  public String auteur;
  
  /** The date acquisition. */
  public Date   dateAcquisition;
  
  /** The id membre. */
  public int    idMembre;
  
  /** The date pret. */
  public Date   datePret;
}
