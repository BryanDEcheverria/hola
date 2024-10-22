/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Usuarios;

public class sr_usuarios extends HttpServlet {


     Usuarios usuarios;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet sr_usuarios</title>");
            out.println("</head>");
            out.println("<body>");
            
            
            usuarios = new Usuarios(Integer.parseInt(request.getParameter("txt_id")),
                    request.getParameter("txt_usuario"),
                    request.getParameter("txt_nombres"),
                    request.getParameter("txt_apellidos"),
                    request.getParameter("txt_ce"),
                    request.getParameter("txt_contrasena")
                    );

             if ("agregar".equals(request.getParameter("btn_agregar"))) {
                if (usuarios.agregar() > 0) {
                    request.getSession().setAttribute("mensaje", "Usuario agregado con éxito.");
                } else {
                    request.getSession().setAttribute("mensaje", "Error al agregar el usuario.");
                }
                response.sendRedirect("index.jsp");
            }
             
             if ("modificar".equals(request.getParameter("btn_modificar"))) {
                if (usuarios.modificar() > 0) {
                    request.getSession().setAttribute("mensaje", "Usuario modificado con éxito.");
                } else {
                    request.getSession().setAttribute("mensaje", "Error al modificar el Usuario.");
                }
                response.sendRedirect("index.jsp");
            }
             
             if ("eliminar".equals(request.getParameter("btn_eliminar"))) {
                if (usuarios.eliminar() > 0) {
                    request.getSession().setAttribute("mensaje", "Usuario eliminado con éxito.");
                } else {
                    request.getSession().setAttribute("mensaje", "Error al eliminar el Usuario.");
                }
                response.sendRedirect("index.jsp");
            }
             
            out.println("</body>");
            out.println("</html>");
        
    }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

