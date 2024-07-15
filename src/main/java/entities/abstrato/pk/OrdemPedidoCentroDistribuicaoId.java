package entities.abstrato.pk;

import entities.abstrato.mediador.Pedido;
import entities.fisico.estrutural.CentroDistribuicao;
import jakarta.persistence.JoinColumn;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class OrdemPedidoCentroDistribuicaoId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_centroDistribuicao")
    private CentroDistribuicao centroDistribuicao;

    public OrdemPedidoCentroDistribuicaoId() {}

   public OrdemPedidoCentroDistribuicaoId(Pedido pedido, CentroDistribuicao centroDistribuicao) {
        this.pedido = pedido;
        this.centroDistribuicao = centroDistribuicao;
   }
   public Pedido getPedido() {
        return pedido;
   }
   public void setPedido(Pedido pedido) {
        this.pedido = pedido;
   }
   public CentroDistribuicao getCentroDistribuicao() {
        return centroDistribuicao;
   }

   public  void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) {
       this.centroDistribuicao = centroDistribuicao;
   }

}
