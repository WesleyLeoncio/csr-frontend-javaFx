
package modelo.control.mecanico;

import java.net.URL;
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
import modelo.service.RevisaoService;


public class FXMLMecanicoRevisaoRegistrarController implements Initializable {

    private ObservableList<Revisao> observableRevisao;
    private RevisaoService revisaoService = new RevisaoService();
    private Funcionario funcionario;

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        carregarTableView(); 
    }
     
    @FXML
    private AnchorPane anchorPaneTelas;
    @FXML
    private TableView<Revisao> tableView;
    
    @FXML
    private TextField txt_horario;
    @FXML
    private ComboBox<Veiculo> comboBoxVeiculo;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TableColumn<Revisao, String> columnHorario;
    @FXML
    private TableColumn<Revisao, String> columnData;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarRevisao(newValue));
    }    
    
    
    @FXML
    public void registrarRevisao(){
        Revisao revisao = tableView.getSelectionModel().getSelectedItem();
        if(revisao != null){
            revisao.setStatus(true);
            String resultado = revisaoService.update(revisao);
            exibirMensagemErro(resultado);
            carregarTableView();
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
    
    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }
    
}
