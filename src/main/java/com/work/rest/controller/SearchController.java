package com.work.rest.controller;


import com.work.rest.entity.Search;
import com.work.rest.entity.SearchResponse;
import com.work.rest.exception.UnknownSiteException;
import com.work.rest.service.base.SearchService;
import com.work.rest.utils.ProjectConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/search")
public class SearchController {

    private final SearchService workService;
    private final SearchService douService;
    private final SearchService rabotaService;

    @Autowired
    public SearchController(@Qualifier("workServiceImpl") SearchService workService, @Qualifier("douServiceImpl") SearchService douService, @Qualifier("rabotaServiceImpl") SearchService rabotaService) {
        this.workService = workService;
        this.douService = douService;
        this.rabotaService = rabotaService;
    }

    @PostMapping(name="/")
    public ResponseEntity search(@RequestBody Search search) throws Exception {
        SearchResponse response;
        switch (search.getSite()){
            case ProjectConstants.WORK_UA_NAME: response = workService.search(search); break;
            case ProjectConstants.RABOTA_UA_NAME: response = rabotaService.search(search); break;
            case ProjectConstants.DOU_NAME: response = douService.search(search); break;
            default: throw new UnknownSiteException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
