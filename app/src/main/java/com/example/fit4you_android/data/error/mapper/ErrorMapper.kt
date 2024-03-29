package com.example.fit4you_android.data.error.mapper

import android.content.Context
import com.example.fit4you_android.R
import com.example.fit4you_android.data.error.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {
    override val errorsMap: Map<Int, String>
        get() = mapOf(
            NO_INTERNET_CONNECTION to getErrorString(R.string.no_internet),
            NETWORK_ERROR to getErrorString(R.string.network_error),
            PASSWORD_ERROR to getErrorString(R.string.invalid_password),
            EMAIL_ERROR to getErrorString(R.string.invalid_email),
            EMAIL_OR_PASSWORD_ERROR to getErrorString(R.string.invalid_email_or_password),
            EXPIRED_ACCESS_TOKEN to getErrorString(R.string.expired_access_token),
            INVALID_ACCESS_TOKEN to getErrorString(R.string.invalid_access_token),
            EXPIRED_REFRESH_TOKEN to getErrorString(R.string.expired_refresh_token),
        ).withDefault { getErrorString(R.string.network_error) }

    override fun getErrorString(errorId: Int): String = context.getString(errorId)
}
