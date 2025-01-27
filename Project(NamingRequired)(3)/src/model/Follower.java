package model;

import java.util.Arrays;
import java.util.Objects;

public class Follower {
	protected String name;
	protected String status;
	protected Channel[] followChannels;
	
	protected int maxChannels; //maximum channels to follow
	protected int currentfollowChannels;
	
	protected Channel[] watchChannels;
	protected int currentWatchChannel;
	
	protected Channel[] watchChannels;
	protected int currentWatchChannel;
	
	public Follower(String name) {
		this.name = name;
<<<<<<< HEAD
=======
		this.removecurrentfollowIndexChannels = -1;
		this.type = "Follower";
>>>>>>> 3bf04088723e664b1b15d94df6b4f75b15412690
		
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
		this.followChannels[this.currentfollowChannels++] = c;
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
	
	public Channel returnCopyChannel(String channelName) {
		Channel copyChannel = null;
		
		for (int i = 0; i < this.watchChannels.length; i++) {
			if (this.watchChannels[i] != null) {
				if (this.watchChannels[i].channelName.equals(channelName)) {
					copyChannel = this.watchChannels[i];
				}
			}
		}
		
		return copyChannel;
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