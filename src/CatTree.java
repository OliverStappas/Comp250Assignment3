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

                if (c.data.furThickness < this.data.furThickness) {
                    if (this.same == null) {

                        this.same = c;
                    }

                    else {

                        this.same = this.same.addCat(c);
                    }

                }

                else if (c.data.furThickness > this.data.furThickness) {
                    //If the cat to be added has thicker fur than the cat at the root, ity6
                    //should be stored in the root node, and the cat previously in the root node should be stored
                    //in the subtree with root c.same.

                    c.same = this.same;
                    this.same = c;

                    CatInfo temp = this.data;
                    this.data = c.data;
                    c.data = temp;

                }

            } else if (c.data.monthHired < this.data.monthHired) {
                if (this.senior == null) {
                    this.senior = new CatNode(c.data);
                } else {
                    this.senior = this.senior.addCat(c);
                }

            } else if (c.data.monthHired > this.data.monthHired) {
                if (this.junior == null) {
                    this.junior = new CatNode(c.data);
                } else {
                    this.junior = this.junior.addCat(c);
                }

            }

            return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }


        public CatNode removeCat(CatInfo c) {
            // ADD YOUR CODE HERE

            // if c.senior's junior is null, you can directly add the cats, otherwise find an available position recursively.
            //When we remove a CatNode c, do I need to set c.senior, c.junior and c.same to null?
            //You should. Otherwise when you access the CatNode c, it still has all the children nodes attached to it, and you may traverse a tree that doesn't exist anymore.

            if (this.senior == null && this.junior == null && this.same == null) {
                return  null;
            }

            CatNode output = this;

            if (this.data.equals(c)) {
                // there are three cases to consider.

                //First, if c.same != null, then the CatInfo object in
                //c.same moves to the root, and the subtrees of c should be adjusted in relation to the new root.
                if (this.same != null) {
                    this.data = this.same.data;
                    CatNode tempSame = this.same.same;
                    this.same.senior = null;
                    this.same.junior = null;
                    this.same.same = null;
                    this.same = tempSame;

                }

                //Second, if c.same == null and c.senior != null, then the new root becomes c.senior and
                //you need to add the CatInfos in c.junior to this new root.
                else if (this.same == null && this.senior != null) {
                    CatNode rootJunior = this.junior;
                    this.data = this.senior.data;
                    this.same = this.senior.same;
                    CatNode tempJunior = this.senior.junior;
                    CatNode tempSenior = this.senior.senior;
                    this.senior.junior = null;
                    this.senior.senior = null;
                    this.senior = tempSenior;
                    this.junior = tempJunior;
                    CatNode current = this.junior;

                    if (current != null) {
                        while (current.junior != null) {
                            current = current.junior;
                        }
                        current.junior = rootJunior; }

                    else {
                        this.junior = rootJunior;  }
                }

                //Finally, if c.same == null and
                //c.senior == null, then the new root is c.junior.

                else if (this.same == null && this.senior == null && this.junior != null) {
                    CatNode rootSenior = this.senior;
                    this.data = this.junior.data;
                    this.same = this.junior.same;
                    CatNode tempJunior = this.junior.junior;
                    CatNode tempSenior = this.junior.senior;
                    this.junior.junior = null;
                    this.junior.senior = null;
                    this.senior = tempSenior;
                    this.junior = tempJunior;
                    this.same = null;

                    CatNode current = this.senior;
                    if (current != null) {
                        while (current.senior != null) {
                            current = current.senior;
                        }
                        current.senior = rootSenior;
                    }

                    else  {
                        this.senior = rootSenior;
                    }
                }


            } else if (c.monthHired < this.data.monthHired && this.senior != null) {
                this.senior = this.senior.removeCat(c);

            } else if (c.monthHired > this.data.monthHired && this.junior != null) {
                this.junior = this.junior.removeCat(c);
            }


            return output; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }




        public int mostSenior() {
            // ADD YOUR CODE HERE

            CatNode current;

            current = this;

            while (current.senior != null) {
                current = current.senior;
            }

            return current.data.monthHired;

        }

        public int fluffiest() {
            // ADD YOUR CODE HERE

            int maxFluffiness = this.data.furThickness;
            int leftMax = 0;
            int rightMax = 0;


            if (this.senior != null) {
                leftMax = this.senior.fluffiest();

            }

            if (this.junior != null) {
                rightMax = this.junior.fluffiest();
            }


            if (leftMax >= rightMax && leftMax >= maxFluffiness) {
                return leftMax;
            } else if (rightMax >= leftMax && rightMax >= maxFluffiness) {
                return rightMax;
            } else {
                return maxFluffiness;
            }

        }


        public int hiredFromMonths(int monthMin, int monthMax) {
            // ADD YOUR CODE HERE

            int numHired = 0;

            if (monthMin > monthMax) {
                return 0;
            }

            if (monthMin < this.data.monthHired & this.senior != null) {
                numHired += this.senior.hiredFromMonths(monthMin, monthMax);
            }

            if (monthMax >= this.data.monthHired  &&  monthMin <= this.data.monthHired) {

                if (this.same != null) {
                    CatNode current = this;
                    numHired += 1;
                    while (current.same != null) {
                        current = current.same;
                        numHired += 1;
                    }
                }

                else {
                    numHired += 1;
                }
            }

            if (monthMax > this.data.monthHired && this.junior != null) {
                numHired += this.junior.hiredFromMonths(monthMin, monthMax);
            }

            return numHired;

        }


        public CatInfo fluffiestFromMonth(int month) {
            // ADD YOUR CODE HERE

            CatInfo fluffiest = null;

            if (month < this.data.monthHired) {
                fluffiest = this.senior.fluffiestFromMonth(month);
            }

            if (month == this.data.monthHired) {
                fluffiest = this.data;
            }

            if (month > this.data.monthHired) {
                fluffiest = this.junior.fluffiestFromMonth(month);
            }

            return fluffiest;

        }


        public int[] costPlanning(int nbMonths) {
            // ADD YOUR CODE HERE

            int[] costPlan = new int[nbMonths];

            CatTree tree = new CatTree(this);

            for (int i = 0; i < nbMonths; i++) {
                int totalForCurrentMonth = 0;
                for (CatInfo catInfo : tree) {
                    if (catInfo.nextGroomingAppointment - 243 == i) {
                        totalForCurrentMonth += catInfo.expectedGroomingCost;
                    }
                }
                costPlan[i] = totalForCurrentMonth;
            }

            return costPlan; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }


    }
        

    
    private class CatTreeIterator implements Iterator<CatInfo> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED
        Iterator<CatInfo> catInfoIterator;

        public CatTreeIterator() {
            //YOUR CODE GOES HERE
            ArrayList<CatInfo> sortedCats = new ArrayList<>();

            populateSortedCatArrayList(root, sortedCats);

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

        private void populateSortedCatArrayList(CatNode node, ArrayList<CatInfo> sortedCats) {

            if (node == null)
                return;

            /* left */
            populateSortedCatArrayList(node.senior, sortedCats);

            /* visit */

            CatNode current;
            ArrayList<CatInfo> listOfSames = new ArrayList<>();
            listOfSames.add(node.data);

            // If the node we're visiting has the same seniority as other nodes
            if (node.same != null) {
                current = node.same;
                while (current != null) {
                    listOfSames.add(current.data);
                    current = current.same;
                }

                for (int i = listOfSames.size() - 1; i >= 0; i-- ) {
                    sortedCats.add(listOfSames.get(i));
                }
            }

            else {
                sortedCats.add(node.data);

            }

            /* right*/

            populateSortedCatArrayList(node.junior, sortedCats);

        }
    }
    
}

