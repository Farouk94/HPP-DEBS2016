package tse.fi2.hpp.labs.objs;

import java.util.ArrayList;

public class Posts {
	public long dt;
	public long postId;
	public long userId;
	public String post;
	public String user;
	public long score = 0;
        public int comNumber = 0;
        
        public ArrayList<Comments> comList = new ArrayList<Comments>(0);
	
	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long l) {
		this.score = l;
	}

        /*
        *
        */
        
        public ArrayList<Comments> getComments()
        {
            return comList;
        }
        
        /*
        *
        */
        public void addComment(Comments com, boolean rescore, boolean rescoreComms)
        {
            if(com != null)
            {
                
                comList.add(com);
                if(com.getUserId() != this.getUserId())
                    comNumber++;
                if(rescore)
                    this.computeScore(com.getDt(), rescoreComms);
            }
        }
        /*
        *
        */
        public void addCommentScore(long pscore)
        {
            if(pscore > 0)
                score+= pscore;
        }
          /* decremente le score d'apres les jours passées. et calcule
        * le score de commentaire d'un commentaire.
        */
        public void computeScore(long pnow, boolean computeComments)
        {
            int diffTime = (int)((pnow - this.getDt()) / 86400000);                        
            score = Math.max(0, 10 - diffTime);
            if(computeComments) // if we have a commentRplyID 
            {
                for(int i=0; i < comList.size(); i++)
                {
                    comList.get(i).computeScore(pnow);
                    score += comList.get(i).getScore();
                }
            }
        }
          /* calculer le nombre de commentaires d'un meme userID  a
        * partir d'un commentaire qu'il a mis.
        */
        public void computeCommentsNumber()
        {
            comNumber = 0;
            for(int i =0; i < this.comList.size(); i++)
            {
                if(this.comList.get(i).getUserId() != this.getUserId())
                    comNumber++;
            }
        }
        
        public int getCommentsNumber()
        {
            return comNumber;
        }
	public Posts(long dt, long postId, long userId, String post, String user,
			long score) {
		super();
		this.dt = dt;
		this.postId = postId;
		this.userId = userId;
		this.post = post;
		this.user = user;
		this.score = score;
	}
        public Posts()
        {}

	@Override
	public String toString() {
		return "Posts [dt=" + dt + ", postId=" + postId + ", userId=" + userId
				+ ", post=" + post + ", user=" + user + ", score=" + score
				+ "]";
	}
        
        /* compareTo compare 2 posts by score, date, and the date of 
        *  the latest comment
        */
         public int compareTo(Posts other){
             
             if(this.score > other.getScore())
                 return 1;
             else if(this.score < other.getScore())
                 return -1;
             else if(this.getDt() > other.getDt())
                 return 1;
             else if(this.getDt() < other.getDt())
                 return -1;
             else
             {
                 if(this.comList.size() > 0 && other.getComments().size() > 0)
                 {
                     if(comList.get(0).getDt() > other.getComments().get(0).getDt())
                         return 1;
                     else if(comList.get(0).getDt() < other.getComments().get(0).getDt())
                         return -1;
                     else 
                         return 0;
                 }
                 else if(this.comList.size() > 0) 
                         return 1;
                 else
                     return -1;
             }
        }

    
	
	
}
