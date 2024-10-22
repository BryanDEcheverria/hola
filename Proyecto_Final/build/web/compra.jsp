<%@page import="javax.swing.table.DefaultTableModel"%>
<%@page import="java.util.HashMap"%>
<%@page import="modelo.Compra" %>
<%@page import="modelo.Productos" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-label {
            font-weight: bold;
        }
        .modal-lg {
            max-width: 80%;
        }
        .modal-body {
            background-color: #f8f9fa;
            padding: 2rem;
            border-radius: 0.5rem;
        }
        .form-control {
            border-radius: 0.5rem;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .modal-footer {
            border-top: none;
        }
        h1.text-center {
            color: #00bfa5;
            font-weight: bold;
            margin-bottom: 2rem;
        }
        .btn-custom {
            box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center">Registrar Compras</h1>
    <div class="d-flex justify-content-end mb-3">
        <button type="button" class="btn btn-primary btn-custom" id="btn_crear_compra" data-bs-toggle="modal" data-bs-target="#modal_compra" onclick="limpiar()">Crear Compra</button>
    </div>
    
    <div class="modal fade" id="modal_compra" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Detalles de Compra</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="sr_compra" method="post" id="form_compra" class="form-group">
                        <div class="mb-3">
                            <label for="txt_id_compra" class="form-label">ID Compra:</label>
                            <input type="text" name="txt_id_compra" id="txt_id_compra" class="form-control" value="0" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="txt_id_proveedor" class="form-label">No de Proveedor:</label>
                            <input type="text" name="txt_id_proveedor" id="txt_id_proveedor" class="form-control" placeholder="Ingrese ID Proveedor" required>
                        </div>    
                        <div class="row">
                            <div class="col-md-4 mb-4">
                                <label for="txt_no_orden_compra" class="form-label">No. de Orden de Compra</label>
                                <input type="number" name="txt_no_orden_compra" id="txt_no_orden_compra" class="form-control" placeholder="Ingrese No. de Orden de Compra" required>
                            </div>
                            <div class="col-md-4 mb-4">
                                <label for="txt_fecha_orden" class="form-label">Fecha de Orden:</label>
                                <input type="date" name="txt_fecha_orden" id="txt_fecha_orden" class="form-control" required>
                            </div>
                            <div class="col-md-4 mb-4">
                                <label for="txt_fecha_ingreso" class="form-label">Fecha de Ingreso:</label>
                                <input type="date" name="txt_fecha_ingreso" id="txt_fecha_ingreso" class="form-control" required>
                            </div>
                        </div>
                        <hr class="my-4">
                        <h4 class="mb-4">Detalles de Compra</h4>
                        <div class="row">
                            <div class="col-md-4 mb-4">
                                <label for="drop_producto" class="form-label">Producto:</label>
                                <select name="drop_producto" id="drop_producto" class="form-select">
                                    <%
                                    Productos productos = new Productos();
                                    HashMap<String,String> dropP = productos.drop_productos();
                                    for(String i: dropP.keySet()){
                                        out.println("<option value='" + i + "'>" + dropP.get(i) + "</option>");
                                    }
                                    %>
                                </select>
                            </div>
                            <div class="col-md-4 mb-4">
                                <label for="txt_cantidad" class="form-label">Cantidad:</label>
                                <input type="number" name="txt_cantidad" id="txt_cantidad" class="form-control" placeholder="Cantidad" required>
                            </div>
                            <div class="col-md-4 mb-4">
                                <label for="txt_precio_unitario" class="form-label">Precio Unitario:</label>
                                <input type="number" step="0.01" name="txt_precio_unitario" id="txt_precio_unitario" class="form-control" placeholder="Precio Unitario" required>
                            </div>
                        </div>
                        <div class="modal-footer mt-4">
                            <button type="button" class="btn btn-secondary btn-custom" data-bs-dismiss="modal">Cancelar</button>
                            <button name="btn_agregar" id="btn_agregar" value="agregar" class="btn btn-primary btn-custom">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <table class="table table-bordered table-hover mt-4">
        <thead class="table-dark">
            <tr>
                <th scope="col">ID Compra</th>
                <th scope="col">No. Proveedor</th>
                <th scope="col">No. Orden de Compra</th>
                <th scope="col">Fecha de Orden</th>
                <th scope="col">Fecha de Ingreso</th>
                <th scope="col">Producto</th>
                <th scope="col">Cantidad</th>
                <th scope="col">Precio Unitario</th>
                <th scope="col">Acciones</th>
            </tr>
        </thead>
        <tbody id="tbl_compras">
            <%
            Compra compra = new Compra();
            DefaultTableModel tabla = compra.leer();
            for(int t = 0; t < tabla.getRowCount(); t++){
                out.println("<tr data-id_compra='" + tabla.getValueAt(t, 0) + "'>");
                out.println("<td>" + tabla.getValueAt(t, 0) + "</td>");
                out.println("<td>" + tabla.getValueAt(t, 3) + "</td>");
                out.println("<td>" + tabla.getValueAt(t, 1) + "</td>");
                out.println("<td>" + tabla.getValueAt(t, 2) + "</td>");
                out.println("<td>" + tabla.getValueAt(t, 7) + "</td>"); 
                out.println("<td>" + tabla.getValueAt(t, 4) + "</td>"); 
                out.println("<td>" + tabla.getValueAt(t, 5) + "</td>"); 
                out.println("<td>" + tabla.getValueAt(t, 6) + "</td>"); 
                out.println("<td><button class='btn btn-warning btn-sm' onclick='editarCompra(" + tabla.getValueAt(t, 0) + ")'>Editar</button> ");
                out.println("<button class='btn btn-danger btn-sm' onclick='eliminarCompra(" + tabla.getValueAt(t, 0) + ")'>Eliminar</button></td>");
                out.println("</tr>");
            }
            %>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript">
function limpiar() {
    $("#txt_id_compra").val(0);
    $("#txt_id_proveedor").val('');
    $("#txt_no_orden_compra").val('');
    $("#txt_fecha_orden").val('');
    $("#txt_fecha_ingreso").val('');
    $("#drop_producto").val('');
    $("#txt_cantidad").val('');
    $("#txt_precio_unitario").val('');
}

function editarCompra(id) {
}

function eliminarCompra(id) {
    if (confirm("¿Estás seguro de que deseas eliminar esta compra?")) {
    }
}
</script>
</body>
</html>