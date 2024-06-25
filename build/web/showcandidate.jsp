<%-- 
    Document   : showcandidate
    Created on : Mar 7, 2024, 4:51:13 PM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,evoting.dto.CandidateInfo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <script src="jsscript/vote .js"></script>
        <script src="jsscript/jquery.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <link href="stylesheet/showcandidate.css" rel="stylesheet">
        <title>Show candidate</title>
    </head>
    <body>
        <%
         RequestDispatcher rd=null;
         HttpSession sess=request.getSession();
         String userid=(String)sess.getAttribute("userid");
         if(userid==null){
             sess.invalidate();
             response.sendRedirect("accessdenied.html");
             return;
         }   
        StringBuffer displayBlock=new StringBuffer("");
             displayBlock.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br>"+
              "<div class='subcandidate'>Whom do you want to vote ?</div>"
              +"<div class='logout'<a href='login.html'>logout</a></div>"
              +"</div><div class='buttons'>");
             ArrayList<CandidateInfo> candidateList =(ArrayList<CandidateInfo>)request.getAttribute("candidateList");
             for(CandidateInfo c:candidateList)
             {
              displayBlock.append("<input type='radio' name='flat' id='"+c.getCandidateId()+"' value='"+c.getCandidateId()+"' onclick='addvote()'");
              displayBlock.append("<label for='"+c.getCandidateId()+"'> <img src='data:image/jpg;base64,"+c.getSymbol()+"'style='width:300px;height:200px;'/></label>");
              displayBlock.append("<br><div class='candidateprofile'><p>CandidateId:"+c.getCandidateId()+"<br>");
              displayBlock.append("Candidate Name:"+c.getCandidateName()+"<br>");
              displayBlock.append("Party:"+c.getParty()+"<br></div>");
             }
           // System.out.println("al size:"+candidateList.size());
             out.println(displayBlock);
        %>    
    </body>   
        