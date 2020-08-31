
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
import modelo.model.RelatorioMelhoreFunc;
import modelo.service.VendaService;


public class FXMLRelatorioVendaMelhorFuncController implements Initializable {
    private VendaService vendaService = new VendaService();
    private ObservableList<RelatorioMelhoreFunc> observableRelatorio;
    @FXML
    private TableView<RelatorioMelhoreFunc> tableView;
    @FXML
    private TableColumn<RelatorioMelhoreFunc, String> columnNome;
    @FXML
    private TableColumn<RelatorioMelhoreFunc, String> columnProfissao;
    @FXML
    private TableColumn<RelatorioMelhoreFunc, Integer> columnQuantidade;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarRelatorio();
    }    
    
    public void carregarTableView(List<RelatorioMelhoreFunc> relatorio) {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnProfissao.setCellValueFactory(new PropertyValueFactory<>("profissao"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        observableRelatorio = FXCollections.observableArrayList(relatorio);
        Platform.runLater(() -> tableView.setItems(observableRelatorio));

    }
    
    public void carregarRelatorio(){
       List<RelatorioMelhoreFunc> listRelatorio = new ArrayList();
       for (Object object : vendaService.findVendaMelhorFunc()) {
            listRelatorio.add(relatorioFormatado((String) object));
        }
         carregarTableView(listRelatorio);
     }
    
    public RelatorioMelhoreFunc relatorioFormatado(String dados) {
        RelatorioMelhoreFunc relatorio = new RelatorioMelhoreFunc();
        StringTokenizer st = new StringTokenizer(dados);
        relatorio.setNome(st.nextToken(","));
        relatorio.setProfissao(st.nextToken(","));
        relatorio.setQuantidade(Integer.parseInt(st.nextToken(",")));
        return relatorio;
    }
}
