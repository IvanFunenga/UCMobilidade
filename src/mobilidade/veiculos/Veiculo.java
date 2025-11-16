package mobilidade.veiculos;

import mobilidade.utilizadores.Utilizador;

/**
 * Classe abstrata que representa um Veículo genérico.
 */
public abstract class Veiculo {
    protected String id; // Identificador de 4 dígitos
    protected String localizacaoGPS;
    protected boolean emUso;

    public Veiculo(String id, String localizacaoGPS) {
        this.id = id;
        this.localizacaoGPS = localizacaoGPS;
        this.emUso = false;
    }

    public String getId() {
        return id;
    }

    public boolean isEmUso() {
        return emUso;
    }

    public void setEmUso(boolean emUso) {
        this.emUso = emUso;
    }

    @Override
    public String toString() {
        return "Veiculo ID=" + id + " (GPS: " + localizacaoGPS + ")";
    }

    /**
     * Calcula o custo por hora deste veículo para um utilizador específico.
     * Implementa o padrão Visitor: o veículo "aceita" o utilizador
     * e delega o cálculo do preço para o método getPreco() específico do utilizador.
     *
     * @param u O utilizador que está a alugar.
     * @return O custo por hora.
     */
    public abstract double getCusto(Utilizador u);
}