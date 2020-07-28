package com.ifreshmart.githubsearch2020.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifreshmart.githubsearch2020.model.DataFollowers
import com.ifreshmart.githubsearch2020.view.detail.fragment.FragmentFollowers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class FollowersViewModel : ViewModel() {
    private val listFollowerNonMutable = ArrayList<DataFollowers>()
    private val listFollowerMutable = MutableLiveData<ArrayList<DataFollowers>>()

    fun getListFollower(): LiveData<ArrayList<DataFollowers>> {
        return listFollowerMutable
    }

    fun getDataGit(context: Context, id: String) {
        // Login
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 0e9f2530036a4d1b335ae12dfa925651fb71e031")
        asyncClient.addHeader("User-Agent", "request")
        //
        val urlClient = "https://api.github.com/users/$id/followers"
        asyncClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FragmentFollowers.TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDataGitDetail(usernameLogin, context)
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDataGitDetail(usernameLogin: String, context: Context) {
        // Login
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 0e9f2530036a4d1b335ae12dfa925651fb71e031")
        asyncClient.addHeader("User-Agent", "request")
        //
        val urlClient = "https://api.github.com/users/$usernameLogin"
        asyncClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FragmentFollowers.TAG, result)

                try {
                    val jsonObject = JSONObject(result)
                    val usersData = DataFollowers()
                    usersData.username = jsonObject.getString("login")
                    usersData.name = jsonObject.getString("name")
                    usersData.avatar = jsonObject.getString("avatar_url")
                    usersData.company = jsonObject.getString("company")
                    usersData.location = jsonObject.getString("location")
                    usersData.repository = jsonObject.getString("public_repos")
                    usersData.followers = jsonObject.getString("followers")
                    usersData.following = jsonObject.getString("following")
                    listFollowerNonMutable.add(usersData)
                    listFollowerMutable.postValue(listFollowerNonMutable)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}