package ch.basler.cat.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException { {
            Connection connection = session.connection();

            try {
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("select max(id) as Id from Application");

                if (rs.next()) {
                    return rs.getLong(1) + 1L;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

