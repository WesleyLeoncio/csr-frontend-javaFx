
package modelo.control.vendedor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.control.FXMLLoginController;
import modelo.model.Funcionario;


public class FXMLVendedorController implements Initializable {

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
    public void cadastrarCliente() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLVendedorCadastroClienteController.class.getResource("/fxml/FXMLVendedorCadastroCliente.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLVendedorCadastroClienteController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void agendarTesteDrive() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLVendedorTesteDriveController.class.getResource("/fxml/FXMLVendedorTesteDrive.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLVendedorTesteDriveController controller = loader.getController();
        controller.setFuncionario(funcionario);
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void escolhaVenda() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLVendedorVendaEscolhaController.class.getResource("/fxml/FXMLVendedorVendaEscolha.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLVendedorVendaEscolhaController controller = loader.getController();
        controller.setFuncionario(funcionario);
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
