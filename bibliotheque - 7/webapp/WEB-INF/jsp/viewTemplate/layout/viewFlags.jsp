<%@ page import="ca.qc.collegeahuntsic.bibliotheque.servlet.action.ChangeLocaleServlet" %>

<form id="change-locale-form" method="post" action="changeLocale.do">
  <div id="flags" class="flags">
    <div id="french-flag" class="french-flag"><a href="javascript:changeLocale('change-locale-form','fr','CA');"><jsp:include page="/WEB-INF/jsp/layout/viewFrenchFlag.jsp" flush="true"/></a></div>
    <div id="english-flag" class="english-flag"><a href="javascript:changeLocale('change-locale-form','en','CA');"><jsp:include page="/WEB-INF/jsp/layout/viewEnglishFlag.jsp" flush="true"/></a></div>
    <input id="language-iso-code" class="language-iso-code" type="hidden" name="<%= ChangeLocaleServlet.LANGUAGE_ISO_CODE_PARAMETER_NAME %>"/>
    <input id="country-iso-code" class="country-iso-code" type="hidden" name="<%= ChangeLocaleServlet.COUNTRY_ISO_CODE_PARAMETER_NAME %>"/>
  </div>
</form>
