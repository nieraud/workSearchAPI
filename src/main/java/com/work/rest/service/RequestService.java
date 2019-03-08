package com.work.rest.service;

import com.work.rest.entity.Search;
import com.work.rest.entity.WorkDetails;
import org.jsoup.nodes.Document;

import java.io.IOException;

public interface RequestService {
    Document search(String siteName, Search search) throws IOException;

    Document workDetails(String siteName, WorkDetails workDetails) throws IOException;
}
