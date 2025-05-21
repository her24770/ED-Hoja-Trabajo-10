import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;

public class GrafoFloydTest {
    private GrafoFloyd grafo;
    private String[] ciudades = {"A", "B", "C", "D"};

    @Before
    public void setUp() {
        grafo = new GrafoFloyd(ciudades);
    }

    // Pruebas para la construcción inicial del grafo
    @Test
    public void testConstructor() {
        assertEquals(4, grafo.getMatrizAdyacencia().length);
        assertEquals(4, grafo.getIndices().size());
        
        Map<String, Integer> indices = grafo.getIndices();
        assertEquals(0, (int) indices.get("A"));
        assertEquals(1, (int) indices.get("B"));
        assertEquals(2, (int) indices.get("C"));
        assertEquals(3, (int) indices.get("D"));
        
        int[][] matriz = grafo.getMatrizAdyacencia();
        for (int i = 0; i < ciudades.length; i++) {
            for (int j = 0; j < ciudades.length; j++) {
                if (i == j) {
                    assertEquals(0, matriz[i][j]);
                } else {
                    assertEquals(Integer.MAX_VALUE, matriz[i][j]);
                }
            }
        }
    }

    // Pruebas para agregar aristas
    @Test
    public void testAgregarArista() {
        grafo.agregarArista("A", "B", 5);
        grafo.agregarArista("B", "C", 3);
        grafo.agregarArista("C", "D", 7);
        
        int[][] matriz = grafo.getMatrizAdyacencia();
        assertEquals(5, matriz[0][1]);
        assertEquals(3, matriz[1][2]);
        assertEquals(7, matriz[2][3]);
        assertEquals(Integer.MAX_VALUE, matriz[1][0]); // Debe seguir siendo infinito (grafo dirigido)
    }

    @Test(expected = NullPointerException.class)
    public void testAgregarAristaNodoInexistente() {
        grafo.agregarArista("A", "X", 5); // "X" no existe
    }

    // Pruebas para eliminar aristas
    @Test
    public void testEliminarArista() {
        grafo.agregarArista("A", "B", 5);
        assertEquals(5, grafo.getMatrizAdyacencia()[0][1]);
        
        grafo.eliminarArista("A", "B");
        assertEquals(Integer.MAX_VALUE, grafo.getMatrizAdyacencia()[0][1]);
    }

    @Test(expected = NullPointerException.class)
    public void testEliminarAristaNodoInexistente() {
        grafo.eliminarArista("A", "X"); // "X" no existe
    }

    // Pruebas para el algoritmo de Floyd-Warshall
    @Test
    public void testFloydWarshallGrafoSimple() {
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "C", 2);
        grafo.agregarArista("C", "D", 3);
        
        int[][] distancias = grafo.floydWarshall();
        
        assertEquals(0, distancias[0][0]); // A -> A
        assertEquals(1, distancias[0][1]); // A -> B
        assertEquals(3, distancias[0][2]); // A -> C (A->B->C)
        assertEquals(6, distancias[0][3]); // A -> D (A->B->C->D)
        assertEquals(Integer.MAX_VALUE, distancias[1][0]); // B -> A (no hay camino)
    }

    @Test
    public void testFloydWarshallConCiclo() {
        grafo.agregarArista("A", "B", 3);
        grafo.agregarArista("B", "C", 2);
        grafo.agregarArista("C", "A", 1);
        
        int[][] distancias = grafo.floydWarshall();
        
        // A -> B -> C -> A (ciclo de peso 6)
        assertEquals(0, distancias[0][0]); // A -> A
        assertEquals(3, distancias[0][1]); // A -> B
        assertEquals(5, distancias[0][2]); // A -> C (A->B->C)
        assertEquals(1, distancias[2][0]); // C -> A
        assertEquals(4, distancias[2][1]); // C -> A -> B
    }

    @Test
    public void testCalcularCentroGrafoComplejo() {
        grafo.agregarArista("A", "B", 3);
        grafo.agregarArista("B", "C", 2);
        grafo.agregarArista("C", "A", 1);
        grafo.agregarArista("C", "D", 4);
        
        assertEquals("C", grafo.calcularCentroGrafo());
    }

    @Test
    public void testCalcularCentroGrafoDesconectado() {
        // Solo conexiones A-B y C-D (grafo desconectado)
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("C", "D", 1);
        
        // En este caso, debería devolver uno de los nodos con la mínima excentricidad
        String centro = grafo.calcularCentroGrafo();
        assertTrue(centro.equals("A") || centro.equals("B") || centro.equals("C") || centro.equals("D"));
    }
}