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
        // Tratamento de exceções básico no carregamento (caso os ficheiros não existam ou estejam corrompidos)
        try {
            gestor.carregarUtilizadores("dados/utilizadores.txt");
            gestor.carregarVeiculos("dados/veiculos.txt");
            gestor.carregarAlugueres("dados/alugueres.obj");
        } catch (Exception e) {
            System.out.println("Aviso: Erro ao carregar alguns dados iniciais. O sistema iniciará com dados vazios ou parciais.");
            // Log do erro para debug se necessário: e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    criarAluguer(sc);
                    break;
                case 2:
                    listarAlugueres();
                    break;
                case 0:
                    terminarPrograma();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- Mobilidade UC ---");
        System.out.println("1. Criar Aluguer (Manual)");
        System.out.println("2. Listar Alugueres");
        System.out.println("0. Terminar");
    }

    private static void terminarPrograma() {
        System.out.println("A terminar...");
        try {
            gestor.guardarAlugueres("dados/alugueres.obj");
            System.out.println("Dados guardados com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro crítico ao guardar dados: " + e.getMessage());
        }
    }

    /**
     * Funcionalidade 1: Criar um novo Aluguer com validação robusta.
     */
    private static void criarAluguer(Scanner sc) {
        System.out.println("\n--- Novo Aluguer (Manual) ---");

        // 1. Obter e validar Utilizador
        Utilizador utilizador = null;
        while (utilizador == null) {
            String numMec = lerTextoObrigatorio(sc, "Insira o número mecanográfico do utilizador (ou 'sair' para cancelar): ");

            if (numMec.equalsIgnoreCase("sair")) return;

            utilizador = gestor.procurarUtilizador(numMec);
            if (utilizador == null) {
                System.out.println("Erro: Utilizador não encontrado. Tente novamente.");
            }
        }
        System.out.println(">> Utilizador identificado: " + utilizador.getNome());

        // 2. Obter e validar Veículo
        Veiculo veiculo = null;
        while (veiculo == null) {
            String idVeiculo = lerTextoObrigatorio(sc, "Insira o ID do veículo (ex: B101) (ou 'sair' para cancelar): ");

            if (idVeiculo.equalsIgnoreCase("sair")) return;

            veiculo = gestor.procurarVeiculo(idVeiculo);

            if (veiculo == null) {
                System.out.println("Erro: Veículo não encontrado.");
            } else if (veiculo.isEmUso()) {
                System.out.println("Erro: O veículo " + veiculo.getId() + " já se encontra em uso. Escolha outro.");
                veiculo = null; // Reseta para continuar no loop
            }
        }
        System.out.println(">> Veículo identificado: " + veiculo.toString());

        // 3. Calcular Duração
        LocalDateTime inicio = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        double duracaoHoras = lerDecimalPositivo(sc, "Insira a duração do aluguer em horas (ex: 1.5): ");

        long minutos = (long) (duracaoHoras * 60);
        LocalDateTime fim = inicio.plusMinutes(minutos);

        // 4. Opções adicionais
        boolean capacete = lerBooleano(sc, "Incluir capacete (5€/dia)? (s/n): ");
        boolean luz = lerBooleano(sc, "Incluir luz (2.5€/dia)? (s/n): ");

        // 5. Finalizar Registo
        try {
            Aluguer novoAluguer = new Aluguer(utilizador, veiculo, inicio, fim, capacete, luz);
            gestor.registarAluguer(novoAluguer);
            veiculo.setEmUso(true);

            System.out.println("\n" + novoAluguer.getResumo());
            System.out.println("SUCESSO: Aluguer criado e registado.");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao registar aluguer: " + e.getMessage());
        }
    }

    private static void listarAlugueres() {
        System.out.println("\n--- Lista de Todos os Alugueres ---");

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

    // ==========================================================
    // MÉTODOS AUXILIARES DE LEITURA E VALIDAÇÃO (INPUT SECURE)
    // ==========================================================

    /**
     * Lê um número inteiro do utilizador. Continua a pedir até que um valor válido seja inserido.
     */
    private static int lerInteiro(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor insira um número inteiro.");
            }
        }
    }

    /**
     * Lê um número decimal positivo. Continua a pedir até ser válido e maior que zero.
     */
    private static double lerDecimalPositivo(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine().trim().replace(',', '.'); // Aceita vírgula ou ponto
            try {
                double valor = Double.parseDouble(input);
                if (valor <= 0) {
                    System.out.println("Erro: O valor deve ser superior a 0.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor insira um número decimal (ex: 1.5).");
            }
        }
    }

    /**
     * Lê uma String e garante que não está vazia ou composta apenas por espaços.
     */
    private static String lerTextoObrigatorio(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Erro: Este campo não pode estar vazio.");
        }
    }

    /**
     * Lê uma resposta Sim/Não de forma robusta.
     */
    private static boolean lerBooleano(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("s") || input.equals("sim") || input.equals("y")) {
                return true;
            } else if (input.equals("n") || input.equals("nao") || input.equals("não")) {
                return false;
            }
            System.out.println("Erro: Responda apenas com 's' (sim) ou 'n' (não).");
        }
    }
}