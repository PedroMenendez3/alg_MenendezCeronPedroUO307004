
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedores {
    
    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK; //Numero minimo de contenedores
    private List<List<Integer>> mejorDistribucion; 
    private int llamadasRecursivas = 0;

    public AlmacenajeContenedores(int capacidadC, Integer[] conjuntoS){
        this.capacidadC = capacidadC;
        this.conjuntoS = conjuntoS;
        Arrays.sort(this.conjuntoS, Collections.reverseOrder());
        this.mejorK = conjuntoS.length;
    }

    public void resolver(){
        //ordenar descendente (ya se hace en el constructor)

        List<List<Integer>> contenedores = new ArrayList<>();
        backTracking(0, contenedores);
        //mostrar solucion
        imprimirSolucion();
    }

    private void backTracking(int indexObject, List<List<Integer>> contenedores){
        llamadasRecursivas++;
        //Caso base
        if(indexObject == conjuntoS.length){
            if(contenedores.size() < mejorK){
                mejorK = contenedores.size();
                mejorDistribucion = copiar(contenedores);
            }
            return; //OJO muy importante este return para que no se siga con la ejecucion estando ya en el caso base
        }

        //Podamos if(size contenedores) > mejorK paramos
        if(contenedores.size() >= mejorK){
            return;
        }

        //Probar a meter en contenedores existentes 
        for(int i = 0; i < contenedores.size(); i++){
            if(sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC){
                // Avanzar
                contenedores.get(i).add(conjuntoS[indexObject]);
                //backTracking
                backTracking(indexObject + 1, contenedores);
                //Retroceder
                contenedores.get(i).remove(contenedores.get(i).size() -1);
            }
        }

        //Intentar meterlo en un nuevo contenedor
        List<Integer> nuevoContenedor = new ArrayList<>();
        nuevoContenedor.add(conjuntoS[indexObject]);
        //Avanzo
        contenedores.add(nuevoContenedor);
        //backTracking
        backTracking(indexObject + 1, contenedores);
        //Retrocedo
        contenedores.remove(contenedores.size() - 1);
        
    }

    private List<List<Integer>> copiar(List<List<Integer>> contenedores){
        List<List<Integer>> copia = new ArrayList<>();
        for(List<Integer> i : contenedores){
            copia.add(new ArrayList<>(i));
        }
        return copia;
    }

    private Integer sum(List<Integer> contenedor){
        Integer sumatorio= 0;
        for(int i = 0; i < contenedor.size(); i++){
            sumatorio = sumatorio + contenedor.get(i);
        }
        return sumatorio;
    }

    private void imprimirSolucion(){
        System.out.println("Lista de contenedores y objetos contenidos: ");
        if(mejorDistribucion == null){
            System.out.print("no hay mejor distribucion");
            return;
        }
            
        for(int i = 0; i < mejorDistribucion.size(); i++){
            System.out.print("Contenedor " + (i+1) + ": ");
            for(int j = 0; j < mejorDistribucion.get(i).size(); j++){
                System.out.print(mejorDistribucion.get(i).get(j) + " ");
            }
            System.out.println("");
        }

        System.out.println("El número de contenedores necesario es " + mejorK);
        System.out.println("Se han realizado " + llamadasRecursivas + " llamadas recursivas");
    }

    public static void main(String archivo){
        try {
            Scanner sc;
            sc = new Scanner(new FileReader(archivo));
            int capacidadC = sc.nextInt();
            sc.nextLine();
            String[] parts = sc.nextLine().split(" ");
            Integer[] toS = new Integer[parts.length];
            
            int i = 0;
            for(String s : parts){
                toS[i] = Integer.parseInt(s);
                i++;
            }
            
            sc.close();
            new AlmacenajeContenedores(capacidadC,toS).resolver();
        } catch (FileNotFoundException ex) {
            System.out.println("Reventó");
        }
    }

}
