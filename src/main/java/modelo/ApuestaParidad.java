package modelo;

public class ApuestaParidad extends ApuestaBase {
    private final boolean esParidadApostadaPar;

    public ApuestaParidad(int monto, String paridadApostada) {
        super(monto, paridadApostada.toUpperCase());
        this.esParidadApostadaPar = paridadApostada.toUpperCase().equals("PAR");

    }

    @Override
    public boolean evaluar (int numeroRuleta) {
        if (numeroRuleta == 0){
            return this.esParidadApostadaPar;
        }

        boolean esNumeroPar = (numeroRuleta % 2 == 0);

        return esNumeroPar == this.esParidadApostadaPar;
    }

    @Override
    public double getMultiplicadorGanancia() {
        return 1.0;
    }
}
