package modelo.operacao;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Arquivo {
   
    public String abrirArquivo() {
        String nomeDoArquivo = "";
        JFileChooser arquivo = new JFileChooser();
        FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Arquivos PNG,JPG", "png","jpg");
        arquivo.addChoosableFileFilter(filtroTxt);
        arquivo.setAcceptAllFileFilterUsed(false);
        if (arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION) {
            nomeDoArquivo = (arquivo.getSelectedFile().getName());
        }
        return nomeDoArquivo;
    }
   
}