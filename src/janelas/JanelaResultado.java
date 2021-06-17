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
        this.janelaJogo = janela;
        this.tempo = new JLabel(tempo);
        resultado(mensagem);
    }
    
    public void resultado(String mensagem) {
        setTitle("Resultado da partida.");
        setSize(300, 300);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        //setVisible(true);
        Container janelaResultado = getContentPane();
        //GridBagConstraints gbc = new GridBagConstraints();
        //setLayout(new GridBagLayout());
       
        JLabel textoResultado = new JLabel(mensagem);
        textoResultado.setFont(new Font("Arial", Font.PLAIN, 14));
        textoResultado.setAlignmentY(TOP_ALIGNMENT);
        
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new GridBagLayout());
        
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setName("Reiniciar");
        reiniciar.addActionListener(this);
        
        JButton novoJogo = new JButton("Novo Jogo");
        novoJogo.setName("Novo Jogo");
        novoJogo.addActionListener(this);
        
        this.ranking = new JTextArea(100, 100);
        JScrollPane scrollPane = new JScrollPane(this.ranking);
        this.ranking.setEditable(false); // Para ninguém mexer no ranking.
        
        painelOpcoes.add(textoResultado);
        painelOpcoes.add(reiniciar);
        painelOpcoes.add(novoJogo);
        painelOpcoes.add(this.tempo);
        painelOpcoes.add(this.ranking);
        
        lerArquivo();
        
        janelaResultado.add(painelOpcoes);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource();
        
        if(origem instanceof JButton) {
            String botaoNome = ((JButton) origem).getName();
            System.out.println(botaoNome);
            
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
                    //janelaJogo.setVisible(false);
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
    
    public static void main(String args[]) {// ISSO TEM QUE SAIR
        Sistema sistema = new Sistema();
        //JanelaResultado janelaResultado = new JanelaResultado(null, null);
        //janelaResultado.setVisible(true);
   }  
}