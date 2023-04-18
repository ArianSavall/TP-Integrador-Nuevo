package org.utilities;

import org.Modelos.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.utilities.LectorDB.*;

public class LectorCSV {

    private Path rutaResultados;
    private Path rutaConfig;
    private List<Ronda> rondas = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();


    public LectorCSV(Path rutaResultados, Path rutaConfig) {
        this.rutaResultados = rutaResultados;
        this.rutaConfig = rutaConfig;
    }

    public LectorCSV() {
    }

    public List<Ronda> getRondas() {
        return rondas;
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


    public void leerResultados() {
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

    public List<String> leerConfig() {
        List<String> lineasConfig = new ArrayList<>();
        List<String> datosUsuario = new ArrayList<>();
        try {
            lineasConfig = Files.readAllLines(rutaConfig);
        } catch (
                IOException e) {
            System.out.println("No se pudo leer la linea de configuraciones...");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        boolean primera = true;
        for (String lineaConfig : lineasConfig) {
            if(primera){
                primera=false;
            }else{
                String[] campos = lineaConfig.split(";");
                datosUsuario.addAll(Arrays.asList(campos).subList(0, 6));
            }
        }
        return datosUsuario;
    }
}