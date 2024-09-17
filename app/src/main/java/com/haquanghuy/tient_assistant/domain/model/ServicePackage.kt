package com.haquanghuy.tient_assistant.domain.model

data class ServicePackage(
    val value: Double,
    val name: String,
    val intro: String,
)
val listService = listOf(
    ServicePackage(10.0, "Gói tích luỹ linh hoạt", "Đến 10% lợi nhuận / năm"),
    ServicePackage(12.0, "Gói tích luỹ tối ưu", "Đến 12% lợi nhuận / năm"),
    ServicePackage(15.0, "Gói tích luỹ ưu việt", "Đến 15% lợi nhuận / năm"),
)