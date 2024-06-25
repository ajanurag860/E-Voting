<%-- 
    Document   : registrationresponse
    Created on : Jul 24, 2023, 8:36:21 AM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
boolean userfound=(boolean)request.getAttribute("userfound");
boolean result=(boolean)request.getAttribute("result");
 if(userfound==true)
      out.println("uap");
 else if(result==true)
      out.println("success");
 else
     out.println("error" + result + userfound); 
%>    

