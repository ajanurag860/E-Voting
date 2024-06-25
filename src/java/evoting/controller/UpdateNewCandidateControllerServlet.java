/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDAO;
import evoting.dto.CandidateDTO;
import evoting.dto.UpdateCandidateDTO;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author hp
 */
public class UpdateNewCandidateControllerServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
        RequestDispatcher rd = null;
        HttpSession session = request.getSession();
        String userid = (String)session.getAttribute("userid");
        if(userid==null)
        {
            session.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        try
        {
           DiskFileItemFactory df = new DiskFileItemFactory();
           ServletFileUpload sfu = new ServletFileUpload(df);
           ServletRequestContext srq = new ServletRequestContext(request);
           List<FileItem> multilist = sfu.parseRequest(srq);
           ArrayList<String> al = new ArrayList<>();
           InputStream is=null;
           for(FileItem x: multilist)
           {
               if(x.isFormField())
               {
                   al.add(x.getString());
                   System.out.println(x.getString());
               }
               else
               {
                   is = x.getInputStream();
                   
               }
               
           }
           UpdateCandidateDTO ud = new UpdateCandidateDTO();
           ud.setCandidateid(userid);
           ud.setSymbol(is);
           ud.setParty(al.get(0));
           ud.setCity(al.get(1));
           ud.setCandidateid(al.get(2));
           
           
           boolean result = CandidateDAO.updateCandidate(ud);
           request.setAttribute("result", result);
           rd = request.getRequestDispatcher("updateresponse.jsp");
            }
            catch (Exception ex)
            {
                System.out.println("Exception in UpdateNewCandidateControllerServlet");
                ex.printStackTrace();
            }
            finally{
               if(rd!=null)
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
