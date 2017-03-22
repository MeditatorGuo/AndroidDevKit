package com.aliex.devkit.api;

import com.aliex.commonlib.net.api.BaseApiService;
import com.aliex.devkit.model.CommentInfo;
import com.aliex.devkit.model.DataArr;
import com.aliex.devkit.model.ImageInfo;
import com.apt.annotation.apt.ApiFactory;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */
@ApiFactory
public interface ApiService {
    @GET("classes/Image")
    Observable<DataArr<ImageInfo>> getAllImages(@Query("where") String where, @Query("skip") int skip,
            @Query("limit") int limit, @Query("order") String order);

    @GET("classes/Comment")
    Observable<DataArr<CommentInfo>> getCommentList(@Query("include") String include, @Query("where") String where,
            @Query("skip") int skip, @Query("limit") int limit);

}
