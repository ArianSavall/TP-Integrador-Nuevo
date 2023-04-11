package org.Modelos;

public class Persona {
    private String nombre;
    private int puntaje = 0;

    private int cantPronosticos = 0;

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public Persona(String nombre, int puntaje, int cantPronosticos) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.cantPronosticos = cantPronosticos;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public int getPuntaje() {return puntaje;}

    public void setPuntaje(int puntaje) {this.puntaje = puntaje;}

    public int getCantPronosticos() {return cantPronosticos;}

    public void setCantPronosticos(int cantPronosticos) {this.cantPronosticos = cantPronosticos;}
}
