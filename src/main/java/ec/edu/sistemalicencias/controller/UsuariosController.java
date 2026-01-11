package ec.edu.sistemalicencias.controller;

import ec.edu.sistemalicencias.model.entities.Usuarios;
import ec.edu.sistemalicencias.model.exceptions.UsuarioException;
import ec.edu.sistemalicencias.service.UsuariosService;

import java.util.List;

public class UsuariosController {
    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    public UsuariosController() {
        this.usuariosService = new UsuariosService();
    }

    public Long registrarUsuario(Usuarios usuarios) throws UsuarioException{
        return usuariosService.registrarUsuario(usuarios);
    }

    public void actualizarUsuario(Usuarios usuarios) throws UsuarioException{
        usuariosService.actualizarUsuario(usuarios);
    }

    public Usuarios buscarUsuarioPorCedula(String cedula) throws UsuarioException{
        return usuariosService.buscarPorCedula(cedula);
    }

    public Usuarios buscarUsuarioPorID(Long id) throws UsuarioException{
        return usuariosService.buscarPorId(id);
    }

    public Usuarios buscarUsuarioPorNombre(String nombre) throws  UsuarioException{
        return usuariosService.buscarNombre(nombre);
    }

    public Usuarios buscarUsuarioPorCuenta(String nombre_usuario) throws UsuarioException{
        return usuariosService.buscarNombreUsuario(nombre_usuario);
    }

    public void eliminarUsuario(Long id) throws UsuarioException {
        usuariosService.eliminarUsuarioPorID(id);
    }

    public void eliminarUsuarioPorCedula(String cedula) throws UsuarioException{
        usuariosService.eliminarUsuarioPorCedula(cedula);
    }

    public void eliminarUsuarioPorNombre(String nombre) throws UsuarioException{
        usuariosService.eliminaUsuarioPorNombre(nombre);
    }

    public void eliminarUsuarioPorCuenta(String nombre_usuario) throws UsuarioException{
        usuariosService.eliminarUsuarioPorCuenta(nombre_usuario);
    }

    public List<Usuarios> listarTodosUsuarios() throws UsuarioException {
        return usuariosService.obtenerTodosUsuarios();
    }
}
