import entities.produtos.Higiene;
import enums.HigieneEnum;
import repositories.HigieneRepository;
import repositories.RoupaRepository;
import enums.Sexo;
import enums.RoupaTamanhoEnum;
import entities.produtos.Roupa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banco");
        EntityManager em = emf.createEntityManager();
        RoupaRepository roupaRepository = new RoupaRepository(em);
        HigieneRepository higieneRepository = new HigieneRepository(em);
        Scanner input = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Menu");
            System.out.println("1- DOAR ");
            System.out.println("2- ENCERRAR");
            String opcao = input.next();

            switch (opcao) {
                case "1":
                    boolean exitDoar = false;
                    while (!exitDoar) {
                        System.out.println("1- DOAR ROUPA");
                        System.out.println("2- DOAR PRODUTO DE HIGIENE");
                        System.out.println("3- VOLTAR AO MENU PRINCIPAL");
                        String opcaoDoar = input.next();

                        switch (opcaoDoar) {
                            case "1":
                                System.out.print("Digite a DESCRIÇÃO da roupa: ");
                                String descricaoRoupa = input.next();

                                RoupaTamanhoEnum tamanho = null;
                                while (true) {
                                    for (RoupaTamanhoEnum valorTamanho : RoupaTamanhoEnum.values()) {
                                        System.out.println(valorTamanho + " - " + valorTamanho.getValue());
                                    }
                                    System.out.print("Selecione o TAMANHO da roupa: ");
                                    int opcaoTamanho = input.nextInt();
                                    if (opcaoTamanho > 6 || opcaoTamanho < 1) {
                                        System.out.println("TAMANHO INVÁLIDO");
                                    } else {
                                        tamanho = RoupaTamanhoEnum.fromValue(opcaoTamanho);
                                        break;
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
                                int quantidadeRoupa = input.nextInt();

                                Roupa roupa = new Roupa(descricaoRoupa, quantidadeRoupa, sexo, tamanho);
                                roupaRepository.save(roupa);
                                break;
                            case "2":
                                System.out.print("Digite a DESCRIÇÃO do produto de higiene: ");
                                String descricaoHigiene = input.next();

                                HigieneEnum tipoProduto = null;
                                while (tipoProduto == null) {
                                    for (HigieneEnum valorTipo : HigieneEnum.values()) {
                                        System.out.println(valorTipo + " - " + valorTipo.getValue());
                                    }
                                    System.out.print("Selecione o TIPO do produto de higiene: ");
                                    int opcaoTipo = input.nextInt();
                                    tipoProduto = HigieneEnum.fromValue(opcaoTipo);
                                    if (tipoProduto == null) {
                                        System.out.println("TIPO INVÁLIDO");
                                    }
                                }

                                Higiene higiene = new Higiene(tipoProduto, descricaoHigiene);
                                higieneRepository.save(higiene);
                                break;
                            case "3":
                                exitDoar = true;  // Sair do loop de doação e voltar ao menu principal
                                break;
                            default:
                                System.out.println("Opção inválida!");
                                break;
                        }
                    }
                    break; // Necessário para evitar cair no case "2" quando sair do loop de doação
                case "2":
                    em.close();
                    emf.close();
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
}
