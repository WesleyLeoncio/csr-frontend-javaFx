package modelo.model;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.codehaus.jackson.annotate.JsonIgnore;



public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    

    private Integer id;
  
    private Double valorCompra;
   
    private Date dataCompra;
   
    private Funcionario funcionario;
    
    private Veiculo veiculo;
    
    @JsonIgnore
    private String dataFormatada;
    
    public Compra() {
    }

    public Compra(Integer id, Double valorCompra, Date dataCompra, Funcionario funcionario, Veiculo veiculo) {
        this.id = id;
        this.valorCompra = valorCompra;
        this.dataCompra = dataCompra;
        this.funcionario = funcionario;
        this.veiculo = veiculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(Double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
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
    
    public String getDataFormatada() {
        Calendar c = Calendar.getInstance();
        c.setTime(dataCompra);
        c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+1);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        return sdf1.format(c.getTime());
    }

    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }
        
}
