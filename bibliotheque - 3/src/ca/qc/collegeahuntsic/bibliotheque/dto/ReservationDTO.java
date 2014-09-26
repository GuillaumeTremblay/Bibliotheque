
package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * Permet de représenter un tuple de la table membre.
 *
 */

public class ReservationDTO extends DTO {

    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    /** The id reservation. */
    private int idReservation;

    /** The id livre. */
    private int idLivre;

    /** The id membre. */
    private int idMembre;

    /** The date reservation. */
    private Timestamp dateReservation;

    /**
     * Getter de la variable d'instance <code>this.idReservation</code>.
     *
     * @return La variable d'instance <code>this.idReservation</code>
     */
    public int getIdReservation() {
        return this.idReservation;
    }

    /**
     * Setter de la variable d'instance <code>this.idReservation</code>.
     *
     * @param idReservation La valeur à utiliser pour la variable d'instance <code>this.idReservation</code>
     */
    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    /**
     * Getter de la variable d'instance <code>this.idLivre</code>.
     *
     * @return La variable d'instance <code>this.idLivre</code>
     */
    public int getIdLivre() {
        return this.idLivre;
    }

    /**
     * Setter de la variable d'instance <code>this.idLivre</code>.
     *
     * @param idLivre La valeur à utiliser pour la variable d'instance <code>this.idLivre</code>
     */
    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    /**
     * Getter de la variable d'instance <code>this.idMembre</code>.
     *
     * @return La variable d'instance <code>this.idMembre</code>
     */
    public int getIdMembre() {
        return this.idMembre;
    }

    /**
     * Setter de la variable d'instance <code>this.idMembre</code>.
     *
     * @param idMembre La valeur à utiliser pour la variable d'instance <code>this.idMembre</code>
     */
    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    /**
     * Getter de la variable d'instance <code>this.dateReservation</code>.
     *
     * @return La variable d'instance <code>this.dateReservation</code>
     */
    public Timestamp getDateReservation() {
        return this.dateReservation;
    }

    /**
     * Setter de la variable d'instance <code>this.dateReservation</code>.
     *
     * @param dateReservation La valeur à utiliser pour la variable d'instance <code>this.dateReservation</code>
     */
    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }
}
