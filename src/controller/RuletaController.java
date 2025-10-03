package controller;

import Modelo.Ruleta;
import Modelo.TipoApuesta;
import Modelo.Resultado;
import Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RuletaController {
    private  Ruleta ruleta;
    private List<Resultado> historialResultados = new ArrayList<>();
    
    public RuletaController(Usuario usuario){
        this.ruleta = usuario.getRuleta();
    }

    public Resultado jugarRonda(TipoApuesta tipo, int monto) throws IllegalArgumentException {
        if (monto <= 0 || monto > ruleta.getSaldo()) {
            throw new IllegalArgumentException("Monto inválido o insuficiente.");
        }
        //Lógica del Modelo
        int numeroRuleta = ruleta.girar();
        boolean acierto = ruleta.evaluarResultado(numeroRuleta, tipo);
        
        // 2. Actualización del Modelo
        ruleta.actualizarSaldo(monto, acierto);
        
        //Registro del Resultado
        Resultado resultado = new Resultado(numeroRuleta, tipo, monto, acierto);
        historialResultados.add(resultado);
        
        return resultado;
    }

    public boolean recargarSaldo(int monto) {
        return ruleta.depositar(monto);
    }

    //Getters
    public int getSaldoActual() { return ruleta.getSaldo(); }
    public String obtenerColor(int numero) { return ruleta.getColor(numero); }
    public List<Resultado> getHistorialResultados() { return historialResultados; }
}
