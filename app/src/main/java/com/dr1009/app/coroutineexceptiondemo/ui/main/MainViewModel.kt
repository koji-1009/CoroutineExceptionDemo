package com.dr1009.app.coroutineexceptiondemo.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainViewModel : ViewModel() {

    val spinnerPosition = MutableLiveData<Int>().apply { value = 0 }
    val spinnerTitles = FunctionType.values().map { it.name }
    private val functionType = spinnerPosition.map { FunctionType.values()[it ?: 0] }

    val functionText = functionType.map { it.getCodeText() }

    fun onClick() {
        viewModelScope.launch {
            when (functionType.value ?: return@launch) {
                FunctionType.SINGLE -> {
                    Log.d(TAG, "function start")

                    Log.d(TAG, "stop thread 500ms start")
                    delay(500)
                    Log.d(TAG, "stop thread 500ms end")

                    Log.d(TAG, "function end")
                }
                FunctionType.DOUBLE -> {
                    Log.d(TAG, "function start")

                    Log.d(TAG, "stop thread 500ms start 1st")
                    delay(500)
                    Log.d(TAG, "stop thread 500ms end 1st")

                    Log.d(TAG, "stop thread 250ms start 2nd")
                    delay(250)
                    Log.d(TAG, "stop thread 250ms end 2nd")

                    Log.d(TAG, "function end")
                }
                FunctionType.LAUNCH -> {
                    Log.d(TAG, "function start")

                    launch {
                        Log.d(TAG, "stop thread 500ms start 1st")
                        delay(500)
                        Log.d(TAG, "stop thread 500ms end 1st")
                    }

                    launch {
                        Log.d(TAG, "stop thread 250ms start 2nd")
                        delay(250)
                        Log.d(TAG, "stop thread 250ms end 2nd")
                    }

                    Log.d(TAG, "function end")
                }
                FunctionType.COROUTINE_SCOPE -> {
                    Log.d(TAG, "function start")

                    coroutineScope {
                        Log.d(TAG, "stop thread 500ms start 1st")
                        delay(500)
                        Log.d(TAG, "stop thread 500ms end 1st")

                        Log.d(TAG, "stop thread 250ms start 2nd")
                        delay(250)
                        Log.d(TAG, "stop thread 250ms end 2nd")
                    }

                    Log.d(TAG, "function end")
                }
                FunctionType.COROUTINE_SCOPE_LAUNCH -> {
                    Log.d(TAG, "function start")

                    coroutineScope {
                        launch {
                            Log.d(TAG, "stop thread 500ms start 1st")
                            delay(500)
                            Log.d(TAG, "stop thread 500ms end 1st")
                        }

                        launch {
                            Log.d(TAG, "stop thread 250ms start 2nd")
                            delay(250)
                            Log.d(TAG, "stop thread 250ms end 2nd")
                        }
                    }

                    Log.d(TAG, "function end")
                }
                FunctionType.EXCEPTION -> {
                    Log.d(TAG, "function start")

                    Log.d(TAG, "stop thread 500ms start")
                    delay(500)
                    Log.d(TAG, "stop thread 500ms end")

                    throw Exception("Throw Exception")

                    Log.d(TAG, "function end")
                }
                FunctionType.EXCEPTION_COROUTINE_SCOPE -> {
                    Log.d(TAG, "function start")

                    coroutineScope {
                        launch {
                            Log.d(TAG, "stop thread 500ms start 1st")
                            delay(500)
                            Log.d(TAG, "stop thread 500ms end 1st")
                        }

                        launch {
                            Log.d(TAG, "stop thread 250ms start 2nd")
                            delay(250)

                            throw  Exception("Throw Exception")
                            Log.d(TAG, "stop thread 250ms end 2nd")
                        }
                    }

                    Log.d(TAG, "function end")
                }
                FunctionType.EXCEPTION_SUPERVISOR_SCOPE -> {
                    Log.d(TAG, "function start")

                    supervisorScope {
                        launch {
                            Log.d(TAG, "stop thread 500ms start 1st")
                            delay(500)
                            Log.d(TAG, "stop thread 500ms end 1st")
                        }

                        launch {
                            Log.d(TAG, "stop thread 250ms start 2nd")
                            delay(250)

                            throw  Exception("Throw Exception")
                            Log.d(TAG, "stop thread 250ms end 2nd")
                        }
                    }

                    Log.d(TAG, "function end")
                }
                FunctionType.EXCEPTION_COROUTINE_SCOPE_CATCH -> {
                    Log.d(TAG, "function start")

                    runCatching {
                        coroutineScope {
                            launch {
                                Log.d(TAG, "stop thread 500ms start 1st")
                                delay(500)
                                Log.d(TAG, "stop thread 500ms end 1st")
                            }

                            launch {
                                Log.d(TAG, "stop thread 250ms start 2nd")
                                delay(250)

                                throw  Exception("Throw Exception")
                                Log.d(TAG, "stop thread 250ms end 2nd")
                            }
                        }
                    }.fold(
                        onSuccess = {
                            Log.d(TAG, "success runCatching")
                        },
                        onFailure = {
                            Log.d(TAG, "failed runCatching")
                        }
                    )

                    Log.d(TAG, "function end")
                }
                FunctionType.EXCEPTION_SUPERVISOR_SCOPE_CATCH -> {
                    Log.d(TAG, "function start")

                    runCatching {
                        supervisorScope {
                            launch {
                                Log.d(TAG, "stop thread 500ms start 1st")
                                delay(500)
                                Log.d(TAG, "stop thread 500ms end 1st")
                            }

                            launch {
                                Log.d(TAG, "stop thread 250ms start 2nd")
                                delay(250)

                                throw  Exception("Throw Exception")
                                Log.d(TAG, "stop thread 250ms end 2nd")
                            }
                        }
                    }.fold(
                        onSuccess = {
                            Log.d(TAG, "success runCatching")
                        },
                        onFailure = {
                            Log.d(TAG, "failed runCatching")
                        }
                    )

                    Log.d(TAG, "function end")
                }
            }
        }
    }

    private fun FunctionType.getCodeText() = when (this) {
        FunctionType.SINGLE -> """
            Log.d(TAG, "function start")
            
            Log.d(TAG, "stop thread 500ms start")
            delay(500)
            Log.d(TAG, "stop thread 500ms end")
            
            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.DOUBLE -> """
            Log.d(TAG, "function start")
            
            Log.d(TAG, "stop thread 500ms start 1st")
            delay(500)
            Log.d(TAG, "stop thread 500ms end 1st")
            
            Log.d(TAG, "stop thread 250ms start 2nd")
            delay(250)
            Log.d(TAG, "stop thread 250ms end 2nd")
            
            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.LAUNCH -> """
            Log.d(TAG, "function start")
            
            launch {
                Log.d(TAG, "stop thread 500ms start 1st")
                delay(500)
                Log.d(TAG, "stop thread 500ms end 1st")
            }
            
            launch {
                Log.d(TAG, "stop thread 250ms start 2nd")
                delay(250)
                Log.d(TAG, "stop thread 250ms end 2nd")
            }
            
            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.COROUTINE_SCOPE -> """
            Log.d(TAG, "function start")
            
            coroutineScope {
                Log.d(TAG, "stop thread 500ms start 1st")
                delay(500)
                Log.d(TAG, "stop thread 500ms end 1st")
            
                Log.d(TAG, "stop thread 250ms start 2nd")
                delay(250)
                Log.d(TAG, "stop thread 250ms end 2nd")
            }
            
            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.COROUTINE_SCOPE_LAUNCH -> """
            Log.d(TAG, "function start")
            
            coroutineScope {
                launch {
                    Log.d(TAG, "stop thread 500ms start 1st")
                    delay(500)
                    Log.d(TAG, "stop thread 500ms end 1st")
                }
            
                launch {
                    Log.d(TAG, "stop thread 250ms start 2nd")
                    delay(250)
                    Log.d(TAG, "stop thread 250ms end 2nd")
                }
            }
            
            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.EXCEPTION -> """
            Log.d(TAG, "function start")
            
            Log.d(TAG, "stop thread 500ms start")
            delay(500)
            Log.d(TAG, "stop thread 500ms end")
            
            throw Exception()
            
            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.EXCEPTION_COROUTINE_SCOPE -> """
            Log.d(TAG, "function start")

            coroutineScope {
                launch {
                    Log.d(TAG, "stop thread 500ms start 1st")
                    delay(500)
                    Log.d(TAG, "stop thread 500ms end 1st")
                }

                launch {
                    Log.d(TAG, "stop thread 250ms start 2nd")
                    delay(250)

                    throw  Exception("Throw Exception")
                    Log.d(TAG, "stop thread 250ms end 2nd")
                }
            }

            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.EXCEPTION_SUPERVISOR_SCOPE -> """
            Log.d(TAG, "function start")

            supervisorScope {
                launch {
                    Log.d(TAG, "stop thread 500ms start 1st")
                    delay(500)
                    Log.d(TAG, "stop thread 500ms end 1st")
                }

                launch {
                    Log.d(TAG, "stop thread 250ms start 2nd")
                    delay(250)

                    throw  Exception("Throw Exception")
                    Log.d(TAG, "stop thread 250ms end 2nd")
                }
            }

            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.EXCEPTION_COROUTINE_SCOPE_CATCH -> """
            Log.d(TAG, "function start")

            runCatching {
                coroutineScope {
                    launch {
                        Log.d(TAG, "stop thread 500ms start 1st")
                        delay(500)
                        Log.d(TAG, "stop thread 500ms end 1st")
                    }

                    launch {
                        Log.d(TAG, "stop thread 250ms start 2nd")
                        delay(250)

                        throw  Exception("Throw Exception")
                        Log.d(TAG, "stop thread 250ms end 2nd")
                    }
                }
            }.fold(
                onSuccess = {
                    Log.d(TAG, "success runCatching")
                },
                onFailure = {
                    Log.d(TAG, "failed runCatching")
                }
            )

            Log.d(TAG, "function end")
        """.trimIndent()
        FunctionType.EXCEPTION_SUPERVISOR_SCOPE_CATCH -> """
            Log.d(TAG, "function start")

            runCatching {
                supervisorScope {
                    launch {
                        Log.d(TAG, "stop thread 500ms start 1st")
                        delay(500)
                        Log.d(TAG, "stop thread 500ms end 1st")
                    }

                    launch {
                        Log.d(TAG, "stop thread 250ms start 2nd")
                        delay(250)

                        throw  Exception("Throw Exception")
                        Log.d(TAG, "stop thread 250ms end 2nd")
                    }
                }
            }.fold(
                onSuccess = {
                    Log.d(TAG, "success runCatching")
                },
                onFailure = {
                    Log.d(TAG, "failed runCatching")
                }
            )

            Log.d(TAG, "function end")
        """.trimIndent()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
