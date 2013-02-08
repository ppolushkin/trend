package pp.trendservice.dao;

import pp.trendservice.Period;
import pp.trendservice.Symbol;
import pp.trendservice.TrendBarValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Polushkin
 */
public class TrendBarValueJdbcDao implements ITrendBarValueDao {

    private final String driver;

    private final String databaseUrl;

    private final String user;

    private final String password;

    private final static String CREATE_TABLE = "create table trend_bar(symbol char(6), period char(50), time BIGINT, open_price DECIMAL, min_price DECIMAL, max_price DECIMAL, close_price DECIMAL)";

    public TrendBarValueJdbcDao(String driver, String databaseUrl, String user, String password, boolean createTableOnStartup) {
        this.driver = driver;
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;

        if(createTableOnStartup) {
            updateOrInsertByQuery(CREATE_TABLE);
        }
    }

    public void save(TrendBarValue value) {
        StringBuilder sb = new StringBuilder("insert into trend_bar(symbol, period, time, open_price, min_price, max_price, close_price) values(");
        sb.append("'").append(value.getSymbol().getShortName()).append("',");
        sb.append("'").append(value.getPeriod().name()).append("',");
        sb.append("'").append(value.getTime()).append("',");
        sb.append("'").append(value.getOpenPrice()).append("',");
        sb.append("'").append(value.getMinPrice()).append("',");
        sb.append("'").append(value.getMaxPrice()).append("',");
        sb.append("'").append(value.getClosePrice()).append("')");

        updateOrInsertByQuery(sb.toString());
    }

    public List<TrendBarValue> getValues(Symbol symbol, Period period, long fromDate) {
        StringBuilder sb = new StringBuilder("select * from trend_bar where ");
        sb.append("symbol = '").append(symbol.getShortName()).append("' and ");
        sb.append("period = '").append(period.name()).append("' and ");
        sb.append("time >= ").append(fromDate);

        return getValuesByQuery(sb.toString());
    }

    public List<TrendBarValue> getValues(Symbol symbol, Period period, long fromDate, long toDate) {
        StringBuilder sb = new StringBuilder("select * from trend_bar where ");
        sb.append("symbol = '").append(symbol.getShortName()).append("' and ");
        sb.append("period = '").append(period.name()).append("' and ");
        sb.append("time >= ").append(fromDate).append(" and ");
        sb.append("time <= ").append(toDate);

        return getValuesByQuery(sb.toString());
    }

    private void updateOrInsertByQuery(String query) {
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(query);
            statement.executeUpdate(query);
            System.out.println("Query successfully executed");
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
        }
    }

    private List<TrendBarValue> getValuesByQuery(String query) {
        Connection dbConnection = null;
        Statement stmt = null;
        List<TrendBarValue> result = new ArrayList<TrendBarValue>();
        try {
            dbConnection = getDBConnection();
            stmt = dbConnection.createStatement();
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TrendBarValue value = new TrendBarValue(
                        Symbol.createByShortName(rs.getString("symbol")),
                        Period.valueOf(rs.getString("period")),
                        rs.getLong("time"),
                        rs.getBigDecimal("open_price"),
                        rs.getBigDecimal("min_price"),
                        rs.getBigDecimal("max_price"),
                        rs.getBigDecimal("close_price")
                );
                result.add(value);
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
        }
        return result;
    }

    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            return DriverManager.getConnection(databaseUrl, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();

                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}

