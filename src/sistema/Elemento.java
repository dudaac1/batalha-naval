package sistema;

/**
 * @author Eduarda e Julia
 */
public class Elemento {
    private String nome;
    private int tamanho;
    private String parcialUrl;
    private String url;
    private String codigo; // para testagem
    private boolean noTabuleiro; // se está no tabuleiro
    private String nomeDisparo;

    
    public Elemento(String nome, int tamanho, String nomeDisparo) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.parcialUrl = "resources/" + nome;
        this.url =  this.parcialUrl + ".png";
        this.codigo = nome.charAt(0) + String.valueOf(tamanho); // letra inicial + tamanho
        this.noTabuleiro = false;
        this.nomeDisparo = nomeDisparo;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public int getTamanho() {
        return this.tamanho;
    }
    
    public String getParcialUrl() {
        return this.parcialUrl;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getCodigo() {
        return this.codigo;
    }
    
    public boolean estaNoTabuleiro() {
        return this.noTabuleiro;
    }
    
    public void colocarNoTabuleiro() {
        this.noTabuleiro = true;
    }
    
    public void tirarDoTabuleiro() {
        this.noTabuleiro = false;
    }
    
    public String getNomeDisparo() {
        return this.nomeDisparo;
    }
}
