package org.Modelos;

public class Partido {
    private Equipo equipo1;
    private Equipo equipo2;
    private int cantGoles1;
    private int cantGoles2;

    public Partido(Equipo equipo1, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public int getCantGoles1() {
        return cantGoles1;
    }

    public void setCantGoles1(int cantGoles1) {
        this.cantGoles1 = cantGoles1;
    }

    public int getCantGoles2() {
        return cantGoles2;
    }

    public void setCantGoles2(int cantGoles2) {
        this.cantGoles2 = cantGoles2;
    }

    public ResultadoEnum resultado(Equipo equipo){

        return null;
    }

}
