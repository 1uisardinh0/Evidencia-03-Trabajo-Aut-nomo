package modelo;

public class Resultado {
    private final int numeroRuleta;
    private final ApuestaBase apuestaRealizada;
    private final int montoApostado;
    private final boolean acierto;

    //Constructor
    public Resultado(int numeroRuleta, ApuestaBase apuestaRealizada, int montoApostado, boolean acierto) {
        this.numeroRuleta = numeroRuleta;
        this.apuestaRealizada = apuestaRealizada;
        this.montoApostado = montoApostado;
        this.acierto = acierto;
    }

    //Getters
    public int getNumeroRuleta() {return numeroRuleta;}

    public ApuestaBase getApuestaRealizada() {return apuestaRealizada;}

    public int getMontoApostado() {return montoApostado;}

    public boolean isAcierto() {return acierto;}
}
