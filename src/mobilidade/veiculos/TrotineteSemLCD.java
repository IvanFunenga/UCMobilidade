// src/mobilidade/veiculos/TrotineteSemLCD.java
package mobilidade.veiculos;

import mobilidade.utilizadores.Utilizador;

public class TrotineteSemLCD extends Trotinete {
    public TrotineteSemLCD(String id, String localizacaoGPS, int nivelBateria) {
        super(id, localizacaoGPS, nivelBateria);
    }

    @Override
    public double getCusto(Utilizador u) {
        return u.getPreco(this);
    }

    @Override
    public String toString() {
        return "Trotinete sem LCD (ID: " + id + ")";
    }
}