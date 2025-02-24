package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.*;
import java.util.ArrayList;

public class Estrategia4 implements EstrategiaBusqueda {

    public Estrategia4() {
    }

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {

        ArrayList<Estado> explorados = new ArrayList<>();
        Estado estadoActual = p.getEstadoInicial();
        explorados.add(estadoActual);

        Nodo nodo = new Nodo(estadoActual, null, null);
        Nodo nodoHijo = null;

        int i = 1;

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        while (!p.esMeta(estadoActual)){
            System.out.println((i++) + " - " + estadoActual + " no es meta");
            Accion[] accionesDisponibles = p.acciones(estadoActual);
            boolean modificado = false;
            for (Accion acc: accionesDisponibles) {
                Estado sc = p.result(estadoActual, acc);
                System.out.println((i++) + " - RESULT(" + estadoActual + ","+ acc + ")=" + sc);
                if (!explorados.contains(sc)) {
                    estadoActual = sc;
                    System.out.println((i++) + " - " + sc + " NO explorado");

                    nodoHijo = new Nodo(estadoActual, nodo, acc);
                    nodo = nodoHijo;

                    // Mirar lo de estado actual para poner el padre

                    explorados.add(estadoActual);
                    modificado = true;
                    System.out.println((i++) + " - Estado actual cambiado a " + estadoActual);
                    break;
                }
                else
                    System.out.println((i++) + " - " + sc + " ya explorado");
            }
            if (!modificado) throw new Exception("No se ha podido encontrar una solución");
        }
        System.out.println((i++) + " - FIN - " + estadoActual);
        // Mandas todos con new 0
        return reconstruyeSol(nodoHijo).toArray(new Nodo[0]);
    }

    public ArrayList<Nodo> reconstruyeSol(Nodo n){
        ArrayList<Nodo> sol = new ArrayList<Nodo>();
        Nodo nodo = n;
        while (nodo!=null) {
            sol.add(nodo);
            nodo = nodo.getPadre();
        }
        return sol;
    }
}
