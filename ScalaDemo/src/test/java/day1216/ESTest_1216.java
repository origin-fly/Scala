package day1216;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName ESTest_1216
 * @Description  所有对ES操作都是通过client
 * @Author nieyafei
 * @Date 2019-12-16 20:18
 * Version 1.0
 **/
public class ESTest_1216 {

    //所有对ES操作都是通过client

    private TransportClient client;

    @SuppressWarnings("unchecked")
    @Before
    //用before就是在进行所有操作前都先执行这个方法
    public void getClinet(){

        //1.设置连接集群的名称
        Settings settings = Settings.builder().put("cluster.name","my-application").build();

        //2.连接集群
        client = new PreBuiltTransportClient(settings);
        try {
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.50.12.55"),9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    //创建索引
    public void  createIndex_blog(){
        client.admin().indices().prepareCreate("blog").get();
        client.close();
    }

    @Test
    //删除索引
    public void deleteIndex(){
        client.admin().indices().prepareDelete("blog").get();
        client.close();
    }

    //插入数据
    /**
     *@Author nieyafei
     *@Description   插入数据有3种方式 推荐使用第二种和第三种
     *@Date 2019/12/16  20:59
     *@Param []
     *@return void
     **/

    @Test
    public void createDocByJson(){
        //1.使用json 创建document 文档数据准备
        String json = "{" + "\"id\":\"1\"," + "\"title\":\"基于Lucene的搜索服务器\","
                + "\"content\":\"它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口\"" + "}";

        //2.创建文档
        IndexResponse indexResponse = client.prepareIndex("blog", "article", "1").setSource(json).execute().actionGet();

        // 3 打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("result:" + indexResponse.getResult());

        // 4 关闭连接
        client.close();
    }

    @Test
    //插入数据 相比于createDocByJson()，更推荐createDocByMap()方法
    public void createDocByMap(){

        Map<String,Object> json = new HashMap<String,Object>();
        json.put("id","2");
        json.put("title","基于Lucene的搜索服务器");
        json.put("content","它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口");


        //2.创建文档
        IndexResponse indexResponse = client.prepareIndex("blog", "article", "2").setSource(json).execute().actionGet();

        // 3 打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("result:" + indexResponse.getResult());
    }

    @Test
    //使用Elasticsearch提供的类封装 使用xcontent创建document
    public void createDocByXContent() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id","3")
                .field("title","基于Lucene的搜索服务器")
                .field("content","它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口")
                .endObject();

        //2.创建文档
        IndexResponse indexResponse = client.prepareIndex("blog", "article", "3").setSource(builder).execute().actionGet();

        // 3 打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("result:" + indexResponse.getResult());

        // 4 关闭连接
        client.close();
    }

    /**
     *@Author nieyafei
     *@Description ** 查询数据 根据索引查询有2种方式 单个索引和多个索引
     *                        根据条件查询有4种方式 1 类似于 like  2 类似于正则 3 类似于= 4 类似于模糊查询
     *@Date 2019/12/17  15:48
     *@Param []
     *@return void
     **/

    //查询数据
    //单个索引查询数据
    @Test
    public void getDataBySingleIndex(){
        //1.根据索引查询单个文档
        GetResponse getResponse = client.prepareGet("blog", "article", "2").get();
        //2.将结果转换成字符串
        System.out.println(getResponse.getSourceAsString());
        //3.关闭连接
        client.close();
    }

    //多个索引查询数据
    @Test
    public void getMultiDataBySingleIndex(){

        //1.查询多个document
        MultiGetResponse multiGetResponse = client.prepareMultiGet()
                .add("blog", "article", "1")
                .add("blog", "article", "2")
                .add("blog", "article", "3")
                .get();
        //2.遍历查询结果
        for (MultiGetItemResponse multiGetItemResponse : multiGetResponse) {
            GetResponse getResponse = multiGetItemResponse.getResponse();
            //如果获取到查询结果
            if(getResponse.isExists()){
                //转换字符串进行输出
                String sourceAsString = getResponse.getSourceAsString();
                System.out.println(sourceAsString);
            }
        }
        //3. 关闭连接
        client.close();
    }

    //类似于like查询 会对所有字段查询
    @Test
    public void query(){

        //1.执行查询 索引为index 类型为article 中所有字段有大数据的结果
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article")
                .setQuery(QueryBuilders.queryStringQuery("Lucene")).get();
        //2.打印查询结果
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询结果有："+hits.getTotalHits() + " 条");
        //3.遍历每条结果
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        //4.关闭连接
        client.close();
    }

    //类似于通配符查询 *分布式* 类似于 %分布式%
    //但是 是要与分词结果进行 %分布式%
    //* ：表示多个字符（0个或多个字符）
    //？：表示单个字符
    @Test
    public void wildcardQuery(){

        //1.执行条件查询
        SearchResponse searchResponse = client.prepareSearch("blog3")
                .setTypes("article")
                .setQuery(QueryBuilders.wildcardQuery("content", "*分布式*")).get();

        //2.打印查询结果 获取命中次数，查询结果有多少对
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询结果有："+hits.getTotalHits() + " 条");
        //3.遍历每条结果
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        //4.关闭连接
        client.close();
    }

    //类似于 oracle/mysql 中的=
    //不是与字段 = 是与字段的分词结果的 =

    @Test
    public void termQuery(){
        //1.执行条件查询
        SearchResponse searchResponse = client.prepareSearch("blog3")
                .setTypes("article")
                .setQuery(QueryBuilders.termQuery("content", "分布式")).get();

        //2.打印查询结果 获取命中次数，查询结果有多少对
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询结果有："+hits.getTotalHits() + " 条");
        //3.遍历每条结果
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        //4.关闭连接
        client.close();
    }

    //模糊查询 类似于 没有分词器 中文可能搜索不到
    @Test
    public void fuzzy(){
        //1.执行条件查询
        SearchResponse searchResponse = client.prepareSearch("blog3")
                .setTypes("article")
                .setQuery(QueryBuilders.fuzzyQuery("content", "分布式")).get();

        //2.打印查询结果 获取命中次数，查询结果有多少对
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询结果有："+hits.getTotalHits() + " 条");
        //3.遍历每条结果
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        //4.关闭连接
        client.close();
    }



    //更新数据
    //根据id 进行更新数据
    @Test
    public void updateDataBySingleIndex() throws IOException, ExecutionException, InterruptedException {

        //1.创建更新数据的请求对象
        UpdateRequest updateRequest = new UpdateRequest();
        //2.添加更新对象的where条件
        updateRequest.index("blog");
        updateRequest.type("article");
        updateRequest.id("3");

        //3.准备需要更新的数据,进行更新
        updateRequest.doc(
                // 对没有的字段添加, 对已有的字段替换
                XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "基于Lucene的搜索服务器")
                        .field("content","它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。大数据前景无限")
                        .field("createDate", "2019-12-17")
                        .endObject()

        );

        //4.获取更新后的document
        UpdateResponse indexResponse = client.update(updateRequest).get();

        //5.打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("create:" + indexResponse.getResult());

        //6.关闭连接
        client.close();
    }

