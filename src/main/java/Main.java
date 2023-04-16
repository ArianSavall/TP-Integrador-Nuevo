import org.Modelos.Partido;
import org.Modelos.Ronda;
import org.utilities.LectordeArchivos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LectordeArchivos lectordeArchivos = new LectordeArchivos();
        lectordeArchivos.leerResultados();
//        Ronda puntos = new Ronda ("1", partidos);
        lectordeArchivos.leerPronosticos();

//lista{Argentina,Polonia,Mexico,Arabia}


    }
}