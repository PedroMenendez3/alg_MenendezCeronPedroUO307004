
import java.util.List;

public class Ferry {
    
    private int boatLength; //Longitud de los carriles del barco
    private List<Integer> vehicles; //Lista de vehiculos 
    private boolean [][] dp; //Matriz con las posibles soluciones
    private int[] sumatorio; //Suma acumulada de las longitudes de los vehiculos

    public Ferry(int longitud, List<Integer> vehiculos){
        this.boatLength = longitud;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];

        this.sumatorio = new int[vehicles.size() + 1];

        this.sumatorio[0] = 0;
        for(int i = 1; i <= vehicles.size(); i++){
            sumatorio[i] = sumatorio[i-1] + vehicles.get(i-1);
        }


    }

    public void run(){
        dp[0][0] = true;

        for(int i = 1; i < vehicles.size() + 1; i++){
            
            for(int p = 0; p <= boatLength; p++){
                if(!dp[i - 1][p]){
                    continue;
                }
                
                if(p + vehicles.get(i-1) < boatLength){
                    dp[i][p + vehicles.get(i-1)] = true;
                }
    
                if(sumatorio[i] - p <= boatLength){
                    dp[i][p] = true;       
               }
                
            }
            
        }
    }

    // private void loadData(String file){
    //     this.vehicles = new ArrayList<
    // }

    public void printData(){
        System.out.printf("Longitud de los carriles: %d", boatLength);
        System.out.printf("Longitud de los vehiculos:\n");
        for(int i = 0; i < vehicles.size(); i++){
            System.out.printf("\tVehiculo %d: %d unidades\n", i + 1, vehicles.get(i));
        }
    }
}
