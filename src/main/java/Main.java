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
import repositories.fisico.estrutural.AbrigoRepository;
import entities.fisico.produtos.Roupa;
import entities.fisico.produtos.Higiene;
import services.fisico.estrutural.AbrigoService;
import services.fisico.estrutural.CentroDistribuicaoService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banco");
        EntityManager em = emf.createEntityManager();
        DataInitializer dataInitializer = new DataInitializer(em);
        DataInitializer.initialize(em);
        Scanner input = new Scanner(System.in);

        CentroDistribuicaoService centroDistribuicaoService = new CentroDistribuicaoService(dataInitializer.getCentroDistribuicaoRepository());
        boolean exit = false;
        while (!exit) {
            System.out.println("Menu");
            System.out.println("1- DOAR");
            System.out.println("2- VERIFICAR NECESSIDADES DOS ABRIGOS");
            System.out.println("3- GERENCIAR ABRIGOS");
            System.out.println("4- ENCERRAR");
            String opcao = input.next();

            switch (opcao) {
                case "1":
                    Doacao doacao = new Doacao(LocalDateTime.now());
                    CentroDistribuicao centroDistribuicao = null;
                    while (true) {
                        System.out.println("Escolha o centro de distribuição ao qual irá doar:");
                        System.out.println("1- Centro de Distribuição Esperança");
                        System.out.println("2- Centro de Distribuição Prosperidade");
                        System.out.println("3- Centro de Distribuição Reconstrução");
                        Long centroId = input.nextLong();
                        centroDistribuicao = centroDistribuicaoService.buscarCentroPorId(centroId);

                        if (centroDistribuicao == null) {
                            System.out.println("Centro de distribuição não encontrado.");
                        } else {
                            break;
                        }
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
                                    if (opcaoUnidade < 1 || opcaoUnidade > 4) {
                                        System.out.println("UNIDADE INVÁLIDA");
                                    } else {
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
                                if (continuarDoacao.equals("N")) {
                                    dataInitializer.getRoupaRepository().save(roupa);
                                    dataInitializer.getHigieneRepository().save(higiene);
                                    dataInitializer.getAlimentoRepository().save(alimento);
                                    dataInitializer.getDoacaoRepository().save(doacao);
                                    TransferenciaDoacaoCentro transferenciaDoacaoCentro = new TransferenciaDoacaoCentro(doacao, centroDistribuicao);
                                    dataInitializer.getTransferenciaDoacaoCentroRepository().save(transferenciaDoacaoCentro);
                                    centroDistribuicaoService.atualizarEstoque(centroDistribuicao, doacao);
                                    exitDoar = true; // Sair do loop de doação
                                }else{
                                    continue;
                                }
                                break; // Sair do switch case "1" (doar produtos)

                            case "2":
                                exitDoar = true; // Sair do loop de doação
                                break; // Sair do switch case "2" (voltar ao menu principal)

                            default:
                                System.out.println("Opção inválida!");
                                break; // Sair do switch case padrão
                        }
                    }
                    break; // Sair do switch case "1" (opção DOAR)
                            case "2":
                                System.out.print("Digite a descrição do item necessário: ");
                                String descricao = input.next();
                                System.out.print("Digite a quantidade necessária: ");
                                Long quantidade = input.nextLong();

                                List<CentroDistribuicao> centros = centroDistribuicaoService.buscarCentrosPorNecessidade(descricao, quantidade);

                                System.out.println("Centros de Distribuição que possuem o item:");
                                for (CentroDistribuicao centro : centros) {
                                    System.out.println("Centro: " + centro.getNome() + ", Quantidade disponível: " + centro.getQuantidadeItem(descricao));
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
                                    AbrigoService abrigoService = new AbrigoService(new AbrigoRepository(em));

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
