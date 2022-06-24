package be.feeps.flying;

import be.feeps.flying.objects.pets.PetType;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* TODO
* A thing that would be much better is to don't use HashMap to retrieve data:
* When saving data, use the PreparedStatement Object and when retrieving, use
* the ResultSet as parameter.
*
* Advantages:
*  - Don't convert each object with this strangely methods toSQLString
*  - Direct access to each Object Result (ex: Don't use TimeStamp.parse)
*  - PreparedStatement is safer ( Don't get MySQL hacked (Injected Strings) )
*
* Inconveniants:
*  - Think about how to cache all ResultSet (Other solution: never cache so each time do mysql statement directly)
*
*
* - Cache idea -> {
*       Each time we call a method like getObject(i), put it in a HashMap
*
* }
*
*
*
*ANYONE HAS A TIP ?
*/
public class FlyingDatabase {
    private static final String dataPrefix = "v1";
    private static final ArrayList<String> initQueries = new ArrayList<String>(){
        {
            add("CREATE TABLE IF NOT EXISTS " + getTableName() + "(uuid VARCHAR(36) PRIMARY KEY, spawn_join BOOL, last TINYINT)"); // last is the PetType ordinal

            for (PetType type : PetType.values())
                add("CREATE TABLE IF NOT EXISTS " + getTableName(type) + "(uuid VARCHAR(36) PRIMARY KEY, name VARCHAR(16), level INT, exp INT, lastmission TIMESTAMP NULL DEFAULT NULL" + (type.getSqlTable() != null ? "," + type.getSqlTable() : "") + ")");
        }
    };

    private final JavaPlugin plugin;
    private Connection connection;
    private boolean async;

    // Database information
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    public FlyingDatabase(JavaPlugin plugin) throws SQLException {
        this.plugin = plugin;

        File mysqlFile = new File(plugin.getDataFolder(), "mysql.yml");
        if (!mysqlFile.exists())
            plugin.saveResource("mysql.yml", false); // Get the default one on the jar and put it in the plugin data folder if doesn't exist

        FileConfiguration config = YamlConfiguration.loadConfiguration(mysqlFile); // Configuration file

        // Get the MySQL's data from the config
        host = config.getString("host");
        port = config.getInt("port");
        database = config.getString("database");
        username = config.getString("user");
        password = config.getString("password");

        // Init statements will not be in a asychrounous thread, because they'll be executed when the server starts
        Statement statement = getConnection().createStatement(); // First time that getConnection() is called, so the database is currently connecting.
        for (String query : initQueries)
            statement.execute(query);
    }

    private Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver"); // If throw ClassNotFoundException, server not compatible and doesn't have JDBC
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void close(){
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void queryStatement(PreparedStatement statement, Consumer<HashMap<String, Object>> callback){
        Runnable runnable = () -> {
            HashMap<String, Object> results = new HashMap<>();
            try {
                ResultSet queryResult = statement.executeQuery();
                ResultSetMetaData metaData = queryResult.getMetaData();

                while(queryResult.next()){
                    for (int i = 1; i <= metaData.getColumnCount(); i++){
                        Object obj = queryResult.getObject(i);
                        if (obj != null)
                            results.put(metaData.getColumnName(i), obj);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            callback.accept(results);
        };

        if (async)
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), runnable);
        else
            runnable.run();
    }

    public void queryStatement(String query, Consumer<HashMap<String, Object>> callback){
        try {
            queryStatement(getConnection().prepareStatement(query), callback);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatement(PreparedStatement statement){
        Runnable runnable = () -> {
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        if (async)
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), runnable);
        else
            runnable.run();
    }

    public void updateStatement(String query){
        try {
            updateStatement(getConnection().prepareStatement(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        All getters/Setters relative to FlyingPets
     */
    public void getPetData(UUID uuid, PetType type, Consumer<HashMap<String, Object>> callback){
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + getTableName(type) + " WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            queryStatement(statement, callback);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPetData(UUID uuid, PetType type, HashMap<String, Object> data){
        String columns = toSQLString(data.keySet().toArray(), true);
        String values = toSQLString(data.values().toArray());

        updateStatement("REPLACE INTO " + getTableName(type) + "(uuid, " + columns + ")" + " VALUES('" + uuid.toString() + "', " + values + ")");
    }

    public void getFlyingData(UUID uuid, Consumer<HashMap<String, Object>> callback){
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM flyingpets_" + dataPrefix + " WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            queryStatement(statement, callback);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setFlyingData(UUID uuid, HashMap<String, Object> data){
        String columns = toSQLString(data.keySet().toArray(), true);
        String values = toSQLString(data.values().toArray());

        updateStatement("REPLACE INTO " + getTableName() + "(uuid, " + columns + ")" + " VALUES('" + uuid.toString() + "', " + values + ")");
    }

    public void async(){
        async = true;
    }

    public void sync(){
        async = false;
    }

    public void savePlayer(final FlyingPlayer fPlayer){
        final UUID uuid = fPlayer.getUniqueId();

        // Main data
        {
            HashMap<String, Object> data = fPlayer.getPlayerData().getData(); //fPlayer.getSQLCache().get(getTableName());
            if (!data.isEmpty())
                Main.getInstance().getDatabase().setFlyingData(uuid, data);
        }

        if (!fPlayer.getSQLCache().isEmpty()){
            // Pet data
            for (PetType type : PetType.values()){
                HashMap<String, Object> data = fPlayer.getSQLCache().get(getTableName(type));
                if (!data.isEmpty())
                    Main.getInstance().getDatabase().setPetData(uuid, type, data);
            }
        }
    }


    public void loadPlayer(final FlyingPlayer fPlayer, final Runnable callback) {
        final AtomicInteger remainings = new AtomicInteger(PetType.values().length + 1);
        final Runnable rem = () -> {
            if (remainings.decrementAndGet() == 0) // All is finished
                callback.run();
        };

        final UUID uuid = fPlayer.getUniqueId();

        for (final PetType type : PetType.values()){
            getPetData(uuid, type, data -> {
                data.remove("uuid"); // Avoid to get twice time the uuid
                fPlayer.getSQLCache().put(getTableName(type), data);
                rem.run();
            });
        }

        getFlyingData(uuid, data -> {
            data.remove("uuid");
            fPlayer.getSQLCache().put(getTableName(), data);
            fPlayer.getPlayerData().setData(data);
            rem.run();
        });
    }

    /*
        Useful static methods to develop faster!
        MySQL String conversion.

        Not a very good way to use MySQL Database
     */
    public static String getTableName(PetType type){
        return "flyingpets_" + dataPrefix + "_" + type.toString().toLowerCase();
    }

    public static String getTableName(){
        return "flyingpets_" + dataPrefix;
    }

    /*
        Data conversion
     */
    public static String toSQLString(String s){
        return "'" + StringEscapeUtils.escapeSql(s) + "'";
    }

    public static String toSQLString(String s, boolean noQuotes){
        return noQuotes ? StringEscapeUtils.escapeSql(s) : toSQLString(s);
    }

    public static String toSQLString(boolean b){
        return b ? "1" : "0";
    }

    public static String toSQLString(Integer i){
        return i.toString();
    }

    public static String toSQLString(Object obj){
        return toSQLString(obj, true);
    }

    public static String toSQLString(Object obj, boolean noQuotes){
        if (obj == null)
            return null;
        if (obj instanceof String)
            return toSQLString((String) obj, noQuotes);
        if (obj instanceof Integer)
            return toSQLString((int) obj);
        if (obj instanceof Boolean)
            return toSQLString((boolean) obj);
        else
            return toSQLString(obj.toString(), false);
    }

    private static String toSQLString(Object[] array, boolean noQuotes){
        String result = "";

        for (int i = 0; i < array.length; i++) {
            result += toSQLString(array[i], noQuotes);

            if (i != array.length - 1)
                result += ", ";
        }

        return result;
    }

    public static String toSQLString(Object[] array){
        return toSQLString(array, false);
    }
}
