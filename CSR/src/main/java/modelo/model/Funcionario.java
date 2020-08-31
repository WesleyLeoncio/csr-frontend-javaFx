package modelo.model;

import java.util.Date;

public class Funcionario extends Pessoa {
    
    private static final long serialVersionUID = 1L;
    
    private String login;
    
    private String senha;
    
    private String profissao;   
    
    private Double salario;
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Funcionario(String login, String senha, String profissao, Double salario, Integer id, String nome, Date dataNacimento, String cpf, String telefone, String cep, String uf, String cidade, String bairro, String rua, Integer numero) {
        super(id, nome, dataNacimento, cpf, telefone, cep, uf, cidade, bairro, rua, numero);
        this.login = login;
        this.senha = senha;
        this.profissao = profissao;
        this.salario = salario;
    }

    public Funcionario() {
        
    }
    
    

   
    
   
    
}
