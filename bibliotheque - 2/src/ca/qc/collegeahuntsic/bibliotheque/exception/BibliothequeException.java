
package ca.qc.collegeahuntsic.bibliotheque.exception;

// TODO: Auto-generated Javadoc
/**
 * L'exception BiblioException est levée lorsqu'une transaction est inadéquate.
 * Par exemple -- livre inexistant
 */

public final class BibliothequeException extends Exception {
    /**
     * TODO Auto-generated field javadoc 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new biblio exception.
     *
     * @param message the message
     */
    public BibliothequeException(String message) {
        super(message);
    }
}