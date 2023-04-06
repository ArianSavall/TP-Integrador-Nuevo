import org.utilities.LectordeArchivos;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        LectordeArchivos lectordeArchivos = new LectordeArchivos(Path.of(args[0]), Path.of(args[1]));



    }
}