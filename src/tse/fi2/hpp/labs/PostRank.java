package tse.fi2.hpp.labs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import tse.fi2.hpp.labs.objs.Posts;

public class PostRank {

    @Override
    public String toString() {
        return "PostRank [postL=" + postL + "]";
    }

    LinkedList<Posts> postL = new LinkedList<>();
    private long now = 0;

    public void addPost(Posts post) {
        this.postL.add(post);
    }

    public int sizeL() {
        return this.postL.size();
    }

    public Posts getElem(int i) {
        return this.postL.get(i);
    }

    
    /** this function reads 20 000lines from our post file and returns 
     *  a list of the posts
     * @param postlocation
     * @param pp
     * @return 
     */
    public ArrayList<Posts> postRanker(String postlocation, PostsParser pp) {
        BufferedReader pr;
        ArrayList<Posts> res = new ArrayList<Posts>(0);
        try {

            File postf = new File(postlocation);
            pr = new BufferedReader(new FileReader(postf));
            Posts post = null;
            int cpt = 0;

            for (String line = pr.readLine(); line != null; line = pr.readLine()) {
                if (cpt % 20000 == 0) {
                }
                if (line.trim().length() > 0) { //trim to return string without spaces from the sides
                    post = pp.loaderPosts(line);
                    if (post != null) {
                        res.add(post);
                    }
                }
                cpt++;
            }
            pr.close();
        } catch (Exception post) {
            System.out.println("postRanker() error " + post);

        }
        return res;
    }

    public long getNowValue() {
        return now;
    }

    public void setNowValue(long pNow) {
        now = pNow;
    }

    public PostRank(LinkedList<Posts> post) {
        super();
        this.postL = post;
    }

}
