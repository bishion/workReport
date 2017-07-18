package com.bizi.report.utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * https链接安全限制
 *
 * @author minglong.zhang
 */

/**
  * http请求工具类
  */
@Slf4j
public class HttpUtil {

    private static MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
    private static HttpClient                         client;
    static {
        //每主机最大连接数
        Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
        client = new HttpClient(httpConnectionManager);
        client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(32);
        //总最大连接数
        client.getHttpConnectionManager().getParams().setMaxTotalConnections(256);
        //超时时间 3sec
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        client.getHttpConnectionManager().getParams().setSoTimeout(3000);
    }
    public static HttpClient getHttpClient() {
        return client;
    }
    /**
      * 用法： HttpRequestProxy hrp = new HttpRequestProxy();
      * hrp.doRequest("http://www.163.com",null,null,"utf-8");
      *
      * @param url 请求的资源ＵＲＬ
      * @param postData POST请求时form表单封装的数据 没有时传null
      * @param header request请求时附带的头信息(header) 没有时传null
      * @param encoding response返回的信息编码格式 没有时传null
      * @return response返回的文本数据
      * @throws Exception
      */
            @SuppressWarnings("rawtypes")
    public static String doRequest(String url, Map postData, Map header, String encoding) throws Exception {
        String responseString = null;
        //头部请求信息 
        Header[] headers = initHeader(header);
        if (postData != null) {
            //post方式请求
            responseString = executePost(url, postData, encoding, headers);
        } else {
            //get方式 请求
            responseString = executeGet(url, encoding, headers);
        }
        return responseString;
    }
    //get方式 请求
            private static String executeGet(String url, String encoding, Header[] headers) throws Exception {
        String responseString = "";
        GetMethod getRequest = new GetMethod(url.trim());
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                getRequest.setRequestHeader(headers[i]);
            }
        }
        try {
             log.debug("BexecuteGet=" + url + ", object headers:"
                                        + ToStringBuilder.reflectionToString(headers, ToStringStyle.SHORT_PREFIX_STYLE).toString());
            responseString = executeMethod(getRequest, encoding);
             log.debug("BexecuteGet=" + url + ", object headers:"
                                        + ToStringBuilder.reflectionToString(headers, ToStringStyle.SHORT_PREFIX_STYLE).toString());
        } catch (Exception e) {
             log.warn("executeGet error" + url, e);
            throw e;
        } finally {
            getRequest.releaseConnection();
        }
        return responseString;
    }
    //post方式请求
            private static String executePost(String url, @SuppressWarnings("rawtypes") Map postData, String encoding,
                                      Header[] headers) throws Exception {
        String responseString = "";
        PostMethod postRequest = new PostMethod(url.trim());
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                postRequest.setRequestHeader(headers[i]);
            }
        }
        @SuppressWarnings("rawtypes")
        Set entrySet = postData.entrySet();
        int dataLength = entrySet.size();
        NameValuePair[] params = new NameValuePair[dataLength];
        int i = 0;
        for (@SuppressWarnings("rawtypes")
        Iterator itor = entrySet.iterator(); itor.hasNext();) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) itor.next();
            params[i++] = new NameValuePair(entry.getKey().toString(), entry.getValue().toString());
        }
        postRequest.setRequestBody(params);
        try {
            //             log.debug("BexecutePost=" + url + ", object postData:"
            //                    + ToStringBuilder.reflectionToString(params, ToStringStyle.SHORT_PREFIX_STYLE).toString());
            responseString = executeMethod(postRequest, encoding);
            //             log.debug("EexecutePost=" + url + ", object postData:"
            //                    + ToStringBuilder.reflectionToString(params, ToStringStyle.SHORT_PREFIX_STYLE).toString());
        } catch (Exception e) {
            throw e;
        } finally {
            postRequest.releaseConnection();
        }
        return responseString;
    }
    //请求头部信息
            @SuppressWarnings("rawtypes")
    private static Header[] initHeader(Map header) {
        Header[] headers = null;
        if (header != null) {
            Set entrySet = header.entrySet();
            int dataLength = entrySet.size();
            headers = new Header[dataLength];
            int i = 0;
            for (Iterator itor = entrySet.iterator(); itor.hasNext();) {
                Map.Entry entry = (Map.Entry) itor.next();
                headers[i++] = new Header(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return headers;
    }
    //调用并获取返回
            private static String executeMethod(HttpMethod request, String encoding) throws Exception {
        String responseContent = null;
        InputStream responseStream = null;
        BufferedReader rd = null;
        try {
            Long start = System.currentTimeMillis();
            //             log.debug("B. request param:"
            //                    + ToStringBuilder.reflectionToString(request, ToStringStyle.SHORT_PREFIX_STYLE).toString());
            request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
            getHttpClient().executeMethod(request);
            //             log.debug("E. cost " + (System.currentTimeMillis() - start) + "msec, request param:"
            //                    + ToStringBuilder.reflectionToString(request, ToStringStyle.SHORT_PREFIX_STYLE).toString());
            if (encoding != null) {
                responseStream = request.getResponseBodyAsStream();
                rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
                String tempLine = rd.readLine();
                StringBuffer tempStr = new StringBuffer();
                String crlf = System.getProperty("line.separator");
                while (tempLine != null) {
                    tempStr.append(tempLine);
                    tempStr.append(crlf);
                    tempLine = rd.readLine();
                }
                responseContent = tempStr.toString();
            } else {
                responseContent = request.getResponseBodyAsString();
            }
            Header locationHeader = request.getResponseHeader("location");
            //返回代码为302,301时，表示页面己经重定向，则重新请求location的url，这在 
            //一些登录授权取cookie时很重要
            //如果需要处理重定向请求，请在下面代码中改造
            if (locationHeader != null) {
                //String redirectUrl = locationHeader.getValue();
                //doRequest(redirectUrl, null, null, null);
            }
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (rd != null)
                try {
                    rd.close();
                } catch (IOException e) {
                    throw e;
                }
            if (responseStream != null)
                try {
                    responseStream.close();
                } catch (IOException e) {
                    throw e;
                }
        }
        return responseContent;
    }
    /**
      * 特殊请求数据,这样的请求往往会出现redirect本身而出现递归死循环重定向 所以单独写成一个请求方法
      * 比如现在请求的url为：http://localhost:8080/demo/index.jsp 返回代码为302
      * 头部信息中location值为:http://localhost:8083/demo/index.jsp
      * 这时httpclient认为进入递归死循环重定向，抛出CircularRedirectException异常
      *
      * @param url
      * @return
      * @throws Exception
      */
            public String doSpecialRequest(String url, int count, String encoding) throws Exception {
        String str = null;
        InputStream responseStream = null;
        BufferedReader rd = null;
        GetMethod getRequest = new GetMethod(url);
        //关闭httpclient自动重定向动能 
        getRequest.setFollowRedirects(false);
        try {
            client.executeMethod(getRequest);
            Header header = getRequest.getResponseHeader("location");
            if (header != null) {
                //请求重定向后的ＵＲＬ，count同时加1 
                this.doSpecialRequest(header.getValue(), count + 1, encoding);
            }
            //这里用count作为标志位，当count为0时才返回请求的ＵＲＬ文本, 
            //这样就可以忽略所有的递归重定向时返回文本流操作，提高性能 
            if (count == 0) {
                getRequest = new GetMethod(url);
                getRequest.setFollowRedirects(false);
                client.executeMethod(getRequest);
                responseStream = getRequest.getResponseBodyAsStream();
                rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
                String tempLine = rd.readLine();
                StringBuilder tempStr = new StringBuilder();
                String crlf = System.getProperty("line.separator");
                while (tempLine != null) {
                    tempStr.append(tempLine);
                    tempStr.append(crlf);
                    tempLine = rd.readLine();
                }
                str = tempStr.toString();
            }
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            getRequest.releaseConnection();
            if (rd != null)
                try {
                    rd.close();
                } catch (IOException e) {
                    throw e;
                }
            if (responseStream != null)
                try {
                    responseStream.close();
                } catch (IOException e) {
                    throw e;
                }
        }
        return str;
    }
}

@Slf4j
class MySecureProtocolSocketFactory implements SecureProtocolSocketFactory {
    private SSLContext          sslcontext = null;
    private SSLContext createSSLContext() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
             log.warn("createSSLContext error", e);
        } catch (KeyManagementException e) {
             log.warn("createSSLContext error.", e);
        }
        return sslcontext;
    }
    private SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createSSLContext();
        }
        return this.sslcontext;
    }
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
            UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException,
            UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort,
                               HttpConnectionParams params) throws IOException, UnknownHostException,
            ConnectTimeoutException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        SocketFactory socketfactory = getSSLContext().getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteaddr = new InetSocketAddress(host, port);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
            return socket;
        }
    }
    // 自定义私有类
            private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
}