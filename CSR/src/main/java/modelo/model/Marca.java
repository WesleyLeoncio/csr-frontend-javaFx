package modelo.model;

import java.io.Serializable;

public class Marca implements Serializable {

    private Integer id;

    private String nomeMarca;

    public Marca(Integer id, String nomeMarca) {
        this.id = id;
        this.nomeMarca = nomeMarca;
    }

    public Marca() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    @Override
    public String toString() {
        return this.getNomeMarca();
    }
    
    
}
