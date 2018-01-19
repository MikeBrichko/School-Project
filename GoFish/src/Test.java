public class Test {
    public static void main(String[] args) {
        int[] test = {5, 8 , 3};
        int indexOfValue = 0;
        boolean allZeros = false;
        int placement = 1;

        while (!allZeros) {
            int max = 0;
            for (int i = 0; i < test.length; i++) {
                if (test[i] > max) {
                    max = test[i];
                    indexOfValue = i;
                }
            }

            if(max == 0){
                break;
            }

            test[indexOfValue] = 0;
            System.out.println("Player" + (indexOfValue+1) + " placed number " + placement + ".");
            placement +=1;
        }

        for(int i=0; i<test.length; i++) {
            System.out.println(test[i]);
        }
    }
}
