package mobilidade.veiculos;

import mobilidade.utilizadores.Utilizador;

public class BicicletaDupla extends Bicicleta {
    public BicicletaDupla(String id, String localizacaoGPS) {
        super(id, localizacaoGPS);
    }

    @Override
    public double getCusto(Utilizador u) {
        return u.getPreco(this);
    }

    @Override
    public String toString() {
        return "Bicicleta Dupla (ID: " + id + ")";
    }
}