/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import static com.sun.xml.bind.util.CalendarConv.formatter;
import dao.ConexionBBDD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Usuario;

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
               
            HttpSession miSesion=request.getSession(true);
                        //recoger datos
         //   String nombre=request.getParameter("nombre");
           //  int edad=Integer.parseInt( request.getParameter("edad"));
                        String accion=request.getParameter("accion");
            String dni=request.getParameter("dni");
                 String localidad=request.getParameter("localidad");
                 int localidadId=Integer.parseInt(localidad);
            String nombre=request.getParameter("nombre");
            String apellidos=request.getParameter("apellidos");
            String fecha=request.getParameter("fecha_nacimiento");
            String pass=request.getParameter("password");
              // Formato esperado: dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                 // Convertir String a LocalDate
                LocalDate fechaNacimiento = LocalDate.parse(fecha, formatter);
              LocalDate hoy = LocalDate.now();
              


            // Calcular la edad
            Period periodo = Period.between(fechaNacimiento, hoy);
            int edad = periodo.getYears();
     if(edad>=18){
          try {
        ConexionBBDD bbdd = new ConexionBBDD();
        int numCenso=bbdd.personaCenso(dni, nombre, apellidos, fecha,localidadId);
        int num = bbdd.insertarUsuario(dni, pass);
  
             response.sendRedirect("vistasNavegacion/login.jsp");
        
        bbdd.cerrarConexion();
    } catch (ClassNotFoundException e1) {
        miSesion.setAttribute("Mensaje error", e1.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
    } catch (SQLException e2) {
        miSesion.setAttribute("Mensaje error", e2.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
    } 
     }else{
            miSesion.setAttribute("Mensaje error", "EL usuario no puede registrarse debedo a que es menor de edad");
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
