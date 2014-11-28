<%@ taglib uri="struts-html"
           prefix="html" %>

<html:xhtml/>
<div id="template" class="template">
  <div id="template-header" class="template-header"><jsp:include page="/common/viewTemplate/layout/viewHeader.do" flush="true"/></div>
  <div id="template-body" class="template-body"><jsp:include page="/common/viewTemplate/layout/viewBody.do" flush="true"/></div>
  <div id="template-footer" class="template-footer"><jsp:include page="/common/viewTemplate/layout/viewFooter.do" flush="true"/></div>
</div>
