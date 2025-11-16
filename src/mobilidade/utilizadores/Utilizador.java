package mobilidade.utilizadores;

// IMPORTES NECESSÁRIOS (Adicione esta secção)
import mobilidade.veiculos.BicicletaDupla;
import mobilidade.veiculos.BicicletaIndividual;
import mobilidade.veiculos.EBikeBateriaFixa;
import mobilidade.veiculos.EBikeBateriaRemovivel;
import mobilidade.veiculos.TrotineteComLCD;
import mobilidade.veiculos.TrotineteSemLCD;

/**
 * Classe abstrata que representa um Utilizador do serviço.
 */
public abstract class Utilizador {
    protected String nome;
    protected String numeroMecanografico;
    protected String metodoPagamento;

    public Utilizador(String nome, String numeroMecanografico, String metodoPagamento) {
        this.nome = nome;
        this.numeroMecanografico = numeroMecanografico;
        this.metodoPagamento = metodoPagamento;
    }

    public String getNumeroMecanografico() {
        return numeroMecanografico;
    }

    public String getNome() {
        return nome;
    }

    public double getFatorDesconto() {
        return 1.0; // Sem desconto por defeito
    }

    // --- Métodos do Padrão Visitor para Preços ---
    // O Java precisa dos 'imports' para reconhecer estas classes
    public abstract double getPreco(BicicletaIndividual v);
    public abstract double getPreco(BicicletaDupla v);
    public abstract double getPreco(TrotineteSemLCD v);
    public abstract double getPreco(TrotineteComLCD v);
    public abstract double getPreco(EBikeBateriaFixa v);
    public abstract double getPreco(EBikeBateriaRemovivel v);
}