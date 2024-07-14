package entities.fisico.estrutural;

import entities.abstrato.Doacao;
import entities.abstrato.TransferenciaDoacaoCentro;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class CentroDistribuicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    public void setNome(String nome) {
        this.nome = nome;
    }

    @OneToMany(mappedBy = "id.centroDistribuicao")
    private Set<TransferenciaDoacaoCentro> transferencia = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "estoque", joinColumns = @JoinColumn(name = "centro_id"))
    @MapKeyColumn(name = "descricao_item")
    @Column(name = "quantidade")
    private Map<String, Long> estoque;

    public CentroDistribuicao(){
        this.estoque = new HashMap<>();
    }
    public CentroDistribuicao(long l, String nome) {
        this.id = id;
        this.nome = nome;
        this.estoque = new HashMap<>();
    }

    public Set<Doacao> getDoacao() {
        Set<Doacao> set = new HashSet<>();
        for (TransferenciaDoacaoCentro t : transferencia) {
            set.add(t.getDoacao());
        }
        return set;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public long countAlimentos() {
        long count = 0;
        for (TransferenciaDoacaoCentro transferencia : transferencia) {
            count += transferencia.getDoacao().getAlimentos().size();
        }
        return count;
    }
    public Map<String, Long> getEstoque() {
        return estoque;
    }


    public Long getQuantidadeItem(String descricao) {
        return estoque.getOrDefault(descricao, 0L);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CentroDistribuicao that = (CentroDistribuicao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
