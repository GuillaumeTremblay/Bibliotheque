// Fichier ViewErrorPageFilter.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.filter;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.util.Emailer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Filters the requests to forward to the error page if an exception is thrown.
 * 
 * @author Gilles Benichou
 */
public class ViewErrorPageFilter implements Filter {
    private static final String FORWARD_RESOURCE = "/viewErrorPage.do";

    private static final Log LOGGER = LogFactory.getLog(ViewErrorPageFilter.class);

    private FilterConfig filterConfig;

    /**
     * Default constructor.
     */
    public ViewErrorPageFilter() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.filterConfig</code>.
     *
     * @return La variable d'instance <code>this.filterConfig</code>
     */
    private FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    /**
     * Setter de la variable d'instance <code>this.filterConfig</code>.
     *
     * @param filterConfig La valeur à utiliser pour la variable d'instance <code>this.filterConfig</code>
     */
    private void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    // EndRegion Getters and Setters

    /**
     * Does nothing.
     * 
     * @param unFilterConfig The filter configuration to use
     */
    @Override
    public void init(FilterConfig unFilterConfig) {
        setFilterConfig(unFilterConfig);
    }

    /**
     * Displays the error page if an exception is thrown.
     * 
     * @param servletRequest The servlet request to use
     * @param servletResponse the servlet response to use
     * @param chain The filter chain to use
     * @throws IOException If an error occurs
     * @throws ServletException If an error occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain chain) throws IOException,
        ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            chain.doFilter(servletRequest,
                servletResponse);
        } catch(
            ServletException
            | IOException exception) {
            ViewErrorPageFilter.LOGGER.error(exception);
            final Emailer emailer = new Emailer();
            emailer.setFromAddress("gilles.benichou@gmail.com");
            emailer.setFromName("L'application Bibliothèque 7");
            emailer.setSubject(new Date(System.currentTimeMillis())
                + " - Exception dans le système");
            emailer.addTo("gilles.benichou@gmail.com",
                "Gilles Bénichou");
            emailer.setContent(ExceptionUtils.getStackTrace(exception),
                "text/html");
            final Thread thread = new Thread(emailer);
            thread.start();
            final RequestDispatcher dispatcher = getFilterConfig().getServletContext().getRequestDispatcher(ViewErrorPageFilter.FORWARD_RESOURCE);
            dispatcher.forward(request,
                response);
        }
    }

    /**
     * Does nothing.
     */
    @Override
    public void destroy() {
        // Does nothing.
    }
}
