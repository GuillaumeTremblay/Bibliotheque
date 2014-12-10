// Fichier ViewLivre.java
// Auteur : 201205090
// Date de création : 2014-12-10

package ca.qc.collegeahuntsic.bibliotheque.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.struts.form.LivreActionForm;
import ca.qc.collegeahuntsic.bibliotheque.util.FormatteurDate;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.hibernate.SessionFactory;

/**
 * TODO Auto-generated class javadoc.
 *
 * @author Chou Huynh
 */
public class ViewLivreAction extends BibliothequeAction {

    private static final Log LOGGER = LogFactory.getLog(ViewAllLivresAction.class);

    private ILivreFacade livreFacade;

    /**
     * 
     * TODO Auto-generated constructor javadoc.
     *
     * @param sessionFactory The session factory to use.
     * @param livreFacade The livre facade to use.
     */
    public ViewLivreAction(SessionFactory sessionFactory,
        ILivreFacade livreFacade) {
        super(sessionFactory);
        setLivreFacade(livreFacade);
    }

    /**
     * Getter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @return La variable d'instance <code>this.livreFacade</code>
     */
    public ILivreFacade getLivreFacade() {
        return this.livreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @param livreFacade La valeur à utiliser pour la variable d'instance <code>this.livreFacade</code>
     */
    public void setLivreFacade(ILivreFacade livreFacade) {
        this.livreFacade = livreFacade;
    }

    /**
     * .
     * 
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
            final String idLivre = request.getParameter(LivreDTO.ID_LIVRE_COLUMN_NAME).toString();
            final LivreDTO livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            livreActionForm.setIdLivre(livreDTO.getIdLivre());
            livreActionForm.setAuteur(livreDTO.getAuteur());
            livreActionForm.setTitre(livreDTO.getTitre());
            livreActionForm.setDateAcquisition(FormatteurDate.stringValue(livreDTO.getDateAcquisition()));
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException e) {
            try {
                rollbackTransaction();
            } catch(BibliothequeException e1) {
                ViewLivreAction.LOGGER.error(e1);
            }
            ViewLivreAction.LOGGER.error(e);
            saveMessage(request,
                new ActionMessages(),
                Constants.LIVRE_MANAGEMENT_VIEW_LIVRE_FAILED_MESSAGE_ID,
                Constants.ZERO_LENGTH_STRING_ARRAY);
            forwardName = Constants.FAILURE;
        }
        return mapping.findForward(forwardName);
    }
}
