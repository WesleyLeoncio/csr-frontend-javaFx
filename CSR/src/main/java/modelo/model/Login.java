
package modelo.model;

import modelo.model.Funcionario;

public class Login {
    private Funcionario funcionario;
    private String mensagem;

    public Login() {
    }

    public Login(Funcionario funcionario, String mensagem) {
        this.funcionario = funcionario;
        this.mensagem = mensagem;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
}
