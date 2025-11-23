package modelo;

public abstract class ApuestaBase {
    protected int monto;
    protected String etiqueta;

    public ApuestaBase(int monto, String etiqueta) {
        this.monto = monto;
        this.etiqueta = etiqueta;
    }

    public abstract boolean evaluar(int numeroRuleta);

    public abstract double getMultiplicadorGanancia();

    public int getMonto() {
        return monto;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
