package com.aliex.commonlib.net.api;

import android.content.Context;

import com.aliex.commonlib.assist.ClassUtil;
import com.aliex.commonlib.assist.SSLUtil;
import com.aliex.commonlib.cache.DiskCache;
import com.aliex.commonlib.net.callback.ApiCallback;
import com.aliex.commonlib.net.core.ApiCache;
import com.aliex.commonlib.net.core.ApiCookie;
import com.aliex.commonlib.net.exception.ApiException;
import com.aliex.commonlib.net.func.ApiDataFunc;
import com.aliex.commonlib.net.func.ApiErrFunc;
import com.aliex.commonlib.net.func.ApiFunc;
import com.aliex.commonlib.net.func.ApiResultFunc;
import com.aliex.commonlib.net.interceptor.GzipRequestInterceptor;
import com.aliex.commonlib.net.interceptor.HeadersInterceptor;
import com.aliex.commonlib.net.interceptor.OfflineCacheInterceptor;
import com.aliex.commonlib.net.interceptor.OnlineCacheInterceptor;
import com.aliex.commonlib.net.mode.ApiCode;
import com.aliex.commonlib.net.mode.ApiHost;
import com.aliex.commonlib.net.mode.ApiResult;
import com.aliex.commonlib.net.mode.CacheMode;
import com.aliex.commonlib.net.mode.CacheResult;
import com.aliex.commonlib.net.subscriber.ApiCallbackSubscriber;
import com.aliex.commonlib.utils.Config;

import java.io.File;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: Aliex <br/>
 * created on: 2017/3/6 <br/>
 * description: <br/>
 */

public class GalaxyApi {
    private static Context mContext;
    private static BaseApiService apiService;
    private static Retrofit retrofit;
    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient okHttpClient;
    private static OkHttpClient.Builder okHttpClientBuilder;
    private static ApiCache apiCache;
    private static ApiCache.Builder apiCacheBuilder;
    private static CacheMode cacheMode = CacheMode.ONLY_REMOTE;

    public GalaxyApi() {
    }

