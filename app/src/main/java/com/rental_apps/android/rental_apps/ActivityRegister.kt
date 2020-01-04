package com.rental_apps.android.rental_apps

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.model.model_user.ResponseRegister
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.utils.validate
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityRegister constructor() : AppCompatActivity(), InitComponent, View.OnClickListener {
    //declare component
    private var etNama: EditText? = null
    private var etNik: EditText? = null
    private var etUsername: EditText? = null
    private var etNumber: EditText? = null
    private var etAlamat: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etConfirmPassword: EditText? = null
    private var JK: Char? = null
    private var rbl: RadioButton? = null
    private var rbp: RadioButton? = null
    private var btnRegister: Button? = null
    private val coordinatorLayout: CoordinatorLayout? = null
    //declare context
    private var mContext: Context? = null
    //declare variable
    private val userData: DataUser? = null
    //declare sweet alert
// private SweetAlertDialog pDialog;
    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
        getSupportActionBar()!!.hide()
    }

    public override fun initUI() {
        etNama = findViewById(R.id.et_nama) as EditText?
        etNik = findViewById(R.id.et_nik) as EditText?
        etEmail = findViewById(R.id.et_email) as EditText?
        etNumber = findViewById(R.id.et_no_telp) as EditText?
        etAlamat = findViewById(R.id.et_alamat) as EditText?
        etUsername = findViewById(R.id.et_username) as EditText?
        etPassword = findViewById(R.id.et_password) as EditText?
        etConfirmPassword = findViewById(R.id.et_confirm_password) as EditText?
        rbl = findViewById(R.id.jkl) as RadioButton?
        rbp = findViewById(R.id.jkp) as RadioButton?
        btnRegister = findViewById(R.id.btn_register) as Button?
    }

    public override fun initValue() {}
    public override fun initEvent() {
        btnRegister!!.setOnClickListener(this)
        rbl!!.setOnClickListener(this)
        rbp!!.setOnClickListener(this)
    }

    public override fun onClick(view: View) {
        when (view.getId()) {
            R.id.btn_register -> if (validasi()) register()
            R.id.jkl -> {
                JK = 'L'
                rbp!!.setChecked(false)
            }
            R.id.jkp -> {
                JK = 'P'
                rbl!!.setChecked(false)
            }
        }
    }

    private fun register() {
        pDialog = ProgressDialog(this)
        // pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog!!.setMessage("Loading")
        pDialog!!.setCancelable(false)
        pDialog!!.show()
        val register: Call<ResponseRegister?>?
        register = client.getApi().userRegister(etNama!!.getText().toString(),
                etNik!!.getText().toString(),
                etUsername!!.getText().toString(),
                etEmail!!.getText().toString(),
                etNumber!!.getText().toString(),
                JK,
                etAlamat!!.getText().toString(),
                etPassword!!.getText().toString(),
                1, 2)
        register.enqueue(object : Callback<ResponseRegister> {
            public override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                pDialog!!.hide()
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toasty.success((mContext)!!, "Berhasil Dibuat", Toast.LENGTH_LONG).show()
                    } else {
                        Toasty.success((mContext)!!, "Gagal dibuat", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toasty.error((mContext)!!, "Gagal dibuat", Toast.LENGTH_LONG).show()
                }
            }

            public override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                pDialog!!.hide()
                Toasty.success((mContext)!!, "Koneksi bermasalah", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validasi(): Boolean {
        if ((!validate.cek(etNama)
                        && !validate.cek(etNik)
                        && !validate.cek(etUsername)
                        && !validate.cek(etEmail)
                        && !validate.cek(etNumber)
                        && !validate.cek(etAlamat)
                        && !validate.cek(etPassword)
                        && !validate.cek(etConfirmPassword))) {
            if (validate.cekPassword(etConfirmPassword, etPassword!!.getText().toString(), etConfirmPassword!!.getText().toString())) {
                return false
            } else {
                return true
            }
        } else {
            return false
        }
    }
}