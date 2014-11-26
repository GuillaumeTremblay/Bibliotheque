// Fichier ViewErrorPageServlet.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * View error page action servlet.
 * 
 * @author Gilles Benichou
 */
public class ViewErrorPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String FORWARD_RESOURCE = "/WEB-INF/jsp/viewErrorPage/viewIndex.jsp";

    /**
     * Default constructor.
     */
    public ViewErrorPageServlet() {
        super();
    }

    /**
     * Displays the error page index page.
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
        final RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(ViewErrorPageServlet.FORWARD_RESOURCE);
        dispatcher.forward(request,
            response);
    }
}
