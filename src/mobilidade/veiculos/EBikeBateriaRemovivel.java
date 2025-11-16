// src/mobilidade/veiculos/EBikeBateriaRemovivel.java
package mobilidade.veiculos;

import mobilidade.utilizadores.Utilizador;

public class EBikeBateriaRemovivel extends EBike {
    public EBikeBateriaRemovivel(String id, String localizacaoGPS, int nivelBateria) {
        super(id, localizacaoGPS, nivelBateria);
    }

    @Override
    public double getCusto(Utilizador u) {
        return u.getPreco(this);
    }

    @Override
    public String toString() {
        return "E-Bike Bateria Removível (ID: " + id + ")";
    }
}