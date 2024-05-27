import java.util.Scanner;

public class teste {
    public static void main(String[] args) {
        menu();
    }


    public static void menu() {
        Sudoko sd = new Sudoko();
        Forca fc = new Forca();
        var menu = """                                   
                                                                       
                                                                  Digite o numero de acordo com  jogo que deseja
                                                                               1 - Sudoku
                                                                               2 - Forca
                                                                               3 - Senha       
                                                                               4 - Sair                                      
                                                   
                """;
        System.out.println(menu);
        while (true) {
            Scanner sc = new Scanner(System.in);
            int op = sc.nextInt();
            if (op == 1) {
                sd.jogo();
            } else if (op == 2) {
                fc.jogo();
            } else if (op == 3) {
                JogoDaSenha js = new JogoDaSenha();
                js.Jogar();
            } else {
                return;
            }


        }
    }
}
