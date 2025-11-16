// src/mobilidade/veiculos/TrotineteComLCD.java
package mobilidade.veiculos;

import mobilidade.utilizadores.Utilizador;

public class TrotineteComLCD extends Trotinete {
    public TrotineteComLCD(String id, String localizacaoGPS, int nivelBateria) {
        super(id, localizacaoGPS, nivelBateria);
    }

    @Override
    public double getCusto(Utilizador u) {
        return u.getPreco(this);
    }

    @Override
    public String toString() {
        return "Trotinete com LCD (ID: " + id + ")";
    }
}