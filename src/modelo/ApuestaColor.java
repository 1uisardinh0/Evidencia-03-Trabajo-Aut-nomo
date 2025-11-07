package modelo;

public class ApuestaColor extends ApuestaBase {
    private final String colorApostado;

    public ApuestaColor(int monto, String colorApostado) {
        super(monto, colorApostado.toUpperCase());
        this.colorApostado = colorApostado;
    }

    @Override
    public boolean evaluar(int numeroRuleta) {
        if (numeroRuleta == 0) {
            return false;
        }

        Ruleta ruletaHelper = new Ruleta();
        String colorDelNumero = ruletaHelper.getColor(numeroRuleta);

        return this.colorApostado.equals(colorDelNumero);
    }
}
