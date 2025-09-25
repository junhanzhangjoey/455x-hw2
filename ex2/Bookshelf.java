// Name: 
// USC NetID: 
// CSCI455 PA2
// Spring 2025


/**
 * Class Bookshelf
 * Implements idea of arranging books into a bookshelf.
 * Books on a bookshelf can only be accessed in a specific way so books don’t fall down;
 * You can add or remove a book only when it’s on one of the ends of the shelf.   
 * However, you can look at any book on a shelf by giving its location (starting at 0).
 * Books are identified only by their height; two books of the same height can be
 * thought of as two copies of the same book.
*/

import java.util.ArrayList;

public class Bookshelf {
   private ArrayList<Integer> pileOfBooks;

   /**
      Representation invariant:

      <put rep. invar. comment here>

   */
   
   // <add instance variables here>


   /**
    * Creates an empty Bookshelf object i.e. with no books
    */
   public Bookshelf() {
      
      assert isValidBookshelf();  // sample assert statement (you will be adding more of these calls)
      this.pileOfBooks = new ArrayList<Integer>();
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
   }

   /**
    * Inserts book with specified height at the start of the Bookshelf, i.e., it
    * will end up at position 0.
    * 
    * PRE: height > 0 (height of book is always positive)
    */
   public void addFront(int height) {
      this.pileOfBooks.add(0,height);
   }

   /**
    * Inserts book with specified height at the end of the Bookshelf.
    * 
    * PRE: height > 0 (height of book is always positive)
    */
   public void addLast(int height) {
      this.pileOfBooks.add(height);
   }

   /**
    * Removes book at the start of the Bookshelf and returns the height of the
    * removed book.
    * 
    * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
    */
   public int removeFront() {
      return this.pileOfBooks.remove(0);
   }

   /**
    * Removes book at the end of the Bookshelf and returns the height of the
    * removed book.
    * 
    * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
    */
   public int removeLast() {
      return this.pileOfBooks.remove(this.pileOfBooks.size() - 1);    
   }

   /*
    * Gets the height of the book at the given position.
    * 
    * PRE: 0 <= position < this.size()
    */
   public int getHeight(int position) {
      
      return this.pileOfBooks.get(position);
      
   }

   /**
    * Returns number of books on the this Bookshelf.
    */
   public int size() {
      
      return this.pileOfBooks.size();

   }

   /**
    * Returns string representation of this Bookshelf. Returns a string with the height of all
    * books on the bookshelf, in the order they are in on the bookshelf, using the format shown
    * by example here:  “[7, 33, 5, 4, 3]”
    */
   public String toString() {
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
    * Returns true iff the books on this Bookshelf are in non-decreasing order.
    * (Note: this is an accessor; it does not change the bookshelf.)
    */
   public boolean isSorted() {
      for(int i = 1; i < size(); i++){
         if(this.pileOfBooks.get(i) < this.pileOfBooks.get(i - 1)){
            return false;
         }
      }
      return true;
   }

   /**
    * Returns true iff the Bookshelf data is in a valid state.
    * (See representation invariant comment for more details.)
    */
   private boolean isValidBookshelf() {
      
      return false;  // dummy code to get stub to compile

   }

}
