
import javax.swing.*;
import java.awt.*;
/**
 * @author Eduarda e Julia
 */
public class JanelaInicial extends JFrame {
    private JLabel labPreencha;
    private JRadioButton radioAleatorio;
    private JRadioButton radioMontar;
    private JButton btnJogar;
    
    public JanelaInicial() {
        inicializar();
    }
    
    public void inicializar() {
        // configurações da janela
        this.setTitle("Bem-Vindo");
        this.setSize(600, 450);
        this.setLocationRelativeTo(null); // centraliza no monitor
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // matar o processo se apertar o X
        this.setFont(new Font("Arial", Font.PLAIN, 14)); // config da fonte geral
        this.setMinimumSize(new Dimension(600, 450)); // janela não pode ser menor que isso
        //this.setBackground(new Color(0, 0, 0)); // valores rgb
        
        // configurações dos componentes
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1)); 
        // setar as colunas como 0 significa que serão adicionadas conforme necessário
        painel.setComponentOrientation(ComponentOrientation.UNKNOWN);
        this.add(painel);
        
        JLabel labTitulo = new JLabel("BATALHA NAVAL");
        labTitulo.setFont(new Font("Arial Black", Font.BOLD, 36));
        painel.add(labTitulo);
        
        JLabel labBemVindo = new JLabel("Seja bem-vindo!");
        labTitulo.setFont(new Font("Arial", Font.PLAIN, 24));
        painel.add(labBemVindo);
        
        JLabel labInsiraNome = new JLabel("Insira seu nome:");
        painel.add(labInsiraNome);
        
        JTextField fieldName = new JTextField();
        painel.add(fieldName);
        
        // só vai ser usado se, quando o usuário apertar JOGAR
        // o campo do nome tiver vazio
        // ai troca a visibilidade para TRUE
        labPreencha = new JLabel("Preencha o campo acima.");
        labPreencha.setForeground(Color.RED); // cor da fonte
        labPreencha.setVisible(false);
        painel.add(labPreencha);
        
        JLabel labVcDeseja = new JLabel("Você deseja:");
        labVcDeseja.setFont(new Font("Arial", Font.PLAIN, 18));
        painel.add(labVcDeseja);
        
        JPanel painelRadio = new JPanel(); // para deixar os radio buttons lado a lado
        painelRadio.setLayout(new GridLayout(1, 0));
        radioAleatorio = new JRadioButton("Gerar jogo aleatório");
        radioAleatorio.setSelected(true); // por padrão o tipo Jogo Aleatório vai ta selecionado
        painelRadio.add(radioAleatorio);
        radioMontar = new JRadioButton("Montar seu jogo");
        radioMontar.setSelected(false); 
        painelRadio.add(radioMontar);
        painel.add(painelRadio);
        
        btnJogar = new JButton("Jogar");
        painel.add(btnJogar);
    }
    
    
    public static void main(String args[]) {
        JanelaInicial janelaInicial = new JanelaInicial();
        janelaInicial.setVisible(true);
    }
}
