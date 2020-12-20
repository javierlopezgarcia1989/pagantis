package com.pagantis.pagacoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Objects;

@Document(collection = "wallets")
@Data
public class Wallet {

    @Id
    private String id;

    private String userId;
    private Double money;
    private String name;

    public Wallet(){ }

    public Wallet(String userId, String name, Double money) {
        this.userId = userId;
        this.money = money;
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) &&
                Objects.equals(userId, wallet.userId) &&
                Objects.equals(money, wallet.money) &&
                Objects.equals(name, wallet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, money, name);
    }
}
