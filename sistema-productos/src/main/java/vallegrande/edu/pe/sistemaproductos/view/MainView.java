package vallegrande.edu.pe.sistemaproductos.view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vallegrande.edu.pe.sistemaproductos.model.Producto;

public class MainView extends BorderPane {

    private TextField txtId, txtNombre, txtCategoria, txtCantidad, txtPrecio;
    private Button btnRegistrar, btnActualizar, btnEliminar;
    private TableView<Producto> tablaProductos;

    private VBox header, formCard, tableCard;
    private HBox statsBox;
    private Label lblTotal, lblStock, lblValor;

    public MainView() {
        // ---------- Cabecera ----------
        Label lblTitulo = new Label("Catálogo de Productos");
        lblTitulo.getStyleClass().add("titulo");
        Label lblSub = new Label("Registra, actualiza y elimina productos de tu inventario");
        lblSub.getStyleClass().add("subtitulo");
        header = new VBox(4, lblTitulo, lblSub);
        header.getStyleClass().add("header");
        header.setPadding(new Insets(20, 30, 20, 30));
        setTop(header);

        // ---------- Formulario ----------
        txtId = new TextField();
        txtId.setDisable(true);
        txtId.setPromptText("Automático");
        txtNombre = new TextField();
        txtNombre.setPromptText("Ej: Café Orgánico");
        txtCategoria = new TextField();
        txtCategoria.setPromptText("Ej: Café");
        txtCantidad = new TextField();
        txtCantidad.setPromptText("Ej: 50");
        txtPrecio = new TextField();
        txtPrecio.setPromptText("Ej: 25.90");

        btnRegistrar = crearBoton("Registrar", "boton-reg");
        btnActualizar = crearBoton("Actualizar", "boton-act");
        btnEliminar = crearBoton("Eliminar", "boton-eli");
        HBox boxBotones = new HBox(8, btnRegistrar, btnActualizar, btnEliminar);
        boxBotones.setPadding(new Insets(8, 0, 0, 0));

        Label lblForm = new Label("Datos del producto");
        lblForm.getStyleClass().add("card-title");

        formCard = new VBox(12, lblForm,
                campo("ID", txtId), campo("Nombre", txtNombre), campo("Categoría", txtCategoria),
                campo("Cantidad", txtCantidad), campo("Precio (S/)", txtPrecio), boxBotones);
        formCard.getStyleClass().add("card");
        formCard.setPadding(new Insets(20));
        formCard.setPrefWidth(300);
        formCard.setMinWidth(300);
        formCard.setEffect(new DropShadow(18, Color.rgb(0, 0, 0, 0.12)));
        BorderPane.setMargin(formCard, new Insets(20, 10, 10, 20));
        setLeft(formCard);

        // ---------- Tabla ----------
        tablaProductos = new TableView<>();
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        TableColumn<Producto, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPrecio.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("S/ %.2f", item));
            }
        });
        tablaProductos.getColumns().addAll(colId, colNombre, colCategoria, colCantidad, colPrecio);

        // Animación: cada fila aparece con fade
        tablaProductos.setRowFactory(tv -> {
            TableRow<Producto> row = new TableRow<>();
            row.itemProperty().addListener((o, anterior, nuevo) -> {
                if (nuevo != null) {
                    row.setOpacity(0);
                    FadeTransition ft = new FadeTransition(Duration.millis(400), row);
                    ft.setToValue(1);
                    ft.play();
                }
            });
            return row;
        });

        Label lblInv = new Label("Inventario");
        lblInv.getStyleClass().add("card-title");
        tableCard = new VBox(10, lblInv, tablaProductos);
        VBox.setVgrow(tablaProductos, Priority.ALWAYS);
        tableCard.getStyleClass().add("card");
        tableCard.setPadding(new Insets(20));
        tableCard.setEffect(new DropShadow(18, Color.rgb(0, 0, 0, 0.12)));
        BorderPane.setMargin(tableCard, new Insets(20, 20, 10, 10));
        setCenter(tableCard);

        // ---------- Resumen inferior ----------
        lblTotal = new Label("0");
        lblStock = new Label("0");
        lblValor = new Label("S/ 0.00");
        statsBox = new HBox(15, chip(lblTotal, "Productos"), chip(lblStock, "Unidades en stock"),
                chip(lblValor, "Valor del inventario"));
        statsBox.setPadding(new Insets(0, 20, 20, 20));
        statsBox.setAlignment(Pos.CENTER_LEFT);
        setBottom(statsBox);

        tablaProductos.itemsProperty().addListener((o, a, b) -> actualizarStats());
    }

    private void actualizarStats() {
        int total = tablaProductos.getItems().size();
        int stock = 0;
        double valor = 0;
        for (Producto p : tablaProductos.getItems()) {
            stock += p.getCantidad();
            valor += p.getCantidad() * p.getPrecio();
        }
        lblTotal.setText(String.valueOf(total));
        lblStock.setText(String.valueOf(stock));
        lblValor.setText(String.format("S/ %.2f", valor));
    }

    private VBox campo(String etiqueta, TextField tf) {
        Label l = new Label(etiqueta);
        l.getStyleClass().add("campo-label");
        return new VBox(4, l, tf);
    }

    private VBox chip(Label num, String texto) {
        num.getStyleClass().add("stat-num");
        Label t = new Label(texto);
        t.getStyleClass().add("stat-txt");
        VBox box = new VBox(2, num, t);
        box.getStyleClass().add("stat");
        return box;
    }

    private Button crearBoton(String texto, String estilo) {
        Button b = new Button(texto);
        b.getStyleClass().addAll("boton", estilo);
        b.setOnMouseEntered(e -> escalar(b, 1.08));
        b.setOnMouseExited(e -> escalar(b, 1.0));
        return b;
    }

    private void escalar(Node n, double valor) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), n);
        st.setToX(valor);
        st.setToY(valor);
        st.play();
    }

    // Animación de entrada de los bloques
    public void animarEntrada() {
        animar(header, 0, -30, 0);
        animar(formCard, -50, 0, 150);
        animar(tableCard, 50, 0, 300);
        animar(statsBox, 0, 30, 450);
    }

    private void animar(Node n, double desdeX, double desdeY, int retrasoMs) {
        n.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(600), n);
        ft.setToValue(1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(600), n);
        tt.setFromX(desdeX);
        tt.setFromY(desdeY);
        tt.setToX(0);
        tt.setToY(0);
        ParallelTransition pt = new ParallelTransition(ft, tt);
        pt.setDelay(Duration.millis(retrasoMs));
        pt.play();
    }

    public TextField getTxtId() { return txtId; }
    public TextField getTxtNombre() { return txtNombre; }
    public TextField getTxtCategoria() { return txtCategoria; }
    public TextField getTxtCantidad() { return txtCantidad; }
    public TextField getTxtPrecio() { return txtPrecio; }
    public Button getBtnRegistrar() { return btnRegistrar; }
    public Button getBtnActualizar() { return btnActualizar; }
    public Button getBtnEliminar() { return btnEliminar; }
    public TableView<Producto> getTablaProductos() { return tablaProductos; }
}