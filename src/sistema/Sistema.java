package sistema;

import java.util.Random;

/**
 * @author Eduarda e Júlia
 */
public class Sistema {
    private final int MAX = 4;
    private Jogador usuario;
    private Jogador computador;
    private Elemento[] elementos;

    public Sistema() {
        this.usuario = new Jogador("");
        this.computador = new Jogador("Computador");
        
        this.elementos = new Elemento[MAX];
        this.criarElementosEDisparos(); // isso tem q sair dps dos tstes
    }

    public Jogador getUsuario() {
        return this.usuario;
    }
    
    public Jogador getComputador() {
        return this.computador;
    }

     public void gerarTabuleiro(Jogador jogador) {
        int i, j, linha, coluna, casasLivres, tamanho;
        boolean possoInserir;
        Random gerador = new Random();
        for (i = 0; i < this.elementos.length; i++) {
            possoInserir = false;
            do {
                tamanho = this.elementos[i].getTamanho();
                linha = gerador.nextInt(10);
                coluna = gerador.nextInt(10 - tamanho);
                casasLivres = 0;
                for (j = coluna; j < coluna + tamanho; j++) 
                    if (jogador.getPosicaoTabuleiro(linha, coluna).equals("-")) // N3
                        casasLivres++;
                if (casasLivres == tamanho) 
                    possoInserir = true;
                 System.out.println("Casas livres = " + casasLivres+ " possoInserir = " + possoInserir);// ISSO TEM Q SAIR
            } while (!possoInserir);
            String codigo = this.elementos[i].getCodigo();
            jogador.inserirNavio(linha, coluna, tamanho, codigo);
            
        }
        System.out.println("TABULEIRO FINAL " + jogador.getNome() + ":");// ISSO TEM Q SAIR
        jogador.imprimirTabuleiro();// ISSO TEM Q SAIR
    }
    
    public void criarElementosEDisparos() {
        // os nomes dos elementos devem ser os mesmos das imagens
        this.elementos[0] = new Elemento("Porta-Aviões", 4, "Comum");
        this.elementos[1] = new Elemento("Navio Escolta", 3, "Cascata");
        this.elementos[2] = new Elemento("Submarino", 2, "Comum");
        this.elementos[3] = new Elemento("Caça", 2, "Estrela");
        
        String[] disparos = new String[MAX];
        String[] codigos = new String[MAX];
        for (int i = 0; i < this.elementos.length; i++) {
            disparos[i] = this.elementos[i].getNomeDisparo();
            codigos[i] = this.elementos[i].getCodigo();
        }
        this.usuario.gerarDisparos(disparos, codigos);
        this.computador.gerarDisparos(disparos, codigos);
    }
    
    public Elemento[] getElementos() {
        return this.elementos;
    }
    
    public Elemento getElementoPorCod(String codigo) {
        int i;
        for (i = 0; i < this.elementos.length; i++) 
            if (this.elementos[i].getCodigo().equals(codigo)) 
                return this.elementos[i];
        return null;
    }

    public int getElementoIndexPorCod(String codigo) {
        int i;
        for (i = 0; i < this.elementos.length; i++) 
            if (this.elementos[i].getCodigo().equals(codigo)) 
                return i;
        return -1;
    }

    public void limparTabuleiroUsuario() {
        int i;
        for (i = 0; i < this.elementos.length; i++) 
            this.elementos[i].tirarDoTabuleiro();
        this.usuario.resetTabuleiro();
        this.usuario.resetDisparos();
    }
    
}
