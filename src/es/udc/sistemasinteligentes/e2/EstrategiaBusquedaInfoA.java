package es.udc.sistemasinteligentes.e2;

import es.udc.sistemasinteligentes.*;
import java.util.*;

public class EstrategiaBusquedaInfoA implements EstrategiaBusquedaInformada {

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p, Heuristica h) throws Exception {
        // Cola de prioridad ordenada por f = g + h (gracias a compareTo en Nodo)
        PriorityQueue<Nodo> frontera = new PriorityQueue<>();
        // Conjunto de estados explorados
        Set<Estado> explorados = new HashSet<>();
        int semen = 0;

        // 1. Creamos el nodo inicial
        Estado estadoInicial = p.getEstadoInicial();
        // g=0, h se calcula con la heurística
        float hIni = h.evalua(estadoInicial);
        Nodo nodoInicial = new Nodo(estadoInicial, null, null, 0, hIni);

        // Insertamos el nodo inicial en la frontera
        frontera.add(nodoInicial);

        while (!frontera.isEmpty()) {
            // 2. Sacamos el nodo con menor f de la cola de prioridad
            Nodo nodoActual = frontera.poll();
            Estado estadoActual = nodoActual.getEstado();

            // 3. Comprobamos si es meta
            if (p.esMeta(estadoActual)) {
                System.out.println("¡Estado meta alcanzado!\n" + estadoActual);
                System.out.println(semen);
                // Podrías reconstruir el camino aquí si lo deseas
                return reconstruyeSol(nodoActual).toArray(new Nodo[0]);
            }

            // 4. Marcamos el estado como explorado
            explorados.add(estadoActual);

            // 5. Generamos sucesores
            Accion[] acciones = p.acciones(estadoActual);
            for (Accion acc : acciones) {
                Estado sucesor = p.result(estadoActual, acc);
                semen++;
                // Calculamos el coste g del sucesor
                float gSucesor = nodoActual.getG() + 1; // asumiendo coste=1
                float hSucesor = h.evalua(sucesor);
                Nodo nodoSucesor = new Nodo(sucesor, nodoActual, acc, gSucesor, hSucesor);

                // Solo añadimos a la frontera si no ha sido explorado
                if (!explorados.contains(sucesor)) {
                    // (Versión simple) No verificamos si hay uno igual en frontera con menor coste
                    frontera.add(nodoSucesor);
                }
            }
        }

        // Si la frontera se vacía, no hay solución
        throw new Exception("No se ha encontrado solución con A*");
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
