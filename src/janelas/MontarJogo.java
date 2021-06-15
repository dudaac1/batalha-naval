package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.File;

import sistema.Sistema;
import sistema.Elemento;

/**
 * @author Eduarda e Julia
 */
public class MontarJogo extends JFrame implements ActionListener {
    private Sistema sistema;
    private Elemento elemento;
    private String letras = " ABCDEFGHIJ";
    private JLabel elementoSelecionado;
    private int elementoIndex;

    public MontarJogo(Sistema sistema) {
        this.sistema = sistema;
        this.elemento = new Elemento("0", 0, ""); //tem que ter pelo menos 1 caracter pois este está em uso na classe
//        this.sistema.gerarTabuleiro(this.sistema.getUsuario()); // isso tem que sair
        montar();
    }

    public void montar() {
        setTitle("Janela nova");
        setSize(900, 600);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        setVisible(true);
        Container janelaMontar = getContentPane();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new GridLayout(0, 1));
        JLabel titulo = new JLabel("MONTANDO SEU JOGO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.PLAIN, 24));
        JLabel subtitulo = new JLabel("Olá " + this.sistema.getUsuario().getNome() + ", vamos montar o seu jogo. " +
                "Clique no item que deseja colocar no tabuleiro e clique na posição desejada.");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.elementoSelecionado = new JLabel("");
        this.elementoSelecionado.setHorizontalAlignment(SwingConstants.CENTER);
        painelTopo.add(titulo);
        painelTopo.add(subtitulo);
        painelTopo.add(this.elementoSelecionado);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        janelaMontar.add(painelTopo, gbc);

        JPanel painelTabuleiro = new JPanel();
        painelTabuleiro.setLayout(new GridLayout(11, 11));
        
        // inserindo as letras da grade
        for (int i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + this.letras.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelTabuleiro.add(label);
        }
        
        // inserindo os números e os botões da grade
        for (int i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelTabuleiro.add(label);
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(88, 183, 227));
                button.addActionListener(this);
                button.setName(i + "-" + j);
                // se a posição [i][j] do tabuleiro != "-" (ou seja, com navio)
                // então o botão precisa estar desabilitado e cinza,
                String posicao = this.sistema.getUsuario().getPosicaoTabuleiro(i, j);
//                System.out.println(i+":"+j + " = " + posicao);
                if (!(posicao.equals("-"))) {
                    button.setEnabled(false);
                    button.setBackground(Color.LIGHT_GRAY);
                }
                painelTabuleiro.add(button);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        janelaMontar.add(painelTabuleiro, gbc);

        // inserindo elementos (Navios e Avião) na janela
        JPanel painelElementos = new JPanel();
        painelElementos.setLayout(new GridLayout(0, 1, 0, 5));
        int i;
        JButton botao;
        for (i = 0; i < this.sistema.getElementos().length; i++) {
            String nomeElemento = this.sistema.getElementos()[i].getNome();
            String urlElemento = this.sistema.getElementos()[i].getUrl();
            int tamElemento = this.sistema.getElementos()[i].getTamanho();
            File arquivo = new File(urlElemento);
            if (arquivo.exists()) {
                ImageIcon imagem = new ImageIcon(urlElemento);
                int largura = imagem.getIconWidth()/2;
                int altura = imagem.getIconHeight()/2;
                imagem = new ImageIcon(imagem.getImage().getScaledInstance(largura, altura, Image.SCALE_DEFAULT));
                botao = new JButton(" (" + tamElemento + ")", imagem);
            } else { 
                botao = new JButton(nomeElemento + " (" + tamElemento + ")");
                botao.setFont(new Font("Arial", Font.PLAIN, 16));
                botao.setHorizontalAlignment(SwingConstants.CENTER);
            }
                String codElemento = this.sistema.getElementos()[i].getCodigo(); // o codigo do elemento é o nome do botao
                botao.setName(codElemento);
                botao.setPreferredSize(new Dimension(300, 60));
                botao.addActionListener(new ActionListener() { //actionListener separado para os botões dos elementos
                    public void actionPerformed(ActionEvent event) {
                        selecionarElemento(event);
                    }
                });
                // if elemento.noTabuleiro == true, então o botão precisa ficar desabilitado
                if (this.sistema.getElementos()[i].estaNoTabuleiro()) {
                    botao.setEnabled(false);
                    botao.setBackground(Color.LIGHT_GRAY);
                }
                painelElementos.add(botao);
        }
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(50, 20, 0, 0);
        janelaMontar.add(painelElementos, gbc);

        // botões Jogar e Limpar
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(1, 2, 15, 0));
        JButton botaoJogar = new JButton("Jogar");
        botaoJogar.setName("Jogar");
        botaoJogar.addActionListener(this);
        painelBotoes.add(botaoJogar);
        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.setName("Limpar");
        botaoLimpar.addActionListener(this);
        painelBotoes.add(botaoLimpar);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 50, 0);
        janelaMontar.add(painelBotoes, gbc);
    }

    // para quando o usuário clicar em um dos botões de elementos
    public void selecionarElemento(ActionEvent event) {
        Object origem = event.getSource();
        String botaoNome = ((JButton) origem).getName();
        System.out.println(botaoNome);
        this.elemento = this.sistema.getElementoPorCod(botaoNome);
        if (this.elemento != null) {
            this.elementoSelecionado.setText(this.elemento.getNome() + " selecionado.");
            this.elementoIndex = sistema.getElementoIndexPorCod(elemento.getCodigo());
            if (elementoIndex == -1) {
                JOptionPane.showMessageDialog(null, "Algum problema ocorreu. Desculpe o transtorno.");
                System.exit(1);
            }
        }
    }
    
    // para caso o usuário clicar no botão de Jogar, Limpar ou nos do tabuleiro
    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource();
        if (origem instanceof JButton) {
            String botaoNome = ((JButton) origem).getName();
            switch (botaoNome) {
                case "Jogar": // caso o usuário clique em Jogar
                    Jogar();
                    break;
                case "Limpar": // caso o usuário clique em Limpar
                    limparTabuleiroUsuario();
                    break;
                default: // caso o usuário clique em um dos botões do tabuleiro
                    InserirElemento(botaoNome);
                    break;
            }
        } 
    }

    public void Jogar() {
        System.out.println("Esse botão significa que a gente ta indo jogar");
        this.sistema.gerarTabuleiro(this.sistema.getComputador());
        JanelaJogo jogar = new JanelaJogo(this.sistema);
        this.setVisible(false);
        jogar.setVisible(true);
    }
    
    public void limparTabuleiroUsuario() {
        String mensagem = "Você confirma que deseja limpar o tabuleiro?";
        int limpar = JOptionPane.showConfirmDialog(null, mensagem, "Confirmando...", JOptionPane.YES_NO_OPTION);
        if (limpar == JOptionPane.YES_OPTION) {
            this.sistema.limparTabuleiroUsuario();
            MontarJogo recarregar = new MontarJogo(this.sistema); // cria uma nova janela
            recarregar.setVisible(true); // deixa a nova janela visivel
            this.dispose(); // "mata" a janela anteriormente aberta
        }
    }
    
    public void InserirElemento(String botaoNome) {
        if (this.elemento.getCodigo().equals("00")) {
            JOptionPane.showMessageDialog(null, "Selecione um dos elementos antes de clicar no tabuleiro.");
        } else {
            String[] valores = botaoNome.split("-");
            int linha = Integer.parseInt(valores[0]);
            int coluna = Integer.parseInt(valores[1]);
            String mensagem = "Você confirma a inserção do elemento " + elemento.getNome() + " de tamanho " +
                                elemento.getTamanho() + " na posição " + (linha+1) + letras.charAt(coluna+1) + "?";
            int inserir = JOptionPane.showConfirmDialog(null, mensagem, "Confirmação...", JOptionPane.YES_NO_OPTION);
            if (inserir == JOptionPane.YES_OPTION) {
                int tam = elemento.getTamanho();
                String cod = elemento.getCodigo();
                if (this.sistema.getUsuario().inserirNavio(linha, coluna, tam, cod)) { 
                    this.sistema.getElementos()[elementoIndex].colocarNoTabuleiro();
                    MontarJogo recarregar = new MontarJogo(this.sistema); // cria uma nova janela
                    recarregar.setVisible(true); // deixa a nova janela visivel
                    this.dispose(); // "mata" a janela anteriormente aberta
                } else {
                    JOptionPane.showMessageDialog(null, "Navio não foi inserido, pois a posição é muito perto da borda para o tamanho do navio.");
                }
            } 
        }
    }
    
    // essa função só ta aqui pra eu executar a janela direto
    public static void main(String args[]) {
        Sistema sistema = new Sistema();
        sistema.getUsuario().setNome("Fulano");
//        sistema.setNomeUsuario("Fulano");
        MontarJogo janelaMontar = new MontarJogo(sistema);
        janelaMontar.setVisible(true);
    }
}
