package sistema;

/**
 * @author Eduarda e Julia
 */
public class Jogador {
    private final int MAX_LIN = 10;
    private final int MAX_COL = 10;
    private String nome;
    private int acertos;
    private String[][] tabuleiro;
    private Disparo[] disparos;
    
    public Jogador(String nome) {
        this.nome = nome;
        this.tabuleiro = new String[MAX_LIN][MAX_COL];
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
        return this.MAX_LIN;
    }
    
    public int getNumColunas() {
        return this.MAX_COL;
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
        for (int i = 0; i < MAX_LIN; i++)
            for (int j = 0; j < MAX_COL; j++)
                tabuleiro[i][j] = "-";
    }
    
    public boolean inserirNavio(int linha, int coluna, int tam, String cod){
        if (coluna + tam > MAX_LIN) 
            return false;
        
        int j;
        for (j = coluna; j < coluna + tam; j++) 
            this.tabuleiro[linha][j] = cod;

        // editar disparo aqui
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
    
    public void resetDisparos() {
        for (int i = 0; i < this.disparos.length; i++) 
            this.disparos[i].tornarIndisponivel();
    }
    
    public int getDisparoIndexPorCod(String codigo) {
        int i;
        for (i = 0; i < this.disparos.length; i++) 
            if (this.disparos[i].getCodElemento().equals(codigo)) 
                return i;
        return -1;
    }
    
    public void imprimirTabuleiro() { // ISSO TEM Q SAIR
        for (int i = 0; i < MAX_LIN; i++) {
            System.out.printf( (i+1) + ": ");
            for (int j = 0; j < MAX_COL; j++)
                System.out.print((tabuleiro[i][j].equals("-") ? ("-- ") : (tabuleiro[i][j] + " ")));
            System.out.println("");
        }
    }
    
    public void imprimirPartesAtingidas() { // ISSO TEM QUE SAIR
        System.out.println("Porta-Avião: " + this.disparos[0].getPartesAtingidas());
        System.out.println("Escolta: " + this.disparos[1].getPartesAtingidas());
        System.out.println("Submarino: " + this.disparos[2].getPartesAtingidas());
        System.out.println("Caça: " + this.disparos[3].getPartesAtingidas());
        System.out.println();
    }
    
    
}
