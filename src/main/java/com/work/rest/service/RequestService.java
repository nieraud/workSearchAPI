package com.work.rest.service;

import com.work.rest.entity.Search;
import org.jsoup.nodes.Document;

import java.io.IOException;

public interface RequestService {
    Document search(String siteName, Search search) throws IOException;
}
