
package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * Permet de représenter un tuple de la table membre.
 * 
 */

public class TupleReservationDto extends DTO {

    /** The id reservation. */
    public int idReservation;

    /** The id livre. */
    public int idLivre;

    /** The id membre. */
    public int idMembre;

    /** The date reservation. */
    public Date dateReservation;
}
