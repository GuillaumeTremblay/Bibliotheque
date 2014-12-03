<%@ taglib uri="struts-html"
           prefix="html" %>

<%@ taglib uri="struts-bean"
           prefix="bean" %>

<%@ taglib uri="struts-logic"
           prefix="logic" %>

<html:xhtml/>
<html:messages id="message" message="true">
  <bean:message key="errors.header"/>
  <bean:message key="errors.prefix"/>
  <bean:write name="message" scope="page" filter="false"/>
  <bean:message key="errors.suffix"/>
  <bean:message key="errors.footer"/>
</html:messages>
<div id="body" class="body">
  <logic:notEmpty name="livreActionForm" property="livres" scope="request">
  <logic:iterate id="livreDTO" name="livreActionForm" property="livres" scope="request" type="ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO">
    <div id="titre" class="titre"><html:link href="livreManagement/viewLivre/event/viewLivre.do" paramId="idLivre" paramName="livreDTO" paramProperty="idLivre" paramScope="page"><bean:write name="livreDTO" property="titre" scope="page"/></html:link></div>
    <div><img src="img/transparent_pixel.gif"/></div>
  </logic:iterate>
  </logic:notEmpty>
</div>
