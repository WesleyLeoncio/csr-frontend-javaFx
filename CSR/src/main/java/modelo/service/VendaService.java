package modelo.service;

import modelo.model.Venda;
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

public class VendaService {

    private final String URL = "https://csr-backend-spring.herokuapp.com/vendas/";

    private final Client client = ClientBuilder.newClient();

    public Venda find(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Venda Venda = mapper.readValue(json, Venda.class);
            return Venda;
        } catch (IOException ex) {
            Logger.getLogger(VendaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Venda> findAll() {
        try {
            WebTarget target = client.target(URL);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Venda>> mapType = new TypeReference<List<Venda>>() {
            };
            List<Venda> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VendaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Venda> findVendaAnoMes(double ano, double mes) {
        try {
            WebTarget target = client.target(URL + "mesAno/"+ano+"/"+mes);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Venda>> mapType = new TypeReference<List<Venda>>() {
            };
            List<Venda> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public List<?> findVendaLucroAnoMes(double ano, double mes) {
        try {
            WebTarget target = client.target(URL + "lucroAnoMes/"+ano+"/"+mes);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<?>> mapType = new TypeReference<List<?>>() {
            };
            List<?> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     
     public List<?> findVendaVeiculoVendido() {
        try {
            WebTarget target = client.target(URL +"vendaVeiculo");
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<?>> mapType = new TypeReference<List<?>>() {
            };
            List<?> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public List<?> findVendaMelhorFunc() {
        try {
            WebTarget target = client.target(URL +"vendaMelhorFuc");
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<?>> mapType = new TypeReference<List<?>>() {
            };
            List<?> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
     
     public List<Venda> findVendaCliente(int id) {
        try {
            WebTarget target = client.target(URL +"vendaCliente/"+id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Venda>> mapType = new TypeReference<List<Venda>>() {
            };
            List<Venda> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(VeiculoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String insert(Venda venda) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(venda);
            Response response = target.request().post(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(Venda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String update(Venda Venda) {
        try {
            WebTarget target = client.target(URL + Venda.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Venda);
            Response response = target.request().put(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(Venda.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Venda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
