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
import java.util.Timer;
import java.util.TimerTask;

import sistema.Sistema; // Importação do Sistema.java

/**
 * @author Eduarda e Julia
 */
public class JanelaJogo extends JFrame implements ActionListener {
    private Sistema sistema;
    private static int contador = 0;
    private int posNome = 0;
    private JanelaSair janelaSair;
    private JanelaVenceu janelaVenceu;
    private JanelaPerdeu janelaPerdeu;
    private boolean clicouTiro1 = false, clicouTiro2 = false, clicouTiro3 = false;
    private boolean clicouDica = false, clicouSair = false;
    private JLabel cronometro;
    private Timer tempo;
    private int vetorRanking[] = new int[15];
    private String vetorNomes[] = new String[15];
    private JLabel botaoSelecionado;
    private static int dicasRestantes = 3;
    private int contPartes;
    private String parteAnterior;
    
    public JanelaJogo(Sistema sistema) {
        this.sistema = sistema; // Passando as informações de uma janela para outra.
        //this.janelaSair = new JanelaSair(this.sistema);
        this.janelaSair = new JanelaSair(this, this.sistema);
        this.janelaVenceu = new JanelaVenceu(this, this.sistema);
        this.janelaPerdeu = new JanelaPerdeu(this, this.sistema);
        //this.janelaSair.setVisible(false);
        jogar();
        iniciarCronometro();
    }
    
