package mobilidade.utilizadores;

import java.util.List;

public class Docente extends Funcionario {
    private List<String> faculdades;

    // Construtor atualizado para incluir 'nome'
    public Docente(String nome, String numeroMecanografico, String metodoPagamento, int anoContrato, List<String> faculdades) {
        super(nome, numeroMecanografico, metodoPagamento, anoContrato);
        this.faculdades = faculdades;
    }
}