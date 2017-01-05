/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.search.aggregations.geoheatmap.plugins;

import org.elasticsearch.common.io.stream.NamedWriteableRegistry;
import org.elasticsearch.plugins.NetworkPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.geoheatmap.GeoHeatmapAggregationBuilder;
import org.elasticsearch.search.aggregations.geoheatmap.InternalGeoHeatmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

public class GeoHeatmapSearchPlugin extends Plugin implements SearchPlugin, NetworkPlugin {
    private static final List<NamedWriteableRegistry.Entry> namedWriteables = new ArrayList<>();
    private static boolean hasAddedAgg = false;

    static {
        namedWriteables.add(
                new NamedWriteableRegistry.Entry(AggregationBuilder.class, GeoHeatmapAggregationBuilder.NAME,
                        GeoHeatmapAggregationBuilder::new));
        namedWriteables.add(
                new NamedWriteableRegistry.Entry(InternalAggregation.class, GeoHeatmapAggregationBuilder.NAME,
                        InternalGeoHeatmap::new));
    }


    @Override
    public List<AggregationSpec> getAggregations() {
        hasAddedAgg = true;
        return singletonList(new AggregationSpec(GeoHeatmapAggregationBuilder.NAME, GeoHeatmapAggregationBuilder::new,
                GeoHeatmapAggregationBuilder::parse).addResultReader(InternalGeoHeatmap::new));
    }

    @Override
    public List<NamedWriteableRegistry.Entry> getNamedWriteables() {
        if (!hasAddedAgg) return namedWriteables;
        else return Collections.emptyList();
//        return singletonList(new NamedWriteableRegistry.Entry(InternalAggregation.class, 
//        		GeoHeatmapAggregationBuilder.NAME, InternalGeoHeatmap::new));
    }

}
