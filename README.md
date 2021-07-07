# 🚢 Batalha Naval
Trabalho final para a cadeira de Programação Orientada a Objetos do curso de Ciência da Computação.<br>
Projeto ainda em desenvolvimento.

# Características
- Formado por duas grades/tabelas de 10x10 (uma do jogador, outra do computador).]
- Cada tabela possui quatro objetos (três navios e um avião) que possuem disparos diferentes:
  1. Porta-avião: ocupa 4 espaços, um disparo comum (1 célula) a cada dois turnos;
  2. Submarino: ocupa 2 espaços, disparo comum; 
  3. Navio de Escolta: ocupa 3 células, disparo cascata (atinge o quadrado clicado e o logo a direita); 
  4. Caça: ocupa 2 quadrados, disparo estrela (atinge 5 quadrados, o selecionado e mais as células acima, abaixo, à direita e à esquerda deste).

- Cada jogador (usuário e computador) poderá fazer um tipo de disparo por turno, independente se acertou ou não algum espaço contendo algum dos objetos. 
- Os tipos de disparo só estarão disponíveis enquanto o objeto não for totalmente atingido (ou seja, se o objeto Caça for todo atingido, a opção do disparo estrela não poderá estar mais disponível para o jogador nas próximas seleções de disparo).
- As jogadas do computador devem ser implementadas: faz o disparo de forma aleatória, sem repetir a posição. Também utiliza dos diferentes tipos de disparo. 

# Interface gráfica
- Uma janela de boas vindas, com um campo para o jogador inserir seu nome e perguntando se ele pretende gerar um tabuleiro aleatório ou montar seu jogo.
- Uma janela para o usuário montar seu jogo, caso assim ele o escolha, mostrando a grade do jogo. O usuário seleciona o objeto a ser inserido no tabuleiro e depois marca na tabela o objeto através do clique na posição desejada.
- Uma janela para o jogo, onde devem ser exibidas duas grades, uma sendo referente ao tabuleiro do jogador e outra sendo do computador. Deve possuir as opções para escolher o tipo de disparo, um botão para Dica e outra para Sair. Deve apresentar um cronômetro.
- Uma janela caso o usuário clique em sair apresentando as opções "Reiniciar Jogo" e "Novo Jogo".
- Uma janela para ser exibida ao final do jogo informando se o jogador ganhou ou perdeu, com o tempo total de jogo, seguido de um ranking dos 15 melhores jogadores.

# Diretórios
 - _resources_: contém as imagens e o arquivo .txt necessários para a correta execução do programa;
 - _src/janelas_: contém os arquivos .java referentes à interface gráfica do projeto;
 - _src/sistema_: contém os arquivos .java referentes às estruturas necessárias para persistência de dados entre janelas.

# Execução
Você deve saber que o projeto foi desenvolvido utilizando-se do _Apache Netbeans IDE 12.0_ com o _JDK 11_.<br>
Para executar o programa, clone o repositório (`git clone https://github.com/dudaac1/batalha-naval`).<br>
A execução do projeto deve ser feita a partir da classe JanelaInicial.java.


---

_Desenvolvido por Eduarda Carvalho (eduarda.carvalho@inf.ufpel.edu.br) e Júlia Veiga (jvsilva@inf.ufpel.edu.br), junho/2021._
