package com.rental_apps.android.rental_apps

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.pixplicity.easyprefs.library.Prefs
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.SPreferenced.SPref.getPASSWORD
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.model.model_user.ResponseLogin
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.user.SplashActivity
import com.rental_apps.android.rental_apps.user.UserMain
import com.rental_apps.android.rental_apps.utils.move
import com.rental_apps.android.rental_apps.utils.validate
import customfonts.MyEditText
import customfonts.MyTextView
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLogin constructor() : AppCompatActivity(), InitComponent, View.OnClickListener {
    //declare componenr
    private var et_username: MyEditText? = null
    private var et_password: MyEditText? = null
    private var btn_login: MyTextView? = null
    private var txt_register: MyTextView? = null
    private var logofont: TextView? = null
    private val coordinatorlayout: CoordinatorLayout? = null
    //declare context
    private var mContext: Context? = null
    //declate variable
    private var userData: DataUser? = null
    //declare sweet alert
//   private SweetAlertDialog pDialog;
    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext = this
        startInit()
    }

    public override fun startInit() {
        if (Prefs.getInt(SPref.getGroupUser(), 0) == 1) {
            move.moveActivity(mContext, UserMain::class.java)
            finish()
        }
        if (Prefs.getInt(SPref.getGroupUser(), 0) == 2) {
            move.moveActivity(mContext, UserMain::class.java)
            finish()
        }
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    public override fun initToolbar() {
        getSupportActionBar()!!.hide()
    }

    public override fun initUI() {
        et_username = findViewById(R.id.et_username) as MyEditText?
        et_password = findViewById(R.id.et_password) as MyEditText?
        btn_login = findViewById(R.id.btn_login) as MyTextView?
        txt_register = findViewById(R.id.txt_register) as MyTextView?
        logofont = findViewById(R.id.logofont) as TextView?
        val custom_fonts: Typeface = Typeface.createFromAsset(getAssets(), "fonts/ArgonPERSONAL-Regular.otf")
        logofont!!.setTypeface(custom_fonts)
    }

    public override fun initValue() {}
    public override fun initEvent() {
        btn_login!!.setOnClickListener(this)
        txt_register!!.setOnClickListener(this)
    }

    public override fun onClick(view: View) {
        when (view.getId()) {
            R.id.btn_login -> if (validate_login()) login()
            R.id.txt_register -> move.moveActivity(mContext, ActivityRegister::class.java)
        }
    }

    fun validate_login(): Boolean {
        return if ((!validate.cek(et_username) && !validate.cek(et_password))) true else false
    }

    fun login() {
        pDialog = ProgressDialog(this)
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog!!.setMessage("Loading")
        pDialog!!.setCancelable(false)
        // pDialog.setIndeterminate(false);
        pDialog!!.show()
        val user: Call<ResponseLogin?>? = client.getApi().auth(et_username!!.getText().toString(), et_password!!.getText().toString())
        user!!.enqueue(object : Callback<ResponseLogin> {
            public override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                pDialog!!.hide()
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        userData = response.body().getData()
                        Toasty.success((mContext)!!, "login berhasil", Toast.LENGTH_LONG).show()
                        Log.d("data user", userData.toString())
                        setPreference(userData)
                        if ((userData?.getGroup_user() ?:  == 1)) move.moveActivity(mContext, SplashActivity::class.java) else move.moveActivity(mContext, SplashActivity::class.java)
                        finish()
                    } else {
                        Toasty.error((mContext)!!, "Username dan password salah", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toasty.error((mContext)!!, "Username dan password salah", Toast.LENGTH_LONG).show()
                };
            }

            public override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                pDialog!!.hide()
                //                new ProgressDialog(mContext)
//                        .setTitle("Oops...")
//                        .d("Koneksi bermasalah!")
//                        .show();
//                pDialog = new ProgressDialog(ActivityLogin.this);
//                //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setMessage("Tidak ada koneksi");
//                pDialog.show();
                Toasty.success((mContext)!!, "Koneksi Tidak ada", Toast.LENGTH_LONG).show()
                if (pDialog!!.isShowing()) pDialog!!.dismiss()
            }
        })
    }

    private fun setPreference(du: DataUser?) {
        du?.getId_user()?.let { Prefs.putInt(SPref.getIdUser(), it) }
        Prefs.putString(SPref.getUSERNAME(), du?.getUsername())
        Prefs.putString(SPref.getNAME(), du?.getName())
        Prefs.putString(SPref.getEMAIL(), du?.getEmail())
        Prefs.putString(SPref.getNoTelp(), du?.getNo_telp())
        Prefs.putString(SPref.getJenisKelamin(), du?.getJenis_kelamin().toString())
        Prefs.putString(SPref.getPHOTO(), du?.getPhoto())
        Prefs.putString(SPref.getLastUpdate(), du?.getLast_update().toString())
        Prefs.putString(SPref.getALAMAT(), du?.getAlamat())
        du?.getGroup_user()?.let { Prefs.putInt(SPref.getGroupUser(), it) }
        Prefs.putString(getPASSWORD(), du?.getPassword().toString())
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<ResponseLogin>) {

}

