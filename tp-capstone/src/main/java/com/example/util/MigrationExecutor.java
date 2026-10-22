package com.example.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Gestionnaire d'exécution des scripts de migration SQL pour la base H2.
 */
public class MigrationExecutor {

    private static final Logger LOGGER = Logger.getLogger(MigrationExecutor.class.getName());

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public MigrationExecutor(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public void executeMigration(String resourcePath) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            LOGGER.warning("Fichier de migration introuvable : " + resourcePath);
            return;
        }

        List<String> instructions = parseStatements(is);

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            for (String instruction : instructions) {
                if (!instruction.trim().isEmpty()) {
                    statement.execute(instruction);
                }
            }
            LOGGER.info("Migration exécutée avec succès (" + instructions.size() + " instructions) : " + resourcePath);
        }
    }

    private List<String> parseStatements(InputStream is) throws Exception {
        List<String> instructions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                sb.append(line).append(" ");
                if (line.endsWith(";")) {
                    instructions.add(sb.toString().trim());
                    sb.setLength(0);
                }
            }
            if (sb.length() > 0 && !sb.toString().trim().isEmpty()) {
                instructions.add(sb.toString().trim());
            }
        }
        return instructions;
    }
}
