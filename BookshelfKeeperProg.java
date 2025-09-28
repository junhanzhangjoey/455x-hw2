// import java.util.Scanner;
// import java.util.ArrayList;
// public class BookshelfKeeperProg{
//     public static void main(String args[]){
//        Scanner in = new Scanner(System.in);
//        BookshelfKeeper keeper = enterInitialBooks(in);
//        handleOperations(keeper, in);
//     //    in.close();
//     }
//     private static BookshelfKeeper enterInitialBooks(Scanner in){
//         System.out.println("Please enter initial arrangement of books followed by newline:");
//         String line = in.nextLine();
//         Scanner parser = new Scanner(line);
//         ArrayList<Integer> list = new ArrayList<Integer>();
//         int prevHeight = 0;
//         while(parser.hasNextInt()){
//             int height = parser.nextInt();
//             if(height <= 0){
//                 System.out.println("ERROR: Height of a book must be positive.");
//                 System.out.println("Exiting Program.");
//                 System.exit(1);
//             }
//             if(prevHeight != 0 && height < prevHeight){
//                 System.out.println("ERROR: Heights must be specified in non-decreasing order.");
//                 System.out.println("Exiting Program.");
//                 System.exit(1);
//             }
//             list.add(height);
//             prevHeight = height;
//         }
//         parser.close();
//         Bookshelf shelf = new Bookshelf(list);
//         BookshelfKeeper res = new BookshelfKeeper(shelf);
//         System.out.println(res.toString());
//         return res;
//     }

//     private static void handleOperations(BookshelfKeeper keeper, Scanner in){
//         System.out.println("Type pick <index> or put <height> followed by newline. Type end to exit.");
//         String line = in.nextLine();

//         while(!line.equals("end")){
//             Scanner parser = new Scanner(line);
//             String operation = parser.next();
//             if(!operation.equals("pick") && !operation.equals("put")){
//                 System.out.println("ERROR: Invalid command. Valid commands are pick, put, or end.");
//                 System.out.println("Exiting Program.");
//                 System.exit(1);
//             }
//             int num = parser.nextInt();
//             parser.close();
//             if(operation.equals("put")){
//                 if(num <= 0){
//                     System.out.println("ERROR: Height of a book must be positive.");
//                     System.out.println("Exiting Program.");
//                     System.exit(1);
//                 }
//                 keeper.putHeight(num);
//             }else if(operation.equals("pick")){
//                 if(num < 0 || num >= keeper.getNumBooks()){
//                     System.out.println("ERROR: Entered pick operation is invalid on this shelf.");
//                     System.out.println("Exiting Program.");
//                     System.exit(1);
//                 }
//                 keeper.pickPos(num);
//             }
//             System.out.println(keeper.toString());
//             line = in.nextLine();
//         }
//         System.out.println("Exiting Program.");
//         System.exit(1);
//     }
// }

import java.util.ArrayList;
import java.util.Scanner;

/**
 * BookshelfKeeperProg
 * A terminal-based interactive program that allows the user to perform a series of pick and put 
 * operations on a BookshelfKeeper object. Implements all required error checking and output formatting.
 */
public class BookshelfKeeperProg {

    // Must create Scanner(System.in) at most once.
    private static final Scanner IN = new Scanner(System.in);
    private static final String END_COMMAND = "end";

    // --- Main Program Entry ---
    public static void main(String[] args) {
        // 1. Read and initialize BookshelfKeeper
        BookshelfKeeper keeper = enterInitialBooks(IN);

        // 2. Output initial state
        System.out.println(keeper.toString());

        // 3. Enter interactive command loop
        handleOperations(keeper, IN);

        // 4. Exiting message is handled within handleOperations or here.
        // We will let the helper function handle the exit sequence for consistency.
    }

    // --- Helper Method 1: Read and Validate Initial Bookshelf ---

