import java.util.ArrayList;  // Importa a classe ArrayList da biblioteca padrão do Java
import java.util.List;  // Importa a interface List da biblioteca padrão do Java
import java.util.Random;  // Importa a classe Random da biblioteca padrão do Java
import java.util.Scanner;  // Importa a classe Scanner da biblioteca padrão do Java

public class Sudoko {  // Declaração da classe pública Sudoku
    private static int[][] matrix;  // Declaração de uma matriz estática para armazenar o tabuleiro do Sudoku atual
    private static int[][] solucaoMatrix;  // Declaração de uma matriz estática para armazenar a solução do Sudoku
    private static Scanner sc;  // Declaração de um Scanner estático para entrada de dados do usuário
    private static List<Integer> linhas;  // Declaração de uma lista estática para armazenar as linhas das jogadas
    private static List<Integer> colunas;  // Declaração de uma lista estática para armazenar as colunas das jogadas
    private static int qtdDica;  // Declaração de uma variável estática para contar o número de dicas restantes
    private static List<int[][]> sudokus;  // Declaração de uma lista estática para armazenar diferentes tabuleiros de Sudoku

    public void jogo() {  // Método público para iniciar o jogo
        sudokus = new ArrayList<>();  // Inicializa a lista de tabuleiros de Sudoku
        linhas = new ArrayList<>();  // Inicializa a lista de linhas das jogadas
        colunas = new ArrayList<>();  // Inicializa a lista de colunas das jogadas
        sc = new Scanner(System.in);  // Inicializa o Scanner para entrada de dados
        prencheSudokus();  // Chama o método que preenche a lista de tabuleiros de Sudoku

        int op = 1;  // Variável para armazenar a opção do menu escolhida pelo usuário
        int ganhou = 0;  // Variável para indicar se o jogador ganhou o jogo
        var menu = """ 
             // Variável para armazenar o menu do jogo
            Seja Bem vindo ao
            
            ⠀⣿⣿⠟⣛⠻⣿⣿⠀⣿⣿⢻⣿⣿⢿⣿⡇⢸⣿⠟⡛⠻⢿⣿⣿⠀⣿⣿⠟⣛⠻⣿⣿⡄⣾⣿⢻⣿⡿⣻⣿⡇⢸⣿⡟⣿⣿⡿⣿⣿
            ⠀⣿⣿⡘⠿⣿⣿⣿⠀⣿⣿⢸⣿⣿⢸⣿⡇⢸⣿⠀⣿⣿⡆⣿⣿⠠⣿⡇⣾⣿⣷⠘⣿⡇⣿⣿⠸⢋⣴⣿⣿⡇⢸⣿⡇⣿⣿⡇⣿⣿
            ⠀⣿⡿⢿⣷⡄⣿⣿⠀⣿⣿⠸⣿⣿⢸⣿⡇⢸⣿⠀⣿⣿⢇⣿⣿⠀⣿⡇⢿⣿⡿⢠⣿⡇⣿⣿⢸⣦⡻⣿⣿⡇⢸⣿⡇⢿⣿⡇⣿⣿
            ⠀⣿⣿⣦⣭⣴⣿⣿⠀⢿⣿⣶⣤⣴⣿⣿⡇⠸⣿⣦⣤⣶⣾⣿⣿⠀⣿⣿⣦⣭⣴⣿⣿⠇⢿⣿⣼⣿⣷⣼⣿⡇⠸⣿⣷⣦⣥⣾⣿⣿
            
            Digite A dificulade
            1 - Facil 
            2 - Medio
            3 - dificil
        """;
        System.out.println(menu);  // Exibe o menu para o usuário
        String dificuldade = sc.nextLine();  // Lê a dificuldade escolhida pelo usuário
        obtemSudoku(dificuldade);  // Chama o método que obtém o Sudoku de acordo com a dificuldade
        System.out.println();

        while (ganhou == 0) {  // Loop principal do jogo, continua enquanto o jogador não ganhar
            if (op == 1) {  // Opção 1: Efetuar uma nova jogada

                printMatriz(matrix);  // Exibe a matriz do Sudoku atual

                System.out.println();

                System.out.println("Digite um valor de 1 a 9");

                int chute = sc.nextInt();  // Lê o valor a ser inserido pelo usuário

                System.out.println("Digite linha  de 1 a 9");

                int linha = sc.nextInt() - 1;  // Lê a linha onde o valor será inserido

                System.out.println("Digte a coluna de 1 a 9");

                int coluna = sc.nextInt() - 1;  // Lê a coluna onde o valor será inserido


                if (checkingValores(chute, linha, coluna)) {  // Verifica se os valores inseridos são válidos
                    jogar(chute, linha, coluna);  // Realiza a jogada
                }

                if (verficaSeGanhou()) {  // Verifica se o jogador ganhou após a jogada
                    break;  // Sai do loop se o jogador ganhou
                }
            }

            else if (op == 2) {  // Opção 2: Voltar uma jogada
                if (!linhas.isEmpty()) {
                    voltarjogada();  // Volta a última jogada
                } else {
                    System.out.println("Nenhuma jogada disponivel para voltar");
                }

            } else if (op == 3) {  // Opção 3: Apagar uma jogada
                apagarjogada();  // Apaga uma jogada específica
            } else if (op == 4) {  // Opção 4: Pedir uma dica
                if (qtdDica > 0) {
                    pedirDica();  // Fornece uma dica ao jogador
                    qtdDica--;
                } else {
                    System.out.println("VOCE JA ESGOTOU SUAS DICAS");
                }
            } else {
                System.out.println("Digite um valor valido");  // Mensagem de erro para valores inválidos
            }

            if (verficaSeGanhou()) {  // Verifica se o jogador ganhou
                ganhou = 1;
            }

            System.out.println();
            System.out.println("1-Efetuar nova Jogada");
            System.out.println("2-Voltar  Jogada");
            System.out.println("3-Apagar  Jogada");
            System.out.println("4-Dica");
            op = sc.nextInt();  // Lê a próxima opção do usuário

        }
    }

