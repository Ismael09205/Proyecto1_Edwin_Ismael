package ec.edu.sistemalicencias.model.entities;

import ec.edu.sistemalicencias.model.exceptions.DatosInvalidosException;
import ec.edu.sistemalicencias.model.exceptions.DocumentoInvalidoException;
import ec.edu.sistemalicencias.model.interfaces.Validable;

import java.time.LocalDate;

public class Usuarios implements Validable {
    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String profesion;
    private String telefono;
    private String direccion;
    private String nombreUsuario;
    private String contrasenia;
    private String rol;


    private LocalDate fecha_registro;

    public Usuarios() {
        this.fecha_registro = LocalDate.now();
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null) {
            throw new DatosInvalidosException("La cédula es obligatoria");
        }
        this.cedula = cedula.trim();
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        if (profesion == null || profesion.trim().isEmpty()){
            throw new DatosInvalidosException("La profesión no puede estar vacía.");
        }
        this.profesion = profesion.trim().toUpperCase();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("\\d{10}")){
            throw new DatosInvalidosException("El numero de telefono debe tener 10 dígitos");
        }
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()){
            throw new DatosInvalidosException("La dirección no puede estar vacia.");
        }
        this.direccion = direccion.trim().toUpperCase();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()){
            throw new DatosInvalidosException("El campo del nombre de usuario es obligatorio");
        }
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.length() <= 8){
            throw new DatosInvalidosException("La contrasña debe tener mas de 8 carácteres.");
        }
        boolean tieneNumero = contrasenia.matches(".*\\d.*");
        boolean tieneMayus = !contrasenia.equals(contrasenia.toLowerCase());

        if (!tieneMayus || !tieneNumero){
            throw new DatosInvalidosException("La contraseña debe tener almenos una mayuscula y un numero");
        }

        this.contrasenia = contrasenia;

    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        if (rol == null || rol.trim().isEmpty()){
            throw new DatosInvalidosException("El rol no puede estar vacío(Es obligatorio)");
        }
        String rolLimpio = rol.trim();
        if (!rolLimpio.equals("Administrador") && !rolLimpio.equals("Analista")){
            throw new DatosInvalidosException("El rol debe ser 'Administrador' o 'Analista'");
        }
        this.rol = rolLimpio;
    }

    public String getNombreCompleto(){
        return nombre+" "+apellido;
    }

    private boolean validarCedulaEc(String cedula){
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        try {
            // Algoritmo de validación del dígito verificador
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int suma = 0;

            for (int i = 0; i < 9; i++) {
                int valor = Integer.parseInt(cedula.substring(i, i + 1)) * coeficientes[i];
                if (valor >= 10) {
                    valor -= 9;
                }
                suma += valor;
            }

            int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
            int residuo = suma % 10;
            int resultado = residuo == 0 ? 0 : 10 - residuo;

            return resultado == digitoVerificador;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean validar() throws DocumentoInvalidoException {
        StringBuilder errores = new StringBuilder();

        if (nombre == null || nombre.trim().isEmpty()){
            errores.append("- Nombre obligatorio\n");
        }

        if (apellido == null || apellido.trim().isEmpty()){
            errores.append("- Apellido obligatorio\n");
        }

        if (cedula == null || !cedula.matches("\\d{10}") || !validarCedulaEc(cedula)){
            errores.append("- Cedula obligatoria\n");
        }

        if (telefono == null || telefono.trim().isEmpty()){
            errores.append("- El telefono es obligatorio\n");
        }

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()){
            errores.append("- Nombre de usuario obligatorio\n");
        }

        if (contrasenia == null || contrasenia.isEmpty()){
            errores.append("- Contraseña obligatoria\n");
        }

        if(rol == null || rol.trim().isEmpty()){
            errores.append("- EL rol es necesario para crear usuarios\n");
        }
        if (errores.length() > 0) {
            throw new DocumentoInvalidoException("Errores de validación del usuario:\n" + errores.toString());
        }
        return true;
    }

    @Override
    public String obtenerMensajeValidacion() {
        try {
            if (validar()) {
                return "Usuario validado correctamente: " + getNombreCompleto();
            }
        } catch (DocumentoInvalidoException e) {
            return e.getMessage();
        }
        return "Error en la validación";
    }


}