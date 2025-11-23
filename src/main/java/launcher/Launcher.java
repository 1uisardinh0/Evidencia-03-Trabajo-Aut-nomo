package launcher;

import controller.SesionController;
import vista.VentanaLogin;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Launcher {

    private static final SesionController sesionController = new SesionController();

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Launcher::iniciarAplicacion);
    }

    private static void iniciarAplicacion() {

        VentanaLogin login = new VentanaLogin(sesionController);

        configurarCierreGlobal(login.getFrame());

        login.mostrarVentana();
    }

    private static void configurarCierreGlobal(JFrame framePrincipal) {

        framePrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        framePrincipal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Guardando historial de la sesión...");

                sesionController.guardarHistorial();

                e.getWindow().dispose();
                System.exit(0);
            }
        });
    }
}