    private static void prencheSudokus() {
        sudokus.add(new int[][]{{0, 0, 0, 0, 0, 9, 7, 4, 3}, {0, 3, 0, 6, 4, 1, 0, 0, 0}, {8, 2, 0, 0, 0, 0, 0, 1, 9}, {0, 5, 0, 3, 9, 0, 0, 6, 7}, {3, 0, 0, 2, 0, 0, 0, 0, 0}, {6, 9, 8, 1, 5, 7, 3, 0, 4}, {0, 0, 0, 7, 3, 8, 0, 0, 1}, {0, 8, 0, 4, 1, 0, 2, 0, 0}, {1, 0, 5, 0, 6, 0, 0, 3, 8}});
        sudokus.add(new int[][]{{5, 1, 6, 8, 2, 9, 7, 4, 3}, {7, 3, 9, 6, 4, 1, 5, 8, 2}, {8, 2, 4, 5, 7, 3, 6, 1, 9}, {2, 5, 1, 3, 9, 4, 8, 6, 7}, {3, 4, 7, 2, 8, 6, 1, 9, 5}, {6, 9, 8, 1, 5, 7, 3, 2, 4}, {4, 6, 2, 7, 3, 8, 9, 5, 1}, {9, 8, 3, 4, 1, 5, 2, 7, 6}, {1, 7, 5, 9, 6, 2, 4, 3, 8}});
        sudokus.add(new int[][]{{0, 1, 0, 9, 0, 3, 4, 2, 0}, {0, 0, 0, 0, 1, 0, 0, 3, 0}, {3, 0, 0, 0, 8, 4, 0, 0, 0}, {6, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 5, 0, 7, 1, 9}, {8, 0, 5, 0, 0, 0, 3, 6, 0}, {0, 3, 0, 0, 0, 0, 0, 8, 0}, {0, 0, 2, 0, 0, 0, 0, 0, 0}, {0, 0, 7, 0, 0, 1, 5, 0, 3}});
        sudokus.add(new int[][]{{5, 1, 8, 9, 6, 3, 4, 2, 7}, {7, 6, 4, 2, 1, 5, 9, 3, 8}, {3, 2, 9, 7, 8, 4, 1, 5, 6}, {6, 7, 1, 3, 9, 2, 8, 4, 5}, {2, 4, 3, 8, 5, 6, 7, 1, 9}, {8, 9, 5, 1, 4, 7, 3, 6, 2}, {1, 3, 6, 5, 7, 9, 2, 8, 4}, {9, 5, 2, 4, 3, 8, 6, 7, 1}, {4, 8, 7, 6, 2, 1, 5, 9, 3}});
        sudokus.add(new int[][]{{0, 2, 0, 0, 3, 0, 4, 9, 0}, {3, 5, 0, 0, 0, 9, 0, 0, 1}, {1, 0, 9, 0, 5, 0, 2, 0, 7}, {7, 4, 2, 0, 0, 0, 6, 5, 9}, {8, 9, 3, 0, 0, 4, 1, 0, 0}, {0, 1, 0, 0, 2, 7, 0, 0, 8}, {2, 3, 0, 7, 0, 0, 9, 8, 0}, {0, 0, 8, 6, 0, 2, 5, 0, 3}, {0, 6, 0, 0, 0, 0, 7, 0, 0}});
        sudokus.add(new int[][]{{6, 2, 7, 1, 3, 8, 4, 9, 5}, {3, 5, 4, 2, 7, 9, 8, 6, 1}, {1, 8, 9, 4, 5, 6, 2, 3, 7}, {7, 4, 2, 3, 8, 1, 6, 5, 9}, {8, 9, 3, 5, 6, 4, 1, 7, 2}, {5, 1, 6, 9, 2, 7, 3, 4, 8}, {2, 3, 1, 7, 4, 5, 9, 8, 6}, {4, 7, 8, 6, 9, 2, 5, 1, 3}, {9, 6, 5, 8, 1, 3, 7, 2, 4}});
        sudokus.add(new int[][]{{0, 8, 0, 5, 2, 0, 0, 0, 0}, {7, 0, 0, 0, 0, 1, 0, 4, 5}, {3, 0, 5, 0, 0, 0, 0, 0, 8}, {0, 1, 0, 2, 0, 0, 6, 0, 9}, {0, 3, 0, 0, 7, 8, 0, 2, 1}, {0, 0, 0, 9, 0, 5, 0, 0, 0}, {0, 4, 8, 7, 0, 0, 0, 1, 6}, {1, 5, 3, 8, 0, 6, 4, 7, 2}, {6, 7, 0, 1, 4, 2, 8, 0, 0}});
        sudokus.add(new int[][]{{4, 8, 1, 5, 2, 9, 3, 6, 7}, {7, 9, 6, 3, 8, 1, 2, 4, 5}, {3, 2, 5, 4, 6, 7, 1, 9, 8}, {5, 1, 7, 2, 3, 4, 6, 8, 9}, {9, 3, 4, 6, 7, 8, 5, 2, 1}, {8, 6, 2, 9, 1, 5, 7, 3, 4}, {2, 4, 8, 7, 5, 3, 9, 1, 6}, {1, 5, 3, 8, 9, 6, 4, 7, 2}, {6, 7, 9, 1, 4, 2, 8, 5, 3}});
        sudokus.add(new int[][]{{0, 5, 0, 0, 9, 3, 0, 0, 6}, {0, 0, 9, 0, 0, 0, 2, 5, 7}, {4, 0, 0, 5, 2, 6, 0, 0, 0}, {7, 0, 1, 0, 0, 0, 0, 0, 2}, {5, 8, 4, 0, 0, 1, 0, 7, 3}, {0, 3, 6, 9, 0, 5, 4, 0, 0}, {9, 0, 0, 6, 0, 7, 0, 8, 4}, {6, 0, 0, 0, 0, 2, 0, 0, 9}, {8, 0, 7, 0, 4, 9, 0, 2, 5}});
        sudokus.add(new int[][]{{1, 5, 2, 7, 9, 3, 8, 4, 6}, {3, 6, 9, 4, 1, 8, 2, 5, 7}, {4, 7, 8, 5, 2, 6, 3, 9, 1}, {7, 9, 1, 8, 3, 4, 5, 6, 2}, {5, 8, 4, 2, 6, 1, 9, 7, 3}, {2, 3, 6, 9, 7, 5, 4, 1, 8}, {9, 2, 3, 6, 5, 7, 1, 8, 4}, {6, 4, 5, 1, 8, 2, 7, 3, 9}, {8, 1, 7, 3, 4, 9, 6, 2, 5}});
        sudokus.add(new int[][]{{4, 5, 1, 7, 0, 9, 6, 0, 0}, {0, 0, 3, 0, 0, 0, 9, 0, 0}, {0, 0, 0, 0, 3, 0, 0, 2, 5}, {0, 7, 8, 0, 0, 0, 0, 6, 0}, {0, 0, 0, 5, 0, 7, 0, 0, 0}, {0, 0, 0, 0, 0, 3, 0, 0, 7}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 4, 0, 3, 0, 0, 7, 0, 0}, {6, 3, 0, 0, 4, 0, 8, 5, 0}});
        sudokus.add(new int[][]{{4, 5, 1, 7, 2, 9, 6, 3, 8}, {2, 6, 3, 4, 5, 8, 9, 7, 1}, {7, 8, 9, 1, 3, 6, 4, 2, 5}, {5, 7, 8, 2, 1, 4, 3, 6, 9}, {3, 1, 6, 5, 9, 7, 2, 8, 4}, {9, 2, 4, 8, 6, 3, 5, 1, 7}, {8, 9, 2, 6, 7, 5, 1, 4, 3}, {1, 4, 5, 3, 8, 2, 7, 9, 6}, {6, 3, 7, 9, 4, 1, 8, 5, 2}});
        sudokus.add(new int[][]{{0, 6, 0, 0, 0, 0, 8, 3, 0}, {7, 0, 2, 0, 0, 0, 0, 0, 0}, {9, 8, 3, 5, 0, 0, 0, 2, 0}, {4, 0, 0, 0, 0, 0, 0, 0, 6}, {0, 0, 0, 0, 0, 0, 5, 0, 0}, {0, 0, 1, 0, 0, 0, 9, 4, 0}, {0, 3, 0, 7, 0, 0, 0, 9, 2}, {2, 7, 0, 0, 0, 0, 3, 0, 1}, {0, 0, 6, 0, 2, 3, 0, 5, 0}});
        sudokus.add(new int[][]{{1, 6, 5, 2, 4, 7, 8, 3, 9}, {7, 4, 2, 3, 9, 8, 1, 6, 5}, {9, 8, 3, 5, 1, 6, 4, 2, 7}, {4, 5, 8, 1, 3, 9, 2, 7, 6}, {3, 9, 7, 4, 6, 2, 5, 1, 8}, {6, 2, 1, 8, 7, 5, 9, 4, 3}, {5, 3, 4, 7, 8, 1, 6, 9, 2}, {2, 7, 9, 6, 5, 4, 3, 8, 1}, {8, 1, 6, 9, 2, 3, 7, 5, 4}});
        sudokus.add(new int[][]{{0, 0, 0, 0, 5, 7, 8, 4, 9}, {0, 5, 0, 0, 0, 9, 0, 0, 7}, {0, 9, 0, 0, 3, 0, 5, 6, 0}, {0, 0, 0, 1, 0, 0, 4, 7, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 2, 0, 0, 0}, {0, 0, 0, 0, 6, 3, 0, 5, 8}, {0, 0, 0, 0, 2, 0, 3, 1, 0}, {3, 0, 0, 0, 0, 0, 0, 2, 0}});
        sudokus.add(new int[][]{{2, 1, 3, 6, 5, 7, 8, 4, 9}, {6, 5, 8, 2, 4, 9, 1, 3, 7}, {4, 9, 7, 8, 3, 1, 5, 6, 2}, {9, 3, 2, 1, 8, 6, 4, 7, 5}, {1, 8, 6, 5, 7, 4, 2, 9, 3}, {5, 7, 4, 3, 9, 2, 6, 8, 1}, {7, 2, 1, 4, 6, 3, 9, 5, 8}, {8, 4, 9, 7, 2, 5, 3, 1, 6}, {3, 6, 5, 9, 1, 8, 7, 2, 4}});
        sudokus.add(new int[][]{{0, 1, 0, 3, 0, 0, 0, 0, 6}, {4, 0, 0, 0, 6, 1, 0, 0, 8}, {0, 0, 0, 0, 0, 8, 0, 0, 1}, {3, 0, 0, 1, 0, 6, 0, 2, 4}, {0, 4, 1, 0, 0, 9, 3, 6, 0}, {0, 0, 0, 4, 8, 0, 1, 9, 0}, {9, 0, 4, 0, 1, 0, 0, 0, 3}, {0, 8, 0, 0, 3, 0, 0, 0, 2}, {0, 6, 0, 0, 0, 0, 7, 0, 0}});
        sudokus.add(new int[][]{{5, 1, 8, 3, 9, 2, 4, 7, 6}, {4, 3, 2, 7, 6, 1, 9, 5, 8}, {6, 9, 7, 5, 4, 8, 2, 3, 1}, {3, 7, 9, 1, 5, 6, 8, 2, 4}, {8, 4, 1, 2, 7, 9, 3, 6, 5}, {2, 5, 6, 4, 8, 3, 1, 9, 7}, {9, 2, 4, 6, 1, 7, 5, 8, 3}, {7, 8, 5, 9, 3, 4, 6, 1, 2}, {1, 6, 3, 8, 2, 5, 7, 4, 9}});
        sudokus.add(new int[][]{{0, 6, 0, 0, 0, 0, 0, 0, 0}, {7, 0, 3, 0, 2, 0, 0, 5, 8}, {0, 0, 8, 0, 0, 0, 4, 0, 0}, {0, 0, 0, 0, 0, 0, 6, 7, 0}, {2, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 6, 0, 0, 0}, {6, 0, 0, 0, 5, 0, 0, 0, 0}, {3, 0, 7, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 6, 0}});
        sudokus.add(new int[][]{{5, 6, 1, 4, 3, 8, 7, 2, 9}, {7, 4, 3, 6, 2, 9, 1, 5, 8}, {9, 2, 8, 5, 1, 7, 4, 3, 6}, {4, 3, 5, 9, 8, 1, 6, 7, 2}, {2, 7, 6, 3, 4, 5, 8, 9, 1}, {1, 8, 9, 2, 7, 6, 3, 4, 5}, {6, 1, 2, 7, 5, 4, 9, 8, 3}, {3, 9, 7, 8, 6, 2, 5, 1, 4}, {8, 5, 4, 1, 9, 3, 2, 6, 7}});
        sudokus.add(new int[][]{{6, 0, 0, 0, 0, 0, 0, 2, 5}, {5, 9, 0, 0, 0, 3, 0, 0, 8}, {0, 0, 0, 0, 0, 8, 0, 0, 0}, {0, 0, 0, 0, 0, 5, 0, 0, 0}, {0, 0, 0, 0, 6, 2, 0, 0, 0}, {0, 1, 0, 0, 0, 4, 0, 0, 2}, {0, 4, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 9, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 4, 9, 0, 0, 0}});
        sudokus.add(new int[][]{{6, 3, 8, 4, 7, 1, 9, 2, 5}, {5, 9, 1, 6, 2, 3, 7, 4, 8}, {4, 2, 7, 5, 9, 8, 1, 3, 6}, {2, 6, 4, 8, 1, 5, 3, 9, 7}, {8, 7, 3, 9, 6, 2, 4, 5, 1}, {9, 1, 5, 7, 3, 4, 6, 8, 2}, {3, 4, 2, 1, 5, 7, 8, 6, 9}, {7, 5, 9, 3, 8, 6, 2, 1, 4}, {1, 8, 6, 2, 4, 9, 5, 7, 3}});
        sudokus.add(new int[][]{{0, 0, 0, 4, 0, 0, 7, 0, 0}, {0, 7, 0, 0, 0, 0, 6, 0, 8}, {0, 0, 0, 1, 0, 0, 0, 4, 0}, {5, 6, 0, 0, 0, 4, 8, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 7, 0}, {0, 0, 0, 0, 6, 0, 1, 0, 0}, {0, 2, 6, 8, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 2, 7, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 2, 0, 0}});
        sudokus.add(new int[][]{{8, 9, 3, 4, 5, 6, 7, 1, 2}, {1, 7, 4, 2, 3, 9, 6, 5, 8}, {6, 5, 2, 1, 7, 8, 3, 4, 9}, {5, 6, 7, 9, 1, 4, 8, 2, 3}, {9, 3, 1, 5, 8, 2, 4, 7, 6}, {2, 4, 8, 7, 6, 3, 1, 9, 5}, {7, 2, 6, 8, 4, 5, 9, 3, 1}, {3, 1, 9, 6, 2, 7, 5, 8, 4}, {4, 8, 5, 3, 9, 1, 2, 6, 7}});
    }

    private static void obtemSudoku(String dificuldade) {  // Método que obtém o Sudoku de acordo com a dificuldade escolhida
        int x = 0;
        Random rd = new Random();  // Inicializa o gerador de números aleatórios

        if (dificuldade.equalsIgnoreCase("facil")) {  // Caso a dificuldade seja "fácil"
            System.out.println("xxxxxxxxxx");
            qtdDica = 5;  // Define o número de dicas para 5
            x = rd.nextInt(5) * 2;  // Escolhe um índice aleatório
        } else if (dificuldade.equalsIgnoreCase("medio")) {  // Caso a dificuldade seja "médio"
            x = rd.nextInt(5, 9) * 2;  // Escolhe um índice aleatório
            qtdDica = 3;  // Define o número de dicas para 3
        } else if (dificuldade.equalsIgnoreCase("dificil")) {  // Caso a dificuldade seja "difícil"
            x = rd.nextInt(9, 12) * 2;  // Escolhe um índice aleatório
            qtdDica = 2;  // Define o número de dicas para 2
        }

        matrix = sudokus.get(x);  // Obtém o tabuleiro de Sudoku
        solucaoMatrix = sudokus.get(x + 1);  // Obtém a solução do Sudoku
    }

    private static boolean checkingValores(int chute, int linha, int coluna) {  // Método que verifica se os valores inseridos são válidos
        if (linha < 0 || linha > 9) {
            System.out.println("Valor da linha incorreto");
            return false;
        }
        if (coluna < 0 || coluna > 9) {
            System.out.println("Valor da coluna incorreto");
            return false;
        }
        if (chute < 0 || chute > 9) {
            System.out.println("Valor do chute incorreto");
            return false;
        }
        return true;
    }

    public static void printMatriz(int m[][]) {  // Método que imprime a matriz do Sudoku
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(m[i][j] + " ");
                if (j == 2 || j == 5) {
                    System.out.print("| ");
                }
            }
            if (i == 2 || i == 5) {
                System.out.println();
                System.out.print("---------------------");
            }
            System.out.println();
        }
    }

    public static void pedirDica() {  // Método que fornece uma dica ao jogador
        System.out.println("Digite a linha de 1 a 9");
        int linha = sc.nextInt() - 1;
        System.out.println("Digite a coluna  de 1 a 9");
        int coluna = sc.nextInt() - 1;
        int valor = solucaoMatrix[linha][coluna];  // Obtém o valor correto da solução
        matrix[linha][coluna] = valor;  // Atualiza a matriz do Sudoku
        printMatriz(matrix);  // Imprime a matriz atualizada
    }

    public static void voltarjogada() {  // Método que permite voltar a última jogada
        System.out.println("Voce pode voltar " + linhas.size() + " jogada(s)");
        System.out.println();
        Scanner sc = new Scanner(System.in);
        int x = 1;
        while (!linhas.isEmpty()) {
            System.out.println("Digite 1  para voltar uma jogada e 2 para voltar a jogar");
            x = sc.nextInt();
            if (x == 1) {
                int lastRow = linhas.size() - 1;
                int lastColumn = colunas.size() - 1;
                matrix[linhas.get(lastRow)][colunas.get(lastColumn)] = 0;  // Remove o valor da última jogada
                linhas.remove(lastRow);  // Remove a última linha da lista
                colunas.remove(lastColumn);  // Remove a última coluna da lista
                printMatriz(matrix);  // Imprime a matriz atualizada
            } else {
                return;  // Sai do método se o usuário escolher voltar a jogar
            }
        }
    }

    public static void apagarjogada() {  // Método que apaga uma jogada específica
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite a linha de 1 a 9");
        int linha = sc.nextInt() - 1;
        System.out.println("Digite a coluna  de 1 a 9");
        int coluna = sc.nextInt() - 1;
        if (matrix[linha][coluna] != 0) {
            matrix[linha][coluna] = 0;  // Apaga o valor da matriz
            printMatriz(matrix);  // Imprime a matriz atualizada
        } else {
            System.out.println("Posiçoes Invalidas!!");
        }
    }

    public static void jogar(int chute, int linha, int coluna) {  // Método que realiza uma jogada
        if (verficaSeEstaVazio(linha, coluna)) {  // Verifica se a posição está vazia
            if (!verificaAcerto(chute, linha, coluna)) {  // Verifica se a jogada é válida
                return;  // Sai do método se a jogada for inválida
            }
            matrix[linha][coluna] = chute;  // Atualiza a matriz com o valor inserido
            linhas.add(linha);  // Adiciona a linha à lista de jogadas
            colunas.add(coluna);  // Adiciona a coluna à lista de jogadas
            System.out.println("Bela jogada");
            printMatriz(matrix);  // Imprime a matriz atualizada
        }
    }

    public static boolean verificaAcerto(int chute, int linha, int coluna) {
        // Verifica se o chute é válido na linha e coluna especificadas
        boolean colunaLinhaValida = verificaColunaLinha(chute, linha, coluna);

        // Verifica se o chute é válido no bloco 3x3
        boolean escopoValido = verificaMesmoEscopo(linha, coluna, chute);

        // Retorna true se ambas as verificações forem válidas
        return colunaLinhaValida && escopoValido;
    }

    public static boolean verificaColunaLinha(int chute, int linha, int coluna) {  // Método que verifica a validade na linha e coluna
        // Verifica se o chute já existe na linha especificada
        for (int j = 0; j < 9; j++) {
            int valor = matrix[linha][j];  // Obtém o valor atual na linha e coluna j
            if (valor == chute) {  // Se o valor for igual ao chute
                System.out.println("ERROU!!: A linha " + (linha + 1) + " já possui o número " + chute);
                return false;  // Retorna falso se o chute já estiver presente na linha
            }
        }
        // Verifica se o chute já existe na coluna especificada
        for (int j = 0; j < 9; j++) {
            int valor = matrix[j][coluna];  // Obtém o valor atual na coluna e linha j
            if (valor == chute) {  // Se o valor for igual ao chute
                System.out.println("ERROU!!: A coluna " + (coluna + 1) + " já possui o número " + chute);
                return false;  // Retorna falso se o chute já estiver presente na coluna
            }
        }

        return true;  // Retorna verdadeiro se o chute não estiver presente na linha e coluna
    }

    public static boolean verficaSeEstaVazio(int linha, int coluna) {  // Método que verifica se a posição está vazia
        if (matrix[linha][coluna] == 0) {
            return true;
        }
        System.out.println("Tente novamente: Posição Invalida");
        return false;
    }

    public static boolean verificaMesmoEscopo(int linha, int coluna, int chute) {  // Método que verifica a validade no bloco 3x3
        // Calcula o início do bloco 3x3
        int blocoInicialLinha = (linha / 3) * 3;  // Determina a linha inicial do bloco 3x3
        int blocoInicialColuna = (coluna / 3) * 3;  // Determina a coluna inicial do bloco 3x3

        // Verifica se o chute já existe no bloco 3x3
        for (int i = blocoInicialLinha; i < blocoInicialLinha + 3; i++) {
            for (int j = blocoInicialColuna; j < blocoInicialColuna + 3; j++) {
                if (matrix[i][j] == chute) {  // Se o valor no bloco for igual ao chute
                    System.out.println("ERROU!!: Já existe o número " + chute + " nesse escopo");
                    return false;  // Retorna falso se o chute já estiver presente no bloco 3x3
                }
            }
        }

        return true;  // Retorna verdadeiro se o chute não estiver presente no bloco 3x3
    }

    public static boolean verficaSeGanhou() {  // Método que verifica se o jogador ganhou o jogo
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j] == 0) {
                    return false;
                }
            }
        }
        var menu = """
            ⣿⣿⣿⣿⠿⠛⠛⠛⠛⠻⠟⠛⠛⢻⣿⣿⣿⣿⣿⠿⠛⠛⠛⠛⠿⠟⠛⠛⣿⣿
            ⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿
            ⣿⡟⠀⠀⠀⠀⢠⣶⣷⣦⠀⠀⠀⢸⣿⣿⡏⠀⠀⠀⠀⣰⣾⣷⣄⠀⠀⠀⣿⣿
            ⣿⠇⠀⠀⠀⠀⣾⣿⣿⣿⡆⠀⠀⢸⣿⣿⠀⠀⠀⠀⠀⣿⣿⣿⣿⠀⠀⠀⣿⣿
            ⣿⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀ ⣿⣿⣿⣿⣿⣿⣿⣿⣿
            ⣿⠀⠀⠀⠀⠀⣿⣿⡟⠛⠛⠛⠛⠛⣿⣿⠀⠀⠀⠀ ⣿⣿⠛⠛⠛⠛⠛⠛⣿
            ⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀ ⣿⣿⠀⠀⠀⠀⠀⠀⣿
            ⣿⡀⠀⠀⠀⠀⣿⣿⣿⣿⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⣿⡇⠀⠀⠀⠀⣿
            ⣿⣇⠀⠀⠀⠀⢹⣿⣿⠏⠀⠀⠀⠀⣿⣿⡄⠀⠀⠀⠀⢿⣿⣿⠃⠀⠀⠀⠀⣿
            ⣿⣿⣆⠀⠀⠀⠀⠈⠁⠀⠀⠀⠀⠀⣿⣿⣿⡄⠀⠀⠀⠀⠉⠀⠀⠀⠀⠀⠀⣿
            ⣿⣿⣿⣷⣤⣀⣀⠀⢀⣀⣠⣦⣀⣀⣿⣿⣿⣿⣶⣄⣀⣀⠀⢀⣀⣤⣆⣀⣀⣿
        """;
        System.out.println(menu);  // Imprime a mensagem de vitória
        return true;
    }
}
