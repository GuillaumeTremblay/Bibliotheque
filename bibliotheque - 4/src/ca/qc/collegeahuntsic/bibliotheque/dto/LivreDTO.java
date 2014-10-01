// Fichier LivreDTO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Timestamp;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * DTO de la table <code>livre</code>.
 * 
 * @author Gilles Benichou
 */
public final class LivreDTO extends DTO {
    public static final String ID_LIVRE_COLUMN_NAME = "idLivre";

    public static final String TITRE_COLUMN_NAME = "titre";

    public static final String AUTEUR_COLUMN_NAME = "auteur";

    public static final String DATE_ACQUISITION_COLUMN_NAME = "dateAcquisition";

    private static final long serialVersionUID = 1L;

    private String idLivre;

    private String titre;

    private String auteur;

    private Timestamp dateAcquisition;

    /**
     * Crée un DTO de la table <code>livre</code>.
     */
    public LivreDTO() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.idLivre</code>.
     *
     * @return La variable d'instance <code>this.idLivre</code>
     */
    public String getIdLivre() {
        return this.idLivre;
    }

    /**
     * Setter de la variable d'instance <code>this.idLivre</code>.
     *
     * @param idLivre La valeur à utiliser pour la variable d'instance <code>this.idLivre</code>
     */
    public void setIdLivre(String idLivre) {
        this.idLivre = idLivre;
    }

    /**
     * Getter de la variable d'instance <code>this.titre</code>.
     *
     * @return La variable d'instance <code>this.titre</code>
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Setter de la variable d'instance <code>this.titre</code>.
     *
     * @param titre La valeur à utiliser pour la variable d'instance <code>this.titre</code>
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Getter de la variable d'instance <code>this.auteur</code>.
     *
     * @return La variable d'instance <code>this.auteur</code>
     */
    public String getAuteur() {
        return this.auteur;
    }

    /**
     * Setter de la variable d'instance <code>this.auteur</code>.
     *
     * @param auteur La valeur à utiliser pour la variable d'instance <code>this.auteur</code>
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * Getter de la variable d'instance <code>this.dateAcquisition</code>.
     *
     * @return La variable d'instance <code>this.dateAcquisition</code>
     */
    public Timestamp getDateAcquisition() {
        return this.dateAcquisition;
    }

    /**
     * Setter de la variable d'instance <code>this.dateAcquisition</code>.
     *
     * @param dateAcquisition La valeur à utiliser pour la variable d'instance <code>this.dateAcquisition</code>
     */
    public void setDateAcquisition(Timestamp dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
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
                && obj instanceof LivreDTO;
            if(equals) {
                LivreDTO livreDTO = (LivreDTO) obj;
                EqualsBuilder equalsBuilder = new EqualsBuilder();
                equalsBuilder.appendSuper(super.equals(livreDTO));
                equalsBuilder.append(getIdLivre(),
                    livreDTO.getIdLivre());
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
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(459,
            449);
        hashCodeBuilder.appendSuper(super.hashCode());
        hashCodeBuilder.append(getIdLivre());
        return hashCodeBuilder.toHashCode();
    }
}
