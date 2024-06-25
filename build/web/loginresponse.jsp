<%-- 
    Document   : loginrespose.jsp
    Created on : 21 Apr, 2023, 12:24:21 AM
    Author     : Anurag
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String userid=(String)request.getAttribute("userid");
    String result=(String)request.getAttribute("result");
//    usesrid,result not null means user login kar gaya ha
    if(userid!=null && result!=null){
//        
        HttpSession sess=request.getSession();
        sess.setAttribute("userid", userid);
//        is line se ye pta chalega jo user aya ha wo login karke aya ya direct aya ha
        String url="";
        if(result.equalsIgnoreCase("Admin")){
//            session id send karega login.js ko
             url="AdminControllerServlet;jsessionid="+sess.getId();
             
        }
        else{
            url="VotingControllerServlet;jsessionid="+sess.getId();
        }
        
        out.println(url);     
    }
    else
        out.println("error");
    
%>
