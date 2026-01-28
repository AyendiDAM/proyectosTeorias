/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Usuario;
import modelos.Utilidades;

/**
 *
 * @author ayend
 */
public class Login_Controlador extends HttpServlet {

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
                
         String dni = request.getParameter("dni");
        String pass = request.getParameter("password");
        
        // Destino si falla
        String destino = "vistasNavegacion/login.jsp";
        
        try {
            ConexionBBDD dao = new ConexionBBDD();
          
            
            // 1. Encriptamos lo que el usuario escribió para compararlo con la BBDD
            String passEnc = Utilidades.encriptarMD5(pass);
            
            // 2. Intentamos hacer login
            Usuario usuarioLogueado = dao.login(dni, passEnc);
            
            if (usuarioLogueado != null) {
                // --- LOGIN CORRECTO ---
                
                // 3. Crear la sesión (ESTO ES VITAL)
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuarioLogueado);
                
                // 4. Redirigir según el ROL (Requisito de la rúbrica)
                String rol = usuarioLogueado.getRol();
                
                if (rol != null && (rol.equalsIgnoreCase("ADMIN"))) {
                    // Si es Admin, va a su panel de gestión
                     destino = "vistasNavegacion/tablaAdmin.jsp"; // O gestionElecciones.jsp
                } else {
                    // Si es Votante, va a la página principal de votación
                    destino = "vistasNavegacion/tablaVotante.jsp"; 
                }
                
            } else {
                // Login incorrecto
                request.setAttribute("error", "DNI o contraseña incorrectos.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error técnico en el servidor: " + e.getMessage());
        }
        
        // Redirección final
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
