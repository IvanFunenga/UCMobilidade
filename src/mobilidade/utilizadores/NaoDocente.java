package mobilidade.utilizadores;

public class NaoDocente extends Funcionario {
    private String servico;

    // Construtor atualizado para incluir 'nome'
    public NaoDocente(String nome, String numeroMecanografico, String metodoPagamento, int anoContrato, String servico) {
        super(nome, numeroMecanografico, metodoPagamento, anoContrato); // Passa o nome para a superclasse
        this.servico = servico;
    }

    /**
     * Sobrescreve o método para aplicar o desconto de 50%.
     * @return 0.5 (metade do preço)
     */
    @Override
    public double getFatorDesconto() {
        return 0.5;
    }
}