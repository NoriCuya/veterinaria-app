package vallegrande.edu.pe.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vallegrande.edu.pe.model.Dueno;
import vallegrande.edu.pe.model.DuenoDAO;

public class MainController {

    private final DuenoDAO dao = new DuenoDAO();

    public void cargarTabla(TableView<Dueno> tabla) {
        try {
            tabla.setItems(FXCollections.observableArrayList(dao.listar()));
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo conectar a la base de datos", e.getMessage());
        }
    }

    public void registrar(TextField txtNombre, TextField txtTelefono,
                          TextField txtDireccion, TableView<Dueno> tabla) {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Completa todos los campos.");
            return;
        }

        try {
            Dueno nuevo = new Dueno(0, nombre, telefono, direccion);
            if (dao.insertar(nuevo)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Dueño registrado correctamente.");
                txtNombre.clear();
                txtTelefono.clear();
                txtDireccion.clear();
                cargarTabla(tabla);
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo registrar", e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String encabezado, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Veterinaria");
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}