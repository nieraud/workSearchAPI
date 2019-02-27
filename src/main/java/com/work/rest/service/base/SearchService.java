package com.work.rest.service.base;

import com.work.rest.entity.Search;
import com.work.rest.entity.SearchResponse;

import java.io.IOException;

public interface SearchService {
    SearchResponse search(Search search) throws IOException;
}
