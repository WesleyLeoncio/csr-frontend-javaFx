package modelo.model;

public class RelatorioLucro {

    private String quantidade;
    private int mes;
    private int ano;
    private double lucro;

    private String mesFormatado;
    private String valorFormatado;
    public RelatorioLucro(String quantidade, int mes, int ano, double lucro) {
        this.quantidade = quantidade;
        this.mes = mes;
        this.ano = ano;
        this.lucro = lucro;
    }

    public RelatorioLucro() {
    }
    
    
    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    public String getValorFormatado() {
        return "R$: "+this.lucro;
    }

    
   

    public String getMesFormatado() {
        switch (mes) {
            case 1:
                mesFormatado = "Janeiro";
                break;
            case 2:
                mesFormatado = "Fevereiro";
                break;
            case 3:
                mesFormatado = "Março";
                break;
            case 4:
                mesFormatado = "Abril";
                break;
            case 5:
                mesFormatado = "Maio";
                break;
            case 6:
                mesFormatado = "Junho";
                break;
            case 7:
                mesFormatado = "Julho";
                break;
            case 8:
                mesFormatado = "Agosto";
                break;
            case 9:
                mesFormatado = "Setembro";
                break;
            case 10:
                mesFormatado = "Outubro";
                break;
            case 11:
                mesFormatado = "Novembro";
                break;
            case 12:
                mesFormatado = "Dezembro";
                break;

        }
        return mesFormatado;
    }

}
