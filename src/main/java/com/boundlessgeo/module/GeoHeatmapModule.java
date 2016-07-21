package com.boundlessgeo.module;

import com.boundlessgeo.service.GeoHeatmapService;
import org.elasticsearch.common.inject.AbstractModule;

public class GeoHeatmapModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GeoHeatmapService.class).asEagerSingleton();
    }
}
