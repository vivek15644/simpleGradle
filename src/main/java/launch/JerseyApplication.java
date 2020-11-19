package launch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JerseyApplication {
	private static final Logger logger = LoggerFactory.getLogger(JerseyApplication.class);

	public static void main(String[] args) {
		
		final ResourceConfig application = new ResourceConfig()
                .packages("resource")
                .register(JacksonFeature.class);

		Server server = new Server(8080);

		ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);

		servletContextHandler.setContextPath("/");
		server.setHandler(servletContextHandler);

		ServletHolder jerseyServlet = new ServletHolder(new
                org.glassfish.jersey.servlet.ServletContainer(application));
		jerseyServlet.setInitOrder(0);
		
		servletContextHandler.addServlet(jerseyServlet, "/api/*");
		
		
//		ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/api/*");
//		servletHolder.setInitOrder(0);
//		servletHolder.setInitParameter("jersey.config.server.provider.packages",
//				"com.fasterxml.jackson.jaxrs.json;" + "resource");
		System.out.println("launching");
		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			logger.error("Error occurred while starting Jetty", ex);
			System.exit(1);
		}

		finally {
			server.destroy();
		}
	}
}
