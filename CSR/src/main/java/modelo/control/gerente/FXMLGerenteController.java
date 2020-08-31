package modelo.control.gerente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import modelo.control.FXMLLoginController;
import modelo.model.Funcionario;

public class FXMLGerenteController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelas;

    private Funcionario funcionario;
    @FXML
    private AnchorPane anchoPane;

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        System.out.println(funcionario.getNome());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    public void compraVeiculo() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCompraVeiculoController.class.getResource("/fxml/FXMLCompraVeiculo.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLCompraVeiculoController controller = loader.getController();
        controller.setFuncionario(funcionario);
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void gerarRelatorio() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLRelatorioController.class.getResource("/fxml/FXMLRelatorio.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLRelatorioController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void cadastrarMarca() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastarMarcaController.class.getResource("/fxml/FXMLCadastarMarca.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLCadastarMarcaController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void cadastrarModelo() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastrarModeloController.class.getResource("/fxml/FXMLCadastrarModelo.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLCadastrarModeloController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void cadastrarFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastroFuncController.class.getResource("/fxml/FXMLCadastroFunc.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLCadastroFuncController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
   @FXML
    public void sairSistema() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLLoginController.class.getResource("/fxml/FXMLLogin.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLLoginController controller = loader.getController();
        Platform.runLater(() -> anchoPane.getChildren().setAll(page));
    }
    
}
