// Name: Junhan Zhang
// USC NetID: 9250642107
// CSCI455 PA2
// Fall 2025

import java.util.ArrayList;

/**
 * Class BookshelfKeeper 
 *
 * Enables users to perform efficient putPos or pickHeight operation on a bookshelf of books kept in 
 * non-decreasing order by height, with the restriction that single books can only be added 
 * or removed from one of the two *ends* of the bookshelf to complete a higher level pick or put 
 * operation.  Pick or put operations are performed with minimum number of such adds or removes.
 */

public class BookshelfKeeper {
   /**
      Representation invariant:
      1. shelf can not be null
      2. totalOperations has to be greater than or equal to 0;
      3. lastPickOrPutOperations has to be greater than 0;
      4. totalOperations has to be greater than or equal to lastPickOrPutOperations
      5. The contained bookshelf ('shelf') must always be in non-decreasing order by height.
   */
   
   // <add instance variables here>
   private Bookshelf shelf;
   private int totalOperations;
   private int lastPickOrPutOperations;

   private static final int MUTATORS_PER_BOOK_MOVED = 2; 
   private static final int TARGET_BOOK_OPERATION = 1;
   /**
    * Creates a BookShelfKeeper object with an empty bookshelf
    */
   public BookshelfKeeper() {
      shelf = new Bookshelf();
      totalOperations = 0;
      lastPickOrPutOperations = 0;
      isValidBookshelfKeeper();
   }

   /**
    * Creates a BookshelfKeeper object initialized with the given sorted bookshelf.
    * Note: method does not make a defensive copy of the bookshelf.
    * @param sortedBookshelf
    * PRE: sortedBookshelf.isSorted() is true.
    */
   public BookshelfKeeper(Bookshelf sortedBookshelf) {
      shelf = sortedBookshelf;
      isValidBookshelfKeeper();
   }

   /**
    * Removes a book from the specified position in the bookshelf and keeps bookshelf sorted 
    * after picking up the book.
    * @param position the position of the book to be removed
    * @return Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    * 
    * PRE: 0 <= position < getNumBooks()
    */
   public int pickPos(int position) {
      assert isValidBookshelfKeeper();
      int size = this.getNumBooks();
      int numToMoveFromFront = position;
      int numToMoveFromBack = size - position - 1;
      int count = 0;
      ArrayList<Integer> temp = new ArrayList<Integer>();
      if(numToMoveFromFront <= numToMoveFromBack){
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
         count = MUTATORS_PER_BOOK_MOVED * numToMoveFromFront + TARGET_BOOK_OPERATION;
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
         count = MUTATORS_PER_BOOK_MOVED * numToMoveFromBack + TARGET_BOOK_OPERATION;
      }
      this.totalOperations += count;
      lastPickOrPutOperations = count;
      assert isValidBookshelfKeeper();
      return count;
   }

   /**
    * Inserts book with specified height into the shelf.  Keeps the contained bookshelf sorted 
    * after the insertion.
    * @param height  
    * @return Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    * 
    * PRE: height > 0
    */
   public int putHeight(int height){
      assert isValidBookshelfKeeper();
      int size = this.shelf.size();
      int [] bothEnd = getEndIndices(height);
      int loc = bothEnd[0];
      int loc2 = bothEnd[1];
      int numToMoveFromFront = loc; 
      int numToMoveFromBack = size - loc2;
      int count;// the number of the mutator called.
      ArrayList<Integer> temp = new ArrayList<Integer>();
      if(numToMoveFromFront <= numToMoveFromBack){
         for(int i = 0; i < loc; i++){
            temp.add(this.shelf.removeFront());
         }
         this.shelf.addFront(height);
         for(int i = temp.size() - 1; i >= 0; i--){
            this.shelf.addFront(temp.get(i));
         }
         count = MUTATORS_PER_BOOK_MOVED * numToMoveFromFront + TARGET_BOOK_OPERATION;
      }else{
         loc = loc2;
         for(int i = size - 1; i >= loc; i--){
            temp.add(this.shelf.removeLast());
         }
         this.shelf.addLast(height);
         for(int i = temp.size() - 1; i >= 0; i--){
            this.shelf.addLast(temp.get(i));
         }
         count = MUTATORS_PER_BOOK_MOVED * numToMoveFromBack + TARGET_BOOK_OPERATION;
      }
      this.totalOperations += count;
      lastPickOrPutOperations = count;
      assert isValidBookshelfKeeper();
      return count;
   }

   /**
    * Returns the total number of calls made to mutators on the contained bookshelf
    * so far, i.e., all the ones done to perform all of the pick and put operations
    * that have been requested up to now.
    */
   public int getTotalOperations() {
      assert isValidBookshelfKeeper();
      return totalOperations;
   }

   /**
    * Returns the number of books on the contained bookshelf.
    */
   public int getNumBooks() {
      assert isValidBookshelfKeeper();
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
      assert isValidBookshelfKeeper();
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
    * @return Returns true iff the BookshelfKeeper data is in a valid state.
    * (See representation invariant comment for details.)
    */
   private boolean isValidBookshelfKeeper() {
      if(this.shelf == null || this.totalOperations < 0 || this.lastPickOrPutOperations < 0 || this.lastPickOrPutOperations > this.totalOperations){
         return false;
      }
      if(!this.shelf.isSorted()) return false;
      for(int i = 0; i < this.shelf.size(); i++){
         if(this.shelf.getHeight(i) <= 0){
            return false;
         }
      }
      return true;
   }

   // add any other private methods here

   /**
    * In the putHeight method, this helper is used to find the indices of the first and last book 
    * with the given height. If there is no duplicate book, both indices are the same.
    *
    * @param height the height of the book to search for
    * @return an array of size 2, where the first element is the leftmost index 
    *         and the second element is the rightmost index of the book with the given height
    */

   private int[] getEndIndices(int height){
      int [] res = new int [2];
      int loc = 0;
      int size = this.shelf.size();
      while((loc < size) && this.shelf.getHeight(loc) < height){
         loc++;
      }
      int loc2 = loc;
      if(loc < size && height == this.shelf.getHeight(loc)){
         while(loc2 < size && this.shelf.getHeight(loc2) == height){
            loc2++;
         }
      }
      res[0] = loc;
      res[1] = loc2;
      return res;
   }
}