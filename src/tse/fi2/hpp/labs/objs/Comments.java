package tse.fi2.hpp.labs.objs;

import java.io.Serializable;

 

public class Comments implements Serializable, Comparable <Comments> {

	final long dt;
        final Long commentId;
	final long userId;
	final String comment;
	final String user;
	public Long commentRId;
	public Long postId;
        // position evite de reparcourir notre lists et on accede directement 
        // a l'emplacement du commentaire grace a sa position
        public long position = -1;
        
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public long score = 0;
	

	

        public void setPosition(long pos)
        {
            if(pos >= 0)
                position = pos;
        }
        
        public long getPosition()
        {
           return position;
        }
	
	public Long getCommentRId() {
		return commentRId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setScore(long score) {
		this.score = score;
	}
        /*
        *
        */
        public void computeScore(long pnow)
        {
            int diffTime = (int)((pnow - this.getDt()) / 86400000);                        
            this.score = Math.max(0, 10 - diffTime);
        }
        
	public void setcommentRId(Long commentRId) {
		this.commentRId =commentRId;
	}

	public Comments(long pdate, long pCommentId, long pUserId, String pComment,
			String pUser, long pCommentRId, long pPostId, long pscore) {
		super();
		this.dt = pdate;
		this.commentId = pCommentId;
		this.userId = pUserId;
		this.comment = pComment;
		this.user = pUser;
                if(pCommentRId > 0 )
                    this.commentRId = pCommentRId;
                else
                    this.commentRId = new Long(-1);
                if(pPostId > 0)
                    this.postId = pPostId;
                else
                    this.postId = new Long(-1);
		this.score = pscore;
	}

	public long getDt() {
		return dt;
	}

	public Long getCommentId() {
		return commentId;
	}

	public long getUserId() {
		return userId;
	}

	public String getComment() {
		return comment;
	}

	public String getUser() {
		return user;
	}

	

	public long getScore() {
		return score;
	}

	

	@Override
	public int compareTo(Comments arg0) {
		if(this.score<arg0.score)
		{
			return -1;
		}
		else
			return 1;
		
	}
	
	@Override
	public String toString() {
		return "Comments [dt=" + dt + ", commentId=" + commentId + ", userId="
				+ userId + ", comment=" + comment + ", user=" + user
				+ ", commentRId=" + commentRId + ", postId=" + postId
				+ ", score=" + score + "]";
	}
	
	
	
	
	

	
}


