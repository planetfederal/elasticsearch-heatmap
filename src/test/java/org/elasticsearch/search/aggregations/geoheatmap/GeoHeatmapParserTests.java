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

package org.elasticsearch.search.aggregations.geoheatmap;

import java.util.Collection;
import java.util.Collections;

import org.elasticsearch.common.ParseFieldMatcher;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.indices.query.IndicesQueriesRegistry;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin.QuerySpec;
import org.elasticsearch.search.aggregations.geoheatmap.plugins.GeoHeatmapSearchPlugin;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Ignore;

/**
 * Tests the construction of the aggregator from JSON
 */
public class GeoHeatmapParserTests extends ESIntegTestCase {

    //TODO: Can probably change this to ESTestCase and skip cluster creation. Method below would go away
    @Override
    protected Collection<Class<? extends Plugin>> transportClientPlugins() {
        return  Collections.singleton(GeoHeatmapSearchPlugin.class);
    }

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return  Collections.singleton(GeoHeatmapSearchPlugin.class);
    }

    /**
    * Randomly verifies possible field values are able to parse, except the geo_shape query
    * parsing which has its own tests
    */    
    public void testParsing() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"field\": \"my_loc\"");
        if (randomBoolean()) {
            appendRandomNumericOrString(sb, "grid_level", ""+randomInt()+"");
        } else {
            if (randomBoolean()) {
                sb.append(", \"dist_err\": \""+randomDouble()+" "+randomUnits()+ "\"");
            }
            appendRandomNumericOrString(sb, "dist_err_pct", ""+randomDouble()+"");            
        }
        if (randomBoolean()) {
            appendRandomNumericOrString(sb, "max_cells", ""+randomInt()+"");
        }
        if (randomBoolean()) {
            sb.append(
                ", \"geom\":{"
                + "     \"geo_shape\": {"
                + "         \"location\": {" 
                + "             \"shape\": {"
                + "                 \"type\": \"envelope\","
                + "                 \"coordinates\" : [[13.0, 53.0], [14.0, 52.0]]" 
                + "             },"
                + "             \"relation\": \"within\"}}}");
        }
        sb.append("}");
        XContentParser stParser = JsonXContent.jsonXContent.createParser(sb.toString());
        IndicesQueriesRegistry queryParserRegistry = new IndicesQueriesRegistry();
        QueryParseContext parseContext = new QueryParseContext(queryParserRegistry, stParser, ParseFieldMatcher.STRICT);
        XContentParser.Token token = stParser.nextToken();
        assertSame(XContentParser.Token.START_OBJECT, token);
        // can create a factory
        //TODO: get the registry working
        //assertNotNull(GeoHeatmapAggregationBuilder.parse("geo_heatmap", parseContext));
    }
    
    private void appendRandomNumericOrString(StringBuilder sb, String field, String value) {
        if (randomBoolean()) {
            sb.append(", \""+field+"\": \""+value+"\"");
        } else {
            sb.append(", \"grid_level\": "+value);                
        }  
    }
    
    private String randomUnits() {
        // Do this the hard way because not all names for the units are visible in DistanceUnit
        return randomFrom("in", "inch", "yd", "yard", "mi", "miles", "km", "kilometers", "m", "meters",
                "cm", "centimeters", "mm", "millimeters");
    }

}
