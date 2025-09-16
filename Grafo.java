import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grafo {
    
    public static String tipoDoGrafo(int[][] matriz) {
        int n = matriz.length;
        boolean dirigido = false;
        boolean multigrafo = false;
        boolean completo = true;
        boolean nulo = true;
        int[] graus = new int[n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    dirigido = true;
                }
                if (matriz[i][j] > 1) {
                    multigrafo = true;
                }
                if (i != j && matriz[i][j] == 0) {
                    completo = false;
                }
                if (matriz[i][j] != 0) {
                    nulo = false;
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            int grau = 0;
            for (int j = 0; j < n; j++) {
                grau += matriz[i][j];
            }
            graus[i] = grau;
        }
        boolean regular = Arrays.stream(graus).allMatch(g -> g == graus[0]);

        return "Dirigido: " + dirigido +
               ", Simples: " + !multigrafo +
               ", Regular: " + regular +
               ", Completo: " + completo +
               ", Nulo: " + nulo;
    }

    public static String arestasDoGrafo(int[][] matriz) {
        int n = matriz.length;
        List<String> arestas = new ArrayList<>();
        int qtd = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = (i+1); j < n; j++) {
                if (matriz[i][j] > 0) {
                    qtd += matriz[i][j];
                    for (int k = 0; k < matriz[i][j]; k++) {
                        arestas.add("(" + i + "," + j + ")");
                    }
                }
            }
        }
        return "Quantidade de arestas: " + qtd + " | Conjunto: " + arestas;
    }

    public static String grausDoVertice(int[][] matriz) {
        int n = matriz.length;
        int[] graus = new int[n];
        int soma = 0;
        
        for (int i = 0; i < n; i++) {
            int grau = 0;
            for (int j = 0; j < n; j++) {
                grau += matriz[i][j];
            }
            graus[i] = grau;
            soma += grau;
        }
        return "Grau do grafo: " + soma +
               " | Graus dos vértices: " + Arrays.toString(graus);
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
        return "Ordem da busca em profundidade: " + ordem;
    }

    private static void dfs(int v, int[][] matriz, boolean[] visitado, List<Integer> ordem) {
        visitado[v] = true;
        ordem.add(v);
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[v][i] > 0 && !visitado[i]) {
                dfs(i, matriz, visitado, ordem);
            }
        }
    }

    public static void main(String[] args) {
        int[][] matriz = {
            {0, 1, 1, 0},
            {1, 0, 1, 1},
            {1, 1, 0, 1},
            {0, 1, 1, 0}
        };

        System.out.println(tipoDoGrafo(matriz));
        System.out.println(arestasDoGrafo(matriz));
        System.out.println(grausDoVertice(matriz));
        System.out.println(buscaEmProfundidade(matriz));
    }
}
