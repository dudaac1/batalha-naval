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
public class JanelaMontar extends JFrame implements ActionListener {
    private Sistema sistema;
    private Elemento elemento;
    private String letras = " ABCDEFGHIJ";
    private JLabel elementoSelecionado;
    private int elementoIndex;
    private int elementosInseridos = 0;
    private JPanel painelTabuleiro;
    private JPanel painelElementos;

    public JanelaMontar(Sistema sistema) {
        this.sistema = sistema;
        this.elemento = new Elemento("0", 0, ""); //ao menos 1 caracter pois é usado na classe
        montar();
    }

    public void montar() {
        setTitle("Montando seu tabuleiro");
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
        this.elementoSelecionado = new JLabel("Selecione um dos elementos (navios e avião) no painel à direita.");
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

        this.painelTabuleiro = new JPanel();
        int linha = this.sistema.getUsuario().getNumLinhas();
        int coluna = this.sistema.getUsuario().getNumColunas();
        this.painelTabuleiro.setLayout(new GridLayout((linha+1), (coluna+11)));
        
        // inserindo as letras da grade
        for (int i = 0; i < (linha+1); i++) {
            JLabel label = new JLabel("" + this.letras.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.painelTabuleiro.add(label);
        }
        // inserindo os números e os botões da grade
        for (int i = 0; i < linha; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.painelTabuleiro.add(label);
            for (int j = 0; j < coluna; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(88, 183, 227));
                button.addActionListener(this);
                button.setName(i + "-" + j);
                this.painelTabuleiro.add(button);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        janelaMontar.add(this.painelTabuleiro, gbc);

        // inserindo elementos (Navios e Avião) na janela
        this.painelElementos = new JPanel();
        this.painelElementos.setLayout(new GridLayout(0, 1, 0, 5));
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
            this.painelElementos.add(botao);
        }
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(50, 20, 0, 0);
        janelaMontar.add(this.painelElementos, gbc);

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
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource();
        if (origem instanceof JButton) {
            String botaoNome = ((JButton) origem).getName();
            switch (botaoNome) {
                case "Jogar": 
                    jogar();
                    break;
                case "Limpar": 
                    limparTabuleiroUsuario();
                    break;
                default: // qualquer botão do tabuleiro
                    inserirElemento(botaoNome);
                    break;
            }
        } 
    }

    public void jogar() {
        if(elementosInseridos == this.sistema.getMaxElementos()) {
            JanelaJogo jogar = new JanelaJogo(this.sistema);
            this.setVisible(false);
            jogar.setVisible(true);
        } else {
            String mensagem = "Você precisa inserir todos os elementos no tabuleiro.";
            JOptionPane.showMessageDialog(null, mensagem, "Você ainda não pode jogar.", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void limparTabuleiroUsuario() {
        String mensagem = "Você confirma que deseja limpar o tabuleiro?";
        int limpar = JOptionPane.showConfirmDialog(null, mensagem, "Confirmando...", JOptionPane.YES_NO_OPTION);
        if (limpar == JOptionPane.YES_OPTION) {
            this.sistema.limparTabuleiroUsuario();
            elementosInseridos = 0;
            JanelaMontar recarregar = new JanelaMontar(this.sistema); 
            recarregar.setVisible(true); 
            this.dispose(); 
        }
    }
    
    public void inserirElemento(String botaoNome) {
        if (this.elemento.getCodigo().equals("00")) {
            JOptionPane.showMessageDialog(null, "Selecione um dos elementos antes de clicar no tabuleiro.");
        } else {
            String[] valores = botaoNome.split("-");
            int linha = Integer.parseInt(valores[0]);
            int coluna = Integer.parseInt(valores[1]);
            int tam = elemento.getTamanho();    
            String mensagem = "Você confirma a inserção do elemento " + elemento.getNome() + " de tamanho " +
                                tam + " na posição " + (linha+1) + letras.charAt(coluna+1) + "?";
            int inserir = JOptionPane.showConfirmDialog(null, mensagem, "Confirmação...", JOptionPane.YES_NO_OPTION);
            if (inserir == JOptionPane.YES_OPTION) {
                String cod = elemento.getCodigo();
                if (this.sistema.testarInsercao(linha, coluna, tam, this.sistema.getUsuario())) {
                    this.sistema.getUsuario().inserirNavio(linha, coluna, tam, cod);
                    elementosInseridos++;
                    desabilitarElemento(elementoIndex);
                    for (int i = 0; i < tam; i++) {
                        String nomeBotao = linha + "-" + (coluna+i);
                        desabilitarCelula(nomeBotao, i, elementoIndex);
                    }
                    Elemento aux = new Elemento("0", 0, "");
                    this.elemento = aux;
                    this.elementoSelecionado.setText("Selecione um dos elementos (navios e avião) no painel à direita.");
                } else {
                    mensagem = "Muito perto da borda ou já existe um elemento nas posições a serem ocupadas.";
                    JOptionPane.showMessageDialog(null,  mensagem, "Você não pode inserir o elemento aí!", JOptionPane.ERROR_MESSAGE);
                }
            } 
        }
    }
    
    public void desabilitarElemento(int indice) {
        String nomeBotao = this.sistema.getElementos()[indice].getCodigo();
        for (Component comp : this.painelElementos.getComponents()) {
            if (comp instanceof JButton) {
                if (((JButton) comp).getName().equals(nomeBotao)) {
                    ((JButton) comp).setEnabled(false);
                }
            }
        }
    }
    
    public void desabilitarCelula(String nomeBotao, int i, int elemIndice) {
        for (Component comp : this.painelTabuleiro.getComponents()) {
            if (comp instanceof JButton) {
                if (((JButton) comp).getName().equals(nomeBotao)) {
                    ((JButton) comp).setEnabled(false);
                    ((JButton) comp).setBackground(Color.LIGHT_GRAY);
                    int largura = 40;
                    int altura = 30;
                    String url = this.sistema.getElementos()[elemIndice].getUrlParcial();
                    url += (i+1) + ".png";
                    File arquivo = new File(url);
                    if (arquivo.exists()) {
                        ImageIcon imagem = new ImageIcon(url);
                        imagem = new ImageIcon(imagem.getImage().getScaledInstance(largura, altura, Image.SCALE_DEFAULT));
                        ((JButton) comp).setIcon(imagem);
                        ((JButton) comp).setPreferredSize(new Dimension(altura, altura));
                    }
                }
            }
        }
    }
    
}
