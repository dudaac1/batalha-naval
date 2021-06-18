package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import sistema.Sistema; // Importação do Sistema.java
import sistema.Jogador;

/**
 * @author Eduarda e Julia
 */
public class JanelaJogo extends JFrame implements ActionListener {
    private Sistema sistema;
    private JanelaSair janelaSair;
    private JanelaResultado janelaResultado;
	private boolean usuarioVenceu = false;
    private static int contador = 0;
    private JLabel cronometro;
    private Timer tempo;
    private int vetorRanking[] = new int[15];
    private String vetorNomes[] = new String[15];
    private JPanel painelBotoes, painelComputador, painelJogador;
    private boolean clicouTiro1, clicouTiro2, clicouTiro3, clicouDica;
    private static int dicasRestantes = 3;
    private JButton dica;
    private JLabel botaoSelecionado;
    private String urlErrou, urlAcertou;
    private boolean[][] jogadasComputador;
    
    public JanelaJogo(Sistema sistema) {
        this.sistema = sistema; // Passando as informações de uma janela para outra.
        this.janelaSair = new JanelaSair(this, this.sistema);
        this.clicouTiro1 = false;
        this.clicouTiro2 = false;
        this.clicouTiro3 = false;
        this.clicouDica = false;
        this.urlErrou = "resources/jogada-errou.png";
        this.urlAcertou = "resources/jogada-acertou.png";
        this.jogadasComputador = new boolean[this.sistema.getComputador().getNumLinhas()][this.sistema.getComputador().getNumColunas()];
        jogar();
        iniciarCronometro();
    }
    
    public void resetDicas() {
        this.dicasRestantes = 3;
    }
    
    public void resetJogadasComputador() {
        for (int i = 0; i < this.sistema.getComputador().getNumLinhas(); i++)
            for (int j = 0; j < this.sistema.getComputador().getNumColunas(); j++)
                this.jogadasComputador[i][j] = false;
    }
    
    public void jogar() {
        setTitle("Hora do jogo!");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600)); 
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        setVisible(true);
        Container janelaJogo = getContentPane();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
        cronometro = new JLabel("00:00:00");
        System.out.println(this.sistema.getUsuario().getNome());
        
        // BOTÕES
        this.painelBotoes = new JPanel();
        this.painelBotoes.setLayout(new GridBagLayout());
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2); 
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JButton tiro;
        int i;
        for (i = 0; i < this.sistema.getUsuario().getDisparos().length; i++) {
            String texto = "Disparo " + this.sistema.getUsuario().getDisparos()[i].getNome();
            tiro = new JButton(texto);
            tiro.setName("Disparo" + i);
            tiro.addActionListener(this);
//            if (!this.sistema.getUsuario().getDisparos()[i].getDisponivel())
//                tiro.setEnabled(false);
            gbc.gridx = i;
            this.painelBotoes.add(tiro, gbc);
        }
        
        this.dica = new JButton("Dica");
        dica.setName("Dica");
        dica.addActionListener(this);
//        if (dicasRestantes == 0)
//            dica.setEnabled(false);
        gbc.gridx = ++i;
        this.painelBotoes.add(this.dica, gbc);
        
        JButton sair = new JButton("Sair");
        sair.setName("Sair");
        sair.addActionListener(this);
        gbc.gridx = ++i;
        this.painelBotoes.add(sair, gbc);
        
        gbc.gridx = ++i;
        this.painelBotoes.add(cronometro, gbc);
        
        this.botaoSelecionado = new JLabel("Nenhum tipo de disparo selecionado.");
        this.botaoSelecionado.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 0, 0); 
        gbc.gridwidth = i;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.painelBotoes.add(this.botaoSelecionado, gbc);
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.insets = new Insets(20, 0, 15, 0); 
        gbc.anchor = GridBagConstraints.PAGE_START;
        janelaJogo.add(this.painelBotoes, gbc);
        
        // TABULEIROS
        JPanel painelTabuleiros = new JPanel();
        painelTabuleiros.setLayout(new GridBagLayout()); // Dois tabuleiros.
        
        // TABULEIRO DO JOGADOR
        String letras = " ABCDEFGHIJ";
