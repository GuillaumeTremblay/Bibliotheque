package ca.qc.collegeahuntsic.bibliotheque;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.StringTokenizer;
import ca.qc.collegeahuntsic.bibliotheque.DB.ConnexionDb;
import ca.qc.collegeahuntsic.bibliotheque.exception.BiblioException;
import ca.qc.collegeahuntsic.bibliotheque.service.FormatDateSrv;
import ca.qc.collegeahuntsic.bibliotheque.service.GestionBibliothequeSrv;

// TODO: Auto-generated Javadoc
/**
 * Interface du système de gestion d'une bibliothèque
 *
 * Ce programme permet d'appeler les transactions de base d'une
 * bibliothèque.  Il gère des livres, des membres et des
 * réservations. Les données sont conservées dans une base de
 * données relationnelles accédée avec JDBC. Pour une liste des
 * transactions traitées, voir la méthode afficherAide().
 *
 * Paramètres
 * 0- site du serveur SQL ("local", "distant" ou "postgres")
 * 1- nom de la BD
 * 2- user id pour établir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */
public class Biblio {
    
    /** The gestion biblio. */
    private static GestionBibliothequeSrv gestionBiblio;

    /** The lecture au clavier. */
    private static boolean lectureAuClavier;

    /**
     * Ouverture de la BD,
     * traitement des transactions et
     * fermeture de la BD.
     *
     * @param argv the arguments
     * @throws Exception the exception
     */
    public static void main(String argv[]) throws Exception {
        // validation du nombre de paramétres
        if(argv.length < 4) {
            System.out.println("Usage: java Biblio <serveur> <bd> <user> <password> [<fichier-transactions>]");
            System.out.println(ConnexionDb.serveursSupportes());
            return;
        }

        try {
            // ouverture du fichier de transactions
            // s'il est spécifié comme argument
            lectureAuClavier = true;
            InputStream sourceTransaction = System.in;
            if(argv.length > 4) {
                sourceTransaction = new FileInputStream(argv[4]);
                lectureAuClavier = false;
            }

            gestionBiblio = new GestionBibliothequeSrv(argv[0],
                argv[1],
                argv[2],
                argv[3]);

            try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {

                traiterTransactions(reader);
            }

        } catch(Exception e) {
            e.printStackTrace(System.out);
        } finally {
            gestionBiblio.fermer();
        }
    }

    /**
     * Traitement des transactions de la bibliothéque.
     *
     * @param reader the reader
     * @throws Exception the exception
     */
    static void traiterTransactions(BufferedReader reader) throws Exception {
        afficherAide();
        String transaction = lireTransaction(reader);
        while(!finTransaction(transaction)) {
            /* découpage de la transaction en mots*/
            StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            if(tokenizer.hasMoreTokens()) {
                executerTransaction(tokenizer);
            }
            transaction = lireTransaction(reader);
        }
    }

