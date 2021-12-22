package com.nure.br4.domain.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "orderStatus")
@XmlEnum
public enum OrderStatus {
    JustCreated,
    Pending,
    Ready
}
