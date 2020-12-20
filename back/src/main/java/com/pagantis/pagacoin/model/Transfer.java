package com.pagantis.pagacoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transfers")
@Data
public class Transfer {

    @Id
    private String id;

    private String walletId;
    private String walletIdDestination;
    private Double money;

    public Transfer(){ }


    public Transfer(String walletId, String walletIdDestination, Double money) {
        this.walletId = walletId;
        this.walletIdDestination = walletIdDestination;
        this.money = money;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id='" + id + '\'' +
                ", walletId='" + walletId + '\'' +
                ", walletIdDestination='" + walletIdDestination + '\'' +
                ", money=" + money +
                '}';
    }
}
