// Name: Junhan Zhang
// USC NetID: 9250642107
// CSCI455 PA2
// Fall 2025

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Class BookshelfKeeperProg
 *
 * Provides a command-line interface for managing a bookshelf.
 * Users can initialize the bookshelf by inputting book heights, and then perform mutations
 * by typing commands:
 * - pick <index> : removes the book at the given index
 * - put <height> : inserts a book of the given height into the bookshelf
 * - end          : exits the program
 */

/**
 * Program entry point.
 * Initializes the BookshelfKeeper and handles user operations.
 *
 * @param args command-line arguments (not used)
 */
public class BookshelfKeeperProg{
    public static void main(String args[]){
       Scanner in = new Scanner(System.in);
       BookshelfKeeper keeper = enterInitialBooks(in);
       handleOperations(keeper, in);
    //    in.close();
    }
    /**
     * Reads the initial arrangement of books from user input.
     *
     * @param in the Scanner object used to read input
     * @return a BookshelfKeeper instance initialized with the given book heights
     */

    private static BookshelfKeeper enterInitialBooks(Scanner in){
        System.out.println("Please enter initial arrangement of books followed by newline:");
        String line = in.nextLine();
        Scanner parser = new Scanner(line);
        ArrayList<Integer> list = new ArrayList<Integer>();
        int prevHeight = 0;
        while(parser.hasNextInt()){
            int height = parser.nextInt();
            if(height <= 0){
                System.out.println("ERROR: Height of a book must be positive.");
                System.out.println("Exiting Program.");
                System.exit(1);
            }
            if(prevHeight != 0 && height < prevHeight){
                System.out.println("ERROR: Heights must be specified in non-decreasing order.");
                System.out.println("Exiting Program.");
                System.exit(1);
            }
            list.add(height);
            prevHeight = height;
        }
        parser.close();
        Bookshelf shelf = new Bookshelf(list);
        BookshelfKeeper res = new BookshelfKeeper(shelf);
        System.out.println(res.toString());
        return res;
    }
    /**
     * 
     * @param keeper the BookshelfKeeper instance that the user just initialized
     * @param in a Scanner instance that allow users to input operation, pick, put or end 
     */
    private static void handleOperations(BookshelfKeeper keeper, Scanner in){
        System.out.println("Type pick <index> or put <height> followed by newline. Type end to exit.");
        String line = in.nextLine().trim();

        while(!line.equals("end")){
            if(line.isEmpty()){
                line = in.nextLine().trim();
                continue;
            }
            Scanner parser = new Scanner(line);
            String operation = parser.next();
            if(!operation.equals("pick") && !operation.equals("put")){
                System.out.println("ERROR: Invalid command. Valid commands are pick, put, or end.");
                System.out.println("Exiting Program.");
                System.exit(1);
            }
            int num = parser.nextInt();
            parser.close();
            if(operation.equals("put")){
                if(num <= 0){
                    System.out.println("ERROR: Height of a book must be positive.");
                    System.out.println("Exiting Program.");
                    System.exit(1);
                }
                keeper.putHeight(num);
            }else if(operation.equals("pick")){
                if(num < 0 || num >= keeper.getNumBooks()){
                    System.out.println("ERROR: Entered pick operation is invalid on this shelf.");
                    System.out.println("Exiting Program.");
                    System.exit(1);
                }
                keeper.pickPos(num);
            }
            System.out.println(keeper.toString());
            line = in.nextLine().trim();
        }
        System.out.println("Exiting Program.");
        System.exit(1);
    }
}
