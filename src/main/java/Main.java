import org.Modelos.Partido;
import org.Modelos.Ronda;
import org.utilities.LectordeArchivos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        LectordeArchivos lectordeArchivos = new LectordeArchivos(Path.of(args[0]), Path.of(args[1]));
        lectordeArchivos.leerResultados();
        //Ronda puntos = new Ronda ("1", partidos);
        lectordeArchivos.leerPronosticos();




    }
}