    //更新文档数据 upsert 如果where条件存在就更新 不存在则插入
    @Test
    public void upsertData() throws IOException, ExecutionException, InterruptedException {

        //1.设置查询条件, 查找不到则添加
        IndexRequest indexRequest = new IndexRequest("blog", "article", "4")
                .source(
                        XContentFactory.jsonBuilder()
                                .startObject()
                                .field("title", "搜索服务器")
                                .field("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。" +
                                        "Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。" +
                                        "设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。")
                                .endObject()
                );

        //2.设置更新, 查找到更新下面的设置
        UpdateRequest upsert = new UpdateRequest("blog", "article", "4")
                .doc(
                        XContentFactory.jsonBuilder().startObject().field("user", "李四").endObject()
                ).upsert(indexRequest);

        //3.执行更新
        client.update(upsert).get();
        //4.关闭连接
        client.close();
    }

    //删除文档数据
    //根据单个索引删除
    @Test
    public void DeleteDataBySingleIndex(){
        //1.根据索引删除文档数据
        DeleteResponse indexResponse = client.prepareDelete("blog", "article", "5").get();
        //2.打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("found:" + indexResponse.getResult());
        //3.关闭连接
        client.close();
    }

    //mapping 类似于表结构
    //当index有数据时，不能创建mapping 类似于oracle
    @Test
    public void createMapping() throws IOException, ExecutionException, InterruptedException {
        //1.设置mapping
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("article")
                        .startObject("properties")
                            .startObject("id")
                                .field("type","text")
                                .field("store","true")
                            .endObject()
                            .startObject("title")
                                .field("type","text")
                                .field("store","true")
                            .endObject()
                            .startObject("content")
                                .field("type","text")
                                .field("store","false")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();


        //2.添加mapping
        PutMappingRequest mapping = Requests.putMappingRequest("blog2")
                .type("article").source(builder);

        client.admin().indices().putMapping(mapping).get();

        //3.关闭连接
        client.close();
    }

