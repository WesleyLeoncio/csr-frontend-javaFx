package modelo.service;

import modelo.model.Funcionario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import modelo.model.Login;
import modelo.resouces.exceptions.StandardError;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class FuncionarioService {

    private final String URL = "https://csr-backend-spring.herokuapp.com/funcionarios/";

    private final Client client = ClientBuilder.newClient();

    public Funcionario find(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Funcionario funcionario = mapper.readValue(json, Funcionario.class);
            return funcionario;
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Login> findVerificarLogin(String login, String senha) throws IOException {
        List<Login> loginFuncionario = new ArrayList();
        try {
            WebTarget target = client.target(URL + login + "/" + senha);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Funcionario funcionario = mapper.readValue(json, Funcionario.class);
            if (funcionario != null) {
                Login objFunc = new Login(funcionario, "Login Efetuado com Sucesso!");
                loginFuncionario.add(objFunc);
            }
        } catch (Exception e) {

        }
        Login objFunc = new Login(null, "Login ou Senha Incorretos!");
        loginFuncionario.add(objFunc);
        return loginFuncionario;
    }

    public List<Funcionario> findAll() {
        try {
            WebTarget target = client.target(URL);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Funcionario>> mapType = new TypeReference<List<Funcionario>>() {
            };
            List<Funcionario> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String insert(Funcionario funcionario) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(funcionario);
            Response response = target.request().post(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String update(Funcionario funcionario) {
        try {
            WebTarget target = client.target(URL + funcionario.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(funcionario);
            Response response = target.request().put(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String delete(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            ObjectMapper mapper = new ObjectMapper();
            Response response = target.request().delete();
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
