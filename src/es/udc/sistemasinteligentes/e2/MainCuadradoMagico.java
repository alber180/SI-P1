package es.udc.sistemasinteligentes.e2;

import es.udc.sistemasinteligentes.EstrategiaBusqueda;


import java.util.Arrays;

public class MainCuadradoMagico {

    public static void main(String[] args) throws Exception {
        int[][] tablero = { {4, 9, 2}, {3, 5, 0}, {0, 1, 0} };
        int[][] tablero2 = { {2, 0, 0}, {0, 0, 0}, {0, 0, 0} };

        ProblemaCuadradoMagico.EstadoCuadradoMagico estadoInicial = new ProblemaCuadradoMagico.EstadoCuadradoMagico(tablero2);
        ProblemaCuadradoMagico cuadradoMagico = new ProblemaCuadradoMagico(estadoInicial);
        EstrategiaBusqueda buscador = new EstrategiaBusquedaAnchura();
        System.out.println(Arrays.toString(buscador.soluciona(cuadradoMagico)));
    }
}