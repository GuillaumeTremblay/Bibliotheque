// Fichier SaveLivreServlet.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.action;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.util.FormatteurDate;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Save livre action servlet.
 * 
 * @author Gilles Benichou
 */
public class SaveLivreServlet extends BibliothequeServlet {
    public static final String SAVE_LIVRE_SUCCESSFUL = "saveLivreSuccessful";

    private static final long serialVersionUID = 1L;

    private static final String FORWARD_RESOURCE = "/getLivre.do";

    private static final Log LOGGER = LogFactory.getLog(SaveLivreServlet.class);

    /**
     * Default constructor.
     */
    public SaveLivreServlet() {
        super();
    }

    /**
     * Saves a livre to the database.
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
            final String titre = request.getParameter(LivreDTO.TITRE_COLUMN_NAME);
            final String auteur = request.getParameter(LivreDTO.AUTEUR_COLUMN_NAME);
            final String dateAcquisition = request.getParameter(LivreDTO.DATE_ACQUISITION_COLUMN_NAME);
            beginTransaction();
            final LivreDTO livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            livreDTO.setTitre(titre);
            livreDTO.setAuteur(auteur);
            livreDTO.setDateAcquisition(FormatteurDate.timestampValue(dateAcquisition));
            getLivreFacade().updateLivre(getSession(),
                livreDTO);
            commitTransaction();
            request.setAttribute(SaveLivreServlet.SAVE_LIVRE_SUCCESSFUL,
                Boolean.TRUE);
            final RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(SaveLivreServlet.FORWARD_RESOURCE);
            dispatcher.forward(request,
                response);
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | InvalidDTOException
            | FacadeException
            | ParseException exception) {
            try {
                rollbackTransaction();
            } catch(BibliothequeException bibliothequeException) {
                SaveLivreServlet.LOGGER.error(bibliothequeException);
            }
            throw new ServletException(exception);
        }
    }
}
