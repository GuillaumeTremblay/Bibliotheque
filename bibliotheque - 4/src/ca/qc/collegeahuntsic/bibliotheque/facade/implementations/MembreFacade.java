// Fichier Facade.java
// Auteur : Samuel Filion-Normandeau
// Date de création : 2014-10-03

package ca.qc.collegeahuntsic.bibliotheque.facade.implementations;

import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IMembreFacade;

public class MembreFacade extends Facade implements IMembreFacade{
	
	private IMembreService membreService;
	
	public MembreFacade (IMembreService membreService) throws InvalidServiceException {
        super();
        if(membreService == null) {
            throw new InvalidServiceException("Le service de membres ne peut être null");
        }
        setMembreService(membreService);
    }

	private void setMembreService (IMembreService membreService) {
		this.membreService = membreService;
	}
	
}
