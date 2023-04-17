import org.utilities.LectorDB;
import org.utilities.LectorCSV;

public class Main {
    public static void main(String[] args) {
        LectorCSV lectorCSV = new LectorCSV();
        lectorCSV.leerResultados();

        LectorDB lectorDB = new LectorDB(lectorCSV);
        lectorDB.leerPronosticos();
    }
}