package tse.fi2.hpp.labs;

import java.io.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import tse.fi2.hpp.labs.objs.Comments;
import tse.fi2.hpp.labs.objs.Posts;

/**
 *
 * @author Aladdin
 */
public class CommentsList implements Serializable {
    
    public ArrayList<ArrayList> commList;
    public ArrayList<ArrayList> postsList;
    public ArrayList<Comments> theComments;

    final long unity = 1000;

    public CommentsList() {
        //List of comments ID
        commList = new ArrayList<ArrayList>(0);
        //List of posts ID
        postsList = new ArrayList<ArrayList>(0);
        //List of comments
        theComments = new ArrayList<Comments>(0);

    }

    //Adding Comment to list
    public void addComment(Comments com, String path, long dtnow) {
        try {
            long position = 0;
            int idx = 0; // pour que l'array depasse pas maximum de l'array 1000
            if (com != null) {
                position = com.getPosition();
                idx = (int) (position / unity);
                if (com.getPostId() > 0) {
                    if (commList.size() < idx + 1) {
                        commList.add(new ArrayList<Long>(0));
                        postsList.add(new ArrayList<Long>(0));
                    }
                    postsList.get(idx).add(com.getPostId());
                    commList.get(idx).add(com.getCommentId());
                    com.computeScore(dtnow);
                    theComments.add(com);
                    if ((position + 1) % 10000 == 0 && position > 0) {
                        registerCommentsArray(path, position / 10000);
                    }
                } else if (com.getCommentRId() > 0) {
                    int pos = -1;
                    ArrayList temp = null;
                    int cpt = commList.size();
                    while (pos == -1 && cpt > 0) {
                        cpt--;
                        temp = commList.get(cpt);
                        pos = temp.indexOf(com.getCommentRId());
                    }
                    com.setPostId(new Long((long) postsList.get(cpt).get(pos)));
                    addComment(com, path, dtnow);
                }
            }
        } catch (Exception ex) {
            System.err.println(com + "" + ex);
            System.exit(0);
        }
    }
  /** this function stores a list of the comments in a temporary file
   * in the path we choose
   * @param path
   * @param idx 
   */
    public void registerCommentsArray(String path, long idx) {
        try {
            String fpath = path.concat("//comments.part" + Long.toString(idx));
            System.out.println("Saving Comments part: " + fpath);
            FileOutputStream fout = new FileOutputStream(fpath, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(theComments);
            oos.close();

            theComments = new ArrayList<Comments>(0);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
/** this function loads the file of the comments then deletes it after 
 *  loading them in a list of comments.
 * @param path
 * @param idx
 * @param deletef 
 */
    public void loadCommentsArray(String path, long idx, boolean deletef) {
        try {
            String fpath = path.concat("//comments.part" + Long.toString(idx));
            System.out.println("Loading Comments part: " + fpath);
            InputStream file = new FileInputStream(fpath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            theComments = (ArrayList<Comments>) input.readObject();

            input.close();
            buffer.close();
            file.close();
            if (deletef) {
                File f = new File(path.concat("//comments.part" + Long.toString(idx)));
                f.delete();
            }
        } catch (Exception ex) {
             System.err.println(ex);
        }
    }
}
