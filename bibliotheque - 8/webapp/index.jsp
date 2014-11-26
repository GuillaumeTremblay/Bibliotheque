<%@ taglib uri="bibliotheque-config"
           prefix="config" %>

<%@ taglib uri="bibliotheque-bean"
           prefix="bean" %>

<%@ page import="ca.qc.collegeahuntsic.bibliotheque.servlet.action.ChangeLocaleServlet" %>

<!DOCTYPE html PUBLIC
               "-//W3C//DTD XHTML 1.0 Strict//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
  <head>
    <base href="<config:webappProtocol/>://<config:webappServerName/>:<config:webappPortNumber/><config:webappPath/>/"/>

    <title><bean:message key="global.welcome.displayMessage"/></title>

    <script type="text/javascript"
            src="js/core/ajax.js">
    </script>
    <script type="text/javascript"
            src="js/viewTemplate/viewTemplateIndex.js">
    </script>
  </head>

  <body onload="javascript:viewTemplateIndex('view-template-index-form');">
    <form id="view-template-index-form" method="post" action="viewTemplateIndex.do">
    </form>
  </body>
</html>
