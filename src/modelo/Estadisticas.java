package modelo;

import java.util.List;

public class Estadisticas {
    private int totalRondas;
    private int totalAciertos;
    private int totalPerdidas;
    private int saldoInicial; 
    private int saldoFinal;

    // Constructor sin parámetros
    public Estadisticas() {
        this.totalRondas = 0;
        this.totalAciertos = 0;
        this.totalPerdidas = 0;
    }

    // Calcula todas las estadísticas basándose en el historial de resultados
    public void calcularEstadisticas(List<Resultado> historial, int saldoInicial, int saldoFinal) {
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
        this.totalRondas = historial.size();
        
        int aciertos = 0;
        
        for (Resultado r : historial) {
            if (r.isAcierto()) {
                aciertos++;
            }
        }
        
        this.totalAciertos = aciertos;
        this.totalPerdidas = totalRondas - aciertos;
    }

    // Getters generados por el Round-trip
    public int getTotalRondas() { return totalRondas; }
    public int getTotalAciertos() { return totalAciertos; }
    public int getTotalPerdidas() { return totalPerdidas; }
    public int getSaldoInicial() { return saldoInicial; }
    public int getSaldoFinal() { return saldoFinal; }
    
    // Método adicional para el reporte
    public int getGananciaNeta() {
        return saldoFinal - saldoInicial;
    }
}