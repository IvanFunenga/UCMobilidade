package mobilidade;

import mobilidade.utilizadores.*;
import mobilidade.veiculos.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorMobilidade {
    private ArrayList<Utilizador> utilizadores;
    private ArrayList<Veiculo> veiculos;
    private ArrayList<Aluguer> alugueres;

    public GestorMobilidade() {
        this.utilizadores = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.alugueres = new ArrayList<>();
        this.alugueres = new ArrayList<>();
    }

    // ==========================================
    // 1. LEITURA DE DADOS (FICHEIROS DE TEXTO)
    // ==========================================

    public void carregarUtilizadores(String nomeFicheiro) {
        File f = new File(nomeFicheiro);
        if (!f.exists()) {
            System.out.println("Ficheiro " + nomeFicheiro + " não existe.");
            return;
        }

        int numeroLinha = 0;

        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) { // hasNextLine retorna true caso exista mais linhas
                String linhaTexto = scanner.nextLine();
                numeroLinha++;

                if (linhaTexto.trim().isEmpty()) continue; // Ignorar linhas vazias

                String[] dados = linhaTexto.split(";");

                if (dados.length < 4) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Dados insuficientes (esperados min. 4, encontrados " + dados.length + "). Conteúdo: " + linhaTexto);
                    continue;
                }

                // Recolhe os dados que são iguais independentemente do tipo de utilizador
                String tipo = dados[0].trim().toUpperCase();
                String nome = dados[1].trim();
                String id = dados[2].trim();
                String pagamento = dados[3].trim();

                try {
                    switch (tipo) {
                        case "ESTUDANTE":
                            // ESTUDANTE;Nome;ID;Pagamento;Curso;Polo
                            if (dados.length >= 6) {
                                String curso = dados[4].trim();
                                String polo = dados[5].trim();
                                utilizadores.add(new Estudante(nome, id, pagamento, curso, polo));
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Estudante com campos em falta. Esperado Curso e Pólo.");
                            }
                            break;

                        case "NAODOCENTE":
                            // NAODOCENTE;Nome;ID;Pagamento;Ano;Departamento
                            if (dados.length >= 6) {
                                int ano = Integer.parseInt(dados[4].trim());
                                String servico = dados[5].trim();
                                utilizadores.add(new NaoDocente(nome, id, pagamento, ano, servico));
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Não Docente com campos em falta. Esperado Ano e Serviço.");
                            }
                            break;

                        case "DOCENTE":
                            // DOCENTE;Nome;ID;Pagamento;Ano;Fac1;Fac2...
                            if (dados.length >= 6) {
                                int ano = Integer.parseInt(dados[4].trim());
                                ArrayList<String> faculdades = new ArrayList<>();
                                for (int i = 5; i < dados.length; i++) {
                                    faculdades.add(dados[i].trim());
                                }

                                utilizadores.add(new Docente(nome, id, pagamento, ano, faculdades));
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Docente com campos em falta. Esperado Ano e pelo menos uma Faculdade.");
                            }
                            break;

                        default:
                            System.out.println("[Linha " + numeroLinha + "] Erro: Tipo de utilizador desconhecido (" + tipo + ").");
                    }
                } catch (Exception e) {
                    System.out.println("[Linha " + numeroLinha + "] Erro crítico ao criar objeto: " + e.getMessage());
                }
            }
            System.out.println(utilizadores.size() + " carregados com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir ficheiro: " + e.getMessage());
        }
    }

    public void carregarVeiculos(String nomeFicheiro) {
        File f = new File(nomeFicheiro);
        if (!f.exists()) {
            System.out.println("Ficheiro " + nomeFicheiro + " não existe.");
            return;
        }

        int numeroLinha = 0;

        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                String linhaTexto = scanner.nextLine();
                numeroLinha++;

                if (linhaTexto.trim().isEmpty()) continue; // Ignorar linhas vazias

                String[] dados = linhaTexto.split(";");

                // Validação mínima: TIPO;ID;LOCALIZAÇÃO (pelo menos 3 campos obrigatórios)
                if (dados.length < 3) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Dados insuficientes. Conteúdo: " + linhaTexto);
                    continue;
                }

                String tipo = dados[0].trim().toUpperCase();
                String id = dados[1].trim();
                String gps = dados[2].trim();

                try {
                    switch (tipo) {
                        case "BICICLETA":
                            // Formato: BICICLETA;ID;GPS;TIPO(INDIVIDUAL/DUPLA)
                            if (dados.length >= 4) {
                                String subTipo = dados[3].trim().toUpperCase();
                                if (subTipo.equals("DUPLA")) {
                                    veiculos.add(new BicicletaDupla(id, gps));
                                } else {
                                    // Assume individual se for "INDIVIDUAL" ou outro valor
                                    veiculos.add(new BicicletaIndividual(id, gps));
                                }
                            } else {
                                // Default se faltar o subtipo
                                veiculos.add(new BicicletaIndividual(id, gps));
                            }
                            break;

                        case "TROTINETE":
                            // Formato: TROTINETE;ID;GPS;AUTONOMIA;TEM_LCD(SIM/NAO)
                            if (dados.length >= 5) {
                                int autonomia = Integer.parseInt(dados[3].trim());
                                String temLCD = dados[4].trim().toUpperCase();

                                // Instancia a classe específica baseada na flag
                                if (temLCD.equals("SIM") || temLCD.equals("COM_LCD") || temLCD.equals("TRUE")) {
                                    veiculos.add(new TrotineteComLCD(id, gps, autonomia));
                                } else {
                                    veiculos.add(new TrotineteSemLCD(id, gps, autonomia));
                                }
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Trotinete requer Autonomia e info de LCD.");
                            }
                            break;

                        case "EBIKE":
                            // Formato: EBIKE;ID;GPS;AUTONOMIA;BATERIA(FIXA/REMOVIVEL)
                            if (dados.length >= 5) {
                                int autonomia = Integer.parseInt(dados[3].trim());
                                String tipoBateria = dados[4].trim().toUpperCase();

                                // Instancia a classe específica baseada no tipo de bateria
                                if (tipoBateria.equals("REMOVIVEL")) {
                                    veiculos.add(new EBikeBateriaRemovivel(id, gps, autonomia));
                                } else {
                                    veiculos.add(new EBikeBateriaFixa(id, gps, autonomia));
                                }
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: E-Bike requer Autonomia e tipo de Bateria.");
                            }
                            break;

                        default:
                            System.out.println("[Linha " + numeroLinha + "] Tipo de veículo desconhecido: " + tipo);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Formato numérico inválido (provavelmente na Autonomia).");
                } catch (Exception e) {
                    System.out.println("[Linha " + numeroLinha + "] Erro crítico ao criar veículo: " + e.getMessage());
                }
            }
            System.out.println(veiculos.size() + " veículos carregados com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir ficheiro de veículos.");
        }
    }

    // ==========================================
    // 2. PERSISTÊNCIA DE ALUGUERES (BINÁRIO)
    // ==========================================

    public void carregarAlugueres(String nomeFicheiro) {
        File f = new File(nomeFicheiro);
        if (!f.exists()) {
            System.out.println("Nenhum histórico de alugueres encontrado (primeira execução).");
            return;
        }

        // TODO: Implementar leitura de objetos
        // try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
        //     this.alugueres = (ArrayList<Aluguer>) ois.readObject();
        // } catch ...
    }

    public void guardarAlugueres(String nomeFicheiro) {
        // TODO: Implementar escrita de objetos
        // try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
        //     oos.writeObject(this.alugueres);
        // } catch ...
        System.out.println("A guardar alugueres... (Não implementado)");
    }

    // ==========================================
    // 3. MÉTODOS DE APOIO À MAIN (Interação)
    // ==========================================

    /**
     * Procura um utilizador pelo número mecanográfico.
     * @return O objeto Utilizador ou null se não existir.
     */
    public Utilizador procurarUtilizador(String id) {
        for (Utilizador u : utilizadores) {
            if (u.getNumeroMecanografico().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Procura um veículo pelo ID (ex: B101).
     * @return O objeto Veiculo ou null se não existir.
     */
    public Veiculo procurarVeiculo(String id) {
        for (Veiculo v : veiculos) {
            if (v.getId().equalsIgnoreCase(id)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Adiciona um novo aluguer à lista.
     */
    public void registarAluguer(Aluguer a) {
        this.alugueres.add(a);
    }

    /**
     * Devolve a lista de alugueres para ser mostrada na Main.
     */
    public ArrayList<Aluguer> getAlugueres() {
        return this.alugueres;
    }

    // Getters opcionais (para debug ou listagens simples)
    public ArrayList<Utilizador> getUtilizadores() { return utilizadores; }
    public ArrayList<Veiculo> getVeiculos() { return veiculos; }
}
