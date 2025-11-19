package mobilidade;

import mobilidade.utilizadores.Utilizador;
import mobilidade.veiculos.Veiculo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {

    private static GestorMobilidade gestor = new GestorMobilidade();

    public static void main(String[] args) {
        System.out.println("A carregar dados...");
        gestor.carregarUtilizadores("dados/utilizadores.txt");
        gestor.carregarVeiculos("dados/veiculos.txt");
        gestor.carregarAlugueres("dados/alugueres.obj");

        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n---  Mobilidade UC  ---");
            System.out.println("1. Criar Aluguer (Manual)");
            System.out.println("2. Listar Alugueres");
            System.out.println("9. Correr Teste de Prova (Proof of Concept)");
            System.out.println("0. Terminar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        criarAluguer(sc);
                        break;
                    case 2:
                        listarAlugueres();
                        break;
                    case 0:
                        System.out.println("A terminar...");
                        gestor.guardarAlugueres("dados/alugueres.obj");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Insira um número válido.");
            }
        }
        sc.close();
    }

    /**
     * Funcionalidade 1: Criar um novo Aluguer (interativo).
     */
    private static void criarAluguer(Scanner sc) {
        System.out.println("\n--- Novo Aluguer (Manual) ---");

        // ... (código do criarAluguer) ...
        System.out.print("Insira o número mecanográfico do utilizador: ");
        String numMec = sc.nextLine();
        Utilizador utilizador = gestor.procurarUtilizador(numMec);

        if (utilizador == null) {
            System.out.println("Erro: Utilizador não encontrado.");
            return;
        }
        System.out.println("Utilizador encontrado: " + utilizador.getNome());

        System.out.print("Insira o ID do veículo (ex: B101): ");
        String idVeiculo = sc.nextLine();
        Veiculo veiculo = gestor.procurarVeiculo(idVeiculo);

        if (veiculo == null) {
            System.out.println("Erro: Veículo não encontrado.");
            return;
        }
        if (veiculo.isEmUso()) {
            System.out.println("Erro: Veículo " + veiculo.getId() + " já está em uso.");
            return;
        }
        System.out.println("Veículo encontrado: " + veiculo.toString());

        LocalDateTime inicio = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        System.out.print("Insira a duração do aluguer em horas (ex: 1.5 para 90 min): ");
        double duracaoHoras = 0;
        try {
            duracaoHoras = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Duração inválida. A assumir 1 hora.");
            duracaoHoras = 1.0;
        }
        long minutos = (long) (duracaoHoras * 60);
        LocalDateTime fim = inicio.plusMinutes(minutos);

        System.out.print("Incluir capacete (5€/dia)? (s/n): ");
        boolean capacete = sc.nextLine().equalsIgnoreCase("s");

        System.out.print("Incluir luz (2.5€/dia)? (s/n): ");
        boolean luz = sc.nextLine().equalsIgnoreCase("s");

        Aluguer novoAluguer = new Aluguer(utilizador, veiculo, inicio, fim, capacete, luz);
        gestor.registarAluguer(novoAluguer);
        veiculo.setEmUso(true);

        System.out.println(novoAluguer.getResumo());
        System.out.println("Aluguer criado com sucesso!");
    }

    /**
     * Funcionalidade 2: Listar todos os alugueres registados.
     */
    private static void listarAlugueres() {
        System.out.println("\n---  Lista de Todos os Alugueres  ---");

        if (gestor.getAlugueres().isEmpty()) {
            System.out.println("Não existem alugueres registados.");
            return;
        }

        double valorTotal = 0.0;

        for (Aluguer aluguer : gestor.getAlugueres()) {
            System.out.println(aluguer.getResumo());
            valorTotal += aluguer.calcularCusto();
        }

        System.out.println("----------------------------------------");
        System.out.printf(" VALOR TOTAL DE ALUGUERES: %.2f €\n", valorTotal);
        System.out.println("----------------------------------------");
    }
}