package modelo.model;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String tipoCliente;

    private Date dataVenda;

    private Double valorFinal;

    private Cliente cliente;

    private Funcionario funcionario;

    private Veiculo veiculo;

    @JsonIgnore
    private String dataFormatada;
    
    @JsonIgnore
    private String modelo;
    
     @JsonIgnore
    private ImageView imageView;

    public Venda() {
    }

    public Venda(Integer id, String tipoCliente, Date dataVenda, Double valorFinal, Cliente cliente, Funcionario funcionario, Veiculo veiculo) {
        this.id = id;
        this.tipoCliente = tipoCliente;
        this.dataVenda = dataVenda;
        this.valorFinal = valorFinal;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.veiculo = veiculo;
    }
    
    

    public String getModelo() {
        return veiculo.getModelo().getNomeModelo();
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public ImageView getImageView() {
        return new ImageView(new Image(veiculo.getImagem()));
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDataFormatada() {
        Calendar c = Calendar.getInstance();
        c.setTime(dataVenda);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        return sdf1.format(c.getTime());
    }

    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public String toString() {
        return "Venda{" + "id=" + id + ", tipoCliente=" + tipoCliente + ", dataVenda=" + dataVenda + ", valorFinal=" + valorFinal + ", cliente=" + cliente + ", funcionario=" + funcionario + ", veiculo=" + veiculo + '}';
    }

}
