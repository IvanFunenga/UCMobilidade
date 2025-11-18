package mobilidade;

import mobilidade.utilizadores.Utilizador;
import mobilidade.veiculos.Veiculo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Aluguer {
    private Utilizador utilizador;
    private Veiculo veiculo;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private boolean incluiCapacete;
    private boolean incluiLuz;

    public Aluguer(Utilizador utilizador, Veiculo veiculo, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, boolean incluiCapacete, boolean incluiLuz) {
        this.utilizador = utilizador;
        this.veiculo = veiculo;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.incluiCapacete = incluiCapacete;
        this.incluiLuz = incluiLuz;
    }

    /**
     * Calcula o custo total do aluguer, aplicando todas as regras do enunciado.
     * @return O custo final em Euros.
     */
    public double calcularCusto() {
        // 1. Calcular Duração
        Duration duracao = Duration.between(dataHoraInicio, dataHoraFim);
        long totalMinutos = duracao.toMinutes(); // Necessita de ser long para realizar .tominutes()
        if (totalMinutos <= 0) return 0.0;

        // 2. Calcular Horas Faturadas (Regra das 24h = 8h) [cite: 29]
        long diasCompletos = totalMinutos / (24 * 60); // Necessitam ser long devido à variável totalMinutos
        long minutosRestantes = totalMinutos % (24 * 60);

        // Converte minutos restantes para horas fracionadas
        double horasFaturadas = (diasCompletos * 8.0) + (minutosRestantes / 60.0);

        // 3. Custo Base do Veículo (usando Polimorfismo/Visitor)
        // O utilizador sabe o preço do veículo
        double custoHoraVeiculo = veiculo.getCusto(utilizador);
        double custoTotalVeiculo = horasFaturadas * custoHoraVeiculo;

        long diasServico;
        if (minutosRestantes > 0) {
            diasServico = diasCompletos + 1;
        } else {
            diasServico = diasCompletos;
        }

        double custoServicos = 0.0;
        if (incluiCapacete) {
            custoServicos += diasServico * 5.0; // 5 Euros por dia [cite: 30]
        }
        if (incluiLuz) {
            custoServicos += diasServico * 2.5; // 2.5 Euros por dia [cite: 30]
        }

        // 5. Custo Total Parcial
        double custoTotal = custoTotalVeiculo + custoServicos;

        // 6. Aplicar Desconto do Utilizador (Polimorfismo)
        // NaoDocente retorna 0.5, outros retornam 1.0
        double custoFinal = custoTotal * utilizador.getFatorDesconto();

        return custoFinal;
    }

    /**
     * Gera um resumo do aluguer.
     * @return String formatada com o resumo.
     */
    public String getResumo() {
        double custo = calcularCusto();
        return String.format(
                "--- Resumo do Aluguer ---\n" +
                        "> Utilizador: %s (%s)\n" +
                        "> Veículo: %s\n" +
                        "> Início: %s\n" +
                        "> Fim: %s\n" +
                        "> Serviços: %s\n" +
                        "> Custo Final: %.2f €\n",
                utilizador.getNome(), utilizador.getNumeroMecanografico(),
                veiculo.toString(),
                dataHoraInicio.toString(),
                dataHoraFim.toString(),
                (incluiCapacete ? "Capacete " : "") + (incluiLuz ? "Luz" : ""),
                custo
        );
    }
}