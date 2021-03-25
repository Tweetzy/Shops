package ca.tweetzy.shops.settings;

import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.configuration.ConfigSetting;
import ca.tweetzy.shops.Shops;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class Settings {

    static final Config config = Shops.getInstance().getCoreConfig();

    public static final ConfigSetting LANG = new ConfigSetting(config, "lang", "en_US", "Default language file");

    /*
    =========== DATABASE OPTIONS ===========
     */
    public static final ConfigSetting DATABASE_USE = new ConfigSetting(config, "database.use database", false, "Should the plugin use a database to store shop data?");
    public static final ConfigSetting DATABASE_HOST = new ConfigSetting(config, "database.host", "localhost", "What is the connection url/host");
    public static final ConfigSetting DATABASE_PORT = new ConfigSetting(config, "database.port", 3306, "What is the port to database (default is 3306)");
    public static final ConfigSetting DATABASE_NAME = new ConfigSetting(config, "database.name", "plugin_dev", "What is the name of the database?");
    public static final ConfigSetting DATABASE_USERNAME = new ConfigSetting(config, "database.username", "root", "What is the name of the user connecting?");
    public static final ConfigSetting DATABASE_PASSWORD = new ConfigSetting(config, "database.password", "Password1.", "What is the password to the user connecting?");
    public static final ConfigSetting DATABASE_USE_SSL = new ConfigSetting(config, "database.use ssl", true, "Should the database connection use ssl?");



    public static void setup() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
