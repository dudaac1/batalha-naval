package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sistema.Sistema; // adicionei a importação do Sistema.java

/**
 * @author Eduarda e Julia
 */
public class JanelaJogo extends JFrame implements ActionListener {
    private Sistema sistema;
    private int i;
    private String button;
    //private JButton tiro1, tiro2, tiro3, bDica, bSair;

    
    public JanelaJogo(Sistema sistema) {
        this.sistema = sistema; // pra passar as infos de uma janela pra outra
        jogar();
    }
    
    public void jogar() {
        //JFrame f = new JFrame();
        setTitle("Hora do jogo!");
        setSize(600, 450);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        setVisible(true);
        Container janelaJogo = getContentPane();
        setLayout(new GridLayout(0, 1)); // N linhas x 1 coluna
//        setLayout(new GridLayout(0, 2)); // Dois tabuleiros.
        
        //JPanel p = new JPanel(new GridBagLayout());
        JPanel painelBotoes = new JPanel(new GridLayout(1, 6));
        JButton tiro1 = new JButton("Disparo comum");
        JButton tiro2 = new JButton("Disparo cascata");
        JButton tiro3 = new JButton("Disparo ");
        JButton tiro4 = new JButton(" cascata");
        JButton dica = new JButton("DDDmum");
        JButton sair = new JButton("SSascata");
        painelBotoes.add(tiro1);
        painelBotoes.add(tiro2);
        painelBotoes.add(tiro3);
        painelBotoes.add(tiro4);
        painelBotoes.add(dica);
        painelBotoes.add(sair);
        janelaJogo.add(painelBotoes);
        
        JPanel painelTabuleiros = new JPanel();
        painelTabuleiros.setLayout(new GridLayout(0, 2)); // Dois tabuleiros.
        
        JPanel painelJogador = new JPanel(); 
        painelJogador.setLayout(new GridLayout(10, 10));
        
        for (i = 0; i < 100; i++) { 
            button = "button";
            button = button + i;
            System.out.println(button);
            JButton button = new JButton("");
            button.setBounds(0, 0, 4, 4);
            button.setBackground(new Color(88, 183, 227));
            painelJogador.add(button);
        }
        
        JPanel painelComputador = new JPanel();
        painelComputador.setLayout(new GridLayout(10, 10));
        
        for (i = 0; i < 100; i++) {
             button = "button";
             button = button + i;
             System.out.println(button);
             JButton button = new JButton("");
             button.setBounds(0, 0, 4, 4);
             button.setBackground(new Color(222, 136, 230));
             painelComputador.add(button);
        }
        
        /*this.tiro1 = new JButton("Disparo comum");
        this.tiro2 = new JButton("Disparo cascata");
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(10, 10, 10, 10); // 10 pixels de distância entre os botões: top, bottom, left e right.
        gbc.gridx = 0;
        gbc.gridy = 1;
        p.add(tiro1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        p.add(tiro2, gbc);*/
        
        //janelaJogo.add(p, BorderLayout.NORTH);
        //janelaJogo.add(painelJogador, BorderLayout.WEST);
        
        painelTabuleiros.add(painelJogador);
        painelTabuleiros.add(painelComputador);
        janelaJogo.add(painelTabuleiros);
//        janelaJogo.add(painelJogador);
//        janelaJogo.add(painelComputador);
        
        
        //janelaJogo.add(painelComputador, BorderLayout.EAST); 
        
        
        
        
//        JButton tiro1 = new JButton("Disparo comum");
//        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.NORTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.anchor = GridBagConstraints.NORTH;
//        janelaJogo.add(tiro1, c);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
    }
    
    public static void main(String args[]) {
        Sistema sistema = new Sistema();
        sistema.setNomeJogador("Fulanin");
        JanelaJogo janelaJogo = new JanelaJogo(sistema);
        janelaJogo.setVisible(true);
   }   
}