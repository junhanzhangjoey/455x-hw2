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

    // 唯一一次创建 Scanner(System.in)
    private static final Scanner IN = new Scanner(System.in);
    private static final String END_COMMAND = "end";

    // --- 主程序入口 ---
    public static void main(String[] args) {
        // 1. 读取并初始化 BookshelfKeeper
        BookshelfKeeper keeper = enterInitialBooks(IN);

        // 2. 初始状态输出
        System.out.println(keeper.toString());

        // 3. 进入交互式操作循环
        handleOperations(keeper, IN);

        // 4. 退出程序 (必须在 main 或 helper 中调用 System.exit)
        System.out.println("Exiting Program.");
        // 注意：由于 System.exit(1) 在 handleOperations 中已经处理了，这里可以省略，
        // 但如果程序运行到这里才结束，说明是正常退出，应该用 System.exit(0) 或直接结束。
        // 为了和作业的退出信息保持一致，我们让 handleOperations 内部处理 System.exit。
    }

    // --- 辅助方法 1: 读取并验证初始书架配置 ---

    /**
     * 读取并验证初始书架配置。如果输入不合法，打印错误并立即退出。
     * 遵循两阶段扫描，并检查：1) 正数，2) 非递减顺序。
     */
    private static BookshelfKeeper enterInitialBooks(Scanner mainScanner) {
        System.out.println("Please enter initial arrangement of books followed by newline:");

        // Stage 1: 读取整个输入行，作为字符串
        String line = mainScanner.nextLine();

        // Stage 2: 使用一个新的 Scanner 来解析这行字符串
        Scanner lineParser = new Scanner(line); 
        ArrayList<Integer> initialBooks = new ArrayList<>();
        int prevHeight = 0; // 用于检查非递减顺序

        while (lineParser.hasNextInt()) {
            int height = lineParser.nextInt();

            // 1. 错误检查 (Run 2): 高度必须是正数
            if (height <= 0) {
                printAndExit("ERROR: Height of a book must be positive.");
            }
            
            // 2. 错误检查 (Run 1): 检查是否非递减顺序
            if (height < prevHeight) {
                 printAndExit("ERROR: Heights must be specified in non-decreasing order.");
            }

            initialBooks.add(height);
            prevHeight = height;
        }
        
        lineParser.close();
        
        // 使用防御性拷贝 (Assuming Bookshelf constructor does this)
        Bookshelf initialShelf = new Bookshelf(initialBooks);
        
        return new BookshelfKeeper(initialShelf);
    }
    
    // --- 辅助方法 2: 处理操作循环 ---

    /**
     * 处理交互式命令循环 (pick, put, end)，包括错误检查。
     */
    private static void handleOperations(BookshelfKeeper keeper, Scanner mainScanner) {
        
        System.out.println("Type pick <index> or put <height> followed by newline. Type end to exit.");
        
        // 读取第一条命令（并清除首尾空格）
        // 使用 hasNextLine 确保流中还有内容
        while (mainScanner.hasNextLine()) {
            String line = mainScanner.nextLine().trim();

            if (line.isEmpty()) { continue; } // 跳过空行

            // 检查退出命令
            if (line.equals(END_COMMAND)) {
                break; // 退出 while 循环
            }

            // 使用临时的 Scanner 解析
            Scanner parser = new Scanner(line);
            
            // 1. 提取命令
            String command = parser.next();
            
            // 2. 检查命令是否是 pick 或 put (Run 3)
            if (!command.equals("put") && !command.equals("pick")) {
                printAndExit("ERROR: Invalid command. Valid commands are pick, put, or end.");
            }
            
            // 3. 提取参数 (作业保证参数是整数且存在)
            int num = parser.nextInt(); 
            parser.close();

            // 4. 执行并检查参数错误
            if (command.equals("put")) {
                handlePutOperation(keeper, num);
            } else { // command.equals("pick")
                handlePickOperation(keeper, num);
            }
            
            // 5. 打印结果
            System.out.println(keeper.toString());
        }
    }
    
    // --- 辅助方法 3 & 4: 处理 Put/Pick 逻辑及错误 ---

    private static void handlePutOperation(BookshelfKeeper keeper, int height) {
        // 错误检查 (Run 5): 高度必须为正数
        if (height <= 0) {
            printAndExit("ERROR: Height of a book must be positive.");
        }
        
        keeper.putHeight(height);
    }
    
    private static void handlePickOperation(BookshelfKeeper keeper, int position) {
        int numBooks = keeper.getNumBooks();
        
        // 错误检查 (Run 4): 位置是否越界 (包括负数索引)
        if (position < 0 || position >= numBooks) {
            printAndExit("ERROR: Entered pick operation is invalid on this shelf.");
        }
        
        keeper.pickPos(position);
    }
    
    // --- 错误退出辅助方法 ---

    /**
     * 打印精确的错误信息并终止程序。
     */
    private static void printAndExit(String errorMessage) {
        System.out.println(errorMessage);
        System.out.println("Exiting Program.");
        System.exit(1);
    }
}