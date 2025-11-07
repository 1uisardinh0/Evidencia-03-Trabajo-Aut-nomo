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

    private Usuario usuario;
    private Ruleta ruleta;
    private final int saldoInicialSesion;

    // Constructor con párametros
    public ResultadosController(Usuario usuario) {
        this.usuario = usuario;
        this.ruleta = usuario.getRuleta();
        this.saldoInicialSesion = usuario.getRuleta().getSaldo(); 
    }

    // Método para jugar una ronda y registrar el resultado
    public Resultado jugarRonda(String tipoApuesta, String valorApuesta, int monto) throws IllegalArgumentException {
        if (monto <= 0 || monto > ruleta.getSaldo()) {
            throw new IllegalArgumentException("Monto inválido o insuficiente.");
        }

        ApuestaBase apuesta;

        if (tipoApuesta.equals("COLOR")) {
            apuesta = new ApuestaColor(monto, valorApuesta);
        } else if (tipoApuesta.equals("PARIDAD")) {
            apuesta = new ApuestaParidad(monto, valorApuesta);
        } else {
            throw new IllegalArgumentException("Tipo de apuesta no reconocido.");
        }

        int numeroRuleta = ruleta.girar();

        boolean acierto = ruleta.evaluarResultado(numeroRuleta, apuesta);
        ruleta.actualizarSaldo(monto, acierto);

        Resultado resultado = new Resultado(numeroRuleta, apuesta, monto, acierto);

        usuario.agregarResultado(resultado);
        return resultado;
    }

    public Estadisticas generarEstadisticasSesion() {
        Estadisticas stats = new Estadisticas();
        
        // El saldo final es el saldo actual de la Ruleta del usuario
        stats.calcularEstadisticas(
            usuario.getHistorial(), 
            this.saldoInicialSesion, // Saldo al inicio de la sesión
            ruleta.getSaldo()        // Saldo actual
        );

        return stats;
    }
    
    // Método para obtener el historial de resultados del usuario
    public List<Resultado> getHistorialResultados() { 
        return usuario.getHistorial(); 
    }

    public String obtenerColor(int numero) {
        return ruleta.getColor(numero);}
}