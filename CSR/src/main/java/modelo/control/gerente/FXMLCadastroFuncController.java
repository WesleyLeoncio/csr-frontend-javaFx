package modelo.control.gerente;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modelo.model.Funcionario;
import modelo.operacao.TextFormat;
import modelo.service.FuncionarioService;

public class FXMLCadastroFuncController implements Initializable {

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
    private TextField txt_senha;
    @FXML
    private TextField txt_numero;
    @FXML
    private TextField txt_salario;
    private ObservableList<Funcionario> observableFuncionario;
    private FuncionarioService funcionarioService = new FuncionarioService();
    @FXML
    private TableView<Funcionario> tableView;
    @FXML
    private TableColumn<Funcionario, String> columnProfissao;
    @FXML
    private TableColumn<Funcionario, String> columnNome;
    @FXML
    private TextField txt_login;
    @FXML
    private ComboBox<String> comboBoxProfissao;
    @FXML
    private Button bt_cadastrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableView();
        carregarComboBoxProfissao();
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItem(newValue));
    }

    public void carregarTableView() {
        columnProfissao.setCellValueFactory(new PropertyValueFactory<>("profissao"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        observableFuncionario = FXCollections.observableArrayList(funcionarioService.findAll());
        Platform.runLater(() -> tableView.setItems(observableFuncionario));
    }

    @FXML
    public void inserir() {
        if (bloquearBotoes()) {
            if (verificar()) {
                Funcionario funcionario = criarFuncionario();
                String resultado = funcionarioService.insert(funcionario);
                exibirMensagemErro(resultado);
                carregarTableView();
                limparCampos();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Por favor, preencha os campos corretamente!");
                alert.show();
            }
        }

    }

    @FXML
    public void alterar() {
        Funcionario funcionario = tableView.getSelectionModel().getSelectedItem();
        if (verificar()) {
            funcionario = alterarFuncionario(funcionario);
            String resultado = funcionarioService.update(funcionario);
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na tabela!");
            alert.show();
        }
    }

    @FXML
    public void deletar() {
        Funcionario funcionario = tableView.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            String resultado = funcionarioService.delete(funcionario.getId());
            exibirMensagemErro(resultado);
            carregarTableView();
            limparCampos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Funcionario na tabela!");
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

    public void selecionarItem(Funcionario funcionario) {
        if (funcionario != null) {
            carregarDados(funcionario);
        } else {
            limparCampos();
        }
    }

    public void carregarDados(Funcionario funcionario) {
        Calendar c = Calendar.getInstance();
        c.setTime(funcionario.getDataNacimento());
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        txt_nome.setText(funcionario.getNome());
        txt_cpf.setText(funcionario.getCpf());
        txt_login.setText(funcionario.getLogin());
        txt_cep.setText(funcionario.getCep());
        txt_cidade.setText(funcionario.getCidade());
        txt_rua.setText(funcionario.getRua());
        txt_telefone.setText(funcionario.getTelefone());
        txt_senha.setText(funcionario.getSenha());
        txt_uf.setText(funcionario.getUf());
        txt_bairro.setText(funcionario.getBairro());
        txt_numero.setText(String.valueOf(funcionario.getNumero()));
        txt_salario.setText(String.valueOf(funcionario.getSalario()));
        comboBoxProfissao.getSelectionModel().select(funcionario.getProfissao());
        data_nacimento.setValue(c.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public Integer dataMaisUm() {
        return data_nacimento.getValue().getDayOfMonth() + 1;
    }

    @FXML
    public void limparCampos() {
        txt_nome.setText("");
        txt_cpf.setText("");
        txt_login.setText("");
        txt_cep.setText("");
        txt_cidade.setText("");
        txt_rua.setText("");
        txt_telefone.setText("");
        txt_senha.setText("");
        txt_uf.setText("");
        txt_bairro.setText("");
        txt_numero.setText("");
        txt_salario.setText("");
        data_nacimento.setValue(null);
        tableView.getSelectionModel().select(null);
        comboBoxProfissao.getSelectionModel().select(null);
    }

    public Funcionario criarFuncionario() {
        Funcionario func = new Funcionario();
        func.setNome(txt_nome.getText());
        func.setCpf(txt_cpf.getText());
        func.setLogin(txt_login.getText());
        func.setCep(txt_cep.getText());
        func.setCidade(txt_cidade.getText());
        func.setRua(txt_rua.getText());
        func.setTelefone(txt_telefone.getText());
        func.setSenha(txt_senha.getText());
        func.setUf(txt_uf.getText());
        func.setBairro(txt_bairro.getText());
        func.setNumero(Integer.parseInt(txt_numero.getText()));
        func.setSalario(Double.parseDouble(txt_salario.getText() + ".00"));
        func.setDataNacimento(converterLocalDate(data_nacimento.getValue()));
        func.setProfissao(comboBoxProfissao.getSelectionModel().getSelectedItem());

        return func;
    }

    public Funcionario alterarFuncionario(Funcionario func) {
        func.setNome(txt_nome.getText());
        func.setCpf(txt_cpf.getText());
        func.setLogin(txt_login.getText());
        func.setCep(txt_cep.getText());
        func.setCidade(txt_cidade.getText());
        func.setRua(txt_rua.getText());
        func.setTelefone(txt_telefone.getText());
        func.setSenha(txt_senha.getText());
        func.setUf(txt_uf.getText());
        func.setBairro(txt_bairro.getText());
        func.setNumero(Integer.parseInt(txt_numero.getText()));
        func.setSalario(Double.parseDouble(txt_salario.getText()));
        func.setDataNacimento(converterLocalDate(data_nacimento.getValue()));
        func.setProfissao(comboBoxProfissao.getSelectionModel().getSelectedItem());

        return func;
    }

    public void carregarComboBoxProfissao() {
        ObservableList<String> comboProfissao = FXCollections.observableArrayList("GERENTE", "VENDEDOR", "MECANICO");
        Platform.runLater(() -> comboBoxProfissao.setItems(comboProfissao));
    }

    public LocalDate converterData(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public Date converterLocalDate(LocalDate localDate) {
        Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return data;
    }

    public boolean bloquearBotoes() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            bt_cadastrar.setDisable(true);
            return false;
        }
        return true;
    }

    public boolean verificar() {
        int erro = 0;
        if ((txt_nome.getText() == null || txt_nome.getText().length() == 0)) {
            erro++;
        }
        if ((txt_cpf.getText() == null || txt_cpf.getText().length() == 0)) {
            erro++;
        }
        if ((txt_login.getText() == null || txt_login.getText().length() == 0)) {
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
        if ((txt_senha.getText() == null || txt_senha.getText().length() == 0)) {
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
        if ((txt_salario.getText() == null || txt_salario.getText().length() == 0)) {
            erro++;
        }
        if (comboBoxProfissao.getSelectionModel().getSelectedItem() == null) {
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
