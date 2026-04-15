
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedoresRyP {
    
    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK; //Numero minimo de contenedores
    private List<List<Integer>> mejorDistribucion; 
    private int llamadasRecursivas = 0;

    public AlmacenajeContenedoresRyP(int capacidadC, Integer[] conjuntoS){
        this.capacidadC = capacidadC;
        this.conjuntoS = conjuntoS;
        Arrays.sort(this.conjuntoS, Collections.reverseOrder());
        
        for(int i = 0; i < conjuntoS.length-1; i+=2){
            if((conjuntoS[i] + conjuntoS[i+1])%capacidadC == 0){
                mejorK+=(conjuntoS[i] + conjuntoS[i+1])/capacidadC;
            } else {
                mejorK+=(conjuntoS[i] + conjuntoS[i+1])/capacidadC + 1;
            }
        }

        if(conjuntoS.length%2 != 0){
            if(conjuntoS[conjuntoS.length-1]%capacidadC == 0){
                mejorK += conjuntoS[conjuntoS.length-1]/capacidadC;
            } else {
                mejorK += conjuntoS[conjuntoS.length-1]/capacidadC + 1;
            }
        }
    }

    public void resolver(){
        //ordenar descendente (ya se hace en el constructor)

        List<List<Integer>> contenedores = new ArrayList<>();
        backTracking(0, contenedores, sumarS());
        //mostrar solucion
        imprimirSolucion();
    }

    private void backTracking(int indexObject, List<List<Integer>> contenedores, int sumaRestante){
        llamadasRecursivas++;

        //LowerBound
        // Calcular el numero minimo teorico de contenedores adicionales necesarios
        int lowerBound = (sumaRestante + capacidadC - 1)/ capacidadC;
        //Podamos if(size contenedores) > mejorK paramos
        if(contenedores.size() + lowerBound >= mejorK) return;


        //Caso base
        if(indexObject == conjuntoS.length){
            if(contenedores.size() < mejorK){
                mejorK = contenedores.size();
                mejorDistribucion = copiar(contenedores);
            }
            return; //OJO muy importante este return para que no se siga con la ejecucion estando ya en el caso base
        }

        //Probar a meter en contenedores existentes 
        for(int i = 0; i < contenedores.size(); i++){
            if(sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC){
                // Avanzar
                contenedores.get(i).add(conjuntoS[indexObject]);
                //backTracking
                backTracking(indexObject + 1, contenedores, sumaRestante - conjuntoS[indexObject]);
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
        backTracking(indexObject + 1, contenedores, sumaRestante - conjuntoS[indexObject]);
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

    private int sumarS(){
        int resultado = 0;
        for(int i = 0; i < conjuntoS.length;i++){
            resultado += conjuntoS[i];
        }
        return resultado;
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

    public static void main(String[] archivo){
        try {
            Scanner sc;
            sc = new Scanner(new FileReader(archivo[0]));
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
            new AlmacenajeContenedoresRyP(capacidadC,toS).resolver();
        } catch (FileNotFoundException ex) {
            System.out.println("Reventó");
        }
    }

}
