/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.ExcepcionMinimoPartidos;
import modelos.Partido_politico;

/**
 *
 * @author ayend
 */
public class ServletCrearCookie extends HttpServlet {

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
 
       
          
           try {
        ConexionBBDD bbdd = new ConexionBBDD();
                        ArrayList<Partido_politico> totales=bbdd.partidosTotales();

        String estado=bbdd.devuelveEstado();
                  bbdd.cerrarConexion();
                if(estado.equalsIgnoreCase("FINALIZADA")){
                     String nombre=request.getParameter("nombre");
                   String valorCodificado=URLEncoder.encode(nombre,"UTF-8");
               //creo la cookie para el nombre
             Cookie cNombre=new Cookie("nombreGanador", valorCodificado);
             //tiempo de vida
             cNombre.setMaxAge(60*6);
             //guarda cookie para el cliente y dar repuesta
             response.addCookie(cNombre);
     response.sendRedirect("vistasNavegacion/verCookieGanador.jsp");
     
                }else{
                    miSesion.setAttribute("Mensaje error", "No esta finalizada la eleccion");
        response.sendRedirect("errorUsuario/notificaciones.jsp");
                }
       
       
             //
            bbdd.cerrarConexion();
  /* miSesion.setAttribute("Mensaje error", "Todo correcto");
   if(nombreNuevo.equalsIgnoreCase("FINALIZADA")){
          response.sendRedirect("vistasNavegacion/resultados.jsp");
        
   }else if(estadoEleccion.equalsIgnoreCase("ABIERTA")){
       miSesion.setAttribute("puede votar", estadoEleccion);
       response.sendRedirect("vistasNavegacion/votar.jsp");
       miSesion.setAttribute("eleccion", idEleccion);
   }
     
    */
    } catch (ClassNotFoundException e1) {
        miSesion.setAttribute("Mensaje error", e1.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
    } catch (SQLException e2) {
        miSesion.setAttribute("Mensaje error", e2.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
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
