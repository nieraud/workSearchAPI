package com.work.rest.service;

import com.work.rest.entity.Search;
import com.work.rest.entity.SearchResponse;
import com.work.rest.entity.Vacancy;
import com.work.rest.service.base.SearchService;
import com.work.rest.utils.ProjectConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WorkServiceImpl implements SearchService, WorkService {

    private final RequestService requestService;

    @Autowired
    public WorkServiceImpl(RequestService requestService) {
        this.requestService = requestService;
    }


    @Override
    public SearchResponse search(Search search) throws IOException {
        Document document = requestService.search(ProjectConstants.WORK_UA_NAME, search);
        if(document == null) {
            return null;
        }
        return parseDocument(document, search);
    }

    private SearchResponse parseDocument(Document document, Search search) {
        SearchResponse response = new SearchResponse();
        response.setCurrentPage(search.getPage());

        Element divCard = document.select("div.card").select(".add-top").first();
        if(divCard == null){
            return new SearchResponse(0, new ArrayList<Vacancy>());
        }
        Integer vacanciesCount = parseIntegerOrNull(divCard.text());
        response.setCountFound(vacanciesCount);

        Elements divResult = document.select("div#pjax-job-list").first().children();

        Elements paginatioinLinks = divResult.select("ul.pagination.hidden-xs").select("li").select("a");
        List<Integer> pages = new ArrayList<>();
        pages.add(search.getPage());
        for (Element link : paginatioinLinks) {
            String title = link.attr("title");
            if(title != null && !title.isEmpty()) {
                Integer page = parseIntegerOrNull(title);
                pages.add(page);
            }
        }
        Collections.sort(pages);
        response.setAvailablePages(pages);

        List<Vacancy> vacancyList = new ArrayList<>();
        for (int i = 0; i < divResult.size(); i+=2) {
            Vacancy vacancy = new Vacancy();

            while (i < divResult.size() && !divResult.get(i).tagName().equals("a")){
                i++;
            }
            if(divResult.size() == i) break;

            Integer id = Integer.valueOf(divResult.get(i).attr("name"));
            vacancy.setId(id);

            Element divVacancy = divResult.get(i+1);
            Element imgElement = divVacancy.select("img").first();
            if(imgElement != null) {
                String imgSrc = imgElement.attr("src");
                vacancy.setImgSrc("https:" + imgSrc);
            }

            Element vacancyA = divVacancy.select("h2").first().select("a").first();
            String vacancyName = vacancyA.text();
            vacancy.setName(vacancyName);

            String companyName = divVacancy.select("span").select("b").first().text();
            vacancy.setCompanyName(companyName);

            Element cityElement = divVacancy.select("span:contains( ·)").first();
            vacancy.setCity(cityElement.text().replace(" ·", ""));

            Element hotElement = divVacancy.select("span").select("span.label.label-hot").first();
            vacancy.setHot(hotElement != null);

            String description = divVacancy.select("p.overflow").text();
            vacancy.setDescription(description);

            vacancyList.add(vacancy);
        }
        response.setVacancies(vacancyList);
        return response;
    }

    private Integer parseIntegerOrNull(String string){
        Matcher matcher = Pattern.compile("(\\d+)").matcher(string);
        return matcher.find() ? Integer.parseInt(matcher.group(0)) : 0;
    }
}
