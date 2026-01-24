/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import modelos.ExcepcionMinimoPartidos;
import modelos.Partido_politico;

/**
 *
 * @author ayend
 */
public class NumeroPartidosControlador extends HttpServlet {

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
    HttpSession miSesion=request.getSession(true);
            /* TODO output your page here. You may use following sample code. */
     try {
        ConexionBBDD bbdd = new ConexionBBDD();
                        ArrayList<Partido_politico> totales=bbdd.partidosTotales();
                        int contado=0;
     for(Partido_politico p: totales){
         contado++;
     }
     
        if(contado>=4){
                   miSesion.setAttribute("Todo correcto paritodos", totales);
        response.sendRedirect("errorUsuario/verPartidos.jsp");
        }else{
            ExcepcionMinimoPartidos pm=new ExcepcionMinimoPartidos(contado);
                 miSesion.setAttribute("Insuficiente partidos",pm);
        //response.sendRedirect("vistasNavegacion/gestionPartido.jsp");
 response.sendRedirect("errorUsuario/MinimoPartidosPoliticosExcepcion.jsp");
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
