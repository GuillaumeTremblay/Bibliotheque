import java.sql.*;

/**
 * Gestion des transactions de reliées aux prêts de livres
 * aux membres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transactions prêter,
 * renouveler et retourner.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class GestionPret {

private Livre livre;
private Membre membre;
private Reservation reservation;
private Connexion cx;

/**
  * Creation d'une instance.
  * La connection de l'instance de livre et de membre doit être la même que cx,
  * afin d'assurer l'intégrité des transactions.
  */
public GestionPret(Livre livre, Membre membre, Reservation reservation)
  throws BiblioException
{
if (livre.getConnexion() != membre.getConnexion() ||
    reservation.getConnexion() != membre.getConnexion())
    throw new BiblioException
        ("Les instances de livre, de membre et de reservation n'utilisent pas la même connexion au serveur");
this.cx = livre.getConnexion();
this.livre = livre;
this.membre = membre;
this.reservation = reservation;
}

/**
  * Pret d'un livre à un membre.
  * Le livre ne doit pas être prêté.
  * Le membre ne doit pas avoir dépassé sa limite de pret.
  */
public void preter(int idLivre, int idMembre, String datePret)
  throws SQLException, BiblioException, Exception
{
try {
    /* Verfier si le livre est disponible */
    TupleLivre tupleLivre = livre.getLivre(idLivre);
    if (tupleLivre == null)
        throw new BiblioException("Livre inexistant: " + idLivre);
    if (tupleLivre.idMembre != 0)
        throw new BiblioException
            ("Livre " + idLivre + " deja prete a " + tupleLivre.idMembre);

    /* Vérifie si le membre existe et sa limite de pret */
    TupleMembre tupleMembre = membre.getMembre(idMembre);
    if (tupleMembre == null)
        throw new BiblioException("Membre inexistant: " + idMembre);
    if (tupleMembre.nbPret >= tupleMembre.limitePret)
        throw new BiblioException
            ("Limite de pret du membre " + idMembre + " atteinte");

    /* Vérifie s'il existe une réservation pour le livre */
    TupleReservation tupleReservation = reservation.getReservationLivre(idLivre);
    if (tupleReservation != null)
        throw new BiblioException("Livre réservé par : " + tupleReservation.idMembre +
            " idReservation : " + tupleReservation.idReservation);

    /* Enregistrement du pret. */
    int nb1 = livre.preter(idLivre,idMembre,datePret);
    if (nb1 == 0)
        throw new BiblioException
            ("Livre supprimé par une autre transaction");
    int nb2 = membre.preter(idMembre);
    if (nb2 == 0)
        throw new BiblioException
            ("Membre supprimé par une autre transaction");
    cx.commit();
    }
catch (Exception e)
    {
    cx.rollback();
    throw e;
    }
}

/**
  * Renouvellement d'un pret.
  * Le livre doit être prêté.
  * Le livre ne doit pas être réservé.
  */
public void renouveler(int idLivre, String datePret)
  throws SQLException, BiblioException, Exception
{
try {
    /* Verifier si le livre est prêté */
    TupleLivre tupleLivre = livre.getLivre(idLivre);
    if (tupleLivre == null)
        throw new BiblioException("Livre inexistant: " + idLivre);
    if (tupleLivre.idMembre == 0)
        throw new BiblioException
            ("Livre " + idLivre + " n'est pas prete");

    /* Verifier si date renouvellement >= datePret */
    if (Date.valueOf(datePret).before(tupleLivre.datePret))
        throw new BiblioException
            ("Date de renouvellement inferieure à la date de pret");

    /* Vérifie s'il existe une réservation pour le livre */
    TupleReservation tupleReservation = reservation.getReservationLivre(idLivre);
    if (tupleReservation != null)
        throw new BiblioException("Livre réservé par : " + tupleReservation.idMembre +
            " idReservation : " + tupleReservation.idReservation);

    /* Enregistrement du pret. */
    int nb1 = livre.preter(idLivre,tupleLivre.idMembre,datePret);
    if (nb1 == 0)
        throw new BiblioException
            ("Livre supprime par une autre transaction");
    cx.commit();
    }
catch (Exception e)
    {
    cx.rollback();
    throw e;
    }
}

/**
  * Retourner un livre prêté
  * Le livre doit être prêté.
  */
public void retourner(int idLivre, String dateRetour)
  throws SQLException, BiblioException, Exception
{
try {
    /* Verifier si le livre est prêté */
    TupleLivre tupleLivre = livre.getLivre(idLivre);
    if (tupleLivre == null)
        throw new BiblioException("Livre inexistant: " + idLivre);
    if (tupleLivre.idMembre == 0)
        throw new BiblioException
            ("Livre " + idLivre + " n'est pas prêté ");

    /* Verifier si date retour >= datePret */
    if (Date.valueOf(dateRetour).before(tupleLivre.datePret))
        throw new BiblioException
            ("Date de retour inferieure à la date de pret");

    /* Retour du pret. */
    int nb1 = livre.retourner(idLivre);
    if (nb1 == 0)
        throw new BiblioException
            ("Livre supprimé par une autre transaction");

    int nb2 = membre.retourner(tupleLivre.idMembre);
    if (nb2 == 0)
        throw new BiblioException
            ("Livre supprimé par une autre transaction");
    cx.commit();
    }
catch (Exception e)
    {
    cx.rollback();
    throw e;
    }
}
}
