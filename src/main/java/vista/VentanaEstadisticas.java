package vista;

import controller.ResultadosController;
import modelo.Estadisticas;

import javax.swing.*;
import java.awt.*;

public class VentanaEstadisticas {
    private final JFrame frame = new JFrame("Reporte de Estadísticas");
    private final ResultadosController resultadosController;
    private final VentanaMenu menuPadre;

    public VentanaEstadisticas(ResultadosController resultadosController, VentanaMenu menuPadre) {
        this.resultadosController = resultadosController;
        this.menuPadre = menuPadre;
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Estadísticas de la Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // 1. Obtener los datos calculados
        Estadisticas stats = resultadosController.generarEstadisticasSesion();
        
        // 2. Crear panel de detalle
        JPanel panelDetalle = crearPanelDetalle(stats);
        panel.add(panelDetalle, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.addActionListener(e -> volverAlMenu());
        panel.add(btnVolver, BorderLayout.SOUTH);
        
        frame.add(panel);
    }
    
    private JPanel crearPanelDetalle(Estadisticas stats) {
        JPanel p = new JPanel(new GridLayout(6, 2, 10, 10));
        
        // Estilo para el resultado neto
        Color colorResultado = stats.getGananciaNeta() >= 0 ? new Color(0, 150, 0) : Color.RED;

        p.add(new JLabel("Rondas Jugadas:"));
        p.add(new JLabel(String.valueOf(stats.getTotalRondas()), SwingConstants.RIGHT));
        
        p.add(new JLabel("Aciertos:"));
        p.add(new JLabel(String.valueOf(stats.getTotalAciertos()), SwingConstants.RIGHT));

        p.add(new JLabel("Pérdidas:"));
        p.add(new JLabel(String.valueOf(stats.getTotalPerdidas()), SwingConstants.RIGHT));

        p.add(new JSeparator()); // Separador visual
        p.add(new JSeparator());

        p.add(new JLabel("Saldo Final:"));
        p.add(new JLabel(String.format("$%,d", stats.getSaldoFinal()), SwingConstants.RIGHT));

        JLabel lblGanancia = new JLabel("Ganancia/Pérdida Neta:");
        JLabel lblValorGanancia = new JLabel(String.format("$%,d", stats.getGananciaNeta()), SwingConstants.RIGHT);
        lblValorGanancia.setForeground(colorResultado);
        lblValorGanancia.setFont(lblValorGanancia.getFont().deriveFont(Font.BOLD, 14f));
        
        p.add(lblGanancia);
        p.add(lblValorGanancia);
        
        return p;
    }

    private void configurarVentana() {
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                volverAlMenu();
            }
        });
        frame.setLocationRelativeTo(null);
    }
    
    public void mostrarVentana() {
        frame.setVisible(true);
    }

    private void volverAlMenu() {
        frame.dispose();
        menuPadre.getFrame().setVisible(true);
    }
}