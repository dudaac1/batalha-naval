package sistema;

/**
 * @author Eduarda e Julia
 */
public class Elemento {
    private String nome;
    private int tamanho;
    private String urlParcial;
    private String url;
    private String codigo; // para testagem
    private String nomeDisparo;

    
    public Elemento(String nome, int tamanho, String nomeDisparo) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.urlParcial = "resources/" + nome;
        this.url =  this.urlParcial + ".png";
        this.codigo = nome.charAt(0) + String.valueOf(tamanho); // letra inicial + tamanho
        this.nomeDisparo = nomeDisparo;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public int getTamanho() {
        return this.tamanho;
    }
    
    public String getUrlParcial() {
        return this.urlParcial;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getCodigo() {
        return this.codigo;
    }
    
    public String getNomeDisparo() {
        return this.nomeDisparo;
    }
}
