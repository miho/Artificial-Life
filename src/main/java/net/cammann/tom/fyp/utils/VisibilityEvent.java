package net.cammann.tom.fyp.utils;

public class VisibilityEvent {

	private final boolean isVisible;

	public VisibilityEvent(boolean visibility) {
		this.isVisible = visibility;
	}

	public boolean isVisible() {
		return isVisible;
	}
}
