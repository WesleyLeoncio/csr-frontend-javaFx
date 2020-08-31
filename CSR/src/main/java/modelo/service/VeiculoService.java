package modelo.service;

import modelo.model.Veiculo;
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

public class VeiculoService {

    private final String URL = "https://csr-backend-spring.herokuapp.com/veiculos/";

    private final Client client = ClientBuilder.newClient();

    public Veiculo find(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Veiculo Veiculo = mapper.readValue(json, Veiculo.class);
            return Veiculo;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Veiculo> findAll() {
        try {
            WebTarget target = client.target(URL);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Veiculo>> mapType = new TypeReference<List<Veiculo>>() {
            };
            List<Veiculo> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Veiculo> findVeiculoDisponivel() {
        try {
            WebTarget target = client.target(URL + "veiculoDisponivel/");
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Veiculo>> mapType = new TypeReference<List<Veiculo>>() {
            };
            List<Veiculo> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Veiculo> findVeiculoModelo(Integer id) {
        try {
            WebTarget target = client.target(URL + "veiculoModelo/" + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Veiculo>> mapType = new TypeReference<List<Veiculo>>() {
            };
            List<Veiculo> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Veiculo> findVeiculoMarca(Integer id) {
        try {
            WebTarget target = client.target(URL + "veiculoMarca/" + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Veiculo>> mapType = new TypeReference<List<Veiculo>>() {
            };
            List<Veiculo> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Veiculo> findVeiculoCliente(Integer id) {
        try {
            WebTarget target = client.target(URL + "veiculoCliente/" + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Veiculo>> mapType = new TypeReference<List<Veiculo>>() {
            };
            List<Veiculo> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String insert(Veiculo Veiculo) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Veiculo);
            Response response = target.request().post(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(Veiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String update(Veiculo Veiculo) {
        try {
            WebTarget target = client.target(URL + Veiculo.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Veiculo);
            Response response = target.request().put(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(Veiculo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Veiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
