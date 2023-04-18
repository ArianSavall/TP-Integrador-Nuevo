package org.Modelos;

public class Persona {
    private String nombre;
    private int puntaje = 0;
    private int cantPronosticosAcertados = 0;
<<<<<<< HEAD

    public Persona(String nombre) {
        this.nombre = nombre;
=======
    private int nroRonda = 0;

    public Persona(String nombre, int nroRonda) {
        this.nombre = nombre;
        this.nroRonda = nroRonda;
>>>>>>> c7a5b4cf02b1e86e0feaf1a94770077fcf8afcc6
    }

    public Persona(String nombre, int puntaje, int cantPronosticosAcertados) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.cantPronosticosAcertados = cantPronosticosAcertados;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public int getPuntaje() {return puntaje;}

    public void setPuntaje(int puntaje) {this.puntaje = puntaje;}

    public int getCantPronosticos() {return cantPronosticosAcertados;}

    public void setCantPronosticos(int cantPronosticos) {this.cantPronosticosAcertados = cantPronosticos;}
<<<<<<< HEAD
=======

    public int getNroRonda() {return nroRonda;}

    public void setNroRonda(int nroRonda) {this.nroRonda = nroRonda;}
>>>>>>> c7a5b4cf02b1e86e0feaf1a94770077fcf8afcc6
}
