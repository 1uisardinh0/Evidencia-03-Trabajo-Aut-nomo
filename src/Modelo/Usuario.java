package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private Ruleta ruleta;

    public Usuario(String username, String password, String nombre){
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.ruleta = new Ruleta(1000);
    }

    //A cada usuario le corresponde un historial de resultado.
    private final List<Resultado> historial = new ArrayList<>();

    public Usuario(){
        this("Invitado", "", "Usuario Invitado");
    }

    public boolean validarCredenciales(String u, String p){
        return this.username.equals(u) && this.password.equals(p);
    }

    //Agrega un resultado al historial del usuario.
    public void agregarResultado (Resultado r) {
        this.historial.add(r);
    }

    //Devuelve el historial como una lista inmodificable.
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    //Getters
    public String getUsername(){return nombre;}

    public String getNombre(){return nombre;}

    public Ruleta getRuleta(){return ruleta;}

    public boolean setNombre(String nuevoNombre) {
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            this.nombre = nuevoNombre.trim();
            return true;
        }
        return false;
    }
    
}
