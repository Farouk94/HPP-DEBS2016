package tse.fi2.hpp.labs.objs;

import java.util.Date;

public class Likes {

	public Date dt;
	public int userId;
	public int commentId;
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public Likes(Date dt, int userId, int commentId) {
		super();
		this.dt = dt;
		this.userId = userId;
		this.commentId = commentId;
	}
	
}
//2010-02-26T03:02:38.590+0000|768|725662