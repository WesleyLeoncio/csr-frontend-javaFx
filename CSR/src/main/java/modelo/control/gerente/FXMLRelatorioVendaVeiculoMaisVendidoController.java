
package modelo.control.gerente;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.model.RelatorioVendaVeiculo;
import modelo.service.VendaService;


public class FXMLRelatorioVendaVeiculoMaisVendidoController implements Initializable {

    @FXML
    private TableView<RelatorioVendaVeiculo> tableView;
    @FXML
    private TableColumn<RelatorioVendaVeiculo, String> columnVeiculo;
    @FXML
    private TableColumn<RelatorioVendaVeiculo, String> columnModelo;
    @FXML
    private TableColumn<RelatorioVendaVeiculo, Integer> columnQuantidade;

    private VendaService vendaService = new VendaService();
    private ObservableList<RelatorioVendaVeiculo> observableRelatorio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       carregarRelatorio();
    }    
    
     public void carregarTableView(List<RelatorioVendaVeiculo> relatorio) {
        columnVeiculo.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        observableRelatorio = FXCollections.observableArrayList(relatorio);
        Platform.runLater(() -> tableView.setItems(observableRelatorio));

    }
     public void carregarRelatorio(){
       List<RelatorioVendaVeiculo> listRelatorio = new ArrayList();
       for (Object object : vendaService.findVendaVeiculoVendido()) {
            listRelatorio.add(relatorioFormatado((String) object));
        }
         carregarTableView(listRelatorio);
     }
     
      public RelatorioVendaVeiculo relatorioFormatado(String dados) {
        RelatorioVendaVeiculo relatorio = new RelatorioVendaVeiculo();
        StringTokenizer st = new StringTokenizer(dados);
        relatorio.setImagem(st.nextToken(","));
        relatorio.setModelo(st.nextToken(","));
        relatorio.setQuantidade(Integer.parseInt(st.nextToken(",")));
        return relatorio;
    }
     
}
