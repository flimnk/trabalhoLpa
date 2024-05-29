import java.util.Random;
import java.util.Scanner;

public class JogoDaSenha {


    public void jogar() {
        Random random = new Random();
        int[] senha = new int[4];
        int numTentativas = 10;
        int numerosNaPosicaoCorreta;
        int numerosNaPosicaoErrada;
        boolean senhaAcertada;
        String senhaDigitada;
        Scanner sc = new Scanner(System.in);

        // Gerar a senha aleatória
        for (int i = 0; i < 4; i++) {
            senha[i] = random.nextInt(6) + 1;
        }

        System.out.println("Bem-vindo ao Jogo da Senha!");

        // Loop principal do jogo
        do {
            // Solicitar a senha do jogador
            System.out.println("Digite a sua tentativa de senha (4 dígitos de 1 a 6):");
            senhaDigitada = sc.next();

            // Verificar se a senha digitada tem 4 dígitos
            if (senhaDigitada.length() != 4) {
                System.out.println("A senha deve conter exatamente 4 dígitos. Tente novamente.");
                continue; // Pula para a próxima iteração do loop
            }

            numerosNaPosicaoCorreta = 0;
            numerosNaPosicaoErrada = 0;
            senhaAcertada = true;

            // Verificar a senha digitada
            for (int i = 0; i < 4; i++) {
                int digitoDigitado = senhaDigitada.charAt(i) - '0';
                if (senha[i] == digitoDigitado) {
                    numerosNaPosicaoCorreta++;
                } else if (senhaDigitada.contains(String.valueOf(senha[i]))) {
                    numerosNaPosicaoErrada++;
                }
            }

            // Verificar se a senha está correta
            if (numerosNaPosicaoCorreta == 4) {
                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║    🎉 Senha correta! Parabéns! 🎉    ║");
                System.out.println("╚══════════════════════════════════════╝");
                senhaAcertada = true;
                break;
            } else {
                // Exibir feedback para o jogador
                System.out.println("------------------------------------");
                System.out.println("Senha incorreta!");
                System.out.println("Dígitos corretos na posição certa: " + numerosNaPosicaoCorreta);
                System.out.println("Dígitos corretos na posição errada: " + numerosNaPosicaoErrada);
                System.out.println();
                numTentativas--;
                System.out.println("Ainda te restam " + numTentativas + " tentativas");
            }
        } while (numTentativas > 0);

        // Mensagem de derrota se o jogador esgotar todas as tentativas
        if (numTentativas == 0) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║  ❌ Senha incorreta! Derrota! ❌       ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("         Suas tentativas acabaram!      ");
            System.out.println("         Infelizmente, não foi          ");
            System.out.println("         dessa vez.                     ");
        }
    }
}
