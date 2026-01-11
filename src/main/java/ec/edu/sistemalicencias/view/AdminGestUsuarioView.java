package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ec.edu.sistemalicencias.controller.UsuariosController;
import ec.edu.sistemalicencias.model.entities.Usuarios;
import ec.edu.sistemalicencias.service.UsuariosService;
import ec.edu.sistemalicencias.util.PDFUsuario;
import ec.edu.sistemalicencias.view.crudView.PanelActualizarView;
import ec.edu.sistemalicencias.view.crudView.PanelCrearUsuarioView;
import ec.edu.sistemalicencias.view.crudView.PanelEliminarView;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGestUsuarioView extends JFrame {
    private JPanel panel1;
    private JButton btnCrear;
    private JButton btnActu;
    private JButton btnEliminar;
    private JButton btnReportes;
    private JButton btnCerrar;
    private JPanel panelContenido;
    private JLabel lblimCrear;
    private JLabel lblimActu;
    private JLabel lblimEliminar;
    private JLabel lblimReportes;
    private JLabel lblCerrar;

    private CardLayout cardLayout;
    private UsuariosController controller;
    private UsuariosService usuariosService;

    public AdminGestUsuarioView() {
        this.usuariosService = new UsuariosService();
        setTitle("Gestión de Usuarios");
        setSize(1024, 576);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        $$$setupUI$$$();

        setContentPane(panel1);

        controller = new UsuariosController();

        cardLayout = new CardLayout();
        panelContenido.setLayout(cardLayout);

        // Panel CREAR
        PanelCrearUsuarioView panelCrear = new PanelCrearUsuarioView(controller);
        panelContenido.add(panelCrear.$$$getRootComponent$$$(), "CREAR");

        // Panel ACTUALIZAR
        PanelActualizarView panelActualizar = new PanelActualizarView(controller);
        panelContenido.add(panelActualizar.$$$getRootComponent$$$(), "ACTUALIZAR");

        //Panel ELIMINAR
        PanelEliminarView panelEliminarView = new PanelEliminarView(controller);
        panelContenido.add(panelEliminarView.$$$getRootComponent$$$(), "ELIMINAR");
        //Redimencionamiento de Images

        ImageIcon icon = new ImageIcon(
                LoginView.class.getResource("/icons/actualizar.png")
        );

        Image img2 = icon.getImage();

        Image imgEscalada = img2.getScaledInstance(
                30, 30,
                Image.SCALE_SMOOTH
        );

        lblimActu.setIcon(new ImageIcon(imgEscalada));

        // Icono crear

        ImageIcon iconCrear = new ImageIcon(
                LoginView.class.getResource("/icons/guardar.png")
        );

        Image img3 = iconCrear.getImage();

        Image imgEscaladaCrear = img3.getScaledInstance(
                30, 30,
                Image.SCALE_SMOOTH
        );

        lblimCrear.setIcon(new ImageIcon(imgEscaladaCrear));
        //Icono Borrar

        ImageIcon iconBorrar = new ImageIcon(
                LoginView.class.getResource("/icons/eliminar.png")
        );

        Image imgBorrar = iconBorrar.getImage();

        Image imgEscaladaBorrar = imgBorrar.getScaledInstance(
                30, 30,
                Image.SCALE_SMOOTH
        );

        lblimEliminar.setIcon(new ImageIcon(imgEscaladaBorrar));
        //Reportes

        ImageIcon iconReportes = new ImageIcon(
                LoginView.class.getResource("/icons/mostrar.png")
        );

        Image imgMostrar = iconReportes.getImage();

        Image imgEscaladaMostrar = imgMostrar.getScaledInstance(
                30, 30,
                Image.SCALE_SMOOTH
        );

        lblimReportes.setIcon(new ImageIcon(imgEscaladaMostrar));


        //Reportes

        ImageIcon iconCerrar = new ImageIcon(
                LoginView.class.getResource("/icons/cerrar-sesion.png")
        );

        Image imgCerrar = iconCerrar.getImage();

        Image imgCerrarSesion = imgCerrar.getScaledInstance(
                30, 30,
                Image.SCALE_SMOOTH
        );

        lblCerrar.setIcon(new ImageIcon(imgCerrarSesion));


        // Panel INICIO
        JLabel lblInicio = new JLabel("Bienvenido al sistema ", SwingConstants.CENTER);
        lblInicio.setFont(new Font("Roboto", Font.BOLD, 30));
        panelContenido.add(lblInicio, "INICIO");

        cardLayout.show(panelContenido, "INICIO");

        btnCrear.addActionListener(e ->
                cardLayout.show(panelContenido, "CREAR")
        );

        btnActu.addActionListener(e ->
                cardLayout.show(panelContenido, "ACTUALIZAR")
        );

        btnEliminar.addActionListener(e ->
                cardLayout.show(panelContenido, "ELIMINAR")
        );

        btnReportes.addActionListener(e -> {
            ;
            generarDocumentoUsuarios();
        });


        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
                dispose();
            }
        });
        //Llamamiento del metodo estilizador
        estilizarBoton(btnActu);
        estilizarBoton(btnCrear);
        estilizarBoton(btnEliminar);
        estilizarBoton(btnReportes);
        estilizarBoton(btnCerrar);

        btnActu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Agregar accion para actualizar
            }
        });


    }

    //Metodos para estilizar botones
    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(52, 73, 94));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.setPreferredSize(new Dimension(220, 45));

    }


    private void generarDocumentoUsuarios() {
        try {
            List<Usuarios> lista = usuariosService.obtenerTodosUsuarios();

            if (lista == null || lista.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay Usuarios registrados en la base de datos.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccione la ubicación para guardar el Reporte");

            fileChooser.setSelectedFile(new File("Reporte_Usuarios_" + LocalDate.now() + ".pdf"));

            int seleccion = fileChooser.showSaveDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {

                String ruta = fileChooser.getSelectedFile().getAbsolutePath();

                if (!ruta.toLowerCase().endsWith(".pdf")) {
                    ruta += ".pdf";
                }


                PDFUsuario.generarReporteUsuariosPDF(lista, ruta);


                JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en:\n" + ruta,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el documento: " + e.getMessage(),
                    "Error de PDF", JOptionPane.ERROR_MESSAGE);
        }
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
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnCrear = new JButton();
        btnCrear.setText("Crear");
        panel2.add(btnCrear, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnActu = new JButton();
        btnActu.setText("Actualizar");
        panel2.add(btnActu, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEliminar = new JButton();
        btnEliminar.setText("Eliminar");
        panel2.add(btnEliminar, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnReportes = new JButton();
        btnReportes.setText("Reportes");
        panel2.add(btnReportes, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCerrar = new JButton();
        btnCerrar.setText("Cerrar Sesion");
        panel2.add(btnCerrar, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        lblimCrear = new JLabel();
        lblimCrear.setText("");
        panel2.add(lblimCrear, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 4, false));
        lblimActu = new JLabel();
        lblimActu.setText("");
        panel2.add(lblimActu, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 4, false));
        lblimEliminar = new JLabel();
        lblimEliminar.setText("");
        panel2.add(lblimEliminar, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 4, false));
        lblimReportes = new JLabel();
        lblimReportes.setText("");
        panel2.add(lblimReportes, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 4, false));
        lblCerrar = new JLabel();
        lblCerrar.setText("");
        panel2.add(lblCerrar, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 4, false));
        panelContenido = new JPanel();
        panelContenido.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelContenido, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
