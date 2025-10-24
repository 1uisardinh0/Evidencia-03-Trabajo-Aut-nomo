package vista;

import controller.SesionController;
import javax.swing.*;
import java.awt.*;


public class VentanaLogin {
    // Dimensiones de la ventana
    public static final int WIDTH = 350;
    public static final int HEIGHT = 200;

    private final JFrame frame = new JFrame("Login Casino Black Cat");
    private final SesionController sesionController;
    
    // Componentes de la Interfaz (UI)
    private final JTextField txtUsuario = new JTextField(15);
    private final JPasswordField txtClave = new JPasswordField(15);
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistro = new JButton("Registrar");

    //Constructor
    public VentanaLogin(SesionController sesionController) {
        this.sesionController = sesionController;
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(txtUsuario, gbc);

        // Fila 2: Clave
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Clave:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(txtClave, gbc);

        // Fila 3: Botones
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(btnIngresar, gbc);
        gbc.gridx = 2; gbc.gridy = 2;
        panel.add(btnRegistro, gbc);

        frame.add(panel);

        // Eventos: Delegan la acción al método correspondiente
        btnIngresar.addActionListener(e -> intentarLogin());
        btnRegistro.addActionListener(e -> abrirVentanaRegistro());
    }

    private void configurarVentana() {
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void intentarLogin() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword()); 

        // 1. El Controlador gestiona la sesión
        if (sesionController.iniciarSesion(u, p)) {
            // Caso de éxito: El controlador conoce el usuario
            mostrarMensajeExito();
            
            // 2. Abrir la siguiente Vista (Menú)
            VentanaMenu menu = new VentanaMenu(sesionController); 
            menu.mostrarVentana();
            
            frame.dispose(); // Cierra el login
        } else {
            mostrarMensajeError();
        }
    }
    
    private void abrirVentanaRegistro() {
        // Abre la ventana de registro, pasándole el mismo controlador
        new VentanaRegistro(sesionController, this).mostrarVentana();
        frame.setVisible(false);
    }
    
    // Métodos para mostrar mensajes a la vista
    private void mostrarMensajeExito() {
        JOptionPane.showMessageDialog(frame, 
            "¡Ingreso exitoso!\nBienvenido/a " + sesionController.getNombreUsuario(), 
            "Login OK", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarMensajeError() {
        JOptionPane.showMessageDialog(frame, 
            "Credenciales incorrectas. Intente de nuevo.", 
            "Error de Login", JOptionPane.ERROR_MESSAGE);
    }

    public JFrame getFrame() {
        return frame;
    }
}