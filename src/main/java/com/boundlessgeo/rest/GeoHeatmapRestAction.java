package com.boundlessgeo.rest;

import static org.elasticsearch.rest.RestStatus.OK;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;

public class GeoHeatmapRestAction extends BaseRestHandler {

    @Inject
    public GeoHeatmapRestAction(final Settings settings, final Client client,
                                final RestController controller) {
        super(settings, controller, client);

        controller.registerHandler(RestRequest.Method.GET,
                "/{index}/{type}/_geoheatmap", this);
        controller.registerHandler(RestRequest.Method.GET,
                "/{index}/_geoheatmap", this);
        controller.registerHandler(RestRequest.Method.GET,
                "/_geoheatmap", this);
    }

    @Override
    protected void handleRequest(final RestRequest request,
            final RestChannel channel, Client client) {
        try {
            final XContentBuilder builder = JsonXContent.contentBuilder();
            if (request.hasParam("pretty")) {
                builder.prettyPrint().lfAtEnd();
            }
            builder.startObject();
            builder.field("index", request.param("index"));
            builder.field("type", request.param("type"));
            builder.field("description", "This is a elasticsearch-heatmap response: "
                    + new Date().toString());
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(OK, builder));
        } catch (final IOException e) {
            try {
                channel.sendResponse(new BytesRestResponse(channel, e));
            } catch (final IOException e1) {
                logger.error("Failed to send a failure response.", e1);
            }
        }
    }

}
