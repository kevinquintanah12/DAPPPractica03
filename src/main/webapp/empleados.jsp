<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Configuración conexión SQLite
    String dbPath = application.getRealPath("/") + "WEB-INF/empresa.db";
    String url = "jdbc:sqlite:" + dbPath;

    Connection conexion = null;
    Statement st = null;

    try {
        Class.forName("org.sqlite.JDBC");
        conexion = DriverManager.getConnection(url);
        st = conexion.createStatement();

        // Crear tabla si no existe
        st.executeUpdate("CREATE TABLE IF NOT EXISTS empleados (" +
                         "clave INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "nombre TEXT NOT NULL, " +
                         "direccion TEXT NOT NULL, " +
                         "telefono TEXT NOT NULL)");

        // Procesar formulario
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "crear":
                    String nombre = request.getParameter("nombre");
                    String direccion = request.getParameter("direccion");
                    String telefono = request.getParameter("telefono");
                    st.executeUpdate("INSERT INTO empleados (nombre, direccion, telefono) VALUES ('"
                                     + nombre + "','" + direccion + "','" + telefono + "')");
                    break;
                case "actualizar":
                    String claveU = request.getParameter("clave");
                    nombre = request.getParameter("nombre");
                    direccion = request.getParameter("direccion");
                    telefono = request.getParameter("telefono");
                    st.executeUpdate("UPDATE empleados SET nombre='" + nombre + "', direccion='" + direccion
                                     + "', telefono='" + telefono + "' WHERE clave=" + claveU);
                    break;
                case "eliminar":
                    String claveD = request.getParameter("clave");
                    st.executeUpdate("DELETE FROM empleados WHERE clave=" + claveD);
                    break;
            }
        }
    } catch (Exception e) {
        out.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>CRUD Empleados (JSP + SQLite)</title>
    <style>
        body { background-color: white; font-family: Arial; }
        .container { width: 80%; margin: auto; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .form-group { margin: 10px 0; }
        input[type="text"] { padding: 5px; width: 200px; }
        .btn { padding: 5px 10px; background-color: #008CBA; color: white; border: none; cursor: pointer; }
        .btn-danger { background-color: #f44336; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Gestión de Empleados (JSP + SQLite)</h2>
        
        <!-- Formulario -->
        <form method="POST">
            <input type="hidden" name="accion" value="crear" id="accion">
            <input type="hidden" name="clave" id="clave">
            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" name="nombre" id="nombre" required>
            </div>
            <div class="form-group">
                <label>Dirección:</label>
                <input type="text" name="direccion" id="direccion" required>
            </div>
            <div class="form-group">
                <label>Teléfono:</label>
                <input type="text" name="telefono" id="telefono" required>
            </div>
            <button type="submit" class="btn">Guardar</button>
        </form>

        <!-- Tabla empleados -->
        <table>
            <tr>
                <th>Clave</th>
                <th>Nombre</th>
                <th>Dirección</th>
                <th>Teléfono</th>
                <th>Acciones</th>
            </tr>
            <%
                try {
                    ResultSet rs = st.executeQuery("SELECT * FROM empleados");
                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td>" + rs.getInt("clave") + "</td>");
                        out.println("<td>" + rs.getString("nombre") + "</td>");
                        out.println("<td>" + rs.getString("direccion") + "</td>");
                        out.println("<td>" + rs.getString("telefono") + "</td>");
                        out.println("<td>");
                        out.println("<form method='POST' style='display:inline'>");
                        out.println("<input type='hidden' name='accion' value='eliminar'>");
                        out.println("<input type='hidden' name='clave' value='" + rs.getInt("clave") + "'>");
                        out.println("<button type='submit' class='btn btn-danger'>Eliminar</button>");
                        out.println("</form>");
                        out.println("<button onclick='editar("
                                    + "\"" + rs.getInt("clave") + "\","
                                    + "\"" + rs.getString("nombre") + "\","
                                    + "\"" + rs.getString("direccion") + "\","
                                    + "\"" + rs.getString("telefono") + "\")' class='btn'>Editar</button>");
                        out.println("</td>");
                        out.println("</tr>");
                    }
                } catch (Exception e) {
                    out.println("<tr><td colspan='5'>Error cargando datos: " + e.getMessage() + "</td></tr>");
                } finally {
                    if (st != null) try { st.close(); } catch (Exception ignore) {}
                    if (conexion != null) try { conexion.close(); } catch (Exception ignore) {}
                }
            %>
        </table>
    </div>

    <script>
    function editar(clave, nombre, direccion, telefono) {
        document.getElementById('clave').value = clave;
        document.getElementById('nombre').value = nombre;
        document.getElementById('direccion').value = direccion;
        document.getElementById('telefono').value = telefono;
        document.getElementById('accion').value = 'actualizar';
    }
    </script>
</body>
</html>
