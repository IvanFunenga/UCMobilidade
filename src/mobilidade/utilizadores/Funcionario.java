package mobilidade.utilizadores;

public abstract class Funcionario extends Utilizador{
    private int anoContrato;

    public Funcionario() {}

    public Funcionario(String nome, String numMecanografico, String metodoPagamento, int anoContrato) {
        super(nome, numMecanografico, metodoPagamento);
        this.anoContrato = anoContrato;
    }

    public int getAnoContrato() {
        return anoContrato;
    }

    public void setAnoContrato(int anoContrato) {
        this.anoContrato = anoContrato;
    }

    @Override
    public String toString() {
        String s = "Fucnionário contrado em " + anoContrato;
        return s;
    }
}
