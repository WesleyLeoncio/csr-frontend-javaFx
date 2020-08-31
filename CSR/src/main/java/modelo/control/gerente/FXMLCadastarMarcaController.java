package modelo.control.gerente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modelo.model.Marca;
import modelo.service.MarcaService;

public class FXMLCadastarMarcaController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelas;
    private ObservableList<Marca> observableMara;
    private MarcaService marcaService = new MarcaService();
    @FXML
    private TextField txt_marca;
    @FXML
    private TableView<Marca> tableView;
    @FXML
    private TableColumn<Marca, String> columnMarca;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableView();
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItem(newValue));
    }

    public void carregarTableView() {
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("nomeMarca"));
        observableMara = FXCollections.observableArrayList(marcaService.findAll());
        Platform.runLater(() -> tableView.setItems(observableMara));
    }

    @FXML
    public void inserir() {
        Marca marca = new Marca(null, txt_marca.getText());
        String resultado = marcaService.insert(marca);
        exibirMensagemErro(resultado);
        carregarTableView();
        limparCampo();
    }

    @FXML
    public void alterar() {
        Marca marca = tableView.getSelectionModel().getSelectedItem();
        if (marca != null) {
            marca.setNomeMarca(txt_marca.getText());
            String resultado = marcaService.update(marca);
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma Marca na lista!");
            alert.show();
        }
    }

    @FXML
    public void deletar() {
        Marca marca = tableView.getSelectionModel().getSelectedItem();
        if (marca != null) {
            String resultado = marcaService.delete(marca.getId());
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma Marca na lista!");
            alert.show();
        }
    }

    public void selecionarItem(Marca marca) {
        if (marca != null) {
            txt_marca.setText(marca.getNomeMarca());
        } else {
            txt_marca.setText("");
        }
    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

    public void limparCampo() {
        txt_marca.setText("");
    }
}