    //指定mapping的ik分词器
    @Test
    public void createMapping_ik() throws IOException, ExecutionException, InterruptedException {

        //1.设置mapping
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("article")
                .startObject("properties")
                .startObject("id")
                .field("type","text")
                .field("store","true")
                .field("analyzer","ik_smart")
                .endObject()
                .startObject("title")
                .field("type","text")
                .field("store","true")
                .field("analyzer","ik_smart")
                .endObject()
                .startObject("content")
                .field("type","text")
                .field("store","false")
                .field("analyzer","ik_smart")
                .endObject()
                .endObject()
                .endObject()
                .endObject();


        //2.添加mapping
        PutMappingRequest mapping = Requests.putMappingRequest("blog3")
                .type("article").source(builder);

        client.admin().indices().putMapping(mapping).get();

        //3.关闭连接
        client.close();
    }

    //分词器插入数据

    @Test
    public void createDocByJson_ik(){
        //1.使用json 创建document 文档数据准备
        String json = "{" + "\"id\":\"1\"," + "\"title\":\"基于Lucene的搜索服务器\","
                + "\"content\":\"它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口\"" + "}";

        //2.创建文档
        IndexResponse indexResponse = client.prepareIndex("blog3", "article", "1").setSource(json).execute().actionGet();

        // 3 打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("result:" + indexResponse.getResult());

        // 4 关闭连接
        client.close();
    }

    @Test
    //插入数据 相比于createDocByJson()，更推荐createDocByMap()方法
    public void createDocByMap_ik(){

        Map<String,Object> json = new HashMap<String,Object>();
        json.put("id","2");
        json.put("title","基于Lucene的搜索服务器");
        json.put("content","它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口");


        //2.创建文档
        IndexResponse indexResponse = client.prepareIndex("blog3", "article", "2").setSource(json).execute().actionGet();

        // 3 打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("result:" + indexResponse.getResult());
    }

    @Test
    //使用Elasticsearch提供的类封装 使用xcontent创建document
    public void createDocByXContent_ik() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id","3")
                .field("title","基于Lucene的搜索服务器")
                .field("content","它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口")
                .endObject();

        //2.创建文档
        IndexResponse indexResponse = client.prepareIndex("blog3", "article", "3").setSource(builder).execute().actionGet();

        // 3 打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("result:" + indexResponse.getResult());

        // 4 关闭连接
        client.close();
    }

    @Test
    public void upsertData_ik() throws IOException, ExecutionException, InterruptedException {

        //1.设置查询条件, 查找不到则添加
        IndexRequest indexRequest = new IndexRequest("blog3", "article", "4")
                .source(
                        XContentFactory.jsonBuilder()
                                .startObject()
                                .field("id","4")
                                .field("title", "搜索服务器")
                                .field("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。" +
                                        "Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。" +
                                        "设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。")
                                .endObject()
                );

        //2.设置更新, 查找到更新下面的设置
        UpdateRequest upsert = new UpdateRequest("blog3", "article", "4")
                .doc(
                        XContentFactory.jsonBuilder().startObject().field("user", "李四").endObject()
                ).upsert(indexRequest);

        //3.执行更新
        client.update(upsert).get();
        //4.关闭连接
        client.close();
    }

}
