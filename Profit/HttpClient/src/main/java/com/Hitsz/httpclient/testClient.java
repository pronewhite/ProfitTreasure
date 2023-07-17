package com.Hitsz.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class testClient {
    public static void main(String[] args) {
      //testGet();
        testPost();
    }

    /**
     * 使用HttpClient发送Get请求
     */
    public static void testGet(){
        String url = "https://restapi.amap.com/v3/ip?key=0113a13c88697dcea6a445584d535837&ip=60.25.188.64";
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //使用Get请求，创建HttpGet对象
        HttpGet httpGet =  new HttpGet(url);

        try {
            //使用client发请求,CloseableHttpResponse表示响应结果
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //根据结果获取数据,response.getEntity()返回的是一个JSON格式的数据，使用EntityUtils工具转化为字符串
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("访问结果是："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 使用HttpClient发送post请求
     */
    public static void testPost(){

        String url = "https://restapi.amap.com/v3/ip";
        //创建client对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        //给post请求指定请求的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key","0113a13c88697dcea6a445584d535837"));
        params.add(new BasicNameValuePair("ip","127.0.0.1"));
        try{
            //添加其他的参数
            HttpEntity entity = new UrlEncodedFormEntity(params);
            httpPost.setEntity(entity);

            //执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //获取数据
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("post请求结果是："+result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
