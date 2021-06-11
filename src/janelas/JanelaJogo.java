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
    private JanelaSair janelaSair;
    private Boolean clicouTiro1 = false, clicouTiro2 = false, clicouTiro3 = false, clicouDica = false, clicouSair = false;
    
    public JanelaJogo(Sistema sistema) {
        this.sistema = sistema; // Passando as informações de uma janela para outra.
        //this.janelaSair = new JanelaSair(this.sistema);
        this.janelaSair = new JanelaSair(this, this.sistema);
        //this.janelaSair.setVisible(false);
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
        //painelBotoes.setLayout(new GridLayout(1, 5, 5, 0));
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
                button.setName(i + "-" + j);
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
                button.setName(i + "-" + j);
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
                    this.clicouTiro1 = true;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = false;
                    this.clicouDica = false;
                    this.clicouSair = false;
                    break;
                case "Disparo cascata":
                    this.clicouTiro2 = true;
                    this.clicouTiro1 = false;
                    this.clicouTiro3 = false;
                    this.clicouDica = false;
                    this.clicouSair = false;
                    break;
                case "Disparo estrela":
                    this.clicouTiro3 = true;
                    this.clicouTiro2 = false;
                    this.clicouTiro1 = false;
                    this.clicouDica = false;
                    this.clicouSair = false;
                    break;
                case "Dica":
                    this.clicouDica = true;
                    this.clicouTiro1 = false;
                    this.clicouTiro2 = false;
                    this.clicouTiro3 = false;
                    this.clicouSair = false;
                    
                   break;
                case "Sair":
                    this.janelaSair.setVisible(true); // O fluxo tem que parar aqui e não tá parando. Tem que esperar a ação da outra janela pra continuar.
                
                default: // Qualquer botão do tabuleiro.
                    int linha, coluna;
                    
                    linha = Integer.parseInt(botaoNome.substring(0, 1)); // "Fatiar" a string para pegar apenas a linha.
                    System.out.println(linha);
                    
                    coluna = Integer.parseInt(botaoNome.substring(2, 3)); // "Fatiar" a string para pegar apenas a coluna.
                    System.out.println(coluna);
                   
                    
                    if(clicouDica){
                        //se tiver/ou não um navio na posição, abrir uma caixinha
                        //de diálogo só com um ok informando se existe ou nao o navio 
                    }
                        
                    else if(clicouTiro1){
                                
                    }
                    
                    else if(clicouTiro2){
                                
                    }
                    
                    else if(clicouTiro3){
                                
                    }

                   break;
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