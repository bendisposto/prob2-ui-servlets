package de.prob;

public class Main {

	private static void runServer(final String url, final String iface, final int port) {
		logger.debug("Shell");
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					//	WebConsole.run(url, iface, port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public static void main(String[] args) {
		String url = "";
		int port = -1;
		String iface = "0.0.0.0";
		runServer(url, iface, port);
	}

}
