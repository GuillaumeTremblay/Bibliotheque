
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
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
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
     * Ouverture de la BD, traitement des transactions et fermeture de la BD.
     *
     * @param argv = bibliotheque.dat
     * @throws Exception lance une exception
     */
    public static void main(String[] argv) {
        // validation du nombre de paramètres
        if(argv.length < 1) {
            Bibliotheque.LOGGER.info("Usage: java Biblio [<fichier-transactions>]");
        }

        // ouverture du fichier de transactions
        final InputStream sourceTransaction = Bibliotheque.class.getResourceAsStream("/"
            + argv[0]);
        try(
            BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {
            Bibliotheque.gestionBiblio = new BibliothequeCreateur();
            Bibliotheque.traiterTransactions(reader);
        } catch(IOException ioException) {
            Bibliotheque.LOGGER.error(" **** "
                + ioException.getMessage());
        } catch(BibliothequeException bibliothequeException) {
            Bibliotheque.LOGGER.error(" **** "
                + bibliothequeException.getMessage());
        }
    }

    /**
     * Traitement des transactions de la bibliothèque.
     * @param reader recoit un befferedreader
     * @throws IOException
     * @throws BibliothequeException
     * @throws Exception lance Exception
     */
    private static void traiterTransactions(BufferedReader reader) throws IOException,
    BibliothequeException {
        Bibliotheque.afficherAide();
        Bibliotheque.LOGGER.info("\n\n\n");
        String transaction = lireTransaction(reader);
        while(!finTransaction(transaction)) {
            final StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            if(tokenizer.hasMoreTokens()) {
                Bibliotheque.executerTransaction(tokenizer);
            }
            transaction = Bibliotheque.lireTransaction(reader);
        }
    }

    /**
     * Lecture d'une transaction.
     * @param reader recoit un befferedreader
     * @throws IOException lance IOException
     * @return retourne une transaction a effectuer
     */
    private static String lireTransaction(BufferedReader reader) throws IOException {
        final String transaction = reader.readLine();
        if(transaction != null) {
            Bibliotheque.LOGGER.info(transaction);
        }
        return transaction;
    }

    /**
     * Décodage et traitement d'une transaction.
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException
     */
    private static void executerTransaction(StringTokenizer tokenizer) throws BibliothequeException {
        final String command = tokenizer.nextToken();
        try {
            switch(command) {
                case "aide":
                    Bibliotheque.afficherAide();
                    break;
                case "inscrire":
                    Bibliotheque.inscrire(tokenizer);
                    break;
                case "desinscrire":
                    Bibliotheque.desinscrire(tokenizer);
                    break;
                case "acquerir":
                    Bibliotheque.acquerire(tokenizer);
                    break;
                case "vendre":
                    Bibliotheque.vendre(tokenizer);
                    break;
                case "preter":
                    Bibliotheque.preter(tokenizer);
                    break;
                case "renouveler":
                    Bibliotheque.renouveler(tokenizer);
                    break;
                case "retourner":
                    Bibliotheque.terminer(tokenizer);
                    break;
                case "reserver":
                    Bibliotheque.reserver(tokenizer);
                    break;
                case "utiliser":
                    Bibliotheque.utiliser(tokenizer);
                    break;
                case "annuler":
                    Bibliotheque.annuler(tokenizer);
                    break;
                case "--":
                    break;
                default:
                    Bibliotheque.LOGGER.info("  Transactions non reconnue.  Essayer \"aide\"");
            }
        } catch(BibliothequeException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * Affiche le menu des transactions acceptées par le système.
     *
     */
    private static void afficherAide() {
        Bibliotheque.LOGGER.info("\n");
        Bibliotheque.LOGGER.info("Chaque transaction comporte un nom et une liste d'arguments");
        Bibliotheque.LOGGER.info("separes par des espaces. La liste peut etre vide.");
        Bibliotheque.LOGGER.info(" Les dates sont en format yyyy-mm-dd.");
        Bibliotheque.LOGGER.info("");
        Bibliotheque.LOGGER.info("Les transactions sont :");
        Bibliotheque.LOGGER.info("  aide");
        Bibliotheque.LOGGER.info("  exit");
        Bibliotheque.LOGGER.info("  acquerir <titre> <auteur> <dateAcquisition>");
        Bibliotheque.LOGGER.info("  preter <idMembre> <idLivre>");
        Bibliotheque.LOGGER.info("  renouveler <idLivre>");
        Bibliotheque.LOGGER.info("  retourner <idLivre>");
        Bibliotheque.LOGGER.info("  vendre <idLivre>");
        Bibliotheque.LOGGER.info("  inscrire <nom> <telephone> <limitePret>");
        Bibliotheque.LOGGER.info("  desinscrire <idMembre>");
        Bibliotheque.LOGGER.info("  reserver <idMembre> <idLivre>");
        Bibliotheque.LOGGER.info("  utiliser <idReservation>");
        Bibliotheque.LOGGER.info("  annuler <idReservation>");
        // Bibliotheque.LOGGER.info("  listerLivresRetard <dateCourante>");
        // Bibliotheque.LOGGER.info("  listerLivresTitre <mot>");
        // Bibliotheque.LOGGER.info("  listerLivres");
    }

    /**
     * Vérifie si la fin du traitement des transactions est atteinte.
     * @param transaction
     * @return retourne true ou false si la fichier est vide ou non
     */
    private static boolean finTransaction(String transaction) {
        boolean resultat = true;
        if(transaction != null) {
            final StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            resultat = tokenizer.hasMoreTokens();
            if(resultat) {
                final String commande = tokenizer.nextToken();
                resultat = "exit".equals(commande);
            }
        }
        return resultat;
    }

    /**
     * lecture d'une chaîne de caractères de la transaction entrée à l'écran.
     * @param tokenizer recoit un token
     * @throws BibliothequeException lance une BibliothequeException
     * @return renvoit un token sous forme de string
     */
    private static String readString(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**
     * lecture d'une date en format YYYY-MM-DD.
     * @param tokenizer recoit un token
     * @throws BibliothequeException lance une BibliothequeException
     * @return renvoit un token sous forme de date
     */
    private static Timestamp readDate(StringTokenizer tokenizer) throws BibliothequeException {
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

    /**
     *
     * Methode pour inscrire un membre.
     *
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void inscrire(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            Bibliotheque.gestionBiblio.beginTransaction();
            final MembreDTO membreDTO = new MembreDTO();
            membreDTO.setNom(readString(tokenizer));
            membreDTO.setTelephone(readString(tokenizer));
            membreDTO.setLimitePret(readString(tokenizer));
            membreDTO.setNbPret("0");
            Bibliotheque.gestionBiblio.getMembreFacade().inscrireMembre(Bibliotheque.gestionBiblio.getSession(),
                membreDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * Désinscrit un membre.
     *
     * @param tokenizer Le tokenizer à utiliser
     * @throws BibliothequeException S'il y a une erreur
     */
    private static void desinscrire(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            Bibliotheque.gestionBiblio.beginTransaction();
            final MembreDTO membreDTO = new MembreDTO();
            membreDTO.setNom(Bibliotheque.readString(tokenizer));
            membreDTO.setTelephone(Bibliotheque.readString(tokenizer));
            membreDTO.setLimitePret(Bibliotheque.readString(tokenizer));
            membreDTO.setNbPret("0");
            Bibliotheque.gestionBiblio.getMembreFacade().desinscrireMembre(Bibliotheque.gestionBiblio.getSession(),
                membreDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | ExistingLoanException
            | ExistingReservationException
            | FacadeException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
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
            Bibliotheque.gestionBiblio.beginTransaction();
            final LivreDTO livreDTO = new LivreDTO();
            livreDTO.setTitre(readString(tokenizer));
            livreDTO.setAuteur(readString(tokenizer));
            livreDTO.setDateAcquisition(readDate(tokenizer));
            Bibliotheque.gestionBiblio.getLivreFacade().acquerirLivre(Bibliotheque.gestionBiblio.getSession(),
                livreDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
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
            Bibliotheque.gestionBiblio.beginTransaction();
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = gestionBiblio.getLivreFacade().getLivre(Bibliotheque.gestionBiblio.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            Bibliotheque.gestionBiblio.getLivreFacade().vendreLivre(Bibliotheque.gestionBiblio.getSession(),
                livreDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | MissingDTOException
            | ExistingLoanException
            | ExistingReservationException
            | InvalidDTOException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
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
            Bibliotheque.gestionBiblio.beginTransaction();
            final PretDTO pretDTO = new PretDTO();
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = Bibliotheque.gestionBiblio.getMembreFacade().getMembre(Bibliotheque.gestionBiblio.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = Bibliotheque.gestionBiblio.getLivreFacade().getLivre(Bibliotheque.gestionBiblio.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            pretDTO.setMembreDTO(membreDTO);
            pretDTO.setLivreDTO(livreDTO);
            Bibliotheque.gestionBiblio.getPretFacade().commencerPret(Bibliotheque.gestionBiblio.getSession(),
                pretDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | InvalidDTOException
            | ExistingLoanException
            | InvalidLoanLimitException
            | ExistingReservationException
            | MissingDTOException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
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
            Bibliotheque.gestionBiblio.beginTransaction();
            final String idPret = Bibliotheque.readString(tokenizer);
            final PretDTO pretDTO = Bibliotheque.gestionBiblio.getPretFacade().getPret(Bibliotheque.gestionBiblio.getSession(),
                idPret);
            if(pretDTO == null) {
                throw new MissingDTOException("Le prêt "
                    + idPret
                    + " n'existe pas");
            }
            Bibliotheque.gestionBiblio.getPretFacade().renouvelerPret(Bibliotheque.gestionBiblio.getSession(),
                pretDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | FacadeException
            | InvalidDTOException
            | MissingLoanException
            | ExistingReservationException
            | InvalidPrimaryKeyException
            | MissingDTOException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            gestionBiblio.rollbackTransaction();
        }

    }

    /**
     * Termine un prêt.
     *
     * @param tokenizer Le tokenizer à utiliser
     * @throws BibliothequeException S'il y a une erreur
     */
    private static void terminer(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            Bibliotheque.gestionBiblio.beginTransaction();
            Bibliotheque.gestionBiblio.beginTransaction();
            final String idPret = Bibliotheque.readString(tokenizer);
            final PretDTO pretDTO = Bibliotheque.gestionBiblio.getPretFacade().getPret(Bibliotheque.gestionBiblio.getSession(),
                idPret);
            if(pretDTO == null) {
                throw new MissingDTOException("Le prêt "
                    + idPret
                    + " n'existe pas");
            }
            Bibliotheque.gestionBiblio.getPretFacade().terminerPret(Bibliotheque.gestionBiblio.getSession(),
                pretDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException
            | InvalidPrimaryKeyException
            | MissingDTOException
            | MissingLoanException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
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
            Bibliotheque.gestionBiblio.beginTransaction();
            // Juste pour éviter deux timestamps de réservation strictement
            // identiques
            Thread.sleep(1);
            final ReservationDTO reservationDTO = new ReservationDTO();
            final String idMembre = readString(tokenizer);
            final MembreDTO membreDTO = Bibliotheque.gestionBiblio.getMembreFacade().getMembre(gestionBiblio.getSession(),
                idMembre);
            if(membreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + idMembre
                    + " n'existe pas");
            }
            final String idLivre = readString(tokenizer);
            final LivreDTO livreDTO = Bibliotheque.gestionBiblio.getLivreFacade().getLivre(gestionBiblio.getSession(),
                idLivre);
            if(livreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + idLivre
                    + " n'existe pas");
            }
            reservationDTO.setMembreDTO(membreDTO);
            reservationDTO.setLivreDTO(livreDTO);
            reservationDTO.setDateReservation(new Timestamp(System.currentTimeMillis()));
            Bibliotheque.gestionBiblio.getReservationFacade().placerReservation(gestionBiblio.getSession(),
                reservationDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
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
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
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
            Bibliotheque.gestionBiblio.beginTransaction();
            final String idReservation = readString(tokenizer);
            final ReservationDTO reservationDTO = Bibliotheque.gestionBiblio.getReservationFacade().getReservation(gestionBiblio.getSession(),
                idReservation);
            if(reservationDTO == null) {
                throw new MissingDTOException("La reservation "
                    + idReservation
                    + " n'existe pas.");
            }
            Bibliotheque.gestionBiblio.getReservationFacade().utiliserReservation(Bibliotheque.gestionBiblio.getSession(),
                reservationDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | InvalidDTOException
            | ExistingReservationException
            | ExistingLoanException
            | InvalidLoanLimitException
            | MissingDTOException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
        }
    }

    /**
     * Affiche le menu des transactions acceptées par le système.
     * @param tokenizer recoit une string de commande en stringtokenizer
     * @throws BibliothequeException lance l'exception BibliothequeException
     */
    private static void annuler(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            Bibliotheque.gestionBiblio.beginTransaction();
            final String idReservation = readString(tokenizer);
            final ReservationDTO reservationDTO = Bibliotheque.gestionBiblio.getReservationFacade().getReservation(gestionBiblio.getSession(),
                idReservation);
            if(reservationDTO == null) {
                throw new MissingDTOException("La reservation "
                    + idReservation
                    + " n'existe pas.");
            }
            final MembreDTO membreDTO = reservationDTO.getMembreDTO();
            membreDTO.setNbPret(Integer.toString(Integer.parseInt(membreDTO.getNbPret()) + 1));
            Bibliotheque.gestionBiblio.getReservationFacade().annulerReservation(Bibliotheque.gestionBiblio.getSession(),
                reservationDTO);
            Bibliotheque.gestionBiblio.commitTransaction();
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | InvalidPrimaryKeyException
            | MissingDTOException
            | InvalidDTOClassException
            | FacadeException e) {
            Bibliotheque.LOGGER.error(" **** "
                + e.getMessage());
            Bibliotheque.gestionBiblio.rollbackTransaction();
        }
    }

}
