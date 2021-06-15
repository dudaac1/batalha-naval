package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sistema.Sistema; // Importação do Sistema.java
/**
 * @author Eduarda e Julia
 */
public class JanelaVenceu extends JFrame implements ActionListener {
    private Sistema sistema;
    private JanelaJogo janelaJogo;

    public JanelaVenceu(JanelaJogo janela, Sistema sistema) {
        this.sistema = sistema;
        this.janelaJogo = janela;
        venceu();
    }
    
    public void venceu() {
        setTitle("Você venceu.");
        setSize(300, 300);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        //setVisible(true);
        Container janelaVenceu = getContentPane();
        //GridBagConstraints gbc = new GridBagConstraints();
        //setLayout(new GridBagLayout());
       
        JLabel textoVenceu = new JLabel("Parabéns! Você venceu o jogo.");
        textoVenceu.setFont(new Font("Arial", Font.PLAIN, 14));
        textoVenceu.setAlignmentY(TOP_ALIGNMENT);
        
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new GridBagLayout());
        
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setName("Reiniciar");
        reiniciar.addActionListener(this);
        
        JButton novoJogo = new JButton("Novo Jogo");
        novoJogo.setName("Novo Jogo");
        novoJogo.addActionListener(this);
        
        painelOpcoes.add(textoVenceu);
        painelOpcoes.add(reiniciar);
        painelOpcoes.add(novoJogo);
        
        janelaVenceu.add(painelOpcoes);
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
        JanelaVenceu janelaVenceu = new JanelaVenceu(null, null);
        janelaVenceu.setVisible(true);
   }  
}