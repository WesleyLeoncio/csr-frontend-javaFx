
package modelo.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class RelatorioVendaVeiculo {
    private String imagem;
    private String modelo;
    private int quantidade;
    
    private ImageView imageView;
    
    public ImageView getImageView() {
        return new ImageView(new Image(imagem));
    }
    
    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Relatorio{" + "imagem=" + imagem + ", modelo=" + modelo + ", quantidade=" + quantidade + '}';
    }
            
         
}
