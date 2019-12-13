package by.andersenlab.connection;


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;


@Slf4j
public class ConnectionManager {

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection(int level) throws ConnectionException {
        try {
            if (tl.get() == null) {
                tl.set(PoledConnections.getInstance().getConnection(level));
            }
            return tl.get();
        } catch (Exception e) {
            log.error("Create connection error", e);
            throw new ConnectionException("Create connection error " +  e.getMessage());
        }
    }


}
