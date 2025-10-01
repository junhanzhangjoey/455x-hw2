// Name: Junhan Zhang
// USC NetID: 9250642107
// CSCI455 PA2
// Fall 2025


/**
 * Class Bookshelf
 * Implements idea of arranging books into a bookshelf.
 * Books on a bookshelf can only be accessed in a specific way so books don't fall down;
 * You can add or remove a book only when it's on one of the ends of the shelf.   
 * However, you can look at any book on a shelf by giving its location (starting at 0).
 * Books are identified only by their height; two books of the same height can be
 * thought of as two copies of the same book.
*/

import java.util.ArrayList;

public class Bookshelf {

   /**
      Representation invariant:
      1. The ArrayList instance variable pileOfBooks can not be null.
      2. All elements in pileOfBooks has to be greater than 0, representing book height
      

   */
   
   private ArrayList<Integer> pileOfBooks;


   /**
    * Creates an empty Bookshelf object i.e. with no books
    */
   public Bookshelf() {
      this.pileOfBooks = new ArrayList<Integer>();
      assert isValidBookshelf();  // sample assert statement (you will be adding more of these calls)
   }

   /**
    * Creates a Bookshelf with the arrangement specified in pileOfBooks. Example
    * values: [20, 1, 9].
    * 
    * PRE: pileOfBooks contains an array list of 0 or more positive numbers
    * representing the height of each book.
    */
   public Bookshelf(ArrayList<Integer> pileOfBooks) {
      this.pileOfBooks = new ArrayList<Integer>(pileOfBooks);
      assert isValidBookshelf();
   }

   /**
    * Inserts book with specified height at the start of the Bookshelf, i.e., it
    * will end up at position 0.
    * 
    * PRE: height > 0 (height of book is always positive)
    */
   public void addFront(int height) {
      assert isValidBookshelf();
      this.pileOfBooks.add(0,height);
      assert isValidBookshelf();
   }

   /**
    * Inserts book with specified height at the end of the Bookshelf.
    * PRE: height > 0 (height of book is always positive)
    */
   public void addLast(int height) {
      assert isValidBookshelf();
      this.pileOfBooks.add(height);
      assert isValidBookshelf();
   }

   /**
    * Removes book at the start of the Bookshelf and returns the height of the
    * removed book.
    * @return the height of the book that is removed
    * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
    */
   public int removeFront() {
      assert isValidBookshelf();
      return this.pileOfBooks.remove(0);
   }

   /**
    * Removes book at the end of the Bookshelf and returns the height of the
    * removed book.
    * @return the height of the book that is removed
    * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
    */
   public int removeLast() {
      assert isValidBookshelf();
      return this.pileOfBooks.remove(this.pileOfBooks.size() - 1);    
   }

   /**
    * Gets the height of the book at the given position.
    * @param position index of the book that you want to get height
    * PRE: 0 <= position < this.size()
    */
   public int getHeight(int position) {
      assert isValidBookshelf();
      return this.pileOfBooks.get(position);
   }

   /**
    * @return Returns number of books on the this Bookshelf.
    */
   public int size() {
      assert isValidBookshelf();
      return this.pileOfBooks.size();
   }

   /**
    * @return Returns string representation of this Bookshelf. Returns a string with the height of all
    * books on the bookshelf, in the order they are in on the bookshelf, using the format shown
    * by example here:  "[7, 33, 5, 4, 3]"
    */
   public String toString() {
      assert isValidBookshelf();
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      for(int i = 0; i < size(); i++){
         sb.append(this.pileOfBooks.get(i));
         if(i == size() - 1){
            break;
         }
         sb.append(", ");
      }
      sb.append("]");
      return sb.toString();

   }

   /**
    * @return Returns true iff the books on this Bookshelf are in non-decreasing order.
    * (Note: this is an accessor; it does not change the bookshelf.)
    */
   public boolean isSorted() {
      assert isValidBookshelf();
      for(int i = 1; i < size(); i++){
         if(this.pileOfBooks.get(i) < this.pileOfBooks.get(i - 1)){
            return false;
         }
      }
      return true;
   }

   /**
    * @return Returns true iff the Bookshelf data is in a valid state.
    * (See representation invariant comment for more details.)
    */
   private boolean isValidBookshelf() {
      
      if(this.pileOfBooks == null){
         return false;
      }
      for(int height : this.pileOfBooks){
         if(height <= 0){
            return false;
         }
      }
      return true;
   }

}
