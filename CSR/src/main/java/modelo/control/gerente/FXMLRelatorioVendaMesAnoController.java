package modelo.control.gerente;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import modelo.model.Venda;
import modelo.service.VendaService;

public class FXMLRelatorioVendaMesAnoController implements Initializable {

   
    private VendaService vendaService = new VendaService();
    private ObservableList<Venda> observableVenda;
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
    @FXML
    private ComboBox<Integer> comboBoxMes;
    @FXML
    private ComboBox<Integer> comboBoxAno;
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      carregarComboBoxMes();
      Platform.runLater(() -> comboBoxMes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarMes(newValue)));
       Platform.runLater(() -> comboBoxAno.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarAno(newValue)));
    }    
    
   
    public void carregarComboBoxMes() {
      ObservableList<Integer> observableMes;
      observableMes = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12); 
      Platform.runLater(() -> comboBoxMes.setItems(observableMes));
    }
    
  
    public void carregarComboBoxAno() {
      ObservableList<Integer> observableAno;
      observableAno = FXCollections.observableArrayList(2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032); 
      Platform.runLater(() -> comboBoxAno.setItems(observableAno));
    }
    
    public void carregarTableView(int ano,int mes) {
        columnVeiculo.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        columnFuncionario.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
        columnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        observableVenda = FXCollections.observableArrayList(vendaService.findVendaAnoMes(ano, mes));
        Platform.runLater(() -> tableView.setItems(observableVenda));

    }
    
    public void selecionarMes(Integer mes){
         if(mes != null){
             carregarComboBoxAno();
         }
     }
    
     public void selecionarAno(Integer ano){
         if(ano != null){
             int a = comboBoxAno.getSelectionModel().getSelectedItem();
             int m = comboBoxMes.getSelectionModel().getSelectedItem();
             carregarTableView(a, m);
         }
     }
}
