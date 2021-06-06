package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.event.ActionEvent;
import java.io.File;
import sistema.Sistema;

/**
 * @author Eduarda e Julia
 */
public class MontarJogo extends JFrame implements ActionListener {

    private Sistema sistema;

    public MontarJogo(Sistema sistema) {
        this.sistema = sistema;
        montar();
    }

    public void montar() {
        setTitle("Montando seu jogo");
        setSize(900, 600);
        setLocationRelativeTo(null); // Centralizando como padrão.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Para "matar" o processo quando apertar o X.
        setFont(new Font("Arial", Font.PLAIN, 14));
        setVisible(true);
        Container janelaMontar = getContentPane();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new GridLayout(0, 1));
        JLabel titulo = new JLabel("MONTANDO SEU JOGO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        String texto = "Olá " + sistema.getNomeJogador() + ", vamos montar o seu jogo. "
                + "Você deve selecionar o item que deseja colocar no tabuleiro \ne, então, "
                + "clicar na posição que quer inseri-lo.";

        JTextArea subtitulo = new JTextArea(2, 15);
        subtitulo.setText(texto);
        subtitulo.setWrapStyleWord(true);
        subtitulo.setLineWrap(true);
        subtitulo.setOpaque(false);
        subtitulo.setEditable(false);
        subtitulo.setFocusable(false);
        subtitulo.setBackground(UIManager.getColor("Label.background"));
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        painelTopo.add(titulo);
        painelTopo.add(subtitulo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        janelaMontar.add(painelTopo, gbc);

        JPanel painelTabuleiro = new JPanel();
        painelTabuleiro.setLayout(new GridLayout(11, 11));

        String letras = " ABCDEFGHIJ";
        for (int i = 0; i < 11; i++) {
            JLabel label = new JLabel("" + letras.charAt(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelTabuleiro.add(label);
        }
        for (int i = 0; i < 10; i++) {
            JLabel label = new JLabel(String.valueOf(i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            painelTabuleiro.add(label);
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton("");
                button.setBackground(new Color(88, 183, 227));
                button.addActionListener(this);
                button.setName(j + "-" + j);
                painelTabuleiro.add(button);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        janelaMontar.add(painelTabuleiro, gbc);

        
        JPanel painelElementos = new JPanel();
        painelElementos.setLayout(new GridLayout(0, 1));
        int i;
        String nomeElemento, urlElemento;
        File arquivo;
        ImageIcon imagem;
        JLabel label;
        for (i = 0; i < sistema.elementos.length; i++) {
            nomeElemento = sistema.elementos[i].getNome();
            urlElemento = sistema.elementos[i].getUrl();
            arquivo = new File(urlElemento);
            if (arquivo.exists()) {
                imagem = new ImageIcon(urlElemento);
                label = new JLabel(imagem);
            } else{
                
                label = new JLabel(nomeElemento);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
                label.setFont(new Font("Arial", Font.PLAIN, 16));
                painelElementos.add(label);
        }
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 20, 0, 0);
        janelaMontar.add(painelElementos, gbc);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(1, 2, 15, 0));
        
        JButton botaoJogar = new JButton("Jogar");
        botaoJogar.setName("Jogar");
        botaoJogar.addActionListener(this);
        painelBotoes.add(botaoJogar);
        
        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.setName("Limpar");
        botaoLimpar.addActionListener(this);
        painelBotoes.add(botaoLimpar);
        
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 3, 0);
        janelaMontar.add(painelBotoes, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object origem = event.getSource();
        if (origem instanceof JButton) {
            String botaoNome = ((JButton) origem).getName();
            System.out.println(botaoNome);
            switch (botaoNome) {
                case "Jogar":
                    System.out.println("Esse botão significa que a gente ta indo jogar");
                    JanelaJogo jogar = new JanelaJogo(this.sistema);
                    this.setVisible(false);
                    jogar.setVisible(true);
                    break;
                case "Limpar":
                    System.out.println("Esse é pra resetar o tabuleiro");
                    break;
                default:
                    String[] valores = botaoNome.split("-");
                    int linha = Integer.parseInt(valores[0]);
                    int coluna = Integer.parseInt(valores[1]);
                    System.out.println("ORIGEM: " + botaoNome + "; LINHA: " + linha + "; COLUNA: " + coluna);
                    ((JButton) origem).setEnabled(false);
                    ((JButton) origem).setBackground(Color.LIGHT_GRAY);
                    // preciso fazer com que os botões do tamanho do barco
                    // também fiquem desabilitados e cinzas
            }
        }
    }

    // essa função só ta aqui pra eu executar ela direto
    public static void main(String args[]) {
        Sistema sistema = new Sistema();
        sistema.setNomeJogador("Fulano");
        MontarJogo janelaMontar = new MontarJogo(sistema);
        janelaMontar.setVisible(true);
    }
}
