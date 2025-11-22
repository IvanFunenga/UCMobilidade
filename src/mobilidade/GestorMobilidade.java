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
            while (scanner.hasNextLine()) {
                String linhaTexto = scanner.nextLine();
                numeroLinha++;

                if (linhaTexto.trim().isEmpty()) continue; // Ignorar linhas vazias

                String[] dados = linhaTexto.split(";");

                if (dados.length < 4) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Dados insuficientes. Ignorado.");
                    continue;
                }

                String tipo = dados[0].trim().toUpperCase();
                String nome = dados[1].trim();
                String id = dados[2].trim(); // Este é o número mecanográfico
                String pagamento = dados[3].trim();

                // === VALIDAÇÃO DE INPUT (10 DÍGITOS) ===
                if (!validarFormatoId(id, 10)) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: ID de utilizador '" + id + "' inválido. Deve ter exatos 10 dígitos numéricos. Ignorado.");
                    continue;
                }

                // === VALIDAÇÃO DE DUPLICADOS ===
                if (procurarUtilizador(id) != null) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Utilizador com ID " + id + " já existe no sistema. Ignorado.");
                    continue;
                }

                try {
                    switch (tipo) {
                        case "ESTUDANTE":
                            if (dados.length >= 6) {
                                String curso = dados[4].trim();
                                String polo = dados[5].trim();
                                utilizadores.add(new Estudante(nome, id, pagamento, curso, polo));
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Estudante incompleto.");
                            }
                            break;

                        case "NAODOCENTE":
                            if (dados.length >= 6) {
                                int ano = Integer.parseInt(dados[4].trim());
                                String servico = dados[5].trim();
                                utilizadores.add(new NaoDocente(nome, id, pagamento, ano, servico));
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Não Docente incompleto.");
                            }
                            break;

                        case "DOCENTE":
                            if (dados.length >= 6) {
                                int ano = Integer.parseInt(dados[4].trim());
                                ArrayList<String> faculdades = new ArrayList<>();
                                for (int i = 5; i < dados.length; i++) {
                                    faculdades.add(dados[i].trim());
                                }
                                utilizadores.add(new Docente(nome, id, pagamento, ano, faculdades));
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Docente incompleto.");
                            }
                            break;

                        default:
                            System.out.println("[Linha " + numeroLinha + "] Erro: Tipo de utilizador desconhecido (" + tipo + ").");
                    }
                } catch (Exception e) {
                    System.out.println("[Linha " + numeroLinha + "] Erro crítico ao criar objeto: " + e.getMessage());
                }
            }
            System.out.println(utilizadores.size() + " utilizadores carregados com sucesso!");
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

                if (linhaTexto.trim().isEmpty()) continue;

                String[] dados = linhaTexto.split(";");

                if (dados.length < 3) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Dados insuficientes. Ignorado.");
                    continue;
                }

                String tipo = dados[0].trim().toUpperCase();
                String id = dados[1].trim(); // ID do veículo
                String gps = dados[2].trim();

                // === VALIDAÇÃO DE INPUT (4 DÍGITOS) ===
                if (!validarFormatoId(id, 4)) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: ID de veículo '" + id + "' inválido. Deve ter exatos 4 dígitos numéricos. Ignorado.");
                    continue;
                }

                // === VALIDAÇÃO DE DUPLICADOS ===
                if (procurarVeiculo(id) != null) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Veículo com ID " + id + " já existe. Ignorado.");
                    continue;
                }

                try {
                    switch (tipo) {
                        case "BICICLETA":
                            if (dados.length >= 4) {
                                String subTipo = dados[3].trim().toUpperCase();
                                if (subTipo.equals("DUPLA")) {
                                    veiculos.add(new BicicletaDupla(id, gps));
                                } else {
                                    veiculos.add(new BicicletaIndividual(id, gps));
                                }
                            } else {
                                veiculos.add(new BicicletaIndividual(id, gps));
                            }
                            break;

                        case "TROTINETE":
                            if (dados.length >= 5) {
                                int autonomia = Integer.parseInt(dados[3].trim());
                                String temLCD = dados[4].trim().toUpperCase();

                                if (temLCD.equals("SIM") || temLCD.equals("COM_LCD") || temLCD.equals("TRUE")) {
                                    veiculos.add(new TrotineteComLCD(id, gps, autonomia));
                                } else {
                                    veiculos.add(new TrotineteSemLCD(id, gps, autonomia));
                                }
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: Trotinete incompleta.");
                            }
                            break;

                        case "EBIKE":
                            if (dados.length >= 5) {
                                int autonomia = Integer.parseInt(dados[3].trim());
                                String tipoBateria = dados[4].trim().toUpperCase();

                                if (tipoBateria.equals("REMOVIVEL")) {
                                    veiculos.add(new EBikeBateriaRemovivel(id, gps, autonomia));
                                } else {
                                    veiculos.add(new EBikeBateriaFixa(id, gps, autonomia));
                                }
                            } else {
                                System.out.println("[Linha " + numeroLinha + "] Erro: E-Bike incompleta.");
                            }
                            break;

                        default:
                            System.out.println("[Linha " + numeroLinha + "] Tipo de veículo desconhecido: " + tipo);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Linha " + numeroLinha + "] Erro: Formato numérico inválido (Autonomia). Ignorado.");
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

    @SuppressWarnings("unchecked")
    public void carregarAlugueres(String nomeFicheiro) {
        File f = new File(nomeFicheiro);

        if (f.exists() && f.isFile()) {
            try {
                if (f.length() == 0) return;

                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);

                this.alugueres = (ArrayList<Aluguer>) ois.readObject();

                ois.close();
                System.out.println(alugueres.size() + " alugueres carregados do histórico.");

            } catch (Exception ex) {
                System.out.println("Aviso: Não foi possível carregar o histórico de alugueres (ficheiro vazio ou incompatível).");
            }
        }
    }

    public void guardarAlugueres(String nomeFicheiro) {
        File f = new File(nomeFicheiro);

        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.alugueres);

            oos.close();
            System.out.println("Alugueres guardados com sucesso.");

        } catch (IOException ex) {
            System.out.println("Erro a guardar alugueres: " + ex.getMessage());
        }
    }

    // ==========================================
    // 3. MÉTODOS DE APOIO E VALIDAÇÃO
    // ==========================================

    /**
     * Verifica se um ID tem o tamanho exato e contém apenas números.
     */
    private boolean validarFormatoId(String id, int tamanhoEsperado) {
        // Verifica se não é nulo, se tem o tamanho certo e se só tem dígitos (\d+)
        return id != null && id.length() == tamanhoEsperado && id.matches("\\d+");
    }

    public Utilizador procurarUtilizador(String id) {
        for (Utilizador u : utilizadores) {
            if (u.getNumeroMecanografico().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    public Veiculo procurarVeiculo(String id) {
        for (Veiculo v : veiculos) {
            if (v.getId().equalsIgnoreCase(id)) {
                return v;
            }
        }
        return null;
    }

    public void registarAluguer(Aluguer a) {
        if (a != null) {
            this.alugueres.add(a);
        }
    }

    public ArrayList<Aluguer> getAlugueres() {
        return this.alugueres;
    }

    public ArrayList<Utilizador> getUtilizadores() { return utilizadores; }
    public ArrayList<Veiculo> getVeiculos() { return veiculos; }
}