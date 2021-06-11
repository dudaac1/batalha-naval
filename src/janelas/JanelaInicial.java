package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sistema.Sistema;

/**
 * @author Eduarda e Julia
 */
public class JanelaInicial extends JFrame implements ActionListener {

    private Sistema sistema; // para as infos serem passadas adiante
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
    }

    public void inicializar() {
        // configurações da janela
        setTitle("Bem-Vindo");
        setSize(600, 450);
        setLocationRelativeTo(null); // centraliza no monitor
        setDefaultCloseOperation(EXIT_ON_CLOSE); // matar o processo se apertar o X
        setMinimumSize(new Dimension(600, 450)); // janela não pode ser menor que isso
        setVisible(true);
        Container janela = getContentPane();
        janela.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel labTitulo = new JLabel("BATALHA NAVAL");
        labTitulo.setFont(new Font("Arial Black", Font.ITALIC, 36));
        labTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 2;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        janela.add(labTitulo, gbc);

        JLabel labBemVindo = new JLabel("Seja bem-vindo!");
        labBemVindo.setFont(new Font("Arial", Font.PLAIN, 24));
        labBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 30, 0);
        janela.add(labBemVindo, gbc);

        JLabel labInsiraNome = new JLabel("Insira seu nome:");
        labInsiraNome.setFont(new Font("Arial", Font.PLAIN, 14));
        labInsiraNome.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        janela.add(labInsiraNome, gbc);

        fieldNome = new JTextField();
        fieldNome.setColumns(20);
//        gbc.ipady = 1;
        gbc.gridy = 5;
        janela.add(fieldNome, gbc);

        // só vai ser usado se, quando o usuário apertar JOGAR
        // o campo do nome tiver vazio
        // ai troca a visibilidade para TRUE
        labPreencha = new JLabel(" ");
        labPreencha.setForeground(Color.RED); // cor da fonte
        labPreencha.setFont(new Font("Arial", Font.PLAIN, 12));
//        gbc.ipady = 0;
        gbc.gridy = 6;
        janela.add(labPreencha, gbc);

        JLabel labVcDeseja = new JLabel("Você deseja:");
        labVcDeseja.setFont(new Font("Arial", Font.PLAIN, 18));
        labVcDeseja.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7;
        gbc.insets = new Insets(20, 0, 5, 0);
        janela.add(labVcDeseja, gbc);

        radioAleatorio = new JRadioButton("Gerar jogo aleatório");
        radioAleatorio.setFont(new Font("Arial", Font.PLAIN, 14));
        radioAleatorio.setSelected(true); // por padrão o tipo Jogo Aleatório vai ta selecionado
        radioMontar = new JRadioButton("Montar seu jogo");
        radioMontar.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonGroup radioGroup = new ButtonGroup(); // para que só um radioButton esteja selecionado por vez
        radioGroup.add(radioAleatorio);
        radioGroup.add(radioMontar);

        JPanel painelRadio = new JPanel(); // para deixar os radioButtons juntos
        painelRadio.setLayout(new GridLayout(1, 0)); // para ficarem lado a lado
        painelRadio.add(radioAleatorio); // adicionando radioButtons ao painel
        painelRadio.add(radioMontar);
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 0);
        janela.add(painelRadio, gbc);

        btnJogar = new JButton("Jogar");
        btnJogar.addActionListener(this); // para o evento do botão
        gbc.gridy = 9;
        gbc.insets = new Insets(15, 0, 0, 0);
        janela.add(btnJogar, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent event) {  // método do evento do botão
        String nomeJogador = fieldNome.getText();
        System.out.println("Este é o nome: >" + nomeJogador + "<");
        if (nomeJogador.equals("") || nomeJogador.equals(" ")) {
            labPreencha.setText("Preencha o campo acima.");
        } else {
            this.sistema.getUsuario().setNome(nomeJogador);
//            sistema.setNomeUsuario(nomeJogador);
            boolean jogoAleatorio = radioAleatorio.isSelected();
            if (jogoAleatorio) {
                this.sistema.gerarTabuleiro(this.sistema.getComputador()); // gerando tabuleiro do computador
                this.sistema.gerarTabuleiro(this.sistema.getUsuario()); // gerando tabuleiro do usuario
                JanelaJogo jogar = new JanelaJogo(this.sistema);
                this.setVisible(false); // deixa a janela atual invisível
                jogar.setVisible(true);
            } else {
                MontarJogo montar = new MontarJogo(this.sistema);
                this.setVisible(false); // deixa a janela atual invisível
                montar.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JanelaInicial janela = new JanelaInicial();
                janela.setVisible(true);
            }
        });
    }
}
