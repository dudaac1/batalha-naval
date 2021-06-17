package sistema;

/**
 * @author Eduarda e Júlia
 */
public class Disparo {
    private String nome; // para o botão
    private String codElemento; // com qual elemento do tabuleiro o botão ta relacionado
    private boolean disponivel; 
    private int partesAtingidas;
    
    public Disparo(String nome, String codigo) {
        this.nome = nome;
        this.codElemento = codigo;
        this.disponivel = false;
        this.partesAtingidas = 0;
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
    
    public void tornarDisponivel() {
        this.disponivel = true;
    }
    
    public void indisponivel() {
        this.disponivel = false;
    }
    
    public int getPartesAtingidas() {
        return this.partesAtingidas;
    }
    
    public void parteFoiAtingida() {
        this.partesAtingidas++;
    }
    
    public void imprimirDisparo() { // ISSO TEM QUE SAIR
        System.out.print(this.nome + " ");
        System.out.print(this.codElemento+ " ");
        System.out.print(this.disponivel+ " ");
    }
}
