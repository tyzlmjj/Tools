package me.majiajie.httprequest.http;


import java.util.Map;

interface IRequest<T>
{

    /**
     * 在当前线程执行
     */
    Result execute();

    /**
     * 异步队列形式执行
     */
    void enqueue(final HttpCallBack callBack);

    /**
     * 添加请求头信息
     * @param head
     * @return
     */
    T head(Map<String,String> head);

    /**
     * 添加传递的参数
     */
    T params(Map<String,String> params);



}
