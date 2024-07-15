package entities.abstrato;

import entities.abstrato.pk.TransferenciaDoacaoCentroPk;
import entities.fisico.estrutural.CentroDistribuicao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entidade que representa a transferência de uma doação para um centro de distribuição.
 */
@Entity
public class TransferenciaDoacaoCentro implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TransferenciaDoacaoCentroPk id = new TransferenciaDoacaoCentroPk();

    /**
     * Construtor padrão.
     */
    public TransferenciaDoacaoCentro() {}

    /**
     * Construtor que inicializa a transferência com uma doação e um centro de distribuição.
     */
    public TransferenciaDoacaoCentro(Doacao doacao, CentroDistribuicao centroDistribuicao) {
        super();
        id.setDoacao(doacao);
        id.setCentroDistribuicao(centroDistribuicao);
    }

    /**
     * Define a doação associada à transferência.
     */
    public void setDoacao(Doacao doacao) {
        id.setDoacao(doacao);
    }

    /**
     * Obtém a doação associada à transferência.
     */
    public Doacao getDoacao() {
        return id.getDoacao();
    }

    /**
     * Define o centro de distribuição associado à transferência.
     */
    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) {
        id.setCentroDistribuicao(centroDistribuicao);
    }

    /**
     * Obtém o centro de distribuição associado à transferência.
     */
    public CentroDistribuicao getCentroDistribuicao() {
        return id.getCentroDistribuicao();
    }

    /**
     * Obtém o identificador composto da transferência.
     */
    public TransferenciaDoacaoCentroPk getId() {
        return id;
    }

    /**
     * Verifica a igualdade entre esta transferência e outro objeto.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferenciaDoacaoCentro that = (TransferenciaDoacaoCentro) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calcula o hash code desta transferência.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
