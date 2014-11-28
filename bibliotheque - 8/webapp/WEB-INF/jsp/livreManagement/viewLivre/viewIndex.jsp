<%@ taglib uri="struts-html"
           prefix="html" %>

<%@ taglib uri="struts-bean"
           prefix="bean" %>

<%@ taglib uri="bibliotheque-config"
           prefix="config" %>

<!DOCTYPE html PUBLIC
               "-//W3C//DTD XHTML 1.0 Strict//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html:html xhtml="true" lang="true">
  <head>
    <base href="<config:webappProtocol/>://<config:webappServerName/>:<config:webappPortNumber/><config:webappPath/>/"/>

    <title><bean:message key="global.title.displayMessage"/></title>

    <link rel="stylesheet" href="css/core/stylesheet.css" type="text/css"/>
    <link rel="stylesheet" href="css/common/layout/stylesheet.css" type="text/css"/>
    <link rel="stylesheet" href="css/common/viewTemplate/stylesheet.css" type="text/css"/>
    <link rel="stylesheet" href="css/livreManagement/viewLivre/stylesheet.css" type="text/css"/>

    <script type="text/javascript"
            src="js/core/ajax.js">
    </script>
    <script type="text/javascript"
            src="js/common/viewTemplate/changeLocale.js">
    </script>
  </head>

  <body>
    <div id="index-livre" class="index-livre"><jsp:include page="/livreManagement/viewLivre/layout/viewLivre.do" flush="true"/></div>
  </body>
</html:html>
