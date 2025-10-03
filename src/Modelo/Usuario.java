package Modelo;

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

    public Usuario(){
        this("Invitado", "", "Usuario Invitado");
    }

    public boolean validarCredenciales(String u, String p){
        return this.username.equals(u) && this.password.equals(p);
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
