package es.udc.sistemasinteligentes.e2;

import es.udc.sistemasinteligentes.*;
import java.util.*;

public class EstrategiaBusquedaProfundidad implements EstrategiaBusqueda {

    public EstrategiaBusquedaProfundidad() {}

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {
        // Pila para nodos pendientes de explorar (LIFO)
        Stack<Nodo> frontera = new Stack<>();

        // Conjunto de estados ya explorados
        Set<Estado> explorados = new HashSet<>();

        Estado estadoInicial = p.getEstadoInicial();
        Nodo nodoInicial = new Nodo(estadoInicial, null, null);
        // Agregamos el nodo inicial a la pila
        frontera.push(nodoInicial);

        int i = 1;
        int nodosExpandidos = 0;
        int nodosCreados = 1; // ya se ha creado el nodo inicial

        System.out.println((i++) + " - Iniciando búsqueda desde el estado inicial:\n" + estadoInicial);

        while (!frontera.isEmpty()) {
            // Se extrae el último nodo agregado (LIFO)
            Nodo nodoActual = frontera.pop();
            nodosExpandidos++; // Se expande el nodo extraído
            Estado estadoActual = nodoActual.getEstado();

            if (p.esMeta(estadoActual)) {
                System.out.println((i++) + " - ¡Estado meta alcanzado!\n" + estadoActual);
                System.out.println("Nodos expandidos: " + nodosExpandidos);
                System.out.println("Nodos creados: " + nodosCreados);
                return reconstruyeSol(nodoActual).toArray(new Nodo[0]);
            }

            System.out.println((i++) + " - Estado actual no es meta:\n" + estadoActual);
            explorados.add(estadoActual);

            for (Accion acc : p.acciones(estadoActual)) {
                Estado sc = p.result(estadoActual, acc);
                System.out.println((i++) + " - Aplicando acción: " + acc);
                System.out.println((i++) + " - Resultado obtenido:\n" + sc);

                // Verifica que el estado 'sc' no esté explorado ni en la pila
                if (!explorados.contains(sc) && frontera.stream().noneMatch(n -> n.getEstado().equals(sc))) {
                    Nodo nodoHijo = new Nodo(sc, nodoActual, acc);
                    frontera.push(nodoHijo);
                    nodosCreados++;
                    System.out.println((i++) + " - Estado agregado a la pila:\n" + sc);
                } else {
                    System.out.println((i++) + " - Estado ya explorado o presente en la pila:\n" + sc);
                }
            }
            System.out.println("---------------------------------------------------");
        }

        throw new Exception("No se ha podido encontrar una solución");
    }

    public ArrayList<Nodo> reconstruyeSol(Nodo n) {
        ArrayList<Nodo> sol = new ArrayList<>();
        while (n != null) {
            sol.add(n);
            n = n.getPadre();
        }
        Collections.reverse(sol);
        return sol;
    }
}
