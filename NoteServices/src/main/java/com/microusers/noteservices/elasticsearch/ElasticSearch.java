package com.microusers.noteservices.elasticsearch;

import com.microusers.noteservices.exception.NoteException;
import com.microusers.noteservices.model.NoteDetailsModel;
import com.microusers.noteservices.model.UserDetailsModel;
import com.microusers.noteservices.utils.Token;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class ElasticSearch implements IElasticSearch{

    String INDEX = "notedb";
    String TYPE = "notetype";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    Token jwrToken;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public NoteDetailsModel addNewNotes(NoteDetailsModel noteDetailsModel) {
        @SuppressWarnings("unchecked")
        Map<String, Object> dataMap = objectMapper.convertValue(noteDetailsModel, Map.class);
        String uuid = noteDetailsModel.getNoteId().toString();
        System.out.println("UUID TO STRING :"+uuid);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, uuid).source(dataMap);

        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return noteDetailsModel;
    }

    @Override
    public NoteDetailsModel updateNote(NoteDetailsModel noteDetailsModel) {

        @SuppressWarnings("unchecked")
        Map<String, Object> dataMap = objectMapper.convertValue(noteDetailsModel, Map.class);
        String uuid = noteDetailsModel.getNoteId().toString();
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, uuid);
        updateRequest.doc(dataMap);
        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return noteDetailsModel;
    }

    @Override
    public void deleteNote(UUID NoteId) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, NoteId.toString());
        try {
            client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;

    }


    @Override
    public List<NoteDetailsModel> searchData() {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse=null;
        try {
            searchResponse =
                    client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return  getSearchResult(searchResponse);
    }




    private List<NoteDetailsModel> getSearchResult(SearchResponse response) {

        SearchHit[] searchHit = response.getHits().getHits();

        List<NoteDetailsModel> notes = new ArrayList<>();

        if (searchHit.length > 0) {

            Arrays.stream(searchHit)
                    .forEach(hit -> notes
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                            NoteDetailsModel.class))
                    );
        }

        return notes;
    }

    @Override
    public List<NoteDetailsModel> searchall(String query, String token) {
        UserDetailsModel userDetailsModel = findUser(token);
        UUID userId = userDetailsModel.getUserId();
        SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("*" + query + "*")
                .analyzeWildcard(true).field("title").field("description"))
                .filter(QueryBuilders.termsQuery("userId", String.valueOf(userId)));
        System.out.println(queryBuilder);
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);
        } catch (IOException e) {

            e.printStackTrace();
        }

        List<NoteDetailsModel> allnote = getSearchResult(searchResponse);

        return allnote;
    }





    private UserDetailsModel findUser(String token) {

        UserDetailsModel userDetailsModel = restTemplate.
                getForObject("http://localhost:8081/user/getUserRedish?token="+token,
                        UserDetailsModel.class);


        if(userDetailsModel == null){
            throw new NoteException(NoteException.ExceptionType.USER_NOT_PRESENT);
        }

        return userDetailsModel;
    }
}
