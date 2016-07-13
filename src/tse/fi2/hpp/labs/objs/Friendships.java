package tse.fi2.hpp.labs.objs;

import java.util.Date;

public class Friendships {

	public Date dt;
	public int userId1;
	public int userId2;
	public Friendships(Date dt, int userId1, int userId2) {
		super();
		this.dt = dt;
		this.userId1 = userId1;
		this.userId2 = userId2;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	public int getUserId1() {
		return userId1;
	}
	public void setUserId1(int userId1) {
		this.userId1 = userId1;
	}
	public int getUserId2() {
		return userId2;
	}
	public void setUserId2(int userId2) {
		this.userId2 = userId2;
	}
	
	
}
