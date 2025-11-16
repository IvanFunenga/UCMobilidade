package mobilidade.utilizadores;

import java.util.ArrayList;

public class Docente extends Funcionario{
    private ArrayList<String> listaFaculdades; // Qual a melhor forma de guardar os dados

    public Docente(){}

    public Docente(String nome, String numMecanografico, String metodoPagamento, int anoContrato, ArrayList<String> listaFaculdades) {
        super(nome, numMecanografico, metodoPagamento,  anoContrato);
        this.listaFaculdades = listaFaculdades;
    }

    public ArrayList<String> getListaFaculdades() {
        return listaFaculdades;
    }

    public void setListaFaculdades(ArrayList<String> listaFaculdades) {
        this.listaFaculdades = listaFaculdades;
    }

    @Override
    public String toString() {
        return "Docente{" + "listaFaculdades=" + listaFaculdades + '}';
    }
}
