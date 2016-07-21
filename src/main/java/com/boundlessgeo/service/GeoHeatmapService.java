package com.boundlessgeo.service;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;

public class GeoHeatmapService extends AbstractLifecycleComponent<GeoHeatmapService> {

    @Inject
    public GeoHeatmapService(final Settings settings) {
        super(settings);
        logger.info("CREATE GeoHeatmapService");

        // TODO Your code..
    }

    @Override
    protected void doStart() throws ElasticsearchException {
        logger.info("START GeoHeatmapService");

        // TODO Your code..
    }

    @Override
    protected void doStop() throws ElasticsearchException {
        logger.info("STOP GeoHeatmapService");

        // TODO Your code..
    }

    @Override
    protected void doClose() throws ElasticsearchException {
        logger.info("CLOSE GeoHeatmapService");

        // TODO Your code..
    }

}
