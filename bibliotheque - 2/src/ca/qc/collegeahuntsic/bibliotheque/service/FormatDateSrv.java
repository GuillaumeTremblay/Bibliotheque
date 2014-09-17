package ca.qc.collegeahuntsic.bibliotheque.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * Permet de valider le format d'une date en YYYY-MM-DD et de la convertir en un
 * objet Date.
 * 
 */
public class FormatDate
{

/** The format amj. */
private static SimpleDateFormat formatAMJ;
static
    {
    formatAMJ = new SimpleDateFormat("yyyy-MM-dd");
    formatAMJ.setLenient(false);
    }

/**
 * Convertit une String du format YYYY-MM-DD en un objet de la classe Date.
 *
 * @param dateString the date string
 * @return the date
 * @throws ParseException the parse exception
 */
public static Date convertirDate(String dateString)
  throws ParseException
{
return formatAMJ.parse(dateString);
}

/**
 * To string.
 *
 * @param date the date
 * @return the string
 */
public static String toString(Date date)
{
return formatAMJ.format(date);
}
}
