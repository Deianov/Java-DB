package demo;

// Notice, do not import com.mysql.cj.jdbc.*
// or you will have problems!
// https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-connect-drivermanager.html


public class LoadDriver {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        } catch (Exception  ex) {
            ex.printStackTrace();
        }
    }
}
