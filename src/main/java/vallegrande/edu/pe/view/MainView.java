package vallegrande.edu.pe.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vallegrande.edu.pe.controller.MainController;
import vallegrande.edu.pe.model.Dueno;

public class MainView extends Application {

    @Override
    public void start(Stage stage) {
        TableView<Dueno> tabla = new TableView<>();

        TableColumn<Dueno, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Dueno, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Dueno, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Dueno, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        tabla.getColumns().add(colId);
        tabla.getColumns().add(colNombre);
        tabla.getColumns().add(colTelefono);
        tabla.getColumns().add(colDireccion);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        new MainController().cargarTabla(tabla);

        Label titulo = new Label("LISTADO DE DUEÑOS");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox root = new VBox(10, titulo, tabla);
        root.setPadding(new Insets(15));

        stage.setTitle("Veterinaria - Dueños");
        stage.setScene(new Scene(root, 650, 400));
        stage.show();
    }

}