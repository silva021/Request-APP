package com.silva021.myapplication.view.Main

import android.content.Context
import com.google.gson.Gson
import com.silva021.myapplication.API.ConfigRetrofit
import com.silva021.myapplication.DAO.AppDatabase
import com.silva021.myapplication.model.Historic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainPresenter(private val mView: MainContract.View, private val mContext: Context) :
    MainContract.Presenter {
    override fun newRequest(URL: String) {
        mView.showProgress(true)
        try {
            val call = ConfigRetrofit(URL).requestService().getListObject("")
            call.enqueue(object : Callback<List<Any>?> {
                override fun onResponse(call: Call<List<Any>?>, response: Response<List<Any>?>) {
                    responseRequest(response.body(), response.raw())
                }

                override fun onFailure(call: Call<List<Any>?>, t: Throwable) {
                    requestOnFailure(URL)
                }

            })
        } catch (e: Exception) {
            mView.showSnackBar("Ocorreu um problema")
        }
    }

    override fun requestOnFailure(URL: String) {
        ConfigRetrofit(URL).requestService()
            .getObject("")
            .enqueue(
                object : Callback<Any?> {
                    override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                        responseRequest(response.body(), response.raw())
                    }

                    override fun onFailure(call: Call<Any?>, t: Throwable) {
                        mView.showSnackBar(t.message.toString())
                    }
                }
            )
    }

    override fun insertRequestHistoric(response: okhttp3.Response) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstanceDataBase(mContext).historyDao().insertHistoric(
                Historic(
                    0,
                    response.request().url().toString(),
                    response.request().method(),
                    response.code(),
                    returnDate()
                )
            )
        }
    }

    override fun responseRequest(body: Any?, response: okhttp3.Response) {
        val text: String = when (response.code()) {
            200 -> {
                mView.responseSuccess(formatJson(Gson().toJson(body)))
                "Requisição feita com sucesso"
            }
            404 -> {
                "Essa URL não foi encontrada"
            }
            else -> {
                "Erro " + response.code()
            }
        }
        mView.showSnackBar(text)
        mView.showProgress(false)
        insertRequestHistoric(response)
    }

    private fun returnDate(): String {
        val date = Calendar.getInstance()
        return date.get(Calendar.DAY_OF_MONTH)
            .toString() + "-" + (date.get(Calendar.MONTH) + 1).toString() + "-" + date.get(Calendar.YEAR)
            .toString()

    }

    private fun formatJson(json: String): String {
        val jsonBuilder = StringBuilder()
        var indentString = ""
        (json.indices).forEach { i ->
            when (val letter = json[i]) {
                '{', '[' -> {
                    jsonBuilder.append("\n" + indentString + letter + "\n")
                    indentString += "\t\t";
                    jsonBuilder.append(indentString);
                }
                '}', ']' -> {
                    indentString = indentString.replaceFirst("\t\t", "")
                    jsonBuilder.append("\n" + indentString + letter)
                }
                ',' -> jsonBuilder.append(letter + "\n" + indentString);
                else -> jsonBuilder.append(letter);
            }
        }
        return jsonBuilder.toString()
    }
}