package mobilidade.utilizadores;

public class Estudante  extends Utilizador{
    private String curso;
    private String polo;

    public Estudante() {}

    public Estudante(String nome, String numMecanografico, String metodoPagamento, String curso, String polo) {
        super(nome, numMecanografico, metodoPagamento);
        this.curso = curso;
        this.polo = polo;
    }

    public String getCurso() {
        return curso;
    }

    public String getPolo() {
        return polo;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setPolo(String polo) {
        this.polo = polo;
    }

    @Override
    public String toString() {
        String s = "Estudante de " + curso + " que estuda no " + polo;
        return s;
    }
}
