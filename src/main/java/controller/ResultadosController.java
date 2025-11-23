package controller;

import modelo.Ruleta;
import modelo.Resultado;
import modelo.Usuario;
import modelo.Estadisticas;
import modelo.ApuestaBase;
import modelo.ApuestaColor;
import modelo.ApuestaParidad;

import java.util.List;

// Controlador dedicado a gestionar las operaciones relacionadas con los Resultados de juego.
public class ResultadosController {

    private final Usuario usuario;
    private final Ruleta ruleta;
    private final int saldoInicialSesion;

    // Constructor con párametros
    public ResultadosController(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo para ResultadosController.");
        }

        this.usuario = usuario;
        this.ruleta = usuario.getRuleta();
        this.saldoInicialSesion = usuario.getRuleta().getSaldo();
    }

    public Resultado jugarRonda(String tipoApuesta, String valorApuesta, int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto apostado debe ser positivo.");
        }

        ApuestaBase apuesta;
        if (tipoApuesta.equals("COLOR")) {
            apuesta = new ApuestaColor(monto, valorApuesta);
        } else if (tipoApuesta.equals("PARIDAD")) {
            apuesta = new ApuestaParidad(monto, valorApuesta);
        } else {
            throw new IllegalArgumentException("Tipo de apuesta no reconocido.");
        }

        int numeroRuleta = usuario.getRuleta().girar();
        boolean acierto = apuesta.evaluar(numeroRuleta);

        usuario.getRuleta().actualizarSaldo(monto, acierto, apuesta.getMultiplicadorGanancia());

        Resultado resultado = new Resultado(numeroRuleta, apuesta, monto, acierto);

        usuario.agregarResultado(resultado);

        return resultado;
    }

    public Estadisticas generarEstadisticasSesion() {
        Estadisticas stats = new Estadisticas();

        stats.calcularEstadisticas(
            usuario.getHistorial(), 
            this.saldoInicialSesion,
            ruleta.getSaldo()
        );

        return stats;
    }

    public List<Resultado> getHistorialResultados() { 
        return usuario.getHistorial(); 
    }

    public String obtenerColor(int numero) {
        return ruleta.getColor(numero);}
}