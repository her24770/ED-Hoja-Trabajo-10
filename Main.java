public class Main {
    public static void main(String[] args) {
        // Crear el grafo con los nodos A, B, C, D, E
        String[] ciudades = {"A", "B", "C", "D", "E"};
        GrafoFloyd grafo = new GrafoFloyd(ciudades);
        
        // Configurar el grafo exactamente como en tu imagen
        grafo.agregarArista("A", "A", 0);    // A -> A = 0
        grafo.agregarArista("A", "B", 3);    // A -> B = 3
        grafo.agregarArista("A", "D", 7);    // A -> D = 7
        
        grafo.agregarArista("B", "B", 0);    // B -> B = 0
        grafo.agregarArista("B", "C", 1);    // B -> C = 1
        grafo.agregarArista("B", "E", 8);    // B -> E = 8
        
        grafo.agregarArista("C", "C", 0);    // C -> C = 0
        grafo.agregarArista("C", "D", 2);    // C -> D = 2
        
        grafo.agregarArista("D", "D", 0);    // D -> D = 0
        grafo.agregarArista("D", "E", 3);    // D -> E = 3
        
        grafo.agregarArista("E", "E", 0);    // E -> E = 0
        grafo.agregarArista("E", "A", 4);    // E -> A = 4
        
        // Mostrar matriz de adyacencia original
        System.out.println("Matriz de adyacencia original:");
        grafo.imprimirMatriz(grafo.getMatrizAdyacencia());
        
        // Calcular y mostrar distancias más cortas
        System.out.println("\nMatriz de distancias más cortas:");
        int[][] distancias = grafo.floydWarshall();
        grafo.imprimirMatriz(distancias);
        
        // Calcular y mostrar el centro del grafo
        String centro = grafo.calcularCentroGrafo();
        System.out.println("\nEl centro del grafo es: " + centro);
    }
}