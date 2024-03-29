package com.example.fit4you_android.data.dto.response

data class RefreshTokenRes(
    val code: Int,
    val result: String,
    val message: String,
    val data: Data,
) {
    data class Data(
        val id: Long,
        val email: String,
        val jwtToken: String,
        val refreshToken: String,
        val role: String,
    )
}
