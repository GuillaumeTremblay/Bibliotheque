<%@ taglib uri="struts-html"
           prefix="html" %>

<html:xhtml/>
<div id="livre" class="livre">
  <div id="livre-header" class="livre-header"><jsp:include page="/common/viewTemplate/layout/viewHeader.do" flush="true"/></div>
  <div id="livre-body" class="livre-body"><jsp:include page="/livreManagement/viewLivre/layout/viewBody.do" flush="true"/></div>
  <div id="livre-footer" class="livre-footer"><jsp:include page="/common/viewTemplate/layout/viewFooter.do" flush="true"/></div>
</div>
