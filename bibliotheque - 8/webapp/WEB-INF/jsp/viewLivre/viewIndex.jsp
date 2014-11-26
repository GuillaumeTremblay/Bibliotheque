<%@ taglib uri="bibliotheque-config"
           prefix="config" %>

<%@ taglib uri="bibliotheque-bean"
           prefix="bean" %>

<!DOCTYPE html PUBLIC
               "-//W3C//DTD XHTML 1.0 Strict//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
  <head>
    <base href="<config:webappProtocol/>://<config:webappServerName/>:<config:webappPortNumber/><config:webappPath/>/"/>

    <title><bean:message key="global.title.displayMessage"/></title>

    <link rel="stylesheet" href="css/core/stylesheet.css" type="text/css"/>
    <link rel="stylesheet" href="css/layout/stylesheet.css" type="text/css"/>
    <link rel="stylesheet" href="css/viewLivre/stylesheet.css" type="text/css"/>
    <link rel="stylesheet" href="css/viewTemplate/stylesheet.css" type="text/css"/>

    <script type="text/javascript"
            src="js/core/ajax.js">
    </script>
    <script type="text/javascript"
            src="js/viewLivre/getLivre.js">
    </script>
    <script type="text/javascript"
            src="js/viewTemplate/viewTemplateIndex.js">
    </script>
    <script type="text/javascript"
            src="js/viewTemplate/changeLocale.js">
    </script>
  </head>

  <body>
    <div id="index-template" class="index-template"><jsp:include page="/WEB-INF/jsp/viewLivre/layout/viewLivre.jsp" flush="true"/></div>
  </body>
</html>
