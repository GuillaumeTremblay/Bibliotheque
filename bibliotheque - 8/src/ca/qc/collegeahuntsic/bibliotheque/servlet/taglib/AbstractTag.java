// Fichier AbstractTag.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.taglib;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * Base tag for all bilbiothèque tags. <br />
 * All bilbiothèque tags should inherit from it.
 * 
 * @author Gilles Benichou
 */
public abstract class AbstractTag implements Tag {
    /**
     * The page context.
     */
    private PageContext pageContext;

    /**
     * The parent tag.
     */
    private Tag parent;

    /**
     * Default constructor.
     */
    public AbstractTag() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter for pageContext.
     * 
     * @return Returns the pageContext
     */
    public PageContext getPageContext() {
        return this.pageContext;
    }

    /**
     * Setter for pageContext.
     * 
     * @param pageContext The pageContext to set
     */
    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    /**
     * Getter for parent.
     * 
     * @return Returns the parent
     */
    @Override
    public Tag getParent() {
        return this.parent;
    }

    /**
     * Setter for parent.
     * 
     * @param parent The parent to set
     */
    @Override
    public void setParent(Tag parent) {
        this.parent = parent;
    }

    // EndRegion Getters and Setters

    /**
     * Method invoked at start of <code>AbstractTag</code>.
     * 
     * @return {@link Tag#SKIP_BODY}
     * @throws JspTagException If an error occurs
     */
    @Override
    public int doStartTag() throws JspTagException {
        return Tag.SKIP_BODY;
    }

    /**
     * Method invoked to release all resources.
     */
    @Override
    public void release() {
        setPageContext(null);
        setParent(null);
    }
}
