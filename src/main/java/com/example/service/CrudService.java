package com.example.service;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CrudService<T> {

    Map<Long, T> list() throws IOException, ParseException;

    T create(T t) throws IOException, ParseException;

    T get(int id) throws IOException, ParseException;


}
