package com.example.ecom.developmenteffiency.ViewModels

import android.app.Activity.CONNECTIVITY_SERVICE
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.developmenteffiency.Reopsitries.ProductsRepo
import com.example.ecom.developmenteffiency.API.ModelClasses.LoginReq
import com.example.ecom.developmenteffiency.API.ModelClasses.ProductsList
import com.example.ecom.developmenteffiency.API.ModelClasses.SignUpUser
import com.example.ecom.developmenteffiency.Reopsitries.AuthRepo
import kotlinx.coroutines.launch

class DEViewModel : ViewModel() {

    private val _productList = MutableLiveData<List<ProductsList>>()
    val productList: LiveData<List<ProductsList>> get() = _productList


    private val _prodCategoriesList = MutableLiveData<List<String>>()
    val prodCategoriesList: LiveData<List<String>> get() = _prodCategoriesList

    private val _isDarkTheme  = MutableLiveData<Boolean>(false)
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    fun setTheme(isDark:Boolean,from:String){
        _isDarkTheme.value = isDark
        Log.d("theme VM", "$from---${isDark.toString()}")
    }

    private val _isOnline = MutableLiveData<Boolean>(false)
    val isOnline: LiveData<Boolean> get() = _isOnline

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val repo = ProductsRepo()
  /*  init {
        getAllProducts("All")
        getAllCategories()
    }*/

    fun getAllProducts(filter:String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _productList.value = repo.getAllProducts(filter)
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    fun getAllCategories() {
       viewModelScope.launch {
            try {
                val catList: List<String> = (repo.getAllCategories()?.toMutableList() ?: mutableListOf()).apply {
                    add(0, "All")
                }
                _prodCategoriesList.value = catList

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun checkOnline(context: Context) {
        _isOnline.value = isConnected(context)
        if (isOnline.value == true && prodCategoriesList.value.isNullOrEmpty()){
            getAllProducts("All")
            getAllCategories()
        }
    }
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork: Network? = connectivityManager.activeNetwork
            // Use activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            if (capabilities != null) {
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null) {
                return if (activeNetworkInfo.isConnected) {
                    true
                } else {
                    false
                }
            }
        }
        return false
    }


    // Auth
    val authRepo= AuthRepo()

    private val _userAuthData = MutableLiveData<Triple<String,String,String,>>(null)
    val userAuthData: LiveData<Triple<String,String,String,>> get() = _userAuthData


    private val _isSignUp = MutableLiveData<Boolean>(false)
    val isSignUp: LiveData<Boolean> get() = _isSignUp
    private val _isLogin = MutableLiveData<Boolean>(false)
    val isLogin: LiveData<Boolean> get() = _isLogin

    private val _allUsers = MutableLiveData<List<SignUpUser>>(null)
    val allUsers: LiveData<List<SignUpUser>> get() = _allUsers
    /*private val _LoginToken = MutableLiveData<String>("")
    val LoginToken: LiveData<String> get() = _LoginToken*/

    fun getUserAuth(context: Context,){
        val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "") ?: ""
        val email = prefs.getString("email", "") ?: ""
        val password = prefs.getString("password", "") ?: ""
        _userAuthData.value = Triple(username,email,password)
    }

    fun SignUpUser(context: Context,user: SignUpUser,fromUpdate:Boolean=false) {
        viewModelScope.launch {
            try {
                /*val data =  authRepo.SignUpUser(user)
                _isSignUp.value = if (data?.email.equals(user.email)) true else false*/
                val isRegistered = authRepo.Register(context , user)
                if (isRegistered) {
                    Toast.makeText(context,
                        if (fromUpdate) "Update successfully" else "Registration successful",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                _isSignUp.value = isRegistered

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    fun  loginUser(context: Context,loginReq: LoginReq) {
        viewModelScope.launch {
            try {
               // val loginToken = authRepo.LoginUser(loginReq)
               // Log.d("login",loginToken)
              //  _LoginToken.value = loginToken
                val isLoginSuccessful = authRepo.Login(context , loginReq)
                if (isLoginSuccessful) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
                _isLogin.value = isLoginSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    fun  getAllUsers() {
        viewModelScope.launch {
            try {
                _allUsers.value = authRepo.getAllUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}