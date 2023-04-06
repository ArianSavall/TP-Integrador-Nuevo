package org.Modelos;

public class Pronostico {
    private Partido partido;
    private Equipo equipo;
    private ResultadoEnum resultado;

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado) {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
    }

    public int puntos(){
        // this.resultado -> pred
        ResultadoEnum resultadoReal = partido.resultado(equipo);
        if (this.resultado.equals(resultadoReal)) {
            return 1;
        } else {
            return 0;
        }

    }
}
