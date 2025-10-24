package controller;

import modelo.Ruleta;
import modelo.TipoApuesta;
import modelo.Resultado; 
import modelo.Usuario;
import modelo.Estadisticas;

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
    public Resultado jugarRonda(TipoApuesta tipo, int monto) throws IllegalArgumentException {
        if (monto <= 0 || monto > ruleta.getSaldo()) {
            throw new IllegalArgumentException("Monto inválido o insuficiente.");
        }

        // Lógica y Actualización de Saldo.
        int numeroRuleta = ruleta.girar();
        boolean acierto = ruleta.evaluarResultado(numeroRuleta, tipo);
        ruleta.actualizarSaldo(monto, acierto);

        // Creación del Resultado.
        Resultado resultado = new Resultado(numeroRuleta, tipo, monto, acierto);

        // El controlador registra el resultado en el Usuario (Asociación 1:N).
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