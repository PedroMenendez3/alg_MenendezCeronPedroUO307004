package algstudent.s4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColoreoGrafo {

    static private String[] colores = {"red", "blue", "green", "yellow","orange", "purple","cyan", "magenta", "lime"};

    static public Map<String,String> realizarVoraz(Map<String,List<Long>> grafo){
        Map<String,String> coloreado = new HashMap<>();

        //Recorro todo el grafo
        for(String nodo : grafo.keySet()){
            
            List<String> coloresVecinos = new ArrayList<String>();

            //Recorro los vecinos del grafo seleccionado en esta iteracion
            for(Long vecino : grafo.get(nodo)){

                //Si el vecino ya estaba coloreado se guarda el color para no repetirlo
                if(coloreado.containsKey(vecino.toString())){
                    coloresVecinos.add(coloreado.get(vecino.toString()));
                }
            }

            //Se selecciona el primer color de la lista que no coincida con los de los vecinos
            String color = null;
            for(String c : colores){
                if(!coloresVecinos.contains(c)){
                    color = c;
                    break;
                }
            }

            //Guardo el nodo con su color
            coloreado.put(nodo,color);
        }

        return coloreado;
    }
}
