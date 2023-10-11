package com.example.repo;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface CrudRepository<T> {

    Map<Long, T> findAll() throws IOException, ParseException;

}
