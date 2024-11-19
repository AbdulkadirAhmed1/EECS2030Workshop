package model;

public class Monitor extends Follower {
	public Monitor(String name,int maxChannels) {
		super("Monitor " + name);
		super.maxChannels = maxChannels;
		super.followChannels = new Channel[maxChannels];

		super.type = "Monitor";
		super.status = type + " " + name + " follows no channels.";
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
	
	public void updateStatus(Channel watchChannel,int watchTime) {
		if (super.getWatchChannelLength() > 0) {
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
		} else {
			String header3 = ".";


			super.updateStatus();

			this.status = String.format("%s%s", 
					this.status,header3);
		}
	}
	
	public String toString() {
		return super.status;
	}
}
