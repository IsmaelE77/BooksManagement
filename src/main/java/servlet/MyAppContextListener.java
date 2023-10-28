package servlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MyAppContextListener implements ServletContextListener {
    // This method is called when the web application is started, and it initializes the connection pool.
    public void contextInitialized(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        // Initialize the connection pool
        // You can read pool configuration from a file or create it programmatically.
        try {
            // Initialize the connection pool
            DataSource dataSource = createDataSource();
            ctx.setAttribute("datasource", dataSource);
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }
    }

    // This method is called when the web application is stopped, and it closes the connection pool.
    public void contextDestroyed(ServletContextEvent event) {
        // Close the connection pool
        HikariDataSource dataSource = (HikariDataSource) event.getServletContext().getAttribute("dataSource");
        if (dataSource != null) {
            // Close the data source or pool properly to release resources.
            try {
                dataSource.close();
            } catch (Exception e) {
                // Log the exception or handle it appropriately
                e.printStackTrace();
            }
        }
    }

    // This private method creates and configures a HikariCP DataSource for database connections.
    private static DataSource createDataSource() {
        HikariConfig config = new HikariConfig("/hikari.properties");
        return new HikariDataSource(config);
    }
}