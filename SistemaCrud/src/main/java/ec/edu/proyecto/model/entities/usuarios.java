package main.java.ec.edu.proyecto.model.entities;

public class usuarios {
    private String nombre;
    private String apellido;
    private String cedula;
    private String fechaNacimiento;
    private String telefono;
    private String contrasenia;

    public usuarios(String nombre, String apellido, String cedula, String fechaNacimiento, String telefono, String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
    }
}
