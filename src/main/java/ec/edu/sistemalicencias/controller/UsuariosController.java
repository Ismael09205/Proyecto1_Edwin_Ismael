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
    public Usuarios buscarUsuarioPorNombre(String nombre) throws  UsuarioException{
        return usuariosService.buscarNombre(nombre);
    }
    public Usuarios buscarUsuarioPorCuenta(String nombre_usuario) throws UsuarioException{
        return usuariosService.buscarNombreUsuario(nombre_usuario);
    }

    public Usuarios buscarUsuarioPorID(Long id) throws UsuarioException{
        return usuariosService.buscarPorId(id);
    }

    public void eliminarUsuario(Long id) throws UsuarioException {
        usuariosService.eliminarUsuario(id);
    }

    public List<Usuarios> listarTodosUsuarios() throws UsuarioException {
        return usuariosService.obtenerTodosUsuarios();
    }

}
