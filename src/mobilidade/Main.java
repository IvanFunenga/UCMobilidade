package mobilidade;

// --- IMPORTES NECESSÁRIOS ---
import mobilidade.utilizadores.Docente;
import mobilidade.utilizadores.Estudante;
import mobilidade.utilizadores.NaoDocente;
import mobilidade.utilizadores.Utilizador;
import mobilidade.veiculos.BicicletaDupla;
import mobilidade.veiculos.BicicletaIndividual;
import mobilidade.veiculos.EBikeBateriaFixa;
import mobilidade.veiculos.EBikeBateriaRemovivel;
import mobilidade.veiculos.TrotineteComLCD;
import mobilidade.veiculos.TrotineteSemLCD;
import mobilidade.veiculos.Veiculo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// --- FIM DOS IMPORTES ---

public class Main {

    // Listas para armazenar os dados em memória
    private static List<Utilizador> utilizadores = new ArrayList<>();
    private static List<Veiculo> veiculos = new ArrayList<>();
    private static List<Aluguer> alugueres = new ArrayList<>();

    public static void main(String[] args) {
        // Carrega dados de exemplo para teste
        inicializarDados();

        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- 🚲 Mobilidade UC 🚲 ---");
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
                    case 9:
                        testarCalculos();
                        break;
                    case 0:
                        System.out.println("A terminar...");
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
        Utilizador utilizador = findUtilizador(numMec);

        if (utilizador == null) {
            System.out.println("Erro: Utilizador não encontrado.");
            return;
        }
        System.out.println("Utilizador encontrado: " + utilizador.getNome());

        System.out.print("Insira o ID do veículo (ex: B101): ");
        String idVeiculo = sc.nextLine();
        Veiculo veiculo = findVeiculo(idVeiculo);

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
        alugueres.add(novoAluguer);
        veiculo.setEmUso(true);

        System.out.println(novoAluguer.getResumo());
        System.out.println("Aluguer criado com sucesso!");
    }

    /**
     * Funcionalidade 2: Listar todos os alugueres registados.
     */
    private static void listarAlugueres() {
        System.out.println("\n--- 🧾 Lista de Todos os Alugueres 🧾 ---");

        if (alugueres.isEmpty()) {
            System.out.println("Não existem alugueres registados.");
            return;
        }

        double valorTotal = 0.0;

        for (Aluguer aluguer : alugueres) {
            System.out.println(aluguer.getResumo());
            valorTotal += aluguer.calcularCusto();
        }

        System.out.println("----------------------------------------");
        System.out.printf("💰 VALOR TOTAL DE ALUGUERES: %.2f €\n", valorTotal);
        System.out.println("----------------------------------------");
    }

    /**
     * Funcionalidade 9: Executa uma prova de conceito (Proof of Concept)
     */
    private static void testarCalculos() {
        System.out.println("\n--- ⚙️ Iniciando Teste de Prova de Conceito ⚙️ ---");

        // Obter utilizadores e veículos de teste
        Utilizador naoDocente = findUtilizador("20050002");
        Utilizador estudante = findUtilizador("2020123456");
        Veiculo bicicleta = findVeiculo("B101");
        Veiculo trotineteLCD = findVeiculo("T401");
        Veiculo eBike = findVeiculo("E601");

        if (naoDocente == null || estudante == null || bicicleta == null || trotineteLCD == null || eBike == null) {
            System.out.println("Erro: Dados de teste (utilizadores/veículos) não encontrados.");
            return;
        }

        LocalDateTime agora = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        // --- TESTE 1 ---
        System.out.println("\n--- TESTE 1: NaoDocente (50% Desconto) ---");
        LocalDateTime fimTeste1 = agora.plusMinutes(150); // 2.5 horas
        Aluguer aluguer1 = new Aluguer(naoDocente, bicicleta, agora, fimTeste1, true, false);
        alugueres.add(aluguer1);
        System.out.println(aluguer1.getResumo());

        // --- TESTE 2 ---
        System.out.println("\n--- TESTE 2: NaoDocente (Regra 24h = 8h) ---");
        LocalDateTime fimTeste2 = agora.plusHours(26);
        Aluguer aluguer2 = new Aluguer(naoDocente, trotineteLCD, agora, fimTeste2, false, true);
        alugueres.add(aluguer2);
        System.out.println(aluguer2.getResumo());

        // --- TESTE 3 ---
        System.out.println("\n--- TESTE 3: Estudante (Sem Desconto) ---");
        LocalDateTime fimTeste3 = agora.plusHours(3);
        Aluguer aluguer3 = new Aluguer(estudante, eBike, agora, fimTeste3, false, false);
        alugueres.add(aluguer3);
        System.out.println(aluguer3.getResumo());

        System.out.println("\n--- ✅ Teste de Prova de Conceito Concluído ✅ ---");
        System.out.println("(Os alugueres de teste foram adicionados à lista de alugueres)");

    }

    // --- Métodos Auxiliares (Helpers) ---

    /**
     * Procura um utilizador na lista pelo seu número mecanográfico.
     */
    private static Utilizador findUtilizador(String numMec) {
        for (Utilizador u : utilizadores) {
            // CORREÇÃO: getNumeroMecanografico (só um 'c' e 'a' no meio)
            if (u.getNumeroMecanografico().equalsIgnoreCase(numMec)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Procura um veículo na lista pelo seu ID.
     */
    private static Veiculo findVeiculo(String id) {
        for (Veiculo v : veiculos) {
            if (v.getId().equalsIgnoreCase(id)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Simula o carregamento dos ficheiros de texto,
     * pré-preenchendo as listas com dados.
     */
    private static void inicializarDados() {
        // Adicionar Utilizadores (AGORA COM NOMES REAIS)
        utilizadores.add(new Estudante("Rodrigo Ceia", "2020123456", "MBWay", "Eng. Informática", "Pólo II"));
        utilizadores.add(new Docente("Aníbal Santos", "19950001", "Cartão", 2010, List.of("DEI", "DEM")));
        utilizadores.add(new NaoDocente("Manuel Silva", "20050002", "Cartão", 2015, "Serviços Académicos"));

        // Adicionar Veículos
        veiculos.add(new BicicletaIndividual("B101", "Pólo I"));
        veiculos.add(new BicicletaDupla("B201", "Pólo II"));
        veiculos.add(new TrotineteSemLCD("T301", "Pólo I", 80));
        veiculos.add(new TrotineteComLCD("T401", "Pólo III", 90));
        veiculos.add(new EBikeBateriaFixa("E501", "Pólo II", 100));
        veiculos.add(new EBikeBateriaRemovivel("E601", "Pólo I", 75));

        System.out.println("Dados de teste carregados.");
    }
}