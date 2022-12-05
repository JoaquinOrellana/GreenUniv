package com.example.greenuniv;

public class Usuario {

    private String nombre;
    private String apellido;
    private String edad;
    private String correo;
    private String telefono;
    private String universidad;
    private String password;
    private String rol;
    private String imgurlPerf;
    private String imgurlCarnet;

    public String getImgurlCarnet() {
        return imgurlCarnet;
    }

    public void setImgurlCarnet(String imgurlCarnet) {
        this.imgurlCarnet = imgurlCarnet;
    }

    public String getImgurlPerf() {
        return imgurlPerf;
    }

    public void setImgurlPerf(String imgurlPerf) {
        this.imgurlPerf = imgurlPerf;
    }





    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario(String nombre, String apellido, String edad, String correo, String telefono, String universidad, String password, String rol, String imgurlPerf, String imgurlCarnet) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.universidad = universidad;
        this.password = password;
        this.rol = rol;
        this.imgurlPerf = imgurlPerf;
        this.imgurlCarnet = imgurlCarnet;
    }
}
