package mobilidade.utilizadores;

public abstract class Utilizador {
    private String nome;
    private String numMecanografico;
    private String metodoPagamento;

    public Utilizador(){}

    public Utilizador(String nome, String numMecanografico, String metodoPagamento) {
        this.nome = nome;
        this.numMecanografico = numMecanografico;
        this.metodoPagamento = metodoPagamento;
    }

    public String getNome() {
        return nome;
    }

    public String getNumMecanografico() {
        return numMecanografico;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumMecanografico(String numMecanografico) {
        this.numMecanografico = numMecanografico;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    @Override
    public String toString() {
        String s = nome + " com o número mecanográfico (" + numMecanografico + ") usa o " + metodoPagamento;
        return s;
    }
}
