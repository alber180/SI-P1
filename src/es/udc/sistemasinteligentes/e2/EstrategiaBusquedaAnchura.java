package es.udc.sistemasinteligentes.e2;

import es.udc.sistemasinteligentes.*;
import java.util.*;

public class EstrategiaBusquedaAnchura implements EstrategiaBusqueda {

    public EstrategiaBusquedaAnchura() {
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
        int nodosExpandidos = 0;
        int nodosCreados = 1; // ya se ha creado el nodo inicial

        System.out.println((i++) + " - Iniciando búsqueda desde el estado inicial:\n" + estadoInicial);

        while (!frontera.isEmpty()) {
            // Se extrae el primer nodo de la cola
            Nodo nodoActual = frontera.poll();
            nodosExpandidos++;  // Se expande este nodo
            Estado estadoActual = nodoActual.getEstado();

            if (p.esMeta(estadoActual)) {
                System.out.println((i++) + " - ¡Estado meta alcanzado!\n" + estadoActual);
                System.out.println("Nodos expandidos: " + nodosExpandidos);
                System.out.println("Nodos creados: " + nodosCreados);
                return reconstruyeSol(nodoActual).toArray(new Nodo[0]);
            }

            System.out.println((i++) + " - Estado actual no es meta:\n" + estadoActual);
            // Se marca el estado actual como explorado
            explorados.add(estadoActual);

            for (Accion acc : p.acciones(estadoActual)) {
                Estado sc = p.result(estadoActual, acc);
                System.out.println((i++) + " - Aplicando acción: " + acc);
                System.out.println((i++) + " - Resultado obtenido:\n" + sc);

                // Verifica que el estado 'sc' no esté ya explorado y que no se encuentre en la cola
                if (!explorados.contains(sc) && frontera.stream().noneMatch(n -> n.getEstado().equals(sc))) {
                    Nodo nodoHijo = new Nodo(sc, nodoActual, acc);
                    frontera.add(nodoHijo);
                    nodosCreados++; // Se crea un nuevo nodo
                    System.out.println((i++) + " - Estado agregado a la cola:\n" + sc);
                } else {
                    System.out.println((i++) + " - Estado ya explorado o presente en la cola:\n" + sc);
                }
            }
            System.out.println("---------------------------------------------------");
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
