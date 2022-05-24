public class Driver {

    private static void delay( int n ){ // taken from knight's tour code
        try {
        Thread.sleep(n);
        } catch( InterruptedException e ) {
        System.exit(0);
        }
    }

    public static void main(String[] args) {
        Map map = new Map();
        System.out.println(map);

        delay(1000);

        map.moveDown();
        System.out.println(map);

        map.moveRight();
        System.out.println(map);
        delay(1000);
        
        map.moveRight();
        System.out.println(map);
        delay(1000);

        map.moveRight();
        System.out.println(map);
        delay(1000);
        
    }
}
