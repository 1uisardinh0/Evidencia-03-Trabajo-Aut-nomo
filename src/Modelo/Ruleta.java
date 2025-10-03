package Modelo;
import java.util.Random;

public class Ruleta {
    public static final int NUMERO_MAXIMO = 36;
    private static int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private int saldo;
    private static Random rng = new Random();

    //Constructor con el saldo incial para el usuario registrado
    public Ruleta(int saldoInicial) {this.saldo = saldoInicial; } 
    
    //Constructor con el saldo inicial para los usuarios invitados.
    public Ruleta() { this(0); }

    //Método que permite iniciar el juego.
    public int girar() {
        return rng.nextInt(NUMERO_MAXIMO + 1); 
    }

    //Evalua el resultado de la apuesta con el enum.
    public boolean evaluarResultado(int numero, TipoApuesta tipo) {
        if (numero == 0) return false;

        return switch (tipo) {
            case ROJO -> esRojo(numero);
            case NEGRO -> !esRojo(numero);
            case PAR -> numero % 2 == 0;
            case IMPAR -> numero % 2 != 0;
        };
    }
    
    public void actualizarSaldo(int monto, boolean acierto) {
        saldo += acierto ? monto : -monto;
    }
    
    //Permite depositar saldo.
    public boolean depositar(int monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        }
        return false;
    }

    //Getters.
    public int getSaldo() { return saldo; }
    public String getColor(int numero) { return (numero == 0) ? "Verde" : (esRojo(numero) ? "Rojo" : "Negro"); }
    private boolean esRojo(int n) {
        for (int numeroRojo : numerosRojos) {
            if (n == numeroRojo) return true;
        }
        return false;
    }
}
