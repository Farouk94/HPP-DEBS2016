package tse.fi2.hpp.labs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.*;

import tse.fi2.hpp.labs.objs.Comments;
import tse.fi2.hpp.labs.objs.Posts;

public class Main {

    public static void main(String args[]) throws IOException {

        //Get Start Time
        long start = System.currentTimeMillis();
        LinkedList<Comments> topcoms = new LinkedList<>();
        LinkedList<Posts> topposts = new LinkedList<>();

        //Check Program arguments
        if (args.length < 3) {
            System.out.println("Please enter right number of arguments:");
            System.out.println("\t -1 Posts path");
            System.out.println("\t -2 Comments path");
            System.out.println("\t -2 Temp Working path");
            System.exit(1);
        }
        //Posts Path
        String postlocation = args[0];
        //Comments path
        String comslocation1 = args[1];
        //Temp path
        String workingPath = args[2] + "//" + start;
        //Comments Parser used for comments loading
        ComsParser cc = new ComsParser();
        //Posts Parser used for posts loading
        PostsParser pp = new PostsParser();

        //Init Comments List
        CommentsList cl = new CommentsList();

        //Create a temp Directory (will be deleted at the end)
        File dirComms = new File(workingPath);
        dirComms.mkdir();

        //Fetch Comments list from file
        ComRank comr = new ComRank(topcoms);
        ArrayList<Comments> clist = comr.comRanker(comslocation1, cc, cl);

        //Get the last comment date from fetched list
        long now = 0;
        if (clist.size() > 0) {
            now = clist.get(clist.size() - 1).getDt();
        }

        //Find PostID for comments without and save on multiple 
        //files by 10000 comments range 
        //Every comment saved to file is removed from list
        int i = -1;
        int nbComments = clist.size();
        while (clist.size() > 0) {
            i++;
            if (i % 10000 == 0) {
                System.out.println("----------Traitement de la ligne Commentaire n°" + i);
                System.out.println(cl.commList.size() + "------" + cl.postsList.size());
            }

            cl.addComment(clist.get(0), workingPath, now);
            clist.remove(0);

            if (i % 10000 == 0) {
                System.out.println(cl.commList.size() + "------" + cl.postsList.size());
            }
        }

        //Print comments number
        System.out.println(cl.commList.size() + "-----------------------" + cl.postsList.size());

        //Save remaining comments to file 
        if (cl.theComments.size() > 0) {
            cl.registerCommentsArray(workingPath, nbComments / 10000);
        }

        //number of generated files
        int nbFiles = (nbComments / 10000) + 1;

        //Fetch Posts list from file
        PostRank postr = new PostRank(topposts);
        ArrayList<Posts> plist = postr.postRanker(postlocation, pp);
        ArrayList<Long> pidlist = new ArrayList<Long>();

        for (int cpt = 0; cpt < plist.size(); cpt++) {
            plist.get(cpt).computeScore(now, false);
            pidlist.add(plist.get(cpt).getPostId());

        }

        //Computing current posts score (based on last comment date)
        Comments comm;
        int cpt = nbFiles - 1;
        boolean loopComs = true;
        while (cpt >= 0 && loopComs) {
            cl.loadCommentsArray(workingPath, cpt, false);
            int j = cl.theComments.size() - 1;
            while (j >= 0 && loopComs) {
                comm = cl.theComments.get(j);
                if (comm.getScore() > 0) {
                    int pos = pidlist.indexOf(comm.getPostId());
                    if (pos >= 0) {
                        plist.get(pos).addCommentScore(comm.getScore());
                    }
                    j--;
                } else {
                    System.out.println(comm);
                    loopComs = false;
                }
            }
            cpt--;
        }

        //Removes all posts with score = 0
        for (int z = 0; z < plist.size(); z++) {
            if (plist.get(z).getScore() == 0) {
                plist.remove(z);
                pidlist.remove(z);
            }
        }
        System.out.println("NB Posts final = " + plist.size());

        //Computing incremental posts score
        //load comments from oldest to newest 
        //constructs in time score for every post older than current loaded comment
        //Removes every post having score = 0
        cpt = 0;
        while (cpt < nbFiles) {
            cl.loadCommentsArray(workingPath, cpt, true);
            int j = 0;
            while (j < cl.theComments.size()) {
                comm = cl.theComments.get(j);

                int pos = pidlist.indexOf(comm.getPostId());
                if (pos >= 0) {
                    now = comm.getDt();
                    plist.get(pos).addComment(comm, false, false);
                    for (int z = pos; z >= 0; z--) {
                        plist.get(z).computeScore(now, true);
                        if (plist.get(z).getScore() == 0) {
                            plist.remove(z);
                            pidlist.remove(z);
                        }
                    }
                }
                j++;
            }
            cpt++;
        }

        //Score Computed only if newest post is newer than newest comment
        if (plist.size() > 0 && now < plist.get(plist.size() - 1).getDt()) {
            now = plist.get(plist.size() - 1).getDt();
            for (int z = plist.size() - 1; z >= 0; z--) {
                plist.get(z).computeScore(now, true);
                if (plist.get(z).getScore() == 0) {
                    plist.remove(z);
                    pidlist.remove(z);
                }
            }
        }

        //Delete temp directory
        dirComms.delete();

        //Sort remaining posts
        List<Posts> thelist = plist.subList(0, plist.size());
        Collections.sort(thelist, new PostsComparator());

        //Print remaning posts
        for (int z = 0; z < plist.size(); z++) {
            System.out.println(z + " -----> " + plist.get(z));
        }

        //Print results
        System.out.println("=====================================RESULT=======================");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S+0000");
        Date theDate;
        for (int z = plist.size() - 1; z > plist.size() - 4; z--) {
            Posts post = plist.get(z);
            post.computeCommentsNumber();

            theDate = new Date(post.getDt());
            System.out.println("<" + dateFormat.format(theDate) + "," + post.getPostId() + "," + post.getUser() + "," + post.getScore() + "," + post.getCommentsNumber() + ">");
        }

        System.out.println("==================================================================");

        //Print Execution time in ms 
        long stop = System.currentTimeMillis();
        System.out.println("Delay : " + (stop - start) + " ms\n");
    }
}
