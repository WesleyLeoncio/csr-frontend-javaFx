package modelo.control.gerente;

import java.net.URL;
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
import modelo.model.RelatorioLucro;
import modelo.model.Venda;
import modelo.service.VendaService;

public class FXMLRelatorioVendaLucroController implements Initializable {

    @FXML
    private ComboBox<Integer> comboBoxAno;
    @FXML
    private ComboBox<Integer> comboBoxMes;
    @FXML
    private TableView<RelatorioLucro> tableView;
    @FXML
    private TableColumn<RelatorioLucro, Integer> columnQuantidade;
    @FXML
    private TableColumn<RelatorioLucro, String> columnMes;
    @FXML
    private TableColumn<RelatorioLucro, String> columnAno;
    @FXML
    private TableColumn<RelatorioLucro, String> columnLucro;
    private VendaService vendaService = new VendaService();
    private ObservableList<RelatorioLucro> observableVenda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxMes();
        Platform.runLater(() -> comboBoxMes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarMes(newValue)));
        Platform.runLater(() -> comboBoxAno.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarAno(newValue)));
    }

    public void carregarTableView(int ano, int mes) {
        List<?> obj = vendaService.findVendaLucroAnoMes(ano, mes);
        if (obj.get(1) != null) {
            RelatorioLucro rl = new RelatorioLucro();
            rl.setAno(ano);
            rl.setMes(mes);
            rl.setQuantidade(String.valueOf(obj.get(0)));
            rl.setLucro((Double) obj.get(1));
            columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            columnMes.setCellValueFactory(new PropertyValueFactory<>("mesFormatado"));
            columnAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
            columnLucro.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
            observableVenda = FXCollections.observableArrayList(rl);
            Platform.runLater(() -> tableView.setItems(observableVenda));
        }else{
            Platform.runLater(() -> tableView.setItems(null));
        }

    }

    public void carregarComboBoxMes() {
        ObservableList<Integer> observableMes;
        observableMes = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Platform.runLater(() -> comboBoxMes.setItems(observableMes));
    }

    public void carregarComboBoxAno() {
        ObservableList<Integer> observableAno;
        observableAno = FXCollections.observableArrayList(2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032);
        Platform.runLater(() -> comboBoxAno.setItems(observableAno));
    }

    public void selecionarMes(Integer mes) {
        if (mes != null) {
            carregarComboBoxAno();
        }
    }

    public void selecionarAno(Integer ano) {
        if (ano != null) {
            int a = comboBoxAno.getSelectionModel().getSelectedItem();
            int m = comboBoxMes.getSelectionModel().getSelectedItem();
            carregarTableView(a, m);
        }
    }

}
