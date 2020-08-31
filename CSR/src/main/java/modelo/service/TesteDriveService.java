package modelo.service;

import modelo.model.TesteDrive;
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

public class TesteDriveService {

    private final String URL = "https://csr-backend-spring.herokuapp.com/testeDrives/";
   

    private final Client client = ClientBuilder.newClient();

    public TesteDrive find(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TesteDrive TesteDrive = mapper.readValue(json, TesteDrive.class);
            return TesteDrive;
        } catch (IOException ex) {
            Logger.getLogger(TesteDriveService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<TesteDrive> findAll() {
        try {
            WebTarget target = client.target(URL);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<TesteDrive>> mapType = new TypeReference<List<TesteDrive>>() {};
            List<TesteDrive> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(TesteDriveService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

        public String insert(TesteDrive TesteDrive) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(TesteDrive);
            Response response = target.request().post(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(TesteDrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String update(TesteDrive TesteDrive) {
        try {
            WebTarget target = client.target(URL+TesteDrive.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(TesteDrive);
            Response response = target.request().put(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(TesteDrive.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TesteDrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }


}
