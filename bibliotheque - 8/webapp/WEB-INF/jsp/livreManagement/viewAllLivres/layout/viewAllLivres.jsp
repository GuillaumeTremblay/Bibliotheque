<%@ taglib uri="struts-html"
           prefix="html" %>

<html:xhtml/>
<div id="all-livres" class="all-livres">
  <div id="all-livres-header" class="all-livres-header"><jsp:include page="/common/viewTemplate/layout/viewHeader.do" flush="true"/></div>
  <div id="all-livres-body" class="all-livres-body"><jsp:include page="/livreManagement/viewAllLivres/layout/viewBody.do" flush="true"/></div>
  <div id="all-livres-footer" class="all-livres-footer"><jsp:include page="/common/viewTemplate/layout/viewFooter.do" flush="true"/></div>
</div>
