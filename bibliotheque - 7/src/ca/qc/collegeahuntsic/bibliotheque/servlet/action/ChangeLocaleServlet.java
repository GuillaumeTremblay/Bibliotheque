// Fichier ChangeLocaleServlet.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.action;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ca.qc.collegeahuntsic.bibliotheque.Constants;

/**
 * Change locale action servlet.
 * 
 * @author Gilles Benichou
 */
public class ChangeLocaleServlet extends BibliothequeServlet {
    public static final String LANGUAGE_ISO_CODE_PARAMETER_NAME = "languageIsoCode";

    public static final String COUNTRY_ISO_CODE_PARAMETER_NAME = "countryIsoCode";

    private static final long serialVersionUID = 1L;

    private static final String FORWARD_RESOURCE = "/viewTemplateIndex.do";

    /**
     * Default constructor.
     */
    public ChangeLocaleServlet() {
        super();
    }

    /**
     * Changes the user's Locale.
     * 
     * @param request The request to use
     * @param response The response to use
     * @throws ServletException If an error occurs
     * @throws IOException If an error occurs
     */
    @Override
    public void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        final String languageIsoCode = request.getParameter(ChangeLocaleServlet.LANGUAGE_ISO_CODE_PARAMETER_NAME);
        final String countryIsoCode = request.getParameter(ChangeLocaleServlet.COUNTRY_ISO_CODE_PARAMETER_NAME);
        final Locale locale = new Locale(languageIsoCode,
            countryIsoCode);
        final HttpSession httpSession = request.getSession();
        httpSession.setAttribute(Constants.LOCALE_ATTRIBUTE_NAME,
            locale);
        final RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(ChangeLocaleServlet.FORWARD_RESOURCE);
        dispatcher.forward(request,
            response);
    }
}
