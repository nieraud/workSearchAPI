package com.work.rest.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vacancy {
    private Integer id;
    private String name;
    private String imgSrc;
    private String companyName;
    private String city;
    private String description;
    private Boolean hot;
}
