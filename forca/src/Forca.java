import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Forca {
    // Variáveis estáticas para armazenar a palavra escolhida, a categoria, o contador de erros e o Scanner para entrada do usuário
    private static String palavraEscolhida;
    private static StringBuilder stringBuilder;
    private static String categoria;
    private static Scanner sc = new Scanner(System.in);
    private static int erro;



    // Método principal do jogo que controla a lógica do jogo da forca
    public  void jogo() {
        // Cria uma lista de caracteres para armazenar as letras que o jogador já tentou adivinhar
        List<Character> letras = new ArrayList<>();

        // Chama o método menu() para exibir o menu inicial do jogo e permitir que o jogador escolha o nível de dificuldade
        menu();

        // Lê a opção de dificuldade escolhida pelo jogador
        int op = sc.nextInt();

        // Consome a nova linha após a entrada do número inteiro, para evitar problemas de leitura de strings subsequentes
        sc.nextLine();

        // Gera uma palavra aleatória com base na opção de dificuldade escolhida pelo jogador
        geraPalavra(op);

        // Exibe o tema/categoria da palavra escolhida
        imprimirTema();

        // Imprime a palavra oculta no início do jogo, substituindo as letras por underscores
        System.out.println();
        System.out.println("                                                                    " + imprimirPalavraInicio());

        // Inicia um loop enquanto o número de erros for menor que 6, ou seja, enquanto o jogador não exceder o limite de erros permitidos
        while (erro < 6) {
            // Solicita que o jogador insira um palpite e lê a entrada do usuário
            System.out.println("                                                                   DIGITE SEU CHUTE");
            System.out.println();
            String chute = sc.nextLine();

            // Verifica se o palpite do jogador é válido, ou seja, se não foi tentado anteriormente
            if (verifcaValores(letras, chute.charAt(0))) {
                // Adiciona a letra ao conjunto de letras tentadas
                letras.add(chute.charAt(0));

                // Imprime a palavra atualizada com base no palpite do jogador
                imprimirPalavra(chute.charAt(0));

                // Imprime a palavra atualizada
                System.out.println("                                                                       " + stringBuilder);

                // Exibe o boneco da forca com base no número de erros
                exibirBoneco(erro);

                // Verifica se o jogador ganhou o jogo (adivinhou todas as letras da palavra) e encerra o jogo se sim
                if (verifcaGanhou(stringBuilder.toString())) {
                    ganhou();
                    break;
                }

                // Verifica se o jogador excedeu o número máximo de erros permitidos e encerra o jogo se sim
                if (!verifcaGanhou(stringBuilder.toString()) && erro == 6) {
                    break;
                }

                // Solicita que o jogador decida se quer tentar adivinhar a palavra inteira ou continuar adivinhando letras
                System.out.println("                                                                  PARA CHUTAR A PALAVRA DIGITE S / OU N PARA CONTINUAR");

                // Permite que o jogador insira a palavra inteira e verifica se ela está correta, encerrando o jogo de acordo com o resultado
                if (sc.nextLine().equalsIgnoreCase("s")) {
                    System.out.println("                                                                            DIGITE A PALAVRA");
                    if (verifcaGanhou(sc.nextLine())) {
                        ganhou();
                        break;
                    } else {
                        perdeu();
                        break;
                    }
                }
            }
        }

        // Verifica se o jogador excedeu o número máximo de erros permitidos e encerra o jogo se sim
        if (erro >= 6) {
            perdeu();
        }
    }

    public static void geraPalavra(int nivel) {
        // Método para gerar uma palavra aleatória com base no nível de dificuldade escolhido

        Random random = new Random(); // Inicializa um objeto da classe Random para gerar números aleatórios
        int x, y; // Declaração das variáveis para os limites do intervalo de seleção de palavras

        // Definição dos intervalos de seleção de palavras baseado no nível de dificuldade
        if (nivel == 1) {
            x = 0;
            y = 5;
        } else if (nivel == 2) {
            x = 5;
            y = 10;
        } else {
            x = 10;
            y = 15;
        }

        // Vetor de vetores de strings para armazenar as categorias e suas palavras correspondentes
        String[][] listaCategorias = {
                {"Titanic", "Inception", "The Godfather", "The Dark Knight", "Forrest Gump", "Pulp Fiction", "The Shawshank Redemption", "Shiva Baby", "Star Wars", "Jurassic Park", "The Matrix", "Avengers: Endgame", "Gladiator", "Fight Club", "Interstellar", "Filme"},
                {"Breaking Bad", "Game of Thrones", "Stranger Things", "Friends", "The Office", "The Simpsons", "Sherlock", "The Crown", "Westworld", "House of Cards", "Black Mirror", "The Mandalorian", "The Witcher", "Animais Políticos", "True Detective", "Serie"},
                {"Cachorro", "Gato", "Elefante", "Tigre", "Leão", "Girafa", "Panda", "Urso", "Cavalo", "Coelho", "Rinoceronte", "Gorila", "Delfim", "Canguru", "Foca", "Animal"},
                {"Brasil", "Argentina", "Canadá", "Japão", "Alemanha", "Holanda", "Bélgica", "Luxemburgo", "Dinamarca", "Suécia", "Burquina Faso", "Brunei", "Comores", "Kiribati", "Lesoto", "País"},
                {"Médico", "Engenheiro", "Professor", "Advogado", "Arquiteto", "Astronauta", "Biólogo Marinho", "Engenheiro Aeroespacial", "geólogo", "Engenheiro de Software", "Anestesiologista", " Sereia profissional", "Neurocientista", "Luto de aluguel", "Estatístico Quantitativo", "Profissao"},
                {"Maçã", "Banana", "Laranja", "Morango", "Abacaxi", "Cereja", "Pêssego", "Abacate", "Kiwi", "Pitaya", "Cherimoia", "Granadilha", "Lichia", "Longan", "Mangostão", "Fruta"},
                {"Futebol", "Basquete", "Tênis", "Vôlei", "Natação", "Atletismo", "Boxe", "Ciclismo", "Ginástica", "Hóquei", "Rugby", "Skate", "Surfe", "Golfe", "Beisebol", "Esporte"},
                {"Toyota", "Honda", "Ford", "Chevrolet", "BMW", "Mercedes-Benz", "Audi", "Volkswagen", "Porsche", "Ferrari", "Lamborghini", "Tesla", "Nissan", "Hyundai", "Kia", "MARCA DE CARRO"},
                {"Mickey Mouse", "Bob Esponja", "Homer Simpson", "Naruto", "Goku", "Bart Simpson", "Pikachu", "Snoopy", "Tom", "Jerry", "Scooby-Doo", "Pateta", "Popeye", "Shrek", "Elsa", "Personagem"},
                {"The Beatles", "Queen", "Nirvana", "Metallica", "U2", "Legião Urbana", "Led Zeppelin", "Pink Floyd", "Titãs", "Coldplay", "Skank", "The Doors", "Radiohead", "Green Day", "The Who", "Banda Musical"}
        };

        // Seleção aleatória da categoria e do elemento dentro do intervalo definido
        int numberCateogiria = random.nextInt(10); // Gera um número aleatório entre 0 e 9 (inclusivo)
        int numberElemento = random.nextInt(x, y); // Gera um número aleatório dentro do intervalo [x, y)

        // Definindo a categoria e a palavra escolhida
        categoria = listaCategorias[numberCateogiria][15]; // A categoria é sempre o último elemento do array
        palavraEscolhida = listaCategorias[numberCateogiria][numberElemento];
    }


    public static void menu() {

        // Método para exibir o menu inicial do jogo
        var menu ="""   
                                                                                  SEJA BEM VINDO AO JOGO DA
                                                                                                             
                            
                                                               ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣾⡿⠿⠿⠟⣴⣿⠿⠿⣿⣆⠀⣿⣿⠿⠿⣿⡆⢀⣾⡿⠿⠿⣿⠆⠀⣠⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                               ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣤⣤⣄⢸⣿⡇⠀⠀⣿⡟⢠⣿⣧⣤⣴⣿⠇⣼⣿⠃⠀⠀⠀⠀⢠⣿⡏⢸⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                               ⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⡟⠛⠛⠋⣿⣿⠀⠀⣸⣿⠃⣸⣿⠟⠛⣿⣧⠀⣿⡟⠀⠀   ⢠⣿⣿⣶⣾⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                               ⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠃⠀⠀⠀⠹⣿⣷⣾⡿⠏⠀⣿⡿⠀⠀⣿⣿⠀⠻⣿⣷⣾⡿⠃⣾⡿⠀⠀⠸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                               
                                                                                    DESEJA JOGAR O MODO
                                                                                                                                     
                                                                                      1 - FACIL
                                                                                      2 - MEDIO
                                                                                      3 - DIFÍCIL   ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                                                                                    
                                                                                                                                                                                
                """;

        System.out.println(menu);
    }

    public static void imprimirTema() {
        // Método para imprimir o tema/categoria da palavra escolhida
        System.out.println("                                                                    TEMA: " + categoria);
        System.out.println("                                                           **************BOA SORTEE**************");
    }

    private static void exibirBoneco(int erro) {
        // Método para exibir o boneco da forca com base na quantidade de erros
        // (erro >= 1 ? "(_)" : "")) esse ? funciona como um if  ex: (condição) : s : x  dentro do parênteses é a condição se ela estiver correta
        //o codigo é execultado é o s caso contrario é o x;
        System.out.println("  _______");
        System.out.println(" |/      |");
        System.out.println(" |      " + ((erro >= 1) ? "(_)" : ""));
        System.out.println(" |      " + ((erro >= 2) ? "/" : "") + ((erro >= 3 )? "|" : "") + ((erro >= 4) ? "\\" : ""));
        System.out.println(" |      " + ((erro >= 5) ? "/" : "") + " " + ((erro >= 6 )? "\\" : ""));
        System.out.println(" |");
        System.out.println("_|___");
        System.out.println();
    }

    // Método para imprimir a palavra com base no chute do usuário
    public static void imprimirPalavra(char chute) {
        // Variáveis para contar o número de elementos e acertos
        int qtdElementos = 0;
        int qtdAcertos = 0;

        // Percorre cada caractere da palavra escolhida
        for (int i = 0; i < palavraEscolhida.length(); i++) {
            // Verifica se o caractere não é um espaço em branco
            if (palavraEscolhida.charAt(i) != ' ') {
                // Verifica se o caractere atual é igual ao chute do usuário (ignorando diferenças entre maiúsculas e minúsculas)
                if (palavraEscolhida.toLowerCase().charAt(i) == Character.toLowerCase(chute)) {
                    // Se houver correspondência, atualiza a letra na posição correspondente no StringBuilder
                    stringBuilder.setCharAt(qtdElementos + i, palavraEscolhida.charAt(i));
                    // Incrementa o número de acertos
                    qtdAcertos++;
                }
                // Incrementa o contador de elementos não espaços
                qtdElementos++;
            }
        }
        // Se houve pelo menos um acerto, imprime "Bom chute!!!", caso contrário, incrementa o contador de erros e imprime "ERROU!!!"
        if (qtdAcertos > 0) {
            System.out.println("Bom chute!!!");
        } else {
            erro++;
            System.out.println("ERROU!!!");
        }
    }


    // Método para imprimir a palavra com underscores no início do jogo
    public static String imprimirPalavraInicio() {
        //Stringbuilder é um string que pode ser modificada;
        // Inicializa um StringBuilder para construir a representação da palavra com underscores
        stringBuilder = new StringBuilder();

        // Percorre cada caractere da palavra escolhida
        for (int i = 0; i < palavraEscolhida.length(); i++) {
            // Verifica se o caractere atual é um espaço em branco
            if (palavraEscolhida.charAt(i) == ' ') {
                // Se for um espaço, adiciona um espaço em branco ao StringBuilder
                stringBuilder.append(' ');
            } else {
                // Se não for um espaço, adiciona um underscore para ocultar a letra e um espaço em branco para separar
                stringBuilder.append('_');
                stringBuilder.append(' ');
            }
        }
        // Retorna a representação da palavra com underscores como uma string
        return stringBuilder.toString();
    }

    // Método para remover espaços de uma string
    private static String retiraEspaco(String str) {
        StringBuilder strr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                strr.append(str.charAt(i));
            }
        }
        return strr.toString();
    }

    // Método para verificar se o jogador ganhou
    private static boolean verifcaGanhou(String str) {

        return palavraEscolhida.equalsIgnoreCase(retiraEspaco(str));
    }

    // Método para verificar se o jogador já chutou determinada letra
    private static boolean verifcaValores(List<Character> list, char chute) {
        for (int i = 0; i < list.size(); i++) {
            if (Character.toLowerCase(list.get(i)) == Character.toLowerCase(chute)) {
                System.out.println("VOCE JA CHUTOU " + chute);
                return false;
            }
        }
        return true;
    }







    // Método para exibir mensagem de vitória
    public static void ganhou() {

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
                 """;;
        System.out.println(menu);
    }

    // Método para exibir mensagem de derrota
    public static void perdeu() {
        var menu = """
                            
                                                                               ⠀⠀⠀⠀⣠⣤⣶⠾⠿⠟⠿⠷⢶⣤⣄⠀⠀⠀⠀⠀
                                                                               ⠀⠀⢀⡼⠛⠁⠀⠀⠀⠀⠀⠀⠀⠈⠙⠿⣦⡀⠀⠀
                                                                               ⠀⣰⠋⠀⠀⠀⢰⣿⣆⠀⢀⣾⣷⠀⠀⠀⠈⢿⣄⠀
                                                                               ⣰⠃⠀⠀⠀⠀⠸⣿⠏⠀⠈⢿⡿⠀⠀⠀⠀⠀⢿⡄
                                                                               ⡟⠀⠀⠀⠀⠀⢰⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣧
                                                                               ⣷⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀⠄⠀⠀⠀⠀⠀⠀⢀⡟
                                                                               ⢻⡄⠀⠀⠀⠐⠀⠀⠀⠀⠀⠀⠀⠑⠀⠀⠀⠀⣸⠃
                                                                               ⠈⢿⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⠋⠀
                                                                               ⠀⠀⠙⢷⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⠞⠁⠀⠀
                                                                               ⠀⠀⠀⠀⠙⠻⠶⣦⣤⣤⣤⡴⠶⠛⠋⠀⠀⠀⠀⠀
               
                                                   ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                                                   ⣿⣿⣿⠿⠉⣠⣤⣤⣤⣿⠟⢁⣠⣀⠉⠿⡇⠀⠉⠿⠁⠀⢸⠀⠀⣤⣤⣤⣤⣿⣿⣿⣿⠉⠀⣤⣤⠀⠈⣿⠀⢸⣿⣿⠀⢸⡇⠀⣤⣤⣤⣤⣾⠀⠀⣤⣤⡄⠉⣿
                                                   ⣿⣿⣿⠀⠀⣿⣏⡉⠉⣿⠀⠘⠛⠛⠀⠀⡇⠀⣀⠀⡀⠀⢸⠀⠀⣉⣉⣉⣽⣿⣿⣿⣿⠀⠀⣿⣿⠀⠀⣿⡀⠈⠙⠉⠀⣸⡇⠀⣉⣉⣉⣹⣿⠀⠀⠿⠏⢁⣀⣿
                                                   ⣿⣿⣿⣿⣤⠘⠛⠃⠀⣿⠀⢸⣿⣿⠀⢀⡇⠀⣿⣿⡇⠀⢸⠀⠀⠛⠛⠛⠛⣿⣿⣿⣿⣤⠀⠛⠛⠀⣠⣿⣿⣧⡄⣤⣿⣿⡇⠀⠛⠛⠛⠛⢻⠀⠀⣧⡄⠀⠛⣿
                                                   ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿ 
                                                     
                """;;
        System.out.println(menu);
    }
}
