package com.example.repo;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;

public interface CrudRepository<T> {

    Map<Integer, T> findAll() throws IOException, ParseException;

}
