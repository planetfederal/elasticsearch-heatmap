# elasticsearch-heatmap

This elasticsearch plugin exposes the heatmap functionality in
Lucene as an aggregation. Its parameters and output mirror the
Solr heatmap facet.

## Installation

Use the `elasticsearch-plugin` utility to download and install the
plugin. The format of the filename follows this convention:
`aggs-geoheatmap-<PLUGIN VERSION>-es<ES VERSION>.zip`. The full listing
can be found [here](http://es-heatmap.labs.o19s.com/). For example, to
install version 1.0.0 of the plugin on ES 5.3.2, do the following:

`./bin/elasticsearch-plugin install http://es-heatmap.labs.o19s.com/aggs-geoheatmap-1.0.0-es5.3.2.zip`

## Usage


### Request

```
{
    "query": {
        "match_all": { }
    },
    "aggs" : {
        "viewport" : {
            "heatmap" : {
                "field" : "location",
                "grid_level" : 4,
                "max_cells" : 100,
                "geom" : {
                    "geo_shape": {
                        "location": {
                            "shape": {
                                "type": "envelope",
                                "coordinates" : [[6.0, 53.0], [14.0, 49.0]]
                            }
                        }
                    }
                }
            }
        }
    }
}

```

### Response

```
...
        {
            "grid_level":4,
            "rows":7,
            "columns":6,
            "min_x":6.0,
            "min_y":49.0,
            "max_x":14.0,
            "max_y":53.0,
            "counts": [
                [0,0,2,1,0,0],
                [0,0,1,1,0,0],
                [0,1,1,1,0,0],
                [0,0,1,1,0,0],
                [0,0,1,1,0,0],
                [],
                []
            ]
        }
```

### Options
| Parameter | Definition |
| --- | --- |
| field | Mandatory. Document field that contains the shape. |
| geom | Optional. Sets a bounding geometry for the heatmap. The heatmap itself will be rectangular, but hits outside of this geometry will not be counted. Defaults to the world rectangle.
| max_cells | Optional. The maximum number of cells that can be returned in the heatmap. Defaults to 100,000 
| dist_err | Optional*. The maximum allowable error for determining where an indexed shape is relative to the heatmap cells. Measured in meters.
| dist_err_pct | Optional*. Sets the maximum allowable error for determining where an indexed shape is relative to the heatmap cells, specified as a fraction of the shape size. Values should be 0.0 > distErrPct =< 0.5
| grid_level | Optional*. Sets the grid level (granularity) of the heatmap. Higher numbers mean higher granularity. Defaults to 7

*Note: Either grid_level or dist_err_pct must be specified (but not both). If dist_err_pct is specified, dist_err may also be specified. 
