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

        // Crear el nodo inicial
        Estado estadoInicial = p.getEstadoInicial();
        Nodo nodoInicial = new Nodo(estadoInicial, null, null);

        // -- 1) Si el estado inicial es meta, devolvemos directamente
        if (p.esMeta(estadoInicial)) {
            System.out.println("El estado inicial ya es meta:\n" + estadoInicial);
            return new Nodo[]{ nodoInicial };
        }

        // -- 2) Insertamos el nodo inicial en la frontera
        frontera.push(nodoInicial);

        int i = 1;
        int nodosExpandidos = 0;
        int nodosCreados = 1; // Contamos el nodo inicial como creado

        System.out.println((i++) + " - Iniciando búsqueda en el estado inicial:\n" + estadoInicial);

        // -- Mientras la frontera no esté vacía
        while (!frontera.isEmpty()) {
            // -- 3) Sacamos el último nodo (LIFO)
            Nodo nodoActual = frontera.pop();
            nodosExpandidos++; // Lo consideramos expandido
            Estado estadoActual = nodoActual.getEstado();

            // -- 4) Insertamos N en explorados
            explorados.add(estadoActual);

            System.out.println((i++) + " - Expandimos estado:\n" + estadoActual);

            // -- 5) Obtenemos los sucesores (H = sucesores(N))
            Accion[] acciones = p.acciones(estadoActual);

            // -- Para cada sucesor N_h en H
            for (Accion acc : acciones) {
                System.out.println((i++) + " - Generando sucesor con acción: " + acc);

                Estado sc = p.result(estadoActual, acc);
                Nodo nodoHijo = new Nodo(sc, nodoActual, acc);

                // -- Comprobamos si S_h es meta (antes de insertarlo)
                if (p.esMeta(sc)) {
                    System.out.println((i++) + " - ¡Estado meta encontrado!\n" + sc);
                    nodosCreados++; // Hemos creado el nodo meta
                    System.out.println("Nodos expandidos: " + nodosExpandidos);
                    System.out.println("Nodos creados: " + nodosCreados);
                    return reconstruyeSol(nodoHijo).toArray(new Nodo[0]);
                }

                // -- Si no es meta, lo insertamos en la frontera si no está en E ni en F
                if (!explorados.contains(sc)&& frontera.stream().noneMatch(n -> n.getEstado().equals(sc))) {
                    frontera.push(nodoHijo);
                    nodosCreados++;
                    System.out.println((i++) + " - Sucesor NO explorado y NO en frontera. Agregado:\n" + sc);
                } else {
                    System.out.println((i++) + " - Sucesor ya explorado o presente en la frontera:\n" + sc);
                }
            }
            System.out.println("---------------------------------------------------");
        }

        // -- 6) Si la frontera queda vacía, no se encontró solución
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
