
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;



public class LaberintoTodas {
    private List<int[][]> soluciones;

    private int[][] tablero;
    int dimension = 7;
    
    
    public List<int[][]> resolverLaberinto(int[][] mapa){
        int[][] tablero = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                tablero[i][j] = mapa[i][j];
            }
        }

        backTracking(0, 0, tablero);
        return soluciones;
    }

    private void backTracking(int columna, int fila, int[][] solucion){
        if(columna == 7 && fila == 7){
            soluciones.add(solucion);
            return;
        }

        if(columna+1 < dimension && solucion[columna + 1][fila] == 0){
            solucion[columna + 1][fila] = 2;
            backTracking(columna + 1, fila, solucion);
            solucion[columna + 1][fila] = 0;
        }
        if(columna-1 >= 0 && solucion[columna -1][fila] == 0){
            solucion[columna - 1][fila] = 2;
            backTracking(columna + 1, fila, solucion);
            solucion[columna - 1][fila] = 0;
        }
        if(fila + 1 < dimension && solucion[columna][fila + 1] == 0){
            solucion[columna][fila + 1] = 2;
            backTracking(columna + 1, fila, solucion);
            solucion[columna][fila + 1] = 0;
        }
        if(fila - 1 >= 0 && solucion[columna][fila - 1] == 0){
            solucion[columna][fila - 1] = 2;
            backTracking(columna + 1, fila, solucion);
            solucion[columna][fila - 1] = 0;
        }

    }


    private void imprimirSoluciones(){
        
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
                String[] valores = linea.trim().split("\\s+"); // separa por espacios
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

            LaberintoTodas algoritmo = new LaberintoTodas();
            algoritmo.resolverLaberinto(matriz);
            return;
        } catch (Exception e){
            System.out.println("Revento");
        }
        

    }
}
