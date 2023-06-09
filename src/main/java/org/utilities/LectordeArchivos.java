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

    //?constructor
    public LectordeArchivos(Path rutaResultados, Path rutaPronostico) {
        this.rutaResultados = rutaResultados;
        this.rutaPronostico = rutaPronostico;
    }

    //?buscar
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
                Partido partido = new Partido(equipo1, equipo2);
                partido.setCantGoles1(Integer.parseInt(campos[2]));
                partido.setCantGoles2(Integer.parseInt(campos[3]));



                //Buscar equipo en la lista de equipos en lugar de instanciarlos. Crear metodo que instancie nuevo equipo
//                de no existir

                Ronda ronda = this.buscarRonda(Integer.parseInt(campos[0]));


                if (ronda != null) {
                    ronda.agregarPartidos(partido);

                } else {
                    ronda = new Ronda(Integer.parseInt(campos[0]));
                    ronda.agregarPartidos(partido);
                    rondas.add(ronda);
                }

                //System.out.println(ronda.getNro()+ "    " + partido.getEquipo1().getNombre() + "    " + partido.getCantGoles1() + "    " + partido.getCantGoles2() + "    " + partido.getEquipo2().getNombre());
            }
        }
    }


//    public void calcularPuntos() {
//        int puntos = 0;
//        int cantPronosticos = 0;
//        //int i=0;
//        List<String> lineasPronostico = new ArrayList<>();
//
//        Persona persona = new Persona("", 0, 0);
//        try {
//            lineasPronostico = Files.readAllLines(rutaPronostico);
//        } catch (IOException e) {
//            System.out.println("No se pudo leer la linea de pronosticos...");
//            System.out.println(e.getMessage());
//            System.exit(1);
//        }
//        boolean primera = true;
//        for (String lineaPronostico : lineasPronostico) {
//            if (primera) {
//                primera = false;
//            } else {
//                String[] campos = lineaPronostico.split(";");
//                Equipo equipo1 = new Equipo(campos[2]);
//                Equipo equipo2 = new Equipo(campos[6]);
//                Partido partido = null;
//
//                if (!(persona.getNombre().equals(campos[1]))) {
//                    if (persona.getNombre().equals("")) {
//                        Persona p = new Persona(campos[1], 0, 0);
//                        persona = p;
//                        puntos = 0;
//                    } else {
//                        System.out.println("El puntaje de " + persona.getNombre() + " fue " + persona.getPuntaje()
//                                + " y acertó " + persona.getCantPronosticos() + " pronóstico/s");
//                        Persona p = new Persona(campos[1], 0, 0);
//                        persona = p;
//                        puntos = 0;
//                        cantPronosticos = 0;
//
//                    }
//                }
//
//                for (Partido partidoList : partidos) {
//                    if (partidoList.getEquipo1().getNombre().equals(equipo1.getNombre())
//                            && partidoList.getEquipo2().getNombre().equals(equipo2.getNombre())) {
//
//                        partido = partidoList;
//
//                        Equipo equipo = null;
//                        ResultadoEnum resultado = null;
//                        if ("X".equals(campos[3])) {
//                            equipo = equipo1;
//                            resultado = ResultadoEnum.GANADOR;
//                        }
//                        if ("X".equals(campos[4])) {
//                            equipo = equipo1;
//                            resultado = ResultadoEnum.EMPATE;
//                        }
//                        if ("X".equals(campos[5])) {
//                            equipo = equipo1;
//                            resultado = ResultadoEnum.PERDEDOR;
//                        }
//                        Pronostico pronostico = new Pronostico(partido, equipo, resultado);
//                        //sumar los puntos correspondientes
//                        puntos += pronostico.puntos();
//                        cantPronosticos += pronostico.sumarPronosticos();
//                        persona.setPuntaje(puntos);
//                        persona.setCantPronosticos(cantPronosticos);
//
//                    }
//                }
//
//            }
//
//
//        }
//
//        // mostrar los puntos
//        System.out.println("El puntaje de " + persona.getNombre() + " fue " + persona.getPuntaje()
//                + " y acertó " + persona.getCantPronosticos() + " pronóstico/s");
//    }

    public void leerPronosticos() {
        List<String> lineasPronostico = new ArrayList<>();

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
                    if (rondaLista.getNro().equals(ronda.getNro())){
                        ronda=rondaLista;
                    }
                }

                Persona persona = this.buscarPersona(campos[1]);

                if(persona==null){
                   persona= new Persona(campos[1]);
                   this.personas.add(persona);
                }

                Partido partido = ronda.buscarPartido(equipo1, equipo2);
                Equipo equipoPred = null;
                ResultadoEnum resultado = null;

                if("X".equals(campos[3])){
                    equipoPred = partido.getEquipo1();
                    resultado = ResultadoEnum.GANADOR;

                }
                if("X".equals(campos[4])){
                    equipoPred = partido.getEquipo1();
                    resultado = ResultadoEnum.EMPATE;
                }

                if("X".equals(campos[5])){
                    equipoPred = partido.getEquipo1();
                    resultado = ResultadoEnum.PERDEDOR;
                }

                //Equipo equipoPred = this.leerEquipopronosticado(campos, partido);

               // Equipo equipoGanador = partido.calcularGanador(partido);

                Pronostico pronostico = new Pronostico(partido, equipoPred, persona);

                this.pronosticos.add(pronostico);








                //Ronda ronda = this.buscarRonda(Integer.parseInt(campos[0]));

                //Dentro de la clase ronda, hacer un método que reciba dos equipos por parámetro y que me devuelva el partido
//                con esos dos equipos
                //this.buscarequipo para buscar equipo ganador
                //this.buscarPersona para buscar persona ganadora
                //Partido partido = ronda.buscarPartido(eq1,eq2)
                //instanciar nuevo pronostico con estos datos y agregarlo a la lista
//                Pronostico pronostico = new Pronostico(partido, equipoganador, persona);

            }
        }
    }
    /*
    public Equipo leerEquipopronosticado(String[] campos, Partido partido){
        if("X".equals(campos[3])){
            return partido.getEquipo1();
        }
        if("X".equals(campos[5])){
            return partido.getEquipo2();
        }
        return null;
    }
*/
}






//    private int calcularPuntos(){
//
//    }




