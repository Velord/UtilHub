package com.velord.utilhub.coroutine

import android.util.Log
import kotlinx.coroutines.*

suspend fun <T> onMain(f: suspend () -> T): T = withContext(Dispatchers.Main) { f() }

suspend fun <T> onIO(f: suspend () -> T): T = withContext(Dispatchers.IO) { f() }

suspend fun <T> onDef(f: suspend () -> T): T = withContext(Dispatchers.Default) { f() }

fun createDefaultScope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.Default)
fun createIoScope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
fun createMainScope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)

fun <T> CoroutineScope.sendDataWithRetryNoWait(
    tag: String,
    tryCount: Int = 3,
    delay: Long = 10000,
    f: suspend () -> T, ) {
    this.launch {
        try {
            sendDataWithRetry(tryCount, delay, f)
        }
        catch (e: Exception) {
            Log.d(tag, e.message.toString())
        }
    }
}

//be careful may throw error
suspend fun <T> sendDataWithRetry(tryCount: Int = 5,
                                  delay: Long = 5000,
                                  f: suspend () -> T, ): T {
    //exit if tries is over
    if (tryCount <= 0)  error("Can't send report")
    
    return try { f() }
    catch (e: Exception) {
        //another trying after 5 sec
        delay(delay)
        sendDataWithRetry( tryCount - 1, delay, f)
    }
}

suspend fun sendReportWithRetry(tag: String,
                                f: suspend () -> String): String =
    try { f() }
    catch (e: Exception) {
        val msg = e.message.toString()
        Log.d(tag, msg)
        "Can't send report. Check your internet connection"
    }