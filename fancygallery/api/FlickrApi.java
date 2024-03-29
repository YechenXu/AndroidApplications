package edu.vt.cs5254.fancygallery.api;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0007"}, d2 = {"Ledu/vt/cs5254/fancygallery/api/FlickrApi;", "", "fetchPhotos", "Ledu/vt/cs5254/fancygallery/api/FlickrResponse;", "pageSize", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface FlickrApi {
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "services/rest/?method=flickr.interestingness.getList&api_key=766cd55aeca93081b189d8105ab17669&format=json&nojsoncallback=1&extras=url_s,geo")
    public abstract java.lang.Object fetchPhotos(@retrofit2.http.Query(value = "per_page")
    int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super edu.vt.cs5254.fancygallery.api.FlickrResponse> continuation);
}