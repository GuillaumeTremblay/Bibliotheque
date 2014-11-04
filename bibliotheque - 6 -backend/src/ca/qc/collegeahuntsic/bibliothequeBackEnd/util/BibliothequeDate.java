// Fichier Date.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Un objet @{link java.util.Date} amélioré.
 * 
 * @author Gilles Benichou
 */
public class BibliothequeDate extends Date {
    private static final long serialVersionUID = 1L;

    /**
     * Allocates a <code>BibliothequeDate</code> object and initializes it so that it represents the time at which it was allocated, measured to
     * the nearest millisecond.
     * 
     * @see System#currentTimeMillis()
     */
    public BibliothequeDate() {
        super();
    }

    /**
     * Allocates a <code>BibliothequeDate</code> object and initializes it to represent the specified number of milliseconds since the standard
     * base time known as "the epoch", namely January 1, 1970, 00:00:00 GMT.
     * 
     * @param date the milliseconds since January 1, 1970, 00:00:00 GMT.
     * @see System#currentTimeMillis()
     */
    public BibliothequeDate(long date) {
        super(date);
    }

    /**
     * Calcule la date de début d'une date (la veille).
     * 
     * @param date La date à utiliser
     * @return La date de début
     */
    public static Date getStartDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,
            calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE,
            calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
            calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
            calendar.getActualMaximum(Calendar.MILLISECOND));
        calendar.add(Calendar.DATE,
            -1);
        return calendar.getTime();
    }

    /**
     * Calcule la date de fin d'une date (le lendemain).
     * 
     * @param date La date à utiliser
     * @return La date de fin
     */
    public static Date getEndDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,
            calendar.getActualMinimum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE,
            calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
            calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
            calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.add(Calendar.DATE,
            1);
        return calendar.getTime();
    }
}
