<%@ page import="java.util.List" %>
<%@ page import="ca.qc.collegeahuntsic.bibliotheque.servlet.action.GetAllLivresServlet" %>
<%@ page import="ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO" %>

<jsp:include page="/getAllLivres.do" flush="true"/>

<div id="body" class="body">
<% List<LivreDTO> livres = (List<LivreDTO>) request.getAttribute(GetAllLivresServlet.LIVRES_ATTRIBUTE_NAME);
   for(LivreDTO livreDTO : livres) { %>
     <form id="get-livre-<%= livreDTO.getIdLivre() %>-form" method="post" action="getLivre.do">
       <div id="titre" class="titre"><a href="javascript:getLivre('get-livre-<%= livreDTO.getIdLivre() %>-form');"><%= livreDTO.getTitre() %></a></div>
       <div><img src="img/transparent_pixel.gif"/></div>
       <input id="id-livre" class="id-livre" type="hidden" name="<%= LivreDTO.ID_LIVRE_COLUMN_NAME %>" value="<%= livreDTO.getIdLivre() %>"/>
     </form>
<% }
%>
</div>
