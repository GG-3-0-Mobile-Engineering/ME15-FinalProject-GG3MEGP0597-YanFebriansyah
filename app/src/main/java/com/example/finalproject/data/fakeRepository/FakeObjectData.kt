package com.example.finalproject.data.fakeRepository

import com.example.finalproject.data.model.*
import com.example.finalproject.presentation.model.Bencana

object FakeData {
    val fakeGeometries: Geometries = Geometries(
        type = "fake_type",
        properties = Properties(
            pkey = "fake_pkey",
            createdAt = "fake_createdAt",
            source = "fake_source",
            status = "fake_status",
            url = "fake_url",
            imageUrl = "fake_imageUrl",
            disasterType = "flood",
            reportData = ReportData(
                reportType = "fake_reportType",
                floodDepth = 5,
                impact = 3,
                visibility = 2,
                airQuality = 4,
                structureFailure = 1,
                accessabilityFailure = 0,
                condition = 2
            ),
            tags = Tags(
                districtId = "fake_districtId",
                regionCode = "fake_regionCode",
                localAreaId = "fake_localAreaId",
                instanceRegionCode = "fake_instanceRegionCode"
            ),
            title = "fake_title",
            text = "fake_text",
            partnerCode = "fake_partnerCode",
            partnerIcon = "fake_partnerIcon"
        ),
        coordinates = listOf(0.0, 0.0)
    )

    val fakeOutput: Output = Output(
        type = "fake_output_type",
        geometries = listOf(fakeGeometries)
    )

    val fakeObjects: Objects = Objects(output = fakeOutput)

    val fakeResult: Result = Result(
        type = "fake_type",
        objects = fakeObjects,
        arcs = emptyList(),
        bbox = emptyList()
    )

    val fakeResponseData: ResponseData = ResponseData(
        statusCode = 200,
        result = fakeResult
    )

    val fakeBencanaList: List<Bencana>? = fakeResponseData.getGeometriesAsBencanaProperties()
}



object FakeBencana{
    val fakeBencana = listOf<Bencana>(
        Bencana(
            title = "flood",
            description = "flood",
            image = "http://image2.com",
            codeArea = "ID-JT",
            latitude = 2.43,
            longitude = 2.43,
            floodDepth = 12,
            time = "2021-01-01"
        ),
        Bencana(
            title = "wind",
            description = "wind",
            image = "http://image1.com",
            codeArea = "ID-JK",
            latitude = 2.43,
            longitude = 2.43,
            floodDepth = 12,
            time = "2021-01-01"
        ),
        Bencana(
            title = "haze",
            description = "haze",
            image = "http://image3.com",
            codeArea = "ID-JB",
            latitude = 2.43,
            longitude = 2.43,
            floodDepth = 12,
            time = "2021-01-01"
        ),
    )
}
