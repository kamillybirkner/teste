import java.util.*;

public class Grafo {

    public static String tipoDoGrafo(int[][] matriz) {
        int n = matriz.length;
        boolean dirigido = false;
        boolean multigrafo = false;
        boolean completo = true;
        boolean regular = true;
        int grauEsperado = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    dirigido = true;
                }
                if (matriz[i][j] > 1 || matriz[i][i] > 0) {
                    multigrafo = true;
                }
                if (i != j && matriz[i][j] == 0) {
                    completo = false;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int grau = 0;
            for (int j = 0; j < n; j++) {
                grau += matriz[i][j];
            }
            if (matriz[i][i] > 0) {
                grau += matriz[i][i];
            }
            if (grauEsperado == -1) {
                grauEsperado = grau;
            } else if (grau != grauEsperado) {
                regular = false;
            }
        }

        if (n == 0)
            return "Nulo";

        StringBuilder tipo = new StringBuilder();
        tipo.append(dirigido ? "Dirigido" : "Não Dirigido");
        if (multigrafo)
            tipo.append(", Multigrafo");
        else
            tipo.append(", Simples");
        if (regular)
            tipo.append(", Regular");
        if (completo)
            tipo.append(", Completo");

        return tipo.toString();
    }

    public static String arestasDoGrafo(int[][] matriz) {
        int n = matriz.length;
        boolean dirigido = false;
        List<String> arestas = new ArrayList<>();

        for (int i = 0; i < n && !dirigido; i++) {
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    dirigido = true;
                    break;
                }
            }
        }

        int qtdArestas = 0;
        if (dirigido) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < matriz[i][j]; k++) {
                        qtdArestas++;
                        arestas.add("(V" + (i + 1) + "->V" + (j + 1) + ")");
                    }
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    for (int k = 0; k < matriz[i][j]; k++) {
                        qtdArestas++;
                        if (i == j) {
                            arestas.add("(V" + (i + 1) + ",V" + (j + 1) + ")");
                        } else {
                            arestas.add("(V" + (i + 1) + ",V" + (j + 1) + ")");
                        }
                    }
                }
            }
        }

        return "Quantidade de arestas: " + qtdArestas + " -> " + arestas.toString();
    }

    public static String grausDoVertice(int[][] matriz) {
        int n = matriz.length;
        boolean dirigido = false;

        for (int i = 0; i < n && !dirigido; i++) {
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    dirigido = true;
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        List<Integer> sequencia = new ArrayList<>();

        if (dirigido) {
            sb.append("Grafo dirigido\n");
            for (int i = 0; i < n; i++) {
                int grauSaida = 0, grauEntrada = 0;
                for (int j = 0; j < n; j++) {
                    grauSaida += matriz[i][j];
                    grauEntrada += matriz[j][i];
                }
                if (matriz[i][i] > 0) {
                    grauEntrada += matriz[i][i];
                    grauSaida += matriz[i][i];
                }
                int grauTotal = grauEntrada + grauSaida;
                sequencia.add(grauTotal);
                sb.append("Vértice ").append(i + 1)
                        .append(" -> Grau Entrada: ").append(grauEntrada)
                        .append(", Grau Saída: ").append(grauSaida)
                        .append(", Grau Total: ").append(grauTotal).append("\n");
            }
        } else {
            sb.append("Grafo não dirigido\n");
            for (int i = 0; i < n; i++) {
                int grau = 0;
                for (int j = 0; j < n; j++) {
                    grau += matriz[i][j];
                }
                if (matriz[i][i] > 0) {
                    grau += matriz[i][i];
                }
                sequencia.add(grau);
                sb.append("Vértice ").append(i + 1).append(" -> Grau: ").append(grau).append("\n");
            }
        }

        int somaGraus = sequencia.stream().mapToInt(Integer::intValue).sum();
        sb.append("Soma dos Graus do Grafo: ").append(somaGraus).append("\n");
        sb.append("Sequência de Graus: ").append(sequencia.toString());

        return sb.toString();
    }

    public static String buscaEmProfundidade(int[][] matriz) {
        int n = matriz.length;
        boolean[] visitado = new boolean[n];
        List<Integer> ordem = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                dfs(i, matriz, visitado, ordem);
            }
        }

        return "Ordem da busca em profundidade: " + ordem.toString();
    }

    private static void dfs(int v, int[][] matriz, boolean[] visitado, List<Integer> ordem) {
        visitado[v] = true;
        ordem.add(v + 1);
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[v][i] > 0 && !visitado[i]) {
                dfs(i, matriz, visitado, ordem);
            }
        }
    }

    public static void main(String[] args) {
        int[][] matriz = {
                { 0, 1, 1, 0 },
                { 1, 1, 0, 2 },
                { 1, 0, 0, 1 },
                { 0, 2, 1, 0 }
        };

        System.out.println("Tipo do Grafo: " + tipoDoGrafo(matriz));
        System.out.println(arestasDoGrafo(matriz));
        System.out.println(grausDoVertice(matriz));
        System.out.println(buscaEmProfundidade(matriz));
    }
}
