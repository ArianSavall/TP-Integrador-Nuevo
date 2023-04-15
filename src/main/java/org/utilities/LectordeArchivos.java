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
    private List<Ronda> rondas = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();
    private List<Pronostico> pronosticos = new ArrayList<>();
    private List<Persona> personas = new ArrayList<>();


    public LectordeArchivos(Path rutaResultados, Path rutaPronostico) {
        this.rutaResultados = rutaResultados;
        this.rutaPronostico = rutaPronostico;
    }

    //metodos para buscar
    public Ronda buscarRonda(Integer i) {
        for (Ronda r : this.rondas) {
            if (r.getNro() == i) {
                return r;
            }
        }
        return null;
    }
    public Equipo buscarEquipo(String i) {
        for (Equipo e : this.equipos) {
            if (e.getNombre().equals(i)) {
                return e;
            }
        }
        return null;
    }
    public Persona buscarPersona(String i) {
        for (Persona p : this.personas) {
            if (p.getNombre().equals(i)) {
                return p;
            }
        }
        return null;
    }



    public void leerResultados(){
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
                Equipo equipo1 = this.buscarEquipo(campos[1]);
                Equipo equipo2 = this.buscarEquipo(campos[4]);

                //Si el equipo no existe, instanciar uno nuevo
                if (equipo1 == null) {
                    equipo1 = new Equipo(campos[1]);
                    equipos.add(equipo1);
                }
                if (equipo2 == null) {
                    equipo2 = new Equipo(campos[4]);
                    equipos.add(equipo2);
                }


                for (Equipo equipo : this.equipos) {
                    if (equipo.getNombre().equals(campos[1])) {
                        equipo1 = equipo;
                    }

                    if (equipo.getNombre().equals(campos[4])) {
                        equipo2 = equipo;
                    }
                }

                //instancio un nuevo partido
                Partido partido = new Partido(equipo1, equipo2);
                partido.setCantGoles1(Integer.parseInt(campos[2]));
                partido.setCantGoles2(Integer.parseInt(campos[3]));

                Ronda ronda = this.buscarRonda(Integer.parseInt(campos[0]));

                //Si no existe una ronda, crearla
                if (ronda != null) {
                    ronda.agregarPartidos(partido);

                } else {
                    ronda = new Ronda(Integer.parseInt(campos[0]));
                    ronda.agregarPartidos(partido);
                    rondas.add(ronda);
                }
            }
        }
    }

    public void leerPronosticos() {
        List<String> lineasPronostico = new ArrayList<>();
        int puntos = 0;

        Ronda rondapatron = new Ronda(1);
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
                Equipo equipo1 = this.buscarEquipo(campos[2]);
                Equipo equipo2 = this.buscarEquipo(campos[6]);


                if (equipo1!=null){
                    equipo1 = new Equipo(campos[2]);
                }
                if (equipo2!=null){
                    equipo2 = new Equipo(campos[6]);
                }

                Ronda ronda = new Ronda(Integer.parseInt(campos[0]));

                for (Ronda rondaLista : rondas) {
                    if (rondaLista.getNro().equals(ronda.getNro())){ //si la ronda que está en la lista es igual a la ronda que lee en el pronostico...
                        ronda=rondaLista;
                    }
                }

                if(rondapatron.getNro()!=ronda.getNro()){ //si se detecta un cambio de ronda, que imprima los puntajes de la ronda que acaba de finalizar
                    System.out.println("Resultados de la ronda " + rondapatron.getNro());
                    for(int i=0; i<this.personas.size(); i++) {
                     System.out.println(personas.get(i).getNombre());
                     System.out.println(personas.get(i).getPuntaje());
                    }
                    rondapatron.setNro(ronda.getNro());
                }

                Persona persona = this.buscarPersona(campos[1]);

                if(persona==null){ //si no existe la persona, crearla, añadirla a la lista y reiniciar la variable puntos
                   persona= new Persona(campos[1], ronda.getNro());
                   this.personas.add(persona);
                   puntos=0;
                }

                if(persona.getNroRonda()!=ronda.getNro()){ //si la persona ya existe pero cambió la ronda, que se reinicien los puntos
                    persona.setNroRonda(ronda.getNro());
                    puntos = 0;
                }

                Partido partido = ronda.buscarPartido(equipo1, equipo2);
                Equipo equipoPred = null;
                ResultadoEnum resultadoPred = null;

                if("X".equals(campos[3])){  //si la X está en gana1
                    equipoPred = partido.getEquipo1();
                    resultadoPred = ResultadoEnum.GANADOR;

                }
                if("X".equals(campos[4])){  //si la X está en empate
                    equipoPred = partido.getEquipo1();
                    resultadoPred = ResultadoEnum.EMPATE;
                }

                if("X".equals(campos[5])){  //si la X está en gana2
                    equipoPred = partido.getEquipo1();
                    resultadoPred = ResultadoEnum.PERDEDOR;
                }

                Pronostico pronostico = new Pronostico(partido, equipoPred, persona);

                //sumamos los puntos que correspondan y los metemos en el puntaje de la persona
                puntos += pronostico.puntos(resultadoPred);
                pronostico.getPersona().setPuntaje(puntos);

                this.pronosticos.add(pronostico);
            }
        }

        //imprimo los resultados de la ronda final
        System.out.println("Resultados de la ronda " + rondapatron.getNro());
        for(int k=0; k<this.personas.size(); k++) {
            System.out.println(personas.get(k).getNombre());
            System.out.println(personas.get(k).getPuntaje());
        }
    }
}