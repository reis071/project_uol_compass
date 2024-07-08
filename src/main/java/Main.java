import entities.produtos.Roupa;
import enums.Sexo;
import enums.Tamanho;
import repositories.RoupaRepository;

import  javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /* Iniciar todas entidades*/
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banco");
        EntityManager em = emf.createEntityManager();
        RoupaRepository roupaRepository = new RoupaRepository(em);
        Scanner input = new Scanner(System.in);
        /* Menu*/
        boolean exit = true;
        while (exit){
            System.out.println("Menu");
            System.out.println("1-DOAR");
            System.out.println("6-ENCERRAR");
            int opcao = input.nextInt();
            switch (opcao) {
                case 1:
                    System.out.print("Digite a DESCRICAO da roupa:");
                    String descricao = input.next();

                    Tamanho tamanho = null;
                    while (true) {
                        for (Tamanho valorTamanho : Tamanho.values()) {
                            System.out.println(valorTamanho + " - " + valorTamanho.getValue());
                        }
                        System.out.print("Selecione o TAMANHO da roupa: ");
                        int opcaoTamanho = input.nextInt();
                        if (opcaoTamanho > 6 || opcaoTamanho < 1) {
                            System.out.println("TAMANHO INVÁLIDO");
                        } else {
                            tamanho = Tamanho.fromValue(opcaoTamanho);
                            break; // Adiciona o break para sair do loop
                        }
                    }

                    Sexo sexo = null;
                    while (sexo == null) {
                        for (Sexo valorSexo : Sexo.values()) {
                            System.out.println(valorSexo + " - " + valorSexo.getValue());
                        }
                        System.out.print("Selecione o SEXO da roupa: ");
                        int opcaoSexo = input.nextInt();
                        if (opcaoSexo < 0 || opcaoSexo > 1) {
                            System.out.println("SEXO INVÁLIDO");
                        } else {
                            sexo = Sexo.fromValue(opcaoSexo);
                        }
                    }

                    System.out.print("Digite a QUANTIDADE de roupa: ");
                    int quantidade = input.nextInt();

                    Roupa roupa = new Roupa(descricao, quantidade, sexo, tamanho);
                    roupaRepository.save(roupa);
                    break;
                case 6:
                    emf.close();
                    exit = false;
            }
        }


    }
}
