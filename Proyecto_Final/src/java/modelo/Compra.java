/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
/**
 *
 * @author Usuario
 */
public class Compra {
    private String fecha_orden;
    private int no_orden_compra,id_proveedores,id_compra,id_producto,cantidad;
    private double precio_costo_unitario;
    private java.sql.Timestamp fecha_ingreso;
    private Conexion cn;

    public Compra(){       
    }
    
    public Compra(String fecha_orden, int no_orden_compra, int id_proveedores, int id_compra, int id_producto, int cantidad, double precio_costo_unitario, Timestamp fecha_ingreso, Conexion cn) {
        this.fecha_orden = fecha_orden;
        this.no_orden_compra = no_orden_compra;
        this.id_proveedores = id_proveedores;
        this.id_compra = id_compra;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_costo_unitario = precio_costo_unitario;
        this.fecha_ingreso = fecha_ingreso;
        this.cn = cn;
    }
    
    public String getFecha_orden() {
        return fecha_orden;
    }

    public void setFecha_orden(String fecha_orden) {
        this.fecha_orden = fecha_orden;
    }

    public int getNo_orden_compra() {
        return no_orden_compra;
    }

    public void setNo_orden_compra(int no_orden_compra) {
        this.no_orden_compra = no_orden_compra;
    }

    public int getId_proveedores() {
        return id_proveedores;
    }

    public void setId_proveedores(int id_proveedores) {
        this.id_proveedores = id_proveedores;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_costo_unitario() {
        return precio_costo_unitario;
    }

    public void setPrecio_costo_unitario(double precio_costo_unitario) {
        this.precio_costo_unitario = precio_costo_unitario;
    }

    public java.sql.Timestamp getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(java.sql.Timestamp fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
    
    public DefaultTableModel leer() {
    DefaultTableModel tabla = new DefaultTableModel();
    try {
        cn = new Conexion();
        cn.abrir_conexion();

        String query = "SELECT c.id_compra, c.no_orden_compra, c.fecha_orden, c.id_proveedores, d.id_producto, d.cantidad, d.precio_costo_unitario " +
                       "FROM compras c " +
                       "INNER JOIN compras_detalle d ON c.id_compra = d.id_compra;";

        Statement stmt = cn.conexionDB.createStatement();
        ResultSet consulta = stmt.executeQuery(query);

        String encabezado[] = {"ID Compra", "No Orden Compra", "Fecha Orden", "ID Proveedor", "ID Producto", "Cantidad", "Precio Unitario"};
        tabla.setColumnIdentifiers(encabezado);

        String datos[] = new String[7];

        while (consulta.next()) {
            datos[0] = consulta.getString("id_compra");
            datos[1] = consulta.getString("no_orden_compra");
            datos[2] = consulta.getString("fecha_orden");
            datos[3] = consulta.getString("id_proveedores");
            datos[4] = consulta.getString("id_producto");
            datos[5] = consulta.getString("cantidad");
            datos[6] = consulta.getString("precio_costo_unitario");

            tabla.addRow(datos);
        }

        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println("Error al leer los datos de la compra: " + ex.getMessage());
    }
    return tabla;
}
        

 public int agregar() {
        int retorno = 0;
        try {
            PreparedStatement parametro;
            cn.abrir_conexion();
            String query = "INSERT INTO compras (no_orden_compra, id_proveedores, fecha_orden, fecha_ingreso) VALUES (?, ?, ?, ?);";
            parametro = cn.conexionDB.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            parametro.setInt(1, no_orden_compra);
            parametro.setInt(2, id_proveedores);
            parametro.setString(3, fecha_orden);
            parametro.setTimestamp(4, fecha_ingreso);
            retorno = parametro.executeUpdate();

            
            ResultSet generatedKeys = parametro.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idCompraGenerada = generatedKeys.getInt(1);

                
                query = "INSERT INTO compras_detalle (id_compra, id_producto, cantidad, precio_costo_unitario) VALUES (?, ?, ?, ?);";
                parametro = cn.conexionDB.prepareStatement(query);
                parametro.setInt(1, idCompraGenerada);
                parametro.setInt(2, id_producto);
                parametro.setInt(3, cantidad);
                parametro.setDouble(4, precio_costo_unitario);
                retorno += parametro.executeUpdate();
            }

            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error al insertar la compra: " + ex.getMessage());
        }
        return retorno;
    }

    public int modificar() {
        int retorno = 0;
        try {
            PreparedStatement parametro;
            cn.abrir_conexion();
            String query = "UPDATE compras SET no_orden_compra=?, id_proveedores=?, fecha_orden=?, fecha_ingreso=? WHERE id_compra=?;";
            parametro = cn.conexionDB.prepareStatement(query);
            parametro.setInt(1, no_orden_compra);
            parametro.setInt(2, id_proveedores);
            parametro.setString(3, fecha_orden);
            parametro.setTimestamp(4, fecha_ingreso);
            parametro.setInt(5, id_compra);
            retorno = parametro.executeUpdate();

            query = "UPDATE compras_detalle SET id_producto=?, cantidad=?, precio_costo_unitario=? WHERE id_compra=?;";
            parametro = cn.conexionDB.prepareStatement(query);
            parametro.setInt(1, id_producto);
            parametro.setInt(2, cantidad);
            parametro.setDouble(3, precio_costo_unitario);
            parametro.setInt(4, id_compra);
            retorno += parametro.executeUpdate();

            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error al modificar la compra: " + ex.getMessage());
        }
        return retorno;
    }

    public int eliminar() {
        int retorno = 0;
        try {
            PreparedStatement parametro;
            cn.abrir_conexion();
            String query = "DELETE FROM compras_detalle WHERE id_compra=?;";
            parametro = cn.conexionDB.prepareStatement(query);
            parametro.setInt(1, id_compra);
            retorno = parametro.executeUpdate();

            query = "DELETE FROM compras WHERE id_compra=?;";
            parametro = cn.conexionDB.prepareStatement(query);
            parametro.setInt(1, id_compra);
            retorno += parametro.executeUpdate();

            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar la compra: " + ex.getMessage());
        }
        return retorno;
    }
}

