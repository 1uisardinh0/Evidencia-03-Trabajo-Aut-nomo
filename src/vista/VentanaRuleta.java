package vista;

import controller.RuletaController;
import controller.ResultadosController;
import modelo.Resultado;

import javax.swing.*;
import java.awt.*;

public class VentanaRuleta {

    private final JFrame frame = new JFrame("Jugar Ruleta");
    private final RuletaController ruletaController; // Inyección del Controlador
    private final ResultadosController resultadosController;
    private final VentanaMenu menuPadre;

    // UI Components
    private JComboBox<String> cmbTipoApuesta;
    private JComboBox<String> cmbSeleccion;
    private JTextField txtMonto;
    private JButton btnGirar;
    private JLabel lblSaldo;
    private JTextArea txtResultado;


    // Constructor
    public VentanaRuleta(RuletaController ruletaController, ResultadosController resultadosController, VentanaMenu menuPadre) {
        this.ruletaController = ruletaController;
        this.resultadosController = resultadosController;
        this.menuPadre = menuPadre;

        inicializarComponentes();
        configurarVentana();
        actualizarSaldo();
    }


    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        configurarApuestaUI(panel, gbc);

        // Botón Girar y Saldo (Fila 3 de la cuadrícula, posición 2 y 3)
        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0.3;
        panel.add(btnGirar, gbc);

        gbc.gridx = 3; gbc.gridy = 3; gbc.weightx = 0.5;
        lblSaldo = new JLabel();
        panel.add(lblSaldo, gbc);

        // Área de Resultados (Fila 4)
        txtResultado = new JTextArea(10, 20);
        txtResultado.setEditable(false);
        txtResultado.setText("¡Bienvenido! Haz tu primera apuesta.");
        JScrollPane scrollResult = new JScrollPane(txtResultado);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollResult, gbc);

        frame.add(panel);

        // --- Eventos ---
        cmbTipoApuesta.addActionListener(e -> actualizarOpcionesSeleccion());
        btnGirar.addActionListener(e -> intentarJugar());
    }

    private void configurarApuestaUI(JPanel panel, GridBagConstraints gbc) {
        // Fila 0: Tipo de Apuesta
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panel.add(new JLabel("Tipo de apuesta:"), gbc);
        cmbTipoApuesta = new JComboBox<>(new String[]{"Color", "Paridad"});
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3;
        panel.add(cmbTipoApuesta, gbc);

        // Fila 1: Selección (Color o Paridad)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Selección:"), gbc);
        cmbSeleccion = new JComboBox<>(new String[]{"Rojo", "Negro"}); // Opciones iniciales
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3;
        panel.add(cmbSeleccion, gbc);

        // Fila 2: Monto
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(new JLabel("Monto:"), gbc);
        txtMonto = new JTextField("100");
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 3;
        panel.add(txtMonto, gbc);

        // Botón Girar
        btnGirar = new JButton("Girar");
    }

    private void configurarVentana() {
        // Al cerrar la ventana, se vuelve al menú principal
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                volverAlMenu();
            }
        });
        frame.setSize(550, 450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void actualizarOpcionesSeleccion() {
        cmbSeleccion.removeAllItems();
        String tipo = (String) cmbTipoApuesta.getSelectedItem();
        if ("Color".equals(tipo)) {
            cmbSeleccion.addItem("Rojo");
            cmbSeleccion.addItem("Negro");
        } else if ("Paridad".equals(tipo)) {
            cmbSeleccion.addItem("Par");
            cmbSeleccion.addItem("Impar");
        }
    }

    private void intentarJugar() {
        try {
            int monto = Integer.parseInt(txtMonto.getText());
            String seleccionTexto = (String) cmbSeleccion.getSelectedItem();
            seleccionTexto = seleccionTexto.toUpperCase();

            if (seleccionTexto.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Seleccione un valor de apuesta (Rojo/Par, etc.).", "Error de Apuesta", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipoApuesta;
            String valorApuesta;

            if (seleccionTexto.equals("ROJO") || seleccionTexto.equals("NEGRO")) {
                tipoApuesta = "COLOR";
                valorApuesta = seleccionTexto;
            } else if (seleccionTexto.equals("PAR") || seleccionTexto.equals("IMPAR")) {
                tipoApuesta = "PARIDAD";
                valorApuesta = seleccionTexto;
            } else {
                throw new IllegalArgumentException("Selección de apuesta inválida.");
            }

            Resultado resultado = resultadosController.jugarRonda(tipoApuesta, valorApuesta, monto);

            mostrarResultadoRonda(resultado);
            actualizarSaldo();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingrese un monto numérico válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error de Apuesta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarResultadoRonda(Resultado resultado) {
        String color = ruletaController.obtenerColor(resultado.getNumeroRuleta());
        String status = resultado.isAcierto() ? "¡GANASTE!" : "PERDISTE.";

        txtResultado.append(String.format("\nNúmero %d (%s) | Apuesta %s | Monto $%,d | %s | Saldo $%,d",
                resultado.getNumeroRuleta(),
                color,
                resultado.getApuestaRealizada().getEtiqueta(),
                resultado.getMontoApostado(),
                status,
                ruletaController.getSaldoActual()));

        // Mantiene el scroll al final
        txtResultado.setCaretPosition(txtResultado.getDocument().getLength());
    }

    private void actualizarSaldo() {
        lblSaldo.setText(String.format("Saldo: $%,d", ruletaController.getSaldoActual()));
    }

    private void volverAlMenu() {
        frame.dispose(); // Cierra la ventana de juego
        menuPadre.getFrame().setVisible(true); // Muestra el menú padre
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }
}