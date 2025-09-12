import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaLogin {
    public static final List<Usuario> usuarios= new ArrayList<>();

    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");

    public VentanaLogin() {

        usuarios.add(new Usuario("Luisardinho", "1234", "Luis"));
        usuarios.add(new Usuario("GunnarEnderson", "GunnarTheBest", "Gunnar Enderson"));
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
        btnIngresar.setBounds(125, 175, 100, 30);

        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(new JLabel());
        frame.add(btnIngresar);

        btnIngresar.addActionListener(e -> login());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void login() {
        String usuarioIngresado = txtUsuario.getText();
        String claveIngresada = new String(txtClave.getPassword());

        String nombreUsuario = validarCredenciales(usuarioIngresado, claveIngresada);

        if (!nombreUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Bienvenido, " + nombreUsuario + "!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        }

        else {
            JOptionPane.showMessageDialog(frame,
                    "Usuario o clave incorrectos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validarCredenciales(String u, String p) {
        for (Usuario user : usuarios) {
            if (user.validarCredenciales(u, p)) {
                return user.getNombre();
            }
        }
        return "";
    }

    void abrirRegistro(){

    }

    public static void main(String[] args) {
        new VentanaLogin().mostrarVentana();
    }
}

