package org.utilities;

import org.Modelos.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Driver;

import static org.utilities.Conector_SQL.*;

public class LectordeArchivos {

    Connection conexion = null;
    Statement consulta = null;

    private List<Ronda> rondas = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();
    private List<Pronostico> pronosticos = new ArrayList<>();
    private List<Persona> personas = new ArrayList<>();


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

        try {
            //abrir conexion
            conexion = DriverManager.getConnection(DB_URL, USER, PASS);

            //Ejecutar consulta
            consulta = conexion.createStatement();
            String sql;
            sql = "select * from nuevo_tp_integrador.resultados";

            ResultSet resultado = consulta.executeQuery(sql);

            while (resultado.next()) {

                int rondaDB = resultado.getInt("Ronda");
                String equipo1DB = resultado.getString("Equipo 1");
                int cantgoles1DB = resultado.getInt("Cant. goles 1");
                int cantgoles2DB = resultado.getInt("Cant. goles 2");
                String equipo2DB = resultado.getString("Equipo 2");

                //todo para chequear que lea bien
               // System.out.println(rondaDB + "   " + equipo1DB +"   " + cantgoles1DB + "   " + cantgoles2DB + "   " + "   " + equipo2DB);

                Equipo equipo1 = this.buscarEquipo(equipo1DB);
                Equipo equipo2 = this.buscarEquipo(equipo2DB);

                //Si el equipo no existe, instanciar uno nuevo
                if (equipo1 == null) {
                    equipo1 = new Equipo(equipo1DB);
                    equipos.add(equipo1);
                }
                if (equipo2 == null) {
                    equipo2 = new Equipo(equipo2DB);
                    equipos.add(equipo2);
                }


                for (Equipo equipo : this.equipos) {
                    if (equipo.getNombre().equals(equipo1DB)) {
                        equipo1 = equipo;
                    }

                    if (equipo.getNombre().equals(equipo2DB)) {
                        equipo2 = equipo;
                    }
                }

                //instancio un nuevo partido
                Partido partido = new Partido(equipo1, equipo2);
                partido.setCantGoles1(cantgoles1DB);
                partido.setCantGoles2(cantgoles2DB);

                Ronda ronda = this.buscarRonda(rondaDB);

                //Si no existe una ronda, crearla
                if (ronda != null) {
                    ronda.agregarPartidos(partido);

                } else {
                    ronda = new Ronda(rondaDB);
                    ronda.agregarPartidos(partido);
                    rondas.add(ronda);
                }
            }
            // Esto se utiliza par cerrar la conexión con la base de datos
            resultado.close();
            consulta.close();
            conexion.close();
        } catch (SQLException se) {
            // Execpción ante problemas de conexión
            se.printStackTrace();
        }
        finally {
            // Esta sentencia es para que ante un problema con la base igual se cierren las conexiones
            try {
                if (consulta != null)
                    consulta.close();
            } catch (SQLException se2) {
            }
            try {
                if (conexion != null)
                    conexion.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public void leerPronosticos() {
        int puntos = 0;
        Ronda rondapatron = new Ronda(1);
        try {

            //abrir conexion
            conexion = DriverManager.getConnection(DB_URL, USER, PASS);
            //Ejecutar consulta
            consulta = conexion.createStatement();

            String sql;
            sql = "select * from nuevo_tp_integrador.pronostico";

            ResultSet resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                int rondaDB = resultado.getInt("Ronda");
                String participanteDB = resultado.getString("Participante");
                String equipo1DB = resultado.getString("Equipo 1");
                String gana1DB = resultado.getString("Gana 1");
                String empataDB = resultado.getString("Empata");
                String gana2DB = resultado.getString("Gana 2");
                String equipo2DB = resultado.getString("Equipo 2");

                Equipo equipo1 = this.buscarEquipo(equipo1DB);
                Equipo equipo2 = this.buscarEquipo(equipo2DB);


                if (equipo1 != null) {
                    equipo1 = new Equipo(equipo1DB);
                 }
                if (equipo2 != null) {
                    equipo2 = new Equipo(equipo2DB);
                }

                Ronda ronda = new Ronda(rondaDB);

                for (Ronda rondaLista : rondas) {
                if (rondaLista.getNro().equals(ronda.getNro())) { //si la ronda que está en la lista es igual a la ronda que lee en el pronostico...
                    ronda = rondaLista;
                    }
                }

                if (rondapatron.getNro() != ronda.getNro()) { //si se detecta un cambio de ronda, que imprima los puntajes de la ronda que acaba de finalizar
                    System.out.println("Resultados de la ronda " + rondapatron.getNro());
                    for (int i = 0; i < this.personas.size(); i++) {
                        System.out.println(personas.get(i).getNombre());
                        System.out.println(personas.get(i).getPuntaje());
                    }
                    rondapatron.setNro(ronda.getNro());
                }

                Persona persona = this.buscarPersona(participanteDB);

                if (persona == null) { //si no existe la persona, crearla, añadirla a la lista y reiniciar la variable puntos
                    persona = new Persona(participanteDB, ronda.getNro());
                    this.personas.add(persona);
                    puntos = 0;
                }

                if (persona.getNroRonda() != ronda.getNro()) { //si la persona ya existe pero cambió la ronda, que se reinicien los puntos
                    persona.setNroRonda(ronda.getNro());
                    puntos = 0;
                }

                Partido partido = ronda.buscarPartido(equipo1, equipo2);
                Equipo equipoPred = null;
                ResultadoEnum resultadoPred = null;

                if ("X".equals(gana1DB)) {  //si la X está en gana1
                    equipoPred = partido.getEquipo1();
                    resultadoPred = ResultadoEnum.GANADOR;

                }
                if ("X".equals(empataDB)) {  //si la X está en empate
                    equipoPred = partido.getEquipo1();
                    resultadoPred = ResultadoEnum.EMPATE;
                }

                if ("X".equals(gana2DB)) {  //si la X está en gana2
                    equipoPred = partido.getEquipo1();
                    resultadoPred = ResultadoEnum.PERDEDOR;
                }

                Pronostico pronostico = new Pronostico(partido, equipoPred, persona);

                //sumamos los puntos que correspondan y los metemos en el puntaje de la persona
                puntos += pronostico.puntos(resultadoPred);
                pronostico.getPersona().setPuntaje(puntos);

                this.pronosticos.add(pronostico);
            }
            } catch (SQLException se) {
              // Execpción ante problemas de conexión
                se.printStackTrace();
            }  finally {
                // Esta sentencia es para que ante un problema con la base igual se cierren las conexiones
                try {
                  if (consulta != null)
                     consulta.close();
                } catch (SQLException se2) {
                }
                try {
                    if (conexion != null)
                        conexion.close();
                } catch (SQLException se) {
                 se.printStackTrace();
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