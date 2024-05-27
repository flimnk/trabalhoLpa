import java.util.Scanner;
public class JogoDaSenha {
    int tamanhoSenha = 4;
    int numTentativas = 8;
    String senha = "4568";
    boolean senhaAcertada = false;
    String senhaDigitada;
    Scanner sc = new Scanner(System.in);
    String teste = "teste";
    public void Jogar()  {
        do {
            System.out.println("Digite a senha: ");
            senhaDigitada = sc.next();
            if (senha.equalsIgnoreCase(senhaDigitada)) {
                System.out.println("Senha correta!");
                senhaAcertada = true;
                break;
            } else {
                System.out.println("Senha incorreta, tente mais uma vez");
            }
            numTentativas--;
        } while (numTentativas > 0);
        if (senhaAcertada == false) {
            System.out.println("Infelizmente você não conseguiu acertar a senha!");
        }
    }
}