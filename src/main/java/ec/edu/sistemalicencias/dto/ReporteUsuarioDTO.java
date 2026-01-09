package ec.edu.sistemalicencias.dto;

import java.time.LocalDate;

public class ReporteUsuarioDTO {
    private Long id;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String telefono;
    private LocalDate fechaRegistro;
    private String estadoLicencia;
    private boolean tieneLicenciaEmitida;

    public ReporteUsuarioDTO(Long id, String cedula, String nombres,
                             String apellidos, boolean tieneLicenciaEmitida,
                             String estadoLicencia, LocalDate fechaRegistro, String telefono) {
        this.id = id;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tieneLicenciaEmitida = tieneLicenciaEmitida;
        this.estadoLicencia = estadoLicencia;
        this.fechaRegistro = fechaRegistro;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getEstadoLicencia() {
        return estadoLicencia;
    }

    public boolean isTieneLicenciaEmitida() {
        return tieneLicenciaEmitida;
    }
}
