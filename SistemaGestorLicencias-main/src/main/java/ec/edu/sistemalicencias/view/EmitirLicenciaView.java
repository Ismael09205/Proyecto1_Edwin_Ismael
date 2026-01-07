package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.LicenciaController;
import ec.edu.sistemalicencias.model.TipoLicenciaConstantes;
import ec.edu.sistemalicencias.model.entities.Conductor;
import ec.edu.sistemalicencias.model.entities.Licencia;
import ec.edu.sistemalicencias.model.entities.PruebaPsicometrica;
import ec.edu.sistemalicencias.model.exceptions.LicenciaException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

/**
 * Vista para emitir licencias de conducir.
 */
@SuppressWarnings("unused")
public class EmitirLicenciaView extends JFrame {
    private final LicenciaController controller;
    private Conductor conductorActual;
    private PruebaPsicometrica pruebaActual;

    // Componentes enlazados desde el .form (UI Designer)
    private JPanel panelPrincipal;
    private JPanel panelBusqueda;
    private JPanel panelPrueba;
    private JPanel panelLicencia;
    private JPanel panelBotones;
    private JTextField txtCedula;
    private JButton btnBuscar;
    private JTextArea txtInfoConductor;
    private JScrollPane scrollInfo;
    private JLabel lblPruebaInfo;
    private JComboBox<String> cmbTipoLicencia;
    private JButton btnEmitir;
    private JButton btnCerrar;

    public EmitirLicenciaView(LicenciaController controller) {
        this.controller = controller;
        setTitle("Emitir Licencia de Conducir");
        setContentPane(panelPrincipal);
        setSize(650, 500);
        setLocationRelativeTo(null);
        inicializarDatos();
        configurarEventos();
    }

    private void inicializarDatos() {
        // Cargar tipos de licencia en el combo
        cmbTipoLicencia.setModel(new DefaultComboBoxModel<>(TipoLicenciaConstantes.NOMBRES_TIPOS_LICENCIA));
    }

    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscarConductor());
        btnEmitir.addActionListener(e -> emitirLicencia());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void buscarConductor() {
        try {
            conductorActual = controller.buscarConductorPorCedula(txtCedula.getText().trim());

            if (conductorActual == null) {
                controller.mostrarError("Conductor no encontrado");
                limpiarInfo();
                return;
            }

            // Mostrar info conductor
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre: ").append(conductorActual.getNombreCompleto()).append("\n");
            sb.append("Cédula: ").append(conductorActual.getCedula()).append("\n");
            sb.append("Edad: ").append(conductorActual.calcularEdad()).append(" años\n");
            sb.append("Documentos Validados: ").append(conductorActual.isDocumentosValidados() ? "SÍ" : "NO");
            txtInfoConductor.setText(sb.toString());

            // Buscar última prueba aprobada
            pruebaActual = controller.obtenerUltimaPruebaAprobada(conductorActual.getId());

            if (pruebaActual != null) {
                lblPruebaInfo.setText(String.format(
                        "<html>Prueba ID: %d | Promedio: %.2f | Estado: %s</html>",
                        pruebaActual.getId(),
                        pruebaActual.calcularPromedio(),
                        pruebaActual.obtenerEstado()
                ));
                lblPruebaInfo.setForeground(new Color(34, 139, 34));
            } else {
                lblPruebaInfo.setText("No tiene pruebas aprobadas");
                lblPruebaInfo.setForeground(Color.RED);
            }

        } catch (LicenciaException ex) {
            ex.printStackTrace();
            controller.mostrarError("Error: " + ex.getMessage());
        }
    }

    private void emitirLicencia() {
        if (conductorActual == null) {
            controller.mostrarError("Debe buscar un conductor primero");
            return;
        }

        try {
            // Obtener el tipo de licencia seleccionado (convertir índice a constante)
            int indiceSeleccionado = cmbTipoLicencia.getSelectedIndex();
            String tipoLicencia = TipoLicenciaConstantes.TIPOS_LICENCIA[indiceSeleccionado];
            Long pruebaId = pruebaActual != null ? pruebaActual.getId() : null;

            Licencia licencia = controller.emitirLicencia(conductorActual.getId(), tipoLicencia, pruebaId);

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("¡LICENCIA EMITIDA EXITOSAMENTE!\n\n");
            mensaje.append("Número: ").append(licencia.getNumeroLicencia()).append("\n");
            mensaje.append("Tipo: ").append(TipoLicenciaConstantes.obtenerNombre(licencia.getTipoLicencia())).append("\n");
            mensaje.append("Conductor: ").append(conductorActual.getNombreCompleto()).append("\n");
            mensaje.append("Válida hasta: ").append(licencia.getFechaVencimiento()).append("\n");

            controller.mostrarExito(mensaje.toString());

            // Preguntar si desea generar PDF
            if (controller.confirmar("¿Desea generar el documento PDF de la licencia?")) {
                controller.generarDocumentoLicenciaConDialogo(licencia.getId());
            }

            limpiarInfo();

        } catch (LicenciaException ex) {
            ex.printStackTrace();
            controller.mostrarError("Error al emitir licencia:\n" + ex.getMessage());
        }
    }

    private void limpiarInfo() {
        conductorActual = null;
        pruebaActual = null;
        txtCedula.setText("");
        txtInfoConductor.setText("");
        lblPruebaInfo.setText("Sin prueba psicométrica registrada");
        lblPruebaInfo.setForeground(Color.BLACK);
    }

}
