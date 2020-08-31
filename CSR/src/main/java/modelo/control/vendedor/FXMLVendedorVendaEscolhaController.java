package modelo.control.vendedor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import modelo.model.Funcionario;
import modelo.model.Marca;
import modelo.model.Modelo;
import modelo.model.Veiculo;
import modelo.model.Venda;
import modelo.service.MarcaService;
import modelo.service.ModeloService;
import modelo.service.VeiculoService;

public class FXMLVendedorVendaEscolhaController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelas;

    private Funcionario funcionario;
    @FXML
    private TableView<Veiculo> tableView;
    @FXML
    private TableColumn<Veiculo, String> columnVeiculo;
    @FXML
    private TableColumn<Veiculo, String> columnMarca;
    @FXML
    private TableColumn<Veiculo, String> columnModelo;
    @FXML
    private TableColumn<Veiculo, String> columnCor;
    @FXML
    private TableColumn<Veiculo, Double> columnPreco;
    @FXML
    private Pane paneMenu;
    private VeiculoService veiculoService = new VeiculoService();
    private ObservableList<Veiculo> observableVeiculo;
    @FXML
    private ComboBox<Marca> comboBoxFiltroMarca;
    @FXML
    private ComboBox<Modelo> comboBoxFiltroModelo;
    private ObservableList<Marca> observableMarca;
    private ObservableList<Modelo> observableModelo;
    private ModeloService modeloService = new ModeloService();
    private MarcaService marcaService = new MarcaService();

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        carregarTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxMarca();
        comboBoxFiltroMarca.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarModelo(newValue));
    }

    @FXML
    public void enviarVenda() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLVendedorVendaController.class.getResource("/fxml/FXMLVendedorVenda.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLVendedorVendaController controller = loader.getController();
        Veiculo veiculo = tableView.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            Venda venda = new Venda();
            venda.setVeiculo(veiculo);
            venda.setFuncionario(funcionario);
            controller.setVenda(venda);
            Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
        } else {
            exibirMensagemErro("Selecione um Veiculo!");
        }

    }

    public void carregarTableView() {
        columnVeiculo.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));

        observableVeiculo = FXCollections.observableArrayList(veiculoService.findVeiculoDisponivel());
        Platform.runLater(() -> tableView.setItems(observableVeiculo));

    }

    @FXML
    public void menuHabilitado() {
        paneMenu.setDisable(false);
        paneMenu.setVisible(true);
    }

    public void menuDesabilitado() {
        paneMenu.setDisable(true);
        paneMenu.setVisible(false);
    }

    @FXML
    public void pesquisarVeiculo() {
        columnVeiculo.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        Marca marca = comboBoxFiltroMarca.getSelectionModel().getSelectedItem();
        Modelo modelo = comboBoxFiltroModelo.getSelectionModel().getSelectedItem();

        if (marca != null) {
            if (modelo != null) {
                observableVeiculo = FXCollections.observableArrayList(veiculoService.findVeiculoModelo(modelo.getId()));
                Platform.runLater(() -> tableView.setItems(observableVeiculo));
            } else {
                observableVeiculo = FXCollections.observableArrayList(veiculoService.findVeiculoMarca(marca.getId()));
                Platform.runLater(() -> tableView.setItems(observableVeiculo));
            }
        } else {
            exibirMensagemErro("Selecione uma marca para buscar um veiculo!");
        }
        menuDesabilitado();
    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

    public void carregarComboBoxMarca() {
        observableMarca = FXCollections.observableArrayList(marcaService.findAll());
        Platform.runLater(() -> comboBoxFiltroMarca.setItems(observableMarca));
        comboBoxFiltroMarca.getSelectionModel().select(0);
    }

    public void carregarComboBoxModelo(Marca marca) {
        observableModelo = FXCollections.observableArrayList(modeloService.findByMarca(marca.getId()));
        Platform.runLater(() -> comboBoxFiltroModelo.setItems(observableModelo));
    }

    public void selecionarModelo(Marca marca) {
        if (marca != null) {
            carregarComboBoxModelo(marca);
        }
    }

}
