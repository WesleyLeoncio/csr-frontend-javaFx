
package modelo.model;

import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Veiculo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String chassi;
   
    private String combustivel;
 
    private Double valorVenda;
    
    private String ano;
    
    private Integer portas;
 
    private String cor;
  
    private String imagem;
    
    private Boolean disponivel;
   
    private Modelo modelo;
    
    @JsonIgnore
    private ImageView imageView;

    public ImageView getImageView() {
        return new ImageView(new Image(imagem));
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    
    public Veiculo() {
    }

    public Veiculo(Integer id, String chassi, String combustivel, Double valorVenda, String ano, Integer portas, String cor, String imagem, Boolean disponivel, Modelo modelo) {
        this.id = id;
        this.chassi = chassi;
        this.combustivel = combustivel;
        this.valorVenda = valorVenda;
        this.ano = ano;
        this.portas = portas;
        this.cor = cor;
        this.imagem = imagem;
        this.disponivel = disponivel;
        this.modelo = modelo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getPortas() {
        return portas;
    }

    public void setPortas(Integer portas) {
        this.portas = portas;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    
    public String getMarca(){
        return modelo.getMarca().getNomeMarca();
    }
    
    @Override
    public String toString() {
        return this.getModelo().getNomeModelo();
    }
    
    
   

}
