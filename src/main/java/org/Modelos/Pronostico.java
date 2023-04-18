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

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }


    //si empatan, eqGanador = null;


    public int puntos(ResultadoEnum resultado) {
        // this.resultado -> pred

<<<<<<< HEAD
        ResultadoEnum resultadoReal = partido.resultado(equipo);
=======
        ResultadoEnum resultadoReal = partido.obtenerResultadoReal(equipo);
>>>>>>> c7a5b4cf02b1e86e0feaf1a94770077fcf8afcc6
        if (resultado.equals(resultadoReal)) {
            return 1;
        } else {
            return 0;
        }
<<<<<<< HEAD
//
//    }
//    public int sumarPronosticos(){
//        ResultadoEnum resultadoReal = partido.resultado(equipo);
//        if (resultado.equals(resultadoReal)) {
//            return 1 ;
//        } else {
//            return 0;
//        }
//    }
    }
=======

    }
    public int sumarPronosticoAcertado(ResultadoEnum resultado){
        ResultadoEnum resultadoReal = partido.obtenerResultadoReal(equipo);
        if (resultado.equals(resultadoReal)) {
            return 1 ;
        } else {
            return 0;
        }
    }

>>>>>>> c7a5b4cf02b1e86e0feaf1a94770077fcf8afcc6
}
