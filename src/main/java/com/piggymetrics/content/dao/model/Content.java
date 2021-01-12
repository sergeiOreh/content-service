package com.piggymetrics.content.dao.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CONTENT")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_name")
    private Account account;

    private ContentType type;

    private URL url;

    public Content(Account account, ContentType type) {
        this.account = account;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", account=" + account +
                ", type=" + type +
                ", url=" + url +
                '}';
    }
}