//        JPanel painelJogador = new JPanel(); 
        this.painelJogador = new JPanel(); 
        this.painelJogador.setLayout(new GridLayout(11, 11));
        for(i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + letras.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.painelJogador.add(label);
        }
        for(i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.painelJogador.add(label);
            int contPartes = 0;
            String parteAnterior = "";
            for(int j = 0; j < 10; j++) {
                String posicao = this.sistema.getUsuario().getPosicaoTabuleiro(i, j);
                JButton button = new JButton("");
                if (!(posicao.equals("-"))) {
                    if (parteAnterior == posicao) {
                        contPartes++;
                    } else {
                        contPartes = 1;
                    }
                        parteAnterior = posicao;
                        int elementoIndex = this.sistema.getElementoIndexPorCod(posicao);
                        String urlElemento = this.sistema.getElementos()[elementoIndex].getUrlParcial();
                        urlElemento += contPartes + ".png";
                        File arquivo = new File(urlElemento);
                    if (arquivo.exists()) {
                        ImageIcon imagem = new ImageIcon(urlElemento);
                        int largura = 40;
                        int altura = 30;
                        imagem = new ImageIcon(imagem.getImage().getScaledInstance(largura, altura, Image.SCALE_DEFAULT));
                        button.setIcon(imagem);
                        button.setPreferredSize(new Dimension(altura, altura));
                    }
                    else {
                        button.setBackground(Color.LIGHT_GRAY);
                        button.setText(Integer.toString(contPartes));
                    }
                }
                button.setName(i + "-" + j);
                button.setBackground(new Color(88, 183, 227));
                this.painelJogador.add(button);
            }
        }
        
        // TABULEIRO DO COMPUTADOR
        this.painelComputador = new JPanel();
        this.painelComputador.setLayout(new GridLayout(11, 11));
        for(i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + letras.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.painelComputador.add(label);
        }
        for(i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.painelComputador.add(label);
            
            for(int j = 0; j < 10; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(222, 136, 230));
                button.addActionListener(this);
                button.setName(i + "-" + j);
                button.setPreferredSize(new Dimension(32, 30));
                this.painelComputador.add(button);
            }
        }
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);
        painelTabuleiros.add(this.painelJogador, gbc);
        
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        painelTabuleiros.add(this.painelComputador, gbc);
        
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 20, 0, 30);
        janelaJogo.add(painelTabuleiros, gbc);
        
        JLabel txtJogador = new JLabel("Seu tabuleiro.");
        txtJogador.setHorizontalAlignment(SwingConstants.CENTER);
        txtJogador.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel txtComputador = new JLabel("Tabuleiro adversário.");
        txtComputador.setHorizontalAlignment(SwingConstants.CENTER);
        txtComputador.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel labelPanel  = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 2));
        labelPanel.add(txtJogador);
        labelPanel.add(txtComputador);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 25, 0);
        janelaJogo.add(labelPanel, gbc);
    }
    
    public void iniciarCronometro() { // Iniciando a thread.
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                tempo = new Timer(); 
                tempo.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        contador++; // A cada 1 segundo, o contador é incrementado.
                        int segundo = contador%60; // Segundos = resto do contador.  
                        int minuto = contador/60; // Minuto = divisão do contador por 60.
                        int hora = minuto/60; // Hora = resultado do minuto divido por 60.
                        minuto%=60; 
                        cronometro.setText(String.format("%02d:%02d:%02d", hora, minuto, segundo)); // Formatação do contador.
                    }
                }, 1000, 1000); // 1.000 milisegundos = 1 segundo. -> Contando de 1 em 1 segundo.
            }
        }); 
    }
    
    public void pararCronometro() {
        tempo.cancel();
        if(this.usuarioVenceu) { // Só salva o tempo do jogador no ranking se ele venceu.
            File arquivo = new File("resources/ranking.txt"); // Criando o arquivo.
            try(FileReader fr = new FileReader(arquivo)) {
                BufferedReader br = new BufferedReader(fr);
                String conteudoLinha; // Conteúdo da linha do arquivo.
                for(int i = 0; i < 15; i++) {
                    conteudoLinha = br.readLine();
                    vetorRanking[i] = Integer.parseInt(conteudoLinha);
                }
                for(int j = 0; j < 15; j++) {
                    conteudoLinha = br.readLine();
                    if(conteudoLinha == null)
                        vetorNomes[j] = "";
                    else
                        vetorNomes[j] = conteudoLinha;
                }
            } 
            catch(IOException ex) {
                ex.printStackTrace();
            }

            int tmp1 = 0, tmp2 = 0;
            String tmpNome1, tmpNome2;
            for(int i = 0; i < vetorRanking.length; i++) {
                if(vetorRanking[i] >= contador || vetorRanking[i] == 0){
                    tmp1 = vetorRanking[i];
                    tmpNome1 = vetorNomes[i];
                    vetorRanking[i] = contador;
                    vetorNomes[i] = this.sistema.getUsuario().getNome();
                    for(int j = i+1; j < vetorRanking.length-1; j++) {
                        tmp2 = vetorRanking[j];
                        tmpNome2 = vetorNomes[j];
                        vetorRanking[j] = tmp1;
                        vetorNomes[j] = tmpNome1;
                        tmp1 = tmp2;
                        tmpNome1 = tmpNome2;
                    }
                    break;
                }
            }

            try (FileWriter fw = new FileWriter(arquivo)) {
                BufferedWriter bw = new BufferedWriter(fw);
                for(int i = 0; i < vetorRanking.length; i++) {
                    bw.write(Integer.toString(vetorRanking[i]));
                    bw.newLine();
                }
                for(int i = 0; i < vetorNomes.length; i++) {
                    bw.write(vetorNomes[i]);
                    bw.newLine();
                }
                bw.flush(); // Pega tudo que está no buffer e salva no arquivo.
            } 
            catch(IOException ex) {
                 ex.printStackTrace();
            }
        }
        contador = 0;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource(); // Ajuda a determinar qual componente disparou o evento.
        if(origem instanceof JButton) { // "Se 'origem' é um JButton..."
           String botaoNome = ((JButton) origem).getName();
           switch(botaoNome) {
                case "Disparo0":
                case "Disparo2":
                    this.clicouTiro1 = true;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = false;
                    this.clicouDica = false;
                    this.botaoSelecionado.setText("Disparo Comum selecionado.");
                    break;
                case "Disparo1":
                    this.clicouTiro1 = false;
                    this.clicouTiro2 = true;
                    this.clicouTiro3 = false;
                    this.clicouDica = false;
                    this.botaoSelecionado.setText("Disparo Cascata selecionado.");
                    break;
                case "Disparo3":
                    this.clicouTiro1 = false;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = true;
                    this.clicouDica = false;
                    this.botaoSelecionado.setText("Disparo Estrela selecionado.");
                    break;
                case "Dica":
                    this.clicouDica = true;
                    this.clicouTiro1 = false;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = false;
                    this.botaoSelecionado.setText("Dica selecionado. Você tem " + this.dicasRestantes + " dicas restantes.");
                    break;
                case "Sair":
                    pararCronometro();
                    this.janelaSair.setVisible(true);
                    break;
                
                default: // Qualquer botão do tabuleiro.
                    int linha, coluna;
                    linha = Integer.parseInt(botaoNome.substring(0, 1)); // "Fatiar" a string para pegar apenas a linha.
                    coluna = Integer.parseInt(botaoNome.substring(2, 3)); // "Fatiar" a string para pegar apenas a coluna.
                    
                    if(clicouDica){
                        verificarDica(linha, coluna);
                    } else {
                        int partesTotais = this.sistema.getPartesTotais();
                        if (this.sistema.getUsuario().getAcertos() <= partesTotais && this.sistema.getComputador().getAcertos() <= partesTotais ) {
                            if (this.clicouTiro1 || this.clicouTiro2 || this.clicouTiro3 ){
                                this.verificarTiro(linha, coluna, origem, this.sistema.getUsuario(), this.sistema.getComputador());

                                if(this.clicouTiro1){ // Comum
                                    // nada
                                } else if(this.clicouTiro2){ // Cascata
                                    // pegando botão à direita do clicado
                                    if (coluna+1 < this.sistema.getUsuario().getNumColunas()){ 
                                        String nomeBotao = linha + "-" + (coluna+1);
                                        for(Component comp : this.painelComputador.getComponents()) {
                                            if (comp instanceof JButton) {
                                                if (((JButton) comp).getName().equals(nomeBotao)) {
                                                    this.verificarTiro(linha, coluna+1, comp, this.sistema.getUsuario(), this.sistema.getComputador());
                                                }
                                            }
                                        }
                                    }
                                } else if(this.clicouTiro3){ // Estrela
                                    int[][] posicoes = new int[4][2];
                                    posicoes[0][0] = linha-1; posicoes[0][1] = coluna; // à direita
                                    posicoes[1][0] = linha+1; posicoes[1][1] = coluna; // à esquerda
                                    posicoes[2][0] = linha;   posicoes[2][1] = coluna-1; // acima
                                    posicoes[3][0] = linha;   posicoes[3][1] = coluna+1; // abaixo

                                    for (int i = 0; i < posicoes.length; i++) {
                                        int lin = posicoes[i][0]; int col = posicoes[i][1];
                                        String nomeBotao = lin + "-" + col;
                                        for (Component comp : this.painelComputador.getComponents()) {
                                            if (comp instanceof JButton) {
                                                if (((JButton) comp).getName().equals(nomeBotao)) {
                                                    this.verificarTiro(lin, col, comp, this.sistema.getUsuario(), this.sistema.getComputador());
                                                }
                                            }
                                        }
                                    }
                                }
                                this.jogadaComputador();
                            } else {
                                String mensagem = "Selecione um dos elementos antes de clicar no tabuleiro.";
                                JOptionPane.showMessageDialog(null, mensagem, "Você não pode fazer isso.", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            if (this.sistema.getUsuario().getAcertos() == partesTotais) {
                                this.usuarioVenceu();
                            } else {
                                this.usuarioPerdeu();
                            }
                        }
                    }
                    break; // break do default
           }
        }
    }
    
    public void verificarDica(int linha, int coluna) {
        if (this.dicasRestantes > 0) {
            boolean temElemento = false;
            for (int i = 0; i < this.sistema.getComputador().getNumColunas(); i++) {// testando cada posição da linha
                if (!(this.sistema.getComputador().getPosicaoTabuleiro(linha, i).equals("-"))) {
                    temElemento = true;
                    break;
                }
            }
            if (!temElemento) {// testando cada posição da coluna (somente se temElemento = false)
                for (int i = 0; i < this.sistema.getComputador().getTabuleiro().length; i++) {
                    if (!(this.sistema.getComputador().getPosicaoTabuleiro(i, coluna).equals("-"))) {
                        temElemento = true;
                        break;
                    }
                }
            }
            if (temElemento) {
                String mensagem = "Existe um elemento ou na coluna ou na linha indicada.";
                ImageIcon icone = new ImageIcon("resources/rosto-feliz.png");
                JOptionPane.showMessageDialog(null, mensagem, "Uma dica bem usada!", JOptionPane.PLAIN_MESSAGE, icone);
            } else {
                String mensagem = "Não existe um elemento na coluna nem na linha indicada.";
                ImageIcon icone = new ImageIcon("resources/rosto-triste.png");
                JOptionPane.showMessageDialog(null, mensagem, "Você desperdiçou uma dica.", JOptionPane.PLAIN_MESSAGE, icone);
            }
            this.dicasRestantes--;
            if (dicasRestantes == 0) {
                this.dica.setEnabled(false);
                this.botaoSelecionado.setText("Suas dicas acabaram.");
                this.clicouTiro1 = false;
                this.clicouTiro2 = false;
                this.clicouTiro3 = false;
                this.clicouDica = false;
            } else {
                this.botaoSelecionado.setText("Dica selecionado. Você tem " + this.dicasRestantes + " dicas restantes.");
            }
        } else {
            String mensagem = "Infelizmente você já gastou todas suas dicas.";
            JOptionPane.showMessageDialog(null, mensagem, "Sem dicas.", JOptionPane.ERROR_MESSAGE);
        }
    }
            
    public void verificarTiro(int linha, int coluna, Object comp, Jogador jogador, Jogador adversario) {
        ((JButton) comp).setEnabled(false);
        String posicao = adversario.getPosicaoTabuleiro(linha, coluna);
        int disparoIndex = jogador.getDisparoIndexPorCod(posicao);
        ((JButton) comp).setBackground(new Color(88, 183, 227));
        ImageIcon imagem;
        int largura = 40, altura = 30;
        if (posicao.equals("-")) {
            File arquivo = new File(this.urlErrou);
            if (arquivo.exists()) {
                imagem = new ImageIcon(this.urlErrou);
                imagem = new ImageIcon(imagem.getImage().getScaledInstance(largura, altura, Image.SCALE_DEFAULT));
                ((JButton) comp).setIcon(imagem);
            } else {
                ((JButton) comp).setText("--");
            }
        } else {
            File arquivo = new File(this.urlAcertou);
            if (arquivo.exists()) {
                imagem = new ImageIcon(this.urlAcertou);
                imagem = new ImageIcon(imagem.getImage().getScaledInstance(largura, altura, Image.SCALE_DEFAULT));
                ((JButton) comp).setIcon(imagem);
            } else {
                ((JButton) comp).setText("X");
            }
            
            jogador.acertou();
            if (jogador.getAcertos() == this.sistema.getPartesTotais()) {
                if (jogador.getNome().equals(this.sistema.getUsuario().getNome())) {
                    this.usuarioVenceu();
                } else {
                    this.usuarioPerdeu();
                }
            }
            
            adversario.getDisparos()[disparoIndex].parteFoiAtingida();
            
            System.out.println(adversario.getNome()+":");
            adversario.imprimirPartesAtingidas();
            
            int tamElemento = this.sistema.getElementos()[disparoIndex].getTamanho();
            if (adversario.getDisparos()[disparoIndex].getPartesAtingidas() == tamElemento) {
                adversario.getDisparos()[disparoIndex].tornarIndisponivel();
                
                if (adversario.getNome().equals(this.sistema.getUsuario().getNome())) {
                    String nomeBotao = "Disparo" + disparoIndex;
                    for (Component botao : this.painelBotoes.getComponents()) {
                        if (botao instanceof JButton) {
                            if (((JButton) botao).getName().equals(nomeBotao)) {
                                System.out.println(((JButton) botao).getName());
                               ((JButton) botao).setEnabled(false);
                                this.clicouTiro1 = false;
                                this.clicouTiro2 = false;
                                this.clicouTiro3 = false;
                                this.clicouDica = false;
                                this.botaoSelecionado.setText("Nenhum tipo de disparo selecionado.");
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void jogadaComputador() {
        Random gerador = new Random();
        int linha, coluna;
        boolean possoAtirar;
        do {
            linha = gerador.nextInt(10);
            coluna = gerador.nextInt(10);
            possoAtirar = verificaSePodeAtirar(linha, coluna);
        } while (!possoAtirar);
        
        // botar um teste se o disparo ta disponivel
        int disparo = gerador.nextInt(this.sistema.getMaxElementos());
        
        String nomeBotao = linha + "-" + coluna;
        for(Component comp : this.painelJogador.getComponents()) {
            if (comp instanceof JButton) {
                if (((JButton) comp).getName().equals(nomeBotao)) {
                    this.verificarTiro(linha, coluna, comp, this.sistema.getComputador(), this.sistema.getUsuario());
                    this.jogadasComputador[linha][coluna] = true;
                }
            }
        }
        
        String nomeDisparo = "Disparo " + this.sistema.getComputador().getDisparos()[disparo].getNome();
        switch (nomeDisparo) {
            case "Disparo Comum":
                break;
            case "Disparo Cascata":
                if (verificaSePodeAtirar(linha, (coluna+1))) {
                    if ((coluna+1) < this.sistema.getComputador().getNumColunas()) {
                        nomeBotao = linha + "-" + (coluna+1);
                        for(Component comp : this.painelJogador.getComponents()) {
                            if (comp instanceof JButton) {
                                if (((JButton) comp).getName().equals(nomeBotao)) {
                                    this.verificarTiro(linha, (coluna+1), comp, this.sistema.getComputador(), this.sistema.getUsuario());
                                    this.jogadasComputador[linha][(coluna+1)] = true;
                                }
                            }
                        }
                    }
                }
                break;
            case "Disparo Estrela":
                int[][] posicoes = new int[4][2];
                posicoes[0][0] = linha-1; posicoes[0][1] = coluna; // à direita
                posicoes[1][0] = linha+1; posicoes[1][1] = coluna; // à esquerda
                posicoes[2][0] = linha;   posicoes[2][1] = coluna-1; // acima
                posicoes[3][0] = linha;   posicoes[3][1] = coluna+1; // abaixo

                for (int i = 0; i < 4; i++) {
                    int lin = posicoes[i][0];
                    int col = posicoes[i][1];
                    if (verificaSePodeAtirar(lin, col)) {
                        nomeBotao = lin + "-" + col;
                        for(Component comp : this.painelJogador.getComponents()) {
                            if (comp instanceof JButton) {
                                if (((JButton) comp).getName().equals(nomeBotao)) {
                                    this.verificarTiro(lin, col, comp, this.sistema.getComputador(), this.sistema.getUsuario());
                                    this.jogadasComputador[lin][col] = true;
                                }
                            }
                        }
                    }
                }
                break;
        }
    }
    
    public boolean verificaSePodeAtirar(int linha, int coluna) {
        if(linha < this.sistema.getComputador().getNumColunas() && linha >= 0 && 
           coluna < this.sistema.getComputador().getNumLinhas() && coluna >= 0) {
            if (!this.jogadasComputador[linha][coluna])
                return true;
            return false;
        } else{
            return false;
        }
    }
    
	public void usuarioVenceu() {
        this.usuarioVenceu = true;
        pararCronometro();
        String mensagem = "Parabéns " + this.sistema.getUsuario().getNome() + "! Você venceu o jogo.";
        this.janelaResultado = new JanelaResultado(this, this.sistema, cronometro.getText(), mensagem);
        this.janelaResultado.setVisible(true);
    }
    
    public void usuarioPerdeu() {
        pararCronometro();
        this.janelaResultado = new JanelaResultado(this, this.sistema, cronometro.getText(), "Que pena! O adversário ganhou.");
        this.janelaResultado.setVisible(true);
    }
    
    public static void main(String args[]) {// ISSO TEM QUE SAIR
        Sistema sistema = new Sistema();
        sistema.getUsuario().setNome("Fulanin");
        JanelaJogo janelaJogo = new JanelaJogo(sistema);
        janelaJogo.setVisible(true);
   }   
}