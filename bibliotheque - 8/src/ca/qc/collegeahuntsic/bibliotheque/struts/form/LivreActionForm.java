// Fichier LivreActionForm.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-12

package ca.qc.collegeahuntsic.bibliotheque.struts.form;

import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;

/**
 * The livre action form.
 *
 * @author Gilles Benichou
 */
public class LivreActionForm extends ActionForm {
    private static final long serialVersionUID = 1L;

    private String idLivre;

    private String titre;

    private String auteur;

    private String dateAcquisition;

    private List<LivreDTO> livres;

    /**
     * Default constructor.
     */
    public LivreActionForm() {
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
    public String getDateAcquisition() {
        return this.dateAcquisition;
    }

    /**
     * Setter de la variable d'instance <code>this.dateAcquisition</code>.
     *
     * @param dateAcquisition La valeur à utiliser pour la variable d'instance <code>this.dateAcquisition</code>
     */
    public void setDateAcquisition(String dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    /**
     * Getter de la variable d'instance <code>this.livres</code>.
     *
     * @return La variable d'instance <code>this.livres</code>
     */
    public List<LivreDTO> getLivres() {
        return this.livres;
    }

    /**
     * Setter de la variable d'instance <code>this.livres</code>.
     *
     * @param livres La valeur à utiliser pour la variable d'instance <code>this.livres</code>
     */
    public void setLivres(List<LivreDTO> livres) {
        this.livres = livres;
    }
    // EndRegion Getters and Setters
}
