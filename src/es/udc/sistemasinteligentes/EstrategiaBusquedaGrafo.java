package es.udc.sistemasinteligentes;

import java.util.*;

public class EstrategiaBusquedaGrafo implements EstrategiaBusqueda {

    public EstrategiaBusquedaGrafo() {
    }

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {
        // Cola para nodos pendientes de explorar
        Queue<Nodo> frontera = new LinkedList<>();

        // Conjunto de estados ya explorados
        Set<Estado> explorados = new HashSet<>();

        Estado estadoInicial = p.getEstadoInicial();
        Nodo nodoInicial = new Nodo(estadoInicial, null, null);
        frontera.add(nodoInicial); // Agregar el nodo inicial a la frontera

        int i = 1;
        System.out.println((i++) + " - Empezando búsqueda en " + estadoInicial);

        while (!frontera.isEmpty()) {
            // El poll saca el primero de la cola
            Nodo nodoActual = frontera.poll();
            Estado estadoActual = nodoActual.getEstado();

            if (p.esMeta(estadoActual)) {
                System.out.println((i++) + " - FIN - " + estadoActual);
                return reconstruyeSol(nodoActual).toArray(new Nodo[0]);
            }

            System.out.println((i++) + " - " + estadoActual + " no es meta");
            // Lo ponemos antes del foreach para evitar explorados repes (creo)
            explorados.add(estadoActual);

            for (Accion acc : p.acciones(estadoActual)) {
                Estado sc = p.result(estadoActual, acc);
                System.out.println((i++) + " - RESULT(" + estadoActual + "," + acc + ")=" + sc);

                // Verifica que el estado sc no haya sido explorado y que no esté ya en la frontera (nodos pendientes de explorar)
                if (!explorados.contains(sc) && frontera.stream().noneMatch(n -> n.getEstado().equals(sc))) {
                    Nodo nodoHijo = new Nodo(sc, nodoActual, acc);
                    frontera.add(nodoHijo);
                    System.out.println((i++) + " - " + sc + " agregado a la cola");
                } else {
                    System.out.println((i++) + " - " + sc + " ya está explorado o bien en la cola");
                }

            }
        }

        throw new Exception("No se ha podido encontrar una solución");
    }

    public ArrayList<Nodo> reconstruyeSol(Nodo n) {
        ArrayList<Nodo> sol = new ArrayList<Nodo>();
        Nodo nodo = n;
        while (nodo != null) {
            sol.add(nodo);
            nodo = nodo.getPadre();
        }
        Collections.reverse(sol);
        return sol;
    }
}