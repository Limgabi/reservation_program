package reserve;

public class Reserve {
	
	private int reserveNum;
	private String userID;
	private String reserveDate;
	private String reserveTime;
	private int reservePeople;
	private String reserveMenu;
	
	public int getReserveNum() {
		return reserveNum;
	}
	public void setReserveNum(int reserveNum) {
		this.reserveNum = reserveNum;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public int getReservePeople() {
		return reservePeople;
	}
	public void setReservePeople(int reservePeople) {
		this.reservePeople = reservePeople;
	}
	public String getReserveMenu() {
		return reserveMenu;
	}
	public void setReserveMenu(String reserveMenu) {
		this.reserveMenu = reserveMenu;
	}

}
