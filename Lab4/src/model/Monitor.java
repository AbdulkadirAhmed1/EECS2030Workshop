package model;

public class Monitor extends Follower {
	public Monitor(String name,int maxChannels) {
		super("Monitor " + name);
		super.maxChannels = maxChannels;
		super.followChannels = new Channel[maxChannels];

		super.status = "Monitor" + " " + name + " follows no channels.";
	}
	
	public String monitorStringInfo(Channel givenChannel) {
		double averageWatchTime = givenChannel.findAverageWatchTime();
		int maxWatchTime = givenChannel.findMaxWatchTime();
		
		String monitorInfo = String.format("{#views: %d, max watch time: %d, avg watch time: %.2f}"
				,givenChannel.viewers,maxWatchTime,averageWatchTime);
		
		return monitorInfo;
	}
	
	public void updateStatus() {
		String header3 = ".";


		super.updateStatus();

		this.status = String.format("%s%s", 
				this.status,header3);
	}
	
	public void updateStatus(Channel watchChannel,int watchTime) {
		String header = super.name;
		String header2 = "";

		String followerInfo = "[";

		int c = 0;
		
		for (int i = 0; i < super.watchChannels.length; i++) {
			if (super.watchChannels[i] != null) {
				Channel currentChannel = super.watchChannels[i];
				
				if (i == 0) {
					c = 1;
					if (currentChannel.viewers > 0) {
						followerInfo += currentChannel.channelName + " " + monitorStringInfo(currentChannel);
					} else {
						followerInfo += currentChannel.channelName;
					}
				} else {
					if (c != 1) {
						if (currentChannel.viewers > 0) {
							followerInfo += currentChannel.channelName + " " + monitorStringInfo(currentChannel);
						} else {
							followerInfo += currentChannel.channelName;
						}
					} else {
						if (currentChannel.viewers > 0) {
							followerInfo += ", " + currentChannel.channelName + " " + monitorStringInfo(currentChannel);
						} else {
							followerInfo += ", " + currentChannel.channelName;
						}
					}
				}
			}
		}

		followerInfo += "]";

		header2 = "follows " + followerInfo + ".";

		this.status = String.format("%s %s", 
				header,header2);
	}
	
	public String toString() {
		return super.status;
	}
}