import java.text.*;
import java.util.Date;

/**
 * Permet de valider le format d'une date en YYYY-MM-DD et de la convertir en un
 * objet Date.
 * 
 */
public class FormatDate
{
private static SimpleDateFormat formatAMJ;
static
    {
    formatAMJ = new SimpleDateFormat("yyyy-MM-dd");
    formatAMJ.setLenient(false);
    }

/**
 * Convertit une String du format YYYY-MM-DD en un objet de la classe Date.
 */
public static Date convertirDate(String dateString)
  throws ParseException
{
return formatAMJ.parse(dateString);
}

public static String toString(Date date)
{
return formatAMJ.format(date);
}
}
