// Fichier GetAllLivresServlet.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.action;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Get all livres action servlet.
 * 
 * @author Gilles Benichou
 */
public class GetAllLivresServlet extends BibliothequeServlet {
    public static final String LIVRES_ATTRIBUTE_NAME = "livres";

    private static final long serialVersionUID = 1L;

    private static final Log LOGGER = LogFactory.getLog(GetAllLivresServlet.class);

    /**
     * Default constructor.
     */
    public GetAllLivresServlet() {
        super();
    }

    /**
     * Gets all livres from the database.
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
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            commitTransaction();
            request.setAttribute(GetAllLivresServlet.LIVRES_ATTRIBUTE_NAME,
                livres);
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(BibliothequeException bibliothequeException) {
                GetAllLivresServlet.LOGGER.error(bibliothequeException);
            }
            throw new ServletException(exception);
        }
    }
}
