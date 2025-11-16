package mobilidade.utilizadores;

// IMPORTES NECESSÁRIOS (Adicione esta secção)
import mobilidade.veiculos.BicicletaDupla;
import mobilidade.veiculos.BicicletaIndividual;
import mobilidade.veiculos.EBikeBateriaFixa;
import mobilidade.veiculos.EBikeBateriaRemovivel;
import mobilidade.veiculos.TrotineteComLCD;
import mobilidade.veiculos.TrotineteSemLCD;

public abstract class Funcionario extends Utilizador {
    protected int anoContrato;

    public Funcionario(String nome, String numeroMecanografico, String metodoPagamento, int anoContrato) {
        super(nome, numeroMecanografico, metodoPagamento);
        this.anoContrato = anoContrato;
    }

    // O Java precisa dos 'imports' para reconhecer estas classes
    @Override
    public double getPreco(BicicletaIndividual v) { return 2.00; }
    @Override
    public double getPreco(BicicletaDupla v) { return 3.00; }
    @Override
    public double getPreco(TrotineteSemLCD v) { return 2.50; }
    @Override
    public double getPreco(TrotineteComLCD v) { return 2.60; }
    @Override
    public double getPreco(EBikeBateriaFixa v) { return 2.75; }
    @Override
    public double getPreco(EBikeBateriaRemovivel v) { return 3.00; }
}