// src/mobilidade/veiculos/EBikeBateriaFixa.java
package mobilidade.veiculos;

import mobilidade.utilizadores.Utilizador;

public class EBikeBateriaFixa extends EBike {
    public EBikeBateriaFixa(String id, String localizacaoGPS, int nivelBateria) {
        super(id, localizacaoGPS, nivelBateria);
    }

    @Override
    public double getCusto(Utilizador u) {
        return u.getPreco(this);
    }

    @Override
    public String toString() {
        return "E-Bike Bateria Fixa (ID: " + id + ")";
    }
}