    public void jogar() {
        setTitle("Hora do jogo!");
        setSize(900, 600);
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
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridBagLayout());
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
            tiro.setName(texto);
            tiro.addActionListener(this);
            if (!this.sistema.getUsuario().getDisparos()[i].getDisponivel())
                tiro.setEnabled(false);
//            System.out.println("Tiro " + (i+1) + ":" + texto); 
            gbc.gridx = i;
            painelBotoes.add(tiro, gbc);
        }
        
        JButton dica = new JButton("Dica");
        dica.setName("Dica");
        dica.addActionListener(this);
        if (this.dicasRestantes == 0)
            dica.setEnabled(false);
        gbc.gridx = ++i;
        painelBotoes.add(dica, gbc);
        
        JButton sair = new JButton("Sair");
        sair.setName("Sair");
        sair.addActionListener(this);
        gbc.gridx = ++i;
        painelBotoes.add(sair, gbc);
        
        gbc.gridx = ++i;
        painelBotoes.add(cronometro, gbc);
        
        this.botaoSelecionado = new JLabel("Nenhum tipo de disparo selecionado.");
        this.botaoSelecionado.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 0, 0); 
        gbc.gridwidth = i;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelBotoes.add(this.botaoSelecionado, gbc);
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.insets = new Insets(20, 0, 15, 0); 
        gbc.anchor = GridBagConstraints.PAGE_START;
        janelaJogo.add(painelBotoes, gbc);
        
        // TABULEIROS
        JPanel painelTabuleiros = new JPanel();
        painelTabuleiros.setLayout(new GridBagLayout()); // Dois tabuleiros.
        
        
        // TABULEIRO DO JOGADOR
        String letras = " ABCDEFGHIJ";
        JPanel painelJogador = new JPanel(); 
        painelJogador.setLayout(new GridLayout(12, 11));
        for(i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + letras.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelJogador.add(label);
        }
        for(i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelJogador.add(label);
            for(int j = 0; j < 10; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(88, 183, 227));
                button.addActionListener(this);
                button.setName(i + "-" + j);
                button.setEnabled(false);
                String posicao = this.sistema.getUsuario().getPosicaoTabuleiro(i, j);
                if (!(posicao.equals("-"))) {
                        this.contPartes++;
                        int elementoIndex = this.sistema.getElementoIndexPorCod(posicao);
                        String urlElemento = this.sistema.getElementos()[elementoIndex].getParcialUrl();
                        urlElemento += this.contPartes + "png"; /////////////
                        File arquivo = new File(urlElemento);
                    if (arquivo.exists()) {
                        ImageIcon imagem = new ImageIcon(urlElemento);
                        int largura = imagem.getIconWidth()/2;
                        int altura = imagem.getIconHeight()/2;
                        imagem = new ImageIcon(imagem.getImage().getScaledInstance(largura, altura, Image.SCALE_DEFAULT));
                    }
                    else {
                        button.setBackground(Color.LIGHT_GRAY);
                        button.setText(posicao + this.contPartes);
                    }
                } else
                    this.contPartes = 0;
                painelJogador.add(button);
            }
        }
        
        // TABULEIRO DO COMPUTADOR
        JPanel painelComputador = new JPanel();
        painelComputador.setLayout(new GridLayout(12, 11));
        for(i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + letras.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelComputador.add(label);
        }
        for(i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelComputador.add(label);
            
            for(int j = 0; j < 10; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(222, 136, 230));
                button.addActionListener(this);
                button.setName(i + "-" + j);
                painelComputador.add(button);
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
        painelTabuleiros.add(painelJogador, gbc);
        
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        painelTabuleiros.add(painelComputador, gbc);
        
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 20, 0, 30);
        janelaJogo.add(painelTabuleiros, gbc);
        
        
        JPanel labelPanel  = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 2));
        
        JLabel txtJogador = new JLabel("Seu tabuleiro.");
        txtJogador.setHorizontalAlignment(SwingConstants.CENTER);
        txtJogador.setFont(new Font("Arial", Font.PLAIN, 14));
        labelPanel.add(txtJogador);
        
        JLabel txtComputador = new JLabel("Tabuleiro adversário.");
        txtComputador.setHorizontalAlignment(SwingConstants.CENTER);
        txtComputador.setFont(new Font("Arial", Font.PLAIN, 14));
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
//        File arquivo = new File("C:\\teste.txt"); // Criando o arquivo.
        File arquivo = new File("resources/ranking.txt"); // Criando o arquivo.
        try(FileReader fr = new FileReader(arquivo)) {
            int indexVetor = 0;
            BufferedReader br = new BufferedReader(fr);
            String conteudoLinha; // Conteúdo da linha do arquivo.
            while((conteudoLinha = br.readLine()) != null) { // Lê linha por linha do arquivo.
                vetorRanking[indexVetor] = Integer.parseInt(conteudoLinha);
                indexVetor++;
            }
        } 
        catch(IOException ex) {
            ex.printStackTrace();
        }
        
        int tmp1 = 0, tmp2 = 0;
        for(int i = 0; i < vetorRanking.length; i++) {
            if(vetorRanking[i] > contador || vetorRanking[i] == 0){
                tmp1 = vetorRanking[i];
                vetorRanking[i] = contador;
                for(int j = i+1; j < vetorRanking.length-1; j++) {
                    tmp2 = vetorRanking[j];
                    vetorRanking[j] = tmp1;
                    tmp1 = tmp2;
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
            bw.flush();
        } 
        catch(IOException ex) {
             ex.printStackTrace();
        }
        contador = 0;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource(); // Ajuda a determinar qual componente disparou o evento.
        
        if(origem instanceof JButton) { // "Se 'origem' é um JButton..."
           String botaoNome = ((JButton) origem).getName();
           System.out.println(botaoNome);
           
           switch(botaoNome) {
                case "Disparo Comum":
                    this.clicouTiro1 = true;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = false;
                    this.clicouDica = false;
                    this.clicouSair = false;
                    this.botaoSelecionado.setText("Disparo Comum selecionado.");
                    break;
                case "Disparo Cascata":
                    this.clicouTiro1 = false;
                    this.clicouTiro2 = true;
                    this.clicouTiro3 = false;
                    this.clicouDica = false;
                    this.clicouSair = false;
                    this.botaoSelecionado.setText("Disparo Cascata selecionado.");
                    break;
                case "Disparo Estrela":
                    this.clicouTiro1 = false;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = true;
                    this.clicouDica = false;
                    this.clicouSair = false;
                    this.botaoSelecionado.setText("Disparo Estrela selecionado.");
                    break;
                case "Dica":
                    this.clicouDica = true;
                    this.clicouTiro1 = false;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = false;
                    this.clicouSair = false;
                    this.botaoSelecionado.setText("Dica selecionado. Você tem " + this.dicasRestantes + " dicas restantes.");
                    
                    break;
                case "Sair":
                    pararCronometro();
                    this.janelaSair.setVisible(true);
                    break;
                
                default: // Qualquer botão do tabuleiro.
                    //if(clicouTiro1 || clicouTiro2 || clicouTiro3 || clicouDica) {
                        int linha, coluna;
                    
                        linha = Integer.parseInt(botaoNome.substring(0, 1)); // "Fatiar" a string para pegar apenas a linha.
                        coluna = Integer.parseInt(botaoNome.substring(2, 3)); // "Fatiar" a string para pegar apenas a coluna.
//                        System.out.println(linha);
//                        System.out.println(coluna);

                        if(clicouDica){
                            if (this.dicasRestantes > 0) {
                                boolean temElemento = false;
                                for (int i = 0; i < this.sistema.getComputador().getNumColunas(); i++) // testando cada posição da linha
                                    if (!(this.sistema.getComputador().getPosicaoTabuleiro(linha, i).equals("-"))) {
                                        temElemento = true;
                                        break;
                                    }
                                if (!temElemento) // testando cada posição da coluna (somente se temElemento ainda for falso)
                                    for (int i = 0; i < this.sistema.getComputador().getTabuleiro().length; i++) 
                                        if (!(this.sistema.getComputador().getPosicaoTabuleiro(linha, i).equals("-"))) {
                                            temElemento = true;
                                            break;
                                        }
                                if (temElemento)
                                    JOptionPane.showMessageDialog(null, "Existe um elemento ou na coluna ou na linha indicada.");
                                else
                                    JOptionPane.showMessageDialog(null, "Não existe um elemento na coluna nem na linha indicada.");
                                this.dicasRestantes--;
                                this.botaoSelecionado.setText("Dica selecionado. Você tem " + this.dicasRestantes + " dicas restantes.");
                                
                                JanelaJogo recarregar = new JanelaJogo(this.sistema); // cria uma nova janela
                                recarregar.setVisible(true); // deixa a nova janela visivel

                                this.dispose(); // "mata" a janela anteriormente aberta
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Você não possui mais dicas.");
                            }
                        } else {
                            ((JButton) origem).setEnabled(false);
                            if(clicouTiro1){
                                
                            } else if(clicouTiro2){
                                
                            } else if(clicouTiro3){
                                
                            }
                        }
                    break; // break do default
           }
        }
    }
    
    public static void main(String args[]) {
        Sistema sistema = new Sistema();
        sistema.getUsuario().setNome("Fulanin");
//      sistema.setNomeUsuario("Fulanin");
        JanelaJogo janelaJogo = new JanelaJogo(sistema);
        janelaJogo.setVisible(true);
   }   
}