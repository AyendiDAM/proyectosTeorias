/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Candidato;
import modelos.Partido_politico;

/**
 *
 * @author ayend
 */
public class GestionCandidatoServlet extends HttpServlet {

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
            request.setCharacterEncoding("UTF-8");

            String dni = request.getParameter("dni");
            int idPartido = Integer.parseInt(request.getParameter("id_partido"));
            int orden = Integer.parseInt(request.getParameter("orden"));

            String destino = "vistasNavegacion/gestionCandidatos.jsp";

            try {
                ConexionBBDD dao = new ConexionBBDD();
            
     

                // --- VALIDACIÓN 1: ¿Existe en el censo? (-0.25 si falla) ---
                if (!dao.existeEnCenso(dni)) {
                    throw new Exception("Error: El DNI " + dni + " no existe en el censo electoral.");
                }

                // --- VALIDACIÓN 2: ¿Ya es candidato? ---
                if (dao.yaEsCandidato(dni)) {
                    throw new Exception("Error: Esta persona ya es candidata en otro partido.");
                }

                // Guardar
                Candidato c = new Candidato();
                c.setDni(dni);
                c.setIdPartido(idPartido);
                c.setOrden(orden);

                if (dao.registrarCandidato(c)) {
                    request.setAttribute("mensaje", "Candidato registrado correctamente.");
                } else {
                    request.setAttribute("error", "No se pudo registrar al candidato.");
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
