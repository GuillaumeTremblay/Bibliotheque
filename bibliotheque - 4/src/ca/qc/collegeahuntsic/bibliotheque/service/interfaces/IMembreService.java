// Fichier Facade.java
// Auteur : Samuel Filion-Normandeau
// Date de création : 2014-10-03

package ca.qc.collegeahuntsic.bibliotheque.service.interfaces;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;

public interface IMembreService extends IService{

	/**
     * Ajoute un nouveau membre dans la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param membreDTO Le membre à ajouter
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du membre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
	void add(Connexion connexion, MembreDTO membreDTO)
			throws InvalidHibernateSessionException, InvalidDTOException,
			InvalidDTOClassException, InvalidPrimaryKeyRequestException,
			ServiceException;

	MembreDTO get(Connexion connexion, String idMembre)
			throws InvalidHibernateSessionException,
			InvalidPrimaryKeyException, ServiceException;

}
