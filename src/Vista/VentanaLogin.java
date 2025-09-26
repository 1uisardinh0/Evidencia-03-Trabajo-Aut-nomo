package Vista;

import Modelo.Ruleta;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Se crea la clase pricipal del programa, se definen la ventana y los componentes de la ventana, y la lista de usuarios.
public class VentanaLogin {
    public static final List<Usuario> usuarios= new ArrayList<>();

    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistro = new JButton("Registrar");

    //Contructor del programa que crea los objetos usuario y configura los componentes de la ventana.
    public VentanaLogin() {

        usuarios.add(new Usuario("Luisardinho", "1234", "Luis"));
        usuarios.add(new Usuario("GunnarHenderson", "GunnarTheBest", "Gunnar Henderson"));
        usuarios.add(new Usuario("JackHoliday", "Jh1122", "Jackson Holidays"));

        frame.setSize(350, 350);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblUsuario.setBounds(50, 50, 100, 20);

        txtUsuario.setBounds(150, 50, 150, 25);

        lblClave.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblClave.setBounds(50, 100, 100, 20);

        txtClave.setBounds(150, 100, 150, 25);

        btnIngresar.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnIngresar.setBounds(50, 175, 100, 30);

        btnRegistro.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnRegistro.setBounds(200, 175, 100, 30);

        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(new JLabel());
        frame.add(btnIngresar);
        frame.add(btnRegistro);

        btnIngresar.addActionListener(e -> login());
        btnRegistro.addActionListener(e -> abrirRegistro());
    }

    //Muestra la ventana junto a sus componentes.
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Ejecución del login del programa, si el usuario ingresado esta en la lista puede entar si no muestra un mensaje.
    private void login() {
        String usuarioIngresado = txtUsuario.getText();
        String claveIngresada = new String(txtClave.getPassword());

        String nombreUsuario = validarCredenciales(usuarioIngresado, claveIngresada);

        if (!nombreUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Bienvenido, " + nombreUsuario + "!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            iniciarJuego();
        }

        else {
            JOptionPane.showMessageDialog(frame,
                    "Modelo.Usuario o clave incorrectos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Valida las credenciales del usuario viendo si está presente en la lista.
    private String validarCredenciales(String u, String p) {
        for (Usuario user : usuarios) {
            if (user.validarCredenciales(u, p)) {
                return user.getNombre();
            }
        }
        return "";
    }
    //Abre la ventana registro donde permite al usuario poder registrarse si no lo ha hecho.
    void abrirRegistro(){
    }

    private void iniciarJuego(){
        Ruleta.crearInterfazGrafica();
    }
}

