package mobilidade.veiculos;

public abstract class VeiculoEletrico extends Veiculo{
    private int bateria;

    public VeiculoEletrico(){}

    public VeiculoEletrico(int id, String localizacao, int bateria){
        super(id, localizacao);
        this.bateria = bateria;
    }

    public int getBateria() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }
}
