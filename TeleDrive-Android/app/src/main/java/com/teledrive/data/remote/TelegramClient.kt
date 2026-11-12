package com.teledrive.data.remote

import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TelegramClient @Inject constructor() {
    private var client: Client? = null

    init {
        try {
            System.loadLibrary("tdjni")
            client = Client.create({ }, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun send(function: TdApi.Function<out TdApi.Object>, resultHandler: Client.ResultHandler = Client.ResultHandler { }) {
        client?.send(function, resultHandler)
    }
}
