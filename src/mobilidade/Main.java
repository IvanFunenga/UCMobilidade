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

        try {
            gestor.carregarUtilizadores("dados/utilizadores.txt");
            gestor.carregarVeiculos("dados/veiculos.txt");
            gestor.carregarAlugueres("dados/alugueres.obj");
        } catch (Exception e) {
            System.out.println("Aviso: Erro ao carregar alguns dados iniciais.");
        }

        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");

            // Tratamento de exceção local para o menu
            try {
                String input = sc.nextLine();
                opcao = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Erro: A opção deve ser um número inteiro.");
                opcao = -1;
            }

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
                    if (opcao != -1) System.out.println("Opção inválida.");
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
     * Funcionalidade 1: Criar um novo Aluguer.
     */
    private static void criarAluguer(Scanner sc) {
        System.out.println("\n--- Novo Aluguer (Manual) ---");

        // 1. Obter e validar Utilizador (10 Dígitos)
        Utilizador utilizador = null;
        while (utilizador == null) {
            // Usa a função genérica pedindo 10 dígitos
            String numMec = lerCodigoNumerico(sc, "Insira o n. mecanográfico (10 dígitos) ou 'sair': ", 10);

            if (numMec.equalsIgnoreCase("sair")) return;

            utilizador = gestor.procurarUtilizador(numMec);
            if (utilizador == null) {
                System.out.println("Erro: Utilizador não encontrado na base de dados.");
            }
        }
        System.out.println(">> Utilizador identificado: " + utilizador.getNome());

        // 2. Obter e validar Veículo (4 Dígitos)
        Veiculo veiculo = null;
        while (veiculo == null) {
            // Usa a mesma função genérica, mas pedindo 4 dígitos
            String idVeiculo = lerCodigoNumerico(sc, "Insira o ID do veículo (4 dígitos) ou 'sair': ", 4);

            if (idVeiculo.equalsIgnoreCase("sair")) return;

            veiculo = gestor.procurarVeiculo(idVeiculo);

            if (veiculo == null) {
                System.out.println("Erro: Veículo não encontrado.");
            } else if (veiculo.isEmUso()) {
                System.out.println("Erro: O veículo " + veiculo.getId() + " já está em uso.");
                veiculo = null;
            }
        }
        System.out.println(">> Veículo identificado: " + veiculo.toString());

        // 3. Calcular Duração
        double duracaoHoras = 0;
        boolean duracaoValida = false;

        while (!duracaoValida) {
            System.out.print("Insira a duração em horas (ex: 1.5): ");
            String input = sc.nextLine().replace(',', '.');
            try {
                duracaoHoras = Double.parseDouble(input);
                if (duracaoHoras > 0) {
                    duracaoValida = true;
                } else {
                    System.out.println("Erro: A duração deve ser positiva.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Valor inválido. Insira um número (ex: 1.5).");
            }
        }

        LocalDateTime inicio = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        long minutos = (long) (duracaoHoras * 60);
        LocalDateTime fim = inicio.plusMinutes(minutos);

        // 4. Opções adicionais
        boolean capacete = lerBooleano(sc, "Incluir capacete (5EUR/dia)? (s/n): ");
        boolean luz = lerBooleano(sc, "Incluir luz (2.5EUR/dia)? (s/n): ");

        // 5. Finalizar Registo
        try {
            Aluguer novoAluguer = new Aluguer(utilizador, veiculo, inicio, fim, capacete, luz);
            gestor.registarAluguer(novoAluguer);
            veiculo.setEmUso(true);

            System.out.println("\n" + novoAluguer.getResumo());
            System.out.println("SUCESSO: Aluguer criado.");
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
        System.out.printf(" VALOR TOTAL: %.2f EUR\n", valorTotal);
        System.out.println("----------------------------------------");
    }

    // ==========================================================
    // MÉTODOS DE LEITURA AUXILIARES
    // ==========================================================

    /**
     * Valida se o input tem exatamente o número de dígitos especificado.
     * @param digitos Quantidade exata de dígitos numéricos exigidos
     */
    private static String lerCodigoNumerico(Scanner sc, String mensagem, int digitos) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("sair")) return "sair";

            // Verifica se tem o tamanho correto e se todos são números (Regex \d+)
            if (input.length() == digitos && input.matches("\\d+")) {
                return input;
            }
            System.out.println("Erro: O código deve ser composto por exatamente " + digitos + " dígitos numéricos.");
        }
    }

    private static boolean lerBooleano(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("s") || input.equals("sim")) {
                return true;
            } else if (input.equals("n") || input.equals("nao") || input.equals("não")) {
                return false;
            }
            System.out.println("Erro: Responda apenas com 's' ou 'n'.");
        }
    }
}