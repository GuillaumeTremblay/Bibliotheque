<%@ taglib uri="bibliotheque-bean"
           prefix="bean" %>

<%@ page import="ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO" %>
<%@ page import="ca.qc.collegeahuntsic.bibliotheque.servlet.action.GetLivreServlet" %>
<%@ page import="ca.qc.collegeahuntsic.bibliotheque.servlet.action.SaveLivreServlet" %>
<%@ page import="ca.qc.collegeahuntsic.bibliotheque.util.FormatteurDate" %>

<% LivreDTO livreDTO = (LivreDTO) request.getAttribute(GetLivreServlet.LIVRE_ATTRIBUTE_NAME);
   Boolean isSaveSuccessful = (Boolean) request.getAttribute(SaveLivreServlet.SAVE_LIVRE_SUCCESSFUL);
   if(isSaveSuccessful != null) { %>
     <bean:message key="errors.header"/>
     <bean:message key="errors.prefix"/>
     <bean:message key="viewLivre.layout.viewBody.saveSuccessful.displayMessage"/>
     <bean:message key="errors.suffix"/>
     <bean:message key="errors.footer"/>
<% } %>
<div id="body" class="body">
  <form id="save-livre-form" method="post" action="saveLivre.do">
    <div id="id-livre" class="id-livre"><bean:message key="viewTemplate.layout.viewBody.idLivre.displayName"/> <%= livreDTO.getIdLivre() %><input id="livre-id-livre" class="livre-id-livre" type="hidden" name="<%= LivreDTO.ID_LIVRE_COLUMN_NAME %>" value="<%= livreDTO.getIdLivre() %>"/></div>
    <div id="titre" class="titre"><bean:message key="viewTemplate.layout.viewBody.titre.displayName"/> <input id="livre-titre" class="livre-titre" type="text" name="<%= LivreDTO.TITRE_COLUMN_NAME %>" value="<%= livreDTO.getTitre() %>" maxlength="100"/></div>
    <div id="auteur" class="auteur"><bean:message key="viewTemplate.layout.viewBody.auteur.displayName"/> <input id="livre-auteur" class="livre-auteur" type="text" name="<%= LivreDTO.AUTEUR_COLUMN_NAME %>" value="<%= livreDTO.getAuteur() %>" maxlength="100"/></div>
    <div id="date-acquisition" class="date-acquisition"><bean:message key="viewTemplate.layout.viewBody.dateAcquisition.displayName"/> <input id="livre-date-acquisition" class="livre-date-acquisition" type="text" name="<%= LivreDTO.DATE_ACQUISITION_COLUMN_NAME %>" value="<%= FormatteurDate.stringValue(livreDTO.getDateAcquisition()) %>" maxlength="10"/></div>
    <div id="submit" class="submit"><input id="livre-submit" class="livre-submit" type="submit" value="OK"/></div>
    <div id="retour" class="retour"><a href="javascript:viewTemplateIndex('save-livre-form');"><jsp:include page="/WEB-INF/jsp/layout/viewRetour.jsp"/></a></div>
    <div><img src="img/transparent_pixel.gif"/></div>
  </form>
</div>
