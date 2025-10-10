package controller;

import Modelo.Ruleta;
import Modelo.TipoApuesta;
import Modelo.Resultado;
import Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RuletaController {
    private Ruleta ruleta;
    private Usuario usuario; // Referencia al Usuario actual

    public RuletaController(Usuario usuario) {
        this.usuario = usuario;
        this.ruleta = usuario.getRuleta();
    }

    public Resultado jugarRonda(TipoApuesta tipo, int monto) throws IllegalArgumentException {
        if (monto <= 0 || monto > ruleta.getSaldo()) {
            throw new IllegalArgumentException("Monto inválido o insuficiente.");
        }

        //Lógica y Actualización de Saldo
        int numeroRuleta = ruleta.girar();
        boolean acierto = ruleta.evaluarResultado(numeroRuleta, tipo);
        ruleta.actualizarSaldo(monto, acierto);

        //Creación del Resultado
        Resultado resultado = new Resultado(numeroRuleta, tipo, monto, acierto);

        //El controlador registra el resultado directamente en el Usuario
        usuario.agregarResultado(resultado);

        return resultado;
    }

    public boolean recargarSaldo(int monto) {
        return ruleta.depositar(monto);
    }

    // Getters para la Vista.
    public int getSaldoActual() { return ruleta.getSaldo(); }
    public String obtenerColor(int numero) { return ruleta.getColor(numero); }

    //Obtiene el historial directamente del Usuario.
    public List<Resultado> getHistorialResultados() { return usuario.getHistorial(); }
}
