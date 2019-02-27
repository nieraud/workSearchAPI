package com.work.rest.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse {
    private Integer countFound;
    private Integer currentPage;
    private List<Integer> availablePages;
    private List<Vacancy> vacancies = new ArrayList<>();

    public SearchResponse(Integer countFound, List<Vacancy> vacancies) {
        this.countFound = countFound;
        this.vacancies = vacancies;
    }

}
