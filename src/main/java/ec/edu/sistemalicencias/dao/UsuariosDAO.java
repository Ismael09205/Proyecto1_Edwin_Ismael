package ec.edu.sistemalicencias.dao;

import ec.edu.sistemalicencias.config.DatabaseConfig;
import ec.edu.sistemalicencias.model.entities.Usuarios;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.interfaces.Persistible;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO implements Persistible<Usuarios> {
    private final DatabaseConfig dbConfig;

    public UsuariosDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    //Metodo para logearse
    public Usuarios login(String nombreUsuario, String contrasena) throws BaseDatosException {
        String sql = "SELECT id, usuario, contrasenia, rol FROM usuarios WHERE usuario = ? AND contrasenia = ?";

        try (
                Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuarios user = new Usuarios();
                    user.setId(rs.getLong("id"));
                    user.setNombreUsuario(rs.getString("usuario"));
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
    public Long guardar(Usuarios entidad) throws BaseDatosException {
        return 0L;
    }

    @Override
    public Usuarios buscarPorId(Long id) throws BaseDatosException {
        return null;
    }

    @Override
    public boolean eliminar(Long id) throws BaseDatosException {
        return false;
    }
}

