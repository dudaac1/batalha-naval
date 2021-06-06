package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sistema.Sistema; // Importação do Sistema.java

/**
 * @author Eduarda e Julia
 */
public class JanelaJogo extends JFrame implements ActionListener {
    private Sistema sistema;
    private int i;
    private String button;
    
    public JanelaJogo(Sistema sistema) {
        this.sistema = sistema; // Passando as informações de uma janela para outra.
        jogar();
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
        
        // BOTÕES
        //setLayout(new GridLayout(0, 1)); // N linhas x 1 coluna.
        //JPanel painelBotoes = new JPanel(new GridLayout(1, 6));
        JPanel painelBotoes = new JPanel();
//        painelBotoes.setLayout(new GridLayout(1, 5, 5, 0));
        painelBotoes.setLayout(new GridBagLayout());
        
        JButton tiro1 = new JButton("Disparo comum");
        tiro1.setName("Disparo comum");
        tiro1.addActionListener(this);
        
        JButton tiro2 = new JButton("Disparo cascata");
        tiro2.setName("DisparoCascata");
        tiro2.addActionListener(this);
        
        JButton tiro3 = new JButton("Disparo estrela");
        tiro3.setName("Disparo estrela");
        tiro3.addActionListener(this);
        
        JButton dica = new JButton("Dica");
        dica.setName("Dica");
        dica.addActionListener(this);
        
        JButton sair = new JButton("Sair");
        sair.setName("Sair");
        sair.addActionListener(this);
        
        painelBotoes.add(tiro1);
        painelBotoes.add(tiro2);
        painelBotoes.add(tiro3);
        painelBotoes.add(dica);
        painelBotoes.add(sair);
       
        //painelBotoes.setAlignmentY(TOP_ALIGNMENT); // Não deu certo.
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 0, 0, 0); // ?
        janelaJogo.add(painelBotoes, gbc);
        
        // TABULEIROS
        JPanel painelTabuleiros = new JPanel();
        painelTabuleiros.setLayout(new GridLayout(0, 2)); // Dois tabuleiros.
        
        // TABULEIRO DO JOGADOR
        JPanel painelJogador = new JPanel(); 
        painelJogador.setLayout(new GridLayout(11, 11));
        
        String letrasJogador = " ABCDEFGHIJ";
        for(int i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + letrasJogador.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelJogador.add(label);
        }
        
        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelJogador.add(label);
            
            for(int j = 0; j < 10; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(88, 183, 227));
                button.addActionListener(this);
                button.setName(j + "-" + j);
                painelJogador.add(button);
            }
        }
        
        // TABULEIRO DO COMPUTADOR
        JPanel painelComputador = new JPanel();
        painelComputador.setLayout(new GridLayout(11, 11));
        
        String letrasComputador = " ABCDEFGHIJ";
        for(int i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + letrasComputador.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelComputador.add(label);
        }
        
        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelComputador.add(label);
            
            for(int j = 0; j < 10; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(222, 136, 230));
                button.addActionListener(this);
                button.setName(j + "-" + j);
                painelComputador.add(button);
            }
        }
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;

        
        painelTabuleiros.add(painelJogador);
        painelTabuleiros.add(painelComputador);
        janelaJogo.add(painelTabuleiros, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource(); // Ajuda a determinar qual componente disparou o evento.
        
        if(origem instanceof JButton) { // "Se 'origem' é um JButton..."
           String botaoNome = ((JButton) origem).getName();
           System.out.println(botaoNome);
           
           switch(botaoNome) {
                case "Disparo comum":
                   break;
                case "Disparo cascata":
                   break;
                case "Disparo estrela":
                   break;
                case "Dica":
                   break;
                case "Sair":
                    //System.exit(0);
                   JanelaSair sair = new JanelaSair(this.sistema);
                   this.setVisible(true);
                   sair.setVisible(true);
                   break;
           }
        }
    }
    
    public static void main(String args[]) {
        Sistema sistema = new Sistema();
        sistema.setNomeJogador("Fulanin");
        JanelaJogo janelaJogo = new JanelaJogo(sistema);
        janelaJogo.setVisible(true);
   }   
}