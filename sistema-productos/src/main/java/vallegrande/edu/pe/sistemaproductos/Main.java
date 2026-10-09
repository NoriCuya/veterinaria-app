package vallegrande.edu.pe.sistemaproductos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vallegrande.edu.pe.sistemaproductos.controller.MainController;
import vallegrande.edu.pe.sistemaproductos.view.MainView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainView view = new MainView();
        new MainController(view);

        Scene scene = new Scene(view, 1050, 620);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Catálogo de Productos");
        primaryStage.setScene(scene);
        primaryStage.show();
        view.animarEntrada();
    }

    public static void main(String[] args) {
        launch(args);
    }
}