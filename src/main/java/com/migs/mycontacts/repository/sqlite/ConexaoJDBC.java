package com.migs.mycontacts.repository.sqlite;

import com.migs.mycontacts.exception.SQLInvalidoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum ConexaoJDBC {
    INSTANCE;

    private static final String URL = "jdbc:sqlite:mycontacts.db";
    private Connection connection;

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String sql = """
                    CREATE TABLE IF NOT EXISTS contato (
                        id VARCHAR(36) PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        telefone VARCHAR(13) NOT NULL,
                        email TEXT,
                        descricao TEXT
                    );
                """;

                connection = DriverManager.getConnection(URL);
                try (Statement statement = connection.createStatement()) { statement.execute(sql); }
            }
        }

        catch (SQLException ex) {
            throw new SQLInvalidoException(ex.getMessage());
        }

        return connection;
    }
}
