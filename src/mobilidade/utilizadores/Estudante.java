package mobilidade.utilizadores;

// IMPORTES NECESSÁRIOS (Adicione esta secção)
import mobilidade.veiculos.BicicletaDupla;
import mobilidade.veiculos.BicicletaIndividual;
import mobilidade.veiculos.EBikeBateriaFixa;
import mobilidade.veiculos.EBikeBateriaRemovivel;
import mobilidade.veiculos.TrotineteComLCD;
import mobilidade.veiculos.TrotineteSemLCD;

public class Estudante extends Utilizador {
    private String curso;
    private String polo;

    public Estudante(String nome, String numeroMecanografico, String metodoPagamento, String curso, String polo) {
        super(nome, numeroMecanografico, metodoPagamento);
        this.curso = curso;
        this.polo = polo;
    }

    // O Java precisa dos 'imports' para reconhecer estas classes
    @Override
    public double getPreco(BicicletaIndividual v) { return 1.00; }
    @Override
    public double getPreco(BicicletaDupla v) { return 2.00; }
    @Override
    public double getPreco(TrotineteSemLCD v) { return 1.00; }
    @Override
    public double getPreco(TrotineteComLCD v) { return 1.10; }
    @Override
    public double getPreco(EBikeBateriaFixa v) { return 1.25; }
    @Override
    public double getPreco(EBikeBateriaRemovivel v) { return 1.50; }
}