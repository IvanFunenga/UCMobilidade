// src/mobilidade/veiculos/Trotinete.java
package mobilidade.veiculos;

/**
 * Classe abstrata para Trotinetes.
 */
public abstract class Trotinete extends VeiculoEletrico {
    public Trotinete(String id, String localizacaoGPS, int nivelBateria) {
        super(id, localizacaoGPS, nivelBateria);
    }
}