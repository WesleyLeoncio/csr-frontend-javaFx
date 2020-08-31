package modelo.model;


import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.scene.control.DatePicker;
import javax.persistence.*;
import org.codehaus.jackson.annotate.JsonIgnore;



public class TesteDrive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date dataTeste;

    private String horaTeste;

    private Cliente cliente;

    private Funcionario funcionario;

    private Veiculo veiculo;
    
    @JsonIgnore
    private String dataFormatada;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataTeste() {
        return dataTeste;
    }

    public void setDataTeste(Date dataTeste) {
        this.dataTeste = dataTeste;
    }

    public String getHoraTeste() {
        return horaTeste;
    }

    public void setHoraTeste(String horaTeste) {
        this.horaTeste = horaTeste;
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
    
    public String getDataFormatada() {
        Calendar c = Calendar.getInstance();
        c.setTime(dataTeste);
        c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+1);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        return sdf1.format(c.getTime());
    }
    
}
