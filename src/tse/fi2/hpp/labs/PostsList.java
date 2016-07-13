/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tse.fi2.hpp.labs;

import java.util.ArrayList;
import tse.fi2.hpp.labs.objs.Comments;
import tse.fi2.hpp.labs.objs.Posts;

/**
 *
 * @author Aladdin
 */
public class PostsList {

    ArrayList<String> pIDlist;
    ArrayList<Posts> plist;


    public long now = 0;

    public PostsList() {
        pIDlist = new ArrayList<String>(0);
        plist = new ArrayList<Posts>(0);
    }

    public void addComment(Comments com) {
        
    }
    public long getNowValue() {
        return now;
    }

    public void setNowValue(long pNow) {
        now = pNow;
    }
    /** setting our top3 from our posts list
      * 
      * 
     * @return 
    */
    public ArrayList<Posts> getTop3() {

        ArrayList<Posts> resPost = new ArrayList<Posts>(0);
        resPost.add(plist.get(0));

        if (resPost.get(0).compareTo(plist.get(1)) < 0) {
            resPost.add(0, plist.get(1));
        } else {
            resPost.add(plist.get(1));
        }

        if (resPost.get(0).compareTo(plist.get(2)) < 0) {
            resPost.add(0, plist.get(2));
        } else if (resPost.get(1).compareTo(plist.get(1)) < 0) {
            resPost.add(1, plist.get(2));
        } else {
            resPost.add(plist.get(2));
        }
        for (int i = 3; i < plist.size(); i++) {
            Posts post = plist.get(i);
            if (resPost.get(0).compareTo(post) < 0) {
                resPost.remove(2);
                resPost.add(0, post);
            } else if (resPost.get(1).compareTo(post) < 0) {
                resPost.remove(2);
                resPost.add(1, post);
            } else if (resPost.get(2).compareTo(post) < 0) {
                resPost.remove(2);
                resPost.add(post);
            }
        }
        return resPost;
    }
}
