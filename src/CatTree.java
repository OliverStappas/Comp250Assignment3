import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }

    public void addCat(CatInfo c)
    {

        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {
            //The idea is that for every node inside the whole CatTree, you can regard this node and all its descendants as a subtree. Therefore the node you call the function on (i.e. this) can be regarded as the "root".
            //When you recursively call it on other nodes, in the recursive call those other nodes will be regarded as the "new root" in the smaller subtree

            // ADD YOUR CODE HERE

            if (c.data.monthHired == this.data.monthHired) {

                c.same = this.same;
                this.same = c;

                if (c.data.furThickness > this.data.furThickness) {
                    //If the cat to be added has thicker fur than the cat at the root, it
                    //should be stored in the root node, and the cat previously in the root node should be stored
                    //in the subtree with root c.same.

                    CatInfo temp = this.data;
                    this.data = c.data;
                    c.data = temp;

                }

            }
            else if (c.data.monthHired < this.data.monthHired) {
                if (this.senior == null) {
                    this.senior = new CatNode(c.data);
                }
                else {
                    this.senior = this.senior.addCat(c);
                }

            }

            else if (c.data.monthHired > this.data.monthHired) {
                if (this.junior == null) {
                    this.junior = new CatNode(c.data);
                }
                else {
                    this.junior = this.junior.addCat(c);
                }

            }

            return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public CatNode removeCat(CatInfo c) {
            // ADD YOUR CODE HERE

//
//            if (this.data.equals(c)) {
//                return null;
//            }
//            else if (c.monthHired < this.data.monthHired) {
//
//                if(this.senior == null){
//                    ????
//                }
//                else{
//                    this.senior.removeCat(c);
//                }
//            }
//            else if (c.monthHired > this.data.monthHired) {
//
//                if(this.junior == null){
//                    ????
//                }
//                else{
//                    this.junior.removeCat(c);
//                }
//            }
//
//            else if (this.left == null) {
//                this = this.right;
//            }
//
//            else if (this.right == null) {
//                this = this.left;
//            }
//
//            else{
//                this.key = findMin(this.right).key;
//                this.right = removeCat(this.right, this.key);
//            }

            return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public int mostSenior() {
            // ADD YOUR CODE HERE
            return -1; //CHANGE THIS
        }
        
        public int fluffiest() {
            // ADD YOUR CODE HERE
            return -1; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public int hiredFromMonths(int monthMin, int monthMax) {
            // ADD YOUR CODE HERE
            return -1; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
            
        }
        
        public CatInfo fluffiestFromMonth(int month) {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        public int[] costPlanning(int nbMonths) {
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
    }
    
    private class CatTreeIterator implements Iterator<CatInfo> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED
        Iterator<CatInfo> catInfoIterator;

        public CatTreeIterator() {
            //YOUR CODE GOES HERE
            ArrayList<CatInfo> sortedCats = new ArrayList<>();
            //TODO  inorder placing objects
            this.catInfoIterator = sortedCats.iterator();
        }
        
        public CatInfo next(){
            //YOUR CODE GOES HERE

            return this.catInfoIterator.next();
        }

        
        public boolean hasNext() {
            //YOUR CODE GOES HERE

            return this.catInfoIterator.hasNext();
        }
    }
    
}

