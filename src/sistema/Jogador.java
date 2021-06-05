package sistema;

/**
 * @author Eduarda e Julia
 */
public class Jogador {
    private String nome;
    private boolean[][] tabuleiro;
    
    public Jogador(String nome) {
        this.nome = nome;
        this.tabuleiro = new boolean[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                tabuleiro[i][j] = false;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean[][] getTabuleiro() {
        return this.tabuleiro;
    }
    
    
    // tem que ter uma verificação aqui 
    // pro tamanho do navio não estourar o tamanho do tabuleiro
    // por exemplo, o submarino/caça só podem ser incluidos até
    // a posição I das colunas, pq senão eles estouram o tamanho
    // do tabuleiro
    public void inserirNavio(int linha, int coluna, int tamanho){
        for (int i = linha; i < linha + tamanho; i++ )
            for (int j = coluna; j < coluna + tamanho; j++)
                this.tabuleiro[linha][coluna] = true; 
    }
    
    public void removerNavio(int linha, int coluna, int tamanho){
        for (int i = linha; i < linha + tamanho; i++ )
            for (int j = coluna; j < coluna + tamanho; j++)
                this.tabuleiro[linha][coluna] = false; 
    }
}
