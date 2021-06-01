class ThreadDemo1 {
	int count;
	public ThreadDemo1() {
		count = 0;
	}
	public void printOdd() {
		while(true) {
			synchronized(this) {
				while(count%2==0) {
					try {
						wait();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("Printed from the thread 1 " +count);
				count++;
				if (count==100) {
					break;
				}
				notify();
			}
		}
	}
	
	public void printEven() {
		while(true) {
			synchronized(this) {
				while(count%2==1) {
					try {
						wait();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("Printed from the thread 2 " +count);
				count++;
				if (count==100) {
					break;
				}
				notify();
			}
		}
	}
}
class ThreadDemo {
	
	public static void main(String[] arg) {
		ThreadDemo1 d = new ThreadDemo1();
		Runnable r1 = ()-> {
			d.printEven();
		};
		Runnable r2 = ()-> {
			d.printOdd();
		};
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}