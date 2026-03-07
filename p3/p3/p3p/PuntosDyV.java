package p3p;

import java.util.Arrays;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PuntosDyV
{

private static double minDistancia = Double.POSITIVE_INFINITY;
private static double[][] matrizPuntos;
private static double[] punto1 = new double[2];
private static double[] punto2 = new double[2];

private static double distancia(double[] p1, double[] p2){
	double dx = p1[0] - p2[0];
	double dy = p1[1] - p2[1];
	return Math.sqrt(dx*dx + dy*dy);

}

public static double[] puntosDyV(double[][] puntos) {
    minDistancia = Double.POSITIVE_INFINITY;
    punto1 = new double[2];
    punto2 = new double[2];
    matrizPuntos = puntos;
    return puntosDyV();
}

public static double[] puntosDyV ()
{ 
	Arrays.sort(matrizPuntos, Comparator.comparingDouble(p ->p[0]));

    int izq = 0;
    int dcha = matrizPuntos.length - 1;

    buscarDistanciaMinimaRec(matrizPuntos, izq, dcha);

    return new double[]{punto1[0], punto1[1], punto2[0], punto2[1], minDistancia};


}
	   
private static void buscarDistanciaMinimaRec(double[][] matriz, int iz, int de){
    if(de - iz == 1){
        double d = distancia(matriz[iz], matriz[de]);
        if(d < minDistancia){
            minDistancia = d;
            punto1[0] = matriz[iz][0];
            punto1[1] = matriz[iz][1];
            punto2[0] = matriz[de][0];
            punto2[1] = matriz[de][1];
        }
        return;
    }

	int medio = (iz + de)/2;

    buscarDistanciaMinimaRec(matriz, iz, medio);
    buscarDistanciaMinimaRec(matriz, medio + 1, de);

    double d = distancia(matriz[medio], matriz[medio + 1]);
    if (d < minDistancia) {
        minDistancia = d;
        punto1[0] = matriz[medio][0];
        punto1[1] = matriz[medio][1];
        punto2[0] = matriz[medio + 1][0];
        punto2[1] = matriz[medio + 1][1];
    }
}


public static void main (String arg []) 
{
	String[] datosPuntos = null;
	int contador = 0;
	try{
		BufferedReader fichero = new BufferedReader(new FileReader(arg[0]));
		matrizPuntos = new double[Integer.parseInt(fichero.readLine())][2];
		while (fichero.ready()) {
			String linea = fichero.readLine();
			datosPuntos = linea.split(",");
			matrizPuntos[contador][0] = Double.parseDouble(datosPuntos[0]);
			matrizPuntos[contador][1] = Double.parseDouble(datosPuntos[1]);
			contador++;
		}
		fichero.close();
	} catch (FileNotFoundException fnfe) {
		System.out.println("El archivo no se ha encontrado.");
	} catch (IOException ioe) {
		new RuntimeException("Error de entrada/salida.");
	}
	
	double[] cercanos = puntosDyV();

	System.out.printf( "PUNTOS MÁS CERCANOS: [%.6f, %.6f][%.6f, %.6f]\n", cercanos[0],cercanos[1],cercanos[2],cercanos[3]);
	System.out.printf("SU DISTANCIA MINIMA=%.6f",cercanos[4]);
	} 
}

