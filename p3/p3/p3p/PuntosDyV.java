package p3p;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PuntosDyV
{

private static double distancia(double[] p1, double[] p2){
	double dx = p1[0] - p2[0];
	double dy = p1[1] - p2[1];
	return Math.sqrt(dx*dx + dy*dy);

}

public static double[] puntosDyV (double[][] puntos, int izq, int dcha)
{ 
	//Caso base: solo quedan 2 puntos
	if(dcha - izq == 1){
		return new double[]{puntos[izq][0], puntos[izq][1], puntos[dcha][0], puntos[dcha][1], distancia(puntos[izq], puntos[dcha])};
	}

	int medio = (izq + dcha)/2;

	double[] resultadoIzq = puntosDyV(puntos, izq, medio);
	double[] resultadoDcha = puntosDyV(puntos, medio + 1, dcha);

	//Nos quedamos con la mitad que tenga una distancia menor
	double[] resultado = null;
	if(resultadoIzq[4] < resultadoDcha[4]){
		resultado = resultadoIzq;
	} else {
		resultado = resultadoDcha;
	}

	//Se comparan los puntos de las dos mitades
	double distancia;
	for(int i = izq; i <= medio; i++){
		for(int j = medio + 1; j < dcha; j++){
			
			distancia = distancia(puntos[i], puntos[j]);

			if(distancia < resultado[4]){
				resultado[0] = puntos[i][0];
				resultado[1] = puntos[i][1];
				resultado[2] = puntos[j][0];
				resultado[3] = puntos[j][1];
				resultado[4] = distancia;
			}
		}
	}

	return resultado;
}
	   

public static void main (String arg []) 
{
	
	String[] datosPuntos = null;
	int contador = 0;
	double[][] puntos = null;
	try{
		BufferedReader fichero = new BufferedReader(new FileReader(arg[0]));
		puntos = new double[Integer.parseInt(fichero.readLine())][2];
		while (fichero.ready()) {
			String linea = fichero.readLine();
			datosPuntos = linea.split(",");
			puntos[contador][0] = Double.parseDouble(datosPuntos[0]);
			puntos[contador][1] = Double.parseDouble(datosPuntos[1]);
			contador++;
		}
		fichero.close();
	} catch (FileNotFoundException fnfe) {
		System.out.println("El archivo no se ha encontrado.");
	} catch (IOException ioe) {
		new RuntimeException("Error de entrada/salida.");
	}
	
	
	double[] cercanos = puntosDyV(puntos, 0, puntos.length - 1);

	System.out.printf( "PUNTOS MÁS CERCANOS: [%.6f, %.6f][%.6f, %.6f]\n", cercanos[0],cercanos[1],cercanos[2],cercanos[3]);
	System.out.printf("SU DISTANCIA MINIMA=%.6f",cercanos[4]);
	}  // for
} // main
 