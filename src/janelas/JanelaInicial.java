package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sistema.Sistema;

/**
 * @author Eduarda e Julia
 */
public class JanelaInicial extends JFrame implements ActionListener {
    private Sistema sistema;
    // globais para a classe pois serão necessários em outros pontos
    private JLabel labPreencha;
    private JTextField fieldNome;
    private JRadioButton radioAleatorio;
    private JRadioButton radioMontar;
    private JButton btnJogar;
    
    public JanelaInicial() {
        this.sistema = new Sistema();
        this.sistema.criarElementos();
        inicializar();
        //run();
    }
    
    
   public void inicializar() {
        // configurações da janela
        setTitle("Bem-Vindo");
        setSize(600, 450);
        setLocationRelativeTo(null); // centraliza no monitor
        setDefaultCloseOperation(EXIT_ON_CLOSE); // matar o processo se apertar o X
        setFont(new Font("Arial", Font.PLAIN, 14)); // config da fonte geral
        setMinimumSize(new Dimension(600, 450)); // janela não pode ser menor que isso
        setVisible(true);
        //this.setBackground(new Color(0, 0, 0)); // valores rgb
        
        Container janela = getContentPane();
//        janela.setLayout(new GridLayout(0, 1));
        
        // configurações dos componentes
        JPanel painel = new JPanel();
        //painel.setLayout(null);
        painel.setLayout(new GridLayout(0, 1)); // setar como 0 = serão adicionados quantos necessáerios
        janela.add(painel);
        
        JLabel labTitulo = new JLabel("BATALHA NAVAL");
        labTitulo.setFont(new Font("Arial Black", Font.BOLD, 36));
        painel.add(labTitulo);
        
        JLabel labBemVindo = new JLabel("Seja bem-vindo!");
        labTitulo.setFont(new Font("Arial", Font.PLAIN, 24));
        painel.add(labBemVindo);
        
        JLabel labInsiraNome = new JLabel("Insira seu nome:");
        painel.add(labInsiraNome);
        
        fieldNome = new JTextField();
        painel.add(fieldNome);
        
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
        
        radioAleatorio = new JRadioButton("Gerar jogo aleatório");
        radioAleatorio.setSelected(true); // por padrão o tipo Jogo Aleatório vai ta selecionado
//        radioAleatorio.addActionListener(this);
        
        radioMontar = new JRadioButton("Montar seu jogo");
//        radioMontar.addActionListener(this);
        
        ButtonGroup radioGroup = new ButtonGroup(); // para que só um radioButton esteja selecionado por vez
        radioGroup.add(radioAleatorio);
        radioGroup.add(radioMontar);
        
        JPanel painelRadio = new JPanel(); // para deixar os radioButtons juntos
        painelRadio.setLayout(new GridLayout(1, 0)); // para ficarem lado a lado
        painelRadio.add(radioAleatorio); // adicionando radioButtons ao painel
        painelRadio.add(radioMontar);
        painel.add(painelRadio);
        
        btnJogar = new JButton("Jogar");
        btnJogar.setBounds(50, 30, 100, 30);
//        btnJogar.setMaximumSize(new Dimension(150, 20));
//        btnJogar.setMinimumSize(new Dimension(150, 20));
//        btnJogar.setPreferredSize(new Dimension(150, 20));
        
        btnJogar.addActionListener(this); // para o evento do botão
        painel.add(btnJogar);
    }
    
   @Override
     public void actionPerformed(ActionEvent event) {  // método do evento do botão
                String nomeJogador = fieldNome.getText();
                System.out.println("Este é o nome: >"+nomeJogador+"<");
                if (nomeJogador.equals("") || nomeJogador.equals(null) || nomeJogador.equals(" ")) {
                    labPreencha.setVisible(true);
                } else {
                    
                    boolean jogoAleatorio = radioAleatorio.isSelected();
                    if (jogoAleatorio) {
                       // função gerarJogoAleatorioJogador()
                       // criando a janela nova e passando sistema como parâmetro
                       // para que as infos sejam passadas adiante
                       JanelaJogo jogar = new JanelaJogo(sistema);
                       jogar.setVisible(true);
                    }
                    else {
                        MontarJogo janela2 = new MontarJogo(this.sistema);
                        janela2.setVisible(true);
                    }
                }
            }
           
//    public static void main(String args[]) {
//        JanelaInicial janelaInicial = new JanelaInicial();
//        janelaInicial.setVisible(true);
//   }
    
   public static void main(String[] args) {
////        SwingUtilities.invokeLater(new JanelaInicial());
        EventQueue.invokeLater(new Runnable() {
//        
            @Override
            public void run() {
                JanelaInicial janela = new JanelaInicial();
               janela.setVisible(true);           
            }

        });
    }
}