package mobilidade.veiculos;

public abstract class Veiculo {
    private int id;
    private String localizacao; // Podemos criar uma classe localizacao

    public Veiculo(){}

    public Veiculo(int id, String localizacao) {
        this.id = id;
        this.localizacao = localizacao;
    }

    public int getId() {
        return id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
