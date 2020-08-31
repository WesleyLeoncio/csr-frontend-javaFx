package modelo.control.vendedor;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modelo.model.Cliente;
import modelo.operacao.TextFormat;
import modelo.service.ClienteService;

public class FXMLVendedorCadastroClienteController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelas;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_cpf;
    @FXML
    private TextField txt_telefone;
    @FXML
    private TextField txt_uf;
    @FXML
    private TextField txt_cep;
    @FXML
    private TextField txt_cidade;
    @FXML
    private TextField txt_bairro;
    @FXML
    private TextField txt_rua;
    @FXML
    private DatePicker data_nacimento;
    @FXML
    private TextField txt_numero;
    @FXML
    private ComboBox<String> comboBoxTipoCliente;
    private ObservableList<Cliente> observableCliente;
    private ClienteService clienteService = new ClienteService();
    @FXML
    private TableView<Cliente> tableView;
    @FXML
    private TableColumn<Cliente, Integer> columnId;
    @FXML
    private TableColumn<Cliente, String> columnNome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableView();
        carregarComboBoxTipoCliente();
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItem(newValue));
    }

    public void carregarTableView() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        observableCliente = FXCollections.observableArrayList(clienteService.findAll());
        Platform.runLater(() -> tableView.setItems(observableCliente));
    }

    @FXML
    public void inserir() {
        if (verificar()) {
            Cliente cliente = criarCliente();
            String resultado = clienteService.insert(cliente);
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, preencha os campos corretamente!");
            alert.show();
        }
    }

    @FXML
    public void alterar() {
        Cliente cliente = tableView.getSelectionModel().getSelectedItem();
        if (verificar()) {
            cliente = alterarCliente(cliente);
            String resultado = clienteService.update(cliente);
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Cliente na tabela!");
            alert.show();
        }
    }

    @FXML
    public void deletar() {
        Cliente Cliente = tableView.getSelectionModel().getSelectedItem();
        if (Cliente != null) {
            String resultado = clienteService.delete(Cliente.getId());
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Cliente na tabela!");
            alert.show();
        }
    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

    public void selecionarItem(Cliente Cliente) {
        if (Cliente != null) {
            carregarDados(Cliente);
        } else {
            limparCampos();
        }
    }

    public void carregarDados(Cliente cliente) {
        Calendar c = Calendar.getInstance();
        c.setTime(cliente.getDataNacimento());
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        txt_nome.setText(cliente.getNome());
        txt_cpf.setText(cliente.getCpf());
        txt_cep.setText(cliente.getCep());
        txt_cidade.setText(cliente.getCidade());
        txt_rua.setText(cliente.getRua());
        txt_telefone.setText(cliente.getTelefone());
        txt_uf.setText(cliente.getUf());
        txt_bairro.setText(cliente.getBairro());
        txt_numero.setText(String.valueOf(cliente.getNumero()));
        comboBoxTipoCliente.getSelectionModel().select(cliente.getTipoCliente());
        data_nacimento.setValue(c.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @FXML
    public void limparCampos() {
        txt_nome.setText("");
        txt_cpf.setText("");
        txt_cep.setText("");
        txt_cidade.setText("");
        txt_rua.setText("");
        txt_telefone.setText("");
        txt_uf.setText("");
        txt_bairro.setText("");
        txt_numero.setText("");
        data_nacimento.setValue(null);
        tableView.getSelectionModel().select(null);
        comboBoxTipoCliente.getSelectionModel().select(null);
    }

    public Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(txt_nome.getText());
        cliente.setCpf(txt_cpf.getText());
        cliente.setCep(txt_cep.getText());
        cliente.setCidade(txt_cidade.getText());
        cliente.setRua(txt_rua.getText());
        cliente.setTelefone(txt_telefone.getText());
        cliente.setUf(txt_uf.getText());
        cliente.setBairro(txt_bairro.getText());
        cliente.setNumero(Integer.parseInt(txt_numero.getText()));
        cliente.setDataNacimento(converterLocalDate(data_nacimento.getValue()));
        cliente.setTipoCliente(comboBoxTipoCliente.getSelectionModel().getSelectedItem());

        return cliente;
    }

    public Cliente alterarCliente(Cliente cliente) {
        cliente.setNome(txt_nome.getText());
        cliente.setCpf(txt_cpf.getText());
        cliente.setCep(txt_cep.getText());
        cliente.setCidade(txt_cidade.getText());
        cliente.setRua(txt_rua.getText());
        cliente.setTelefone(txt_telefone.getText());
        cliente.setUf(txt_uf.getText());
        cliente.setBairro(txt_bairro.getText());
        cliente.setNumero(Integer.parseInt(txt_numero.getText()));
        cliente.setDataNacimento(converterLocalDate(data_nacimento.getValue()));
        cliente.setTipoCliente(comboBoxTipoCliente.getSelectionModel().getSelectedItem());

        return cliente;
    }

    public LocalDate converterData(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public Date converterLocalDate(LocalDate localDate) {
        Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return data;
    }

    public void carregarComboBoxTipoCliente() {
        ObservableList<String> comboTipoCliente = FXCollections.observableArrayList("COMUM", "FUNCIONARIO");
        Platform.runLater(() -> comboBoxTipoCliente.setItems(comboTipoCliente));
    }

    public boolean verificar() {
        int erro = 0;
        if ((txt_nome.getText() == null || txt_nome.getText().length() == 0)) {
            erro++;
        }
        if ((txt_cpf.getText() == null || txt_cpf.getText().length() == 0)) {
            erro++;
        }
        if ((txt_cep.getText() == null || txt_cep.getText().length() == 0)) {
            erro++;
        }
        if ((txt_cidade.getText() == null || txt_cidade.getText().length() == 0)) {
            erro++;
        }
        if ((txt_rua.getText() == null || txt_rua.getText().length() == 0)) {
            erro++;
        }
        if ((txt_telefone.getText() == null || txt_telefone.getText().length() == 0)) {
            erro++;
        }
        if ((txt_uf.getText() == null || txt_uf.getText().length() == 0)) {
            erro++;
        }
        if ((txt_bairro.getText() == null || txt_bairro.getText().length() == 0)) {
            erro++;
        }
        if ((txt_numero.getText() == null || txt_numero.getText().length() == 0)) {
            erro++;
        }
        if (data_nacimento.getValue() == null) {
            erro++;
        }
        if (comboBoxTipoCliente.getSelectionModel().getSelectedItem() == null) {
            erro++;
        }
        if (erro != 0) {
            return false;
        }
        return true;
    }

    @FXML
    public void formateCpf() {
        TextFormat cpf = new TextFormat();
        cpf.setMask("###.###.###-##");
        cpf.setCaracteresValidos("0123456789");
        cpf.setTf(txt_cpf);
        cpf.formatter();
    }

    @FXML
    public void formateCep() {
        TextFormat cep = new TextFormat();
        cep.setMask("#####-###");
        cep.setCaracteresValidos("0123456789");
        cep.setTf(txt_cep);
        cep.formatter();
    }

}
