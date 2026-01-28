/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.MenorEdadExcepcion;
import modelos.Usuario;
import modelos.Utilidades;

/**
 *
 * @author ayend
 */
public class RegistroControlador extends HttpServlet {

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
// Es importante establecer la codificación al principio
    request.setCharacterEncoding("UTF-8"); 

    String dni = request.getParameter("dni");
    String pass = request.getParameter("password");

    // 1. Definimos el destino por defecto: VOLVER AL REGISTRO (por si falla)
    String vistaDestino = "vistasNavegacion/registroVotante.jsp";

    try {
        // Instanciamos el DAO (Asegúrate de usar la clase correcta, DAO o ConexionBBDD)
        ConexionBBDD dao = new ConexionBBDD(); 

        // --- VALIDACIÓN A: ¿Ya está registrado? ---
        if (dao.existeUsuarioRegistrado(dni)) {
            // Lanzamos excepción para saltar directamente al 'catch' de abajo
            throw new Exception("El usuario ya tiene una cuenta activa en el sistema.");
        }

        // --- VALIDACIÓN B: Buscar en el censo ---
        Usuario usuarioDelCenso = dao.buscarEnCenso(dni);

        if (usuarioDelCenso == null) {
            throw new Exception("El DNI introducido no figura en el censo electoral.");
        }

        // --- VALIDACIÓN C: Validar Edad ---
        // Asegúrate de importar java.time.LocalDate y java.time.Period
        LocalDate fechaNac = usuarioDelCenso.getFechaNacimiento().toLocalDate();
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();

        if (edad < 18) {
            // Usamos tu excepción personalizada
            throw new MenorEdadExcepcion("Lo sentimos, tiene " + edad + " años. Necesita ser mayor de edad para votar.");
        }

        // --- PASO D: Registrar Usuario ---
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setDni(dni);
        // Asegúrate de importar tu clase Utilidades para el MD5
        nuevoUsuario.setPassword(Utilidades.encriptarMD5(pass)); 

        if (dao.registrarUsuario(nuevoUsuario)) {
            // SI TODO HA IDO BIEN:
            // 1. Preparamos mensaje de éxito
            String mensaje = "Registro exitoso. Bienvenido/a " + usuarioDelCenso.getNombre();
            request.setAttribute("mensajeExito", mensaje);
            
            // 2. Cambiamos el destino al LOGIN
            vistaDestino = "vistasNavegacion/login.jsp";
        } else {
            throw new Exception("Error al guardar en la base de datos.");
        }

    } catch (MenorEdadExcepcion e) {
        // Captura el error de edad y lo manda a la vista
        request.setAttribute("error", e.getMessage());
        
    } catch (Exception e) {
        // Captura cualquier otro error (DNI duplicado, no censo, error SQL, etc.)
        request.setAttribute("error", e.getMessage());
        e.printStackTrace(); // Para ver el error técnico en la consola de NetBeans
    }

    // --- FINAL: ENVIAR A LA VISTA SELECCIONADA ---
    // Esto lleva los atributos ("error" o "mensajeExito") a la página JSP
    request.getRequestDispatcher(vistaDestino).forward(request, response);
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
    


