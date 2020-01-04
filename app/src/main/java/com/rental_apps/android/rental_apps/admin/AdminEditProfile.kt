package com.rental_apps.android.rental_apps.admin

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.helper.Hash
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.model.model_user.ResponseRegister
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.utils.validate
import customfonts.MyEditText
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by Muhajir on 07/10/2017.
 */
/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class AdminEditProfile : AppCompatActivity(), InitComponent, View.OnClickListener {
    private var name: MyEditText? = null
    private var nik: MyEditText? = null
    private var email: MyEditText? = null
    private var noTelp: MyEditText? = null
    private var address: MyEditText? = null
    private var jenis_kelamin: MyEditText? = null
    private var status: MyEditText? = null
    private var username: MyEditText? = null
    private var old_password: MyEditText? = null
    private var new_password: MyEditText? = null
    private var confirm_password: MyEditText? = null
    private var userPhoto: CircleImageView? = null
    private var update: Button? = null
    private var pDialog: SweetAlertDialog? = null
    private var JK: String? = null
    var mContext: Context? = null
    var toolbar: Toolbar? = null
    var user: DataUser? = null
    private var ket = false
    var selectedImage: Uri? = null
    var filePath = ""
    private var encodedImage: String? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_edit_user)
        val gson = Gson()
        user = gson.fromJson(intent.getStringExtra("user"), DataUser::class.java)
        mContext = this
        startInit()
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
        val upArrow = resources.getDrawable(R.drawable.ic_action_back)
        upArrow.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = ""
    }

    override fun initUI() {
        name = findViewById(R.id.name) as MyEditText
        nik = findViewById(R.id.nik) as MyEditText
        email = findViewById(R.id.email) as MyEditText
        noTelp = findViewById(R.id.notelp) as MyEditText
        address = findViewById(R.id.address) as MyEditText
        jenis_kelamin = findViewById(R.id.jenis_kelamin) as MyEditText
        status = findViewById(R.id.status) as MyEditText
        username = findViewById(R.id.username) as MyEditText
        old_password = findViewById(R.id.old_password) as MyEditText
        new_password = findViewById(R.id.password) as MyEditText
        confirm_password = findViewById(R.id.confirm_password) as MyEditText
        userPhoto = findViewById(R.id.userPhoto) as CircleImageView
        update = findViewById(R.id.btn_update) as Button
    }

    override fun initValue() {
        name!!.setText(Prefs.getString(SPref.getNAME(), ""))
        nik!!.setText(Prefs.getString(SPref.getNIK(), ""))
        email!!.setText(Prefs.getString(SPref.getEMAIL(), ""))
        noTelp!!.setText(Prefs.getString(SPref.getNoTelp(), ""))
        address!!.setText(Prefs.getString(SPref.getALAMAT(), ""))
        username!!.setText(Prefs.getString(SPref.getUSERNAME(), ""))
        JK = Prefs.getString(SPref.getJenisKelamin(), "")
        if (Prefs.getString(SPref.getJenisKelamin(), "") == 'L') {
            jenis_kelamin!!.setText("Laki-laki")
        } else {
            jenis_kelamin!!.setText("Perempuan")
        }
        status!!.setText("Aktif")
        //        if(!Prefs.getString(SPref.getPHOTO(),null).isEmpty())
//
// Picasso.with(mContext).load(client.getBaseUrlImage()+Prefs.getString(SPref.getPHOTO(),null)).into(userPhoto);
    }

    override fun initEvent() {
        old_password!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (Hash.MD5(old_password!!.text.toString()) != Prefs.getString(SPref.getPASSWORD(), "")) {
                    var focusView: View? = null
                    old_password!!.error = "Password tidak sama"
                    focusView = old_password
                    focusView!!.requestFocus()
                    ket = false
                } else {
                    ket = true
                }
            }
        })
        update!!.setOnClickListener(this)
        userPhoto!!.setOnClickListener(this)
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

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_register -> if (validasi()) register()
            R.id.userPhoto -> ChooseGallerOrCamera()
            R.id.jkl -> JK = "L"
            R.id.jkp -> JK = "P"
            R.id.btn_update -> if (validasi()) register()
        }
    }

    private fun register() {
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog!!.titleText = "Loading"
        pDialog!!.setCancelable(false)
        pDialog!!.show()
        val register: Call<ResponseRegister?>?
        //        Toasty.success(mContext,Prefs.getString(SPref.getIdUser(),""),Toast.LENGTH_SHORT).show();
        register = client.getApi().userUpdate("" + Prefs.getInt(SPref.getIdUser(), 0), name!!.text.toString(),
                nik!!.text.toString(),
                username!!.text.toString(),
                email!!.text.toString(),
                noTelp!!.text.toString(),
                JK,
                address!!.text.toString(),
                new_password!!.text.toString(),
                1, Prefs.getInt(SPref.getGroupUser(), 0), encodedImage)
        register.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                pDialog!!.hide()
                if (response.isSuccessful) {
                    if (response.body().status) {
                        SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Info")
                                .setContentText("Akun Berhasil Di Update!")
                                .show()
                        setPreference(response.body().data)
                    } else {
                        SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Info")
                                .setContentText("Akun Gagal Di Update!")
                                .show()
                    }
                } else {
                    SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Info")
                            .setContentText("Akun Gagal Di Update!")
                            .show()
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                pDialog!!.hide()
                SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Koneksi bermasalah!")
                        .show()
            }
        })
    }

    private fun validasi(): Boolean {
        return if (!validate.cek(name)
                && !validate.cek(nik)
                && !validate.cek(username)
                && !validate.cek(email)
                && !validate.cek(noTelp)
                && !validate.cek(address)
                && !validate.cek(old_password) && ket) {
            if (validate.cekPassword(confirm_password, new_password!!.text.toString(), confirm_password!!.text.toString())) {
                false
            } else {
                true
            }
        } else {
            false
        }
    }

    private fun setPreference(du: DataUser?) {
        Prefs.putInt(SPref.getIdUser(), du.getId_user())
        Prefs.putString(SPref.getNIK(), du.getNik())
        Prefs.putString(SPref.getUSERNAME(), du.getUsername())
        Prefs.putString(SPref.getNAME(), du.getName())
        Prefs.putString(SPref.getEMAIL(), du.getEmail())
        Prefs.putString(SPref.getNoTelp(), du.getNo_telp())
        Prefs.putString(SPref.getJenisKelamin(), du.getJenis_kelamin().toString())
        Prefs.putString(SPref.getPHOTO(), du.getPhoto())
        Prefs.putString(SPref.getLastUpdate(), du.getLast_update().toString())
        Prefs.putString(SPref.getALAMAT(), du.getAlamat())
        Prefs.putInt(SPref.getGroupUser(), du.getGroup_user())
        Prefs.putString(SPref.getPASSWORD(), du.getPassword().toString())
    }

    private fun ChooseGallerOrCamera() {
        val items = arrayOf<CharSequence>("Choose from Gallery",
                "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo")
        builder.setItems(items, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, item: Int) {
                if (items[item] == "Take Photo") {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    var photo: File? = null
                    try { // place where to store camera taken picture
                        photo = createTemporaryFile("picture", ".jpg")
                        photo.delete()
                    } catch (e: Exception) {
                    }
                    selectedImage = Uri.fromFile(File("/sdcard/sample.jpg"))
                    cameraIntent.putExtra("ket", "1")
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage)
                    startActivityForResult(cameraIntent, REQUEST_CAMERA)
                } else if (items[item] == "Choose from Gallery") {
                    val intent = Intent()
                    intent.type = "image/*" //set type for files (image type)
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY)
                } else if (items[item] == "Cancel") {
                    dialog.dismiss()
                }
            }

            @Throws(Exception::class)
            private fun createTemporaryFile(part: String, ext: String): File {
                var tempDir = Environment.getExternalStorageDirectory()
                tempDir = File(tempDir.absolutePath + "/.temp/")
                if (!tempDir.exists()) {
                    tempDir.mkdirs()
                }
                return File.createTempFile(part, ext, tempDir)
            }
        })
        builder.show()
    }

    fun getImageUri(inContext: Context, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri?): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

    private fun getRealPathFromURIPath(contentURI: Uri?, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI!!.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                val cr = this.contentResolver
                var photo: Bitmap? = null
                try {
                    photo = MediaStore.Images.Media.getBitmap(cr, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                //                selectedImage = getImageUri(getApplicationContext(), photo);
//                filePath=getRealPathFromURI(selectedImage);
//                setImageView(filePath);
            } else if (resultCode == Activity.RESULT_CANCELED) { // user cancelled Image capture
                Toast.makeText(applicationContext,
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show()
            } else { // failed to capture image
                Toast.makeText(applicationContext,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show()
            }
        } else if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.data
                //                tampil_gambar_sk_hilang.setImageURI(selectedImage);
                filePath = getRealPathFromURIPath(selectedImage, this)
                setImageView(filePath)
            }
        }
    }

    private fun setImageView(filepath: String) {
        val imgFile = File(filepath)
        val bm = BitmapFactory.decodeFile(filepath)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val ba = baos.toByteArray()
        encodedImage = Base64.encodeToString(ba, Base64.DEFAULT)
        if (imgFile.exists()) {
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            val f = File(filepath)
            var b: Bitmap? = null
            try {
                var fis = FileInputStream(f)
                BitmapFactory.decodeStream(fis, null, o)
                fis.close()
                var sc = 0.0f
                var scale = 1
                //if image height is greater than width
                if (o.outHeight > o.outWidth) {
                    sc = o.outHeight / 400.toFloat()
                    scale = Math.round(sc)
                } else {
                    sc = o.outWidth / 400.toFloat()
                    scale = Math.round(sc)
                }
                // Decode with inSampleSize
                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale
                fis = FileInputStream(f)
                b = BitmapFactory.decodeStream(fis, null, o2)
                //                if (ketImage==1) {
//                    tampil_gambar_rt.setImageBitmap(b);
//                    listOfImagesPath.set(0,filepath);
//                }else if (ketImage==2) {
//                    tampil_gambar_kk.setImageBitmap(b);
//                    listOfImagesPath.set(1,filepath);
//                }else if (ketImage==3) {
//                    tampil_gambar_nikah.setImageBitmap(b);
//                    listOfImagesPath.set(2,filepath);
//                }else if (ketImage==4) {
//                    tampil_gambar_akta.setImageBitmap(b);
//                    listOfImagesPath.set(3,filepath);
//                }else if (ketImage==5) {
//                    tampil_gambar_master_kk.setImageBitmap(b);
//                    listOfImagesPath.set(4,filepath);
//                }
                userPhoto!!.setImageBitmap(b)
                fis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 1888
        private const val SELECT_FILE = 1887
        private const val PICK_FROM_GALLERY = 2
    }
}