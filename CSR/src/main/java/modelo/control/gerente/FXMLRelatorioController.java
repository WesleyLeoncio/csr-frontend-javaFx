
package modelo.control.gerente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class FXMLRelatorioController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelas;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
       
    @FXML
    public void relatorioVendaCliente() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLRelatorioVendaClienteController.class.getResource("/fxml/FXMLRelatorioVendaCliente.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLRelatorioVendaClienteController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void relatorioVendaMesAno() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLRelatorioVendaMesAnoController.class.getResource("/fxml/FXMLRelatorioVendaMesAno.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLRelatorioVendaMesAnoController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void relatorioVendaLucroMesAno() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLRelatorioVendaLucroController.class.getResource("/fxml/FXMLRelatorioVendaLucro.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLRelatorioVendaLucroController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void relatorioVendaVeiculoMaisVendido() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLRelatorioVendaVeiculoMaisVendidoController.class.getResource("/fxml/FXMLRelatorioVendaVeiculoMaisVendido.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLRelatorioVendaVeiculoMaisVendidoController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }
    
    @FXML
    public void relatorioVendaMelhorFunc() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLRelatorioVendaMelhorFuncController.class.getResource("/fxml/FXMLRelatorioVendaMelhorFunc.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLRelatorioVendaMelhorFuncController controller = loader.getController();
        Platform.runLater(() -> anchorPaneTelas.getChildren().setAll(page));
    }

}
