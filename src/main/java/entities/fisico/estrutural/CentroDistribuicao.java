package entities.fisico.estrutural;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CentroDistribuicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    public CentroDistribuicao(){}
    public CentroDistribuicao(long l, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}