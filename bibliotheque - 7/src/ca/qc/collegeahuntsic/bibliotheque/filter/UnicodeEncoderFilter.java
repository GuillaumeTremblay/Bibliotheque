// Fichier UnicodeEncoderFilter.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.Constants;

/**
 * Filters the requests and response by encoding them in UTF-8.
 * 
 * @author Gilles Benichou
 */
public class UnicodeEncoderFilter implements Filter {
    /**
     * Default constructor.
     */
    public UnicodeEncoderFilter() {
        super();
    }

    /**
     * Does nothing.
     * 
     * @param filterConfig The filter configuration to use
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // Does nothing.
    }

    /**
     * Filters the requests and response by encoding them in UTF-8.
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
        request.setCharacterEncoding(Constants.UTF_8_CHARACTER_ENCODING);
        response.setContentType(Constants.UTF_8_CONTENT_TYPE);
        chain.doFilter(servletRequest,
            servletResponse);
    }

    /**
     * Does nothing.
     */
    @Override
    public void destroy() {
        // Does nothing.
    }
}
