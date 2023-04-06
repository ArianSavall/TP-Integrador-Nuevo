package org.Modelos;

import java.util.List;

public class Ronda {
    private String nro;
    private List<Partido> partidos;

    public Ronda(String nro, List<Partido> partidos) {
        this.nro = nro;
        this.partidos = partidos;
    }
}
