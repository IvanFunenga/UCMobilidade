package mobilidade.veiculos;

/**
 * Classe abstrata base para Bicicletas.
 */
public abstract class Bicicleta extends Veiculo {
    public Bicicleta(String id, String localizacaoGPS) {
        super(id, localizacaoGPS);
    }

}