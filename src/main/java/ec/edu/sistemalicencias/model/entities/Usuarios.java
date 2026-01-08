package ec.edu.sistemalicencias.model.entities;

public class Usuarios {
    private Long id;
    private String nombreUsuario;
    private String contrasena;
    private String rol;

    // Constructor vacío (necesario para el DAO)
    public Usuarios() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}