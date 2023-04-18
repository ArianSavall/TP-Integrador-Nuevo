<<<<<<< HEAD
import org.Modelos.Partido;
import org.Modelos.Ronda;
import org.utilities.LectordeArchivos;

import java.nio.file.Path;
import java.util.ArrayList;
=======
import org.utilities.LectorDB;
import org.utilities.LectorCSV;

import java.nio.file.Path;
>>>>>>> c7a5b4cf02b1e86e0feaf1a94770077fcf8afcc6
import java.util.List;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD

        LectordeArchivos lectordeArchivos = new LectordeArchivos(Path.of(args[0]), Path.of(args[1]));
        lectordeArchivos.leerResultados();
        //Ronda puntos = new Ronda ("1", partidos);
        lectordeArchivos.leerPronosticos();

//lista{Argentina,Polonia,Mexico,Arabia}


=======
        LectorCSV lectorCSV = new LectorCSV(Path.of(args[0]), Path.of(args[1]));
        System.out.println(args[1]);
        lectorCSV.leerResultados();
        List<String> datosUsuario = lectorCSV.leerConfig();

        LectorDB lectorDB = new LectorDB(lectorCSV);
        lectorDB.leerPronosticos(datosUsuario);
>>>>>>> c7a5b4cf02b1e86e0feaf1a94770077fcf8afcc6
    }
}