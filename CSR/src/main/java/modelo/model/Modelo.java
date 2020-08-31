package modelo.model;

import java.io.Serializable;

public class Modelo implements Serializable {


    private Integer id;
    
    private String nomeModelo;
    
    private Marca marca;

    public Modelo(Integer id, String nomeModelo, Marca marca) {
        this.id = id;
        this.nomeModelo = nomeModelo;
        this.marca = marca;
    }

    public Modelo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return this.getNomeModelo();
    }
    
    
}
