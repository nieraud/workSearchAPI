package com.work.rest.service.base;

import com.work.rest.entity.Search;
import com.work.rest.entity.SearchResponse;
import com.work.rest.entity.WorkDetails;
import com.work.rest.entity.WorkDetailsResponce;

import java.io.IOException;

public interface SearchService {
    SearchResponse search(Search search) throws IOException;
    WorkDetailsResponce workDetails(WorkDetails workDetails) throws IOException;
}
