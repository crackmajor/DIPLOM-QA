package ru.netology.data;

import lombok.Data;

@Data
public class SQLEntity {
    private String id;
    private int amount;
    private String status;
    private String bank_id;
}