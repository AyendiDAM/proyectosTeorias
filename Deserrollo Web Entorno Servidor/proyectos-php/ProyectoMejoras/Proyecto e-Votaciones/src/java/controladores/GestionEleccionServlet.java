/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Eleccion;

/**
 *
 * @author ayend
 */
public class GestionEleccionServlet extends HttpServlet {//crear elecciones

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

            String nombre = request.getParameter("nombre");
            String fInicio = request.getParameter("fecha_inicio");
            String fFin = request.getParameter("fecha_fin");

            try {
                ConexionBBDD dao = new ConexionBBDD();
       

                // Requisito Rúbrica: No permitir crear si ya hay una activa
                if (dao.hayEleccionActiva()) {
                    request.setAttribute("error", "Error: Ya existe una elección activa. Finalícela antes de crear una nueva.");
                } else {
                    Eleccion e = new Eleccion();
                    e.setNombre(nombre);
                    e.setFechaInicio(Date.valueOf(fInicio)); // Convertimos String a SQL Date
                    e.setFechaFinal(Date.valueOf(fFin));

                    if (dao.crearEleccion(e)) {
                        request.setAttribute("mensaje", "Elección creada correctamente (Estado: CERRADA).");
                    } else {
                        request.setAttribute("error", "Error al guardar en base de datos.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error técnico: " + e.getMessage());
            }

            request.getRequestDispatcher("vistasNavegacion/gestionElecciones.jsp").forward(request, response);

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
