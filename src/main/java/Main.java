import entities.abstrato.Doacao;
import entities.fisico.estrutural.Abrigo;
import entities.fisico.estrutural.CentroDistribuicao;
import entities.fisico.produtos.Alimento;
import enums.RoupaTamanhoEnum;
import enums.Sexo;
import enums.UnidadeMedida;
import initializerData.DataInitializer;
import repositories.fisico.estrutural.AbrigoRepository;
import entities.fisico.produtos.Roupa;
import entities.fisico.produtos.Higiene;
import services.abstrato.CsvService;
import services.abstrato.PedidoService;
import services.fisico.estrutural.AbrigoService;
import services.fisico.estrutural.CentroDistribuicaoService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banco");
        EntityManager em = emf.createEntityManager();
        DataInitializer dataInitializer = new DataInitializer(em);
        DataInitializer.initialize(em);
        Scanner input = new Scanner(System.in);
        CsvService csvService = new CsvService();
        CentroDistribuicaoService centroDistribuicaoService = new CentroDistribuicaoService(dataInitializer.getCentroDistribuicaoRepository());
        boolean exit = false;
        while (!exit) {
            System.out.println("Menu");
            System.out.println("1- DOAR");
            System.out.println("2- VERIFICAR NECESSIDADES DOS ABRIGOS");
            System.out.println("3- GERENCIAR ABRIGOS");
            System.out.println("4- ENVIAR PEDIDO");
            System.out.println("5- ENCERRAR");
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
                        System.out.println("Menu de Doação");
                        System.out.println("1 - Doar Roupa");
                        System.out.println("2 - Doar Produto de Higiene");
                        System.out.println("3 - Doar Alimento");
                        System.out.println("4 - Exportar Doação arquivo csv");
                        System.out.println("5 - Voltar ao Menu Principal");

                        String opcaoDoar = input.next();

                        switch (opcaoDoar) {
                            case "1":
                                if(centroDistribuicao.contarRoupa() <= 999L) {
                                    System.out.print("Digite a DESCRIÇÃO da roupa: ");
                                    String descricaoRoupa = input.next();

                                    RoupaTamanhoEnum tamanho = null;
                                    while (true) {
                                        for (RoupaTamanhoEnum valorTamanho : RoupaTamanhoEnum.values()) {
                                            System.out.println(valorTamanho + " - " + valorTamanho.getValue());
                                        }
                                        System.out.print("Selecione o TAMANHO da roupa: ");
                                        int opcaoTamanho = input.nextInt();
                                        input.nextLine();
                                        tamanho = RoupaTamanhoEnum.fromValue(opcaoTamanho);
                                        if (tamanho != null) {
                                            break;
                                        } else {
                                            System.out.println("TAMANHO INVÁLIDO");
                                        }
                                    }

                                    Sexo sexo = null;
                                    while (true) {
                                        for (Sexo valorSexo : Sexo.values()) {
                                            System.out.println(valorSexo + " - " + valorSexo.getValue());
                                        }
                                        System.out.print("Selecione o SEXO da roupa: ");
                                        int opcaoSexo = input.nextInt();
                                        input.nextLine(); // Consumir a nova linha
                                        sexo = Sexo.fromValue(opcaoSexo);
                                        if (sexo != null) {
                                            break;
                                        } else {
                                            System.out.println("SEXO INVÁLIDO");
                                        }
                                    }

                                    Roupa roupa = new Roupa(descricaoRoupa, sexo, tamanho);
                                    doacao.getRoupas().add(roupa);

                                    dataInitializer.getRoupaRepository().save(roupa);
                                    dataInitializer.getDoacaoRepository().save(doacao);
                                    centroDistribuicaoService.atualizarEstoque(centroDistribuicao, doacao);

                                    System.out.print("Deseja Continuar doando (S/N)? ");
                                    String continuarDoacao = input.next();
                                    if (!continuarDoacao.equalsIgnoreCase("S")) {
                                        exitDoar = true;
                                    }
                                }
                                else {
                                    System.out.println("limite de doacoes de roupas excedidos");
                                    break;
                                }
                                break;
                            case "2":
                                if(centroDistribuicao.contarProdutosHigiene() <= 999L) {
                                    System.out.print("Digite a DESCRIÇÃO do produto de higiene: ");
                                    String descricaoHigiene = input.next();

                                    Higiene higiene = new Higiene(descricaoHigiene);
                                    doacao.getHigienes().add(higiene);

                                    dataInitializer.getHigieneRepository().save(higiene);
                                    dataInitializer.getDoacaoRepository().save(doacao);
                                    centroDistribuicaoService.atualizarEstoque(centroDistribuicao, doacao);

                                    System.out.print("Deseja Continuar doando (S/N)? ");
                                    String continuarDoacao = input.next();
                                    if (!continuarDoacao.equalsIgnoreCase("S")) {
                                        exitDoar = true;
                                    }
                                    System.out.println("limite de doacoes de produtos de higiene excedidos");
                                    break;
                                }
                                break;
                            case "3":
                                if(centroDistribuicao.contarAlimentos() <= 999L){
                                System.out.print("Digite a DESCRIÇÃO do alimento: ");
                                String descricaoAlimento = input.next();

                                System.out.print("Digite a QUANTIDADE do alimento: ");
                                Double quantidadeAlimento = input.nextDouble();
                                input.nextLine(); // Consumir a nova linha

                                UnidadeMedida unidadeMedida = null;
                                while (unidadeMedida == null) {
                                    for (UnidadeMedida valorUnidade : UnidadeMedida.values()) {
                                        System.out.println(valorUnidade + " - " + valorUnidade.getValue());
                                    }
                                    System.out.print("Selecione a UNIDADE de medida: ");
                                    int opcaoUnidade = input.nextInt();
                                    input.nextLine(); // Consumir a nova linha
                                    unidadeMedida = UnidadeMedida.fromValue(opcaoUnidade);
                                    if (unidadeMedida == null) {
                                        System.out.println("UNIDADE INVÁLIDA");
                                    }
                                }

                                System.out.print("Digite a DATA DE VALIDADE do alimento (formato: yyyy-MM-dd): ");
                                String dataValidadeStr = input.next();
                                Date dataValidade = java.sql.Date.valueOf(dataValidadeStr);

                                Alimento alimento = new Alimento(descricaoAlimento, quantidadeAlimento, unidadeMedida, dataValidade);
                                doacao.getAlimentos().add(alimento);

                                dataInitializer.getAlimentoRepository().save(alimento);
                                dataInitializer.getDoacaoRepository().save(doacao);
                                centroDistribuicaoService.atualizarEstoque(centroDistribuicao, doacao);

                                System.out.print("Deseja Continuar doando (S/N)? ");
                                String continuarDoacao = input.next();
                                if (!continuarDoacao.equalsIgnoreCase("S")) {
                                    exitDoar = true;
                                }

                                }else{
                                    System.out.println("limite de doacoes de alimentos excedidos");
                                    break;
                                }
                                break;
                            case "4":
                                boolean sair = false;
                                while (!sair) {
                                    System.out.println("Menu Principal");
                                    System.out.println("1- Exportar Doações de Roupas para CSV");
                                    System.out.println("2- Exportar Doações de Produtos de Higiene para CSV");
                                    System.out.println("3- Exportar Doações de Alimentos para CSV");
                                    System.out.println("4- Sair");

                                    opcao = input.nextLine();

                                    switch (opcao) {
                                        case "1":
                                            // Lógica para exportar doações de roupas para CSV
                                            List<Roupa> roupas = dataInitializer.getRoupaRepository().findAll();
                                            try {
                                                csvService.exportarDoacoesRoupas(roupas, "doacoes_roupas.csv");
                                                System.out.println("Doações de roupas exportadas com sucesso para doacoes_roupas.csv");
                                            } catch (IOException e) {
                                                System.err.println("Erro ao exportar doações de roupas: " + e.getMessage());
                                            }
                                            break;
                                        case "2":
                                            // Lógica para exportar doações de produtos de higiene para CSV
                                            List<Higiene> higienes = dataInitializer.getHigieneRepository().findAll();
                                            try {
                                                csvService.exportarDoacoesHigiene(higienes, "doacoes_higiene.csv");
                                                System.out.println("Doações de produtos de higiene exportadas com sucesso para doacoes_higiene.csv");
                                            } catch (IOException e) {
                                                System.err.println("Erro ao exportar doações de produtos de higiene: " + e.getMessage());
                                            }
                                            break;
                                        case "3":
                                            // Lógica para exportar doações de alimentos para CSV
                                            List<Alimento> alimentos = dataInitializer.getAlimentoRepository().findAll();
                                            try {
                                                csvService.exportarDoacoesAlimentos(alimentos, "doacoes_alimentos.csv");
                                                System.out.println("Doações de alimentos exportadas com sucesso para doacoes_alimentos.csv");
                                            } catch (IOException e) {
                                                System.err.println("Erro ao exportar doações de alimentos: " + e.getMessage());
                                            }
                                            break;
                                        case "4":
                                            sair = true;
                                            break;
                                        default:
                                            System.out.println("Opção inválida!");
                                            break;
                                    }
                                }
                            break;
                            case "5":
                                exitDoar = true; // Voltar ao menu principal
                                break;

                            default:
                                System.out.println("Opção inválida.");
                                break; // Sair do switch case padrão
                        }
                        break;
                    }
                    break;
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
                                for (Abrigo abrigo : abrigoService.listarAbrigos()) {

                                    System.out.println("ID: " + abrigo.getId());
                                    System.out.println("Nome: " + abrigo.getNome());
                                    System.out.println("Responsável: " + abrigo.getResponsavel());
                                    System.out.println("Telefone: " + abrigo.getTelefone());
                                    System.out.println("Email: " + abrigo.getEmail());
                                    System.out.println("Capacidade: " + abrigo.getCapacidade());
                                    System.out.println("Ocupação: " + abrigoService.calcularOcupacao(abrigo.getId()));
                                }
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
                    PedidoService pedidoService = new PedidoService(dataInitializer.getPedidoRepository(), centroDistribuicaoService);
                    boolean exitPedido = false;

                    while (!exitPedido) {
                        System.out.println("Menu");
                        System.out.println("1- Criar Pedido");
                        System.out.println("2- Analisar pedido");
                        System.out.println("3- Sair");

                        String opcaoItem = input.next();


                        switch (opcaoItem) {
                            case "1":
                                System.out.println("Digite o ID do Abrigo:");
                                Long abrigoId = input.nextLong();
                                input.nextLine();  // Consumir a nova linha
                                Abrigo abrigo = dataInitializer.getAbrigoRepository().findById(abrigoId);

                                if (abrigo != null) {
                                    Map<String, Long> itensNecessarios = new HashMap<>();
                                    boolean adicionarMaisItens = true;

                                    while (adicionarMaisItens) {
                                        System.out.println("Digite a descrição do item:");
                                        String descricaoItens = input.nextLine();
                                        System.out.println("Digite a quantidade necessária:");
                                        Long quantidadeItem = input.nextLong();
                                        input.nextLine();  // Consumir a nova linha
                                        itensNecessarios.put(descricaoItens, quantidadeItem);

                                        System.out.println("Adicionar mais itens? (s/n)");
                                        String resposta = input.nextLine();
                                        adicionarMaisItens = resposta.equalsIgnoreCase("s");
                                    }

                                    pedidoService.criarPedido(abrigo, itensNecessarios);
                                    System.out.println("Pedido criado com sucesso!");
                                } else {
                                    System.out.println("Abrigo não encontrado.");
                                }
                                break;

                            case "2":
                                System.out.print("Digite o ID do Pedido: ");
                                Long pedidoId = input.nextLong();

                                System.out.print("Digite o ID do Centro de Distribuição: ");
                                Long centroId = input.nextLong();

                                System.out.print("Deseja aceitar (A) ou recusar (R) o pedido? ");
                                String acao = input.next();

                                if (acao.equalsIgnoreCase("A")) {
                                    try {
                                        pedidoService.aceitarPedido(pedidoId, centroId);
                                        System.out.println("Pedido aceito com sucesso.");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else if (acao.equalsIgnoreCase("R")) {
                                    System.out.print("Digite o motivo da recusa: ");
                                    input.nextLine(); // Consome a nova linha
                                    String motivo = input.nextLine();

                                    try {
                                        pedidoService.recusarPedido(pedidoId, motivo);
                                        System.out.println("Pedido recusado com sucesso.");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else {
                                    System.out.println("Ação inválida.");
                                }
                                break;

                            case "3":
                                exitPedido = true;
                                System.out.println("Saindo do menu de pedidos.");
                                break;

                            default:
                                System.out.println("Opção inválida.");
                        }
                    }
                    break;

                case "5":
                    em.close();
                    emf.close();
                    input.close();
                    exit = true;
                    break;
                default:
                    System.out.println("opcao invalida");
            }
        }

    }
}