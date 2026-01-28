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
import modelos.Eleccion;
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
            HttpSession session = request.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            int idPartido = Integer.parseInt(request.getParameter("id_partido"));

            String destino = "vistasNavegacion/votar.jsp";

            try {
                ConexionBBDD eDao = new ConexionBBDD();
            

                // 1. IDENTIFICAR LA ELECCIÓN ACTIVA
                // Buscamos cuál es la elección que está "ABIERTA" ahora mismo    
                ArrayList<Eleccion> elecciones = eDao.obtenerElecciones();
                
                Eleccion eleccionActiva = null;
                for (Eleccion e : elecciones) {
                    if ("ABIERTA".equalsIgnoreCase(e.getEstado())) {
                        eleccionActiva = e;
                        break;
                    }
                }

                if (eleccionActiva == null) {
                    throw new Exception("Error: No hay ninguna elección abierta en este momento.");
                }

                // 2. VERIFICAR SI YA VOTÓ
                if (eDao.yaHaVotado(usuario.getId_usuario(), eleccionActiva.getId())) {
                    throw new Exception("Usted ya ha ejercido su derecho al voto en estas elecciones.");
                }

                // 3. REGISTRAR VOTO
                if (eDao.registrarVoto(usuario.getId_usuario(), eleccionActiva.getId(), idPartido)) {
                    request.setAttribute("mensaje", "¡Su voto ha sido registrado correctamente! Gracias por participar.");
                    // Actualizamos el usuario en sesión para que sepa que ya votó
                    usuario.setVoto(1);
                } else {
                    request.setAttribute("error", "Hubo un error al guardar su voto. Inténtelo de nuevo.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
            }

            request.getRequestDispatcher(destino).forward(request, response);

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
