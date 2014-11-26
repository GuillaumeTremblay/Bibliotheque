// Fichier WebappProtocolTag.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.config;

import java.io.IOException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.AbstractTag;

/**
 * Tag to display the web application protocol.
 * 
 * @author Gilles Benichou
 */
public class WebappProtocolTag extends AbstractTag {
    /**
     * Default constructor.
     */
    public WebappProtocolTag() {
        super();
    }

    /**
     * Prints out the web application protocol.
     * 
     * @return {@link Tag#EVAL_PAGE}
     * @throws JspTagException If an error occurs
     */
    @Override
    public int doEndTag() throws JspTagException {
        try {
            final HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();
            final URL url = new URL(request.getRequestURL().toString());
            getPageContext().getOut().write(url.getProtocol());
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
