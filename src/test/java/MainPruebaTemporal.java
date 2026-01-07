import ec.edu.sistemalicencias.dao.ConductorDAO;

public class MainPruebaTemporal {
    public static void main(String[] args) {
        try {
            ConductorDAO dao = new ConductorDAO();
            System.out.println(dao.obtenerTodos());
            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


