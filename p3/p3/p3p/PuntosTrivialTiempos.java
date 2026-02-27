package p3p;

import java.util.Random;

public class PuntosTrivialTiempos
{
	   
public static double[][] rellenarVector(int n){
	double[][] puntos = new double[n][2];
	Random random = new Random();

	for(int i = 0; i < n; i++){
		puntos[i][0] = Math.round(random.nextDouble() * 100 * 1000000.0) / 1000000.0;
		puntos[i][1] = Math.round(random.nextDouble() * 100 * 1000000.0) / 1000000.0;
	}

	return puntos;
}

public static void main (String arg []) 
{
	long t1,t2;
	double[][] puntos = null;
	for(int i = 128; i <= 262144; i*=2){
		puntos = rellenarVector(i);
		t1 = System.currentTimeMillis ();

		PuntosTrivial.puntosTrivial(puntos);

		t2 = System.currentTimeMillis();
		System.out.println (" n="+i+ "**TIEMPO="+(t2-t1));
	}
	
} // main
}
 