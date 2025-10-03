package Modelo;

public class Resultado {
    private final int numeroRuleta;
    private final TipoApuesta tipoApuesta;
    private final int montoApostado;
    private final boolean acierto;

    //COntructor
    public Resultado(int numeroRuleta, TipoApuesta tipoApuesta, int montoApostado, boolean acierto) {
        this.numeroRuleta = numeroRuleta;
        this.tipoApuesta = tipoApuesta;
        this.montoApostado = montoApostado;
        this.acierto = acierto;
    }

    //Getters
    public int getNumeroRuleta() {
        return numeroRuleta;
    }

    public TipoApuesta getTipoApuesta() {
        return tipoApuesta;
    }

    public int getMontoApostado() {
        return montoApostado;
    }

    public boolean isAcierto() {
        return acierto;
    }
}
