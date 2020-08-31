package modelo.service;

import modelo.model.Revisao;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import modelo.resouces.exceptions.StandardError;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class RevisaoService {

    private final String URL = "https://csr-backend-spring.herokuapp.com/revisao/";
   

    private final Client client = ClientBuilder.newClient();

    public Revisao find(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Revisao Revisao = mapper.readValue(json, Revisao.class);
            return Revisao;
        } catch (IOException ex) {
            Logger.getLogger(RevisaoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public List<Revisao> findRevisaoPendente(Integer id) {
        try {
            WebTarget target = client.target(URL + "revisaoPendente/" + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Revisao>> mapType = new TypeReference<List<Revisao>>() {
            };
            List<Revisao> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(Revisao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Revisao> findAll() {
        try {
            WebTarget target = client.target(URL);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Revisao>> mapType = new TypeReference<List<Revisao>>() {};
            List<Revisao> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(RevisaoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

        public String insert(Revisao Revisao) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Revisao);
            Response response = target.request().post(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(Revisao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String update(Revisao Revisao) {
        try {
            WebTarget target = client.target(URL+Revisao.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Revisao);
            Response response = target.request().put(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(Revisao.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Revisao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }


}
