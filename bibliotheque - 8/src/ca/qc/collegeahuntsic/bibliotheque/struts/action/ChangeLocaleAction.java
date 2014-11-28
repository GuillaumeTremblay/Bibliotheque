// Fichier ChangeLocaleAction.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-12

package ca.qc.collegeahuntsic.bibliotheque.struts.action;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import ca.qc.collegeahuntsic.bibliotheque.struts.form.LocaleActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

/**
 * Change locale action.
 * 
 * @author Gilles Benichou
 */
public class ChangeLocaleAction extends BibliothequeAction {
    /**
     * Default constructor.
     * 
     * @param sessionFactory The session factory to use
     */
    public ChangeLocaleAction(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Changes the user's Locale.
     * 
     * @param mapping The mapping to use
     * @param form The action form to use
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
        final String forwardName = Constants.SUCCESS;
        final LocaleActionForm localeActionForm = (LocaleActionForm) form;
        final Locale locale = new Locale(localeActionForm.getLanguageIsoCode(),
            localeActionForm.getCountryIsoCode());
        setLocale(request,
            locale);
        return mapping.findForward(forwardName);
    }
}
