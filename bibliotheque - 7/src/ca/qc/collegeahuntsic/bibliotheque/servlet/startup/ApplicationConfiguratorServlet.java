// Fichier ApplicationConfiguratorServlet.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.startup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import ca.qc.collegeahuntsic.bibliotheque.util.ApplicationConfigurator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Application configurator servlet. Loads the {@link ca.qc.collegeahuntsic.bibliotheque.util.ApplicationConfigurator}.
 * 
 * @author Gilles Benichou
 */
public class ApplicationConfiguratorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * The logger.
     */
    private static final Log LOGGER = LogFactory.getLog(ApplicationConfiguratorServlet.class);

    /**
     * Default constructor.
     */
    public ApplicationConfiguratorServlet() {
        super();
    }

    /**
     * Initializes the {@link ca.qc.collegeahuntsic.bibliotheque.util.ApplicationConfigurator}.
     * 
     * @throws ServletException If an error occurs
     */
    @Override
    public void init() throws ServletException {
        ApplicationConfigurator.init(getServletConfig().getInitParameter(Constants.APPLICATION_CONFIGURATOR_INIT_PARAMETER_NAME));
        ApplicationConfiguratorServlet.LOGGER.info(Constants.APPLICATION_CONFIGURATOR_INIT_MESSAGE);
    }

    /**
     * Does nothing.
     */
    @Override
    public void destroy() {
        ApplicationConfiguratorServlet.LOGGER.info(Constants.APPLICATION_CONFIGURATOR_DESTROY_MESSAGE);
    }
}
