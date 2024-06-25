 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.VoteDAO;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hp
 */
public class AddVoteControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd=null;
        HttpSession sess= request.getSession();
        String userid=(String)sess.getAttribute("userid");
        if(userid==null)
      {
       sess.invalidate();
       response.sendRedirect("accessdenied.html");
       return;
      }
      try
      {
       String candidateId=(String)request.getParameter("candidateid");
       VoteDTO vote = new VoteDTO(candidateId,userid);
       boolean result=VoteDAO.addVote(vote);
       CandidateInfo candidate=VoteDAO.getVote(candidateId);
       if(result==true) // This line checks whether the user has voted or not,if voted the details of cand. will be stored in CandidateInfo obj.
       sess.setAttribute("candidate",candidate);
         
// The candidate obj is required in the JSP and the JSP in called by JS this time so the obj can be only sent through session.
       request.setAttribute("result",result);
       rd=request.getRequestDispatcher("verifyvote .jsp");
      }
     catch(Exception ex)
     {  
       ex.printStackTrace();
       request.setAttribute("Exception",ex);
       rd=request.getRequestDispatcher("showexception.jsp");
     }
      finally
      {
         rd.forward(request, response);
      }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
