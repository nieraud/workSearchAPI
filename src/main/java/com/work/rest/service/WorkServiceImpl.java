package com.work.rest.service;

import com.work.rest.entity.*;
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
        return parseSearchDocument(document, search);
    }

    @Override
    public WorkDetailsResponce workDetails(WorkDetails workDetails) throws IOException {
        Document document = requestService.workDetails(ProjectConstants.WORK_UA_NAME, workDetails);
        if(document == null) {
            return null;
        }
        return parseWorkDetailsDocument(document, workDetails);
    }

    private SearchResponse parseSearchDocument(Document document, Search search) {
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
        String title = paginatioinLinks.get(paginatioinLinks.size()-2).attr("title");
        if(title != null && !title.isEmpty()) {
            response.setLastPage(parseIntegerOrNull(title));
        }

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

    private WorkDetailsResponce parseWorkDetailsDocument(Document document, WorkDetails workDetails){
        WorkDetailsResponce responce = new WorkDetailsResponce();

        Element divCard = document.select("div.card.wordwrap").first();
        if(divCard == null){
            return new WorkDetailsResponce(); // no one found
        }

        Element logoContainer = divCard.select("p.logo-job-container").first();
        if(logoContainer != null){
            String logoSrc = logoContainer.select("a").first().select("img").first().attr("src");
            if(logoSrc != null && !logoSrc.isEmpty()) {
                responce.setCompanyLogoSrc("https:" + logoSrc);
            }
        }

        String text = divCard.select("p.cut-bottom-print").first().select("span.text-muted").text();
        responce.setJobCreatedDate(text);

        String jobName = divCard.select("h1.add-top-sm").first().text();
        responce.setJobName(jobName);

        Element salaryEl = divCard.select("h3.text-muted.text-muted-print").first();
        if(salaryEl != null){
            responce.setJobSalary(salaryEl.text());
        }

        Elements infoDDList = divCard.select("dl.dl-horizontal").select("dd");
        Elements companyElement = infoDDList.get(0).select("a");

        Integer companyId = parseIntegerOrNull(companyElement.attr("href"));
        responce.setCompanyId(companyId);

        String companyName = companyElement.select("b").text();
        responce.setCompanyName(companyName);

        String contactPeople = infoDDList.get(1).text();
        responce.setCompanyContactPeople(contactPeople);

        String companyPhoneNumber = infoDDList.get(2).select("a").text();
        responce.setCompanyPhoneNumber(companyPhoneNumber);

        String city = infoDDList.get(3).text();
        responce.setJobCity(city);

        String employmentType = infoDDList.get(4).text();
        responce.setJobEmploymentType(employmentType);

        String requirements = infoDDList.get(5).text();
        responce.setJobRequirements(requirements);

        StringBuilder jobDescription = new StringBuilder();
        Elements point = divCard.select("h2").next();
        while (!point.is("div.form-group.hidden-print")){
            jobDescription.append(point.html()).append("\n");
            point = point.next();
        }
        responce.setJobDescription(jobDescription.toString());

        return responce;
    }

    private Integer parseIntegerOrNull(String string){
        Matcher matcher = Pattern.compile("(\\d+)").matcher(string);
        return matcher.find() ? Integer.parseInt(matcher.group(0)) : 0;
    }
}
