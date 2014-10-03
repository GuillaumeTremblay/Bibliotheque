// Fichier Facade.java
// Auteur : Samuel Filion-Normandeau
// Date de création : 2014-10-03

package ca.qc.collegeahuntsic.bibliotheque.facade.implementations;

import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IPretFacade;

public class PretFacade extends Facade implements IPretFacade{

	private IPretSertvice pretService;
	
	public PretFacade (IPretService pretService) throws InvalidServiceException {
		
		super();
		if (pretService == null) {throw new InvalidServiceException("Le service de Prêt n'existe pas");}
		setPretService(pretService);
		
	} 
	
	private void setPretService (IPretService pretService) {
		this.pretService = pretService;
	}
	
}
