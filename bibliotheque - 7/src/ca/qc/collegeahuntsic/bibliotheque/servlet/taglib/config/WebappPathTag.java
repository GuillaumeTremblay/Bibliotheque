// Fichier WebappPathTag.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.AbstractTag;

/**
 * Tag to display the web application path.
 * 
 * @author Gilles Benichou
 */
public class WebappPathTag extends AbstractTag {
    /**
     * Default constructor.
     */
    public WebappPathTag() {
        super();
    }

    /**
     * Prints out the web application path.
     * 
     * @return {@link Tag#EVAL_PAGE}
     * @throws JspTagException If an error occurs
     */
    @Override
    public int doEndTag() throws JspTagException {
        try {
            final HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();
            getPageContext().getOut().write(request.getContextPath());
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
