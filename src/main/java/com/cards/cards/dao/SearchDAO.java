package com.cards.cards.dao;

import java.sql.Date;
import java.util.Optional;

import lombok.Getter;

@Getter
public class SearchDAO {
    private Optional<String> email = Optional.empty();
    private Optional<String> phone = Optional.empty();
    private Optional<Date> birthday = Optional.empty();
}
