package com.work.rest.service;

import com.work.rest.entity.Search;
import com.work.rest.utils.ProjectConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Service
public class RequestServiceImpl implements RequestService {
    @Override
    public Document search(@NotNull String siteName, @NotNull Search search) throws IOException {
        switch (siteName) {
            case ProjectConstants.WORK_UA_NAME : return parseWorkUa(search);
            case ProjectConstants.DOU_NAME : return parseDou(search);
            case ProjectConstants.RABOTA_UA_NAME : return parseRabotaUa(search);
            default: return null;
        }
    }

    private Document parseRabotaUa(Search search) {
        return null;
    }

    private Document parseDou(Search search) {
        return null;
    }

    // jobs/id - for detail vacancy
    private Document parseWorkUa(Search search) throws IOException {
        String url = "https://www.work.ua/";
        if(search.getLang().equals("ru")){
            url += "ru/";
        }
        url += "jobs";

        if(search.getRegion().isEmpty() && search.getName().isEmpty() && search.getSort().isEmpty() && search.getPage() == 1){
            url += "/?ss=1";
            return Jsoup.connect(url).userAgent("Mozilla").timeout(4000).get();
        } else if(!search.getRegion().isEmpty()){
            url += "-" + search.getRegion();
        }

        if(!search.getName().isEmpty()){
           url += "-" + search.getName().replace(" ", "+");
        }

        if(!search.getSort().isEmpty()){
            url += "?sort=" + search.getSort();
        }

        if(search.getPage() != 1){
            if(url.contains("sort")) {
                url += "&page=" + search.getPage();
            } else {
                url += "?page=" + search.getPage();
            }
        }

        return Jsoup.connect(url).userAgent("Mozilla").timeout(4000).get();
    }
}
