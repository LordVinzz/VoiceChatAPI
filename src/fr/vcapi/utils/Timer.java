package fr.vcapi.utils;

public class Timer {

	private boolean isReady = false;
	float tickTime, clock = 0;

	public Timer(float tickTime) {
		this.tickTime = tickTime;
	}

	public void tick(float interval) {
		if(!isReady) {
			clock += interval;
		}
		if(clock >= tickTime) {
			isReady = true;
			clock = 0;
		}
	}

	public void reset() {
		if (isReady) isReady = false;
	}

	public boolean isReady() {
		return isReady;
	}
	
}
