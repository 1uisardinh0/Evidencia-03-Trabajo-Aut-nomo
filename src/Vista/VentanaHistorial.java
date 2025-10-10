package Vista;

import controller.RuletaController;
import Modelo.Resultado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaHistorial {
    private final JFrame frame = new JFrame("Historial de Jugadas");
    private final RuletaController ruletaController;
    private final VentanaMenu menuPadre;

    private JTable tablaHistorial;

    public VentanaHistorial(RuletaController ruletaController, VentanaMenu menuPadre) {
        this.ruletaController = ruletaController;
        this.menuPadre = menuPadre;
        inicializarComponentes();
        configurarVentana();
        cargarDatosHistorial();
    }

    private void inicializarComponentes() {
        frame.setLayout(new BorderLayout());

        // Definición del modelo de la tabla
        String[] columnNames = {"#", "Número", "Color", "Apuesta", "Monto", "Resultado"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaHistorial = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.addActionListener(e -> volverAlMenu());
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.add(btnVolver);
        frame.add(panelSur, BorderLayout.SOUTH);
    }

    //Recupera los resultados del controlador y los carga en la tabla.
    private void cargarDatosHistorial() {
        DefaultTableModel model = (DefaultTableModel) tablaHistorial.getModel();
        model.setRowCount(0); //Limpia datos anteriores.

        //El controlador trae la lista de objetos Resultado.
        List<Resultado> historial = ruletaController.getHistorialResultados();
        int contador = 1;

        for (Resultado r : historial) {
            String color = ruletaController.obtenerColor(r.getNumeroRuleta());
            String resultadoStr = r.isAcierto() ? "GANÓ" : "PERDIÓ";

            model.addRow(new Object[]{
                    contador++,
                    r.getNumeroRuleta(),
                    color,
                    r.getTipoApuesta().name(),
                    String.format("$%,d", r.getMontoApostado()),
                    resultadoStr
            });
        }
    }

    private void configurarVentana() {
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Maneja el evento de cierre para volver al menú.
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
