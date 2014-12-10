<%@ taglib uri="struts-html"
           prefix="html" %>

<%@ taglib uri="struts-bean"
           prefix="bean" %>

<html:xhtml/>
<html:messages id="message" message="true">
  <bean:message key="errors.header"/>
  <bean:message key="errors.prefix"/>
  <bean:write name="message" scope="page" filter="false"/>
  <bean:message key="errors.suffix"/>
  <bean:message key="errors.footer"/>
</html:messages>
<div id="body" class="body">
  <html:form action="event/saveLivre" method="post">
    <div id="id-livre" class="id-livre"><bean:message key="livreManagement.viewLivre.layout.viewBody.idLivre.displayName"/> <bean:write name="livreActionForm" property="idLivre" scope="request"/><html:hidden property="idLivre"/></div>
    <div id="titre" class="titre"><bean:message key="livreManagement.viewLivre.layout.viewBody.titre.displayName"/> <html:text property="titre" maxlength="100" styleId="livre-titre" styleClass="livre-titre"/></div>
    <div id="auteur" class="auteur"><bean:message key="livreManagement.viewLivre.layout.viewBody.auteur.displayName"/> <html:text property="auteur" maxlength="100" styleId="livre-auteur" styleClass="livre-auteur"/></div>
    <div id="date-acquisition" class="date-acquisition"><bean:message key="livreManagement.viewLivre.layout.viewBody.dateAcquisition.displayName"/> <html:text property="dateAcquisition" maxlength="10" styleId="livre-date-acquisition" styleClass="livre-date-acquisition"/></div>
    <div id="submit" class="submit"><html:submit value="OK" styleId="livre-submit" styleClass="livre-submit"/></div>
    <div id="retour" class="retour"><html:link href="livreManagement/viewAllLivres/event/viewAllLivres.do"><jsp:include page="/common/layout/viewRetour.do" flush="true"/></html:link></div>
    <div><img src="img/transparent_pixel.gif"/></div>
  </html:form>
</div>
