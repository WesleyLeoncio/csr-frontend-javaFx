package modelo.control.vendedor;

import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.Calendar;
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
import modelo.model.Funcionario;
import modelo.model.Marca;
import modelo.model.Modelo;
import modelo.model.TesteDrive;
import modelo.model.Veiculo;
import modelo.service.ClienteService;
import modelo.service.MarcaService;
import modelo.service.ModeloService;
import modelo.service.TesteDriveService;
import modelo.service.VeiculoService;

public class FXMLVendedorTesteDriveController implements Initializable {

    private Funcionario funcionario;
    @FXML
    private AnchorPane anchorPaneTelas;
    @FXML
    private TableView<TesteDrive> tableView;
    @FXML
    private TableColumn<TesteDrive, String> columnHorario;
    @FXML
    private TableColumn<TesteDrive, String> columnData;
    private ObservableList<TesteDrive> observableTesteVenda;
    private ObservableList<Marca> observableMarca;
    private ObservableList<Modelo> observableModelo;
    private VeiculoService veiculoService = new VeiculoService();
    private ObservableList<Veiculo> observableVeiculo;
    private ObservableList<Cliente> observableCliente;
    private ClienteService clienteService = new ClienteService();
    private ModeloService modeloService = new ModeloService();
    private MarcaService marcaService = new MarcaService();
    private TesteDriveService testeDriveService = new TesteDriveService();
    @FXML
    private ComboBox<Marca> comboBoxMarca;
    @FXML
    private ComboBox<Modelo> comboBoxModelo;
    @FXML
    private ComboBox<Veiculo> comboBoxVeiculo;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField txt_horario;

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        carregarTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxMarca();
        carregarComboBoxCliente();
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarTeste(newValue));
        comboBoxMarca.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarModelo(newValue));
        comboBoxModelo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarVeiculo(newValue));

    }

    @FXML
    public void cadastrarTesteDrive() {
        if (verificarCampos()) {
            //dataPicker.setValue(dataPicker.getValue().withDayOfMonth(dataMaisUm()));
            TesteDrive testeD = new TesteDrive();
            testeD.setId(null);
            testeD.setVeiculo(comboBoxVeiculo.getSelectionModel().getSelectedItem());
            testeD.setFuncionario(funcionario);
            testeD.setHoraTeste(txt_horario.getText());
            testeD.setDataTeste(Date.valueOf(dataPicker.getValue()));
            testeD.setCliente(comboBoxCliente.getSelectionModel().getSelectedItem());
            String resultado = testeDriveService.insert(testeD);
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        }
    }

    @FXML
    public void alterarTesteDrive() {
        TesteDrive testeD = tableView.getSelectionModel().getSelectedItem();
        if (testeD != null) {
            if (verificarCampos()) {
                //dataPicker.setValue(dataPicker.getValue().withDayOfMonth(dataMaisUm()));
                testeD.setVeiculo(comboBoxVeiculo.getSelectionModel().getSelectedItem());
                testeD.setFuncionario(funcionario);
                testeD.setHoraTeste(txt_horario.getText());
                testeD.setDataTeste(Date.valueOf(dataPicker.getValue()));
                testeD.setCliente(comboBoxCliente.getSelectionModel().getSelectedItem());
                String resultado = testeDriveService.update(testeD);
                exibirMensagemErro(resultado);
                carregarTableView();
                limparCampos();
            }
        }else{
            exibirMensagemErro("Selecione um agendamento!");
        }

    }
    
    @FXML
    public void deletarTesteDrive(){
        TesteDrive testeD = tableView.getSelectionModel().getSelectedItem();
        if(testeD != null){
            String resultado = testeDriveService.delete(testeD.getId());
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        }else{
           exibirMensagemErro("Selecione um agendamento!"); 
        }
    }

    public void selecionarTeste(TesteDrive teste) {
        if (teste != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(teste.getDataTeste());
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
            comboBoxMarca.getSelectionModel().select(teste.getVeiculo().getModelo().getMarca());
            comboBoxModelo.getSelectionModel().select(teste.getVeiculo().getModelo());
            comboBoxVeiculo.getSelectionModel().select(teste.getVeiculo());
            comboBoxCliente.getSelectionModel().select(teste.getCliente());
            txt_horario.setText(teste.getHoraTeste());
            dataPicker.setValue(c.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    public void limparCampos() {
        comboBoxMarca.getSelectionModel().select(null);
        comboBoxModelo.getSelectionModel().select(null);
        comboBoxVeiculo.getSelectionModel().select(null);
        comboBoxCliente.getSelectionModel().select(null);
        txt_horario.setText("");
        dataPicker.setValue(null);
    }

    public void carregarTableView() {
        columnHorario.setCellValueFactory(new PropertyValueFactory<>("horaTeste"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        observableTesteVenda = FXCollections.observableArrayList(testeDriveService.findAll());
        Platform.runLater(() -> tableView.setItems(observableTesteVenda));
    }

    public void carregarComboBoxMarca() {
        observableMarca = FXCollections.observableArrayList(marcaService.findAll());
        Platform.runLater(() -> comboBoxMarca.setItems(observableMarca));
    }

    public void carregarComboBoxModelo(Marca marca) {
        observableModelo = FXCollections.observableArrayList(modeloService.findByMarca(marca.getId()));
        Platform.runLater(() -> comboBoxModelo.setItems(observableModelo));
    }

    public void carregarComboBoxVeiculo(Modelo modelo) {
        observableVeiculo = FXCollections.observableArrayList(veiculoService.findVeiculoModelo(modelo.getId()));
        Platform.runLater(() -> comboBoxVeiculo.setItems(observableVeiculo));
    }

    public void carregarComboBoxCliente() {
        observableCliente = FXCollections.observableArrayList(clienteService.findAll());
        Platform.runLater(() -> comboBoxCliente.setItems(observableCliente));
    }

    public void selecionarModelo(Marca marca) {
        if (marca != null) {
            carregarComboBoxModelo(marca);
        }
    }

    public void selecionarVeiculo(Modelo modelo) {
        if (modelo != null) {
            carregarComboBoxVeiculo(modelo);
        }
    }

    public boolean verificarCampos() {
        String msg = "";

        if (txt_horario == null || txt_horario.getText().length() == 0) {
            msg += "Preencha o campo horario!\n";
        }
        if (dataPicker.getValue() == null) {
            msg += "Selecione uma data!\n";
        }
        if (comboBoxMarca.getSelectionModel().getSelectedItem() == null) {
            msg += "Selecione uma marca!\n";
        }
        if (comboBoxModelo.getSelectionModel().getSelectedItem() == null) {
            msg += "Selecione uma modelo!\n";
        }
        if (comboBoxVeiculo.getSelectionModel().getSelectedItem() == null) {
            msg += "Selecione uma veiculo!\n";
        }

        if (comboBoxCliente.getSelectionModel().getSelectedItem() == null) {
            msg += "Selecione uma Cliente!\n";
        }

        if (msg.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(msg);
            alert.show();
            return false;
        }
    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

    public Integer dataMaisUm() {
        return dataPicker.getValue().getDayOfMonth() + 1;
    }
}
