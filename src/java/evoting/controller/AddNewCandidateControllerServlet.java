/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDAO;
import evoting.dto.CandidateDTO;
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
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author hp
 */
public class AddNewCandidateControllerServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            RequestDispatcher rd=null;
            try
            {
              DiskFileItemFactory df = new DiskFileItemFactory(); // temporarily secondary disk mein store krti h data ko in case bulky data ho to. 
              ServletFileUpload  sfu = new ServletFileUpload(df); // iska obj isliye bnaya kyuki parseRequest method iske pas h jo data ko convert krega AL mein
              ServletRequestContext srq =new ServletRequestContext(request); // data retrieve krne k liye jo AJAX se aaya h (just like request.getParameter()) aur link bhi kia h isko HttpServletRequest k saath. 
              List<FileItem> multiList=sfu.parseRequest(srq);//ajax se data FileItem type mein aata h jisko arraylist mein store krr rhe h
              ArrayList<String> objValues = new ArrayList<>();// to send data to dto
              InputStream inp=null;  
               for(FileItem fit:multiList){
                   if(fit.isFormField()){
                       //data is text 
                       String fname= fit.getFieldName();// to get the field name and to check whether data is adhar_no,c_no,name etc..
                       String value = fit.getString(); // returns value
                       System.out.println("Inside if");
                       System.out.println(fname+":"+value);
                       objValues.add(value);
                   }
                   else
                   {
                      inp=fit.getInputStream();
                     String key=fit.getFieldName();
                     String filename=fit.getName();
                       System.out.println("Inside else ");
                       System.out.println(key+":"+filename);
                   }   
               }
               CandidateDTO candidate = new CandidateDTO(objValues.get(0),objValues.get(3),objValues.get(4),objValues.get(1),inp); 
               boolean result = CandidateDAO.addCandidate(candidate);
               if(result)
               {
                 rd=request.getRequestDispatcher("success.jsp");
               }
               else{
                  rd=request.getRequestDispatcher("failure.jsp");
               }
            }
            catch (Exception ex)
            {
                System.out.println("Exception in AddNewCandidateControllerServlet");
                ex.printStackTrace();
            }
            finally{
               if(rd!=null)
                  rd.forward(request, response);
            }
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
