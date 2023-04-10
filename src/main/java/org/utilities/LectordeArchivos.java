package org.utilities;

import org.Modelos.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectordeArchivos {
    private Path rutaResultados;
    private Path rutaPronostico;
    private List<Partido> partidos = new ArrayList<>();


    public LectordeArchivos(Path rutaResultados, Path rutaPronostico) {
        this.rutaResultados = rutaResultados;
        this.rutaPronostico = rutaPronostico;
    }


    public List<Partido> leerResultados() {
        List<String> lineasResultado = new ArrayList<>();
        try {
            lineasResultado = Files.readAllLines(rutaResultados);
        } catch (
                IOException e) {
            System.out.println("No se pudo leer la linea de resultados...");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        boolean primera = true;
        for (String lineaResultado : lineasResultado) {
            if (primera) {
                primera = false;
            } else {
                String[] campos = lineaResultado.split(";");
                Equipo equipo1 = new Equipo(campos[1]);
                Equipo equipo2 = new Equipo(campos[4]);
                Partido partido = new Partido(equipo1, equipo2);
                partido.setCantGoles1(Integer.parseInt(campos[2]));
                partido.setCantGoles2(Integer.parseInt(campos[3]));
                partidos.add(partido);
            }
        }
        return partidos;
    }


    public void calcularPuntos() {
        int puntos= 0;
        List<String> lineasPronostico = new ArrayList<>();
//        Persona personaStarter = new Persona ("");
//        int i=-1; //para recorrer la lista puntajes
//        int k=-1; //para recorrer la lista personas
        Persona persona = new Persona("",0);
        try {
            lineasPronostico = Files.readAllLines(rutaPronostico);
        } catch (IOException e) {
            System.out.println("No se pudo leer la linea de pronosticos...");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        boolean primera = true;
        for (String lineaPronostico : lineasPronostico) {
            if (primera) {
                primera = false;
            } else {
                String[] campos = lineaPronostico.split(";");
                Equipo equipo1 = new Equipo(campos[1]);
                Equipo equipo2 = new Equipo(campos[5]);
                Partido partido;

                if(!(persona.getNombre().equals(campos[0]))) {
                    if(persona.getNombre().equals("")){
                        Persona p = new Persona(campos[0], 0);
                        persona = p;
                        puntos = 0;
                    }
                    else {
                        System.out.println("El puntaje de " + persona.getNombre() + " fue " + persona.getPuntaje());
                        Persona p = new Persona(campos[0], 0);
                        persona = p;
                        puntos = 0;
                    }
                }




                for (Partido partidoList : partidos) {
                    if (partidoList.getEquipo1().getNombre().equals(equipo1.getNombre())
                            && partidoList.getEquipo2().getNombre().equals(equipo2.getNombre())) {
                        
                        partido = partidoList;

                        Equipo equipo = null;
                        ResultadoEnum resultado = null;
                        if("X".equals(campos[2])) {
                            equipo = equipo1;
                            resultado = ResultadoEnum.GANADOR;
                        }
                        if("X".equals(campos[3])) {
                            equipo = equipo1;
                            resultado = ResultadoEnum.EMPATE;
                        }
                        if("X".equals(campos[4])) {
                            equipo = equipo1;
                            resultado = ResultadoEnum.PERDEDOR;
                        }
                        Pronostico pronostico = new Pronostico(partido, equipo, resultado);
                        //sumar los puntos correspondientes
                        puntos+=pronostico.puntos();
                        persona.setPuntaje(puntos);

                        }
                    }

            }

        }

        // mostrar los puntos
        System.out.println("El puntaje de " + persona.getNombre() + " fue " + persona.getPuntaje());
        System.out.println();
    }

}



