package com.cheirmin.pojo;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Filename
 * @auther 吴星辰;
 * @data 2019/12/21 12:10;
 * @Descripion
 * @Version 1.1.1
 * @Function
 * @History
 */

@Table(name = "address")
public class Address implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(useGeneratedKeys = true)
    Integer id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "address")
    String address;


    @Column(name = "is_defulet")
    Integer isDefulat;

    public Address() {
    }

    public Address(Long userId, String address, Integer isDefulat) {
        this.userId = userId;
        this.address = address;
        this.isDefulat = isDefulat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDefulat() {
        return isDefulat;
    }

    public void setIsDefulat(Integer isDefulat) {
        this.isDefulat = isDefulat;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId=" + userId +
                ", address='" + address + '\'' +
                ", isDefulat=" + isDefulat +
                '}';
    }
}
