/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.ConexionBBDD;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import modelos.Partido_politico;

/**
 *
 * @author ayend
 */
@MultipartConfig
public class GestionPartidoAnadirServlet extends HttpServlet {

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
        String siglas = request.getParameter("siglas");
        
        // 1. Recoger la imagen
        Part filePart = request.getPart("logo"); // "logo" es el name del input en el JSP
        String nombreArchivo = filePart.getSubmittedFileName(); // Nombre original (ej: psoe.png)
        
        // 2. Definir dónde guardar la imagen
        // Esto guarda la imagen en la carpeta "imagenes" dentro de tu proyecto desplegado
        String path = getServletContext().getRealPath("/imagenes"); 
        File uploads = new File(path);
        if (!uploads.exists()) {
            uploads.mkdirs(); // Crea la carpeta si no existe
        }
        
        File file = new File(uploads, nombreArchivo);
        
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Guardar datos en BBDD
        try {
            ConexionBBDD conn = new ConexionBBDD();
        
            
            Partido_politico nuevoPartido = new Partido_politico();
            nuevoPartido.setNombre(nombre);
            nuevoPartido.setSiglas(siglas);
            nuevoPartido.setLogo(nombreArchivo); // Guardamos solo el nombre en BBDD
            
            if(conn.registrarPartido(nuevoPartido)){
                request.setAttribute("mensaje", "Partido guardado correctamente con imagen.");
            } else {
                request.setAttribute("mensaje", "Error al guardar en BBDD.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error: " + e.getMessage());
        }

        // Volver a la página
        request.getRequestDispatcher("vistasNavegacion/gestionPartido.jsp").forward(request, response);

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
