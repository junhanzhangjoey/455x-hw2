import java.util.ArrayList;
import java.util.Arrays;

public class BookshelfTester{

    public static void main(String [] args){
        //Ex.2
        Bookshelf emptyBs = new Bookshelf();
        System.out.println("Test for Ex. 2");

        System.out.println("Result: " + emptyBs.toString());
        System.out.println("Expected: []");

        Bookshelf  bs = new Bookshelf(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5)));
        System.out.println("Result: " + bs.toString());
        System.out.println("Expected: [1, 2, 3, 4, 5]");
        System.out.println("--------------------------");
        //Ex.3
        Bookshelf bs3 = new Bookshelf(new ArrayList<Integer>(Arrays.asList(3, 8, 9)));
        bs3.addFront(2);
        System.out.println("Test for Ex. 3");
        System.out.println("Result: " + bs3.toString());
        System.out.println("Expected: [2, 3, 8, 9]");

        bs3.addLast(10);
        System.out.println("Result: " + bs3.toString());
        System.out.println("Expected: [2, 3, 8, 9, 10]");

        bs3.removeFront();
        System.out.println("Result: " + bs3.toString());
        System.out.println("Expected: [3, 8, 9, 10]");

        bs3.removeLast();
        System.out.println("Result: " + bs3.toString());
        System.out.println("Expected: [3, 8, 9]");
        System.out.println("--------------------------");
    }
}