    /**
     * 可传入自定义的接口服务
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * 由外部设置被观察者
     *
     * @param observable
     * @param <T>
     * @return
     */
    public <T> Observable<T> call(Observable<T> observable) {
        return observable.compose(new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(new ApiErrFunc<T>());
            }
        });
    }

    /**
     * 普通Get方式请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> get(String url, Map<String, String> maps, Class<T> clazz) {
        return apiService.get(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 普通Get方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription get(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.get(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 带缓存Get方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<CacheResult<T>> cacheGet(final String url, final Map<String, String> maps, Class<T> clazz) {
        return this.get(url, maps, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 带缓存Get方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，无需订阅，只需配置Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription cacheGet(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.cacheGet(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 普通POST方式请求，需传入实体类
     *
     * @param url
     * @param parameters
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> post(final String url, final Map<String, String> parameters, Class<T> clazz) {
        return apiService.post(url, parameters).compose(this.norTransformer(clazz));
    }

    /**
     * 普通POST方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription post(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.post(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 带缓存POST方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<CacheResult<T>> cachePost(final String url, final Map<String, String> maps, Class<T> clazz) {
        return this.post(url, maps, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 带缓存POST方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，无需订阅，只需配置Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription cachePost(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.cachePost(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 提交表单方式请求，需传入实体类
     *
     * @param url
     * @param fields
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> form(final String url, final @FieldMap(encoded = true) Map<String, Object> fields,
            Class<T> clazz) {
        return apiService.postForm(url, fields).compose(this.norTransformer(clazz));
    }

    /**
     * 提交表单方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param fields
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription form(final String url, final @FieldMap(encoded = true) Map<String, Object> fields,
            ApiCallback<T> callback) {
        return this.form(url, fields, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 提交Body方式请求，需传入实体类
     *
     * @param url
     * @param body
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> body(final String url, final Object body, Class<T> clazz) {
        return apiService.postBody(url, body).compose(this.norTransformer(clazz));
    }

    /**
     * 提交Body方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param body
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription body(final String url, final Object body, ApiCallback<T> callback) {
        return this.body(url, body, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 删除信息请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> delete(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.delete(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 删除信息请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription delete(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.delete(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 修改信息请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> put(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.put(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 修改信息请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription put(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.put(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 上传图片，需传入请求body和实体类
     *
     * @param url
     * @param requestBody
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadImage(String url, RequestBody requestBody, Class<T> clazz) {
        return apiService.uploadImage(url, requestBody).compose(this.norTransformer(clazz));
    }

    /**
     * 上传图片，需传入图片文件和实体类
     *
     * @param url
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadImage(String url, File file, Class<T> clazz) {
        return apiService
                .uploadImage(url, RequestBody.create(okhttp3.MediaType.parse("image/jpg; " + "charset=utf-8"), file))
                .compose(this.norTransformer(clazz));
    }

    /**
     * 上传文件
     *
     * @param url
     * @param requestBody
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadFile(String url, RequestBody requestBody, MultipartBody.Part file, Class<T> clazz) {
        return apiService.uploadFile(url, requestBody, file).compose(this.norTransformer(clazz));
    }

    /**
     * 上传多文件
     *
     * @param url
     * @param files
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadFlies(String url, Map<String, RequestBody> files, Class<T> clazz) {
        return apiService.uploadFiles(url, files).compose(this.norTransformer(clazz));
    }

    /* =============================以下处理服务器返回对象为ApiResult<T>形式的请求================================= */

    /**
     * 由外部设置被观察者
     *
     * @param observable
     * @param <T>
     * @return
     */
    public <T> Observable<T> apiCall(Observable<T> observable) {
        return observable.map(new Func1<T, T>() {
            @Override
            public T call(T result) {
                if (result instanceof ApiResult) {
                    ApiResult value = (ApiResult) result;
                    return (T) value.getData();
                } else {
                    Throwable throwable = new Throwable(
                            "Please call(Observable<T> observable) , < T > is not " + "ApiResult object");
                    new ApiException(throwable, ApiCode.Request.INVOKE_ERROR);
                    return (T) result;
                }
            }
        }).compose(new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(new ApiErrFunc<T>());
            }
        });
    }

    /**
     * 返回ApiResult<T>的Get方式请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> apiGet(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.get(url, maps).map(new ApiResultFunc<T>(clazz)).compose(this.<T> apiTransformer());
    }

    /**
     * 返回ApiResult<T>的Get方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription apiGet(final String url, final Map<String, String> maps, ApiCallback<T> callback) {
        return this.apiGet(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 返回ApiResult<T>并带缓存的Get方式请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<CacheResult<T>> apiCacheGet(final String url, final Map<String, String> maps,
            Class<T> clazz) {
        return this.apiGet(url, maps, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 返回ApiResult<T>并带缓存的Get方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription apiCacheGet(final String url, final Map<String, String> maps, ApiCallback<T> callback) {
        return this.apiCacheGet(url, maps, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 返回ApiResult<T>的POST方式请求，需传入实体类
     *
     * @param url
     * @param parameters
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> apiPost(final String url, final Map<String, String> parameters, Class<T> clazz) {
        return apiService.post(url, parameters).map(new ApiResultFunc<T>(clazz)).compose(this.<T> apiTransformer());
    }

    /**
     * 返回ApiResult<T>的POST方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param parameters
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription apiPost(final String url, final Map<String, String> parameters, ApiCallback<T> callback) {
        return this.apiPost(url, parameters, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 返回ApiResult<T>并带缓存的POST方式请求，需传入实体类
     *
     * @param url
     * @param parameters
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<CacheResult<T>> apiCachePost(final String url, final Map<String, String> parameters,
            Class<T> clazz) {
        return this.apiPost(url, parameters, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 返回ApiResult<T>并带缓存的POST方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param parameters
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription apiCachePost(final String url, final Map<String, String> parameters,
            ApiCallback<T> callback) {
        return this.apiCachePost(url, parameters, ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(mContext, callback));
    }

    /**
     * 清楚所有缓存
     *
     * @return
     */
    public Subscription clearCache() {
        return apiCache.clear();
    }

    /**
     * 清除对应Key的缓存
     *
     * @param key
     */
    public void removeCache(String key) {
        apiCache.remove(key);
    }

    /**
     * 创建ViseApi.Builder
     *
     * @param context
     * @return
     */
    public GalaxyApi.Builder newBuilder(Context context) {
        return new GalaxyApi.Builder(context);
    }

    private <T> Observable.Transformer<ResponseBody, T> norTransformer(final Class<T> clazz) {
        return new Observable.Transformer<ResponseBody, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).map(new ApiFunc<T>(clazz))
                        .onErrorResumeNext(new ApiErrFunc<T>());
            }
        };
    }

    private <T> Observable.Transformer<ApiResult<T>, T> apiTransformer() {
        return new Observable.Transformer<ApiResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).map(new ApiDataFunc<T>())
                        .onErrorResumeNext(new ApiErrFunc<T>());
            }
        };
    }

    public static final class Builder {
        private okhttp3.Call.Factory callFactory;
        private Converter.Factory converterFactory;
        private CallAdapter.Factory callAdapterFactory;
        private HostnameVerifier hostnameVerifier;
        private SSLSocketFactory sslSocketFactory;
        private ConnectionPool connectionPool;
        private File httpCacheDirectory;
        private ApiCookie apiCookie;
        private Cache cache;
        private Boolean isCookie = false;
        private Boolean isCache = false;
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

        public GalaxyApi.Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) {
                throw new NullPointerException("connectionPool == null");
            }
            this.connectionPool = connectionPool;
            return this;
        }

        public GalaxyApi.Builder proxy(Proxy proxy) {
            okHttpClientBuilder.proxy(checkNotNull(proxy, "proxy == null"));
            return this;
        }

        public GalaxyApi.Builder cookie(boolean isCookie) {
            this.isCookie = isCookie;
            return this;
        }

        public GalaxyApi.Builder cache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public GalaxyApi.Builder cacheKey(String cacheKey) {
            apiCacheBuilder.cacheKey(checkNotNull(cacheKey, "cacheKey == null"));
            return this;
        }

        public GalaxyApi.Builder cacheTime(long cacheTime) {
            apiCacheBuilder.cacheTime(Math.max(DiskCache.CACHE_NEVER_EXPIRE, cacheTime));
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

        public GalaxyApi.Builder cookieManager(ApiCookie apiCookie) {
            if (apiCookie == null) {
                throw new NullPointerException("apiCookie == null");
            }
            this.apiCookie = apiCookie;
            return this;
        }

        public GalaxyApi.Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public GalaxyApi.Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public GalaxyApi.Builder cacheMode(CacheMode mode) {
            cacheMode = mode;
            return this;
        }

        public GalaxyApi.Builder cacheOnline(Cache cache) {
            networkInterceptor(new OnlineCacheInterceptor());
            this.cache = cache;
            return this;
        }

        public GalaxyApi.Builder cacheOnline(Cache cache, int cacheControlValue) {
            networkInterceptor(new OnlineCacheInterceptor(cacheControlValue));
            this.cache = cache;
            return this;
        }

        public GalaxyApi.Builder cacheOffline(Cache cache) {
            networkInterceptor(new OfflineCacheInterceptor(mContext));
            interceptor(new OfflineCacheInterceptor(mContext));
            this.cache = cache;
            return this;
        }

        public GalaxyApi.Builder cacheOffline(Cache cache, int cacheControlValue) {
            networkInterceptor(new OfflineCacheInterceptor(mContext, cacheControlValue));
            interceptor(new OfflineCacheInterceptor(mContext, cacheControlValue));
            this.cache = cache;
            return this;
        }

        public GalaxyApi.Builder postGzipInterceptor() {
            interceptor(new GzipRequestInterceptor());
            return this;
        }

        public GalaxyApi build() {

            if (okHttpClientBuilder == null) {
                throw new NullPointerException("OkHttpClientBuilder is required.");
            }

            if (retrofitBuilder == null) {
                throw new NullPointerException("RetrofitBuilder is required.");
            }

            if (apiCacheBuilder == null) {
                throw new NullPointerException("ApiCacheBuilder is required.");
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

            if (hostnameVerifier == null) {
                hostnameVerifier = new SSLUtil.UnSafeHostnameVerifier(baseUrl);
            }
            okHttpClientBuilder.hostnameVerifier(hostnameVerifier);

            if (sslSocketFactory == null) {
                sslSocketFactory = SSLUtil.getSslSocketFactory(null, null, null);
            }
            okHttpClientBuilder.sslSocketFactory(sslSocketFactory);

            if (connectionPool == null) {
                connectionPool = new ConnectionPool(Config.DEFAULT_MAX_IDLE_CONNECTIONS,
                        Config.DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS);
            }
            okHttpClientBuilder.connectionPool(connectionPool);

            if (isCookie && apiCookie == null) {
                apiCookie = new ApiCookie(mContext);
            }
            if (isCookie) {
                okHttpClientBuilder.cookieJar(apiCookie);
            }

            if (httpCacheDirectory == null) {
                httpCacheDirectory = new File(mContext.getCacheDir(), Config.CACHE_HTTP_DIR);
            }
            if (isCache) {
                try {
                    if (cache == null) {
                        cache = new Cache(httpCacheDirectory, Config.CACHE_MAX_SIZE);
                    }
                    cacheOnline(cache);
                    cacheOffline(cache);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cache != null) {
                okHttpClientBuilder.cache(cache);
            }

            okHttpClient = okHttpClientBuilder.build();
            retrofitBuilder.client(okHttpClient);
            retrofit = retrofitBuilder.build();
            apiCache = apiCacheBuilder.build();
            apiService = retrofit.create(BaseApiService.class);
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
