<%-- 
    Document   : updateresponse
    Created on : Mar 16, 2024, 8:59:54 AM
    Author     : hp
--%>

<%
    String userid = (String)session.getAttribute("userid");
    if(userid==null)
    {
        session.invalidate();
        response.sendRedirect("accessdenied.html");
        return;
    }
    boolean result =(boolean) request.getAttribute("result");
    if(result)
        out.println("success");
    else
        out.println("failed");
    
    
    %>