package tse.fi2.hpp.labs;

import java.util.Comparator;

import tse.fi2.hpp.labs.objs.Comments;

public class CommsComparator implements Comparator<Comments> {

    /** this function compares 2 comments by their respective score
     * 
     * @param e1
     * @param e2
     * @return 
     */
    @Override
    public int compare(Comments e1, Comments e2) {
        if (e1.getScore() < e2.getScore()) {
            return 1;
        } else {
            return -1;
        }
    }
}
