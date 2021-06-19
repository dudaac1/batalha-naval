package sistema;

/**
 * @author Eduarda e Julia
 */
public class Jogador {
    private final int LINHAS = 10;
    private final int COLUNAS = 10;
    private String nome;
    private String[][] tabuleiro;
    private Disparo[] disparos;
    private int acertos;
    
    public Jogador(String nome) {
        this.nome = nome;
        this.tabuleiro = new String[LINHAS][COLUNAS];
        this.acertos = 0;
        this.resetTabuleiro();
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getNumLinhas() {
        return this.LINHAS;
    }
    
    public int getNumColunas() {
        return this.COLUNAS;
    }
    
    public int getAcertos() {
        return this.acertos;
    }
    
    public void acertou() {
        this.acertos++;
    }
    
    public void resetAcertos() {
        this.acertos = 0;
        for (int i = 0; i < this.disparos.length; i++) {
            this.disparos[i].resetPartesAtingidas();
        }
    }
    
    public String[][] getTabuleiro() {
        return this.tabuleiro;
    }
    
    public String getPosicaoTabuleiro(int linha, int coluna) {
        return this.tabuleiro[linha][coluna];
    }
    
    public void resetTabuleiro() {
        for (int i = 0; i < LINHAS; i++)
            for (int j = 0; j < COLUNAS; j++)
                tabuleiro[i][j] = "-";
    }
    
    public boolean inserirNavio(int linha, int coluna, int tam, String cod){
        if (coluna + tam > COLUNAS) 
            return false;
        
        int j;
        for (j = coluna; j < coluna + tam; j++) 
            this.tabuleiro[linha][j] = cod;

        int disparoIndex = this.getDisparoIndexPorCod(cod);
        if (disparoIndex != -1) {
            this.disparos[disparoIndex].tornarDisponivel();
        } else {
            System.out.println("Um erro inesperado ocorreu. Desculpe.");
            System.exit(1);
        }
        return true;
    }

    public void gerarDisparos(String[] nomesDisparos, String[] codigos) {
        int i, totalDisparos = nomesDisparos.length;
        this.disparos = new Disparo[totalDisparos];
        for (i = 0; i < totalDisparos; i++) 
            this.disparos[i] = new Disparo(nomesDisparos[i], codigos[i]);
    }
    
    public Disparo[] getDisparos() {
        return this.disparos;
    }
    
    public int getDisparoIndexPorCod(String codigo) {
        int i;
        for (i = 0; i < this.disparos.length; i++) 
            if (this.disparos[i].getCodElemento().equals(codigo)) 
                return i;
        return -1;
    }
    
    public void resetDisparos() {
        for (int i = 0; i < this.disparos.length; i++) 
            this.disparos[i].tornarIndisponivel();
    }
    
}
