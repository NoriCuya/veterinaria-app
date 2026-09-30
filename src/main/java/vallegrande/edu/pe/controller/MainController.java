package vallegrande.edu.pe.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import vallegrande.edu.pe.model.Dueno;
import vallegrande.edu.pe.model.DuenoDAO;

public class MainController {

    private final DuenoDAO dao = new DuenoDAO();

    public void cargarTabla(TableView<Dueno> tabla) {
        try {
            tabla.setItems(FXCollections.observableArrayList(dao.listar()));
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo conectar a la base de datos");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

}