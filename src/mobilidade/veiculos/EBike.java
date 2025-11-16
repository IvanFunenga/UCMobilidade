// src/mobilidade/veiculos/EBike.java
package mobilidade.veiculos;

/**
 * Classe abstrata para EBikes.
 */
public abstract class EBike extends VeiculoEletrico {
    public EBike(String id, String localizacaoGPS, int nivelBateria) {
        super(id, localizacaoGPS, nivelBateria);
    }
}