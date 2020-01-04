package com.rental_apps.android.rental_apps.user

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.mikepenz.itemanimators.SlideLeftAlphaAnimator
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.adapter.DetailHistoryAdapter
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.DataDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.ResponseDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_transaksi.DataTransaksi
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import customfonts.MyTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ActivityDetailListHistory constructor() : AppCompatActivity(), InitComponent, View.OnClickListener {
    private val confirm: MyTextView? = null
    private var mContext: Context? = null
    private var recyclerCart: RecyclerView? = null
    //Declare Adapter
    private var mAdapter: DetailHistoryAdapter? = null
    private val pDialog: SweetAlertDialog? = null
    //Declare Object Users
    var dataTransaksi: ResponseDetailTransaksi? = null
    var listDetailTransaksi: MutableList<DataDetailTransaksi> = ArrayList()
    var transaksi: DataTransaksi? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_detail_list_history)
        val gson: Gson = Gson()
        transaksi = gson.fromJson(getIntent().getStringExtra("transaksi"), DataTransaksi::class.java)
        mContext = this
        startInit()
    }

    public override fun startInit() {
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    public override fun initToolbar() {
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar().setTitle(transaksi.getKODETRANSAKSI())
    }

    public override fun initUI() {
        recyclerCart = findViewById(R.id.rCartList) as RecyclerView?
    }

    public override fun initValue() {
        prepareCart()
        getTransaksi()
        mAdapter!!.notifyDataSetChanged()
        //        setCheckout();
    }

    public override fun initEvent() {}
    private fun prepareCart() {
        mAdapter = DetailHistoryAdapter((mContext)!!, listDetailTransaksi)
        recyclerCart!!.setHasFixedSize(true)
        recyclerCart!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerCart!!.setItemAnimator(DefaultItemAnimator())
        recyclerCart!!.setAdapter(mAdapter)
        recyclerCart!!.setItemAnimator(SlideLeftAlphaAnimator())
        recyclerCart!!.getItemAnimator().setAddDuration(500)
        recyclerCart!!.getItemAnimator().setRemoveDuration(500)
    }

    public override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun getTransaksi() {
        val users: Call<ResponseDetailTransaksi?>? = client.getApi().dataDetailTransaksi(transaksi.getKODETRANSAKSI())
        users!!.enqueue(object : Callback<ResponseDetailTransaksi?> {
            public override fun onResponse(call: Call<ResponseDetailTransaksi?>, response: Response<ResponseDetailTransaksi?>) {
                dataTransaksi = response.body()
                if (response.isSuccessful()) {
                    if (dataTransaksi.getStatus()) {
                        listDetailTransaksi.clear()
                        listDetailTransaksi.addAll(dataTransaksi.getData())
                        mAdapter!!.notifyDataSetChanged()
                    } else {
                        listDetailTransaksi.clear()
                        mAdapter!!.notifyDataSetChanged()
                        Toast.makeText(mContext, "Tidak Ada Data Ditemukan", Toast.LENGTH_LONG).show()
                    }
                } else {
                    listDetailTransaksi.clear()
                    mAdapter!!.notifyDataSetChanged()
                    Toast.makeText(mContext, "Tidak Ada Data Ditemukan", Toast.LENGTH_LONG).show()
                }
            }

            public override fun onFailure(call: Call<ResponseDetailTransaksi?>, t: Throwable) {
                Toast.makeText(mContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    public override fun onClick(view: View) {
        when (view.getId()) {
        }
    }
}