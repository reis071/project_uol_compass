import entities.abstrato.Doacao;
import entities.fisico.produtos.Alimento;
import enums.HigieneEnum;
import enums.RoupaTamanhoEnum;
import enums.Sexo;
import enums.UnidadeMedida;
import repositories.abstrato.DoacaoRepository;
import repositories.fisico.produto.AlimentoRepository;
import repositories.fisico.produto.HigieneRepository;
import repositories.fisico.produto.RoupaRepository;
import entities.fisico.produtos.Roupa;
import entities.fisico.produtos.Higiene;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banco");
        EntityManager em = emf.createEntityManager();
        RoupaRepository roupaRepository = new RoupaRepository(em);
        HigieneRepository higieneRepository = new HigieneRepository(em);
        AlimentoRepository alimentoRepository = new AlimentoRepository(em);
        DoacaoRepository doacaoRepository = new DoacaoRepository(em);
        Scanner input = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Menu");
            System.out.println("1- DOAR");
            System.out.println("2- ENCERRAR");
            String opcao = input.next();

            switch (opcao) {
                case "1":
                    Doacao doacao = new Doacao(LocalDateTime.now());

                    boolean exitDoar = false;
                    while (!exitDoar) {
                        System.out.println("1- DOAR PRODUTOS");
                        System.out.println("2- VOLTAR AO MENU PRINCIPAL");
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
                                while (true) {
                                    for (Sexo valorSexo : Sexo.values()) {
                                        System.out.println(valorSexo + " - " + valorSexo.getValue());
                                    }
                                    System.out.print("Selecione o SEXO da roupa: ");
                                    int opcaoSexo = input.nextInt();
                                    if (opcaoSexo < 0 || opcaoSexo > 1) {
                                        System.out.println("SEXO INVÁLIDO");
                                    } else {
                                        sexo = Sexo.fromValue(opcaoSexo);
                                        break;
                                    }
                                }

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

                                System.out.print("Digite a DESCRIÇÃO do alimento: ");
                                String descricaoAlimento = input.next();

                                System.out.print("Digite a QUANTIDADE do alimento: ");
                                Double quantidadeAlimento = input.nextDouble();

                                UnidadeMedida unidadeMedida = null;
                                while (unidadeMedida == null) {
                                    for (UnidadeMedida valorUnidade : UnidadeMedida.values()) {
                                        System.out.println(valorUnidade + " - " + valorUnidade.getValue());
                                    }
                                    System.out.print("Selecione a UNIDADE de medida: ");
                                    int opcaoUnidade = input.nextInt();
                                    if (opcaoUnidade < 1 || opcaoUnidade > 4 ) {
                                        System.out.println("UNIDADE INVÁLIDA");
                                    }else{
                                        unidadeMedida = UnidadeMedida.fromValue(opcaoUnidade);
                                    }
                                }

                                System.out.print("Digite a DATA DE VALIDADE do alimento (formato: yyyy-MM-dd): ");
                                String dataValidadeStr = input.next();

                                Date dataValidade = java.sql.Date.valueOf(dataValidadeStr);

                                Roupa roupa = new Roupa(descricaoRoupa, sexo, tamanho);
                                roupaRepository.save(roupa);

                                Higiene higiene = new Higiene(tipoProduto, descricaoHigiene);
                                higieneRepository.save(higiene);

                                Alimento alimento = new Alimento(descricaoAlimento, quantidadeAlimento, unidadeMedida, dataValidade);
                                alimentoRepository.save(alimento);

                                doacao.getAlimentos().add(alimento);
                                doacao.getHigienes().add(higiene);
                                doacao.getRoupas().add(roupa);

                                System.out.print("Deseja Continuar doando (S/N)?");
                                String continuarDoacao = input.next();
                                if (continuarDoacao.equals("1")) {
                                    continue;
                                }else{
                                    doacaoRepository.save(doacao);
                                    break;
                                }


                            case "2":
                                exitDoar = true; // Sair do loop de doação e voltar ao menu principal
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
