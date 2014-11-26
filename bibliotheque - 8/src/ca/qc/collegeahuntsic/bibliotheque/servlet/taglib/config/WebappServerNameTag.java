// Fichier WebappServerNameTag.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.config;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.AbstractTag;

/**
 * Tag to display the web application server name.
 * 
 * @author Gilles Benichou
 */
public class WebappServerNameTag extends AbstractTag {
    /**
     * Default constructor.
     */
    public WebappServerNameTag() {
        super();
    }

    /**
     * Prints out the web application server name.
     * 
     * @return {@link Tag#EVAL_PAGE}
     * @throws JspTagException If an error occurs
     */
    @Override
    public int doEndTag() throws JspTagException {
        try {
            getPageContext().getOut().write(getPageContext().getRequest().getServerName());
        } catch(IOException iOException) {
            throw new JspTagException(iOException.getMessage());
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
