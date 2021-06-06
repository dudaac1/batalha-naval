package sistema;

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
    
    public void setNomeJogador(String nome) {
        this.usuario.setNome(nome);
    }
    
    public String getNomeJogador() {
        return this.usuario.getNome();
    }
    
    public void criarElementos() {
        // os nomes dos elementos devem ser os mesmos das imagens
        this.elementos[0] = new Elemento("Porta-Aviões", 4);
        this.elementos[1] = new Elemento("Navio Escolta", 3);
        this.elementos[2] = new Elemento("Submarino", 2);
        this.elementos[3] = new Elemento("Caça", 2);
    }
    
    public int getQuantElementos() {
        return this.elementos.length;
    }
    
    
    // da pra ter a função de gerar o tabuleiro do Computador aqui.
    // ai é só dar um sistema.gerarTabuleiroComputador
    
    // e quando o usuário escolher pra ser automático
    // é só dar um sistema.gerarTabuleiroUsuario
}
