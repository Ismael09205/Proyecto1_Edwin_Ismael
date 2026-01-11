package ec.edu.sistemalicencias.view.crudView;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ec.edu.sistemalicencias.controller.UsuariosController;
import ec.edu.sistemalicencias.model.entities.Usuarios;
import ec.edu.sistemalicencias.model.exceptions.UsuarioException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelActualizarView {
    private JPanel PanelMainUpdate;
    private JTextField txtCredencial;
    private JButton btnBuscar;
    private JComboBox<String> cmbTipoCredencial;
    private JPanel PanelUpdateUser;
    private JPanel PanelBuscar;
    private JTable tblUsuariosEncontrados;
    private JLabel lblIngresarCredencial;

    private UsuariosController controller;
    private CardLayout cardLayout;
    private DefaultTableModel modeloTabla;
    private Usuarios usuarioSeleccionado;

    public PanelActualizarView(UsuariosController controller) {
        this.controller = controller;
        cardLayout = new CardLayout();
        PanelUpdateUser.setLayout(cardLayout);

        configurarTabla();
        btnBuscar.addActionListener(e -> buscarUsuario());
        tblUsuariosEncontrados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tblUsuariosEncontrados.getSelectedRow();
                if (filaSeleccionada != -1) {
                    usuarioSeleccionado = new Usuarios();
                    usuarioSeleccionado.setId((Long) modeloTabla.getValueAt(filaSeleccionada, 0));
                    usuarioSeleccionado.setNombre((String) modeloTabla.getValueAt(filaSeleccionada, 1));
                    usuarioSeleccionado.setApellido((String) modeloTabla.getValueAt(filaSeleccionada, 2));
                    usuarioSeleccionado.setCedula((String) modeloTabla.getValueAt(filaSeleccionada, 3));
                    usuarioSeleccionado.setProfesion((String) modeloTabla.getValueAt(filaSeleccionada, 4));
                    usuarioSeleccionado.setTelefono((String) modeloTabla.getValueAt(filaSeleccionada, 5));
                    usuarioSeleccionado.setDireccion((String) modeloTabla.getValueAt(filaSeleccionada, 6));
                    usuarioSeleccionado.setNombreUsuario((String) modeloTabla.getValueAt(filaSeleccionada, 7));
                    usuarioSeleccionado.setRol((String) modeloTabla.getValueAt(filaSeleccionada, 8));

                    cargarDatosParaActualizar();
                }
            }
        });
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Nombres", "Apellidos", "Cedula", "Profesión", "Teléfono", "Dirección", "Cuenta", "Rol"};
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
            JOptionPane.showMessageDialog(PanelMainUpdate, "Ingrese una credencial para buscar\n",
                    "ADVERTENCIA: ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipoBusqueda = (String) cmbTipoCredencial.getSelectedItem();
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
                    JOptionPane.showMessageDialog(PanelMainUpdate, "El ID debe ser un numero valido y existente en la base de datos.\n",
                            "ERROR DE FORMATO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (usuarios != null) {
                Object[] fila = {
                        usuarios.getId(),
                        usuarios.getNombre(),
                        usuarios.getApellido(),
                        usuarios.getCedula(),
                        usuarios.getProfesion(),
                        usuarios.getTelefono(),
                        usuarios.getDireccion(),
                        usuarios.getNombreUsuario(),
                        usuarios.getRol()
                };
                modeloTabla.addRow(fila);
            } else {
                JOptionPane.showMessageDialog(PanelMainUpdate, "Usuario no encontrado\n",
                        "INFORMACION: ", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (UsuarioException e) {
            JOptionPane.showMessageDialog(PanelMainUpdate, "Error al buscar usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosParaActualizar() {
        PanelUpdateUser.removeAll();
        ActualizarDatosView actualizarDatosView = new ActualizarDatosView(controller, usuarioSeleccionado, this);

        PanelUpdateUser.add(actualizarDatosView.$$$getRootComponent$$$(), "EDITAR");
        cardLayout.show(PanelUpdateUser, "EDITAR");

        PanelUpdateUser.revalidate();
        PanelUpdateUser.repaint();
    }


    public JPanel getPanelMainUpdate() {
        return PanelMainUpdate;
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
        PanelMainUpdate = new JPanel();
        PanelMainUpdate.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        PanelBuscar = new JPanel();
        PanelBuscar.setLayout(new GridLayoutManager(1, 4, new Insets(5, 5, 5, 5), -1, -1));
        PanelMainUpdate.add(PanelBuscar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        PanelBuscar.setBorder(BorderFactory.createTitledBorder(null, "Buscar usuario a actualizar", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        lblIngresarCredencial = new JLabel();
        lblIngresarCredencial.setText("Ingrese credencial:");
        PanelBuscar.add(lblIngresarCredencial, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtCredencial = new JTextField();
        PanelBuscar.add(txtCredencial, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cmbTipoCredencial = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("ID");
        defaultComboBoxModel1.addElement("Cedula");
        defaultComboBoxModel1.addElement("Nombre");
        defaultComboBoxModel1.addElement("Cuenta");
        cmbTipoCredencial.setModel(defaultComboBoxModel1);
        PanelBuscar.add(cmbTipoCredencial, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnBuscar = new JButton();
        btnBuscar.setText("Buscar");
        PanelBuscar.add(btnBuscar, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PanelUpdateUser = new JPanel();
        PanelUpdateUser.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        PanelMainUpdate.add(PanelUpdateUser, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        PanelMainUpdate.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(-1, 80), new Dimension(-1, 150), new Dimension(-1, 200), 0, false));
        tblUsuariosEncontrados = new JTable();
        scrollPane1.setViewportView(tblUsuariosEncontrados);
        final Spacer spacer1 = new Spacer();
        PanelMainUpdate.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return PanelMainUpdate;
    }

}
