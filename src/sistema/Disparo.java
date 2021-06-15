package sistema;

/**
 * @author Eduarda e Júlia
 */
public class Disparo {
    private String nome; // para o botão
    private String codElemento; // com qual elemento do tabuleiro o botão ta relacionado
    private boolean disponivel; 
    private String posicaoInicial; // do elemento no tabuleiro (1A, 2B, ou 0-0, 1-1)
    private String posicaoFinal; 
    
    public Disparo(String nome, String codigo) {
        this.nome = nome;
        this.codElemento = codigo;
        this.disponivel = false;
        this.posicaoInicial = ""; // ACHO QUE N PRECISA
        this.posicaoFinal = ""; // ACHO Q N PRECISA
        // talvez utilizar um contador? pra contar quantas partes do elemento
        // ja foram acertadas
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public String getCodElemento() {
        return this.codElemento;
    }
    
    public boolean getDisponivel() {
        return this.disponivel;
    }
    
    public String getPosicaoInicial() {
        return this.posicaoInicial;
    }
    
    public String getPosicaoFinal() {
        return this.posicaoFinal;
    }
    
    public void setPosicoes(String posInicial, String posFinal) {
        this.posicaoInicial = posInicial;
        this.posicaoFinal = posFinal;
    }
    
    public void imprimirDisparo() {
        System.out.print(this.nome + " ");
        System.out.print(this.codElemento+ " ");
        System.out.print(this.disponivel+ " ");
        System.out.print(this.posicaoInicial + " ");
        System.out.print(this.posicaoFinal);
    }
}
