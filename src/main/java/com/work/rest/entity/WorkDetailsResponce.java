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
public class WorkDetailsResponce {
    private Integer companyId;
    private String companyLogoSrc;
    private String companyName;
    private String companyContactPeople;
    private String companyPhoneNumber;

    private String jobCreatedDate;
    private String jobName;
    private String jobCity;
    private String jobSalary;
    private String jobEmploymentType;
    private String jobRequirements;
    private String jobDescription;


}
