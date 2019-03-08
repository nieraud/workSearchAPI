package com.work.rest.service;

import com.work.rest.entity.Search;
import com.work.rest.entity.WorkDetails;
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
            case ProjectConstants.WORK_UA_NAME : return getWorkSearch(search);
            case ProjectConstants.DOU_NAME : return getDouSearch(search);
            case ProjectConstants.RABOTA_UA_NAME : return getRabotaSearch(search);
            default: return null;
        }
    }

    @Override
    public Document workDetails(String siteName, WorkDetails workDetails) throws IOException {
        switch (siteName) {
            case ProjectConstants.WORK_UA_NAME : return getWorkDetailDocument(workDetails);
            case ProjectConstants.DOU_NAME : return getDouDetailDocument(workDetails);
            case ProjectConstants.RABOTA_UA_NAME : return getRabotaDetailDocument(workDetails);
            default: return null;
        }
    }

    private Document getRabotaSearch(Search search) {
        return null;
    }

    private Document getDouSearch(Search search) {
        return null;
    }

    private Document getWorkSearch(Search search) throws IOException {
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

    private Document getDouDetailDocument(WorkDetails workDetails) {
        return null;
    }

    private Document getRabotaDetailDocument(WorkDetails workDetails) {
        return null;
    }

    private Document getWorkDetailDocument(WorkDetails workDetails) throws IOException {
        String url = "https://www.work.ua/jobs/";

        if(workDetails.getJobId() == null) {
            throw new UnsupportedOperationException();
        }
        url += workDetails.getJobId();

        if(workDetails.getLang().equals(ProjectConstants.RU_LANG)) {
            url += "?setlp=ru";
        }

        return Jsoup.connect(url).userAgent("Mozilla").timeout(4000).get();
    }


}
