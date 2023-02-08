package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    public static final String paymentData = "SELECT * FROM app.payment_entity";
    public static final String orderData = "SELECT * FROM app.order_entity";
    public static final String creditData = "SELECT * FROM app.credit_request_entity";
    public static final String deletePayment = "DELETE FROM app.payment_entity";
    public static final String deleteOrder = "DELETE FROM app.order_entity";
    public static final String deleteCredit = "DELETE FROM app.credit_request_entity";
    public static final String url = System.getProperty("db.url");
    public static final String username = System.getProperty("username");
    public static final String pass = System.getProperty("password");

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(url, username, pass);
    }

    @SneakyThrows
    public static void dropData() {
        var runner = new QueryRunner();
        try (Connection conn = getConnection()) {
            runner.update(conn, deletePayment);
            runner.update(conn, deleteOrder);
            runner.update(conn, deleteCredit);
        }
    }

    @SneakyThrows
    public static SQLEntity getPaymentData() {
        var runner = new QueryRunner();
        try (Connection conn = getConnection()) {
            return runner.query(conn, paymentData, new BeanHandler<>(SQLEntity.class));
        }
    }

    @SneakyThrows
    public static SQLEntity getCreditData() {
        var runner = new QueryRunner();
        try (Connection conn = getConnection()) {
            return runner.query(conn, creditData, new BeanHandler<>(SQLEntity.class));
        }
    }
}