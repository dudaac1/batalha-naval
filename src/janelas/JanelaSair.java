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
    private JanelaJogo janelaJogo;

    public JanelaSair(JanelaJogo janela, Sistema sistema) {
        this.sistema = sistema;
        this.janelaJogo = janela;
        janela.resetDicas(); 
        janela.resetJogadasComputador();
        this.sistema.getUsuario().resetAcertos();
        this.sistema.getComputador().resetAcertos();
        sair();
    }
    
    public void sair() {
        setTitle("Saindo...");
        setSize(300, 300);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        Container janelaSair = getContentPane();
       
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
    
    public static void main(String args[]) {// ISSO TEM QUE SAIR
        //Sistema sistema = new Sistema();
        //JanelaSair janelaSair = new JanelaSair(sistema);
        //janelaSair.setVisible(true);
   }  
}