    /**
     * Reads and validates the initial bookshelf configuration from the input stream.
     * Prints an error and exits if input violates preconditions (positive, non-decreasing).
     */
    private static BookshelfKeeper enterInitialBooks(Scanner mainScanner) {
        System.out.println("Please enter initial arrangement of books followed by newline:");

        // Stage 1: Read the entire line of input as a String (using newline as sentinel)
        String line = mainScanner.nextLine();

        // Stage 2: Use a temporary Scanner to parse the numbers from the String
        Scanner lineParser = new Scanner(line); 
        ArrayList<Integer> initialBooks = new ArrayList<>();
        int prevHeight = 0; // Used to check non-decreasing order

        while (lineParser.hasNextInt()) {
            int height = lineParser.nextInt();

            // Error Check (Run 2): Height must be positive
            if (height <= 0) {
                printAndExit("ERROR: Height of a book must be positive.");
            }
            
            // Error Check (Run 1): Heights must be in non-decreasing order
            if (height < prevHeight) {
                 printAndExit("ERROR: Heights must be specified in non-decreasing order.");
            }

            initialBooks.add(height);
            prevHeight = height;
        }
        
        lineParser.close();
        
        // Create BookshelfKeeper object
        Bookshelf initialShelf = new Bookshelf(initialBooks);
        
        return new BookshelfKeeper(initialShelf);
    }
    
    // --- Helper Method 2: Handle Operations Loop ---

    /**
     * Handles the interactive command loop (pick, put, end), including error checking.
     */
    private static void handleOperations(BookshelfKeeper keeper, Scanner mainScanner) {
        
        System.out.println("Type pick <index> or put <height> followed by newline. Type end to exit.");
        
        // Read the first line of command input (or the first command from file)
        String line = mainScanner.nextLine().trim();

        while (!line.equals(END_COMMAND)) {
            
            // Handle possibility of empty lines (though assignment says operations line won't be blank)
            if (line.isEmpty()) { 
                line = mainScanner.nextLine().trim();
                continue; 
            }

            // Use temporary Scanner to parse the command and argument
            Scanner parser = new Scanner(line);
            
            // 1. Check if there is a command word
            if (!parser.hasNext()) {
                // If the line was just whitespace (already handled by trim() and !isEmpty()), but defensive check
                line = mainScanner.nextLine().trim();
                continue;
            }
            
            String command = parser.next();
            
            // 2. Check for invalid command string (Run 3)
            if (!command.equals("put") && !command.equals("pick")) {
                printAndExit("ERROR: Invalid command. Valid commands are pick, put, or end.");
            }
            
            // 3. Extract argument (Guaranteed to be present and an integer by assignment specs)
            int num = parser.nextInt(); 
            parser.close();

            // 4. Execute command and check its validity
            if (command.equals("put")) {
                handlePutOperation(keeper, num);
            } else { // command.equals("pick")
                handlePickOperation(keeper, num);
            }
            
            // 5. Output result and get the next command line
            System.out.println(keeper.toString());
            line = mainScanner.nextLine().trim();
        }
        
        // End of program sequence
        System.out.println("Exiting Program.");
        System.exit(1);
    }
    
    // --- Helper Method 3: Put Operation Handler ---

    private static void handlePutOperation(BookshelfKeeper keeper, int height) {
        // Error Check (Run 5): Height must be positive
        if (height <= 0) {
            printAndExit("ERROR: Height of a book must be positive.");
        }
        
        keeper.putHeight(height);
    }
    
    // --- Helper Method 4: Pick Operation Handler ---

    private static void handlePickOperation(BookshelfKeeper keeper, int position) {
        int numBooks = keeper.getNumBooks();
        
        // Error Check (Run 4): Check bounds (0 <= position < size)
        // Also checks against negative index (num < 0)
        if (position < 0 || position >= numBooks) {
            printAndExit("ERROR: Entered pick operation is invalid on this shelf.");
        }
        
        keeper.pickPos(position);
    }
    
    // --- Error Exit Utility ---

    /**
     * Prints the exact error message required by the assignment and terminates the program.
     */
    private static void printAndExit(String errorMessage) {
        System.out.println(errorMessage);
        System.out.println("Exiting Program.");
        System.exit(1);
    }
}