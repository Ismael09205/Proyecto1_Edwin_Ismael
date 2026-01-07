package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.LicenciaController;
import ec.edu.sistemalicencias.model.entities.Conductor;
import ec.edu.sistemalicencias.model.exceptions.LicenciaException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Vista para validación de documentos de conductores.
 * Verifica la entrega de documentos requeridos para licencias no profesionales.
 */
@SuppressWarnings("unused")
public class ValidarDocumentosView extends JFrame {
    private final LicenciaController controller;
    private Conductor conductorActual;

    // Componentes enlazados desde el .form (UI Designer)
    private JPanel panelPrincipal;
    private JPanel panelBusqueda;
    private JPanel panelDocumentos;
    private JPanel panelBotones;
    private JTextField txtCedula;
    private JButton btnBuscar;
    private JTextArea txtInfo;
    private JScrollPane scrollInfo;
    private JCheckBox chkTituloConductor;
    private JCheckBox chkPermisoAprendizaje;
    private JCheckBox chkExamenPsicosensometrico;
    private JCheckBox chkCedulaPapeleta;
    private JCheckBox chkCertificadoSangre;
    private JCheckBox chkComprobantePago;
    private JCheckBox chkTurnoImpreso;
    private JCheckBox chkCertificadoEducacion;
    private JButton btnMarcarTodos;
    private JButton btnDesmarcarTodos;
    private JButton btnGuardar;
    private JButton btnCerrar;

    public ValidarDocumentosView(LicenciaController controller) {
        this.controller = controller;
        setTitle("Validar Documentos - Licencia No Profesional");
        setContentPane(panelPrincipal);
        setSize(750, 700);
        setLocationRelativeTo(null);
        configurarEventos();
    }

    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscarConductor());
        btnMarcarTodos.addActionListener(e -> marcarTodosDocumentos(true));
        btnDesmarcarTodos.addActionListener(e -> marcarTodosDocumentos(false));
        btnGuardar.addActionListener(e -> guardarValidacion());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void marcarTodosDocumentos(boolean seleccionar) {
        chkTituloConductor.setSelected(seleccionar);
        chkPermisoAprendizaje.setSelected(seleccionar);
        chkExamenPsicosensometrico.setSelected(seleccionar);
        chkCedulaPapeleta.setSelected(seleccionar);
        chkCertificadoSangre.setSelected(seleccionar);
        chkComprobantePago.setSelected(seleccionar);
        chkTurnoImpreso.setSelected(seleccionar);
        chkCertificadoEducacion.setSelected(seleccionar);
    }

    private void buscarConductor() {
        try {
            conductorActual = controller.buscarConductorPorCedula(txtCedula.getText().trim());
            if (conductorActual != null) {
                mostrarInfoConductor();
            } else {
                controller.mostrarError("No se encontró el conductor con esa cédula");
                txtInfo.setText("");
                limpiarCheckboxes();
            }
        } catch (LicenciaException ex) {
            controller.mostrarError("Error: " + ex.getMessage());
        }
    }

    private void mostrarInfoConductor() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cédula: ").append(conductorActual.getCedula()).append("\n");
        sb.append("Nombre: ").append(conductorActual.getNombreCompleto()).append("\n");
        sb.append("Edad: ").append(conductorActual.calcularEdad()).append(" años\n");
        sb.append("Dirección: ").append(conductorActual.getDireccion()).append("\n");
        sb.append("Teléfono: ").append(conductorActual.getTelefono()).append("\n");
        sb.append("Estado actual: ").append(conductorActual.isDocumentosValidados() ? "DOCUMENTOS VALIDADOS" : "PENDIENTE DE VALIDACIÓN");

        txtInfo.setText(sb.toString());

        if (conductorActual.isDocumentosValidados()) {
            marcarTodosDocumentos(true);
        } else {
            limpiarCheckboxes();
        }
    }

    private void limpiarCheckboxes() {
        marcarTodosDocumentos(false);
    }

    private boolean todosDocumentosValidados() {
        return chkTituloConductor.isSelected() &&
                chkPermisoAprendizaje.isSelected() &&
                chkExamenPsicosensometrico.isSelected() &&
                chkCedulaPapeleta.isSelected() &&
                chkCertificadoSangre.isSelected() &&
                chkComprobantePago.isSelected() &&
                chkTurnoImpreso.isSelected() &&
                chkCertificadoEducacion.isSelected();
    }

    private String obtenerDocumentosFaltantes() {
        StringBuilder faltantes = new StringBuilder();
        if (!chkTituloConductor.isSelected()) faltantes.append("- Título de conductor\n");
        if (!chkPermisoAprendizaje.isSelected()) faltantes.append("- Permiso de aprendizaje\n");
        if (!chkExamenPsicosensometrico.isSelected()) faltantes.append("- Examen psicosensométrico\n");
        if (!chkCedulaPapeleta.isSelected()) faltantes.append("- Cédula y papeleta de votación\n");
        if (!chkCertificadoSangre.isSelected()) faltantes.append("- Certificado de tipo sanguíneo\n");
        if (!chkComprobantePago.isSelected()) faltantes.append("- Comprobante de pago\n");
        if (!chkTurnoImpreso.isSelected()) faltantes.append("- Turno impreso\n");
        if (!chkCertificadoEducacion.isSelected()) faltantes.append("- Certificado de educación básica\n");
        return faltantes.toString();
    }

    private void guardarValidacion() {
        if (conductorActual == null) {
            controller.mostrarError("Debe buscar un conductor primero");
            return;
        }

        try {
            boolean validacionCompleta = todosDocumentosValidados();
            String observaciones = "";

            if (!validacionCompleta) {
                observaciones = "Documentos faltantes:\n" + obtenerDocumentosFaltantes();
            }

            conductorActual.setDocumentosValidados(validacionCompleta);
            conductorActual.setObservaciones(observaciones);

            controller.actualizarConductor(conductorActual);

            if (validacionCompleta) {
                controller.mostrarExito("Validación completada exitosamente.\nTodos los documentos han sido verificados.");
            } else {
                controller.mostrarError("Validación guardada como INCOMPLETA.\n\n" + observaciones);
            }

            mostrarInfoConductor();

        } catch (LicenciaException ex) {
            controller.mostrarError("Error al guardar: " + ex.getMessage());
        }
    }

}
