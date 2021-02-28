package com.silva021.myapplication.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.silva021.myapplication.API.ConfigRetrofit
import com.silva021.myapplication.DAO.AppDatabase
import com.silva021.myapplication.DAO.AppDatabase.Companion.getInstanceDataBase
import com.silva021.myapplication.R
import com.silva021.myapplication.databinding.ActivityMainBinding
import com.silva021.myapplication.model.Historic
import com.silva021.myapplication.model.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        binding.btnRequest.setOnClickListener {
            val call =
                ConfigRetrofit(binding.edtBaseUrl.text.toString()).LocationService().getAllUf()



            call.enqueue(object : Callback<List<Location>?> {
                override fun onResponse(
                    call: Call<List<Location>?>,
                    response: Response<List<Location>?>
                ) {
                    if (response.code() == 200) {
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstanceDataBase(applicationContext).historyDao().insertHistoric(
                                Historic(
                                    0,
                                    response.raw().request().url().toString(),
                                    response.raw().request().method(),
                                    response.code(),
                                    "a"
                                )
                            )
                            withContext(Dispatchers.Main) {
                                showSnackBar("Salvo no banco ")
                            }


                            binding.txtJson.text = formatJson(Gson().toJson(response.body()))
                            showSnackBar("Requisao feita com sucesso")
                        }

                    }
                }

                override fun onFailure(call: Call<List<Location>?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
    }

    fun formatJson(json: String): String {
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

}