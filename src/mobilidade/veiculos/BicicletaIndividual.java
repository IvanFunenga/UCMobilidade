package mobilidade.veiculos;

import mobilidade.utilizadores.Utilizador;

public class BicicletaIndividual extends Bicicleta {
    public BicicletaIndividual(String id, String localizacaoGPS) {
        super(id, localizacaoGPS);
    }

    @Override
    public double getCusto(Utilizador u) {
        // Padrão Visitor: "Visita" o utilizador para saber o preço
        return u.getPreco(this);
    }

    @Override
    public String toString() {
        return "Bicicleta Individual (ID: " + id + ")";
    }
}