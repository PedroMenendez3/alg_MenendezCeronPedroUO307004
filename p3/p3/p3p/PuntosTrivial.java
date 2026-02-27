package p3p;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PuntosTrivial
{



public static double[] puntosTrivial (double[][] puntos)
{ 
	double[] resultado = new double[5];
	double distanciaMinima = Double.POSITIVE_INFINITY;

	double dx;
	double dy;
	double distancia;
	for(int i = 0; i < puntos.length; i++){
		for(int j = i + 1; j < puntos.length; j++){
			dx = puntos[i][0] - puntos[j][0];
			dy = puntos[i][1] - puntos[j][1];
			distancia = Math.sqrt(dx*dx + dy*dy);

			if(distancia < distanciaMinima){
				distanciaMinima = distancia;
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
	
	
	double[] cercanos = puntosTrivial(puntos);

	System.out.printf( "PUNTOS MÁS CERCANOS: [%.6f, %.6f][%.6f, %.6f]\n", cercanos[0],cercanos[1],cercanos[2],cercanos[3]);
	System.out.printf("SU DISTANCIA MINIMA=%.6f",cercanos[4]);
	}  // for
} // main
 