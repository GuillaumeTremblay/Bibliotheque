// Fichier LocaleActionForm.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-12

package ca.qc.collegeahuntsic.bibliotheque.struts.form;

/**
 * The locale action form.
 *
 * @author Gilles Benichou
 */
public class LocaleActionForm extends ActionForm {
    private static final long serialVersionUID = 1L;

    private String languageIsoCode;

    private String countryIsoCode;

    /**
     * Default constructor.
     */
    public LocaleActionForm() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.languageIsoCode</code>.
     *
     * @return La variable d'instance <code>this.languageIsoCode</code>
     */
    public String getLanguageIsoCode() {
        return this.languageIsoCode;
    }

    /**
     * Setter de la variable d'instance <code>this.languageIsoCode</code>.
     *
     * @param languageIsoCode La valeur à utiliser pour la variable d'instance <code>this.languageIsoCode</code>
     */
    public void setLanguageIsoCode(String languageIsoCode) {
        this.languageIsoCode = languageIsoCode;
    }

    /**
     * Getter de la variable d'instance <code>this.countryIsoCode</code>.
     *
     * @return La variable d'instance <code>this.countryIsoCode</code>
     */
    public String getCountryIsoCode() {
        return this.countryIsoCode;
    }

    /**
     * Setter de la variable d'instance <code>this.countryIsoCode</code>.
     *
     * @param countryIsoCode La valeur à utiliser pour la variable d'instance <code>this.countryIsoCode</code>
     */
    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }
    // EndRegion Getters and Setters
}
