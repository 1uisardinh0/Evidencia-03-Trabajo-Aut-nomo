package vista;

import controller.ResultadosController;
import controller.RuletaController;
import controller.SesionController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {
    
    private final JFrame frame = new JFrame("Registro de Usuario");
    private final SesionController sesionController;
    private final VentanaLogin loginPadre;

    // Componentes de la Interfaz (UI)
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtUsuario = new JTextField(15);
    private final JPasswordField txtClave = new JPasswordField(15);
    private final JButton btnRegistrar = new JButton("Registrar");

    /**
     * Constructor: Inyecta el controlador y la ventana padre.
     */
    public VentanaRegistro(SesionController sesionController, VentanaLogin loginPadre) {
        this.sesionController = sesionController;
        this.loginPadre = loginPadre;
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtNombre, gbc);
        
        // Fila 2: Usuario
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtUsuario, gbc);

        // Fila 3: Clave
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Clave:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtClave, gbc);

        // Fila 4: Botón
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        frame.add(panel);

        // Evento
        btnRegistrar.addActionListener(e -> intentarRegistro());
    }

    private void configurarVentana() {
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Controla el cierre
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                volverAlLogin();
            }
        });
        frame.setResizable(false);
    }
    
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Captura la entrada de la vista y delega el registro al Controlador.
     */
    private void intentarRegistro() {
        String n = txtNombre.getText();
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());
        
        try {
            // El Controlador realiza la validación y el registro en el Modelo
            sesionController.registrarUsuario(u, p, n);
            
            JOptionPane.showMessageDialog(frame, 
                "Usuario registrado con éxito.", 
                "Registro OK", JOptionPane.INFORMATION_MESSAGE);

            Usuario usuarioActual = sesionController.getUsuarioActual();

            RuletaController ruletaController = new RuletaController(usuarioActual);
            ResultadosController resultadosController = new ResultadosController(usuarioActual);

            VentanaMenu menu = new VentanaMenu(ruletaController, resultadosController, sesionController);

            menu.mostrarVentana();
            frame.setVisible(false);

            txtNombre.setText("");
            txtUsuario.setText("");
            txtClave.setText("");

            //volverAlLogin();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error inesperado al registrar: " + e.getMessage(), "Error General", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAlLogin() {
        frame.dispose(); // Cierra el registro
        loginPadre.mostrarVentana(); // Muestra el login
    }
}