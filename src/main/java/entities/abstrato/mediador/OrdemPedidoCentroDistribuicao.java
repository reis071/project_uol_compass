package entities.abstrato.mediador;



import entities.abstrato.pk.OrdemPedidoCentroDistribuicaoId;
import entities.fisico.estrutural.CentroDistribuicao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class OrdemPedidoCentroDistribuicao implements Serializable {

    @EmbeddedId
    private OrdemPedidoCentroDistribuicaoId id = new OrdemPedidoCentroDistribuicaoId();

    @ManyToOne
    @MapsId("pedido")
    private Pedido pedido;

    @ManyToOne
    @MapsId("centroDistribuicao")
    private CentroDistribuicao centroDistribuicao;

    @ElementCollection
    @CollectionTable(name = "itens_pedido", joinColumns = {
            @JoinColumn(name = "id_pedido"),
            @JoinColumn(name = "id_centroDistribuicao")
    })
    @MapKeyColumn(name = "descricao_item")
    @Column(name = "quantidade")
    private Map<String, Long> itensPedido = new HashMap<>();

    // Construtores, getters e setters
    public OrdemPedidoCentroDistribuicao() {
    }

    public OrdemPedidoCentroDistribuicao(Pedido pedido, CentroDistribuicao centroDistribuicao) {
        this.id = new OrdemPedidoCentroDistribuicaoId(pedido, centroDistribuicao);
        this.pedido = pedido;
        this.centroDistribuicao = centroDistribuicao;
    }

    public OrdemPedidoCentroDistribuicaoId getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public CentroDistribuicao getCentroDistribuicao() {
        return centroDistribuicao;
    }

    public Map<String, Long> getItensPedido() {
        return itensPedido;
    }

    public void addItem(String descricao, Long quantidade) {
        itensPedido.put(descricao, quantidade);
    }
}
