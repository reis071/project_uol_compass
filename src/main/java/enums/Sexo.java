package enums;

public enum Sexo {
        FEMININO(0),
        MASCULINO(1);

    private int valor;

    private Sexo(int valor) {
        this.valor = valor;
    }

    public int getValue() {
        return valor;
    }

    public static Sexo fromValue(int value) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.getValue() == value) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Sexo: " + value);
    }
}
