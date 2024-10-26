package org.example.manager;

import org.example.model.Reader;

import java.util.List;

public interface ReaderManagerInterface {
    void addReader(String name, String email);
    List<Reader> getAllReaders();
    Reader findReaderByEmail(String email);
    void deleteReader(int id);
}
