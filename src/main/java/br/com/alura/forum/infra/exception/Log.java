package br.com.alura.forum.infra.exception;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private final static Logger LOGGER = Logger.getLogger(Log.class.getName());
    private static final String FILE_PATH = "conversorlog.txt";
    static {
        try {
            FileHandler fileHandler = new FileHandler(FILE_PATH, true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao configurar o handler de arquivo", e);
        }
    }
}