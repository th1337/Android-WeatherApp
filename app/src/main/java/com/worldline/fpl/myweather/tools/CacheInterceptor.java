package com.worldline.fpl.myweather.tools;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by a607937 on 15/06/2015.
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Add Cache Control only for GET methods
        if (request.method().equals("GET")) {
            request.newBuilder()
                    .header("Cache-Control", "only-if-cached")
                    .build();
        }

        Response response = chain.proceed(request);

        // Re-write response CC header to force use of cache
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=60") // 1 day
                .build();
    }
}
