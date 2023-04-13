package org.Modelos;

import java.util.List;

public class Pronostico {
    private Partido partido;
    private Equipo equipo;
    private Persona persona;


    public Pronostico(Partido partido, Equipo equipo, Persona persona) {
        this.partido = partido;
        this.equipo = equipo;
        this.persona = persona;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    //si empatan, eqGanador = null;
    public int puntos(){
        // this.resultado -> pred

        ResultadoEnum resultadoReal = partido.resultado(equipo);
        if (resultado.equals(resultadoReal)) {
            return 1 ;
        } else {
            return 0;
        }

    }
    public int sumarPronosticos(){
        ResultadoEnum resultadoReal = partido.resultado(equipo);
        if (resultado.equals(resultadoReal)) {
            return 1 ;
        } else {
            return 0;
        }
    }
}
