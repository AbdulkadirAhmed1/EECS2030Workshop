package model;

import java.util.Arrays;
import java.util.Objects;

public class Channel {
	String channelName;
	String status;
	String[] videos;
	
	Follower[] followerlist;
	
	int maxFollowers;
	int maxVideos; 
	int currentVideos;
	int currentFollower;
	int removecurrentIndexFollower;
	
	//int viewers;
	//int[] watchTime;
	//int currentWatchTime;
	
	public Channel(String channelName,int maxFollowers,int maxVideos) {
		this.channelName = channelName;
		this.maxFollowers = maxFollowers;
		this.maxVideos = maxVideos;
		//this.currentWatchTime = 0;
		
		this.removecurrentIndexFollower = -1;
		this.videos = new String[maxVideos];
		this.followerlist = new Follower[maxFollowers];
		
		this.status = String.format("%s released no videos and "
				+ "has no followers."
				,this.channelName);
		
		//this.watchTime = new int[100];
	}

	/*public void addViewer() {
		viewers++;
	}
	
	public void addWatchTime(int watchTime) {
		this.watchTime[currentWatchTime++] = watchTime;
	}
	
	public int findMaxWatchTime() {
		int max = -1;
		
		for (int i = 0; i < watchTime.length; i++) {
			if (watchTime[i] > max) {
				max = watchTime[i];
			}
		}
		
		return max;
	}
	
	public double findAverageWatchTime() {
		double average = 0;
		double sum = 0;
		double amount = 0;
		
		for (int i = 0; i < watchTime.length; i++) {
			if (watchTime[i] != 0) {
				sum += watchTime[i];
				amount += 1;
			}
		}
		
		if (amount > 0) {average = sum/amount;}
		
		return average;
	}*/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		return Objects.equals(channelName, other.channelName) && currentFollower == other.currentFollower
				&& currentVideos == other.currentVideos && Arrays.equals(followerlist, other.followerlist)
				&& maxFollowers == other.maxFollowers && maxVideos == other.maxVideos
				&& Objects.equals(status, other.status) && Arrays.equals(videos, other.videos);
	}



	public void updateStatus() {
		String header = String.format("%s released",this.channelName);
		String header1 = "no videos";
		String header2 = "has no followers.";
		
		if (currentVideos > 0) {
			String videoInfo = "<";
			
			int c = 0;
			
			for (int i = 0; i < this.videos.length; i++) {
				if (videos[i] != null) {
					if (i == 0) {
						c = 1;
						videoInfo += videos[i];
					} else {
						if (c != 1) {
							videoInfo += videos[i];
						} else {
							videoInfo += ", " + videos[i];	
						}
					}
				}
			}
			
			
			
			videoInfo += ">";
			
			header1 = videoInfo;
		}
		
		if (currentFollower > 0) {
			String followerInfo = "[";
			
			int c = 0;
			
			for (int i = 0; i < this.followerlist.length; i++) {
				if (followerlist[i] != null) {
					Follower follower = followerlist[i];
					
					if (i == 0) {
						c = 1;
						followerInfo += follower.name;
					} else {
						if (c != 1) {
							followerInfo += follower.name;
						} else {
							followerInfo += ", " + follower.name;
						}
					}
				}
			}
			
			
			followerInfo += "]";
			
			header2 = "is followed by " + followerInfo + ".";
		}
		
	
		this.status = String.format("%s %s and %s", header,header1,header2);
	}
	
	public void follow(Follower f) {
		//[a,b,c,d,...] 
		//counter=4
		
		//[a,b,null,d,...]
		//counter=3
		
		//[a,b,e,d,....]
		//counter=4
		
		if (this.currentFollower < this.maxFollowers) {
			if (this.removecurrentIndexFollower == -1) {
				this.followerlist[this.currentFollower++] = f;
			} else {
				this.followerlist[this.removecurrentIndexFollower] = f;
				this.removecurrentIndexFollower = -1;
				this.currentFollower++;
			}
			
			f.addfollowChannel(this);
			f.updateStatus();
			
			this.updateStatus();
		}
	}
	
	public void unfollow(Follower f) {
		for (int i = 0; i < this.followerlist.length; i++) {
			if (this.followerlist[i] != null && this.followerlist[i].equals(f)) {
				this.followerlist[i] = null;
			    this.currentFollower--;
			    this.removecurrentIndexFollower = i;
				
				f.removefollowChannel(this);
				f.updateStatus();
				
				this.updateStatus();
				
				break;
			}
		}
	}
	 
	public void releaseANewVideo(String videoName) {
		this.videos[this.currentVideos++] = videoName;
		
		for (int i = 0; i < followerlist.length; i++) {
			if (this.followerlist[i] != null) {
				//I will use down-casting here:
				//Its safe because I know my reference here is Follower:
				//Which is higher up and we have two options to downcast
				//too which is Subscriber/Monitor
				
				if (this.followerlist[i].type == "Subscriber") {
					Subscriber follower = (Subscriber) this.followerlist[i];
					
					follower.addrecommendedVideo(videoName);
					follower.updateStatus();
				}
				//this.followerlist[i].updateStatus();
			}
		}
		
		updateStatus();
	}
	
	public String toString() {
		return this.status;
	}
}
