public class AlmacenajeContenedoresTiempos{
    public static void main(String arg[]){
        long t1,t2;
        
        for(int i = 0; i < 10; i++){
            t1 = System.currentTimeMillis();

            AlmacenajeContenedores.main("CasosPrueba/test0" + i + ".txt");
            
            t2 = System.currentTimeMillis();
            System.out.println(" test " +i+"**TIEMPO="+(t2-t1));
        }
    }
}