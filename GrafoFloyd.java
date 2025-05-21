/*
 * Clase GrafoFloyd que implementa el algoritmo de Floyd-Warshall para encontrar
 * Codigo basado en https://algorithms.discrete.ma.tum.de/graph-algorithms/spp-floyd-warshall/index_en.html#tab_te
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GrafoFloyd {
    
    private int[][] matrizAdyacencia;
    private Map<String, Integer> indices;
    private String[] ciudades;
    
    public GrafoFloyd(String[] ciudades) {
        int n = ciudades.length;
        this.ciudades = ciudades;
        this.matrizAdyacencia = new int[n][n];
        this.indices = new HashMap<>();
        
        // Inicializar matriz con infinito (representado por Integer.MAX_VALUE)
        for (int i = 0; i < n; i++) {
            Arrays.fill(matrizAdyacencia[i], Integer.MAX_VALUE);
            matrizAdyacencia[i][i] = 0; // Diagonal a 0
            indices.put(ciudades[i], i);
        }
    }
    
    // Método para agregar una arista dirigida con peso
    public void agregarArista(String origen, String destino, int peso) {
        int i = indices.get(origen);
        int j = indices.get(destino);
        matrizAdyacencia[i][j] = peso;
    }
    
    // Método para eliminar una arista
    public void eliminarArista(String origen, String destino) {
        int i = indices.get(origen);
        int j = indices.get(destino);
        matrizAdyacencia[i][j] = Integer.MAX_VALUE;
    }
    
    // Implementación del algoritmo de Floyd-Warshall
    public int[][] floydWarshall() {
        int n = matrizAdyacencia.length;
        int[][] distancias = new int[n][n];
        
        // Copiar la matriz de adyacencia
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrizAdyacencia[i], 0, distancias[i], 0, n);
        }
        
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE && 
                        distancias[k][j] != Integer.MAX_VALUE &&
                        distancias[i][j] > distancias[i][k] + distancias[k][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                    }
                }
            }
        }
        
        return distancias;
    }
    
    // Método para calcular el centro del grafo
    public String calcularCentroGrafo() {
        int[][] distancias = floydWarshall();
        int[] maximos = new int[distancias.length];
        int minMax = Integer.MAX_VALUE;
        int centro = 0;
        
        for (int i = 0; i < distancias.length; i++) {
            int max = 0;
            for (int j = 0; j < distancias[i].length; j++) {
                if (distancias[i][j] > max) {
                    max = distancias[i][j];
                }
            }
            maximos[i] = max;
            
            if (max < minMax) {
                minMax = max;
                centro = i;
            }
        }
        
        return ciudades[centro];
    }
    
    // Método para imprimir la matriz de distancias
    public void imprimirMatriz(int[][] matriz) {
        System.out.print("    ");
        for (String ciudad : ciudades) {
            System.out.printf("%4s", ciudad);
        }
        System.out.println();
        
        for (int i = 0; i < matriz.length; i++) {
            System.out.printf("%4s", ciudades[i]);
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == Integer.MAX_VALUE) {
                    System.out.print("  ∞ ");
                } else {
                    System.out.printf("%4d", matriz[i][j]);
                }
            }
            System.out.println();
        }
    }
}