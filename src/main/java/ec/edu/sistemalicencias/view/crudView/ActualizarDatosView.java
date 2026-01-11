package ec.edu.sistemalicencias.view.crudView;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.UsuariosController;
import ec.edu.sistemalicencias.model.entities.Usuarios;

import javax.swing.*;
import java.awt.*;

public class ActualizarDatosView {
    private JTextField txtNombre;
    private JTextField txtProfesion;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtCedula;
    private JTextField txtDireccion;
    private JTextField txtNombreUsuario;
    private JButton btnActualizar;
    private JPanel PanelActualizarDatos;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCedula;
    private JLabel lblNombreUsuario;
    private JLabel lblProfesion;
    private JLabel lblTelefono;
    private JLabel lblDireccion;
    private JRadioButton rbtnAnalista;
    private JRadioButton rbtnAdministrador;

    private UsuariosController controller;
    private Usuarios usuarios;
    private PanelActualizarView panelPadre;

    public ActualizarDatosView(UsuariosController controller, Usuarios usuarios, PanelActualizarView panelPadre) {
        this.controller = controller;
        this.usuarios = usuarios;
        this.panelPadre = panelPadre;

        grupoDeBotones();
        cargarDatosUsuario();
        configurarEvento();
    }

    private void cargarDatosUsuario() {
        txtNombre.setText(usuarios.getNombre());
        txtApellido.setText(usuarios.getApellido());
        txtCedula.setText(usuarios.getCedula());
        txtProfesion.setText(usuarios.getProfesion());
        txtTelefono.setText(usuarios.getTelefono());
        txtDireccion.setText(usuarios.getDireccion());
        txtNombreUsuario.setText(usuarios.getNombreUsuario());
        if ("Administrador".equalsIgnoreCase(usuarios.getRol())) {
            rbtnAdministrador.setSelected(true);
        } else if ("Analista".equalsIgnoreCase(usuarios.getRol())) {
            rbtnAnalista.setSelected(true);
        }

    }

    private void grupoDeBotones() {
        ButtonGroup cmbsGrupoRol = new ButtonGroup();
        cmbsGrupoRol.add(rbtnAdministrador);
        cmbsGrupoRol.add(rbtnAnalista);
    }

    private void configurarEvento() {
        btnActualizar.addActionListener(e -> actualizarUsuario());
    }

    private void actualizarUsuario() {
        if (!validarCampos()) {
            return;
        }
        try {
            usuarios.setNombre(txtNombre.getText());
            usuarios.setApellido(txtApellido.getText());
            usuarios.setCedula(txtCedula.getText());
            usuarios.setProfesion(txtProfesion.getText());
            usuarios.setTelefono(txtTelefono.getText());
            usuarios.setDireccion(txtDireccion.getText());
            usuarios.setNombreUsuario(txtNombreUsuario.getText());

            if (rbtnAdministrador.isSelected()) {
                usuarios.setRol("Administrador");
            } else if (rbtnAnalista.isSelected()) {
                usuarios.setRol("Analista");
            }

            controller.actualizarUsuario(usuarios);
            JOptionPane.showMessageDialog(PanelActualizarDatos,
                    "El usuario se actualizo correctamente",
                    "EXITO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    PanelActualizarDatos,
                    e.getMessage(),
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre no puede estar vacío");
            return false;
        }

        if (txtApellido.getText().trim().isEmpty()) {
            mostrarError("El apellido no puede estar vacío");
            return false;
        }

        if (txtCedula.getText().trim().isEmpty()) {
            mostrarError("La cédula es obligatoria");
            return false;
        }

        if (txtProfesion.getText().trim().isEmpty()) {
            mostrarError("La profesión es obligatoria");
            return false;
        }

        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarError("El teléfono es obligatorio");
            return false;
        }

        if (txtDireccion.getText().trim().isEmpty()) {
            mostrarError("La dirección es obligatoria");
            return false;
        }

        if (txtNombreUsuario.getText().trim().isEmpty()) {
            mostrarError("El nombre de usuario es obligatorio");
            return false;
        }

        if (!rbtnAdministrador.isSelected() && !rbtnAnalista.isSelected()) {
            mostrarError("Debe seleccionar un rol");
            return false;
        }

        return true;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                PanelActualizarDatos,
                mensaje,
                "Validación",
                JOptionPane.WARNING_MESSAGE
        );
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
        PanelActualizarDatos = new JPanel();
        PanelActualizarDatos.setLayout(new GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        lblNombre = new JLabel();
        lblNombre.setText("Nombre:");
        PanelActualizarDatos.add(lblNombre, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblProfesion = new JLabel();
        lblProfesion.setText("Profesion:");
        PanelActualizarDatos.add(lblProfesion, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtNombre = new JTextField();
        PanelActualizarDatos.add(txtNombre, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtProfesion = new JTextField();
        PanelActualizarDatos.add(txtProfesion, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtApellido = new JTextField();
        PanelActualizarDatos.add(txtApellido, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblApellido = new JLabel();
        lblApellido.setText("Apellido:");
        PanelActualizarDatos.add(lblApellido, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTelefono = new JLabel();
        lblTelefono.setText("Télefono:");
        PanelActualizarDatos.add(lblTelefono, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTelefono = new JTextField();
        PanelActualizarDatos.add(txtTelefono, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCedula = new JLabel();
        lblCedula.setText("Cedula:");
        PanelActualizarDatos.add(lblCedula, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtCedula = new JTextField();
        PanelActualizarDatos.add(txtCedula, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblDireccion = new JLabel();
        lblDireccion.setText("Dirección:");
        PanelActualizarDatos.add(lblDireccion, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDireccion = new JTextField();
        PanelActualizarDatos.add(txtDireccion, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("ACTUALIZAR DATOS DEL USUARIO");
        PanelActualizarDatos.add(label1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtNombreUsuario = new JTextField();
        PanelActualizarDatos.add(txtNombreUsuario, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblNombreUsuario = new JLabel();
        lblNombreUsuario.setText("Nombre usuario:");
        PanelActualizarDatos.add(lblNombreUsuario, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rbtnAnalista = new JRadioButton();
        rbtnAnalista.setText("Analista");
        PanelActualizarDatos.add(rbtnAnalista, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rbtnAdministrador = new JRadioButton();
        rbtnAdministrador.setText("Administrador");
        PanelActualizarDatos.add(rbtnAdministrador, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnActualizar = new JButton();
        btnActualizar.setText("Actualizar");
        PanelActualizarDatos.add(btnActualizar, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return PanelActualizarDatos;
    }

}
