package model;

public class Subscriber extends Follower {
	int maxVideos; //maximum videos to be recommended by the channels
	
	String[] recommendedVideos;
	int currentrecommendedVideos;
	int removecurrentrecommendedVideos;
	
	public Subscriber(String name,int maxChannels,int maxVideos) {
		super("Subscriber " + name);
		super.maxChannels = maxChannels;
		this.maxVideos = maxVideos;
		this.removecurrentrecommendedVideos = -1;
		
		super.followChannels = new Channel[maxChannels];
		this.recommendedVideos = new String[maxVideos];
		
		super.type = "Subscriber";
		
		super.status = type + " " + name + " follows no channels "
				+ "and has no recommended videos.";
	}
	
	public void updateStatus() {
		String header3 = "has no recommended videos.";
		
		super.updateStatus();
		
		if (this.currentrecommendedVideos > 0) {
			int c = 0;
			String recommendedVideoInfo = "<";
			
			for (int i = 0; i < recommendedVideos.length; i++) {
				if (this.recommendedVideos[i] != null) {
					if (i == 0) {
						c = 1;
						recommendedVideoInfo += this.recommendedVideos[i];
					} else {
						if (c != 1) {
							recommendedVideoInfo += this.recommendedVideos[i];
						} else {
							recommendedVideoInfo += ", " + this.recommendedVideos[i];
						}
					}
				}
			}
			
			recommendedVideoInfo += ">";
			
			header3 = "is recommended " + recommendedVideoInfo + ".";
		}
		
		this.status = String.format("%s and %s", 
				this.status,header3);
	}
	
	public void addrecommendedVideo(String video) {
		if (this.currentrecommendedVideos < this.maxVideos) {
			if (this.removecurrentrecommendedVideos == -1) {
				this.recommendedVideos[this.currentrecommendedVideos++] = video;
			} else {
				this.recommendedVideos[this.removecurrentrecommendedVideos] = video;
				this.removecurrentrecommendedVideos = -1;		
				this.currentrecommendedVideos++;
			}
		}
	}
	
	public Monitor findMonitor(Channel givenChannel,int watchTime, int Index) {
		Monitor foundMonitor = null;
		
		for (int i = 0; i < givenChannel.followerlist.length; i++) {
			if (givenChannel.followerlist[i] != null) {
				//I will use down-casting here:
				//Its safe because I know my reference here is Follower:
				//Which is higher up and we have two options to downcast
				//too which is Subscriber/Monitor
				
				if (givenChannel.followerlist[i].type == "Monitor") {
					Monitor monitor = (Monitor) givenChannel.followerlist[i];
					
					monitor.addViewer(Index);
					monitor.addWatchTime(Index,watchTime);
					
					monitor.updateStatus(givenChannel);
				}
			}
		}
		
		return foundMonitor;
	}
	
	public void watch(String video, int watchTime) {
		for (int i = 0; i < super.followChannels.length; i++) {
			if (super.followChannels[i] != null) {
				Channel givenChannel = super.followChannels[i];
				
				boolean foundChannel = false;
				
				for (int j = 0; j < givenChannel.videos.length; j++) {
					if (givenChannel.videos[j] != null && givenChannel.videos[j] == video) {
						foundChannel = true;
					}
				}
				
				if (foundChannel == true) {
					findMonitor(givenChannel,watchTime,i);
				}
			}
		}
	}
	
	public String toString() {
		return super.status;
	}
}
