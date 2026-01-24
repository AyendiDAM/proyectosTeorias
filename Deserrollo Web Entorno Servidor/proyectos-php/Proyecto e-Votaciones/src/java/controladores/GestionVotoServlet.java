/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Usuario;

/**
 *
 * @author ayend
 */
public class GestionVotoServlet extends HttpServlet {

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
            HttpSession sesion = request.getSession(true);

        try {
            // Obtener parámetros
            String siglasPartido = request.getParameter("partidoElegido");
         
            String idUsuario = (String) sesion.getAttribute("idUsuarioVotado");

            // Obtener datos del usuario que está votando
            Usuario usuario = (Usuario) sesion.getAttribute("votar");

            if (usuario.getVoto() == 1) {
                sesion.setAttribute("Mensaje error", "Ya has votado anteriormente.");
                response.sendRedirect("errorUsuario/notificaciones.jsp");
                return;
            }

            
            // Abrir conexión
            ConexionBBDD bbdd = new ConexionBBDD();
            
            //obtener el id de la eleccion
      int idEleccion=(int)sesion.getAttribute("idEleccion");
 
            // Obtener ID del partido real
            int idPartido = Integer.parseInt(bbdd.id_partido(siglasPartido).trim());

            // obtener localidad
            int idLocalidad = bbdd.obtenerLocalidadUsuarioVotado(idUsuario);

            // Insertar el voto
            int filas = bbdd.insertarVoto(idEleccion,idPartido,  idLocalidad);
    
          

       if (filas > 0) {

                // marcar al usuario como que ha votado
                usuario.setVoto(1);
                sesion.setAttribute("votar", usuario);

                sesion.setAttribute("Mensaje error", "Voto emitido correctamente");
                        int votoUsuario=bbdd.updateVoto(usuario.getVoto(), usuario.getDni());
                response.sendRedirect("errorUsuario/notificaciones.jsp");
                  bbdd.cerrarConexion();
            } else {
                sesion.setAttribute("Mensaje error", "Error al registrar el voto.");
                response.sendRedirect("errorUsuario/notificaciones.jsp");
                  bbdd.cerrarConexion();
            }

        } catch (Exception e) {
            sesion.setAttribute("Mensaje error", e.getMessage());
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
