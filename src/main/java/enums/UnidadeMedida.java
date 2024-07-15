package enums;

public enum UnidadeMedida {
    G(1),
    KG(2),
    ML(3),
    L(4);
    private int valor;
    private UnidadeMedida(int valor) {
        this.valor = valor;
    }

    public int getValue() {
        return valor;
    }

    public static UnidadeMedida fromValue(int valor) {
        for (UnidadeMedida unidadeMedida : UnidadeMedida.values()) {
            if (unidadeMedida.getValue() == valor) {
                return unidadeMedida;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Unidade Medida: " + valor);
    }
}
