package modelo.control;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import modelo.MainApp;
import modelo.control.gerente.FXMLGerenteController;
import modelo.control.mecanico.FXMLMecanicoController;
import modelo.control.vendedor.FXMLVendedorController;
import modelo.model.Funcionario;
import modelo.model.Login;
import modelo.service.FuncionarioService;

public class FXMLLoginController implements Initializable {

    @FXML
    private AnchorPane anchor_pane;
    Thread slide;
    @FXML
    private JFXTextField txt_login;
    @FXML
    private JFXPasswordField txt_senha;
    private FuncionarioService funcionarioService = new FuncionarioService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void botaoLogar() throws IOException {
        principal();
    }

    public void principal() throws IOException {
        if (verificarCampos()) {
            String login = txt_login.getText();
            String senha = txt_senha.getText();
            verificarLogin(login, senha);
        } else {
            exibirMensagemErro("Campo(s) obrigatório(s) de Login não foi(foram) preenchido(s)");
        }

    }

    public void gerenteLogin(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLGerenteController.class.getResource("/fxml/FXMLGerente.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLGerenteController controller = loader.getController();
        MainApp.controleStage.setTitle("CSR GERENTE");
        controller.setFuncionario(funcionario);
        Platform.runLater(() -> anchor_pane.getChildren().setAll(page));
    }

    public void vendedorLogin(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLVendedorController.class.getResource("/fxml/FXMLVendedor.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLVendedorController controller = loader.getController();
        MainApp.controleStage.setTitle("CSR VENDEDOR");
        controller.setFuncionario(funcionario);
        Platform.runLater(() -> anchor_pane.getChildren().setAll(page));
    }

    public void mercanicoLogin(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLMecanicoController.class.getResource("/fxml/FXMLMecanico.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLMecanicoController controller = loader.getController();
        MainApp.controleStage.setTitle("CSR MECÂNICO");
        controller.setFuncionario(funcionario);
        Platform.runLater(() -> anchor_pane.getChildren().setAll(page));
    }

    public void abrirTela(Funcionario funcionario) throws IOException {
        if (funcionario.getProfissao().equals("GERENTE")) {
            gerenteLogin(funcionario);
        } else if (funcionario.getProfissao().equals("VENDEDOR")) {
            vendedorLogin(funcionario);
        } else if (funcionario.getProfissao().equals("MECANICO")) {
            mercanicoLogin(funcionario);
        }
    }

    public void verificarLogin(String login, String senha) throws IOException {
        Funcionario funcionario = new Funcionario();
        List<Login> loginFuncionario = funcionarioService.findVerificarLogin(login, senha);
        if (loginFuncionario.get(0).getFuncionario() != null) {
            abrirTela(loginFuncionario.get(0).getFuncionario());
        } else {
            exibirMensagemErro(loginFuncionario.get(0).getMensagem());
            limparCampos();
        }

    }

    public boolean verificarCampos() {
        int erro = 0;
        if (txt_login.getText() == null || txt_login.getText().length() == 0) {
            erro++;
        }
        if (txt_senha.getText() == null || txt_senha.getText().length() == 0) {
            erro++;
        }
        if (erro != 0) {
            return false;
        }
        return true;
    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

    public void limparCampos() {
        txt_login.setText("");
        txt_senha.setText("");
    }
}
