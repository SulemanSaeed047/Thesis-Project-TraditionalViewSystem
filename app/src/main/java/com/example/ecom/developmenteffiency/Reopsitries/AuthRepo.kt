package com.example.ecom.developmenteffiency.Reopsitries

import android.content.Context
import com.example.ecom.developmenteffiency.API.ModelClasses.LoginReq
import com.example.ecom.developmenteffiency.API.ModelClasses.SignUpUser
import com.example.ecom.developmenteffiency.DataSources.AuthDataSrc

class AuthRepo() {
    private val AuthDataSrc = AuthDataSrc()
    suspend fun SignUpUser(user: SignUpUser): SignUpUser? {

        return AuthDataSrc.SignUpUser(user)

    }
    suspend fun LoginUser(loginReq: LoginReq): String {
        return AuthDataSrc.loginUser(loginReq)
    }
    suspend fun getAllUsers(): List<SignUpUser>? {
        return AuthDataSrc.getAllUsers()
    }
    suspend fun Login(context: Context, loginReq: LoginReq): Boolean {
        return AuthDataSrc.login(context,loginReq)
    }
    suspend fun Register(context: Context,user: SignUpUser): Boolean {
        return AuthDataSrc.register(context,user)
    }
}