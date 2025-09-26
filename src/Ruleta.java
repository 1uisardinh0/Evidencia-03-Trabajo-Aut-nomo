import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Ruleta {

    public static final int MAX_HISTORIAL = 100;
    public static final int MIN_NUMERO = 0;
    public static final int MAX_NUMERO = 36;

    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;

    public static Random rng = new Random();
    public static int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

    private static JFrame frame;
    private static JTextArea resultadoArea;
    private static JTextArea estadisticasArea;
    private static JComboBox<String> tipoApuestaCombo;
    private static JTextField montoField;
    private static JLabel numeroResultadoLabel;
    private static JLabel estadoResultadoLabel;

    public static void main(String[] args) {
        crearInterfazGrafica();
    }

    public static void crearInterfazGrafica() {

        frame = new JFrame("Ruleta Casino Black Cat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        JPanel tituloPanel = new JPanel();
        tituloPanel.setBackground(new Color(220, 20, 60));
        JLabel tituloLabel = new JLabel("RULETA CASINO BLACK CAT");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setForeground(Color.WHITE);
        tituloPanel.add(tituloLabel);
        frame.add(tituloPanel, BorderLayout.NORTH);

        JPanel juegoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        juegoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        juegoPanel.add(new JLabel("Tipo de apuesta:"));
        String[] tiposApuesta = {"Rojo (R)", "Negro (N)", "Par (P)", "Impar (I)"};
        tipoApuestaCombo = new JComboBox<>(tiposApuesta);
        juegoPanel.add(tipoApuestaCombo);

        juegoPanel.add(new JLabel("Monto a apostar:"));
        montoField = new JTextField("100");
        juegoPanel.add(montoField);

        JButton jugarButton = new JButton("Girar Ruleta");
        jugarButton.setBackground(new Color(30, 144, 255)); // Azul
        jugarButton.setForeground(Color.WHITE);
        jugarButton.setFont(new Font("Arial", Font.BOLD, 16));
        jugarButton.addActionListener(new JugarButtonListener());
        juegoPanel.add(jugarButton);

        numeroResultadoLabel = new JLabel("00", JLabel.CENTER);
        numeroResultadoLabel.setFont(new Font("Arial", Font.BOLD, 48));
        numeroResultadoLabel.setOpaque(true);
        numeroResultadoLabel.setBackground(Color.LIGHT_GRAY);
        juegoPanel.add(numeroResultadoLabel);

        frame.add(juegoPanel, BorderLayout.CENTER);

        JPanel resultadosPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        resultadosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultadoArea = new JTextArea(8, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane resultadoScroll = new JScrollPane(resultadoArea);
        resultadoScroll.setBorder(BorderFactory.createTitledBorder("Últimos Resultados"));
        resultadosPanel.add(resultadoScroll);

        estadisticasArea = new JTextArea(8, 30);
        estadisticasArea.setEditable(false);
        estadisticasArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane estadisticasScroll = new JScrollPane(estadisticasArea);
        estadisticasScroll.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        resultadosPanel.add(estadisticasScroll);

        frame.add(resultadosPanel, BorderLayout.SOUTH);

        estadoResultadoLabel = new JLabel("Bienvenido al Casino Black Cat! Realice su apuesta.", JLabel.CENTER);
        estadoResultadoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        estadoResultadoLabel.setOpaque(true);
        estadoResultadoLabel.setBackground(Color.YELLOW);
        frame.add(estadoResultadoLabel, BorderLayout.SOUTH);

        JPanel botonesPanel = new JPanel();
        JButton estadisticasButton = new JButton("Ver Estadísticas");
        estadisticasButton.addActionListener(new EstadisticasButtonListener());
        botonesPanel.add(estadisticasButton);

        JButton limpiarButton = new JButton("Limpiar Historial");
        limpiarButton.addActionListener(new LimpiarButtonListener());
        botonesPanel.add(limpiarButton);

        frame.add(botonesPanel, BorderLayout.EAST);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        actualizarEstadisticas();
    }

    private static class JugarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (historialSize >= MAX_HISTORIAL) {
                JOptionPane.showMessageDialog(frame, "Límite de rondas alcanzado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                int selectedIndex = tipoApuestaCombo.getSelectedIndex();
                char tipo = switch (selectedIndex) {
                    case 0 -> 'R';
                    case 1 -> 'N';
                    case 2 -> 'P';
                    case 3 -> 'I';
                    default -> ' ';
                };

                int monto = Integer.parseInt(montoField.getText());
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(frame, "El monto debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int numero = girarRuleta();
                boolean acierto = evaluarResultado(numero, tipo);

                registrarResultado(numero, monto, acierto);
                mostrarResultadoEnGUI(numero, tipo, monto, acierto);
                actualizarEstadisticas();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class EstadisticasButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actualizarEstadisticas();
        }
    }

    private static class LimpiarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "¿Está seguro de que desea limpiar todo el historial?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                historialSize = 0;
                resultadoArea.setText("");
                actualizarEstadisticas();
                estadoResultadoLabel.setText("Historial limpiado. Listo para nuevas apuestas.");
                estadoResultadoLabel.setBackground(Color.YELLOW);
                numeroResultadoLabel.setText("00");
                numeroResultadoLabel.setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public static int girarRuleta() {
        return rng.nextInt(MAX_NUMERO + 1);
    }

    public static boolean evaluarResultado(int numero, char tipo) {
        if (numero == 0) return false;

        return switch (tipo) {
            case 'R' -> esRojo(numero);
            case 'N' -> !esRojo(numero);
            case 'P' -> numero % 2 == 0;
            case 'I' -> numero % 2 == 1;
            default -> false;
        };
    }

    public static boolean esRojo(int n) {
        for (int rojo : numerosRojos) {
            if (rojo == n) return true;
        }
        return false;
    }

    public static void registrarResultado(int numero, int apuesta, boolean acierto) {
        historialNumeros[historialSize] = numero;
        historialApuestas[historialSize] = apuesta;
        historialAciertos[historialSize] = acierto;
        historialSize++;
    }

    public static void mostrarResultadoEnGUI(int numero, char tipo, int monto, boolean acierto) {
        numeroResultadoLabel.setText(String.format("%02d", numero));

        if (acierto) {
            numeroResultadoLabel.setBackground(Color.GREEN);
            estadoResultadoLabel.setText("¡FELICIDADES! ¡GANÓ LA APUESTA!");
            estadoResultadoLabel.setBackground(Color.GREEN);
        } else {
            numeroResultadoLabel.setBackground(Color.RED);
            estadoResultadoLabel.setText("Lo siento, perdió esta apuesta. ¡Intente de nuevo!");
            estadoResultadoLabel.setBackground(Color.RED);
        }

        String tipoTexto = switch (tipo) {
            case 'R' -> "Rojo";
            case 'N' -> "Negro";
            case 'P' -> "Par";
            case 'I' -> "Impar";
            default -> "";
        };

        String resultado = String.format("Ronda %d: N°%02d | %s | $%d | %s\n",
                historialSize, numero, tipoTexto, monto, acierto ? "GANÓ" : "PERDIÓ");

        resultadoArea.append(resultado);
        resultadoArea.setCaretPosition(resultadoArea.getDocument().getLength()); // Auto-scroll
    }

    public static void actualizarEstadisticas() {
        if (historialSize == 0) {
            estadisticasArea.setText("No hay rondas jugadas.");
            return;
        }

        int totalRondas = historialSize;
        int totalApostado = 0;
        int totalAciertos = 0;

        for (int i = 0; i < historialSize; i++) {
            totalApostado += historialApuestas[i];
            if (historialAciertos[i]) totalAciertos++;
        }

        double porcentajeAciertos = (double) totalAciertos / totalRondas * 100;
        int gananciaNeta = (totalAciertos * 2) - totalApostado; // Se paga el doble al ganar

        String estadisticas = String.format(
                "Rondas jugadas: %d\n" +
                        "Total apostado: $%d\n" +
                        "Total de aciertos: %d\n" +
                        "Porcentaje de aciertos: %.2f%%\n" +
                        "Ganancia/Pérdida neta: $%d\n\n" +
                        "Últimos 5 números:\n",
                totalRondas, totalApostado, totalAciertos, porcentajeAciertos, gananciaNeta
        );

        int inicio = Math.max(0, historialSize - 5);
        for (int i = inicio; i < historialSize; i++) {
            estadisticas += String.format("Ronda %d: %02d\n", i + 1, historialNumeros[i]);
        }

        estadisticasArea.setText(estadisticas);
    }
}
