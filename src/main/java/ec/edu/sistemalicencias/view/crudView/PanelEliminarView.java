package ec.edu.sistemalicencias.view.crudView;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.UsuariosController;
import ec.edu.sistemalicencias.model.entities.Usuarios;
import ec.edu.sistemalicencias.model.exceptions.UsuarioException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelEliminarView {
    private JPanel panel1;
    private JTable tblUsuariosEncontrados;
    private JButton btnEliminar;
    private JTextField txtCredencial;
    private JComboBox<String> cmbTipoBusqueda;
    private JButton btnBuscarUsuario;
    private JPanel PanelBuscarEliminar;
    private JLabel lblIngresarCredencial;
    private JScrollPane JScrollTable;

    private UsuariosController controller;
    private DefaultTableModel modeloTabla;

    public PanelEliminarView(UsuariosController controller) {

        this.controller = controller;
        configurarTabal();
        btnBuscarUsuario.addActionListener(e -> buscarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
    }

    private void configurarTabal() {
        String[] columnas = {"ID", "Nombres", "Apellidos", "Cedula", "Usuario", "Rol"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuariosEncontrados.setModel(modeloTabla);
        tblUsuariosEncontrados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void buscarUsuario() {
        String credencial = txtCredencial.getText().trim();

        if (credencial.isEmpty()) {
            JOptionPane.showMessageDialog(panel1, "Ingrese una credencial para buscar\n",
                    "ADVERTENCIA: ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipoBusqueda = (String) cmbTipoBusqueda.getSelectedItem();
        List<Usuarios> usuariosObtenidos = new ArrayList<>();
        try {
            modeloTabla.setRowCount(0);
            Usuarios usuarios = null;

            if (tipoBusqueda.equalsIgnoreCase("Cedula")) {
                usuarios = controller.buscarUsuarioPorCedula(credencial);
            } else if (tipoBusqueda.equalsIgnoreCase("Nombre")) {
                usuarios = controller.buscarUsuarioPorNombre(credencial);
            } else if (tipoBusqueda.equalsIgnoreCase("Cuenta")) {
                usuarios = controller.buscarUsuarioPorCuenta(credencial);
            } else if (tipoBusqueda.equalsIgnoreCase("ID")) {
                try {
                    Long credencialID = Long.valueOf(txtCredencial.getText());
                    usuarios = controller.buscarUsuarioPorID(credencialID);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(panel1, "El ID debe ser un numero valido y existente en la base de datos.\n",
                            "ERROR DE FORMATO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            modeloTabla.setRowCount(0);
            if (usuarios != null) {
                Object[] fila = {
                        usuarios.getId(),
                        usuarios.getNombre(),
                        usuarios.getApellido(),
                        usuarios.getCedula(),
                        usuarios.getNombreUsuario(),
                        usuarios.getRol()
                };
                modeloTabla.addRow(fila);
            } else {
                JOptionPane.showMessageDialog(panel1, "Usuario no encontrado\n",
                        "INFORMACION: ", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (UsuarioException e) {
            JOptionPane.showMessageDialog(panel1, "Error al buscar usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tblUsuariosEncontrados.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(panel1, "Seleccione un usuario de la tabla",
                    "ADVERTENCIA: ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(panel1, "¿Estas seguro de querer eliminar este usuario?",
                "Confirmar seleccion", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Long idUsuario = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
                controller.eliminarUsuario(idUsuario);
                modeloTabla.removeRow(filaSeleccionada);
                txtCredencial.setText("");
                JOptionPane.showMessageDialog(panel1, "Usuario eliminado correctamente",
                        "EXITO AL ELIMINAR", JOptionPane.ERROR_MESSAGE);
            } catch (UsuarioException e) {
                JOptionPane.showMessageDialog(panel1, "Error al eliminar el usuario: " + e.getMessage(),
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getPanel1() {
        return panel1;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        PanelBuscarEliminar = new JPanel();
        PanelBuscarEliminar.setLayout(new GridLayoutManager(1, 4, new Insets(5, 5, 5, 5), -1, -1));
        panel1.add(PanelBuscarEliminar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, true));
        PanelBuscarEliminar.setBorder(BorderFactory.createTitledBorder(null, "Buscar usuario a eliminar", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        lblIngresarCredencial = new JLabel();
        lblIngresarCredencial.setText("Ingrese credencial:");
        PanelBuscarEliminar.add(lblIngresarCredencial, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtCredencial = new JTextField();
        PanelBuscarEliminar.add(txtCredencial, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cmbTipoBusqueda = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("ID");
        defaultComboBoxModel1.addElement("Cedula");
        defaultComboBoxModel1.addElement("Nombre");
        defaultComboBoxModel1.addElement("Cuenta");
        cmbTipoBusqueda.setModel(defaultComboBoxModel1);
        PanelBuscarEliminar.add(cmbTipoBusqueda, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnBuscarUsuario = new JButton();
        btnBuscarUsuario.setText("Buscar");
        PanelBuscarEliminar.add(btnBuscarUsuario, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JScrollTable = new JScrollPane();
        panel1.add(JScrollTable, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 70), new Dimension(-1, 130), new Dimension(-1, 150), 0, false));
        tblUsuariosEncontrados = new JTable();
        JScrollTable.setViewportView(tblUsuariosEncontrados);
        btnEliminar = new JButton();
        btnEliminar.setText("Eliminar");
        panel1.add(btnEliminar, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
