package com.work.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private String site;
    private String lang = "ua";
    private String name = "";
    private String region = "";
    private String sort = "";
    private Integer page = 1;
}
