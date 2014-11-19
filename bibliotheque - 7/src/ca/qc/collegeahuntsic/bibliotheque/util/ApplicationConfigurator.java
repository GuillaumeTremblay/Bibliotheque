// Fichier ApplicationConfigurator.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.util;

import java.util.ResourceBundle;
import ca.qc.collegeahuntsic.bibliotheque.Constants;

/**
 * Getter of properties in properties file.
 * 
 * @author Gilles Benichou
 */
public final class ApplicationConfigurator {
    /**
     * Resource bundle.
     */
    private static ResourceBundle resourceBundle;

    /**
     * Default constructor.
     */
    private ApplicationConfigurator() {
        super();
    }

    /**
     * Initializes the ApplicationConfiguratorPlugIn.
     * 
     * @param resourceBundleBaseName Resource bundle base name to use
     */
    public static synchronized void init(String resourceBundleBaseName) {
        ApplicationConfigurator.resourceBundle = ResourceBundle.getBundle(resourceBundleBaseName);
    }

    /**
     * Gets the value associated with a property key.
     * 
     * @param key The property key to use
     * @return The value associated with the property key
     */
    public static String getProperty(String key) {
        return ApplicationConfigurator.resourceBundle.getString(key);
    }

    /**
     * Indicates if a value associated with a property key exists and is equals to "on".
     * 
     * @param key The property key to use
     * @return If a value associated with a property key exists and is equals to "on".
     */
    public static boolean isEnabled(String key) {
        final String value = ApplicationConfigurator.getProperty(key);
        return value != null
            && Constants.ON.equals(value);
    }
}
