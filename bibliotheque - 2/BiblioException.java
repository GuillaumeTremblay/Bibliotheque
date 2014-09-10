/**
 * L'exception BiblioException est levée lorsqu'une transaction est inadéquate.
 * Par exemple -- livre inexistant
 */

public final class BiblioException extends Exception {
    /**
         * TODO Auto-generated field javadoc 
         */
    private static final long serialVersionUID = 7216530337546754800L;

    public BiblioException(String message) {
        super(message);
    }
}