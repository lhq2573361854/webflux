package com.tianling.webflux.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/12 20:42
 */
@Data
@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NotBlank
    private String name;
    @Range(min = 10,max = 120)
    private int age;
}
