package com.caspar.ocr.common.http;

import com.caspar.ocr.common.enums.HttpMethod;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-13
 */
public class OkHttpSender {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpSender.class);

    public static String get(String url) {
        Request request = new Request.Builder().url(url).get().build();
        return send(request);
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, params, null);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        final Request.Builder requestBuilder = new Request.Builder();
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach((k, v) -> formBodyBuilder.add(k, v));
        }

        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach((k, v) -> requestBuilder.addHeader(k, v));
        }

        final FormBody requestBody = formBodyBuilder.build();
        Request request = requestBuilder.url(url).method(HttpMethod.POST.name(), requestBody).build();
        return send(request);
    }

    public static String post(String url, String json, Map<String, String> headers) {
        final Request.Builder requestBuilder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach((k, v) -> requestBuilder.addHeader(k, v));
        }

        Request request = requestBuilder.url(url)
                .method(HttpMethod.POST.name(), RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                .build();
        return send(request);
    }

    private static String send(Request request) {
        return send(request, OkHttpBuilder.getDefaultClient());
    }

    private static String send(Request request, OkHttpClient okHttpClient) {
        Response response = null;
        ResponseBody responseBody = null;

        try {
            response = okHttpClient.newCall(request).execute();
            responseBody = response.body();
            if (!response.isSuccessful()) {
                throw new Exception("Unexpected code " + response);
            }

            return responseBody.string();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }

            if (response != null) {
                response.close();
            }
        }

        return null;
    }

}
