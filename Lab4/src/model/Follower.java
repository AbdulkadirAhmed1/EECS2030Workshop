package model;

import java.util.Arrays;
import java.util.Objects;

public class Follower {
	protected String name;
	protected String status;
	protected Channel[] followChannels;
	protected String type;
	
	protected int maxChannels; //maximum channels to follow
	protected int currentfollowChannels;
	protected int removecurrentfollowIndexChannels;
	
	protected Channel[] watchChannels;
	protected int currentWatchChannel;
	
	public Follower(String name) {
		this.name = name;
		this.removecurrentfollowIndexChannels = -1;
		this.type = "Follower";
		
		this.watchChannels = new Channel[100];	
		this.currentWatchChannel = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Follower other = (Follower) obj;
		return currentfollowChannels == other.currentfollowChannels
				&& Arrays.equals(followChannels, other.followChannels) && maxChannels == other.maxChannels
				&& Objects.equals(name, other.name) && Objects.equals(status, other.status);
	}

	public void updateStatus() {
		String header = this.name;
		String header2 = "follows no channels";
		
		if (this.currentfollowChannels > 0) {
			String followerInfo = "[";
			
			int c = 0;
			
			for (int i = 0; i < this.followChannels.length; i++) {
				if (followChannels[i] != null) {
					if (i == 0) {
						c = 1;
						followerInfo += this.followChannels[i].channelName;
					} else {
						if (c != 1) {
							followerInfo += this.followChannels[i].channelName;
						} else {
							followerInfo += ", " + this.followChannels[i].channelName;
						}
					}
				}
			}
			
			followerInfo += "]";
			
			header2 = "follows " + followerInfo;
		}
		
		this.status = String.format("%s %s", 
				header,header2);
	}
	
	public void addfollowChannel(Channel c) {
		if (this.currentfollowChannels < this.maxChannels) {
			if (this.removecurrentfollowIndexChannels == -1) {
				this.followChannels[this.currentfollowChannels++] = c;
			} else {
				this.followChannels[this.removecurrentfollowIndexChannels] = c;
				this.removecurrentfollowIndexChannels = -1;		
				this.currentfollowChannels++;
			}
		}
	}
	
	public void addCopyChannel(Channel watchChannel) {
		this.watchChannels[this.currentWatchChannel++] = watchChannel;
	}
	
	public boolean checkForCopy(String channelName) {
		boolean copyFound = false;
		
		for (int i = 0; i < this.watchChannels.length; i++) {
			if (this.watchChannels[i] != null) {
				copyFound = this.watchChannels[i].channelName.equals(channelName);
			}
		}
		
		return copyFound;
	}
	
	public int getWatchChannelLength() {
		int length = 0;
		
		for (int i = 0; i < this.watchChannels.length; i++) {
			if (this.watchChannels[i] != null) {
				length += 1;
			}
		}
		
		return length;
	}
	
	public void removefollowChannel(Channel c) {
		for (int i = 0; i < this.followChannels.length; i++) {
			if (this.followChannels[i] != null && this.followChannels[i].equals(c)) {
				this.followChannels[i] = null;
			    this.currentfollowChannels--;
				break;
			}
		}
	}
	
	public String toString() {
		return this.status;
	}
}
