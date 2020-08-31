package modelo.control.gerente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modelo.model.Marca;
import modelo.model.Modelo;
import modelo.service.MarcaService;
import modelo.service.ModeloService;

public class FXMLCadastrarModeloController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelas;
    @FXML
    private TableView<Modelo> tableView;
    private ObservableList<Modelo> observableModelo;
    private ObservableList<Marca> observableMara;
    private ModeloService modeloService = new ModeloService();
    private MarcaService marcaService = new MarcaService();
    @FXML
    private TableColumn<Modelo, String> columnMarca;
    @FXML
    private TableColumn<Modelo, String> columnModelo;
    @FXML
    private TextField txt_modelo;
    @FXML
    private ComboBox<Marca> comboBoxMarca;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableView();
        carregarComboBox();
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItem(newValue));
    }

    @FXML
    public void inserir() {
        Marca marca = comboBoxMarca.getSelectionModel().getSelectedItem();
        Modelo modelo = new Modelo(null, txt_modelo.getText(), marca);
        if (verificar()) {
            String resultado = modeloService.insert(modelo);
            exibirMensagemErro(resultado);
            atualizarDados();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, preencha os campos corretamente!");
            alert.show();

        }
    }

    @FXML
    public void alterar() {
        Modelo modelo = tableView.getSelectionModel().getSelectedItem();
        if (verificar()){
            modelo.setNomeModelo(txt_modelo.getText());
            modelo.setMarca(comboBoxMarca.getSelectionModel().getSelectedItem());
            String resultado = modeloService.update(modelo);
            exibirMensagemErro(resultado);
            atualizarDados();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na tabela!");
            alert.show();
        }
    }

    @FXML
    public void deletar() {
        Modelo modelo = tableView.getSelectionModel().getSelectedItem();
        if (modelo != null) {
            String resultado = modeloService.delete(modelo.getId());
            exibirMensagemErro(resultado);
            atualizarDados();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na tabela!");
            alert.show();
        }
    }

    public void carregarTableView() {
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("nomeModelo"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        observableModelo = FXCollections.observableArrayList(modeloService.findAll());
        Platform.runLater(() -> tableView.setItems(observableModelo));
    }

    public void carregarComboBox() {
        observableMara = FXCollections.observableArrayList(marcaService.findAll());
        Platform.runLater(() -> comboBoxMarca.setItems(observableMara));
    }

    public void selecionarItem(Modelo modelo) {
        if (modelo != null) {
            comboBoxMarca.getSelectionModel().select(modelo.getMarca());
            txt_modelo.setText(modelo.getNomeModelo());
        } else {
            txt_modelo.setText("");
        }
    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

    public void atualizarDados() {
        txt_modelo.setText("");
        carregarComboBox();
        carregarTableView();
        limparCampo();
    }

    public boolean verificar() {
        int erro = 0;
        if (txt_modelo.getText() == null || txt_modelo.getText().length() == 0) {
            erro++;
        }
        if (comboBoxMarca.getSelectionModel().getSelectedItem()== null) {
            erro++;
        }
        if (erro != 0) {
            return false;
        }
        return true;
    }
    
    public void limparCampo() {
       txt_modelo.setText("");
       comboBoxMarca.getSelectionModel().select(null);
    }
}
