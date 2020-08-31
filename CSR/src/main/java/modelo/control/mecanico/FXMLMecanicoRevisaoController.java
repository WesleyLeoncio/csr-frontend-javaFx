/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.control.mecanico;

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
import modelo.model.Revisao;
import modelo.model.Veiculo;
import modelo.service.ClienteService;
import modelo.service.RevisaoService;
import modelo.service.VeiculoService;

/**
 * FXML Controller class
 *
 * @author Wesley
 */
public class FXMLMecanicoRevisaoController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelas;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField txt_horario;
    @FXML
    private TableView<Revisao> tableView;
    @FXML
    private TableColumn<Revisao, String> columnHorario;
    @FXML
    private TableColumn<Revisao, String> columnData;
    private ObservableList<Cliente> observableCliente;
    private ClienteService clienteService = new ClienteService();
    private VeiculoService veiculoService = new VeiculoService();
    private RevisaoService revisaoService = new RevisaoService();
    private ObservableList<Veiculo> observableVeiculo;
    private ObservableList<Revisao> observableRevisao;
    @FXML
    private ComboBox<Veiculo> comboBoxVeiculo;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    private Funcionario funcionario;

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        carregarTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxCliente();
         tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarRevisao(newValue));
        Platform.runLater(() -> comboBoxCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarVeiculo(newValue)));
    }

    public void carregarComboBoxCliente() {
        observableCliente = FXCollections.observableArrayList(clienteService.findClienteComVeiculo());
        Platform.runLater(() -> comboBoxCliente.setItems(observableCliente));
    }

    public void carregarComboBoxVeiculo(int id) {
        observableVeiculo = FXCollections.observableArrayList(veiculoService.findVeiculoCliente(id));
        Platform.runLater(() -> comboBoxVeiculo.setItems(observableVeiculo));
    }

    public void selecionarVeiculo(Cliente cliente) {
        if (cliente != null) {
            carregarComboBoxVeiculo(cliente.getId());
        }
    }

    @FXML
    public void cadastrarRevisao() {
        if (verificarCampos()) {
            //dataPicker.setValue(dataPicker.getValue().withDayOfMonth(dataMaisUm()));
            Revisao revisao = new Revisao();
            revisao.setId(null);
            revisao.setStatus(false);
            revisao.setCliente(comboBoxCliente.getSelectionModel().getSelectedItem());
            revisao.setVeiculo(comboBoxVeiculo.getSelectionModel().getSelectedItem());
            revisao.setFuncionario(funcionario);
            revisao.setHoraRevisao(txt_horario.getText());
            revisao.setDataRevisao(Date.valueOf(dataPicker.getValue()));
            String resultado = revisaoService.insert(revisao);
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        }
    }

    @FXML
    public void alterarRevisao() {
        Revisao revisao = tableView.getSelectionModel().getSelectedItem();
        if (revisao != null) {
            if (verificarCampos()) {
                //dataPicker.setValue(dataPicker.getValue().withDayOfMonth(dataMaisUm()));
                revisao.setCliente(comboBoxCliente.getSelectionModel().getSelectedItem());
                revisao.setVeiculo(comboBoxVeiculo.getSelectionModel().getSelectedItem());
                revisao.setFuncionario(funcionario);
                revisao.setHoraRevisao(txt_horario.getText());
                revisao.setDataRevisao(Date.valueOf(dataPicker.getValue()));
                String resultado = revisaoService.update(revisao);
                exibirMensagemErro(resultado);
                carregarTableView();
                limparCampos();
            }
        }

    }
    
     @FXML
    public void deletarRevisao(){
        Revisao revisao = tableView.getSelectionModel().getSelectedItem();
        if(revisao != null){
            String resultado = revisaoService.delete(revisao.getId());
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        }else{
           exibirMensagemErro("Selecione uma revisão!"); 
        }
    }
    
     public void selecionarRevisao(Revisao revisao) {
        if (revisao!= null) {
            Calendar c = Calendar.getInstance();
            c.setTime(revisao.getDataRevisao());
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1); 
            comboBoxVeiculo.getSelectionModel().select(revisao.getVeiculo());
            comboBoxCliente.getSelectionModel().select(revisao.getCliente());
            txt_horario.setText(revisao.getHoraRevisao());
            dataPicker.setValue(c.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }
    
    public void carregarTableView() {
        columnHorario.setCellValueFactory(new PropertyValueFactory<>("horaRevisao"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        observableRevisao = FXCollections.observableArrayList(revisaoService.findRevisaoPendente(funcionario.getId()));
        Platform.runLater(() -> tableView.setItems(observableRevisao));
    }

    public boolean verificarCampos() {
        String msg = "";

        if (txt_horario == null || txt_horario.getText().length() == 0) {
            msg += "Preencha o campo horario!\n";
        }
        if (dataPicker.getValue() == null) {
            msg += "Selecione uma data!\n";
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

    public void limparCampos() {
        comboBoxVeiculo.getSelectionModel().select(null);
        comboBoxCliente.getSelectionModel().select(null);
        txt_horario.setText("");
        dataPicker.setValue(null);
    }

}
