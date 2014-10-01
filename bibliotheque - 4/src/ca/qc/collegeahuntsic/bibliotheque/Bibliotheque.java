
package ca.qc.collegeahuntsic.bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.StringTokenizer;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.util.BibliothequeCreateur;
import ca.qc.collegeahuntsic.bibliotheque.util.FormatteurDate;

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
public class Bibliotheque {
    private static BibliothequeCreateur gestionBiblio;

    /**
     * Ouverture de la BD,
     * traitement des transactions et
     * fermeture de la BD.
     */
    public static void main(String argv[]) throws Exception {
        // validation du nombre de paramï¿½tres
        if(argv.length < 5) {
            System.out.println("Usage: java Biblio <serveur> <bd> <user> <password> [<fichier-transactions>]");
            System.out.println(Connexion.getServeursSupportes());
            return;
        }

        try {
            // ouverture du fichier de transactions
            InputStream sourceTransaction = Bibliotheque.class.getResourceAsStream("/"
                + argv[4]);
            try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {

                gestionBiblio = new BibliothequeCreateur(argv[0],
                    argv[1],
                    argv[2],
                    argv[3]);
                traiterTransactions(reader);
            }
        } catch(Exception e) {
            gestionBiblio.rollback();
            e.printStackTrace(System.out);
        } finally {
            gestionBiblio.close();
        }
    }

    /**
     * Traitement des transactions de la bibliothèque
     */
    static void traiterTransactions(BufferedReader reader) throws Exception {
        afficherAide();
        System.out.println("\n\n\n");
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
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        String transaction = reader.readLine();
        if(transaction != null) {
            System.out.print("> ");
            System.out.println(transaction);
        }
        /* echo si lecture dans un fichier */
        return transaction;
    }

    /**
     * Décodage et traitement d'une transaction
     */
    static void executerTransaction(StringTokenizer tokenizer) throws BibliothequeException {
        try {
            String command = tokenizer.nextToken();

            if("aide".startsWith(command)) {
                afficherAide();
            } else if("acquerir".startsWith(command)) {
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setTitre(readString(tokenizer));
                livreDTO.setAuteur(readString(tokenizer));
                livreDTO.setDateAcquisition(readDate(tokenizer));
                gestionBiblio.getLivreService().acquerir(livreDTO);
                gestionBiblio.commit();
            } else if("vendre".startsWith(command)) {
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readInt(tokenizer));
                gestionBiblio.getLivreService().vendre(livreDTO);
                gestionBiblio.commit();
            } else if("preter".startsWith(command)) {
                PretDTO pretDTO = new PretDTO();
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readInt(tokenizer));
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readInt(tokenizer));
                pretDTO.setMembreDTO(membreDTO);
                pretDTO.setLivreDTO(livreDTO);
                gestionBiblio.getPretService().commencer(pretDTO);
                gestionBiblio.commit();
            } else if("renouveler".startsWith(command)) {
                PretDTO pretDTO = new PretDTO();
                pretDTO.setIdPret(readInt(tokenizer));
                gestionBiblio.getPretService().renouveler(pretDTO);
                gestionBiblio.commit();
            } else if("retourner".startsWith(command)) {
                PretDTO pretDTO = new PretDTO();
                pretDTO.setIdPret(readInt(tokenizer));
                gestionBiblio.getPretService().retourner(pretDTO);
                gestionBiblio.commit();
            } else if("inscrire".startsWith(command)) {
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setNom(readString(tokenizer));
                membreDTO.setTelephone(readLong(tokenizer));
                membreDTO.setLimitePret(readInt(tokenizer));
                gestionBiblio.getMembreService().inscrire(membreDTO);
                gestionBiblio.commit();
            } else if("desinscrire".startsWith(command)) {
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readInt(tokenizer));
                gestionBiblio.getMembreService().desincrire(membreDTO);
                gestionBiblio.commit();
            } else if("reserver".startsWith(command)) {
                // Juste pour éviter deux timestamps de réservation strictement identiques
                Thread.sleep(1);
                ReservationDTO reservationDTO = new ReservationDTO();
                MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readInt(tokenizer));
                LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readInt(tokenizer));
                reservationDTO.setMembreDTO(membreDTO);
                reservationDTO.setLivreDTO(livreDTO);
                gestionBiblio.getReservationService().reserver(reservationDTO);
                gestionBiblio.commit();
            } else if("utiliser".startsWith(command)) {
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readInt(tokenizer));
                gestionBiblio.getReservationService().utiliser(reservationDTO);
                gestionBiblio.commit();
            } else if("annuler".startsWith(command)) {
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readInt(tokenizer));
                gestionBiblio.getReservationService().annuler(reservationDTO);
                gestionBiblio.commit();
            } else if("--".startsWith(command)) {
                // ne rien faire; c'est un commentaire
            } else {
                System.out.println("  Transactions non reconnue.  Essayer \"aide\"");
            }
        } catch(InterruptedException interruptedException) {
            System.out.println("** "
                + interruptedException.toString());
            gestionBiblio.rollback();
        } catch(ServiceException serviceException) {
            System.out.println("** "
                + serviceException.toString());
            gestionBiblio.rollback();
        } catch(BibliothequeException bibliothequeException) {
            System.out.println("** "
                + bibliothequeException.toString());
            gestionBiblio.rollback();
        }
    }

    /**
     * Affiche le menu des transactions acceptées par le système
     */
    static void afficherAide() {
        System.out.println();
        System.out.println("Chaque transaction comporte un nom et une liste d'arguments");
        System.out.println("separes par des espaces. La liste peut etre vide.");
        System.out.println(" Les dates sont en format yyyy-mm-dd.");
        System.out.println("");
        System.out.println("Les transactions sont :");
        System.out.println("  aide");
        System.out.println("  exit");
        System.out.println("  acquerir <idLivre> <titre> <auteur> <dateAcquisition>");
        System.out.println("  preter <idMembre> <idLivre>");
        System.out.println("  renouveler <idLivre>");
        System.out.println("  retourner <idLivre>");
        System.out.println("  vendre <idLivre>");
        System.out.println("  inscrire <idMembre> <nom> <telephone> <limitePret>");
        System.out.println("  desinscrire <idMembre>");
        System.out.println("  reserver <idReservation> <idMembre> <idLivre>");
        System.out.println("  utiliser <idReservation>");
        System.out.println("  annuler <idReservation>");
        //		System.out.println("  listerLivresRetard <dateCourante>");
        //		System.out.println("  listerLivresTitre <mot>");
        //		System.out.println("  listerLivres");
    }

    /**
     * Vérifie si la fin du traitement des transactions est
     * atteinte.
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
        return commande.equals("exit");
    }

    /**
     * lecture d'une chaîne de caractères de la transaction entrée à l'écran
     */
    static String readString(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**
     * lecture d'un int java de la transaction entrée à l'écran
     */
    static int readInt(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
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
     * lecture d'un long java de la transaction entrée à l'écran
     */
    static long readLong(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
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
     * lecture d'une date en format YYYY-MM-DD
     */
    static Timestamp readDate(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
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
