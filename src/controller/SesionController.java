package controller;

import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

public class SesionController {
    private Usuario usuarioActual;
    private List<Usuario> usuariosRegistrados = new ArrayList<>();

    public SesionController(){
        usuariosRegistrados.add(new Usuario("admin", "admin123", "Administrador"));
        usuariosRegistrados.add(new Usuario("Gunnar", "gunnar123", "Gunnar Holidays"));
        usuariosRegistrados.add(new Usuario("Luisardinho", "1234", "Luis Villalobos"));
    }
    
    public boolean iniciarSesion(String u, String p){
        for (Usuario user : usuariosRegistrados){
            if (user.validarCredenciales(u, p)){
                this.usuarioActual = user;
                return true;
            }
        }
        return false;
    }

    public void cerrarSesion(){
        this.usuarioActual = null;
    }

    public void registrarUsuario(String u, String p, String n) throws IllegalArgumentException {
        if (u.isBlank() || p.isBlank() || n.isBlank()) {
            throw new IllegalArgumentException("Todos los campos son requeridos.");
        }
        usuariosRegistrados.add(new Usuario(u, p, n));
    }

    // --- Getters para la Vista ---
    public String getNombreUsuario() {
        return usuarioActual != null ? usuarioActual.getNombre() : "Invitado";
    }
    
    public int getSaldoActual() {
        return usuarioActual != null ? usuarioActual.getRuleta().getSaldo() : 0; 
    }
    
    public boolean setNombreUsuario(String nuevoNombre) {
        // La vista usa el setter del controlador, que lo pasa al setter validado del Modelo
        return usuarioActual != null && usuarioActual.setNombre(nuevoNombre);
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
