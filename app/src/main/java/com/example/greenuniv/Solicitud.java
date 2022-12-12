package com.example.greenuniv;

public class Solicitud {


    private String nombreSolicitante;
    private String univer;
    private String tipoSoli;
    private String monto;
    private String articuloIntercambiar;
    private String articuloinicial;
    private String nombreEstud;
    private String imgurlArtiInter;

    public Solicitud(String nombreSolicitante, String univer, String tipoSoli, String monto, String articuloIntercambiar, String articuloinicial, String nombreEstud, String imgurlArtiInter) {
        this.nombreSolicitante = nombreSolicitante;
        this.univer = univer;
        this.tipoSoli = tipoSoli;
        this.monto = monto;
        this.articuloIntercambiar = articuloIntercambiar;
        this.articuloinicial = articuloinicial;
        this.nombreEstud = nombreEstud;
        this.imgurlArtiInter = imgurlArtiInter;
    }

    public String getImgurlArtiInter() {
        return imgurlArtiInter;
    }

    public void setImgurlArtiInter(String imgurlArtiInter) {
        this.imgurlArtiInter = imgurlArtiInter;
    }

    public  Solicitud(){

    }


    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getUniver() {
        return univer;
    }

    public void setUniver(String univer) {
        this.univer = univer;
    }

    public String getTipoSoli() {
        return tipoSoli;
    }

    public void setTipoSoli(String tipoSoli) {
        this.tipoSoli = tipoSoli;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getArticuloIntercambiar() {
        return articuloIntercambiar;
    }

    public void setArticuloIntercambiar(String articuloIntercambiar) {
        this.articuloIntercambiar = articuloIntercambiar;
    }

    public String getArticuloinicial() {
        return articuloinicial;
    }

    public void setArticuloinicial(String articuloinicial) {
        this.articuloinicial = articuloinicial;
    }

    public String getNombreEstud() {
        return nombreEstud;
    }

    public void setNombreEstud(String nombreEstud) {
        this.nombreEstud = nombreEstud;
    }
}
