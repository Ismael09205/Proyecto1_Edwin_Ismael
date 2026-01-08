package ec.edu.sistemalicencias.view;

import ec.edu.sistemalicencias.controller.LicenciaController;
import ec.edu.sistemalicencias.dto.ReporteUsuarioDTO;
import ec.edu.sistemalicencias.model.entities.Conductor;
import ec.edu.sistemalicencias.model.entities.Licencia;
import ec.edu.sistemalicencias.model.exceptions.LicenciaException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
* Vista para generar reporte de todos los usuarios registrados en el sistema
* Permite generar un docuemento que muestra al usuario, fecha_registro, emitido o no emitido la licencia.
* */
public class GenerarReporteView extends JFrame{
    private JButton btnGeneratePDF;
    private JButton btnGoBack;
    private JComboBox<String> cmbFilterUsers;
    private JPanel PanelMainReport;
    private JLabel lblDescription;
    private JTable tblUsers;
    private JLabel lblViewUsers;
    private JScrollPane scrollTableUsers;
    private JPanel panelButtons;
    private JPanel panelFilter;

    private final String Todos = "Todos";
    private final String Emitidos = "Emitidos";
    private final String NoEmitidos = "No emitidos";
    private final String Vencidos = "Vencidos";

    private final LicenciaController controller;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DefaultTableModel modeloTabla;
    private List<ReporteUsuarioDTO> listaCompletaUsuarios;

    public GenerarReporteView(LicenciaController controller) {
        this.controller = controller;
        setTitle("Reporte de usuarios");
        setContentPane(PanelMainReport);
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void inicializarDatos(){
        cmbFilterUsers.removeAllItems();
        cmbFilterUsers.addItem("Todos");
        cmbFilterUsers.addItem("Emitidos");
        cmbFilterUsers.addItem("No Emitidos");
        cmbFilterUsers.addItem("Vencidos");

        cargarDatosIniciales();

    }

    private void iniciarTabla(){
        String[] columnas = {"ID","Cédula", "Nombres", "Apellidos","Teléfono", "Fecha Registro","Estado Licencia"};
        modeloTabla = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int row, int column){return false;}
        };

        tblUsers.setModel(modeloTabla);
        tblUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void ConfigEventos(){
        //btnGeneratePDF.addActionListener();
        btnGoBack.addActionListener(e -> dispose());
        cmbFilterUsers.addActionListener(e -> {
            String filtroSeleccionado = (String) cmbFilterUsers.getSelectedItem();

            if (listaCompletaUsuarios == null) return;
            if ("Todos".equals(filtroSeleccionado)) {
                llenarTabla(listaCompletaUsuarios);
            } else {
                List<ReporteUsuarioDTO> filtrada = new ArrayList<>();
                for (ReporteUsuarioDTO u : listaCompletaUsuarios) {
                    if (u.getEstadoLicencia().equals(filtroSeleccionado)) {
                        filtrada.add(u);
                    }
                }
                llenarTabla(filtrada);
            }
        });
    }

    private void cargarDatosIniciales() {
        try {
            listaCompletaUsuarios = controller.obtenerReporte();
            llenarTabla(listaCompletaUsuarios);

        } catch (LicenciaException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void llenarTabla(List<ReporteUsuarioDTO> listaAMostrar) {
        modeloTabla.setRowCount(0);

        for (ReporteUsuarioDTO u : listaAMostrar) {
            Object[] fila = {
                    u.getId(),
                    u.getCedula(),
                    u.getNombres(),
                    u.getApellidos(),
                    u.getTelefono(),
                    (u.getFechaRegistro() != null) ? u.getFechaRegistro().format(dateFormatter) : "Sin fecha",
                    u.getEstadoLicencia()
            };
            modeloTabla.addRow(fila);
        }
    }
}

