package ec.edu.sistemalicencias.dao;

import ec.edu.sistemalicencias.config.DatabaseConfig;
import ec.edu.sistemalicencias.model.entities.Usuarios;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.interfaces.Persistible;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO implements Persistible<Usuarios> {

    private final DatabaseConfig dbConfig;

    public UsuariosDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    //Metodo para logearse
    public Usuarios login(String nombreUsuario, String contrasena) throws BaseDatosException {
        String sql = "SELECT id, nombre_usuario, contrasenia, " +
                "rol FROM usuarios WHERE nombre_usuario = ? AND contrasenia = ?";

        try (
                Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuarios user = new Usuarios();
                    user.setId(rs.getLong("id"));
                    user.setNombreUsuario(rs.getString("nombre_usuario"));
                    user.setRol(rs.getString("rol"));
                    return user; // Éxito: Retorna el usuario encontrado
                }
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error en el proceso de login", e);
        }
        return null; // Fallo: Usuario o clave incorrectos
    }

    @Override
    public Long guardar(Usuarios usuario) throws BaseDatosException {
        if (usuario.getId() == null){
            return insertar(usuario);
        }else{
            System.out.println("No se pude insertar por el momento");
            return usuario.getId();
        }
    }

    @Override
    public Usuarios buscarPorId(Long id) throws BaseDatosException {
        return null;
    }


    private Long insertar(Usuarios usuario) throws BaseDatosException{
        String sql = "INSERT INTO usuarios(nombre, apellido, cedula, "+
                "profesion, telefono, direccion, nombre_usuario, contrasenia, rol)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1,usuario.getNombre());
            stmt.setString(2,usuario.getApellido());
            stmt.setString(3,usuario.getCedula());
            stmt.setString(4,usuario.getProfesion());
            stmt.setString(5,usuario.getTelefono());
            stmt.setString(6,usuario.getDireccion());
            stmt.setString(7,usuario.getNombreUsuario());
            stmt.setString(8, usuario.getContrasenia());
            stmt.setString(9, usuario.getRol());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0){
                throw new BaseDatosException("No se pudo insertar los datos del usuario.");
            }

            //Para obtener el id generado infe
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new BaseDatosException("No se pudo obtener el ID generado");
            }

        }catch (SQLException e){
            throw new BaseDatosException("Error al insertar un nuevo usuario: "+e.getMessage(), e);
        }finally {
            cerrarRecursos(conn,stmt,rs);
        }
    }

    public void actualizar (Usuarios usuarios) throws BaseDatosException{
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, cedula = ?, profesion = ?," +
                "telefono = ?, direccion = ?, nombre_usuario = ?, contrasenia = ?, rol = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,usuarios.getNombre());
            stmt.setString(2,usuarios.getApellido());
            stmt.setString(3,usuarios.getCedula());
            stmt.setString(4,usuarios.getProfesion());
            stmt.setString(5,usuarios.getTelefono());
            stmt.setString(6,usuarios.getDireccion());
            stmt.setString(7,usuarios.getNombreUsuario());
            stmt.setString(8, usuarios.getContrasenia());
            stmt.setString(9, usuarios.getRol());
            stmt.setLong(10, usuarios.getId());

            int fliasAfectadas = stmt.executeUpdate();

            if (fliasAfectadas == 0){
                throw new BaseDatosException("No se encontro al usuario con ID: "+ usuarios.getId());

            }
        }catch (SQLException e){
            throw new BaseDatosException("Error al actualizar el usuario: "+e.getMessage(), e);
        }finally {
            cerrarRecursos(conn,stmt,null);
        }
    }

    public Usuarios buscarPorCedula(String cedula) throws BaseDatosException {
        String sql = "SELECT * FROM usuarios where cedula = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,cedula);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearResults(rs);
            }

            return null;

        }catch (SQLException e){
            throw new BaseDatosException("Error al buscar usuario por cedula: "+e.getMessage(),e);
        }finally {
            cerrarRecursos(conn,stmt,rs);
        }
    }

    public List<Usuarios> obtenerListaUsuarios() throws BaseDatosException{
        String sql = "SELECT * FROM usuarios ORDER BY nombre, apellido DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuarios> usuarios = new ArrayList<>();

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()){
                usuarios.add(mapearResults(rs));
            }
            return usuarios;
        }catch (SQLException e){
            throw new BaseDatosException("Error al quere obtener los usuarios: "+e.getMessage(),e);
        }finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }

    @Override
    public boolean eliminar(Long id) throws BaseDatosException {
        String sql = "DELETE FROM usuarios where id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new BaseDatosException("Error al eliminar conductor: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
    }

    private void cerrarRecursos(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }

    private Usuarios mapearResults (ResultSet rs) throws SQLException{
        Usuarios usuarios = new Usuarios();

        usuarios.setId(rs.getLong("id"));
        usuarios.setNombre(rs.getString("nombre"));
        usuarios.setApellido(rs.getString("apellido"));
        usuarios.setCedula(rs.getString("cedula"));
        usuarios.setProfesion(rs.getString("profesion"));
        usuarios.setTelefono(rs.getString("telefono"));
        usuarios.setDireccion(rs.getString("direccion"));
        usuarios.setNombreUsuario(rs.getString("nombre_usuario"));
        usuarios.setContrasenia(rs.getString("contrasenia"));
        usuarios.setRol(rs.getString("rol"));

        Date fechaReg = rs.getDate("fecha_registro");
        if (fechaReg != null){
            usuarios.setFecha_registro(fechaReg.toLocalDate());
        }
        return usuarios;
    }
}

