package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sistema.Sistema; // Importação do Sistema.java
/**
 * @author Eduarda e Julia
 */
public class JanelaSair extends JFrame implements ActionListener {
    private Sistema sistema;
    
    public JanelaSair(Sistema sistema) {
        this.sistema = sistema;
        sair();
    }
    
    public void sair() {
        setTitle("Saindo...");
        setSize(300, 300);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        setVisible(true);
        Container janelaSair = getContentPane();
        //GridBagConstraints gbc = new GridBagConstraints();
        //setLayout(new GridBagLayout());
       
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new GridBagLayout());
        
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setName("Reiniciar");
        reiniciar.addActionListener(this);
        
        JButton novoJogo = new JButton("Novo Jogo");
        novoJogo.setName("Novo Jogo");
        novoJogo.addActionListener(this);
        
        painelOpcoes.add(reiniciar);
        painelOpcoes.add(novoJogo);
        
        janelaSair.add(painelOpcoes);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource();
        
        if(origem instanceof JButton) {
            String botaoNome = ((JButton) origem).getName();
            System.out.println(botaoNome);
            
            switch(botaoNome) {
                case "Reiniciar":
                    break;
                case "Novo Jogo":
                    break;
            }
        }
    }
    
    public static void main(String args[]) {
        Sistema sistema = new Sistema();
        JanelaSair janelaSair = new JanelaSair(sistema);
        janelaSair.setVisible(true);
   }  
}
