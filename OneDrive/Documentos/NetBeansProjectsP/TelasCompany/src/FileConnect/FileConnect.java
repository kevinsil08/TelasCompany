package FileConnect;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author natha
 */
public class FileConnect {

    private Connection connection;

    public FileConnect() throws IOException {
        this.connection = read();
    }

    private Connection read() throws FileNotFoundException, IOException {

        Properties properties = new Properties();
        Thread currentThread = Thread.currentThread();
        ClassLoader contextClassLoader = currentThread.getContextClassLoader();
        InputStream propertiesStream = contextClassLoader.getResourceAsStream("FileConnect/dbProperties.properties");
        if (propertiesStream != null) {
            properties.load(propertiesStream);
            String baseDatosNombre = properties.getProperty("baseDatosNombre");
            String usuario = properties.getProperty("usuario");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url") + baseDatosNombre;
            String driver = properties.getProperty("driver");
            try {
                Class.forName(driver);
                return DriverManager.getConnection(url, usuario, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(" db properties file not found ");
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
