package ch.basler.cat.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CodeTextIdGenerator implements IdentifierGenerator {

    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        Connection connection = session.connection();
        try {
            CodeText codeText = (CodeText) object;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select max(value) from value WHERE type_id = " + codeText.getType());
            if (rs.next()) {
                return rs.getLong(1) + 1L;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

