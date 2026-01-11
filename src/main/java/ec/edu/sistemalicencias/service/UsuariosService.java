package ec.edu.sistemalicencias.service;

import ec.edu.sistemalicencias.dao.UsuariosDAO;
import ec.edu.sistemalicencias.model.entities.Usuarios;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.exceptions.DocumentoInvalidoException;
import ec.edu.sistemalicencias.model.exceptions.UsuarioException;

import java.util.List;

public class UsuariosService {
    private final UsuariosDAO usuariosDAO;

    public UsuariosService() {
        this.usuariosDAO = new UsuariosDAO();
    }

    public Long registrarUsuario(Usuarios usuarios) throws UsuarioException {
        try {
            usuarios.validar();

            Usuarios usuariosExistente = usuariosDAO.buscarPorCedula(usuarios.getCedula());
            if (usuariosExistente != null) {
                throw new DocumentoInvalidoException("El usuario con la cedula " + usuarios.getCedula() + "ya esta registrado.");
            }

            Long id = usuariosDAO.guardar(usuarios);
            usuarios.setId(id);

            return id;
        } catch (DocumentoInvalidoException e) {
            throw new UsuarioException("Error de validacion de datos: " + e.getMessage(), e);
        } catch (BaseDatosException e) {
            e.printStackTrace();
            throw new UsuarioException("Error al registrar usuario", e);
        }

    }

    public void actualizarUsuario(Usuarios usuarios) throws UsuarioException {
        try {
            usuarios.validar();

            if (usuarios.getId() == null || usuariosDAO.buscarPorId(usuarios.getId()) == null) {
                throw new UsuarioException("El usuario no existe en el sistema");
            }
            usuariosDAO.actualizar(usuarios);
        } catch (DocumentoInvalidoException e) {
            throw new UsuarioException("Error de validacion de datos: " + e.getMessage(), e);
        } catch (BaseDatosException e) {
            throw new UsuarioException("Error al actualizar el usuario en la base de datos", e);
        }
    }

    public void eliminarUsuarioPorID(Long id) throws UsuarioException {
        try {
            if (id == null) {
                throw new UsuarioException("El ID del usuario es requerido para eliminar");
            }

            Usuarios usuario = usuariosDAO.buscarPorId(id);
            if (usuario == null ) {
                throw new UsuarioException("El usuario con ID " + id + " no existe");
            } else {
                usuariosDAO.eliminar(id);
            }
        } catch (BaseDatosException e) {
            throw new UsuarioException("Error al eliminar usuario de la base de datos", e);
        }
    }

    public void eliminarUsuarioPorCedula(String cedula) throws UsuarioException{
        try {
            if (cedula == null || cedula.isEmpty()){
                throw new UsuarioException("La cedula del usuario es necesario para elminar");
            }

            Usuarios usuario = usuariosDAO.buscarPorCedula(cedula);
            if (usuario == null){
                throw new UsuarioException("El usuario con la cedula: "+cedula+" no existe");
            }else {
                usuariosDAO.eliminarPorCedula(cedula);
            }
        }catch (BaseDatosException e){
            throw new UsuarioException("Error al eliminar usuario de la base de datos", e);
        }
    }

    public void eliminaUsuarioPorNombre(String nombre) throws UsuarioException{
        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new UsuarioException("El nombre del usuario es necesario para eliminar");
            }
            Usuarios usuario = usuariosDAO.buscarPorNombre(nombre);
            if (usuario == null) {
                throw new UsuarioException("El usuario con el nombre " + nombre + " no existe");
            } else {
                usuariosDAO.eliminarPorNombre(nombre);
            }
        }catch (BaseDatosException e){
            throw new UsuarioException("Error al eliminar usuario de la base de datos", e);
        }
    }

    public void eliminarUsuarioPorCuenta(String nombre_usuario) throws UsuarioException{
        try{
            if (nombre_usuario == null || nombre_usuario.isEmpty()){
                throw new UsuarioException("La cuenta del usuario es necesario para eliminar");
            }
            Usuarios usuario = usuariosDAO.buscarPorNombreUsuario(nombre_usuario);
            if (usuario == null){
                throw new UsuarioException("El usuario con la cuenta "+nombre_usuario+" no existe");
            }else{
                usuariosDAO.eliminarPorCuenta(nombre_usuario);
            }
        }catch (BaseDatosException e){
            throw new UsuarioException("Error al elimniar usuario de la base de datos", e);
        }
    }


    public Usuarios buscarPorId(Long id) throws UsuarioException {
        try {
            return usuariosDAO.buscarPorId(id);
        } catch (BaseDatosException e) {
            throw new UsuarioException("Error al buscar usuario", e);
        }
    }

    public Usuarios buscarPorCedula(String cedula) throws UsuarioException {
        try {
            return usuariosDAO.buscarPorCedula(cedula);
        } catch (BaseDatosException e) {
            throw new UsuarioException("Error al buscar usuario por cédula", e);
        }
    }

    public Usuarios buscarNombre(String nombre) throws UsuarioException {
        try {
            return usuariosDAO.buscarPorNombre(nombre);
        } catch (BaseDatosException e) {
            throw new UsuarioException("Error al buscar usuario por nombre", e);
        }
    }

    public Usuarios buscarNombreUsuario(String nombreUsuario) throws UsuarioException {
        try {
            return usuariosDAO.buscarPorNombreUsuario(nombreUsuario);
        } catch (BaseDatosException e) {
            throw new UsuarioException("Error al buscar un usuario por su cuenta de usuario", e);
        }
    }

    public List<Usuarios> obtenerTodosUsuarios() throws UsuarioException{
        try {
            return usuariosDAO.obtenerListaUsuarios();
        }catch (BaseDatosException e){
            throw new UsuarioException("Error al obtener los usuarios", e);
        }
    }
}
