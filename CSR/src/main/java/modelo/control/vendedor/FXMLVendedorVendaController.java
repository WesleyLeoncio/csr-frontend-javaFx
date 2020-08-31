package modelo.control.vendedor;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modelo.model.Cliente;
import modelo.model.Venda;
import modelo.service.ClienteService;
import modelo.service.VendaService;

public class FXMLVendedorVendaController implements Initializable {

    private Venda venda;
    @FXML
    private AnchorPane anchorPaneTelas;
    @FXML
    private TextField txt_preco;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    @FXML
    private ComboBox<String> comboBoxtTipo;
    @FXML
    private TableView<Venda> tableView;
    @FXML
    private TableColumn<Venda, String> columnData;
    @FXML
    private TableColumn<Venda, String> columnCliente;
    private VendaService vendaService = new VendaService();
    private ClienteService clienteService = new ClienteService();
    private ObservableList<Venda> observableVenda;
    private ObservableList<Cliente> observableCliente;

    @FXML
    private TextField txt_veiculo;
    @FXML
    private Button bt_salvarVenda;

    public void setVenda(Venda venda) {
        this.venda = venda;
        carregarTableView();
        carregarComboBoxTipo();
        txt_veiculo.setText(venda.getVeiculo().getModelo().getNomeModelo());
        txt_preco.setText(String.valueOf(venda.getVeiculo().getValorVenda()));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> comboBoxtTipo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> carregarClientes(newValue)));
        Platform.runLater(() -> tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItem(newValue)));
    }

    public void carregarComboBoxTipo() {
        ObservableList<String> observableListTipoCliente = FXCollections.observableArrayList("COMUM", "FUNCIONARIO");
        Platform.runLater(() -> comboBoxtTipo.setItems(observableListTipoCliente));
    }

    public void carregarTableView() {
        columnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        observableVenda = FXCollections.observableArrayList(vendaService.findAll());
        Platform.runLater(() -> tableView.setItems(observableVenda));
    }

    public void carregarClientes(String tipo) {
        if (tipo != "" && tipo != null) {
            observableCliente = FXCollections.observableArrayList(clienteService.findTipoCliente(tipo));
            comboBoxCliente.setItems(observableCliente);
            comboBoxCliente.getSelectionModel().select(0);
            Platform.runLater(() -> txt_preco.setText(String.valueOf(verificarTipo(tipo))));
        }
    }

    public void selecionarItem(Venda venda) {
        if (venda != null) {
            txt_veiculo.setText(venda.getVeiculo().getModelo().getNomeModelo());
            comboBoxtTipo.getSelectionModel().select(venda.getCliente().getTipoCliente());
            comboBoxCliente.getSelectionModel().select(venda.getCliente());
            txt_preco.setText(String.valueOf(venda.getValorFinal()));
        } else {
            txt_veiculo.setText("");
            comboBoxtTipo.getSelectionModel().select(null);
            comboBoxCliente.getSelectionModel().select(null);
            txt_preco.setText("");
        }
    }

    @FXML
    public void registrarVenda() {
        if (bloquearBotoes()) {
            Date data = new Date();
            venda.setId(null);
            venda.setTipoCliente(comboBoxtTipo.getSelectionModel().getSelectedItem());
            venda.setCliente(comboBoxCliente.getSelectionModel().getSelectedItem());
            venda.setValorFinal(Double.parseDouble(txt_preco.getText()));
            venda.setDataVenda(data);

            String resultado = vendaService.insert(venda);
            exibirMensagemErro(resultado);
            carregarTableView();
            bt_salvarVenda.setDisable(true);
        }

    }

    @FXML
    public void alterarVenda() {
        Venda venda = tableView.getSelectionModel().getSelectedItem();
        if (venda != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(venda.getDataVenda());
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
            venda.setDataVenda(c.getTime());
            venda.setTipoCliente(comboBoxtTipo.getSelectionModel().getSelectedItem());
            venda.setCliente(comboBoxCliente.getSelectionModel().getSelectedItem());
            venda.setValorFinal(Double.parseDouble(txt_preco.getText()));

            String resultado = vendaService.update(venda);
            exibirMensagemErro(resultado);
            carregarTableView();
        } else {
            exibirMensagemErro("Selecione uma venda!");
        }
    }

    @FXML
    public void deletarVenda() {
        Venda venda = tableView.getSelectionModel().getSelectedItem();
        if (venda != null) {
            String resultado = vendaService.delete(venda.getId());
            exibirMensagemErro(resultado);
            carregarTableView();
        } else {
            exibirMensagemErro("Selecione uma venda!");
        }
    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

    public boolean bloquearBotoes() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            bt_salvarVenda.setDisable(true);
            return false;
        }
        return true;
    }

    // REGRA DE NEGOCIO 
    public Double verificarTipo(String tipo) {
        if (tipo.equals("FUNCIONARIO")) {
            return calcularValorFinal(venda.getVeiculo().getValorVenda());
        }
        return venda.getVeiculo().getValorVenda();
    }

    public Double calcularValorFinal(Double valor) {
        Double p = 0.05;
        valor = valor - (valor * p);
        return valor;
    }

}
