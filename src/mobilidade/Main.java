package mobilidade;
import java.util.Scanner;
import mobilidade.utilizadores.*;

public class Main {
    public static void main(String[] args) {
        Estudante e =  new Estudante("Ivan", "2023222432", "MBway", "Engenharia Informática", "Polo 2");
        e.toString();
        System.out.println(e.toString());
    }
}
