// Fichier Action.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Base action.
 * 
 * @author Gilles Benichou
 */
public class Action extends org.apache.struts.action.Action {
    /**
     * Default constructor.
     */
    public Action() {
        super();
    }

    /**
     * Does nothing.
     * 
     * @param mapping The mapping to use
     * @param form The form to use
     * @param request The request to use
     * @param response The response to use
     * @return The appropriate action forward
     * @throws Exception If an error occurs
     */
    @Override
    public ActionForward execute(ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        return mapping.findForward(Constants.SUCCESS);
    }
}
