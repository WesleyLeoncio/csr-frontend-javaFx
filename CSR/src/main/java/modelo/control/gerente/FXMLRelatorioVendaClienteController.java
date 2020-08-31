
package modelo.control.gerente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.model.Cliente;
import modelo.model.Venda;
import modelo.service.ClienteService;
import modelo.service.VendaService;


public class FXMLRelatorioVendaClienteController implements Initializable {

    private ObservableList<Cliente> observableCliente;
    private ClienteService clienteService = new ClienteService();
    private VendaService vendaService = new VendaService();
    private ObservableList<Venda> observableVenda;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    @FXML
    private TableView<Venda> tableView;
    @FXML
    private TableColumn<Venda, String> columnVeiculo;
    @FXML
    private TableColumn<Venda, String> columnModelo;
    @FXML
    private TableColumn<Venda, String> columnData;
    @FXML
    private TableColumn<Venda, String> columnFuncionario;
    @FXML
    private TableColumn<Venda, String> columnCliente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       carregarComboBoxCliente();
       Platform.runLater(() -> comboBoxCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionar(newValue)));
    }
    public void carregarComboBoxCliente() {
        observableCliente = FXCollections.observableArrayList(clienteService.findClienteComVeiculo());
        Platform.runLater(() -> comboBoxCliente.setItems(observableCliente));
    }

     public void carregarTableView(int id) {
        columnVeiculo.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        columnFuncionario.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
        columnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        observableVenda = FXCollections.observableArrayList(vendaService.findVendaCliente(id));
        Platform.runLater(() -> tableView.setItems(observableVenda));

    }

     
     public void selecionar(Cliente cliente){
         if(cliente != null){
             carregarTableView(cliente.getId());
         }
     }
    
  
    
}
