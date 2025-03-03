package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.EstrategiaBusquedaGrafo;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        ProblemaAspiradora.EstadoAspiradora estadoInicial = new ProblemaAspiradora.EstadoAspiradora(ProblemaAspiradora.EstadoAspiradora.PosicionRobot.IZQ,
                                                                                                    ProblemaAspiradora.EstadoAspiradora.PosicionBasura.AMBAS);
        ProblemaBusqueda aspiradora = new ProblemaAspiradora(estadoInicial);

         EstrategiaBusqueda buscador = new Estrategia4();
         System.out.println(Arrays.toString(buscador.soluciona(aspiradora)));

        EstrategiaBusquedaGrafo buscadorConGrafo = new EstrategiaBusquedaGrafo();
        System.out.println(Arrays.toString(buscadorConGrafo.soluciona(aspiradora)));
    }
}
