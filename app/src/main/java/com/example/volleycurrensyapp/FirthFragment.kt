package com.example.volleycurrensyapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volleycurrensyapp.adapters.CurrencyAdapter
import com.example.volleycurrensyapp.databinding.BottomsheetDialogBinding
import com.example.volleycurrensyapp.databinding.FragmentFirthBinding
import com.example.volleycurrensyapp.models.Currency
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class FirthFragment : Fragment() {

    lateinit var currencyAdapter: CurrencyAdapter
    lateinit var requestQueue: RequestQueue
    val url = "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFirthBinding.inflate(inflater)

        (activity as AppCompatActivity).supportActionBar?.setTitle("Markaziy Bank valyuta kursi")
        requestQueue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url,null,object : Response.Listener<JSONArray>{
            override fun onResponse(response: JSONArray?) {
                val type =  object : TypeToken<List<Currency>>() {}.type
                var list: List<Currency> = Gson().fromJson(response.toString(),type)
                Log.d("TTT", "onResponse: ${response.toString()}")
                currencyAdapter = CurrencyAdapter(list, object : CurrencyAdapter.MyItemClickListener{
                    override fun onMyItemClick(currency: Currency) {
                        var dialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme)
                        val bind = BottomsheetDialogBinding.bind(layoutInflater.inflate(R.layout.bottomsheet_dialog, null))
                        bind.nameTv.setText(" Id : ${currency.id} \n" +
                                " Code : ${currency.Code} \n" +
                                " Ccy : ${currency.Ccy} \n" +
                                " CcyNm_RU : ${currency.CcyNm_RU}\n" +
                                " CcyNm_UZ : ${currency.CcyNm_UZ}\n" +
                                " CcyNm_UZC : ${currency.CcyNm_UZC}\n" +
                                " CcyNm_EN : ${currency.CcyNm_EN}\n" +
                                " Nominal : ${currency.Nominal}\n" +
                                " Rate : ${currency.Rate}\n" +
                                " Diff : ${currency.Diff}\n" +
                                " Date : ${currency.Date}\n")
                        dialog.setContentView(bind.root)
                        dialog.show()
                    }
                })
                binding.rv.adapter = currencyAdapter
            }
        },object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {

            }
        })

        requestQueue.add(jsonArrayRequest)
        return binding.root
    }

}