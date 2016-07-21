package com.boundlessgeo;

import java.util.Collection;

import com.boundlessgeo.module.GeoHeatmapModule;
import com.boundlessgeo.rest.GeoHeatmapRestAction;
import com.boundlessgeo.service.GeoHeatmapService;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestModule;

import com.google.common.collect.Lists;

public class GeoHeatmapPlugin extends Plugin {
    @Override
    public String name() {
        return "GeoHeatmapPlugin";
    }

    @Override
    public String description() {
        return "This is a elasticsearch-heatmap plugin.";
    }

    public void onModule(final RestModule module) {
        module.addRestAction(GeoHeatmapRestAction.class);
    }

    @Override
    public Collection<Module> nodeModules() {
        final Collection<Module> modules = Lists.newArrayList();
        modules.add(new GeoHeatmapModule());
        return modules;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Class<? extends LifecycleComponent>> nodeServices() {
        final Collection<Class<? extends LifecycleComponent>> services = Lists.newArrayList();
        services.add(GeoHeatmapService.class);
        return services;
    }
}
