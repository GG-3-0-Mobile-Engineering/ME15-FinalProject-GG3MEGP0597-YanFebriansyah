package com.example.finalproject.data.model

import com.example.finalproject.presentation.model.Bencana
import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("statusCode") val statusCode: Int, @SerializedName("result") val result: Result
)

data class Result(
    @SerializedName("type") val type: String,
    @SerializedName("objects") val objects: Objects,
    @SerializedName("arcs") val arcs: List<Any>,
    @SerializedName("bbox") val bbox: List<Double>
)

data class Objects(
    @SerializedName("output") val output: Output
)

data class Output(
    @SerializedName("type") val type: String,
    @SerializedName("geometries") val geometries: List<Geometries>
)

data class Geometries(
    @SerializedName("type") val type: String,
    @SerializedName("properties") val properties: Properties,
    @SerializedName("coordinates") val coordinates: List<Double>
)

data class Properties(
    @SerializedName("pkey") val pkey: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("source") val source: String,
    @SerializedName("status") val status: String,
    @SerializedName("url") val url: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("disaster_type") val disasterType: String,
    @SerializedName("report_data") val reportData: ReportData,
    @SerializedName("tags") val tags: Tags,
    @SerializedName("title") val title: String?,
    @SerializedName("text") val text: String,
    @SerializedName("partner_code") val partnerCode: String?,
    @SerializedName("partner_icon") val partnerIcon: String?
)

data class ReportData(
    @SerializedName("report_type") val reportType: String,
    @SerializedName("flood_depth") val floodDepth: Int?,
    @SerializedName("impact") val impact: Int?,
    @SerializedName("visibility") val visibility: Int?,
    @SerializedName("airQuality") val airQuality: Int?,
    @SerializedName("structureFailure") val structureFailure: Int?,
    @SerializedName("accessabilityFailure") val accessabilityFailure: Int?,
    @SerializedName("condition") val condition: Int?
)

data class Tags(
    @SerializedName("district_id") val districtId: String?,
    @SerializedName("region_code") val regionCode: String,
    @SerializedName("local_area_id") val localAreaId: String?,
    @SerializedName("instance_region_code") val instanceRegionCode: String
)

fun ResponseData.getGeometriesAsBencanaProperties(): List<Bencana>? {
    return this.result.objects.output.geometries?.map { geometries ->
        Bencana(
            image = geometries.properties.imageUrl,
            title = geometries.properties.disasterType,
            description = geometries.properties.status,
            latitude = geometries.coordinates[1],
            longitude = geometries.coordinates[0],
            codeArea = geometries.properties.tags.instanceRegionCode,
            floodDepth = geometries.properties.reportData.floodDepth,
            time = geometries.properties.createdAt
        )
    }
}




