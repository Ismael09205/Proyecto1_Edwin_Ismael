package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.LicenciaController;
import ec.edu.sistemalicencias.model.TipoLicenciaConstantes;
import ec.edu.sistemalicencias.model.entities.Conductor;
import ec.edu.sistemalicencias.model.entities.Licencia;
import ec.edu.sistemalicencias.model.exceptions.LicenciaException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para consultar licencias emitidas.
 */
@SuppressWarnings("unused")
public class ConsultarLicenciasView extends JFrame {
    private final LicenciaController controller;
    private DefaultTableModel modeloTabla;

    // Componentes enlazados desde el .form (UI Designer)
    private JPanel panelPrincipal;
    private JPanel panelBusqueda;
    private JPanel panelBotones;
    private JScrollPane scrollTabla;
    private JComboBox<String> cmbTipoBusqueda;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JTable tableLicencias;
    private JButton btnGenerarPDF;
    private JButton btnCerrar;

    public ConsultarLicenciasView(LicenciaController controller) {
        this.controller = controller;
        setTitle("Consultar Licencias");
        setContentPane(panelPrincipal);
        setSize(900, 550);
        setLocationRelativeTo(null);
        inicializarTabla();
        configurarEventos();
        cargarTodasLicencias();
    }

    private void inicializarTabla() {
        String[] columnas = {"ID", "Número", "Conductor", "Cédula", "Tipo", "Emisión", "Vencimiento", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLicencias.setModel(modeloTabla);
        tableLicencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscarLicencias());
        btnActualizar.addActionListener(e -> cargarTodasLicencias());
        btnGenerarPDF.addActionListener(e -> generarPDFSeleccionada());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void buscarLicencias() {
        String criterio = txtBuscar.getText().trim();

        if (criterio.isEmpty()) {
            cargarTodasLicencias();
            return;
        }

        try {
            List<Licencia> licencias = new ArrayList<>();

            // Buscar por número de licencia
            Licencia licPorNumero = controller.buscarLicenciaPorNumero(criterio);
            if (licPorNumero != null) {
                licencias.add(licPorNumero);
            }

            // Si no encontró por número, buscar por cédula del conductor
            if (licencias.isEmpty()) {
                Conductor conductor = controller.buscarConductorPorCedula(criterio);
                if (conductor != null) {
                    licencias = controller.obtenerLicenciasConductor(conductor.getId());
                }
            }

            if (licencias.isEmpty()) {
                controller.mostrarError("No se encontraron licencias con el criterio: " + criterio);
            }

            cargarLicenciasEnTabla(licencias);

        } catch (LicenciaException ex) {
            controller.mostrarError("Error: " + ex.getMessage());
        }
    }

    private void cargarTodasLicencias() {
        try {
            List<Licencia> licencias = controller.obtenerTodasLicencias();
            cargarLicenciasEnTabla(licencias);
        } catch (LicenciaException ex) {
            controller.mostrarError("Error: " + ex.getMessage());
        }
    }

    private void cargarLicenciasEnTabla(List<Licencia> licencias) {
        modeloTabla.setRowCount(0);

        for (Licencia lic : licencias) {
            try {
                Conductor conductor = controller.buscarConductorPorId(lic.getConductorId());

                Object[] fila = {
                        lic.getId(),
                        lic.getNumeroLicencia(),
                        conductor != null ? conductor.getNombreCompleto() : "N/A",
                        conductor != null ? conductor.getCedula() : "N/A",
                        TipoLicenciaConstantes.obtenerNombre(lic.getTipoLicencia()),
                        lic.getFechaEmision(),
                        lic.getFechaVencimiento(),
                        lic.obtenerEstado()
                };
                modeloTabla.addRow(fila);

            } catch (LicenciaException ex) {
                System.err.println("Error al cargar conductor: " + ex.getMessage());
            }
        }
    }

    private void generarPDFSeleccionada() {
        int filaSeleccionada = tableLicencias.getSelectedRow();

        if (filaSeleccionada == -1) {
            controller.mostrarError("Debe seleccionar una licencia de la tabla");
            return;
        }

        Long licenciaId = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        controller.generarDocumentoLicenciaConDialogo(licenciaId);
    }

}
