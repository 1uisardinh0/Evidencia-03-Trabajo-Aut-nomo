package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class RepositorioArchivo implements IRepositorioResultados {

    private final List<Resultado> historial = new ArrayList<>();
    private final String NOMBRE_ARCHIVO = "historial_usuario.csv";

    public RepositorioArchivo() {
        cargarDesdeFuente();
    }

    @Override
    public void guardarResultado(Resultado resultado) {
        this.historial.add(resultado);
    }

    @Override
    public List<Resultado> obtenerHistorial() {
        return Collections.unmodifiableList(this.historial);
    }

    @Override
    public void guardarEnFuente(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {

            writer.write("NumeroRuleta,Monto,Acierto,TipoApuesta,ValorApuesta\n");

            for (Resultado r : historial) {
                ApuestaBase apuesta = r.getApuestaRealizada();
                String tipo = apuesta instanceof ApuestaColor ? "COLOR" : "PARIDAD";

                writer.write(String.format("%d,%d,%b,%s,%s\n",
                        r.getNumeroRuleta(),
                        r.getMontoApostado(),
                        r.isAcierto(),
                        tipo,
                        apuesta.getEtiqueta()
                ));
            }
            System.out.println("Historial guardado en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar el historial en archivo: " + e.getMessage());
        }
    }

    @Override
    public void cargarDesdeFuente(){
        this.historial.clear();
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            reader.readLine();
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length != 5) continue;

                int numeroRuleta = Integer.parseInt(datos[0].trim());
                int monto = Integer.parseInt(datos[1].trim());
                boolean acierto = Boolean.parseBoolean(datos[2].trim());
                String tipo = datos[3].trim();
                String valor = datos[4].trim();

                ApuestaBase apuesta;
                if (tipo.equals("COLOR")) {
                    apuesta = new ApuestaColor(monto, valor);
                } else if (tipo.equals("PARIDAD")) {
                    apuesta = new ApuestaParidad(monto, valor);
                } else {
                    continue;
                }

                this.historial.add(new Resultado(numeroRuleta, apuesta, monto, acierto));
            }
            System.out.println("Historial cargado desde " + NOMBRE_ARCHIVO + " (" + this.historial.size() + " registros)");
        }
        catch (Exception e) {
            System.err.println("Error al cargar historial desde archivo: " + e.getMessage());
            this.historial.clear();
        }
    }
}
