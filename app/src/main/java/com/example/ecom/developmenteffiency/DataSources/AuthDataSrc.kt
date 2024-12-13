package com.example.ecom.developmenteffiency.DataSources

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.ecom.developmenteffiency.API.RetrofitHelper
import com.example.ecom.developmenteffiency.API.UsersAuthApiService
import com.example.ecom.developmenteffiency.API.ModelClasses.LoginReq
import com.example.ecom.developmenteffiency.API.ModelClasses.LoginResp
import com.example.ecom.developmenteffiency.API.ModelClasses.SignUpUser
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException

class AuthDataSrc() {

    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"
    private val KEY_EMAIL = "email"

    suspend fun SignUpUser(user: SignUpUser): SignUpUser? {
        return suspendCancellableCoroutine { continuation ->
            val retrofit = RetrofitHelper.getInstance()
            val service = retrofit.create(UsersAuthApiService::class.java)
            val call = service.updateUser(1,user)
            call.enqueue(object : Callback<SignUpUser> {
                override fun onResponse(
                    call: Call<SignUpUser>,
                    response: Response<SignUpUser>,
                ) {
                    if (response.isSuccessful) {
                        val user: SignUpUser? = response.body()
                        continuation.resume(user, {})
                    } else {
                        continuation.resumeWithException(Exception("Network call failed"))
                    }
                }
                override fun onFailure(call: Call<SignUpUser>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
            continuation.invokeOnCancellation { }
        }
    }

    suspend fun loginUser(loginReq:LoginReq): String {
        return suspendCancellableCoroutine { continuation ->
            val retrofit = RetrofitHelper.getInstance()
            val service = retrofit.create(UsersAuthApiService::class.java)
            val call = service.loginUser(loginReq)
            Log.d("login DS"," 1 ")

            call.enqueue(object : Callback<LoginResp> {
                override fun onResponse(
                    call: Call<LoginResp>,
                    response: Response<LoginResp>,
                ) {
                    Log.d("login DS"," 2 ")
                    if (response.isSuccessful) {
                        Log.d("login DS","${response.body()}")
                        val token: String = response.body()?.token ?: ""
                        continuation.resume(token, {})
                    } else {
                        Log.d("login DS"," 3 :${response.errorBody()}")
                        Log.d("login DS"," 3 m :${response.message()}")
                        continuation.resume("", {})
                    }
                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    Log.d("login DS"," 4 ")
                    continuation.resumeWithException(t)
                }
            })
            continuation.invokeOnCancellation { }
        }
    }

    suspend fun getAllUsers(): List<SignUpUser>? {
        return suspendCancellableCoroutine { continuation ->
            val retrofit = RetrofitHelper.getInstance()
            val service = retrofit.create(UsersAuthApiService::class.java)
            val call = service.getAllUsers()

            call.enqueue(object : Callback<List<SignUpUser>> {
                override fun onResponse(call: Call<List<SignUpUser>>, response: Response<List<SignUpUser>>) {
                    if (response.isSuccessful) {
                        val users: List<SignUpUser>? = response.body()
                        continuation.resume(users, {})
                    } else {
                        continuation.resumeWithException(Exception("Network call failed"))
                    }
                }

                override fun onFailure(call: Call<List<SignUpUser>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }


    fun register(context: Context,user: SignUpUser): Boolean {
        val sharedPreferences =  context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_PASSWORD, user.password)
        editor.putString(KEY_EMAIL, user.email)
        editor.apply()
        return true
    }
    fun login(context: Context,loginReq:LoginReq): Boolean {
        val sharedPreferences =  context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString(KEY_USERNAME, null)
        val savedPassword = sharedPreferences.getString(KEY_PASSWORD, null)
        return savedUsername == loginReq.username && savedPassword == loginReq.password
    }
}