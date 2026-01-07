package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.LicenciaController;
import ec.edu.sistemalicencias.model.entities.Conductor;
import ec.edu.sistemalicencias.model.entities.PruebaPsicometrica;
import ec.edu.sistemalicencias.model.exceptions.LicenciaException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

/**
 * Vista para registrar pruebas psicométricas.
 */
@SuppressWarnings("unused")
public class PruebasPsicometricasView extends JFrame {
    private final LicenciaController controller;
    private Conductor conductorActual;

    // Componentes enlazados desde el .form (UI Designer)
    private JPanel panelPrincipal;
    private JPanel panelBusqueda;
    private JPanel panelNotas;
    private JPanel panelResultados;
    private JPanel panelObservaciones;
    private JPanel panelBotones;
    private JScrollPane scrollInfo;
    private JScrollPane scrollObservaciones;
    private JTextField txtCedula;
    private JButton btnBuscar;
    private JTextArea txtInfoConductor;
    private JTextField txtNotaReaccion;
    private JTextField txtNotaAtencion;
    private JTextField txtNotaCoordinacion;
    private JTextField txtNotaPercepcion;
    private JTextField txtNotaPsicologica;
    private JButton btnCalcular;
    private JLabel lblPromedio;
    private JLabel lblResultado;
    private JTextArea txtObservaciones;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    public PruebasPsicometricasView(LicenciaController controller) {
        this.controller = controller;
        setTitle("Pruebas Psicométricas");
        setContentPane(panelPrincipal);
        setSize(700, 600);
        setLocationRelativeTo(null);
        configurarEventos();
    }

    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscarConductor());
        btnCalcular.addActionListener(e -> calcularPromedio());
        btnGuardar.addActionListener(e -> guardarPrueba());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void buscarConductor() {
        try {
            conductorActual = controller.buscarConductorPorCedula(txtCedula.getText().trim());
            if (conductorActual != null) {
                txtInfoConductor.setText(String.format("Nombre: %s\nCédula: %s\nEdad: %d años",
                        conductorActual.getNombreCompleto(),
                        conductorActual.getCedula(),
                        conductorActual.calcularEdad()));
            } else {
                controller.mostrarError("Conductor no encontrado");
                txtInfoConductor.setText("");
            }
        } catch (LicenciaException ex) {
            // Imprimir traza en consola para depuración
            ex.printStackTrace();
            controller.mostrarError("Error: " + ex.getMessage());
        }
    }

    private void calcularPromedio() {
        try {
            double reaccion = Double.parseDouble(txtNotaReaccion.getText().trim());
            double atencion = Double.parseDouble(txtNotaAtencion.getText().trim());
            double coordinacion = Double.parseDouble(txtNotaCoordinacion.getText().trim());
            double percepcion = Double.parseDouble(txtNotaPercepcion.getText().trim());
            double psicologica = Double.parseDouble(txtNotaPsicologica.getText().trim());

            double promedio = (reaccion + atencion + coordinacion + percepcion + psicologica) / 5.0;
            lblPromedio.setText(String.format("Promedio: %.2f", promedio));

            if (promedio >= 70) {
                lblResultado.setText("Estado: APROBADO");
                lblResultado.setForeground(Color.GREEN.darker());
            } else {
                lblResultado.setText("Estado: REPROBADO");
                lblResultado.setForeground(Color.RED);
            }
        } catch (NumberFormatException ex) {
            // Imprimir traza en consola para depuración
            ex.printStackTrace();
            controller.mostrarError("Ingrese todas las notas correctamente");
        }
    }

    private void guardarPrueba() {
        if (conductorActual == null) {
            controller.mostrarError("Debe buscar un conductor primero");
            return;
        }

        try {
            PruebaPsicometrica prueba = new PruebaPsicometrica(conductorActual.getId());
            prueba.setNotaReaccion(Double.parseDouble(txtNotaReaccion.getText().trim()));
            prueba.setNotaAtencion(Double.parseDouble(txtNotaAtencion.getText().trim()));
            prueba.setNotaCoordinacion(Double.parseDouble(txtNotaCoordinacion.getText().trim()));
            prueba.setNotaPercepcion(Double.parseDouble(txtNotaPercepcion.getText().trim()));
            prueba.setNotaPsicologica(Double.parseDouble(txtNotaPsicologica.getText().trim()));
            prueba.setObservaciones(txtObservaciones.getText().trim());

            Long id = controller.registrarPruebaPsicometrica(prueba);
            controller.mostrarExito("Prueba registrada exitosamente con ID: " + id +
                    "\nPromedio: " + String.format("%.2f", prueba.calcularPromedio()) +
                    "\nEstado: " + prueba.obtenerEstado());

            limpiarFormulario();

        } catch (NumberFormatException ex) {
            // Imprimir traza en consola para depuración
            ex.printStackTrace();
            controller.mostrarError("Error en formato de notas");
        } catch (LicenciaException ex) {
            // Imprimir traza en consola para depuración
            ex.printStackTrace();
            controller.mostrarError("Error: " + ex.getMessage());
        } catch (Exception ex) {
            // Captura cualquier excepción inesperada y muestra la traza
            ex.printStackTrace();
            controller.mostrarError("Ocurrió un error inesperado. Revisa la consola.");
        }
    }

    private void limpiarFormulario() {
        txtNotaReaccion.setText("");
        txtNotaAtencion.setText("");
        txtNotaCoordinacion.setText("");
        txtNotaPercepcion.setText("");
        txtNotaPsicologica.setText("");
        txtObservaciones.setText("");
        lblPromedio.setText("Promedio: --");
        lblResultado.setText("Estado: --");
        lblResultado.setForeground(Color.BLACK);
    }

}