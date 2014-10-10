// Fichier PretDTO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-26

package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Timestamp;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * DTO de la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public final class PretDTO extends DTO {
    public static final String ID_PRET_COLUMN_NAME = "idPret";

    public static final String ID_MEMBRE_COLUMN_NAME = "idMembre";

    public static final String ID_LIVRE_COLUMN_NAME = "idLivre";

    public static final String DATE_PRET_COLUMN_NAME = "datePret";

    public static final String DATE_RETOUR_COLUMN_NAME = "dateRetour";

    private static final long serialVersionUID = 1L;

    private String idPret;

    private MembreDTO membreDTO;

    private LivreDTO livreDTO;

    private Timestamp datePret;

    private Timestamp dateRetour;

    /**
     * Crée un DTO de la table <code>pret</code>.
     */
    public PretDTO() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.idPret</code>.
     *
     * @return La variable d'instance <code>this.idPret</code>
     */
    public String getIdPret() {
        return this.idPret;
    }

    /**
     * Setter de la variable d'instance <code>this.idPret</code>.
     *
     * @param idPret La valeur à utiliser pour la variable d'instance <code>this.idPret</code>
     */
    public void setIdPret(String idPret) {
        this.idPret = idPret;
    }

    /**
     * Getter de la variable d'instance <code>this.membreDTO</code>.
     *
     * @return La variable d'instance <code>this.membreDTO</code>
     */
    public MembreDTO getMembreDTO() {
        return this.membreDTO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDTO</code>.
     *
     * @param membreDTO La valeur à utiliser pour la variable d'instance <code>this.membreDTO</code>
     */
    public void setMembreDTO(MembreDTO membreDTO) {
        this.membreDTO = membreDTO;
    }

    /**
     * Getter de la variable d'instance <code>this.livreDTO</code>.
     *
     * @return La variable d'instance <code>this.livreDTO</code>
     */
    public LivreDTO getLivreDTO() {
        return this.livreDTO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDTO</code>.
     *
     * @param livreDTO La valeur à utiliser pour la variable d'instance <code>this.livreDTO</code>
     */
    public void setLivreDTO(LivreDTO livreDTO) {
        this.livreDTO = livreDTO;
    }

    /**
     * Getter de la variable d'instance <code>this.datePret</code>.
     *
     * @return La variable d'instance <code>this.datePret</code>
     */
    public Timestamp getDatePret() {
        return this.datePret;
    }

    /**
     * Setter de la variable d'instance <code>this.datePret</code>.
     *
     * @param datePret La valeur à utiliser pour la variable d'instance <code>this.datePret</code>
     */
    public void setDatePret(Timestamp datePret) {
        this.datePret = datePret;
    }

    /**
     * Getter de la variable d'instance <code>this.dateRetour</code>.
     *
     * @return La variable d'instance <code>this.dateRetour</code>
     */
    public Timestamp getDateRetour() {
        return this.dateRetour;
    }

    /**
     * Setter de la variable d'instance <code>this.dateRetour</code>.
     *
     * @param dateRetour La valeur à utiliser pour la variable d'instance <code>this.dateRetour</code>
     */
    public void setDateRetour(Timestamp dateRetour) {
        this.dateRetour = dateRetour;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        boolean equals = this == obj;
        if(!equals) {
            equals = obj != null
                && obj instanceof PretDTO;
            if(equals) {
                PretDTO pretDTO = (PretDTO) obj;
                EqualsBuilder equalsBuilder = new EqualsBuilder();
                equalsBuilder.appendSuper(super.equals(pretDTO));
                equalsBuilder.append(getIdPret(),
                    pretDTO.getIdPret());
                equals = equalsBuilder.isEquals();
            }
        }
        return equals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(23,
            13);
        hashCodeBuilder.appendSuper(super.hashCode());
        hashCodeBuilder.append(getIdPret());
        return hashCodeBuilder.toHashCode();
    }
}
