package com.piggymetrics.content.dao.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    private String name;

    private Date dateCreated;

    private String note;

    @OneToMany(mappedBy = "account")
    private List<Content> contentList;

    public Account(String name) {
        this.name = name;
    }

    public Account(String name, String note) {
        this.name = name;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", note='" + note + '\'' +
                '}';
    }
}
