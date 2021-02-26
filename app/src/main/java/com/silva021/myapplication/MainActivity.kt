package com.silva021.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.silva021.myapplication.API.ConfigRetrofit
import com.silva021.myapplication.databinding.ActivityMainBinding
import com.silva021.myapplication.model.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val txtJson = findViewById<TextView>(R.id.txtJson)
        val call = ConfigRetrofit().LocationService().getAllUf()
        call.enqueue(object : Callback<List<Location>?> {
            override fun onResponse(
                call: Call<List<Location>?>,
                response: Response<List<Location>?>
            ) {
                txtJson.text = formatString(Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<List<Location>?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
    fun formatString(text: String): String? {
        val json = StringBuilder()
        var indentString = ""
        for (i in 0 until text.length) {
            val letter = text[i]
            when (letter) {
                '{', '[' -> {
                    json.append(
                        """
                        $indentString$letter
                        
                        """.trimIndent()
                    )
                    indentString = indentString + "\t"
                    json.append(indentString)
                }
                '}', ']' -> {
                    indentString = indentString.replaceFirst("\t".toRegex(), "")
                    json.append(
                        """
                        $indentString$letter
                        """.trimIndent()
                    )
                }
                ',' -> json.append(
                    """
                    $letter
                    $indentString
                    """.trimIndent()
                )
                else -> json.append(letter)
            }
        }
        return json.toString()
    }
}