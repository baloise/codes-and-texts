package ch.basler.cat.model;

import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractLongIdGenerator {
    protected Long generateId(SharedSessionContractImplementor session, String idFieldName, String entityName) {

        Connection connection = session.connection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select max(" + idFieldName + " ) as Id from " + entityName + "");
            if (rs.next()) {
                return rs.getLong(1) + 1L;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
