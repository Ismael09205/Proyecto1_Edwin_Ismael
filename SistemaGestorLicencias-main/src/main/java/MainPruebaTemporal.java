import ec.edu.sistemalicencias.dao.ConductorDAO;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;

public class MainPruebaTemporal {
    public static void main(String[] args) throws BaseDatosException {
        try {
            ConductorDAO dao = new ConductorDAO();
            System.out.println(dao.obtenerTodos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
