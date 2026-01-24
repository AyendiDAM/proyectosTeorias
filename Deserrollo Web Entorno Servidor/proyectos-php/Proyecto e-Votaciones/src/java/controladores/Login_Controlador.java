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
                
            HttpSession miSesion=request.getSession(true);
           
            String dni=request.getParameter("dni");
            
            
            
                try {
        ConexionBBDD bbdd = new ConexionBBDD();
        String tipoUsuario=bbdd.compruebaRol(dni);
        boolean numCenso=bbdd.buscarResgistro(dni);
        String nombre=bbdd.nombreCookie(dni);
        int idLocalidad=bbdd.obtenerLocalidadUsuarioVotado(dni);
        String valorCodificado=URLEncoder.encode(nombre,"UTF-8");
        
          //creo la cookie para el nombre
             Cookie cNombre=new Cookie("nombre", valorCodificado);
             //tiempo de vida
             cNombre.setMaxAge(60*6);
             //guarda cookie para el cliente y dar repuesta
             response.addCookie(cNombre);
          //   bbdd.updateEleccionAdmin("Elecciones Generales 2025","FINALIZADA");
             //
             int voto_=bbdd.devuelveVotoUsuario(dni);
             
            bbdd.cerrarConexion();
            
        if(numCenso){
            if(tipoUsuario.equalsIgnoreCase("VOTANTE")){
                    Usuario usuario=new Usuario(dni,"212", "usuario", idLocalidad,voto_);//controlar el registro
                miSesion.setAttribute("votar", usuario);
                   response.sendRedirect("vistasNavegacion/tablaVotante.jsp");
                   //    public Usuario(String dni, String password, String rol,int localidad,int voto)
           
          
  
                   
                 
            }else    if(tipoUsuario.equalsIgnoreCase("ADMIN")){
                                          Usuario usuario=new Usuario(dni,"212", "usuario", idLocalidad,voto_);//controlar el registro
                miSesion.setAttribute("votar", usuario);
                
                   response.sendRedirect("vistasNavegacion/tablaAdmin.jsp"); 
                   
            }else    if(tipoUsuario.equalsIgnoreCase("ANALISTA")){
                                          Usuario usuario=new Usuario(dni,"212", "usuario", idLocalidad,voto_);//controlar el registro
                miSesion.setAttribute("votar", usuario);
                     response.sendRedirect("vistasNavegacion/tablaAnalista.jsp"); 
                                 
            }
             
        }
        
    
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
