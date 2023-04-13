package org.Modelos;

import java.util.List;

public class Ronda {
    private Integer nro;
    private List<Partido> partidos;

    public Ronda(Integer nro, List<Partido> partidos) {
        this.nro = nro;
        this.partidos = partidos;
    }

    public Ronda(Integer nro) {
        this.nro = nro;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public void agregarPartidos(Partido partido){
        this.partidos.add(partido) ;
    }
}
