package vallegrande.edu.pe.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vallegrande.edu.pe.controller.MainController;
import vallegrande.edu.pe.model.Dueno;

public class MainView extends Application {

    @Override
    public void start(Stage stage) {
        MainController controller = new MainController();

        // Formulario
        Label tituloForm = new Label("REGISTRO DE DUEÑO");
        tituloForm.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");

        Button btnRegistrar = new Button("REGISTRAR");
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnRegistrar.setStyle("-fx-background-color: #0284c7; -fx-text-fill: white; -fx-font-weight: bold;");

        // Tabla
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

        // Evento del botón
        btnRegistrar.setOnAction(e ->
                controller.registrar(txtNombre, txtTelefono, txtDireccion, tabla));

        controller.cargarTabla(tabla);

        Label tituloTabla = new Label("LISTADO DE DUEÑOS");
        tituloTabla.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox root = new VBox(10, tituloForm, txtNombre, txtTelefono, txtDireccion,
                btnRegistrar, tituloTabla, tabla);
        root.setPadding(new Insets(15));

        stage.setTitle("Veterinaria - Dueños");
        stage.setScene(new Scene(root, 650, 550));
        stage.show();
    }

}