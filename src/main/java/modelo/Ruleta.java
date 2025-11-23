package modelo;

public class Ruleta {

    private int saldo;
    private static final int MULTIPLICADOR = 2;
    private static final int MAX_NUMERO = 36;

    private static final int[] NUMEROS_ROJOS = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

    public Ruleta(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    public Ruleta() {}

    public int girar() {
        return (int) (Math.random() * (MAX_NUMERO + 1));
    }

    public boolean evaluarResultado(int numeroRuleta, ApuestaBase apuesta) {
        return apuesta.evaluar(numeroRuleta);
    }

    public void actualizarSaldo(int monto, boolean acierto, double multiplicador) {
        if (acierto) {
            this.saldo += (int) (monto + multiplicador);
        } else {
            this.saldo -= monto;
        }
    }

    public boolean depositar(int monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        }
        return false;
    }

    public String getColor(int numero) {
        if (numero == 0) {
            return "NEUTRO";
        }
        for (int rojo : NUMEROS_ROJOS) {
            if (numero == rojo) {
                return "ROJO";
            }
        }
        return "NEGRO";
    }

    public int getSaldo() { return saldo; }
}
