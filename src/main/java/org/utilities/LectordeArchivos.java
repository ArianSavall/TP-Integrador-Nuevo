package org.utilities;

import org.Modelos.Equipo;
import org.Modelos.Partido;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectordeArchivos {
    private Path rutaResultados;
    private Path rutaPronostico;

    public LectordeArchivos(Path rutaResultados, Path rutaPronostico) {
        this.rutaResultados = rutaResultados;
        this.rutaPronostico = rutaPronostico;
    }


    public List<Partido> getResultados() {
        List<String> resultados = new ArrayList<>();
        List<Partido> partidos = new ArrayList<>();
        try {
            resultados = Files.readAllLines(rutaResultados);
        } catch (
                IOException e) {
            System.out.println("No se pudo leer la linea de resultados...");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        boolean primera = true;
        for (String lineaResultado : resultados) {
            if (primera) {
                primera = false;
            } else {
                // Argentina,1,2,Arabia Saudita
                String[] campos = lineaResultado.split(";");
                Equipo equipo1 = new Equipo(campos[0]);
                Equipo equipo2 = new Equipo(campos[3]);
                Partido partido = new Partido(equipo1, equipo2);
                partido.setCantGoles1(Integer.parseInt(campos[1]));
                partido.setCantGoles2(Integer.parseInt(campos[2]));
                partidos.add(partido);
                System.out.println(lineaResultado);
            }

        }
        return partidos;
    }



}
