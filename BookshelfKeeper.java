// Name: 
// USC NetID: 
// CSCI455 PA2
// Spring 2025


/**
 * Class BookshelfKeeper 
 *
 * Enables users to perform efficient putPos or pickHeight operation on a bookshelf of books kept in 
 * non-decreasing order by height, with the restriction that single books can only be added 
 * or removed from one of the two *ends* of the bookshelf to complete a higher level pick or put 
 * operation.  Pick or put operations are performed with minimum number of such adds or removes.
 */
import java.util.ArrayList;

public class BookshelfKeeper {
   private Bookshelf shelf;
   private int totalOperations;
   private int lastPickOrPutOperations;
   /**
      Representation invariant:

      <put rep. invar. comment here>

   */
   
   // <add instance variables here>


   /**
    * Creates a BookShelfKeeper object with an empty bookshelf
    */
   public BookshelfKeeper() {
      shelf = new Bookshelf();
   }

   /**
    * Creates a BookshelfKeeper object initialized with the given sorted bookshelf.
    * Note: method does not make a defensive copy of the bookshelf.
    *
    * PRE: sortedBookshelf.isSorted() is true.
    */
   public BookshelfKeeper(Bookshelf sortedBookshelf) {
      shelf = sortedBookshelf;
   }

   /**
    * Removes a book from the specified position in the bookshelf and keeps bookshelf sorted 
    * after picking up the book.
    * 
    * Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    * 
    * PRE: 0 <= position < getNumBooks()
    */
   public int pickPos(int position) {
      int size = this.getNumBooks();
      int left = position;
      int right = size - position - 1;
      int count = 0;
      ArrayList<Integer> temp = new ArrayList<Integer>();
      if(left <= right){
         for(int i = 0; i <= position; i++){
            if(i == position){
               this.shelf.removeFront();
               break;
            }
            temp.add(this.shelf.removeFront());
         }
         for(int i = temp.size() - 1; i >= 0; i--){
            this.shelf.addFront(temp.get(i));
         }
         count = 2 * left + 1;
      }else{
         for(int i = size - 1; i >= position; i--){
            if(i == position){
               this.shelf.removeLast();
               break;
            }
            temp.add(this.shelf.removeLast());
         }
         for(int i = temp.size() - 1; i >= 0; i--){
            this.shelf.addLast(temp.get(i));
         }
         count = 2 * right + 1;
      }
      this.totalOperations += count;
      lastPickOrPutOperations = count;
      return count;
   }

   /**
    * Inserts book with specified height into the shelf.  Keeps the contained bookshelf sorted 
    * after the insertion.
    * 
    * Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    * 
    * PRE: height > 0
    */
   public int putHeight(int height){
      int loc = 0;
      int size = this.shelf.size();
      int count = 0;
      while((loc < size) && this.shelf.getHeight(loc) < height){
         loc++;
      }
      if(loc < size && height == this.shelf.getHeight(loc)){
         return 0;
      }
      int left = loc;
      int right = size - loc - 1;
      ArrayList<Integer> temp = new ArrayList<Integer>();
      if(left <= right){
         for(int i = 0; i < loc; i++){
            temp.add(this.shelf.removeFront());
         }
         this.shelf.addFront(height);
         for(int i = temp.size() - 1; i >= 0; i--){
            this.shelf.addFront(temp.get(i));
         }
         count = 2 * left + 1;
      }else{
         for(int i = size - 1; i >= loc; i--){
            temp.add(this.shelf.removeLast());
         }
         this.shelf.addLast(height);
         for(int i = temp.size() - 1; i >= 0; i--){
            this.shelf.addLast(temp.get(i));
         }
         count = 2 * right + 3;
      }
      this.totalOperations += count;
      lastPickOrPutOperations = count;
      return count;
   }

   /**
    * Returns the total number of calls made to mutators on the contained bookshelf
    * so far, i.e., all the ones done to perform all of the pick and put operations
    * that have been requested up to now.
    */
   public int getTotalOperations() {
      
       return totalOperations;
   }

   /**
    * Returns the number of books on the contained bookshelf.
    */
   public int getNumBooks() {
      
       return this.shelf.size();
   }

   /**
    * Returns string representation of this BookshelfKeeper. Returns a String containing height
    * of all books present in the bookshelf in the order they are on the bookshelf, followed 
    * by the number of bookshelf mutator calls made to perform the last pick or put operation, 
    * followed by the total number of such calls made since we created this BookshelfKeeper.
    * 
    * Example return string showing required format: "[1, 3, 5, 7, 33] 4 10"
    * 
    */
   public String toString() {
      
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      for(int i = 0; i < getNumBooks(); i++){
         sb.append(this.shelf.getHeight(i));
         if(i != getNumBooks() - 1){
            sb.append(", ");
         }
      }
      sb.append("] ");
      sb.append(lastPickOrPutOperations);
      sb.append(" ");
      sb.append(totalOperations);
      return sb.toString();
   }

   /**
    * Returns true iff the BookshelfKeeper data is in a valid state.
    * (See representation invariant comment for details.)
    */
   private boolean isValidBookshelfKeeper() {

      return false;  // dummy code to get stub to compile

   }

   // add any other private methods here


}
