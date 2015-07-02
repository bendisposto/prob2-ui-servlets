package de.prob.webconsole;

import javax.servlet.ServletContextEvent;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Developers can access instantiated classes from an active instance of ProB
 * 2.0 by accessing the {@link Injector} from this class (using the
 * {@link ServletContextListener#getInjector()} method).
 *
 * @author joy
 *
 */
public class ServletContextListener extends GuiceServletContextListener {

	/**
	 * * @deprecated Use {@link Main.getInjector()} instead.
	 */
	@Deprecated
	public static final Injector INJECTOR = de.prob.Main.getInjector();

	/**
	 * * @deprecated Use {@link Main.getInjector()} instead.
	 */
	@Deprecated
	@Override
	protected Injector getInjector() {
		return de.prob.Main.getInjector();
	}

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
	}

}