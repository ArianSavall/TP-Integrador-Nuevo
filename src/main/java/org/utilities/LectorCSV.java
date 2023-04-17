package org.utilities;

import org.Modelos.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.utilities.LectorDB.*;

public class LectorCSV {

    Connection conexion = null;
    Statement consulta = null;

    private List<Ronda> rondas = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();


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
}