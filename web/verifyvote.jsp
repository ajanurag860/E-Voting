<%-- 
    Document   : verifyvote
    Created on : Mar 9, 2024, 9:32:44 AM
    Author     : hp
--%>

<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
      session.invalidate(); 
      response.sendRedirect("accessdenied.html");
      return;
    }
    boolean result=(boolean)request.getAttribute("result");
    if(result==true){
      out.println("success");
    }
    else
    {
       out.println("failed");
    }   
%>
