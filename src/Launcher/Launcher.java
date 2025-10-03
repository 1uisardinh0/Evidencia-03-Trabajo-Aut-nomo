package Launcher;

import Vista.VentanaLogin;
import controller.SesionController;

public class Launcher {
    public static void main(String[] args) {
        final SesionController sesionController = new SesionController();

        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaLogin login = new VentanaLogin(sesionController); 
            login.mostrarVentana();
        });
    }
}
