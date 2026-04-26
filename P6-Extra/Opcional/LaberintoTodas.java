package Opcional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;



public class LaberintoTodas {
    private List<int[][]> soluciones;
    private List<Integer> pasosSoluciones; 
    private int[][] tablero;
    int dimension = 7;
    int filaFin;
    int colFin;
    private int[][] mapaOriginal;
    
    public void resolverLaberinto(int[][] mapa, int posInicio, int posFin){
        this.mapaOriginal = mapa;
        this.soluciones = new ArrayList<>();
        this.pasosSoluciones = new ArrayList<>();

        int filaInicio = posInicio / dimension;
        int colInicio = posInicio % dimension;
        this.filaFin = posFin / dimension;
        this.colFin = posFin % dimension; 

        int[][] tablero = copiarTablero(mapa);
        
        System.out.println("EL LABERINTO ES INICIALMENTE DEL SIGUIENTE MODO:");
        imprimirTablero(tablero);
        System.out.println("El objetivo es ir desde la posición " + posInicio
                         + " a la posición " + posFin);
        System.out.println();

        tablero[filaInicio][colInicio] = 2;

        backTracking(filaInicio, colInicio, tablero, 0);
        mostrarMejorSolucion();
    }

    
    private void backTracking(int fila, int columna, int[][] solucion, int pasos){
        if(fila == filaFin && columna == colFin){
            int[][] copia = copiarTablero(solucion);
            soluciones.add(copia);
            pasosSoluciones.add(pasos);

            System.out.println("Solucion encontrada con " + pasos + " pasos");
            imprimirTablero(copia);
            System.out.println();
            return;
        }

        if(columna+1 < dimension && solucion[fila][columna + 1] == 0){
            solucion[fila][columna + 1] = 2;
            backTracking(fila, columna + 1, solucion, pasos + 1);
            solucion[fila][columna + 1] = 0;
        }
        if (columna - 1 >= 0 && solucion[fila][columna - 1] == 0) {
            solucion[fila][columna - 1] = 2;
            backTracking(fila, columna - 1, solucion, pasos + 1);
            solucion[fila][columna - 1] = 0;
        }
        // Abajo
        if (fila + 1 < dimension && solucion[fila + 1][columna] == 0) {
            solucion[fila + 1][columna] = 2;
            backTracking(fila + 1, columna, solucion, pasos + 1);
            solucion[fila + 1][columna] = 0;
        }
        // Arriba
        if (fila - 1 >= 0 && solucion[fila - 1][columna] == 0) {
            solucion[fila - 1][columna] = 2;
            backTracking(fila - 1, columna, solucion, pasos + 1);
            solucion[fila - 1][columna] = 0;
        }

    }


    private void mostrarMejorSolucion(){
        if(soluciones.isEmpty()){
            System.out.println("No hay solucion");
        }

        int minPasos = Integer.MAX_VALUE;
        int indiceMejor = 0;
        for(int i = 0; i < pasosSoluciones.size(); i++){
            if(pasosSoluciones.get(i) < minPasos){
                minPasos = pasosSoluciones.get(i);
                indiceMejor = i;
            }
        }

        System.out.println("La mejor solucion tiene " + minPasos + " pasos, habiendo un total de " + soluciones.size() + " soluciones diferentes");
        imprimirTablero(soluciones.get(indiceMejor));
    }

    private void imprimirTablero(int[][] tablero) {
        for (int i = 0; i < dimension; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < dimension; j++) {
                switch (tablero[i][j]) {
                    case 0:  sb.append(" ·"); break;
                    case 1:  sb.append(" H"); break;
                    case 2:  sb.append(" *"); break;
                    default: sb.append(" ?"); break;
                }
            }
            System.out.println(sb.toString());
        }
    }

    private int[][] copiarTablero(int[][] mapa) {
        int[][] copia = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            System.arraycopy(mapa[i], 0, copia[i], 0, dimension);
        }
        return copia;
    }


    public static void main(String[] args){
        
        // Scanner sc;
        // sc = new Scanner(new FileReader(args[0]));
        // int[] 
        try{
            List<int[]> filas = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim(); // separa por espacios
                if(linea.isEmpty()) continue;
                String[] valores = linea.split("\\s+");
                int[] fila = new int[valores.length];

                for (int i = 0; i < valores.length; i++) {
                    fila[i] = Integer.parseInt(valores[i]);
                }

                filas.add(fila);
            }

            br.close();

            // Convertir List<int[]> a int[][]
            int[][] matriz = new int[filas.size()][];
            for (int i = 0; i < filas.size(); i++) {
                matriz[i] = filas.get(i);
            }

            int posInicio = Integer.parseInt(args[1]);
            int posFin = Integer.parseInt(args[2]);

            LaberintoTodas algoritmo = new LaberintoTodas();
            algoritmo.resolverLaberinto(matriz, posInicio, posFin);
            return;
        } catch (Exception e){
            System.out.println("Revento");
        }
        

    }
}
