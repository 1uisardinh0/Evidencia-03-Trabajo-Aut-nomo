package controller;

import modelo.Ruleta;
import modelo.Usuario;

public class RuletaController {
    private  Ruleta ruleta;
    private Usuario usuario;
    
    public RuletaController(Usuario usuario){
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo para RuletaController.");
        }

        this.usuario = usuario;
        this.ruleta = usuario.getRuleta();
    }

    public int girarRuleta() {
        return ruleta.girar();
    }

    public boolean recargarSaldo(int monto) {
        return ruleta.depositar(monto);
    }

    //Getters
    public int getSaldoActual() { return ruleta.getSaldo(); }
    public String obtenerColor(int numero) { return ruleta.getColor(numero); }
    public Ruleta getRuleta() { return ruleta;}
}
