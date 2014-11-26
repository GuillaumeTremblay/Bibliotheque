// Fichier CurrentYearTag.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.config;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import ca.qc.collegeahuntsic.bibliotheque.servlet.taglib.AbstractTag;

/**
 * Tag to display the current year.
 * 
 * @author Gilles Benichou
 */
public class CurrentYearTag extends AbstractTag {
    /**
     * Default constructor.
     */
    public CurrentYearTag() {
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
            getPageContext().getOut().write(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
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
