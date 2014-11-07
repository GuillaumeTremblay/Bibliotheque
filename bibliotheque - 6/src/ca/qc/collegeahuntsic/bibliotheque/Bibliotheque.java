
package ca.qc.collegeahuntsic.bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.StringTokenizer;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.util.BibliothequeCreateur;
import ca.qc.collegeahuntsic.bibliotheque.util.FormatteurDate;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interface du système de gestion d'une bibliothèque
 *
 * Ce programme permet d'appeler les transactions de base d'une bibliothèque. Il
 * gère des livres, des membres et des réservations. Les données sont conservées
 * dans une base de données relationnelles accédée avec JDBC. Pour une liste des
 * transactions traitées, voir la méthode afficherAide().
 *
 * Paramètres 0- site du serveur SQL ("local", "distant" ou "postgres") 1- nom
 * de la BD 2- user id pour établir une connexion avec le serveur SQL 3- mot de
 * passe pour le user id 4- fichier de transaction [optionnel] si non spécifié,
 * les transactions sont lues au clavier (System.in)
 *
 * Pré-condition la base de données de la bibliothèque doit exister
 *
 * Post-condition le programme effectue les maj associées à chaque transaction
 *
 * @author Chou Huynh
 */
public final class Bibliotheque {
    private static BibliothequeCreateur gestionBiblio;

    private static final Log LOGGER = LogFactory.getLog(Bibliotheque.class);

    /**
     * 
     * Constructeur de bibliotheque.
     *
     */
    private Bibliotheque() {
        super();
    }

    /**
     * 
     */

