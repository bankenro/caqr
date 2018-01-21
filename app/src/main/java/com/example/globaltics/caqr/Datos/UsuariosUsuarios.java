package com.example.globaltics.caqr.Datos;

/*
 * Created by Sam on 19/04/2017.
 */

public class UsuariosUsuarios {
    private int id;
    private String nombre,imagen,tipo,codigo;
    public UsuariosUsuarios(int id, String nombre, String codigo, String imagen, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.imagen = imagen;
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }
    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
