package Vista;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaSaludo {
    //Atributos necesarios para el correcto funcionamiento del programa.
    private static JFrame ventana;
    private static JTextField campoTexto;
    private static JButton botonSaludar;
    private static JLabel etiquetaSaludo;
    private static JLabel etiquetaOpcion;


    public static void main(String[] args) {
        creacionVentana();
        componentesVentana();
        eventoBoton();
        inicio();
    }

    //Metodo que crea y personaliza la ventana.
    public static void creacionVentana() {
        ventana = new JFrame("Saludo Personalizado");
        ventana.setSize(600, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(null);
        ventana.setResizable(false);
        ventana.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
    }

    //Metodo asigna componentes a la ventana (boton, etiqueta y cuadro de texto).
    public static void componentesVentana() {
        campoTexto = new JTextField();
        campoTexto.setBounds(50, 30, 300, 30);
        campoTexto.setFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 16));
        campoTexto.setForeground(java.awt.Color.DARK_GRAY);

        botonSaludar = new JButton("Saludar");
        botonSaludar.setBounds(370, 30, 120, 30);
        botonSaludar.setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 16));
        botonSaludar.setForeground(java.awt.Color.BLUE);

        etiquetaOpcion = new JLabel("Escribe tu nombre:");
        etiquetaOpcion.setBounds(50, -5, 500, 40);
        etiquetaOpcion.setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 18));
        etiquetaOpcion.setForeground(java.awt.Color.BLACK);

        etiquetaSaludo = new JLabel("");
        etiquetaSaludo.setBounds(50, 200, 500, 40);
        etiquetaSaludo.setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 20));
        etiquetaSaludo.setForeground(java.awt.Color.BLACK);
    }

    //Metodo que le da funcionalidad al botón.
    public static void eventoBoton() {
        botonSaludar.addActionListener(e -> {
            String nombre = campoTexto.getText().trim();

            if (nombre.isEmpty()) {
                etiquetaSaludo.setText("Por favor ingresa tu nombre");
                etiquetaSaludo.setForeground(java.awt.Color.RED);
            } else {
                etiquetaSaludo.setText("Hola " + nombre);
                etiquetaSaludo.setForeground(java.awt.Color.BLACK);
            }
        });

        campoTexto.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    botonSaludar.doClick();
                }
            }
        });
    }

    //Metodo que agrega todos los componentes a la ventana.
    public static void inicio() {
        ventana.add(campoTexto);
        ventana.add(botonSaludar);
        ventana.add(etiquetaSaludo);
        ventana.add(etiquetaOpcion);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