    /**
     * Lecture d'une transaction.
     *
     * @param reader the reader
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        System.out.print("> ");
        String transaction = reader.readLine();
        /* echo si lecture dans un fichier */
        if(!lectureAuClavier) {
            System.out.println(transaction);
        }
        return transaction;
    }

    /**
     * Décodage et traitement d'une transaction.
     *
     * @param tokenizer the tokenizer
     * @throws Exception the exception
     */
    static void executerTransaction(StringTokenizer tokenizer) throws Exception {
        try {
            String command = tokenizer.nextToken();

            /* ******************* */
            /*         HELP        */
            /* ******************* */
            if("aide".startsWith(command)) {
                afficherAide();
            } else if("acquerir".startsWith(command)) {
                gestionBiblio.gestionLivre.acquerir(readInt(tokenizer) /* idLivre */,
                    readString(tokenizer) /* titre */,
                    readString(tokenizer) /* auteur */,
                    readDate(tokenizer) /* dateAcquisition */);
            } else if("vendre".startsWith(command)) {
                gestionBiblio.gestionLivre.vendre(readInt(tokenizer) /* idLivre */);
            } else if("preter".startsWith(command)) {
                gestionBiblio.gestionPret.preter(readInt(tokenizer) /* idLivre */,
                    readInt(tokenizer) /* idMembre */,
                    readDate(tokenizer) /* dateEmprunt */);
            } else if("renouveler".startsWith(command)) {
                gestionBiblio.gestionPret.renouveler(readInt(tokenizer) /* idLivre */,
                    readDate(tokenizer) /* dateRenouvellement */);
            } else if("retourner".startsWith(command)) {
                gestionBiblio.gestionPret.retourner(readInt(tokenizer) /* idLivre */,
                    readDate(tokenizer) /* dateRetour */);
            } else if("inscrire".startsWith(command)) {
                gestionBiblio.gestionMembre.inscrire(readInt(tokenizer) /* idMembre */,
                    readString(tokenizer) /* nom */,
                    readLong(tokenizer) /* tel */,
                    readInt(tokenizer) /* limitePret */);
            } else if("desinscrire".startsWith(command)) {
                gestionBiblio.gestionMembre.desinscrire(readInt(tokenizer) /* idMembre */);
            } else if("reserver".startsWith(command)) {
                gestionBiblio.gestionReservation.reserver(readInt(tokenizer) /* idReservation */,
                    readInt(tokenizer) /* idLivre */,
                    readInt(tokenizer) /* idMembre */,
                    readDate(tokenizer) /* dateReservation */);
            } else if("prendreRes".startsWith(command)) {
                gestionBiblio.gestionReservation.prendreRes(readInt(tokenizer) /* idReservation */,
                    readDate(tokenizer) /* dateReservation */);
            } else if("annulerRes".startsWith(command)) {
                gestionBiblio.gestionReservation.annulerRes(readInt(tokenizer) /* idReservation */);
            } else if("listerLivres".startsWith(command)) {
                gestionBiblio.gestionInterrogation.listerLivres();
            } else if("listerLivresTitre".startsWith(command)) {
                gestionBiblio.gestionInterrogation.listerLivresTitre(readString(tokenizer) /* mot */);
                /* *********************           */
                /* commentaire : ligne débutant par --   */
                /* *********************           */
                /* ***********************   */
                /* TRANSACTION NON RECONNUEE */
                /* ***********************   */
            } else {
                System.out.println("  Transactions non reconnue.  Essayer \"aide\"");
            }
        } catch(BiblioException e) {
            System.out.println("** "
                + e.toString());
        }
    }

    /**
     *  Affiche le menu des transactions acceptées par le systéme.
     */
    static void afficherAide() {
        System.out.println();
        System.out.println("Chaque transaction comporte un nom et une liste d'arguments");
        System.out.println("separes par des espaces. La liste peut etre vide.");
        System.out.println(" Les dates sont en format yyyy-mm-dd.");
        System.out.println("");
        System.out.println("Les transactions sont:");
        System.out.println("  aide");
        System.out.println("  exit");
        System.out.println("  acquerir <idLivre> <titre> <auteur> <dateAcquisition>");
        System.out.println("  preter <idLivre> <idMembre> <dateEmprunt>");
        System.out.println("  renouveler <idLivre> <dateRenouvellement>");
        System.out.println("  retourner <idLivre> <dateRetour>");
        System.out.println("  vendre <idLivre>");
        System.out.println("  inscrire <idMembre> <nom> <telephone> <limitePret>");
        System.out.println("  desinscrire <idMembre>");
        System.out.println("  reserver <idReservation> <idLivre> <idMembre> <dateReservation>");
        System.out.println("  prendreRes <idReservation> <dateEmprunt>");
        System.out.println("  annulerRes <idReservation>");
        System.out.println("  listerLivresRetard <dateCourante>");
        System.out.println("  listerLivresTitre <mot>");
        System.out.println("  listerLivres");
    }

    /**
     * Vérifie si la fin du traitement des transactions est
     * atteinte.
     *
     * @param transaction the transaction
     * @return true, if successful
     */
    static boolean finTransaction(String transaction) {
        /* fin de fichier atteinte */
        if(transaction == null) {
            return true;
        }

        StringTokenizer tokenizer = new StringTokenizer(transaction,
            " ");

        /* ligne ne contenant que des espaces */
        if(!tokenizer.hasMoreTokens()) {
            return false;
        }

        /* commande "exit" */
        String commande = tokenizer.nextToken();
        if(commande.equals("exit")) {
            return true;
        }
        return false;
    }

    /**
     *  lecture d'une chaéne de caractéres de la transaction entrée é l'écran.
     *
     * @param tokenizer the tokenizer
     * @return the string
     * @throws BiblioException the biblio exception
     */
    static String readString(StringTokenizer tokenizer) throws BiblioException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BiblioException("autre paramétre attendu");
    }

    /**
     * lecture d'un int java de la transaction entrée é l'écran.
     *
     * @param tokenizer the tokenizer
     * @return the int
     * @throws BiblioException the biblio exception
     */
    static int readInt(StringTokenizer tokenizer) throws BiblioException {
        if(tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Integer.valueOf(token).intValue();
            } catch(NumberFormatException e) {
                throw new BiblioException("Nombre attendu é la place de \""
                    + token
                    + "\"");
            }
        }
        throw new BiblioException("autre paramétre attendu");
    }

    /**
     * lecture d'un long java de la transaction entrée à l'écran.
     *
     * @param tokenizer the tokenizer
     * @return the long
     * @throws BiblioException the biblio exception
     */
    static long readLong(StringTokenizer tokenizer) throws BiblioException {
        if(tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Long.valueOf(token).longValue();
            } catch(NumberFormatException e) {
                throw new BiblioException("Nombre attendu à la place de \""
                    + token
                    + "\"");
            }
        }
        throw new BiblioException("autre paramétre attendu");
    }

    /**
     * lecture d'une date en format YYYY-MM-DD.
     *
     * @param tokenizer the tokenizer
     * @return the string
     * @throws BiblioException the biblio exception
     */
    static String readDate(StringTokenizer tokenizer) throws BiblioException {
        if(tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                FormatDateSrv.convertirDate(token);
                return token;
            } catch(ParseException e) {
                throw new BiblioException("Date en format YYYY-MM-DD attendue à la place  de \""
                    + token
                    + "\"");
            }
        }
        throw new BiblioException("autre paramétre attendu");
    }
}//class
