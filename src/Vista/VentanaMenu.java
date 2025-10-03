package Vista;

import controller.RuletaController;
import controller.SesionController;
import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final JFrame frame = new JFrame("Casino Black Cat - Menú Principal");
    private final SesionController sesionController;
    private final RuletaController ruletaController;
    
    // UI Components
    private JLabel lblSaldo; 
    private JPanel panelCentral; // Contenedor para el CardLayout
    private CardLayout cardLayout = new CardLayout();

    public VentanaMenu(SesionController sesionController) {
        this.sesionController = sesionController;
        // Inicializa RuletaController con el Usuario de la sesión
        this.ruletaController = new RuletaController(sesionController.getUsuarioActual()); 
        
        inicializarComponentes();
        configurarVentana();
        actualizarSaldo();
    }
    
    private void inicializarComponentes() {
        frame.setLayout(new BorderLayout());

        // 1. Panel Lateral: Menú de Navegación (Oeste)
        JPanel panelMenu = crearPanelMenu();
        frame.add(panelMenu, BorderLayout.WEST);
        
        // 2. Panel Central con CardLayout para cambiar vistas (Centro)
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentral.add(crearPanelBienvenida(), "Bienvenida");
        panelCentral.add(crearPanelPerfil(), "Perfil");
        
        frame.add(panelCentral, BorderLayout.CENTER);
        
        // 3. Panel Inferior: Saldo (Sur)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblSaldo = new JLabel();
        panelInferior.add(lblSaldo);
        frame.add(panelInferior, BorderLayout.SOUTH);
        
        // Mostrar la vista inicial
        cardLayout.show(panelCentral, "Bienvenida");
    }
    
    /**
     * Crea el panel lateral con los botones de navegación.
     */
    private JPanel crearPanelMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnInicio = new JButton("Inicio");
        JButton btnJugar = new JButton("Jugar Ruleta");
        JButton btnHistorial = new JButton("Historial");
        JButton btnPerfil = new JButton("Perfil"); // Nuevo botón
        JButton btnSalir = new JButton("Salir");
        
        // Asegurar que los botones tengan el mismo ancho
        Dimension d = new Dimension(120, 30);
        btnInicio.setMaximumSize(d); btnInicio.setPreferredSize(d);
        btnJugar.setMaximumSize(d); btnJugar.setPreferredSize(d);
        btnHistorial.setMaximumSize(d); btnHistorial.setPreferredSize(d);
        btnPerfil.setMaximumSize(d); btnPerfil.setPreferredSize(d);
        btnSalir.setMaximumSize(d); btnSalir.setPreferredSize(d);
        
        panelMenu.add(btnInicio);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnJugar);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnHistorial);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(btnPerfil);
        panelMenu.add(Box.createVerticalGlue()); // Espacio flexible
        panelMenu.add(btnSalir);
        
        // Manejo de Eventos
        btnInicio.addActionListener(e -> cardLayout.show(panelCentral, "Bienvenida"));
        btnJugar.addActionListener(e -> abrirVentanaJuego());
        btnHistorial.addActionListener(e -> mostrarHistorial());
        btnPerfil.addActionListener(e -> cardLayout.show(panelCentral, "Perfil"));
        btnSalir.addActionListener(e -> cerrarSesion());
        
        return panelMenu;
    }

    private JPanel crearPanelBienvenida() {
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel("RULETA – Casino Black Cat", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        
        JTextArea txtBienvenida = new JTextArea();
        txtBienvenida.setEditable(false);
        txtBienvenida.setText("Bienvenido/a al menú principal, " + sesionController.getNombreUsuario() + ".\n\n" +
                              "Utiliza el menú lateral para navegar:\n" +
                              "• Jugar Ruleta: Inicia una nueva sesión de juego.\n" +
                              "• Historial: Muestra tus estadísticas de juego.\n" +
                              "• Perfil: Permite actualizar tu nombre y recargar saldo.\n" +
                              "• Salir: Cierra sesión y vuelve al Login.");
        txtBienvenida.setLineWrap(true);
        txtBienvenida.setWrapStyleWord(true);
        txtBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));

        panelCentral.add(lblTitulo, BorderLayout.NORTH);
        panelCentral.add(new JScrollPane(txtBienvenida), BorderLayout.CENTER);
        
        return panelCentral;
    }

    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblTitulo = new JLabel("Gestión de Perfil", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        panel.add(lblTitulo, gbc);
        
        // Separador
        JSeparator separator = new JSeparator();
        gbc.gridy = 1; 
        panel.add(separator, gbc);

        // Sección 1: Actualizar Nombre
        JLabel lblNombre = new JLabel("Nombre Completo:");
        JTextField txtNombre = new JTextField(sesionController.getNombreUsuario(), 20);
        JButton btnActualizarNombre = new JButton("Actualizar Nombre");

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; panel.add(lblNombre, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1; panel.add(txtNombre, gbc);
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 1; panel.add(btnActualizarNombre, gbc);
        
        // Sección 2: Recarga de Saldo
        JLabel lblRecarga = new JLabel("Monto a Recargar:");
        JTextField txtRecarga = new JTextField("1000");
        JButton btnRecargarSaldo = new JButton("Recargar");

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; panel.add(lblRecarga, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1; panel.add(txtRecarga, gbc);
        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1; panel.add(btnRecargarSaldo, gbc);

        // Eventos de Perfil
        btnActualizarNombre.addActionListener(e -> actualizarNombre(txtNombre.getText()));
        btnRecargarSaldo.addActionListener(e -> recargarSaldo(txtRecarga.getText()));

        return panel;
    }
    
    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
    
    public void mostrarVentana() {
        frame.setVisible(true);
    }

    private void actualizarSaldo() {
        // La vista consulta el saldo al controlador de sesión
        lblSaldo.setText(String.format("Saldo: $%,d", sesionController.getSaldoActual())); 
    }

    private void actualizarNombre(String nuevoNombre) {
        // El controlador valida y actualiza el Modelo (Usuario)
        if (sesionController.setNombreUsuario(nuevoNombre)) {
            JOptionPane.showMessageDialog(frame, "Nombre actualizado con éxito.");
            // Refrescar el panel de bienvenida y el menú si es necesario
            frame.remove(panelCentral);
            panelCentral = new JPanel(cardLayout);
            panelCentral.add(crearPanelBienvenida(), "Bienvenida");
            panelCentral.add(crearPanelPerfil(), "Perfil");
            frame.add(panelCentral, BorderLayout.CENTER);
            frame.revalidate();
            cardLayout.show(panelCentral, "Perfil");
        } else {
            JOptionPane.showMessageDialog(frame, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recargarSaldo(String montoStr) {
        try {
            int monto = Integer.parseInt(montoStr);
            // La vista llama al método de negocio del controlador de ruleta
            if (ruletaController.recargarSaldo(monto)) {
                actualizarSaldo(); // Refresca la vista del saldo
                JOptionPane.showMessageDialog(frame, String.format("Se recargó $%,d.", monto));
            } else {
                JOptionPane.showMessageDialog(frame, "Monto de recarga debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingrese un monto numérico válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void abrirVentanaJuego() {
        // Pasa el controlador de Ruleta a la vista de juego
        new VentanaRuleta(ruletaController, this).mostrarVentana();
        frame.setVisible(false); // Oculta el menú
    }

    private void mostrarHistorial() {
        // Construye un resumen simple del historial
        StringBuilder sb = new StringBuilder("Historial de Rondas:\n\n");
        int contador = 1;
        
        // El controlador de ruleta proporciona la lista de objetos Resultado
        for (Modelo.Resultado r : ruletaController.getHistorialResultados()) {
            String color = ruletaController.obtenerColor(r.getNumeroRuleta());
            String resultado = r.isAcierto() ? "GANÓ" : "PERDIÓ";
            
            sb.append(String.format("%d. Número: %d (%s) | Apuesta: %s ($%,d) | %s\n", 
                contador++, r.getNumeroRuleta(), color, r.getTipoApuesta().name(), r.getMontoApostado(), resultado));
        }
        
        // Muestra el historial y el saldo
        sb.append("\nSaldo Actual: $").append(sesionController.getSaldoActual());

        JOptionPane.showMessageDialog(frame, new JScrollPane(new JTextArea(sb.toString(), 20, 50)),
            "Historial y Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion() {
        sesionController.cerrarSesion();
        frame.dispose(); // Cierra el menú
        // Vuelve a abrir una nueva instancia del Login (que ya conoce el controlador)
        new VentanaLogin(sesionController).mostrarVentana(); 
    }
    
    // Getter usado por VentanaRuleta para regresar al menú
    public JFrame getFrame() {
        return frame;
    }
}
