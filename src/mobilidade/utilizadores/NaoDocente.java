package mobilidade.utilizadores;

import java.util.ArrayList;

public class NaoDocente extends Funcionario{
    private String servico;

    public NaoDocente(){}

    public NaoDocente(String nome, String numMecanografico, String metodoPagamento, int anoContrato, String servico) {
        super(nome, numMecanografico, metodoPagamento,  anoContrato);
        this.servico = servico;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String toString(){
        return "NaoDocente{" + "servico=" + servico + '}';
    }
}