package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sistema.Sistema; // Importação do Sistema.java
/**
 * @author Eduarda e Julia
 */
public class JanelaPerdeu extends JFrame implements ActionListener {
    private Sistema sistema;
    private JanelaJogo janelaJogo;

    public JanelaPerdeu(JanelaJogo janela, Sistema sistema) {
        this.sistema = sistema;
        this.janelaJogo = janela;
        perdeu();
    }
    
    public void perdeu() {
        setTitle("Você perdeu.");
        setSize(300, 300);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        //setVisible(true);
        Container janelaPerdeu = getContentPane();
        //GridBagConstraints gbc = new GridBagConstraints();
        //setLayout(new GridBagLayout());
       
        JLabel textoPerdeu = new JLabel("Que pena! Você perdeu o jogo.");
        textoPerdeu.setFont(new Font("Arial", Font.PLAIN, 14));
        textoPerdeu.setAlignmentY(TOP_ALIGNMENT);
        
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new GridBagLayout());
        
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setName("Reiniciar");
        reiniciar.addActionListener(this);
        
        JButton novoJogo = new JButton("Novo Jogo");
        novoJogo.setName("Novo Jogo");
        novoJogo.addActionListener(this);
        
        painelOpcoes.add(textoPerdeu);
        painelOpcoes.add(reiniciar);
        painelOpcoes.add(novoJogo);
        
        janelaPerdeu.add(painelOpcoes);
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
    
    public static void main(String args[]) {
        Sistema sistema = new Sistema();
        JanelaPerdeu janelaPerdeu = new JanelaPerdeu(null, null);
        janelaPerdeu.setVisible(true);
   }  
}