import org.Modelos.Partido;
import org.Modelos.Ronda;
import org.utilities.LectordeArchivos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //       try {     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");     System.out.println("Driver OK"); } catch (ClassNotFoundException e) {     System.out.println(e); }
        LectordeArchivos lectordeArchivos = new LectordeArchivos(Path.of(args[0]), Path.of(args[1]));
        lectordeArchivos.leerResultados();
        lectordeArchivos.leerPronosticos();

//lista{Argentina,Polonia,Mexico,Arabia}


    }
}