package edu.vt.cs5254.fancygallery;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u000f\u001a\u00020\u000eR\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Ledu/vt/cs5254/fancygallery/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_galleryItems", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Ledu/vt/cs5254/fancygallery/api/GalleryItem;", "galleryItems", "Lkotlinx/coroutines/flow/StateFlow;", "getGalleryItems", "()Lkotlinx/coroutines/flow/StateFlow;", "photoRepository", "Ledu/vt/cs5254/fancygallery/PhotoRepository;", "fetchData", "", "reloadGalleryItems", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final edu.vt.cs5254.fancygallery.PhotoRepository photoRepository = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<edu.vt.cs5254.fancygallery.api.GalleryItem>> _galleryItems = null;
    
    public MainViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<edu.vt.cs5254.fancygallery.api.GalleryItem>> getGalleryItems() {
        return null;
    }
    
    private final void fetchData() {
    }
    
    public final void reloadGalleryItems() {
    }
}