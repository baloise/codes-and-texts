package ch.basler.cat.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CodeValueIdGenerator implements IdentifierGenerator {

    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        Connection connection = session.connection();
        try {
            CodeValue codeValue = (CodeValue) object;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select max(value) from codevalue WHERE codetype_id = " + codeValue.getTypeId());
            if (rs.next()) {
                long newValue = rs.getLong(1) + 1L;
                return codeValue.getTypeId() + ":" + newValue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

