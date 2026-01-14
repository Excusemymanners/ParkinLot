package com.parking.parkinglot.ejb;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

@Stateful
@SessionScoped
public class InvoiceBean implements Serializable {
    Set<Long> userIds = new HashSet<>();

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }
}
