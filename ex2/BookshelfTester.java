import java.util.ArrayList;
import java.util.Arrays;

public class BookshelfTester{

    public static void main(String [] args){
        Bookshelf emptyBs = new Bookshelf();
        System.out.println("Result: " + emptyBs.toString());
        System.out.println("Expected: []");

        Bookshelf  bs = new Bookshelf(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5)));
        System.out.println("Result: " + bs.toString());
        System.out.println("Expected: [1, 2, 3, 4, 5]");
    }
}