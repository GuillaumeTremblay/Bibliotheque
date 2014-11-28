<%@ taglib uri="struts-html"
           prefix="html" %>

<html:xhtml/>
<html:form action="event/changeLocale" method="post">
  <div id="flags" class="flags">
    <div id="french-flag" class="french-flag"><html:link href="javascript:changeLocale('localeActionForm','fr','CA');"><jsp:include page="/common/layout/viewFrenchFlag.do" flush="true"/></html:link></div>
    <div id="english-flag" class="english-flag"><html:link href="javascript:changeLocale('localeActionForm','en','CA');"><jsp:include page="/common/layout/viewEnglishFlag.do" flush="true"/></html:link></div>
    <html:hidden property="languageIsoCode" styleId="language-iso-code" styleClass="language-iso-code"/>
    <html:hidden property="countryIsoCode" styleId="country-iso-code" styleClass="country-iso-code"/>
  </div>
</html:form>
