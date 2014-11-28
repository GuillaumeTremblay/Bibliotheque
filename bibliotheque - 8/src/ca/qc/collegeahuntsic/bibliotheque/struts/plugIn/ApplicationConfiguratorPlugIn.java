// Fichier ApplicationConfiguratorPlugIn.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.struts.plugIn;

import javax.servlet.ServletException;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import ca.qc.collegeahuntsic.bibliotheque.util.ApplicationConfigurator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * Application configurator plug-in. Loads the {@link ca.qc.collegeahuntsic.bibliotheque.util.ApplicationConfigurator} object.
 * 
 * @author Gilles Benichou
 */
public class ApplicationConfiguratorPlugIn implements PlugIn {
    /**
     * The logger.
     */
    private static final Log LOGGER = LogFactory.getLog(ApplicationConfiguratorPlugIn.class);

    /**
     * Default constructor.
     */
    public ApplicationConfiguratorPlugIn() {
        super();
    }

    /**
     * Initializes the {@link ca.qc.collegeahuntsic.bibliotheque.struts.plugIn.ApplicationConfiguratorPlugIn} object.
     * 
     * @param actionServlet The ActionServlet to use
     * @param moduleConfig The ModuleConfig to use
     * @throws ServletException If an error occurs
     */
    @Override
    public void init(ActionServlet actionServlet,
        ModuleConfig moduleConfig) throws ServletException {
        ApplicationConfigurator.init(actionServlet.getInitParameter(Constants.APPLICATION_CONFIGURATOR_INIT_PARAMETER_NAME));
        ApplicationConfiguratorPlugIn.LOGGER.info(Constants.APPLICATION_CONFIGURATOR_INIT_MESSAGE);
    }

    /**
     * Does nothing.
     */
    @Override
    public final void destroy() {
        ApplicationConfiguratorPlugIn.LOGGER.info(Constants.APPLICATION_CONFIGURATOR_DESTROY_MESSAGE);
    }
}
