package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;

import java.awt.Font;
import javax.swing.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ec.edu.sistemalicencias.dao.UsuariosDAO;

import ec.edu.sistemalicencias.model.entities.Usuarios;

public class LoginView extends JFrame {
    private JPanel PanelLogin;
    private JLabel lblIniciarSesion;
    private JComboBox cbxUsers;
    private JLabel lblusuario;
    private JTextField txtcontrasenia;
    private JLabel lblcontrasenia;
    private JButton btnIngresar;
    private JLabel lblImagen_Login;
    private JLabel lblImagen;
    private JTextField txtusuario;


    public LoginView() {
        // Configuración básica de la ventana

        setTitle("Bienvenido - Sistema de Licencias de Conducir");
        setSize(820, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(PanelLogin);

        ImageIcon icono = new ImageIcon(
                LoginView.class.getResource("/icons/ingreso.png")
        );

        Image img = icono.getImage().getScaledInstance(
                70, 70, Image.SCALE_SMOOTH
        );

        lblImagen_Login.setIcon(new ImageIcon(img));

// Ajustar Tamaño
        ImageIcon icon = new ImageIcon(
                LoginView.class.getResource("/icons/images.jpg")
        );

        Image img2 = icon.getImage();

        Image imgEscalada = img2.getScaledInstance(
                300, 520,
                Image.SCALE_SMOOTH
        );

        lblImagen.setIcon(new ImageIcon(imgEscalada));


        btnIngresar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblIniciarSesion.setFont(new Font("Open Sans", Font.BOLD, 40));
        lblusuario.setFont(new Font("Roboto", Font.BOLD, 20));
        lblcontrasenia.setFont(new Font("Roboto", Font.BOLD, 20));
        btnIngresar.setFont(new Font("Roboto", Font.BOLD, 15));
        lblusuario.setForeground(new Color(127, 140, 141));
        lblcontrasenia.setForeground(new Color(127, 140, 141));
        btnIngresar.setBackground(new Color(56, 47, 47));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        //Panel estilizado
        // Agrega margen de 30 pixeles alrededor de todo el contenido
        PanelLogin.setBorder(new EmptyBorder(0, 20, 10, 0));
        txtcontrasenia.setBackground(Color.WHITE);
        txtcontrasenia.setFont(new Font("Arial", Font.PLAIN, 14));
        txtcontrasenia.setForeground(new Color(50, 50, 50));

// Crear un borde personalizado

        txtcontrasenia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        // Delimitadores
        lblIniciarSesion.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(0x5C1A1A)));

        // Personalizar Ingresar usuario

        txtusuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        // Creamos una dimensión estándar, por ejemplo 250 de ancho por 35 de alto
        Dimension dimensionEstandar = new Dimension(250, 35);

        txtusuario.setPreferredSize(dimensionEstandar);
        txtusuario.setMinimumSize(dimensionEstandar);

        txtcontrasenia.setPreferredSize(dimensionEstandar);
        txtcontrasenia.setMinimumSize(dimensionEstandar);

        // SOLUCIÓN: Un solo ActionListener simplificado (usando Lambda)
        btnIngresar.addActionListener(e -> {
            // 1. "Agarramos" lo que el usuario escribió
            String inputUsuario = txtusuario.getText();
            // Para JPasswordField se recomienda usar getPassword(), pero para pruebas rápidas:
            String inputPassword = txtcontrasenia.getText();

            // Validar campos vacíos antes de ir a la DB
            if (inputUsuario.isEmpty() || inputPassword.isEmpty()) {
                JOptionPane.showMessageDialog(LoginView.this, "Por favor, llene todos los campos");
                return;
            }

            // 2. Llamamos al DAO
            UsuariosDAO dao = new UsuariosDAO();

            try {
                Usuarios usuarioEncontrado = dao.login(inputUsuario, inputPassword);

                // 3. LA COMPARACIÓN FINAL
                if (usuarioEncontrado != null) {
                    JOptionPane.showMessageDialog(LoginView.this, "¡Acceso Correcto! Bienvenido " + usuarioEncontrado.getNombreUsuario());

                    // Aquí abrirías tu siguiente ventana y cierras esta
                    // new MenuPrincipal().setVisible(true);
                    // this.dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Usuario o contraseña incorrectos",
                            "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                }

            } catch (BaseDatosException ex) {
                JOptionPane.showMessageDialog(LoginView.this, "Error de base de datos: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
// Abrir ventanas segun el rol
        //Logica del Login
        btnIngresar.addActionListener(e -> {
            String inputUsuario = txtusuario.getText();
            String inputPassword = txtcontrasenia.getText();

            if (inputUsuario.isEmpty() || inputPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos");
                return;
            }

            // Cambiar según como arreglaste el constructor del DAO
            UsuariosDAO dao = new UsuariosDAO();

            try {
                Usuarios usuarioEncontrado = dao.login(inputUsuario, inputPassword);

                if (usuarioEncontrado != null) {
                    String rol = usuarioEncontrado.getRol();

                    if (rol.equalsIgnoreCase("Administrador")) {
                        AdminGestUsuarioView adminview = new AdminGestUsuarioView();
                        adminview.setVisible(true);
                    } else if (rol.equalsIgnoreCase("Analista")) {
                        MainView analistView = new MainView();
                        analistView.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Rol desconocido: " + rol);
                        return;
                    }

                    this.dispose(); // Cerrar login

                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (BaseDatosException ex) {
                JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
            }
        });


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
        PanelLogin = new JPanel();
        PanelLogin.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
        PanelLogin.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        lblIniciarSesion = new JLabel();
        lblIniciarSesion.setText("INICIAR SESION");
        panel1.add(lblIniciarSesion, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblImagen_Login = new JLabel();
        lblImagen_Login.setIcon(new ImageIcon(getClass().getResource("/icons/ingreso.png")));
        lblImagen_Login.setText("");
        panel1.add(lblImagen_Login, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 10, false));
        lblcontrasenia = new JLabel();
        lblcontrasenia.setText("Contraseña");
        panel1.add(lblcontrasenia, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblusuario = new JLabel();
        lblusuario.setText("Usuario");
        panel1.add(lblusuario, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtusuario = new JTextField();
        panel1.add(txtusuario, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtcontrasenia = new JPasswordField();
        panel1.add(txtcontrasenia, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnIngresar = new JButton();
        btnIngresar.setText("Ingresar");
        panel1.add(btnIngresar, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblImagen = new JLabel();
        lblImagen.setIcon(new ImageIcon(getClass().getResource("/icons/images.jpeg")));
        lblImagen.setText("");
        PanelLogin.add(lblImagen, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return PanelLogin;
    }

}

