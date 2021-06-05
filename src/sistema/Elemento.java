package sistema;

/**
 * @author Eduarda e Julia
 */
public class Elemento {
    private String nome;
    private int tamanho;
    private String url;
    private String codigo;
    
    public Elemento(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.url = "./recursos/" + nome + ".png";
        this.codigo = nome.charAt(0) + String.valueOf(tamanho); // letra inicial + tamanho
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public int getTamanho() {
        return this.tamanho;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getCodigo() {
        return this.codigo;
    }
}
