package com.dango.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.dango.model.PageParams;
import com.dango.search.dto.SearchCourseParamDto;
import com.dango.search.dto.SearchPageResultDto;
import com.dango.search.po.CourseIndex;
import com.dango.search.service.CourseSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dango
 * @version 1.0
 * @description 课程搜索service实现类
 * @date 2024-03-22
 */
@Slf4j
@Service
public class CourseSearchServiceImpl implements CourseSearchService {

    @Value("${elasticsearch.course.index}")
    private String courseIndexStore;
    @Value("${elasticsearch.course.source_fields}")
    private String sourceFields;

    @Autowired
    RestHighLevelClient client;

    /**
     * 设置搜索请求
     * 指定索引：通过SearchRequest对象指定要查询的Elasticsearch索引。
     * 构建查询：使用SearchSourceBuilder和BoolQueryBuilder构建复杂的布尔查询。
     * 字段过滤：指定哪些字段应该被包含在响应中。
     * 构建查询条件
     * 关键字搜索：如果提供了关键字，则使用MultiMatchQueryBuilder在多个字段上进行搜索，可以提升特定字段的权重。
     * 过滤条件：根据提供的参数（如类别、子类别等）添加过滤条件。
     * 分页
     * 计算起始点并设置分页参数，以控制返回结果的数量和分页。
     * 聚合
     * 使用buildAggregation方法添加聚合操作，用于生成分组统计数据，例如，按主类别和子类别进行聚合。
     * 执行搜索
     * 使用Elasticsearch客户端发送搜索请求并获取响应。
     * 处理搜索结果
     * 解析搜索命中的结果，将原始JSON转换为Java对象列表。
     * 提取并处理总命中数，用于分页。
     * 添加课程索引到结果列表。
     * 处理聚合结果
     * 从响应中获取聚合数据，提供对主类别和子类别的分组统计概览。
     * 返回搜索结果
     * 封装搜索结果（包括课程列表、总数、分页信息和聚合数据）到SearchPageResultDto对象，并返回。
     * @param pageParams 分页参数
     * @param courseSearchParam 搜索条件
     * @return
     */
    @Override
    public SearchPageResultDto<CourseIndex> queryCoursePubIndex(PageParams pageParams, SearchCourseParamDto courseSearchParam) {

        //设置索引
        SearchRequest searchRequest = new SearchRequest(courseIndexStore);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //source源字段过虑
        String[] sourceFieldsArray = sourceFields.split(",");
        searchSourceBuilder.fetchSource(sourceFieldsArray, new String[]{});
        if(courseSearchParam==null){
            courseSearchParam = new SearchCourseParamDto();
        }
        //关键字
        if(StringUtils.isNotEmpty(courseSearchParam.getKeywords())){
            //匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeywords(), "name", "description");
            //设置匹配占比
            multiMatchQueryBuilder.minimumShouldMatch("70%");
            //提升另个字段的Boost值
            multiMatchQueryBuilder.field("name",10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        //过虑
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("mainCategory",courseSearchParam.getMt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("subCategory",courseSearchParam.getSt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }
        //分页
        int pageNo = pageParams.getPageNo();
        int pageSize = pageParams.getPageSize();
        int start = (pageNo-1)*pageSize;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(Math.toIntExact(pageSize));
        //布尔查询
        searchSourceBuilder.query(boolQueryBuilder);
//        //高亮设置
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font class='eslight'>");
//        highlightBuilder.postTags("</font>");
//        //设置高亮字段
//        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
//        searchSourceBuilder.highlighter(highlightBuilder);
        //请求搜索
        searchRequest.source(searchSourceBuilder);
//        //聚合设置
        buildAggregation(searchRequest);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("课程搜索异常：{}",e.getMessage());
            return new SearchPageResultDto<CourseIndex>(new ArrayList(),0,0,0);
        }

        //结果集处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        //记录总数
        TotalHits totalHits = hits.getTotalHits();
        //数据列表
        List<CourseIndex> list = new ArrayList<>();

        for (SearchHit hit : searchHits) {

            String sourceAsString = hit.getSourceAsString();
            Gson gson = new Gson();
            CourseIndex courseIndex = JSON.parseObject(sourceAsString, CourseIndex.class);

//            //取出source
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

//            //课程id
//            Long id = courseIndex.getId();
//            //取出名称
//            String     name = courseIndex.getName();
//            //取出高亮字段内容
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            if(highlightFields!=null){
//                HighlightField nameField = highlightFields.get("name");
//                if(nameField!=null){
//                    Text[] fragments = nameField.getFragments();
//                    StringBuffer stringBuffer = new StringBuffer();
//                    for (Text str : fragments) {
//                        stringBuffer.append(str.string());
//                    }
//                    name = stringBuffer.toString();
//
//                }
//            }
//            courseIndex.setId(id);
//            courseIndex.setName(name);

            list.add(courseIndex);

        }
        SearchPageResultDto<CourseIndex> pageResult = new SearchPageResultDto<>(list, totalHits.value,pageNo,pageSize);

        //获取聚合结果
        List<String> mtList= getAggregation(searchResponse.getAggregations(), "mtAgg");
        List<String> stList = getAggregation(searchResponse.getAggregations(), "stAgg");

        pageResult.setMtList(mtList);
        pageResult.setStList(stList);

        return pageResult;
    }


    private void buildAggregation(SearchRequest request) {
        request.source().aggregation(AggregationBuilders
                .terms("mtAgg")
                .field("mainCategoryName")
                .size(100)
        );
        request.source().aggregation(AggregationBuilders
                .terms("stAgg")
                .field("subCategoryName")
                .size(100)
        );

    }

    private List<String> getAggregation(Aggregations aggregations, String aggName) {
        // 4.1.根据聚合名称获取聚合结果
        Terms brandTerms = aggregations.get(aggName);
        // 4.2.获取buckets
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        // 4.3.遍历
        List<String> brandList = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            // 4.4.获取key
            String key = bucket.getKeyAsString();
            brandList.add(key);
        }
        return brandList;
    }
}
