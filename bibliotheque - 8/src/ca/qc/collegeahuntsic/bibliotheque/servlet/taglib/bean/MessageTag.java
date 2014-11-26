// Fichier MessageTag.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.AbstractTag;

/**
 * Tag to display a resource bundle message.
 * 
 * @author Gilles Benichou
 */
public class MessageTag extends AbstractTag {
    private String key;

    /**
     * Default constructor.
     */
    public MessageTag() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.key</code>.
     *
     * @return La variable d'instance <code>this.key</code>
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Setter de la variable d'instance <code>this.key</code>.
     *
     * @param key La valeur à utiliser pour la variable d'instance <code>this.key</code>
     */
    public void setKey(String key) {
        this.key = key;
    }

    // EndRegion Getters and Setters

    /**
     * Prints out the web resource bundle message.
     * 
     * @return {@link Tag#EVAL_PAGE}
     * @throws JspTagException If an error occurs
     */
    @Override
    public int doEndTag() throws JspTagException {
        final HttpSession session = getPageContext().getSession();
        Locale locale = (Locale) session.getAttribute(Constants.LOCALE_ATTRIBUTE_NAME);
        if(locale == null) {
            locale = Constants.DEFAULT_LOCALE;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        String languageResourceBundleName = Constants.LANGUAGE_RESOURCE_BUNDLE_NAME.replace('.',
            '/')
            + "_"
            + locale.getLanguage()
            + "_"
            + locale.getCountry()
            + ".properties";
        if(classLoader.getResourceAsStream(languageResourceBundleName) == null) {
            languageResourceBundleName = Constants.LANGUAGE_RESOURCE_BUNDLE_NAME.replace('.',
                '/')
                + "_"
                + locale.getLanguage()
                + ".properties";
            if(classLoader.getResourceAsStream(languageResourceBundleName) == null) {
                languageResourceBundleName = Constants.LANGUAGE_RESOURCE_BUNDLE_NAME.replace('.',
                    '/')
                    + ".properties";
            }
        }
        try(
            InputStream inputStream = classLoader.getResourceAsStream(languageResourceBundleName)) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            final String value = properties.getProperty(getKey());
            getPageContext().getOut().write(value);
        } catch(IOException ioException) {
            throw new JspTagException(ioException.getMessage());
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void release() {
        super.release();
    }
}
