
package modelo.control.mecanico;

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


public class FXMLMecanicoController implements Initializable {

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
    public void agendarRevisao() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLMecanicoRevisaoController.class.getResource("/fxml/FXMLMecanicoRevisao.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLMecanicoRevisaoController controller = loader.getController();
        controller.setFuncionario(funcionario);
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void registarRevisao() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLMecanicoRevisaoRegistrarController.class.getResource("/fxml/FXMLMecanicoRevisaoRegistrar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLMecanicoRevisaoRegistrarController controller = loader.getController();
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
