package model;

public class Monitor extends Follower {
	int[] viewers; //[...,....,....] corresponding to channel followChannels index
	int[][] watchTime; //[[],[],[]] where each index corresponding to channel followChannels index
	int[] currentWatchTime; //corresponding to channel followChannels index
	
	public Monitor(String name,int maxChannels) {
		super("Monitor " + name);
		super.maxChannels = maxChannels;
		super.followChannels = new Channel[maxChannels];
		
		this.currentWatchTime = new int[100];
		this.watchTime = new int[100][100];
		this.viewers = new int[100];
		
		super.type = "Monitor";
		super.status = type + " " + name + " follows no channels.";
	}
	
	public void addViewer(int Index) {
		viewers[Index]++;
	}
	
	public void addWatchTime(int Index,int watchTime) {
		this.watchTime[Index][currentWatchTime[Index]++] = watchTime;
		//i.e maybe we add 1
		//[[1],[],[]]
	}
	
	public int findMaxWatchTime(int Index) {
		int max = -1;
		
		for (int j = 0; j < watchTime[Index].length; j++) {
			if (watchTime[Index][j] > max) {
				max = watchTime[Index][j];
			}
		}
		
		return max;
	}
	
	public double findAverageWatchTime(int Index) {
		double average = 0;
		double sum = 0;
		double amount = 0;
		
		for (int i = 0; i < watchTime[Index].length; i++) {
			if (watchTime[Index][i] != 0) {
				sum += watchTime[Index][i];
				amount += 1;
			}
		}
		
		if (amount > 0) {average = sum/amount;}
		
		return average;
	}
	
	public String monitorStringInfo(int Index) {
		double averageWatchTime = this.findAverageWatchTime(Index);
		int maxWatchTime = this.findMaxWatchTime(Index);
		
		String monitorInfo = String.format("{#views: %d, max watch time: %d, avg watch time: %.2f}"
				,this.viewers[Index],maxWatchTime,averageWatchTime);
		
		return monitorInfo;
	}
	
	public void updateStatus() {
		String header3 = ".";


		super.updateStatus();

		this.status = String.format("%s%s", 
				this.status,header3);
	}
	
	public void updateStatus(Channel allowedChannel) {		
		String header = super.name;
		String header2 = "";

		String followerInfo = "[";

		int c = 0;

		for (int i = 0; i < super.followChannels.length; i++) {
			if (super.followChannels[i] != null) {
				if (i == 0) {
					c = 1;
					if (this.viewers[i] > 0) {
						followerInfo += super.followChannels[i].channelName + " " + monitorStringInfo(i);
					} else {
						followerInfo += super.followChannels[i].channelName;
					}
				} else {
					if (c != 1) {
						if (this.viewers[i] > 0) {
							followerInfo += super.followChannels[i].channelName + " " + monitorStringInfo(i);
						} else {
							followerInfo += super.followChannels[i].channelName;
						}
					} else {
						if (this.viewers[i] > 0) {
							followerInfo += ", " + super.followChannels[i].channelName + " " + monitorStringInfo(i);
						} else {
							followerInfo += ", " + super.followChannels[i].channelName;
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
