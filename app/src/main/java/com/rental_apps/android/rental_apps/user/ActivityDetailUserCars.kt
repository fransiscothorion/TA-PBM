package com.rental_apps.android.rental_apps.user

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.Toolbar
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.adapter.Carts.getSize
import com.rental_apps.android.rental_apps.adapter.Carts.order
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.helper.DateDifference
import com.rental_apps.android.rental_apps.helper.DatePickerView
import com.rental_apps.android.rental_apps.helper.DrawableColor
import com.rental_apps.android.rental_apps.helper.DrawableCounter
import com.rental_apps.android.rental_apps.model.model_carts.DataCarts
import com.rental_apps.android.rental_apps.model.model_mobil.DataCars
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.utils.move
import com.rental_apps.android.rental_apps.utils.validate
import com.squareup.picasso.Picasso
import customfonts.MyTextView
import es.dmoral.toasty.Toasty
import java.util.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityDetailUserCars() : AppCompatActivity(), InitComponent, View.OnClickListener {
    private var mContext: Context? = null
    private var car: DataCars? = null
    private var toolbar: Toolbar? = null
    private var description: MyTextView? = null
    private var merk: TextView? = null
    private var year: TextView? = null
    private var capacity: TextView? = null
    private var plat: TextView? = null
    private var warna_mobil: TextView? = null
    private var bensin_mobil: TextView? = null
    private var price: TextView? = null
    private var nama_mobil: MyTextView? = null
    private var ic_merk: ImageView? = null
    private var ic_year: ImageView? = null
    private var ic_capacity: ImageView? = null
    private var ic_plat: ImageView? = null
    private var ic_warna_mobil: ImageView? = null
    private var ic_bensin_mobil: ImageView? = null
    private var mainbackdrop: ImageView? = null
    private var tgl_awal: DatePickerView? = null
    private var tgl_akhir: DatePickerView? = null
    private var add_cart: MyTextView? = null
    private var tvTimeResult: TextView? = null
    private var btTimePicker: Button? = null
    private var timePickerDialog: TimePickerDialog? = null
    private var jam: String? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_user_detail_cars)
        mContext = this
        val gson = Gson()
        car = gson.fromJson(intent.getStringExtra("car"), DataCars::class.java)
        startInit()
        tvTimeResult = findViewById(R.id.tv_timeresult) as TextView
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
        btTimePicker = findViewById(R.id.bt_showtimepicker) as Button
        nama_mobil = findViewById(R.id.nama_mobil) as MyTextView
        ic_merk = findViewById(R.id.ic_merk) as ImageView
        ic_year = findViewById(R.id.ic_year) as ImageView
        ic_plat = findViewById(R.id.ic_plat) as ImageView
        ic_capacity = findViewById(R.id.ic_capacity) as ImageView
        ic_warna_mobil = findViewById(R.id.ic_warna) as ImageView
        ic_bensin_mobil = findViewById(R.id.ic_bensin) as ImageView
        mainbackdrop = findViewById(R.id.mainbackdrop) as ImageView
        tgl_awal = findViewById(R.id.tgl_awal) as DatePickerView
        tgl_akhir = findViewById(R.id.tgl_akhir) as DatePickerView
        //        jam =(TimePicker) findViewById(R.id.jam);
        add_cart = findViewById(R.id.add_cart) as MyTextView
        val yearIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_go_to_today)
        val capacityIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_cc_bcc)
        val colorIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_picture)
        val fuelIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_action_brightness_auto)
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
        // bensin_mobil.setText(car.getBENSINMOBIL());
        if (car.getBENSINMOBIL().toString().toInt() == 1) {
            bensin_mobil!!.text = "Autometic"
        } else {
            bensin_mobil!!.text = "Manual"
        }
        price!!.text = "Rp. " + String.format("%,.2f", car.getHARGAMOBIL().toString().toDouble())
        nama_mobil.setText(car.getNAMAMOBIL())
        if (car.getIMAGE().size > 0) Picasso.with(mContext).load(client.getBaseImg() + "mobil/" + car.getIMAGE()[0]).into(mainbackdrop)
        if (car.getSTATUSSEWA().toString().toInt() == 1) {
            add_cart!!.text = "Sedang Disewa"
            add_cart!!.setBackgroundResource(R.drawable.roundred)
            add_cart!!.isEnabled = false
            tgl_awal!!.isEnabled = false
            tgl_akhir!!.isEnabled = false
            btTimePicker!!.isEnabled = false
        }
    }

    override fun initEvent() {
        add_cart!!.setOnClickListener(this)
        btTimePicker!!.setOnClickListener { showTimeDialog() }
    }

    private fun showTimeDialog() {
        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        val calendar = Calendar.getInstance()
        /**
         * Initialize TimePicker Dialog
         */
        timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            /**
             * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
             */
            /**
             * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
             */
            jam = "$hourOfDay:$minute:00"
            tvTimeResult!!.text = "Jam dipilih: $jam"
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE],
                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */
                DateFormat.is24HourFormat(this))
        timePickerDialog!!.show()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.add_cart -> if (validasi()) {
                if ((DateDifference.betweenDates(tgl_awal!!.text.toString(), tgl_akhir!!.text.toString()) + 1) > 1) {
                    order(DataCarts(car.getIDMOBIL(), car.getNAMAMOBIL(), car.getMERKMOBIL(), car.getPLATNOMOBIL(), tgl_awal!!.text.toString() + " " + jam, tgl_akhir!!.text.toString() + " " + jam, car.getHARGAMOBIL(), "" + (car.getHARGAMOBIL().toInt() * (DateDifference.betweenDates(tgl_awal!!.text.toString(), tgl_akhir!!.text.toString())))), SPref.getCARTS())
                    invalidateOptionsMenu()
                    Toasty.success((mContext)!!, "Mobil Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toasty.error((mContext)!!, "Inputan Tanggal Tidak Sesuai", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean { // TODO Auto-generated method stub
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        menuInflater.inflate(R.menu.menu_user_icon, menu)
        setCart(menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun setCart(menu: Menu) {
        val menuItem = menu.findItem(R.id.cart)
        val menuRefresh = menu.findItem(R.id.refresh)
        val menuSetting = menu.findItem(R.id.action_settings)
        menuRefresh.isVisible = false
        menuSetting.isVisible = false
        val icon = menuItem.icon as LayerDrawable
        val badge: DrawableCounter
        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_group_count)
        if (reuse != null && reuse is DrawableCounter) {
            badge = reuse
        } else {
            badge = DrawableCounter(mContext)
        }
        badge.setCount("" + getSize(SPref.getCARTS()))
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_group_count, badge)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.cart -> {
                move.moveActivity(mContext, ActivityListTransaksi::class.java)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun validasi(): Boolean {
        return if ((!validate.cek(tgl_awal) && !validate.cek(tgl_akhir))) true else false
    }

    public override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }
}