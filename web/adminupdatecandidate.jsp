<%-- 
    Document   : adminupdatecandidate
    Created on : Feb 21, 2024, 10:11:16 AM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@page import="evoting.dto.CandidateDetails"%>
<%@page import="evoting.dao.CandidateDAO"%>
<%@page import="java.util.ArrayList"%>

<% 
   String userid=(String)session.getAttribute("userid");
          if(userid==null){
              response.sendRedirect("accessdenied.html");
              return;
          }
          String result= (String)request.getAttribute("result");
     //   System.out.println(result);               
          StringBuffer displayBlock= new StringBuffer();
          
if(result!=null && result.equalsIgnoreCase("candidatelist"))
  {
      ArrayList<String> candidateId=(ArrayList<String>)request.getAttribute("candidateid");
       System.out.print(candidateId);  
       displayBlock.append("<option value=' '>Choose Id</option>");
      for(String c:candidateId){
          displayBlock.append("<option value='"+c+"'>"+c+"</option>");
      }
      JSONObject json=new JSONObject();      
//      display bock ko  to string isliye jar rahe kyo ke ye send ho as a string not a object
      json.put("cid",displayBlock.toString());
      out.println(json);
      System.out.println("In adminupdatecandidate");
      System.out.println(displayBlock);
   }
   else if(result!=null && result.equals("details"))
   {
       CandidateDetails cd=(CandidateDetails)request.getAttribute("candidate");
       ArrayList<String> ct=CandidateDAO.getCity(); 
      String str="<img src='data:image/jpg;base64,"+cd.getSymbol()+"'style='width:300px;height:200px;'/>";
      displayBlock.append("<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>");     
      displayBlock.append("<table><tr><th>Candidate Id:</th><td><input type='text' id='cid' value="+cd.getCandidateId()+" disabled></td></tr>");
      displayBlock.append("<tr><th>Candidate Name:</th><td><input type='text' id='cname' value="+cd.getCandidateName()+" disabled></td></tr>");
      displayBlock.append("<tr><th>City:</th><td><select id='city'>");
      for(String c:ct){
          displayBlock.append("<option value='"+c+"'>"+c+"</option>");
      }
      displayBlock.append("</select></td></tr>");
      displayBlock.append("<tr><th>Party:</th><td><input type='text' id='party' value="+cd.getParty()+"></td></tr>");
      displayBlock.append("<tr><td colspan='2'><input type='file' name='files' value='Select Image'></td></tr>");  
      displayBlock.append("<tr><th><input type='button' value='Update Candidate' onclick='updatecandidate()' id='addcnd'></th></tr>");
      displayBlock.append("<tr><th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table>");
      JSONObject json=new JSONObject();
      json.put("subdetails",displayBlock.toString());
      out.println(json);    
      
  }
           
%>
