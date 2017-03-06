package com.aliex.commonlib.net.api;

import android.content.Context;

import com.aliex.commonlib.net.interceptor.HeadersInterceptor;
import com.aliex.commonlib.net.mode.ApiHost;
import com.aliex.commonlib.utils.Config;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: Aliex <br/>
 * created on: 2017/3/6 <br/>
 * description: <br/>
 */

public class GalaxyApi {
    private static Context mContext;
    private static ApiService apiService;
    private static Retrofit retrofit;
    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient okHttpClient;
    private static OkHttpClient.Builder okHttpClientBuilder;

    public GalaxyApi() {
    }

    public static final class Builder {
        private okhttp3.Call.Factory callFactory;
        private Converter.Factory converterFactory;
        private CallAdapter.Factory callAdapterFactory;
        private String baseUrl;

        public Builder(Context context) {
            mContext = context;
            retrofitBuilder = new Retrofit.Builder();
            okHttpClientBuilder = new OkHttpClient.Builder();
        }

        public GalaxyApi.Builder headers(Map<String, String> headers) {
            okHttpClientBuilder.addInterceptor(new HeadersInterceptor(headers));
            return this;
        }

        public GalaxyApi.Builder parameters(Map<String, String> parameters) {
            okHttpClientBuilder.addInterceptor(new HeadersInterceptor(parameters));
            return this;
        }

        public GalaxyApi.Builder interceptor(Interceptor interceptor) {
            okHttpClientBuilder.addInterceptor(checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

        public GalaxyApi.Builder networkInterceptor(Interceptor interceptor) {
            okHttpClientBuilder.addNetworkInterceptor(checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

        public GalaxyApi.Builder cacheKey(String cacheKey) {

            return this;
        }

        public GalaxyApi.Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public GalaxyApi.Builder client(OkHttpClient client) {
            retrofitBuilder.client(checkNotNull(client, "client == null"));
            return this;
        }

        public GalaxyApi.Builder callFactory(Call.Factory factory) {
            this.callFactory = checkNotNull(factory, "factory == null");
            return this;
        }

        public GalaxyApi.Builder converterFactory(Converter.Factory factory) {
            this.converterFactory = checkNotNull(factory, "factory == null");
            return this;
        }

        public GalaxyApi.Builder callAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterFactory = checkNotNull(factory, "factory == null");
            return this;
        }

        public GalaxyApi.Builder connectTimeout(int timeout) {
            connectTimeout(timeout, TimeUnit.SECONDS);
            return this;
        }

        public GalaxyApi.Builder readTimeout(int timeout) {
            readTimeout(timeout, TimeUnit.SECONDS);
            return this;
        }

        public GalaxyApi.Builder writeTimeout(int timeout) {
            writeTimeout(timeout, TimeUnit.SECONDS);
            return this;
        }

        public GalaxyApi.Builder connectTimeout(int timeout, TimeUnit unit) {
            if (timeout > -1) {
                okHttpClientBuilder.connectTimeout(timeout, unit);
            } else {
                okHttpClientBuilder.connectTimeout(Config.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        public GalaxyApi.Builder readTimeout(int timeout, TimeUnit unit) {
            if (timeout > -1) {
                okHttpClientBuilder.readTimeout(timeout, unit);
            } else {
                okHttpClientBuilder.readTimeout(Config.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        public GalaxyApi.Builder writeTimeout(int timeout, TimeUnit unit) {
            if (timeout > -1) {
                okHttpClientBuilder.writeTimeout(timeout, unit);
            } else {
                okHttpClientBuilder.writeTimeout(Config.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        public GalaxyApi build() {

            if (okHttpClientBuilder == null) {
                throw new NullPointerException("OkHttpClientBuilder is required.");
            }

            if (retrofitBuilder == null) {
                throw new NullPointerException("RetrofitBuilder is required.");
            }

            if (baseUrl == null) {
                baseUrl = ApiHost.getHost();
            }
            retrofitBuilder.baseUrl(baseUrl);

            if (converterFactory == null) {
                converterFactory = GsonConverterFactory.create();
            }
            retrofitBuilder.addConverterFactory(converterFactory);

            if (callAdapterFactory == null) {
                callAdapterFactory = RxJavaCallAdapterFactory.create();
            }
            retrofitBuilder.addCallAdapterFactory(callAdapterFactory);

            if (callFactory != null) {
                retrofitBuilder.callFactory(callFactory);
            }

            okHttpClient = okHttpClientBuilder.build();

            retrofitBuilder.client(okHttpClient);
            retrofit = retrofitBuilder.build();
            apiService = retrofit.create(ApiService.class);
            return new GalaxyApi();
        }

    }

    private static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
