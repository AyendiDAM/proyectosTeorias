/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ayend
 */
public class InicioSeccion extends HttpServlet {

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
            
                    
            HttpSession miSesion=request.getSession(true);
            String accion=request.getParameter("accion");
            String pass=request.getParameter("pass");
            String edad=request.getParameter("edad");
            int edadVerificar=Integer.parseInt(edad);
            
            
            
            if(accion.equalsIgnoreCase("Dar de alta usuario")){
                  response.sendRedirect("vistas/seccion.jsp");
                
                
            }else if(accion.equalsIgnoreCase("Entrar")){
                if(edadVerificar>=18){
                      miSesion.setAttribute("Inicio",pass);
                      response.sendRedirect("vistas/Login.jsp"); 
                }else{
                      String error="Todavía no eres mayor de edad";
                miSesion.setAttribute("Mensaje error", error);
                response.sendRedirect("vistaError.jsp");
                }
              
                   
                    
                    
            }else if(accion.equalsIgnoreCase("guardar seccion")){
              
                 
            }else{
                String error="No se ha pulsado una acción correcta";
                miSesion.setAttribute("Mensaje error", error);
                response.sendRedirect("vistaError.jsp");
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
