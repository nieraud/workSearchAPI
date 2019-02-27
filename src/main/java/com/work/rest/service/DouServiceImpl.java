package com.work.rest.service;

import com.work.rest.entity.Search;
import com.work.rest.entity.SearchResponse;
import com.work.rest.service.base.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DouServiceImpl implements SearchService, DouService {
    @Override
    public SearchResponse search(Search search) {
        return null;
    }
}
