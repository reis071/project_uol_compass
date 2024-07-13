import entities.abstrato.Doacao;
import entities.abstrato.TransferenciaDoacaoCentro;
import entities.fisico.estrutural.Abrigo;
import entities.fisico.estrutural.CentroDistribuicao;
import entities.fisico.produtos.Alimento;
import enums.HigieneEnum;
import enums.RoupaTamanhoEnum;
import enums.Sexo;
import enums.UnidadeMedida;
import initializerData.DataInitializer;
import repositories.abstrato.DoacaoRepository;
import repositories.abstrato.TransferenciaDoacaoCentroRepository;
import repositories.fisico.estrutural.AbrigoRepository;
import repositories.fisico.produto.AlimentoRepository;
import repositories.fisico.produto.HigieneRepository;
import repositories.fisico.produto.RoupaRepository;
import entities.fisico.produtos.Roupa;
import entities.fisico.produtos.Higiene;
import services.fisico.estrutural.AbrigoService;

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
        TransferenciaDoacaoCentroRepository transferenciaDoacaoCentroRepository = new TransferenciaDoacaoCentroRepository(em);
        DoacaoRepository doacaoRepository = new DoacaoRepository(em);
        AbrigoRepository abrigoRepository = new AbrigoRepository(em);
        DataInitializer.initialize(em);
        Scanner input = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Menu");
            System.out.println("1- DOAR");
            System.out.println("2- QUANTIDADE DE PPRODUTOS POR CENTRO");
            System.out.println("3- GERENCIAR ABRIGOS");
            System.out.println("4- ENCERRAR");
            String opcao = input.next();

            switch (opcao) {
                case "1":

                    Doacao doacao = new Doacao(LocalDateTime.now());
                    CentroDistribuicao centroDistribuicao = null;

                    // Escolha do centro de distribuição
                    System.out.println("Escolha o centro de distribuição a qual ira Doar:");
                    System.out.println("1- Centro de Distribuição Esperança");
                    System.out.println("2- Centro de Distribuição Prosperidade");
                    System.out.println("3- Centro de Distribuição Reconstrução");
                    int opcaoCentro = input.nextInt();
                    Long centroId = null;
                    switch (opcaoCentro) {
                        case 1:
                            centroDistribuicao = em.find(CentroDistribuicao.class, 1L); // Substitua 1L pelo ID correto
                            break;
                        case 2:
                            centroDistribuicao = em.find(CentroDistribuicao.class, 2L); // Substitua 2L pelo ID correto
                            break;
                        case 3:
                            centroDistribuicao = em.find(CentroDistribuicao.class, 3L); // Substitua 3L pelo ID correto

                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
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
                                Higiene higiene = new Higiene(tipoProduto, descricaoHigiene);


                                Alimento alimento = new Alimento(descricaoAlimento, quantidadeAlimento, unidadeMedida, dataValidade);


                                doacao.getAlimentos().add(alimento);
                                doacao.getHigienes().add(higiene);
                                doacao.getRoupas().add(roupa);


                                System.out.print("Deseja Continuar doando (S/N)? ");
                                String continuarDoacao = input.next();
                                if (!continuarDoacao.equalsIgnoreCase("S")) {
                                    roupaRepository.save(roupa);
                                    higieneRepository.save(higiene);
                                    alimentoRepository.save(alimento);
                                    doacaoRepository.save(doacao);
                                    TransferenciaDoacaoCentro transferenciaDoacaoCentro = new TransferenciaDoacaoCentro(doacao, centroDistribuicao);
                                    transferenciaDoacaoCentroRepository.save(transferenciaDoacaoCentro);
                                    break; // Sair do switch case, mas continuar no loop principal
                                }
                                else{
                                    exitDoar = true;
                                }
                                break;

                            default:
                                System.out.println("Opção inválida!");
                                break;
                        }
                    }
                    break; // Necessário para evitar cair no case "2" quando sair do loop de doação
                case "2":
                    for (long i = 1L; i <= 3; i++) {
                        centroDistribuicao = em.find(CentroDistribuicao.class, i);
                        if (centroDistribuicao != null) {
                            System.out.println("Centro de Distribuição selecionado: " + centroDistribuicao.getNome());

                            long quantidadeAlimentos = centroDistribuicao.countAlimentos();
                            long quantidadeRoupas = centroDistribuicao.countRoupas();
                            long quantidadeHigienes = centroDistribuicao.countHigienes();

                            System.out.println("Centro de Distribuição: " + centroDistribuicao.getNome());
                            System.out.println("Quantidade de Alimentos: " + quantidadeAlimentos);
                            System.out.println("Quantidade de Roupas: " + quantidadeRoupas);
                            System.out.println("Quantidade de Produtos de Higiene: " + quantidadeHigienes);
                        } else {
                            System.out.println("Centro de Distribuição não encontrado.");
                        }
                    }
                    break;
                case "3":
                    boolean exitAbrigos = false;
                    while (!exitAbrigos) {
                        System.out.println("Gerenciamento de Abrigos");
                        System.out.println("1- Cadastrar Abrigo");
                        System.out.println("2- Listar Abrigos");
                        System.out.println("3- Editar Abrigo");
                        System.out.println("4- Excluir Abrigo");
                        System.out.println("5- Procurar Abrigo");
                        System.out.println("6- Voltar ao Menu Principal");
                        AbrigoService abrigoService = new AbrigoService(abrigoRepository);

                        String opcaoAbrigo = input.next();
                        switch (opcaoAbrigo) {
                            case "1":
                                System.out.println("Digite NOME do Abrigo");
                                String nome = input.next();

                                System.out.println("Digite RESPONSAVEL do Abrigo");
                                String responsavel = input.next();

                                System.out.println("Digite TELEFONE do Abrigo");
                                String telefone = input.next();

                                System.out.println("Digite EMAIL do Abrigo");
                                String email = input.next();

                                System.out.println("Digite CAPACIDADE do Abrigo");
                                int capacidade = input.nextInt();

                                abrigoService.cadastrarAbrigo(nome, responsavel, telefone, email, capacidade);
                                break;
                            case "2":
                                abrigoService.listarAbrigos();
                                break;
                            case "3":
                                System.out.println("Digite o ID do Abrigo");
                                long id = input.nextLong();

                                System.out.println("Digite NOME do Abrigo");
                                nome = input.next();

                                System.out.println("Digite RESPONSAVEL do Abrigo");
                                responsavel = input.next();

                                System.out.println("Digite TELEFONE do Abrigo");
                                telefone = input.next();

                                System.out.println("Digite EMAIL do Abrigo");
                                email = input.next();

                                System.out.println("Digite CAPACIDADE do Abrigo");
                                capacidade = input.nextInt();

                                abrigoService.editarAbrigo(id, nome, responsavel, telefone, email, capacidade);
                                break;
                            case "4":
                                System.out.println("Digite o ID do Abrigo");
                                id = input.nextLong();
                                abrigoService.excluirAbrigo(id);
                                break;
                            case "5":
                                System.out.println("Digite o ID do Abrigo que deseja visualizar");
                                id = input.nextLong();
                                Abrigo abrigo = abrigoService.buscarAbrigoPorId(id);
                                if (abrigo != null) {
                                    System.out.println("ID: " + abrigo.getId());
                                    System.out.println("Nome: " + abrigo.getNome());
                                    System.out.println("Responsável: " + abrigo.getResponsavel());
                                    System.out.println("Telefone: " + abrigo.getTelefone());
                                    System.out.println("Email: " + abrigo.getEmail());
                                    System.out.println("Capacidade: " + abrigo.getCapacidade());
                                    System.out.println("Ocupação: " + abrigoService.calcularOcupacao(id));
                                    System.out.println("Quantidade de Alimentos: " + abrigoService.countAlimentosRecebidos(id));
                                    System.out.println("Quantidade de Roupas: " + abrigoService.countRoupasRecebidas(id));
                                    System.out.println("Quantidade de Produtos de Higiene: " + abrigoService.countHigienesRecebidas(id));
                                    System.out.println("-------------------------");
                                } else {
                                    System.out.println("Abrigo não encontrado.");
                                }
                            case "6":
                                exitAbrigos = true;
                                break;
                            default:
                                System.out.println("Opcao Inexistente");
                        }
                    }
                    break;
                case "4":
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
