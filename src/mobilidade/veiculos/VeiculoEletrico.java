package mobilidade.veiculos;

/**
 * Classe abstrata para Veículos Elétricos.
 */
public abstract class VeiculoEletrico extends Veiculo {
    protected int nivelBateria; // Nível da bateria

    public VeiculoEletrico(String id, String localizacaoGPS, int nivelBateria) {
        super(id, localizacaoGPS);
        this.nivelBateria = nivelBateria;
    }

    public int getNivelBateria() {
        return nivelBateria;
    }
}