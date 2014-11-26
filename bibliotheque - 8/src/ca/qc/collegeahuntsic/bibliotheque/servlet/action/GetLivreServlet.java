// Fichier GetLivreServlet.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Get livre action servlet.
 * 
 * @author Gilles Benichou
 */
public class GetLivreServlet extends BibliothequeServlet {
    public static final String LIVRE_ATTRIBUTE_NAME = "livre";

    private static final long serialVersionUID = 1L;

    private static final String FORWARD_RESOURCE = "/WEB-INF/jsp/viewLivre/viewIndex.jsp";

    private static final Log LOGGER = LogFactory.getLog(GetLivreServlet.class);

    /**
     * Default constructor.
     */
    public GetLivreServlet() {
        super();
    }

    /**
     * Gets a livre from the database.
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
        try {
            final String idLivre = request.getParameter(LivreDTO.ID_LIVRE_COLUMN_NAME);
            beginTransaction();
            final LivreDTO livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            commitTransaction();
            request.setAttribute(GetLivreServlet.LIVRE_ATTRIBUTE_NAME,
                livreDTO);
            final RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(GetLivreServlet.FORWARD_RESOURCE);
            dispatcher.forward(request,
                response);
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(BibliothequeException bibliothequeException) {
                GetLivreServlet.LOGGER.error(bibliothequeException);
            }
            throw new ServletException(exception);
        }
    }
}
