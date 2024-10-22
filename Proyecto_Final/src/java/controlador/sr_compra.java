package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Compra;

/**
 *
 * @author Bomiki
 */
public class sr_compra extends HttpServlet {

    Compra compra;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            compra = new Compra(
                Integer.parseInt(request.getParameter("txt_id_compra")),
                Integer.parseInt(request.getParameter("txt_id_proveedor")),
                Integer.parseInt(request.getParameter("txt_no_orden_compra")),
                request.getParameter("txt_fecha_orden"),
                request.getParameter("txt_fecha_ingreso"),
                Integer.parseInt(request.getParameter("drop_producto")),
                Integer.parseInt(request.getParameter("txt_cantidad")),
                Double.parseDouble(request.getParameter("txt_precio_unitario"))
            );

            
            String action = request.getParameter("action"); 
            if ("agregar".equals(action)) {
                if (compra.agregar() > 0) {
                    request.getSession().setAttribute("mensaje", "Compra agregada con éxito.");
                } else {
                    request.getSession().setAttribute("mensaje", "Error al agregar la compra.");
                }
            } else if ("modificar".equals(action)) {
                if (compra.modificar() > 0) {
                    request.getSession().setAttribute("mensaje", "Compra modificada con éxito.");
                } else {
                    request.getSession().setAttribute("mensaje", "Error al modificar la compra.");
                }
            } else if ("eliminar".equals(action)) {
                if (compra.eliminar() > 0) {
                    request.getSession().setAttribute("mensaje", "Compra eliminada con éxito.");
                } else {
                    request.getSession().setAttribute("mensaje", "Error al eliminar la compra.");
                }
            } else {
               
                request.getSession().setAttribute("mensaje", "Acción no reconocida.");
            }

           
            response.sendRedirect("index.jsp");
            return; 

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensaje", "Error en los datos ingresados. Verifique los campos numéricos.");
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("mensaje", "Error inesperado: " + e.getMessage());
            response.sendRedirect("index.jsp");
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
        return "Controlador para las operaciones de compra";
    }
}