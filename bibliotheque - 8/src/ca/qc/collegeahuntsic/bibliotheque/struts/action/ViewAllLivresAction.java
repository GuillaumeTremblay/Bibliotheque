// Fichier ViewAllLivresAction.java
// Auteur : 201205090
// Date de création : 2014-12-03

package ca.qc.collegeahuntsic.bibliotheque.struts.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.struts.form.LivreActionForm;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.implementations.LivreFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.hibernate.SessionFactory;

/**
 * The view all livres action.
 *
 * @author Chou Huynh
 */
public class ViewAllLivresAction extends BibliothequeAction {

    private static final Log LOGGER = LogFactory.getLog(ViewAllLivresAction.class);

    private LivreFacade livreFacade;

    /**
     * Default constructor.
     * @param sessionFactory The session factory to use.
     * @param livreFacade The livre facade to use.
     */
    public ViewAllLivresAction(SessionFactory sessionFactory,
        LivreFacade livreFacade) {
        super(sessionFactory);
        setLivreFacade(livreFacade);
    }

    /**
     * Getter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @return La variable d'instance <code>this.livreFacade</code>
     */
    private LivreFacade getLivreFacade() {
        return this.livreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @param livreFacade La valeur à utiliser pour la variable d'instance <code>this.livreFacade</code>
     */
    private void setLivreFacade(LivreFacade livreFacade) {
        this.livreFacade = livreFacade;
    }

    /**
     * Display all livres.
     * @param mapping The mapping to use
     * @param form The action form to use
     * @param request The request to use
     * @param response The response to use
     * @return The appropriate action forward
     * @throws Exception If an error occurs
    */
    @Override
    public ActionForward execute(ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String forwardName = Constants.SUCCESS;
        final LivreActionForm livreActionForm = (LivreActionForm) form;
        try {
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            commitTransaction();
            livreActionForm.setLivres(livres);
        } catch(
            BibliothequeException
            | InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException e) {
            try {
                rollbackTransaction();
            } catch(BibliothequeException e1) {
                ViewAllLivresAction.LOGGER.error(e1);
            }
            ViewAllLivresAction.LOGGER.error(e);
            saveMessage(request,
                new ActionMessages(),
                Constants.LIVRE_MANAGEMENT_VIEW_ALL_LIVRES_FAILED_MESSAGE_ID,
                Constants.ZERO_LENGTH_STRING_ARRAY);
            forwardName = Constants.FAILURE;
        }
        return mapping.findForward(forwardName);
    }
}
