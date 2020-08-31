
package modelo.model;

import java.util.Date;


public class Cliente extends Pessoa{
    
    private static final long serialVersionUID = 1L;
    
    private String tipoCliente;

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }   

    public Cliente(String tipoCliente, Integer id, String nome, Date dataNacimento, String cpf, String telefone, String cep, String uf, String cidade, String bairro, String rua, Integer numero) {
        super(id, nome, dataNacimento, cpf, telefone, cep, uf, cidade, bairro, rua, numero);
        this.tipoCliente = tipoCliente;
    }

    public Cliente() {
    }
    
    
}
