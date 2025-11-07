package controller;

import modelo.IRepositorioResultados;
import modelo.RepositorioArchivo;
import modelo.RepositorioEnMemoria;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SesionController {
    private Usuario usuarioActual;
    private List<Usuario> usuariosRegistrados = new ArrayList<>();
    private final boolean USAR_PERSISTENCIA_ARCHIVO = false;

    public SesionController(){
        IRepositorioResultados repositorio = new RepositorioEnMemoria();

        usuariosRegistrados.add(new Usuario("admin", "admin123", "Administrador", repositorio));
        usuariosRegistrados.add(new Usuario("Gunnar", "gunnar123", "Gunnar Holidays", repositorio));
        usuariosRegistrados.add(new Usuario("Luisardinho", "1234", "Luis Villalobos", repositorio));
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

    public void registrarUsuario(String u, String p, String n) throws IllegalArgumentException {
        if (u.isBlank() || p.isBlank() || n.isBlank()) {
            throw new IllegalArgumentException("Todos los campos son requeridos.");
        }
        usuariosRegistrados.add(new Usuario(u, p, n, null));

        IRepositorioResultados repositorio;
        if (USAR_PERSISTENCIA_ARCHIVO) {
            repositorio = new RepositorioArchivo();
        } else {
            repositorio = new RepositorioEnMemoria();
        }

        Usuario nuevoUsuario = new Usuario(u, p, n, repositorio);
    }

    // --- Getters para la Vista ---
    public String getNombreUsuario() {
        return usuarioActual != null ? usuarioActual.getNombre() : "Invitado";
    }
    
    public int getSaldoActual() {
        return usuarioActual != null ? usuarioActual.getRuleta().getSaldo() : 0; 
    }
    
    public boolean setNombreUsuario(String nuevoNombre) {
        return usuarioActual != null && usuarioActual.setNombre(nuevoNombre);
    }

    public void guardarHistorial() {
        if (hayUsuario()) {
            usuarioActual.getRepositorio().guardarEnFuente();
        }
    }

    public void cerrarSesion() {
        guardarHistorial();
        usuarioActual = null;
    }

    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
