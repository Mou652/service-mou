
package com.mou.mapstuct.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
public class User implements Serializable {
    private BigInteger id;
    private String name;
    private Integer age;
    private Date birthdate;
    private float wallet;
}