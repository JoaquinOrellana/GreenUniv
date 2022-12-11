package com.example.greenuniv;

public class Articulo {

    private String nombre;
    private String estado;
    private String precio;
    private String descripcion;
    private String imgurl;
    private String solicitudes;

    public String getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(String solicitudes) {
        this.solicitudes = solicitudes;
    }



    public String getNombre() {
        return nombre;
    }

    public Articulo(String nombre) {
        this.nombre = nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDuenio() {
        return duenio;
    }

    public Articulo(String nombre, String estado, String precio, String descripcion, String imgurl, String solicitudes, String tipo, String duenio, String universidad) {
        this.nombre = nombre;
        this.estado = estado;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imgurl = imgurl;
        this.solicitudes = solicitudes;
        this.tipo = tipo;
        this.duenio = duenio;
        this.universidad = universidad;
    }

    public Articulo(){

    }
    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    private String tipo;
    private String duenio;
    private String universidad;



}
