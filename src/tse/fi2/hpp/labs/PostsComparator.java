package tse.fi2.hpp.labs;
import java.util.Comparator;
import tse.fi2.hpp.labs.objs.Posts;
/** this function compares 2 posts by score , date of the post, and the date 
 * the latest comment in that post.
 * 
 */
public class PostsComparator implements Comparator<Posts>{
		 @Override
		    public int compare(Posts p1, Posts p2) 
                    {
		        if(p1.getScore() > p2.getScore())
                            return 1;
                        else if(p1.getScore() < p2.getScore())
                            return -1;
                        else if(p1.getDt() > p2.getDt())
                            return 1;
                        else if(p1.getDt() < p2.getDt())
                            return -1;
                        else
                        {
                            if(p1.getComments().size() > 0 && p2.getComments().size() > 0)
                            {
                                if(p1.getComments().get(p1.getComments().size() - 1).getDt() > p2.getComments().get(p2.getComments().size() - 1).getDt())
                                    return 1;
                                else if(p1.getComments().get(p1.getComments().size() - 1).getDt() < p2.getComments().get(p2.getComments().size() - 1).getDt())
                                    return -1;
                                else 
                                    return 0;
                            }
                            else if(p1.getComments().size() > 0) 
                                    return 1;
                            else
                                return -1;
                        }
		    }
		 
		 
		

}
