package Interfaces;

public interface Publishable<T> {
	
	void attach(Subscribable<T> sub);
	void detach(Subscribable<T> sub);
	void notifySubscribers();
	

}
