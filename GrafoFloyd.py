##
 # Universidad del Valle de Guatemala
 # Estructura de Datos
 # @author Josue Hernández González
 # Hoja de trabajo #10
 
 
import networkx as nx

def main():
    # Crear grafo dirigido
    grafo = nx.DiGraph()
    
    # Configurar el grafo exactamente como en tu matriz
    grafo.add_edge('A', 'A', weight=0)
    grafo.add_edge('A', 'B', weight=3)
    grafo.add_edge('A', 'D', weight=7)
    grafo.add_edge('B', 'B', weight=0)
    grafo.add_edge('B', 'C', weight=1)
    grafo.add_edge('B', 'E', weight=8)
    grafo.add_edge('C', 'C', weight=0)
    grafo.add_edge('C', 'D', weight=2)
    grafo.add_edge('D', 'D', weight=0)
    grafo.add_edge('D', 'E', weight=3)
    grafo.add_edge('E', 'E', weight=0)
    grafo.add_edge('E', 'A', weight=4)
    
    # Calcular distancias con Floyd-Warshall
    distancias = dict(nx.floyd_warshall_predecessor_and_distance(grafo, weight='weight')[1])
    
    # Imprimir matriz de distancias
    print("Matriz:")
    nodos = sorted(grafo.nodes())
    print("    " + "   ".join(f"{n:>2}" for n in nodos))
    for i in nodos:
        print(f"{i:3}", end=" ")
        for j in nodos:
            print(f"{distancias[i][j] if distancias[i][j] != float('inf') else '∞':>3}", end=" ")
        print()
    
    # Calcular centro del grafo
    excentricidades = nx.eccentricity(grafo, sp=dict(nx.floyd_warshall_predecessor_and_distance(grafo, weight='weight')[1])) 
    print(f"\nEl centro del grafo es: {nx.center(grafo, e=excentricidades)[0]}")

main()