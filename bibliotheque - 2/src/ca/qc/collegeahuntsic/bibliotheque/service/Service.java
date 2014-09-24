// Fichier Service.java
// Auteur : Chou Huynh
// Date de création : 2014-09-23

package ca.qc.collegeahuntsic.bibliotheque.service;

import java.io.Serializable;

/**
 * Classe de base pour tous les services.
 *
 * @author Chou Huynh
 */
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Crée un Service à partir d'une connexion à la base de données.
     */
    public Service() {
        super();
    }
}