package modelo.control.gerente;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import modelo.model.Compra;
import modelo.model.Funcionario;
import modelo.model.Marca;
import modelo.model.Modelo;
import modelo.model.Veiculo;
import modelo.operacao.Arquivo;
import modelo.operacao.TextFormat;
import modelo.service.CompraService;
import modelo.service.MarcaService;
import modelo.service.ModeloService;

public class FXMLCompraVeiculoController implements Initializable {

    private List<Compra> listCompra = new ArrayList();
    private ObservableList<Compra> observableCompra;
    @FXML
    private AnchorPane anchorPaneTelas;
    @FXML
    private TableView<Compra> tableView;
    @FXML
    private TableColumn<Compra, String> columnData;
    @FXML
    private TableColumn<Compra, Veiculo> columnVeiculo;
    @FXML
    private TextField txt_numeroPortas;
    @FXML
    private TextField txt_combustivel;
    @FXML
    private TextField txt_ano;
    @FXML
    private TextField txt_valorVenda;
    @FXML
    private TextField txt_valorCompra;
    @FXML
    private TextField txt_cor;
    @FXML
    private TextField txt_imagem;
    @FXML
    private TextField txt_chassi;
    private ObservableList<Marca> observableMarca;
    private ObservableList<Modelo> observableModelo;
    private ModeloService modeloService = new ModeloService();
    private MarcaService marcaService = new MarcaService();
    private CompraService compraService = new CompraService();
    @FXML
    private ComboBox<Marca> comboBoxMarca;
    @FXML
    private ComboBox<Modelo> comboBoxModelo;
    private Funcionario funcionario;
    @FXML
    private Button bt_cadastrar;

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        carregarTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxMarca();
        comboBoxMarca.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarModelo(newValue));
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItem(newValue));

    }

    public void carregarTableView() {
        columnData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        columnVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));

        observableCompra = FXCollections.observableArrayList(compraService.findAll());
        Platform.runLater(() -> tableView.setItems(observableCompra));
    }

    public void carregarComboBoxMarca() {
        observableMarca = FXCollections.observableArrayList(marcaService.findAll());
        Platform.runLater(() -> comboBoxMarca.setItems(observableMarca));
    }

    public void carregarComboBoxModelo(Marca marca) {
        observableModelo = FXCollections.observableArrayList(modeloService.findByMarca(marca.getId()));
        Platform.runLater(() -> comboBoxModelo.setItems(observableModelo));
    }

    public void selecionarModelo(Marca marca) {
        if (marca != null) {
            carregarComboBoxModelo(marca);
        }
    }

    @FXML
    public void efetuarCompra() {
        if (bloquearBotoes()) {
            if (verificarCampos()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(null);
                veiculo.setChassi(txt_chassi.getText());
                veiculo.setCombustivel(txt_combustivel.getText());
                veiculo.setPortas(Integer.parseInt(txt_numeroPortas.getText()));
                veiculo.setCor(txt_cor.getText());
                veiculo.setImagem(txt_imagem.getText());
                veiculo.setDisponivel(true);
                veiculo.setAno(txt_ano.getText());
                veiculo.setModelo(comboBoxModelo.getSelectionModel().getSelectedItem());

                Date data = new Date();
                Compra compra = new Compra();
                compra.setId(null);
                compra.setDataCompra(data);
                compra.setFuncionario(funcionario);
                compra.setValorCompra(Double.parseDouble(txt_valorCompra.getText() + ".00"));

                veiculo.setValorVenda(Double.valueOf(txt_valorVenda.getText()));
                compra.setVeiculo(veiculo);
                String resultado = compraService.insert(compra);
                exibirMensagemErro(resultado);
                carregarTableView();
                limparCampos();
            }
        }

    }

    @FXML
    public void alterarCompra() {
        Compra compra = tableView.getSelectionModel().getSelectedItem();
        if (compra != null) {
            if (verificarCampos()) {
                Calendar c = Calendar.getInstance();
                c.setTime(compra.getDataCompra());
                c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
                compra.setDataCompra(c.getTime());
                compra.getVeiculo().setChassi(txt_chassi.getText());
                compra.getVeiculo().setCombustivel(txt_combustivel.getText());
                compra.getVeiculo().setPortas(Integer.parseInt(txt_numeroPortas.getText()));
                compra.getVeiculo().setCor(txt_cor.getText());
                compra.getVeiculo().setImagem(txt_imagem.getText());
                compra.getVeiculo().setAno(txt_ano.getText());
                compra.setValorCompra(Double.parseDouble(txt_valorCompra.getText()));
                compra.getVeiculo().setModelo(comboBoxModelo.getSelectionModel().getSelectedItem());
                compra.getVeiculo().setValorVenda(Double.valueOf(txt_valorVenda.getText()));

                String resultado = compraService.update(compra);
                exibirMensagemErro(resultado);
                carregarTableView();
                limparCampos();
            }
        } else {
            exibirMensagemErro("Selecione uma compra!");
        }
    }

    @FXML
    public void removerCompra() {
        Compra compra = tableView.getSelectionModel().getSelectedItem();
        if (compra != null) {
            String resultado = compraService.delete(compra.getId());
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        } else {
            exibirMensagemErro("Selecione uma compra!");
        }
    }

    @FXML
    public void calcularValor() {
        if (txt_valorCompra.getText() == null || txt_valorCompra.getText().length() == 0) {
            exibirMensagemErro("Preencha o valor da compra!");
        } else {
            double valor = Double.parseDouble(txt_valorCompra.getText());
            txt_valorVenda.setText(String.valueOf(calcularValorVenda(valor)));
        }
    }

    public Double calcularValorVenda(double valor) {
        double p = 0.30;
        if (valor > 0) {
            return valor += (valor * p);
        }
        return 0.0;
    }

    public void selecionarItem(Compra compra) {
        if (compra != null) {
            txt_chassi.setText(compra.getVeiculo().getChassi());
            txt_numeroPortas.setText(String.valueOf(compra.getVeiculo().getPortas()));
            txt_combustivel.setText(compra.getVeiculo().getCombustivel());
            txt_ano.setText(compra.getVeiculo().getAno());
            txt_cor.setText(compra.getVeiculo().getCor());
            txt_valorCompra.setText(String.valueOf(compra.getValorCompra()));
            txt_valorVenda.setText(String.valueOf(compra.getVeiculo().getValorVenda()));
            txt_valorCompra.setText(String.valueOf(compra.getValorCompra()));
            txt_imagem.setText(compra.getVeiculo().getImagem());
            comboBoxMarca.getSelectionModel().select(compra.getVeiculo().getModelo().getMarca());
            comboBoxModelo.getSelectionModel().select(compra.getVeiculo().getModelo());
        } else {
            limparCampos();
        }
    }

    @FXML
    public void carregarImagem() {
        String caminhoImagem = "/icons/veiculos/";
        Arquivo arquivo = new Arquivo();
        String imagem = arquivo.abrirArquivo();
        if (imagem != null && imagem != "") {
            caminhoImagem += imagem;
            txt_imagem.setText(caminhoImagem);
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
            bt_cadastrar.setDisable(true);
            return false;
        }
        return true;
    }

    public void limparCampos() {
        txt_chassi.setText("");
        txt_numeroPortas.setText("");
        txt_combustivel.setText("");
        txt_ano.setText("");
        txt_cor.setText("");
        txt_valorCompra.setText("");
        txt_valorVenda.setText("");
        txt_imagem.setText("");
        comboBoxMarca.getSelectionModel().select(null);
        comboBoxModelo.getSelectionModel().select(null);
    }

    public boolean verificarCampos() {
        String msg = "";

        if (txt_chassi.getText() == null || txt_chassi.getText().length() == 0) {
            msg += "Preencha o campo Chassi!\n";
        } else if (txt_chassi.getText().length() != 17) {
            msg += "O Chassi deve ter 17 Caracter!\n";
        }
        if (txt_numeroPortas.getText() == null || txt_numeroPortas.getText().length() == 0) {
            msg += "Preencha o campo Portas!\n";
        }
        if (txt_combustivel.getText() == null || txt_combustivel.getText().length() == 0) {
            msg += "Preencha o campo Combustivel!\n";
        }
        if (txt_ano.getText() == null || txt_ano.getText().length() == 0) {
            msg += "Preencha o campo Ano!\n";
        }
        if (txt_cor.getText() == null || txt_cor.getText().length() == 0) {
            msg += "Preencha o campo Cor!\n";
        }
        if (comboBoxMarca.getSelectionModel().getSelectedItem() == null) {
            msg += "Selecione uma marca!\n";
        }
        if (comboBoxModelo.getSelectionModel().getSelectedItem() == null) {
            msg += "Selecione uma modelo!\n";
        }
        if (txt_valorCompra.getText() == null || txt_valorCompra.getText().length() == 0) {
            msg += "Preencha o campo valor compra!\n";
        }
        if (txt_valorVenda.getText() == null || txt_valorVenda.getText().length() == 0) {
            msg += "Preencha o campo valor venda!\n";
        }
        if (txt_imagem.getText() == null || txt_imagem.getText().length() == 0) {
            msg += "Preencha o campo imagem!\n";
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

}
