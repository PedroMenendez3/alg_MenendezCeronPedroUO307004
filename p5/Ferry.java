

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ferry {
    
    private int boatLength; //Longitud de los carriles del barco
    private List<Integer> vehicles; //Lista de vehiculos 
    private boolean [][] dp; //Matriz con las posibles soluciones
    private int[] sumatorio; //Suma acumulada de las longitudes de los vehiculos

    private List<Step> path;

    public Ferry(int longitud, List<Integer> vehicles){
        this.boatLength = longitud;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];

        this.sumatorio = new int[vehicles.size() + 1];

        this.sumatorio[0] = 0;
        for(int i = 1; i <= vehicles.size(); i++){
            sumatorio[i] = sumatorio[i-1] + vehicles.get(i-1);
        }

        this.path = new ArrayList<Step>();
    }

    public void run(){
        dp[0][0] = true;

        for(int i = 1; i < vehicles.size() + 1; i++){
            
            for(int p = 0; p <= boatLength; p++){
                if(!dp[i - 1][p]){
                    continue;
                }
                
                if(p + vehicles.get(i-1) <= boatLength){
                    dp[i][p + vehicles.get(i-1)] = true;
                }
    
                if(sumatorio[i] - p <= boatLength){
                    dp[i][p] = true;       
               }
                
            }
            
        }
    }

    /**
    * Devuelve el numero máximo de vehiculos posibles
    * l (siendo l < boatlength) con dp[i][l] = true. es el maximo número de coches que pueden entrar.
    */
    public int getMaximumNumberOfVehicles() {
        for(int i = vehicles.size(); i >= 0; i--){
            for(int l = 0; l <= boatLength; l++){
                if(dp[i][l]){
                    return i;
                }
            }
        }
        return 0;
    }
    
    public void printData(){
        System.out.printf("Length of parallel lanes for starboard and port on the ferry: %d\n", boatLength);
		System.out.printf("The vehicles have the following lengths:\n");
		for (int i = 0; i < vehicles.size(); i++) {
			System.out.printf("\tVehicle %d: %d\n", i+1, vehicles.get(i));
		}
    }

    public void printPossibleAssignation() {
		boolean found = false;
		System.out.printf("\nPossible assignation:\n");
		for (int i = getMaximumNumberOfVehicles(); i > 0; i--) {
			//si found es true -> rompo la ejecución
            if(found){
                break;
            }
			//para cada p de la longitud del barco
            for(int p = 0; p <= boatLength; p++){
                //si found es true -> rompo la ejecución
                if(found){
                    break;
                }
                //si dp[i][p-v(i)] es true -> found = true; llamo a processAssignation()
                if(p - vehicles.get(i-1)>=0 && dp[i - 1][p-vehicles.get(i - 1)]){
                    found = true;
                    processAssignation(i,p);
                }
            }
		}
	}

    private void processAssignation(int i, int l){
        // if ((i == 0) && (l == 0)) { // llamo a printPath y acabo la ejecución (return)
        if((i == 0) && (l == 0)){
            printPath();
            return;
        }
        //if (dp[i-1][l]) {
	    //añado al path (path.addFirst) un nuevo Step llamado estribor; llamo a processAssignation(i-1, l);
        if(dp[i-1][l]){
            Step estribor = new Step(i - 1, l, i, l, i, "estribor");
            path.addFirst(estribor);
            processAssignation(i - 1, l);
            return;
        }
        // if (dp[i-1][l-vehicles.get(i-1)]) {
	    //añado al path (path.addFirst) un nuevo Step llamado babor; llamo a processAssignation(i-1, l-vehicles.get(i-1));
        if(l - vehicles.get(i - 1) >= 0 && dp[i - 1][l - vehicles.get(i - 1)]){
            Step babor = new Step(i - 1, 1 - vehicles.get(i - 1), i, l, i, "babor");
            path.addFirst(babor);
            processAssignation(i - 1, l - vehicles.get(i - 1));
        }
    }

    public void printSolutionTable() {
        System.out.printf("\nTable with calculations:\n");
            
        System.out.printf("%4s", "V/L");
        for (int i = 0; i <= boatLength; i++) {
            System.out.printf("%4d", i);	
        }
        System.out.printf("\n");
            
        for (int i = 0; i <= vehicles.size(); i++) {
            System.out.printf("%4d", i);
            for (int l = 0; l <= boatLength; l++) {
                if (dp[i][l]){				
                    System.out.printf("%4s", "T");
                }
                else{ 
                        System.out.printf("%4s", "F");
                    }
                }
                System.out.printf("\n");
            }
	}


    private void printPath() {
        int portLength = 0;
        int starboardLength = 0;
        for (var step : path) {		
            if (step.movement().equals("babor")){
                portLength += vehicles.get(step.vehicle()-1);
            }
            else{
                starboardLength += vehicles.get(step.vehicle()-1);
            }
            System.out.printf("Vehicle %d (length %d) -- From (%d, %d) -- To (%d, %d) -- Position: %s -- Port lengh: %d -- Starboard length: %d\n", 
                    step.vehicle(), vehicles.get(step.vehicle()-1),
                    step.previousI(), step.previousL(),
                    step.currentI(), step.currentL(), 
                    step.movement(), portLength, starboardLength);
        }
    }

    public static void main(String[] args){
        String file = args[0];
        int length = 0;
	    List<Integer> vehicles = new ArrayList<Integer>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			length = Integer.valueOf(reader.readLine());		
			for (String s : reader.readLine().split(" ")) {
				vehicles.add(Integer.valueOf(s));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

        Ferry ferry = new Ferry(length, vehicles);
        ferry.run();
 
        int n = ferry.getMaximumNumberOfVehicles();
 
        System.out.printf("\nHan llegado un total de %d vehículos (%d viajarán).\n",
                vehicles.size(), n);
 
        ferry.printSolutionTable();
        ferry.printPossibleAssignation();
    }

}

record Step(int previousI, int previousL, 
		int currentI, int currentL, 
		int vehicle, String movement) {}
