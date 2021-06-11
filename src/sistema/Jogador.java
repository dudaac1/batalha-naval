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
        this.limparTabuleiroUsuario();
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
    
    public boolean getPosicaoTabuleiro(int linha, int coluna) {
        return this.tabuleiro[linha][coluna];
    }
    
    
    public void limparTabuleiroUsuario() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                tabuleiro[i][j] = false;
    }
    
    public void imprimirTabuleiro() {
        for (int i = 0; i < 10; i++) {
            System.out.printf( (i+1) + ": ");
            for (int j = 0; j < 10; j++)
                System.out.print((tabuleiro[i][j] == true ? "1 " : "0 "));
            System.out.println("");
        }
    }
    
    public boolean inserirNavio(int linha, int coluna, int tamanho){
        if (coluna + tamanho > 10) 
            return false;
        
        for (int j = coluna; j < coluna + tamanho; j++) 
            this.tabuleiro[linha][j] = true;
        return true;
    }

}
