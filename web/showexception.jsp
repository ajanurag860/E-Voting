<%-- 
    Document   : showexception
    Created on : Jul 24, 2023, 8:54:10 AM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
 Exception ex =(Exception)request.getAttribute("Exception");
 System.out.println("Exception is"+ex);
 out.println("Some Error Occured :"+ex.getMessage());
 %>   
