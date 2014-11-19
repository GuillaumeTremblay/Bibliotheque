<%@ taglib uri="bibliotheque-bean"
           prefix="bean" %>

<div id="body" class="body">
  <bean:message key="global.viewError.displayMessage"/>
  <form id="error-page-form" method="post" action="viewTemplateIndex.do">
    <div id="retour" class="retour"><a href="javascript:viewTemplateIndex('error-page-form');"><jsp:include page="/WEB-INF/jsp/layout/viewRetour.jsp"/></a></div>
  </form>
</div>
