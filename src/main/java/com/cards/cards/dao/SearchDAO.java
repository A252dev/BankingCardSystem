package com.cards.cards.dao;

import java.sql.Date;
import java.util.Optional;

import lombok.Data;

@Data
public class SearchDAO {
    private Optional<String> email = Optional.empty();
    private Optional<String> phone = Optional.empty();
    private Optional<Date> birthday = Optional.empty();
}
