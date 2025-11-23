package modelo;

import java.util.List;

public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private Ruleta ruleta;

    private IRepositorioResultados repositorio;

    private Estadisticas estadisticas;

    public Usuario(String username, String password, String nombre, IRepositorioResultados repositorio){
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.ruleta = new Ruleta(1000);
        this.estadisticas = new Estadisticas();
        this.repositorio = repositorio;
    }

    public boolean validarCredenciales(String u, String p){
        return this.username.equals(u) && this.password.equals(p);
    }

    public void agregarResultado(Resultado r){
        this.repositorio.guardarResultado(r);
    }

    public List<Resultado> getHistorial() {
        return this.repositorio.obtenerHistorial();
    }
    
    //Getters
    public String getUsername(){return nombre;}

    public String getNombre(){return nombre;}

    public Ruleta getRuleta(){return ruleta;}

    public Estadisticas getEstadisticas(){return estadisticas;}

    public IRepositorioResultados getRepositorio(){return repositorio;}

    //Setters
    public void setEstadisticas(Estadisticas estadisticas){
        this.estadisticas = estadisticas;}

    public boolean setNombre(String nuevoNombre) {
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            this.nombre = nuevoNombre.trim();
            return true;
        }
        return false;
    }
}