package tse.fi2.hpp.labs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;


import tse.fi2.hpp.labs.objs.Comments;

public class ComRank {

    LinkedList<Comments> comList = new LinkedList<Comments>();
    private long now = 0;

    public LinkedList<Comments> getComL() {
        return this.comList;
    }

    public void setComL(LinkedList<Comments> comL) {
        this.comList = comL;
    }

    public void addCom(Comments com) {
        this.comList.add(com);
    }

    public void delCom(int i) {
        this.comList.remove(i);
    }

    public int sizeL() {
        return this.comList.size();
    }

    public Comments getElem(int i) {
        return this.comList.get(i);
    }

    public void listReset(LinkedList<Comments> com) {
        for (int i = 0; i <= com.size() - 1; ++i) {
            if (com.get(i).getScore() != 0 && com.get(i).getScore() >= 0) {
                continue; // leaves the loop
            }
            for (int j = 0; j <= com.size() - 1; ++j) {
                com.remove(i);
            }
        }
    }

    public ArrayList comRanker(String comslocation, ComsParser cc, CommentsList cl) {       
        ArrayList<Comments> cl_temp = new ArrayList<Comments>(0);       
        try {    
            Comments com = null;            
            FileInputStream fis = new FileInputStream(comslocation);
            InputStreamReader instrm = new InputStreamReader(fis);          
            BufferedReader br = new BufferedReader(instrm);           
            String line;          
            int cpt = 0;
            // Read one line at a time 
            while((line = br.readLine()) != null)
            {    
                //if(cpt%20000 == 0)
                    //System.out.println("Chargement de la ligne comment n°" + (cpt + 1));
                if(line.trim().length() > 0)
                {
                    com = cc.loaderComments(line);
                    if(com != null)
                    {
                        com.setPosition(cl_temp.size());
                        cl_temp.add(com);
                    }
                } 
                //cpt ++;
                
            }
            
             fis.close();
            br.close();
            instrm.close();
        } catch (Exception e) {
            System.out.println("comRanker() error "+e);
        }
        
        return cl_temp;
    }
    @Override
    public String toString() {
        return "ComRank [comL=" + comList + "]";
    }
    
    public long getNowValue()
    {
        return now;
    }
    public void setNowValue(long pNow)
    {
        now = pNow;
    }

    public ComRank(LinkedList<Comments> comL) {
        super();
        this.comList = comL;
    }

}
