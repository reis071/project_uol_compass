package entities.abstrato.mediador;

import entities.fisico.estrutural.Abrigo;
import enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

@Entity
public class Pedido implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "abrigo_id", nullable = false)
    private Abrigo abrigo;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
    private Set<OrdemPedidoCentroDistribuicao> ordens = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = true)
    private String motivoRecusa;

    public Pedido() {
        this.status = Status.PENDENTE;
    }

    public Pedido(Abrigo abrigo) {
        this.abrigo = abrigo;
        this.status = Status.PENDENTE;
        this.dataPedido = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    public Abrigo getAbrigo() {
        return abrigo;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Set<OrdemPedidoCentroDistribuicao> getOrdens() {
        return ordens;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
