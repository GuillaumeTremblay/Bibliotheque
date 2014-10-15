// Fichier LivreDTO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;
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

    private Set<PretDTO> prets;

    private Set<ReservationDTO> reservations;

    /**
     * Crée un DTO de la table <code>livre</code>.
     */
    public LivreDTO() {
        super();
        setPrets(Collections.EMPTY_SET);
        setReservations(Collections.EMPTY_SET);
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

    /**
     * Getter de la variable d'instance <code>this.prets</code>.
     *
     * @return La variable d'instance <code>this.prets</code>
     */
    public Set<PretDTO> getPrets() {
        return this.prets;
    }

    /**
     * Setter de la variable d'instance <code>this.prets</code>.
     *
     * @param prets La valeur à utiliser pour la variable d'instance <code>this.prets</code>
     */
    public void setPrets(Set<PretDTO> prets) {
        this.prets = prets;
    }

    /**
     * Getter de la variable d'instance <code>this.reservations</code>.
     *
     * @return La variable d'instance <code>this.reservations</code>
     */
    public Set<ReservationDTO> getReservations() {
        return this.reservations;
    }

    /**
     * Setter de la variable d'instance <code>this.reservations</code>.
     *
     * @param reservations La valeur à utiliser pour la variable d'instance <code>this.reservations</code>
     */
    public void setReservations(Set<ReservationDTO> reservations) {
        this.reservations = reservations;
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
                final LivreDTO livreDTO = (LivreDTO) obj;
                final EqualsBuilder equalsBuilder = new EqualsBuilder();
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
        final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(459,
            449);
        hashCodeBuilder.appendSuper(super.hashCode());
        hashCodeBuilder.append(getIdLivre());
        return hashCodeBuilder.toHashCode();
    }
}
