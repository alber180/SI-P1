package es.udc.sistemasinteligentes.e2;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ProblemaCuadradoMagico extends ProblemaBusqueda {

    public static class EstadoCuadradoMagico extends Estado {

        private int[][] cuadradoMagico;
        private int n;

        public EstadoCuadradoMagico(int[][] tablero) {
            this.n = tablero.length;
            this.cuadradoMagico = new int[n][n];

            // Se hace la copia para que vaya guay
            for (int i = 0; i < n; i++) {
                System.arraycopy(tablero[i], 0, this.cuadradoMagico[i], 0, n);
            }
        }


        public int getNumeroMagico() {
            return (n * (n * n + 1) / 2);
        }

        // No se pueden poner numeros repetidos, va de 1 a N^2
        public boolean yaUtilizado(int num) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(cuadradoMagico[i][j] == num) return true;
                }
            }

            return false;
        }

        public boolean todasRellenas() {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(cuadradoMagico[i][j] == 0) return false;
                }
            }

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EstadoCuadradoMagico)) return false;
            EstadoCuadradoMagico otro = (EstadoCuadradoMagico) o;
            if (this.n != otro.n) return false;
            for (int i = 0; i < n; i++){
                if (!Arrays.equals(this.cuadradoMagico[i], otro.cuadradoMagico[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(cuadradoMagico);
        }

        @Override
        public String toString() {
            StringBuilder cad = new StringBuilder();

            cad.append("[");
            for (int i = 0; i < n; i++) {
                cad.append("(");

                for (int j = 0; j < n; j++) {
                    cad.append(" ").append(cuadradoMagico[i][j]);
                }

                cad.append(" )\n");
            }
            cad.append("]");

            return cad.toString();
        }
    }

    public static class AccionCuadradoMagico extends Accion {

        private int fila, col, num;

        public AccionCuadradoMagico(int fila, int col, int num) {
             this.fila = fila;
             this.col = col;
             this.num = num;
        }

        @Override
        public boolean esAplicable(Estado es) {
            EstadoCuadradoMagico estado = (EstadoCuadradoMagico)es;

            if(estado.cuadradoMagico[fila][col] != 0) return false;
            if(estado.yaUtilizado(num)) return false;

            return true;
        }

        @Override
        public Estado aplicaA(Estado es) {
            EstadoCuadradoMagico estado = (EstadoCuadradoMagico)es;
            int n = estado.n;
            int[][] nuevoCuadradoMagico = new int[n][n];

            // Si no hacemos copia del cuadrado se va a la shit
            for (int i = 0; i < n; i++) {
                System.arraycopy(estado.cuadradoMagico[i], 0, nuevoCuadradoMagico[i], 0, n);
            }
            nuevoCuadradoMagico[fila][col] = num;

            return new EstadoCuadradoMagico(nuevoCuadradoMagico);
        }

        @Override
        public String toString() {
            return "Añadido en (" + fila + ", " + col + ") -> " + num;
        }
    }

    public ProblemaCuadradoMagico(Estado estadoInicial) {
        super(estadoInicial);
    }

    @Override
    public boolean esMeta(Estado es) {
        EstadoCuadradoMagico estado = (EstadoCuadradoMagico) es;
        if(!estado.todasRellenas()) return false;

        int numeroMagico = estado.getNumeroMagico();
        int n = estado.cuadradoMagico.length;
        int[][] tablero = estado.cuadradoMagico;

        int sumaFila;
        int sumaColumna;
        int sumaDiagonalPrincipal = 0;
        int sumaDiagonalSecundaria = 0;

        for (int i = 0; i < n; i++) {
            sumaFila = 0;
            sumaColumna = 0;
            sumaDiagonalPrincipal += tablero[i][i];
            sumaDiagonalSecundaria += tablero[i][n-1-i];

            for (int j = 0; j < n; j++) {
                sumaFila += tablero[i][j];
                sumaColumna += tablero[j][i];
            }

            if(sumaFila != numeroMagico) return false;
            if(sumaColumna != numeroMagico) return false;
        }
        if(sumaDiagonalPrincipal != numeroMagico) return false;
        if(sumaDiagonalSecundaria != numeroMagico) return false;

        return true;
    }

    // Devuelve todas las acciones dado un tablero
    @Override
    public Accion[] acciones(Estado es) {
        EstadoCuadradoMagico estado = (EstadoCuadradoMagico) es;
        int n = estado.cuadradoMagico.length;
        ArrayList<Accion> acciones = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(estado.cuadradoMagico[i][j] == 0) {
                    for (int num = 1; num <= n * n; num++) {
                        AccionCuadradoMagico accion = new AccionCuadradoMagico(i, j, num);

                        // Solo vamos a meter las acciones validas, tipo que tenga un 0 y no se utilizase el num
                       if(accion.esAplicable(estado)) {
                           acciones.add(accion);
                       }
                    }
                }
            }
        }

        return acciones.toArray(new Accion[0]);
    }
}