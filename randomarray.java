import java.util.Random;
public class randomarray {
   public static void main(String[] args) {
      Random rd = new Random(); // creating Random object
      int[] arr = new int[20];
      for (int i = 0; i < arr.length; i++) {
         arr[i] = rd.nextInt(20); // storing random integers in an array
         System.out.print(arr[i] + ", "); // printing each array element
      }
   }
}
