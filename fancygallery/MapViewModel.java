package edu.vt.cs5254.fancygallery;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0004R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2 = {"Ledu/vt/cs5254/fancygallery/MapViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "<set-?>", "Lorg/osmdroid/api/IGeoPoint;", "mapCenter", "getMapCenter", "()Lorg/osmdroid/api/IGeoPoint;", "", "zoomLevel", "getZoomLevel", "()D", "saveMapState", "", "zoom", "center", "app_debug"})
public final class MapViewModel extends androidx.lifecycle.ViewModel {
    private double zoomLevel = 1.5;
    @org.jetbrains.annotations.NotNull()
    private org.osmdroid.api.IGeoPoint mapCenter;
    
    public MapViewModel() {
        super();
    }
    
    public final double getZoomLevel() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.osmdroid.api.IGeoPoint getMapCenter() {
        return null;
    }
    
    public final void saveMapState(double zoom, @org.jetbrains.annotations.NotNull()
    org.osmdroid.api.IGeoPoint center) {
    }
}