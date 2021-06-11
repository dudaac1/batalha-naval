package sistema;

import java.util.Random;

/**
 * @author Eduarda e Júlia
 */
public class Sistema {

    private Jogador usuario;
    private Jogador computador;
    public Elemento[] elementos;

    public Sistema() {
        this.usuario = new Jogador("");
        this.computador = new Jogador("Computador");
        this.elementos = new Elemento[4];
        this.criarElementos(); // isso tem q sair dps dos tstes
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
//                System.out.println();
//                System.out.println(this.elementos[i].getNome() + " - " + tamanho);
//                System.out.println("LINHA e COLUNA gerados: " + linha + "-" + coluna);
                casasLivres = 0;
                for (j = coluna; j < coluna + tamanho; j++) 
                    if (!(jogador.getPosicaoTabuleiro(linha, coluna))) 
                        casasLivres++;
                if (casasLivres == tamanho) 
                    possoInserir = true;
                
                System.out.println("Casas livres = " + casasLivres+ " possoInserir = " + possoInserir);
//                jogador.imprimirTabuleiro();
//                System.out.println();
            } while (!possoInserir);
            jogador.inserirNavio(linha, coluna, tamanho);
//        System.out.println("TABULEIRO PÓS INSERÇÃO: ");
//        jogador.imprimirTabuleiro();
        }
        System.out.println("TABULEIRO FINAL: ");
        jogador.imprimirTabuleiro();
    }
    
    public void limparTabuleiroUsuario() {
        int i;
        for (i = 0; i < this.elementos.length; i++) 
            this.elementos[i].tirarDoTabuleiro();
        this.usuario.limparTabuleiroUsuario();
    }

    public void criarElementos() {
        // os nomes dos elementos devem ser os mesmos das imagens
        this.elementos[0] = new Elemento("Porta-Aviões", 4);
        this.elementos[1] = new Elemento("Navio Escolta", 3);
        this.elementos[2] = new Elemento("Submarino", 2);
        this.elementos[3] = new Elemento("Caça", 2);
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
    
}
