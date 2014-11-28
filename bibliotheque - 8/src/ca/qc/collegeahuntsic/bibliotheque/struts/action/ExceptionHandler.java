// Fichier ExceptionHandler.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-15

package ca.qc.collegeahuntsic.bibliotheque.struts.action;

import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import ca.qc.collegeahuntsic.bibliotheque.util.ApplicationConfigurator;
import ca.qc.collegeahuntsic.bibliotheque.util.Emailer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

/**
 * Exception handler to catch any exception thrown by any action.
 * 
 * @author Gilles Benichou
 */
public class ExceptionHandler extends org.apache.struts.action.ExceptionHandler {
    /**
     * Default constructor.
     */
    public ExceptionHandler() {
        super();
    }

    /**
     * Catches any exception thrown by any action, sends an email to the admin and logs the exception.
     * 
     * @param exception The exception to use
     * @param exceptionConfig The exception config to use
     * @param mapping The mapping to use
     * @param form The form to use
     * @param request The request to use
     * @param response The response to use
     * @throws ServletException If any error occurs
     * @return The appropriate action forward
     */
    @Override
    public ActionForward execute(Exception exception,
        ExceptionConfig exceptionConfig,
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws ServletException {
        final Emailer emailer = new Emailer();
        emailer.setFromAddress(ApplicationConfigurator.getProperty(Constants.ERROR_FROM_ADDRESS_KEY));
        emailer.setFromName(ApplicationConfigurator.getProperty(Constants.ERROR_FROM_NAME_KEY));
        emailer.setSubject(new Date(System.currentTimeMillis())
            + " - Exception dans le syst&#232;me");
        emailer.addTo(ApplicationConfigurator.getProperty(Constants.ERROR_TO_ADDRESS_KEY),
            ApplicationConfigurator.getProperty(Constants.ERROR_TO_NAME_KEY));
        emailer.setContent(ExceptionUtils.getStackTrace(exception),
            "text/html");
        final Thread thread = new Thread(emailer);
        thread.start();
        logException(exception);
        return super.execute(exception,
            exceptionConfig,
            mapping,
            form,
            request,
            response);
    }
}
