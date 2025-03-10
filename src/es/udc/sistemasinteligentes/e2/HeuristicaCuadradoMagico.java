package es.udc.sistemasinteligentes.e2;

import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.Heuristica;

public class HeuristicaCuadradoMagico extends Heuristica {

    @Override
    public float evalua(Estado e) {
        ProblemaCuadradoMagico.EstadoCuadradoMagico estado = (ProblemaCuadradoMagico.EstadoCuadradoMagico) e;
        int n = estado.getN();
        int casillasVacias = 0;

        // Cada casilla sin poner se suma 1 al coste
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (estado.getCuadradoMagico()[i][j] == 0) {
                    casillasVacias++;
                }
            }
        }
        return casillasVacias;
    }
}
