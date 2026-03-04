package p3p;

public class PuntosDyVEnClase 
{

private double minDistancia = Double.POSITIVE_INFINITY;
private double[][] matrizPuntos;

//metodo que lee la matriz (bufferedReader)

//ordenar la matriz por x

public static void main (String[] arg){
    buscarDistanciaMinima();

}

public static String[] buscarDistanciaMinima(){
    Arrays.sort(matrizPuntos, Comparator.comparingDouble(matriz -> matriz[0]))
    
    int iz = 0;
    int de = matrizPuntos.length - 1;
    
    buscarDistanciaMinimaRec(matrizPuntos, iz, de);
    medio = (iz + de)/2;
    if(minDistancia > distancia(medio, medio + 1)){
        minDistancia = distancia(medio, medio + 1)
    }

    return minDistancia;

    
}

private static void buscarDistanciaMinimaRec(double[][] matriz, int iz, int de){
    if(de - iz == 1){
        
        //minDistancia = sqrt
    }

    buscarDistanciaMinimaRec(matrizPuntos, iz, (iz + de)/2);
    buscarDistanciaMinimaRec(matrizPuntos, (iz + de)/2 + 1, de);
}


//revisar limite inicial


























}