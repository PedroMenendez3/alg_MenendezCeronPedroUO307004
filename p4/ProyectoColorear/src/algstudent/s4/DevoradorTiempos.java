package algstudent.s4;

public class DevoradorTiempos {
    public static void main(String arg[]){
        long t1,t2;
        for(int i = 8; i < 65537; i*=2){
            t1 = System.currentTimeMillis();
            Devorador.main("sols/g" + i + ".json");
            t2 = System.currentTimeMillis();
            System.out.println(" n=" +i+"**TIEMPO="+(t2-t1));
        }
    }
}
