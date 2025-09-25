import java.util.ArrayList;
import java.util.Arrays;

public class TestAssert{
    public static void main(String[] args){
        Bookshelf good = new Bookshelf(new ArrayList<Integer>(Arrays.asList(10, 4, 8)));
        Bookshelf bad = new Bookshelf(new ArrayList<Integer>(Arrays.asList(1, -1, -2)));
    }
}