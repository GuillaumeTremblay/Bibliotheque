// Fichier Constants.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque;

import java.text.SimpleDateFormat;
import java.util.Locale;
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
     * Character for the character '/'.
     */
    Character SLASH = new Character('/');

    /**
     * Character for the character ' '.
     */
    Character SPACE = new Character(' ');

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
     * String literal for {@link ca.qc.collegeahuntsic.bibliotheque.servlet.startup.ApplicationConfiguratorServlet} object init
     * parameter name.
     */
    String APPLICATION_CONFIGURATOR_INIT_PARAMETER_NAME = "applicationConfigurator"; //$NON-NLS-1$

    /**
     * String literal for {@link ca.qc.collegeahuntsic.bibliotheque.servlet.startup.ApplicationConfiguratorServlet}
     * object init message.
     */
    String APPLICATION_CONFIGURATOR_INIT_MESSAGE = "Application configurator loaded for application " //$NON-NLS-1$
        + Constants.QUOTED_APPLICATION_NAME
        + Constants.DOT;

    /**
     * String literal for {@link ca.qc.collegeahuntsic.bibliotheque.servlet.startup.ApplicationConfiguratorServlet}
     * object destroy message.
     */
    String APPLICATION_CONFIGURATOR_DESTROY_MESSAGE = "Application configurator unloaded for application " //$NON-NLS-1$
        + Constants.QUOTED_APPLICATION_NAME
        + Constants.DOT;

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
     * String literal for the language resources bundle name.
     */
    String LANGUAGE_RESOURCE_BUNDLE_NAME = "lang.ApplicationResources"; //$NON-NLS-1$

    /**
     * String literal for the attribute name for Locale.
     */
    String LOCALE_ATTRIBUTE_NAME = "locale"; //$NON-NLS-1$

    /**
     * String literal for the internationalized messages.
     */
    String MESSAGES_ATTRIBUTE_NAME = "messages"; //$NON-NLS-1$

    /**
     * String literal for the default language ID.
     */
    String DEFAULT_LANGUAGE_ID = ApplicationConfigurator.getProperty("defaultLanguageId"); //$NON-NLS-1$

    /**
     * String literal for the default country ID.
     */
    String DEFAULT_COUNTRY_ID = ApplicationConfigurator.getProperty("defaultCountryId"); //$NON-NLS-1$

    /**
     * Locale for the default Locale.
     */
    Locale DEFAULT_LOCALE = new Locale(Constants.DEFAULT_LANGUAGE_ID,
        Constants.DEFAULT_COUNTRY_ID);

    /**
     * String literal for the default date format.
     */
    String DEFAULT_DATE_FORMAT = ApplicationConfigurator.getProperty("defaultDateFormat"); //$NON-NLS-1$

    /**
     * Default simple date format from the default date format.
     */
    SimpleDateFormat DEFAULT_SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
}
