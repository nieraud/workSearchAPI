package com.work.rest.controller;

import com.work.rest.entity.WorkDetails;
import com.work.rest.entity.WorkDetailsResponce;
import com.work.rest.exception.UnknownSiteException;
import com.work.rest.service.base.SearchService;
import com.work.rest.utils.ProjectConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DetailWorkInfoController {

    private final SearchService workService;
    private final SearchService douService;
    private final SearchService rabotaService;

    @Autowired
    public DetailWorkInfoController(@Qualifier("workServiceImpl") SearchService workService, @Qualifier("douServiceImpl") SearchService douService, @Qualifier("rabotaServiceImpl") SearchService rabotaService) {
        this.workService = workService;
        this.douService = douService;
        this.rabotaService = rabotaService;
    }

    @RequestMapping("/work")
    public ResponseEntity workDetails(@RequestBody WorkDetails details) throws UnknownSiteException, IOException {
        WorkDetailsResponce response;
        switch (details.getSite()) {
            case ProjectConstants.WORK_UA_NAME:
                response = workService.workDetails(details);
                break;
            case ProjectConstants.RABOTA_UA_NAME:
                response = rabotaService.workDetails(details);
                break;
            case ProjectConstants.DOU_NAME:
                response = douService.workDetails(details);
                break;
            default:
                throw new UnknownSiteException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