    /**
     * 
     * Ouverture de la BD, traitement des transactions et fermeture de la BD.
     *
     * @param argv = bibliotheque.dat
     * @throws Exception lance une exception
     */
    public static void main(String[] argv) throws Exception {
        // validation du nombre de paramètres
        if(argv.length < 1) {
            LOGGER.info("Usage: java Biblio [<fichier-transactions>]");
        }

        // ouverture du fichier de transactions
        final InputStream sourceTransaction = Bibliotheque.class.getResourceAsStream("/"
            + argv[0]);
        try(
            BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {
            gestionBiblio = new BibliothequeCreateur();
            traiterTransactions(reader);
        } catch(Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * Traitement des transactions de la bibliothèque.
     * @param reader recoit un befferedreader
     * @throws Exception lance Exception
     */
    static void traiterTransactions(BufferedReader reader) throws Exception {
        afficherAide();
        LOGGER.info("\n\n\n");
        String transaction = lireTransaction(reader);
        while(!finTransaction(transaction)) {
            final StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            if(tokenizer.hasMoreTokens()) {
                executerTransaction(tokenizer);
            }
            transaction = lireTransaction(reader);
        }
    }

    /**
     * Lecture d'une transaction.
     * @param reader recoit un befferedreader
     * @throws IOException lance IOException
     * @return retourne une transaction a effectuer
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        final String transaction = reader.readLine();
        if(transaction != null) {
            LOGGER.info(transaction);
        }
        return transaction;
    }

    /**
     * Décodage et traitement d'une transaction.
     * @param tokenizer recoit une string de commande en stringtokenizer
     */
    static void executerTransaction(StringTokenizer tokenizer) {
        final String command = tokenizer.nextToken();

        try {
            if("aide".startsWith(command)) {
                afficherAide();
            } else if("acquerir".startsWith(command)) {
                acquerire(tokenizer);
            } else if("vendre".startsWith(command)) {
                vendre(tokenizer);
            } else if("preter".startsWith(command)) {
                preter(tokenizer);
            } else if("renouveler".startsWith(command)) {
                renouveler(tokenizer);
            } else if("retourner".startsWith(command)) {
                retourner(tokenizer);
            } else if("inscrire".startsWith(command)) {
                inscrire(tokenizer);
            } else if("desinscrire".startsWith(command)) {
                desinscrire(tokenizer);
            } else if("reserver".startsWith(command)) {
                reserver(tokenizer);
            } else if("utiliser".startsWith(command)) {
                utiliser(tokenizer);
            } else if("annuler".startsWith(command)) {
                annuler(tokenizer);
            } else if("--".startsWith(command)) {
                // ne rien faire; c'est un commentaire
                LOGGER.info("");
            } else {
                LOGGER.info("  Transactions non reconnue.  Essayer \"aide\"");
            }
        } catch(BibliothequeException e) {
            LOGGER.error(e);
        }
    }

    /**
     * 
     * Methode pour aquérir un livre.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void acquerire(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final LivreDTO livreDTO = new LivreDTO();
            livreDTO.setTitre(readString(tokenizer));
            livreDTO.setAuteur(readString(tokenizer));
            livreDTO.setDateAcquisition(readDate(tokenizer));
            gestionBiblio.getLivreFacade().acquerir(gestionBiblio.getSession(),
                livreDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | InvalidDTOClassException
            | FacadeException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour vendre un livre.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void vendre(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = gestionBiblio.getLivreFacade().getLivre(gestionBiblio.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            gestionBiblio.getLivreFacade().vendre(gestionBiblio.getSession(),
                livreDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | ExistingLoanException
            | ExistingReservationException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour preter un livre.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void preter(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final PretDTO pretDTO = new PretDTO();
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = gestionBiblio.getMembreFacade().getMembre(gestionBiblio.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = gestionBiblio.getLivreFacade().getLivre(gestionBiblio.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            pretDTO.setMembreDTO(membreDTO);
            pretDTO.setLivreDTO(livreDTO);
            gestionBiblio.getPretFacade().commencer(gestionBiblio.getSession(),
                pretDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | ExistingLoanException
            | InvalidLoanLimitException
            | ExistingReservationException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour renouveler un pret.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void renouveler(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final PretDTO pretDTO = new PretDTO();
            pretDTO.setIdPret(readString(tokenizer));
            gestionBiblio.getPretFacade().renouveler(gestionBiblio.getSession(),
                pretDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | MissingLoanException
            | ExistingReservationException
            | FacadeException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour retourner un livre.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void retourner(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final PretDTO pretDTO = new PretDTO();
            pretDTO.setIdPret(readString(tokenizer));
            gestionBiblio.getPretFacade().terminer(gestionBiblio.getSession(),
                pretDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | MissingLoanException
            | FacadeException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour inscrire un membre.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void inscrire(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final MembreDTO membreDTO = new MembreDTO();
            membreDTO.setNom(readString(tokenizer));
            membreDTO.setTelephone(readString(tokenizer));
            membreDTO.setLimitePret(readString(tokenizer));
            membreDTO.setNbPret("0");
            gestionBiblio.getMembreFacade().inscrire(gestionBiblio.getSession(),
                membreDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | InvalidDTOClassException
            | FacadeException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour desinscrire un membre.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void desinscrire(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = gestionBiblio.getMembreFacade().getMembre(gestionBiblio.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            gestionBiblio.getMembreFacade().desinscrire(gestionBiblio.getSession(),
                membreDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | InvalidDTOClassException
            | ExistingLoanException
            | InvalidCriterionException
            | InvalidSortByPropertyException
            | ExistingReservationException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour faire une reservation.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void reserver(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            // Juste pour éviter deux timestamps de réservation strictement
            // identiques
            Thread.sleep(1);
            final ReservationDTO reservationDTO = new ReservationDTO();
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = gestionBiblio.getMembreFacade().getMembre(gestionBiblio.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = gestionBiblio.getLivreFacade().getLivre(gestionBiblio.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            reservationDTO.setMembreDTO(membreDTO);
            reservationDTO.setLivreDTO(livreDTO);
            gestionBiblio.getReservationFacade().placer(gestionBiblio.getSession(),
                reservationDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InterruptedException
            | InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | MissingDTOException
            | InvalidDTOException
            | MissingLoanException
            | ExistingLoanException
            | ExistingReservationException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * 
     * Methode pour utiliser une reservation.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void utiliser(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setIdReservation(readString(tokenizer));
            gestionBiblio.getReservationFacade().utiliser(gestionBiblio.getSession(),
                reservationDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | ExistingReservationException
            | ExistingLoanException
            | InvalidLoanLimitException
            | FacadeException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * Affiche le menu des transactions acceptées par le système.
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    static void annuler(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            gestionBiblio.beginTransaction();
            final ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setIdReservation(readString(tokenizer));
            gestionBiblio.getReservationFacade().annuler(gestionBiblio.getSession(),
                reservationDTO);
            gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | InvalidPrimaryKeyException
            | MissingDTOException
            | InvalidDTOClassException
            | FacadeException e) {
            gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * Affiche le menu des transactions acceptées par le système.
     * 
     */
    static void afficherAide() {
        LOGGER.info("");
        LOGGER.info("Chaque transaction comporte un nom et une liste d'arguments");
        LOGGER.info("separes par des espaces. La liste peut etre vide.");
        LOGGER.info(" Les dates sont en format yyyy-mm-dd.");
        LOGGER.info("");
        LOGGER.info("Les transactions sont :");
        LOGGER.info("  aide");
        LOGGER.info("  exit");
        LOGGER.info("  acquerir <titre> <auteur> <dateAcquisition>");
        LOGGER.info("  preter <idMembre> <idLivre>");
        LOGGER.info("  renouveler <idLivre>");
        LOGGER.info("  retourner <idLivre>");
        LOGGER.info("  vendre <idLivre>");
        LOGGER.info("  inscrire <nom> <telephone> <limitePret>");
        LOGGER.info("  desinscrire <idMembre>");
        LOGGER.info("  reserver <idMembre> <idLivre>");
        LOGGER.info("  utiliser <idReservation>");
        LOGGER.info("  annuler <idReservation>");
        // LOGGER.info("  listerLivresRetard <dateCourante>");
        // LOGGER.info("  listerLivresTitre <mot>");
        // LOGGER.info("  listerLivres");
    }

    /**
     * Vérifie si la fin du traitement des transactions est atteinte.
     * @param transaction 
     * @return retourne true ou false si la fichier est vide ou non
     */
    static boolean finTransaction(String transaction) {
        // fin de fichier atteinte
        boolean aReturn = false;
        if(transaction == null) {
            aReturn = true;
        }

        final StringTokenizer tokenizer = new StringTokenizer(transaction,
            " ");

        // ligne ne contenant que des espaces
        if(!tokenizer.hasMoreTokens()) {
            aReturn = false;
        }

        // commande "exit"
        final String commande = tokenizer.nextToken();
        aReturn = "exit".equals(commande);

        return aReturn;
    }

    /**
     * lecture d'une chaîne de caractères de la transaction entrée à l'écran.
     * @param tokenizer recoit un token
     * @throws BibliothequeException lance une BibliothequeException
     * @return renvoit un token sous forme de string
     */
    static String readString(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**
     * lecture d'un int java de la transaction entrée à l'écran.
     * @param tokenizer recoit un token
     * @throws BibliothequeException lance une BibliothequeException
     * @return renvoit un token sous forme de int
     */
    static int readInt(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return Integer.valueOf(token).intValue();
            } catch(NumberFormatException e) {
                throw new BibliothequeException("Nombre attendu à la place de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**
     * lecture d'un long java de la transaction entrée à l'écran.
     * @param tokenizer recoit un token
     * @throws BibliothequeException lance une BibliothequeException
     * @return renvoit un token sous forme de long
     */
    static long readLong(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return Long.valueOf(token).longValue();
            } catch(NumberFormatException e) {
                throw new BibliothequeException("Nombre attendu à la place de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**
     * lecture d'une date en format YYYY-MM-DD.
     * @param tokenizer recoit un token
     * @throws BibliothequeException lance une BibliothequeException
     * @return renvoit un token sous forme de date
     */
    static Timestamp readDate(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return FormatteurDate.timestampValue(token);
            } catch(ParseException e) {
                throw new BibliothequeException("Date en format YYYY-MM-DD attendue à la place  de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }
}
