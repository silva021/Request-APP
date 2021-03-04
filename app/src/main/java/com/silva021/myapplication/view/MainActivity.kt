package com.silva021.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.silva021.myapplication.API.ConfigRetrofit
import com.silva021.myapplication.DAO.AppDatabase.Companion.getInstanceDataBase
import com.silva021.myapplication.R
import com.silva021.myapplication.databinding.ActivityMainBinding
import com.silva021.myapplication.model.Historic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mBinding.btnRequest.setOnClickListener {
            try {
                val call =
                    ConfigRetrofit(mBinding.edtBaseUrl.text.toString()).RequestService()
                        .getAllListObject("")

                call.enqueue(object : Callback<List<Any>?> {
                    override fun onResponse(
                        call: Call<List<Any>?>,
                        response: Response<List<Any>?>
                    ) {
                        responseRequest(response.body(), response.raw())
                    }

                    override fun onFailure(call: Call<List<Any>?>, t: Throwable) {
                        newRequest()
                    }

                })
            } catch (e: Exception) {
                showSnackBar("Ocorreu um problema")
            }
        }

    }

    private fun newRequest() {
        ConfigRetrofit(mBinding.edtBaseUrl.text.toString()).RequestService()
            .getAllObject("")
            .enqueue(
                object : Callback<Any?> {
                    override fun onResponse(
                        call: Call<Any?>,
                        response: Response<Any?>
                    ) {
                        responseRequest(response.body(), response.raw())
                    }

                    override fun onFailure(call: Call<Any?>, t: Throwable) {
                        showSnackBar(t.message.toString())
                    }
                }
            )
    }

    private fun responseRequest(body: Any?, response: okhttp3.Response) {
        val text: String
        when (response.code()) {
            200 -> {
                mBinding.txtJson.text = formatJson(Gson().toJson(body))
                text = "Requisição feita com sucesso"
            }
            404 -> text = "Essa URL não foi encontrada"
            else -> text = "Erro " + response.code()
        }
        showSnackBar(text)
        CoroutineScope(Dispatchers.IO).launch {
            getInstanceDataBase(applicationContext).historyDao().insertHistoric(
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

    private fun returnDate(): String {
        val date = Calendar.getInstance()
        return date.get(Calendar.DAY_OF_MONTH)
            .toString() + "-" + (date.get(Calendar.MONTH) + 1).toString() + "-" + date.get(Calendar.YEAR)
            .toString()

    }

    fun showSnackBar(text: String) {
        Snackbar.make(mBinding.root, text, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_historic -> {
                startActivity(Intent(this, HistoricActivity::class.java))
                true
            }
            R.id.menu_item_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}