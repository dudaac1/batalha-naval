import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Eduarda e Julia
 */
public class JanelaJogo extends JFrame implements ActionListener {
    private int i;
    private String button;
    //private JButton tiro1, tiro2, tiro3, bDica, bSair;

    
    public JanelaJogo() {
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
        setLayout(new GridLayout(0, 2)); // Dois tabuleiros.
        
        //JPanel p = new JPanel(new GridBagLayout());
        
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
        janelaJogo.add(painelJogador);
        //janelaJogo.add(painelComputador, BorderLayout.EAST); 
        janelaJogo.add(painelComputador);
        
        JButton tiro1 = new JButton("Disparo comum");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        janelaJogo.add(tiro1, c);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
    }
    
    public static void main(String args[]) {
        JanelaJogo janelaJogo = new JanelaJogo();
        janelaJogo.setVisible(true);
   }   
}