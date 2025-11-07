package modelo;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RepositorioEnMemoria implements IRepositorioResultados {

    private final List<Resultado> historial = new ArrayList<> ();

    @Override
    public void guardarResultado(Resultado resultado) {
        this.historial.add(resultado);
    }

    @Override
    public List<Resultado> obtenerHistorial() {
        return Collections.unmodifiableList(this.historial);
    }

    @Override
    public void guardarEnFuente() {}

    @Override
    public void cargarDesdeFuente() {}
}
