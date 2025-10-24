package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private Ruleta ruleta;

    private final List<Resultado> historial = new ArrayList<>();

    private Estadisticas estadisticas;

    public Usuario(String username, String password, String nombre){
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.ruleta = new Ruleta(1000);
        this.estadisticas = new Estadisticas();
    }
    
    public Usuario(){
        this("Invitado", "", "Usuario Invitado");
    }

    public boolean validarCredenciales(String u, String p){
        return this.username.equals(u) && this.password.equals(p);
    }

    public void agregarResultado(Resultado r){
        historial.add(r);
    }

    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial); 
    }
    
    //Getters
    public String getUsername(){return nombre;}

    public String getNombre(){return nombre;}

    public Ruleta getRuleta(){return ruleta;}

    public Estadisticas getEstadisticas(){return estadisticas;}

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
