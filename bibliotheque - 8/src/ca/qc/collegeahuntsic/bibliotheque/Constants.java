// Fichier Constants.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque;

import ca.qc.collegeahuntsic.bibliotheque.util.ApplicationConfigurator;

/**
 * Bibliothèque constants.
 * 
 * @author Gilles Benichou
 */
public interface Constants {
    /**
     * Character for the character '.'.
     */
    Character DOT = new Character('.');

    /**
     * Character for the character '''.
     */
    Character QUOTE = new Character('\'');

    /**
     * Application name ID.
     */
    String APPLICATION_NAME_ID = "app.name"; //$NON-NLS-1$

    /**
     * String literal for the application name.
     */
    String APPLICATION_NAME = ApplicationConfigurator.getProperty(Constants.APPLICATION_NAME_ID);

    /**
     * String literal for the quoted application name.
     */
    String QUOTED_APPLICATION_NAME = Constants.QUOTE
        + Constants.APPLICATION_NAME
        + Constants.QUOTE;

    /**
     * String literal for {@link ca.qc.collegeahuntsic.bibliotheque.struts.plugIn.ApplicationConfiguratorPlugIn} object init
     * parameter name.
     */
    String APPLICATION_CONFIGURATOR_INIT_PARAMETER_NAME = "applicationConfigurator"; //$NON-NLS-1$

    /**
     * String literal for {@link ca.qc.collegeahuntsic.bibliotheque.struts.plugIn.ApplicationConfiguratorPlugIn}
     * object init message.
     */
    String APPLICATION_CONFIGURATOR_INIT_MESSAGE = "Application configurator loaded for application " //$NON-NLS-1$
        + Constants.QUOTED_APPLICATION_NAME
        + Constants.DOT;

    /**
     * String literal for {@link ca.qc.collegeahuntsic.bibliotheque.struts.plugIn.ApplicationConfiguratorPlugIn}
     * object destroy message.
     */
    String APPLICATION_CONFIGURATOR_DESTROY_MESSAGE = "Application configurator unloaded for application " //$NON-NLS-1$
        + Constants.QUOTED_APPLICATION_NAME
        + Constants.DOT;

    /**
     * String literal for the UTF-8 character encoding.
     */
    String UTF_8_CHARACTER_ENCODING = "UTF-8"; //$NON-NLS-1$

    /**
     * String literal for the UTF-8 content type.
     */
    String UTF_8_CONTENT_TYPE = "text/html; charset=UTF-8"; //$NON-NLS-1$

    /**
     * String literal for "ON".
     */
    String ON = "on"; //$NON-NLS-1$

    /**
     * String literal for "OFF".
     */
    String OFF = "off"; //$NON-NLS-1$

    /**
     * String literal for the mail bundle name.
     */
    String MAIL_RESOURCE_BUNDLE_NAME = "conf/mail.properties"; //$NON-NLS-1$

    /**
     * String literal for the mail user key.
     */
    String MAIL_USER_KEY = "mail.user"; //$NON-NLS-1$

    /**
     * String literal for the mail password key.
     */
    String MAIL_PASSWORD_KEY = "mail.password"; //$NON-NLS-1$

    /**
     * String array for the zero length String array.
     */
    String[] ZERO_LENGTH_STRING_ARRAY = new String[0];

    /**
     * String literal for the success action forward name.
     */
    String SUCCESS = "success"; //$NON-NLS-1$

    /**
     * String literal for the failure action forward name.
     */
    String FAILURE = "failure"; //$NON-NLS-1$

    /**
     * String literal for the failed fetching of all livres.
     */
    String LIVRE_MANAGEMENT_VIEW_ALL_LIVRES_FAILED_MESSAGE_ID = "livreManagement.viewAllLivres.viewAllLivresFailed.displayMessage"; //$NON-NLS-1$

    /**
     * String literal for the failed fetching of a livre.
     */
    String LIVRE_MANAGEMENT_VIEW_LIVRE_FAILED_MESSAGE_ID = "livreManagement.viewLivre.viewLivreFailed.displayMessage"; //$NON-NLS-1$

    /**
      * String literal for the successful save of a livre.
      */
    String LIVRE_MANAGEMENT_SAVE_LIVRE_SUCCESSFUL_MESSAGE_ID = "livreManagement.viewLivre.saveSuccessful.displayMessage"; //$NON-NLS-1$

    /**
     * String literal for the failed save of a livre.
     */
    String LIVRE_MANAGEMENT_SAVE_LIVRE_FAILED_MESSAGE_ID = "livreManagement.viewLivre.saveFailed.displayMessage"; //$NON-NLS-1$

    /**
     * String literal for the admin email.
     */
    String ADMIN_EMAIL_KEY = "admin.email"; //$NON-NLS-1$

    /**
     * String literal for the error from address.
     */
    String ERROR_FROM_ADDRESS_KEY = "error.from.address"; //$NON-NLS-1$

    /**
     * String literal for the error from name.
     */
    String ERROR_FROM_NAME_KEY = "error.from.name"; //$NON-NLS-1$

    /**
     * String literal for the error to address.
     */
    String ERROR_TO_ADDRESS_KEY = "error.to.address"; //$NON-NLS-1$

    /**
     * String literal for the error to name.
     */
    String ERROR_TO_NAME_KEY = "error.to.name"; //$NON-NLS-1$
}
