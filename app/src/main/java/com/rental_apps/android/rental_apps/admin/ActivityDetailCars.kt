package com.rental_apps.android.rental_apps.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.helper.DrawableColor
import com.rental_apps.android.rental_apps.model.model_mobil.DataCars
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.squareup.picasso.Picasso
import customfonts.MyTextView

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityDetailCars : AppCompatActivity(), InitComponent, View.OnClickListener {
    private var mContext: Context? = null
    private var car: DataCars? = null
    private var toolbar: Toolbar? = null
    private var merk: TextView? = null
    private var year: TextView? = null
    private var capacity: TextView? = null
    private var plat: TextView? = null
    private var warna_mobil: TextView? = null
    private var bensin_mobil: TextView? = null
    private var price: TextView? = null
    private var description: MyTextView? = null
    private var nama_mobil: MyTextView? = null
    private var ic_merk: ImageView? = null
    private var ic_year: ImageView? = null
    private var ic_capacity: ImageView? = null
    private var ic_plat: ImageView? = null
    private var ic_warna_mobil: ImageView? = null
    private var ic_bensin_mobil: ImageView? = null
    private var action_mobil: FloatingActionButton? = null
    private var mainbackdrop: ImageView? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_detail_cars)
        mContext = this
        val gson = Gson()
        car = gson.fromJson(intent.getStringExtra("car"), DataCars::class.java)
        startInit()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.action_mobil -> {
                val gson = Gson()
                val datacar = gson.toJson(car)
                val i = Intent(view.context, ActivityEditMobil::class.java)
                i.putExtra("car", datacar)
                view.context.startActivity(i)
            }
        }
    }

    override fun startInit() {
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    override fun initToolbar() {
        toolbar = findViewById(R.id.maintoolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar.setTitle(car.getNAMAMOBIL())
    }

    override fun initUI() {
        merk = findViewById(R.id.merk) as TextView
        year = findViewById(R.id.year) as TextView
        plat = findViewById(R.id.plat) as TextView
        price = findViewById(R.id.price) as TextView
        capacity = findViewById(R.id.capacity) as TextView
        warna_mobil = findViewById(R.id.warna) as TextView
        bensin_mobil = findViewById(R.id.bensin) as TextView
        description = findViewById(R.id.description) as MyTextView
        nama_mobil = findViewById(R.id.nama_mobil) as MyTextView
        ic_merk = findViewById(R.id.ic_merk) as ImageView
        ic_year = findViewById(R.id.ic_year) as ImageView
        ic_plat = findViewById(R.id.ic_plat) as ImageView
        ic_capacity = findViewById(R.id.ic_capacity) as ImageView
        ic_warna_mobil = findViewById(R.id.ic_warna) as ImageView
        ic_bensin_mobil = findViewById(R.id.ic_bensin) as ImageView
        mainbackdrop = findViewById(R.id.mainbackdrop) as ImageView
        action_mobil = findViewById(R.id.action_mobil) as FloatingActionButton
        val yearIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_go_to_today)
        val capacityIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_cc_bcc)
        val colorIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_picture)
        val fuelIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_fuel)
        val merkIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_storage)
        val platIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_screen_locked_to_landscape)
        ic_year!!.setImageDrawable(DrawableColor.setColor(yearIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_capacity!!.setImageDrawable(DrawableColor.setColor(capacityIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_warna_mobil!!.setImageDrawable(DrawableColor.setColor(colorIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_bensin_mobil!!.setImageDrawable(DrawableColor.setColor(fuelIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_merk!!.setImageDrawable(DrawableColor.setColor(merkIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_plat!!.setImageDrawable(DrawableColor.setColor(platIcon, R.color.nliveo_orange_colorPrimaryDark))
    }

    override fun initValue() {
        description.setText(car.getDESKRIPSIMOBIL())
        merk.setText(car.getMERKMOBIL())
        year.setText(car.getTAHUNMOBIL())
        capacity.setText(car.getKAPASITASMOBIL())
        plat.setText(car.getPLATNOMOBIL())
        warna_mobil.setText(car.getWARNAMOBIL())
        bensin_mobil.setText(car.getBENSINMOBIL())
        price!!.text = "Rp. " + String.format("%,.2f", car.getHARGAMOBIL().toString().toDouble())
        nama_mobil.setText(car.getNAMAMOBIL())
        if (car.getIMAGE().size > 0) Picasso.with(mContext).load(client.getBaseImg() + "mobil/" + car.getIMAGE()[0]).into(mainbackdrop)
    }

    override fun initEvent() {
        action_mobil!!.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}