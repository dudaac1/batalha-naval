package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import sistema.Sistema; // Importação do Sistema.java
/**
 * @author Eduarda e Julia
 */
public class JanelaResultado extends JFrame implements ActionListener {
    private Sistema sistema;
    private JanelaJogo janelaJogo;
    private JLabel tempo;
    private int vetorRanking[] = new int[15];
    private String vetorNomes[] = new String[15];
    private JTextArea ranking;
    
    public JanelaResultado(JanelaJogo janela, Sistema sistema, String tempo, String mensagem) {
        this.sistema = sistema;
        janela.resetDicas(); 
        janela.resetJogadasComputador();
        this.janelaJogo = janela;
        this.janelaJogo.setVisible(false);
        this.sistema.getUsuario().resetAcertos();
        this.sistema.getComputador().resetAcertos();
        this.tempo = new JLabel("Seu tempo: " + tempo);
        resultado(mensagem);
    }
    
    public void resultado(String mensagem) {
        setTitle("Resultado da partida.");
        setSize(600, 450);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setFont(new Font("Arial", Font.PLAIN, 14));
        Container janelaResultado = getContentPane();
       
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new GridBagLayout());
        
        JLabel textoResultado = new JLabel(mensagem);
        textoResultado.setFont(new Font("Arial Black", Font.PLAIN, 24));
        textoResultado.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelOpcoes.add(textoResultado, gbc);
        
        this.tempo.setHorizontalAlignment(SwingConstants.CENTER);
        this.tempo.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 20, 0);
        painelOpcoes.add(this.tempo, gbc);
        
        this.ranking = new JTextArea(100, 200);
        this.ranking.setEditable(false); // Para ninguém mexer no ranking.
        this.ranking.setBackground(janelaResultado.getBackground());
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.25;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipady = 5;
        gbc.ipadx = 15;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        painelOpcoes.add(this.ranking, gbc);
                
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridBagLayout());
        
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setName("Reiniciar");
        reiniciar.addActionListener(this);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.ipadx = 20;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelBotoes.add(reiniciar, gbc);
        
        JButton novoJogo = new JButton("Novo Jogo");
        novoJogo.setName("Novo Jogo");
        novoJogo.addActionListener(this);
        gbc.gridx = 1;
        painelBotoes.add(novoJogo, gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.ipadx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        painelOpcoes.add(painelBotoes, gbc);
        
        lerArquivo();
        janelaResultado.add(painelOpcoes);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource();
        
        if(origem instanceof JButton) {
            String botaoNome = ((JButton) origem).getName();
            switch(botaoNome) {
                case "Reiniciar":
                    this.janelaJogo.dispose();
                    JanelaJogo reiniciar = new JanelaJogo(this.sistema);
                    reiniciar.setVisible(true);
                    this.dispose();
                    break;
                case "Novo Jogo":
                    this.janelaJogo.dispose();
                    JanelaInicial novoJogo = new JanelaInicial(); 
                    novoJogo.setVisible(true);
                    this.dispose();
                    break;
            }
        }
    }
    
    public void lerArquivo() {
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
        
        for(int i = 0; i < 15; i++) {
            if(vetorRanking[i] != 0) {
                int segundo = vetorRanking[i]%60; // Segundos = resto do contador.  
                int minuto = vetorRanking[i]/60; // Minuto = divisão do contador por 60.
                int hora = minuto/60; // Hora = resultado do minuto divido por 60.
                minuto%=60; 
                this.ranking.append(String.format("%02d:%02d:%02d", hora, minuto, segundo) + " - " + vetorNomes[i] + "\n");
            }
        } 
    }

}