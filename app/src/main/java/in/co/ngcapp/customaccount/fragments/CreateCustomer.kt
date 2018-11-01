package `in`.co.ngcapp.customaccount.fragments


import `in`.co.ngcapp.customaccount.R
import `in`.co.ngcapp.customaccount.Utility.PermissionCheck
import `in`.co.ngcapp.customaccount.database.SqliteDatabase
import `in`.co.ngcapp.customaccount.interfaces.DialogClicks
import `in`.co.ngcapp.dialog.DialogLocations
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.io.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CreateCustomer : Fragment(), View.OnClickListener {

    var click_imageview: ImageView? = null
    private var userChoosenTask: String? = ""
    private var GALLERY: String? = null
    private var base64EncodedString = ""
    private var profile_image: CircleImageView? = null


    var input_username: EditText? = null
    var input_phone_number: EditText? = null
    var date_of_birth: EditText? = null
    var input_address: EditText? = null
    var input_income: EditText? = null
    var submit: Button? = null
    var input_layout_username: TextInputLayout? = null
    var input_layout_mobile: TextInputLayout? = null
    var error_date_of_birth: TextView? = null
    var input_layout_address: TextInputLayout? = null
    var error_income: TextView? = null
    var getname: String? = null
    var getPhoneNumber: String? = null
    var getDateOfBirth: String? = null
    var getAddress: String? = null
    var getInCome: String? = null
    var file: TextView? = null
    var file_image: ImageView? = null

    var pathName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_customer, container, false)
        click_imageview = view.findViewById(R.id.click_imageview)
        profile_image = view.findViewById(R.id.profile_image)
        input_username = view.findViewById(R.id.input_username)
        input_phone_number = view.findViewById(R.id.input_phone_number)
        input_address = view.findViewById(R.id.input_address)
        date_of_birth = view.findViewById(R.id.date_of_birth)
        input_income = view.findViewById(R.id.input_income)
        input_layout_username = view.findViewById<TextInputLayout>(R.id.input_layout_username)
        input_layout_mobile = view.findViewById(R.id.input_layout_mobile)
        error_date_of_birth = view.findViewById(R.id.error_date_of_birth)
        input_layout_address = view.findViewById(R.id.input_layout_address)
        error_income = view.findViewById(R.id.error_income)
        submit = view.findViewById(R.id.submit)
        file = view.findViewById(R.id.file)
        file_image = view.findViewById(R.id.file_image)
        submit!!.setOnClickListener(this)
        input_income!!.setOnClickListener(this)
        date_of_birth!!.setOnClickListener(this)
        input_address!!.setOnClickListener(this)
        input_income!!.setOnClickListener(this)
        file!!.setOnClickListener(this)
        textChangable()
        click_imageview!!.setOnClickListener(this)
        return view
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.click_imageview -> {
                val result = PermissionCheck.checkPermission(this.context!!)

                if (result == false) {
                    PermissionCheck.requestPermission(this.context!!)
                    return
                } else {
                    SelectImages()


                }

            }
            R.id.input_income -> {
                openSalary()
            }
            R.id.submit -> {
                if (getData() == false) {
                    Log.i("Error", "Error")

                } else {
                    savedata(
                        getname.toString(),
                        base64EncodedString,
                        getDateOfBirth.toString(),
                        getPhoneNumber.toString(),
                        getAddress.toString(),
                        getInCome.toString(),
                        ""
                    )
                }
            }
            R.id.date_of_birth -> {
                openCalendar()
            }
            R.id.input_address -> {

            }
            R.id.file -> {
                //


            }
        }
    }

    fun SelectImages() {

        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")

        val builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, which ->
            if (items[which] == "Take Photo") {
                userChoosenTask = "Take Photo"
                cameraIntent()
            } else if (items[which] == "Choose from Library") {
                userChoosenTask = "Choose from Library"
                galleryIntent()
            } else if (items[which] == "Cancel") {
                dialog.cancel()
            }
        }

        builder.show()

    }

    /*
    Camera Intent
     */
    private fun cameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 123)

    }

    fun getData(): Boolean {
        var isValidate: Boolean = true

        getname = input_username!!.text.toString().trim()
        getPhoneNumber = input_phone_number!!.text.toString().trim()
        getDateOfBirth = date_of_birth!!.text.toString().trim()
        getAddress = input_address!!.text.toString().trim()
        getInCome = input_income!!.text.toString().trim()
        if (getname.isNullOrEmpty()) {
            input_layout_username!!.setError("Please Enter Name")

            isValidate = false
        }
        if (getPhoneNumber.isNullOrEmpty()) {
            input_layout_mobile!!.error = "Please Enter Mobile Number"

            isValidate = false
        }
        if (getPhoneNumber!!.length < 10) {
            Log.i("PhoneNUmber", "PhoneNumber")
            input_layout_mobile!!.error = "Please Enter 10 digit Phone Number"
            isValidate = false

        }
        if (getDateOfBirth.isNullOrEmpty()) {
            error_date_of_birth!!.visibility = View.VISIBLE
            isValidate = false
        }
        if (getAddress.isNullOrEmpty()) {
            input_layout_address!!.error = "Please Enter Address"

            isValidate = false
        }
        if (getInCome.isNullOrEmpty()) {
            error_income!!.visibility = View.VISIBLE
            isValidate = false
        }
        if (base64EncodedString.isNullOrEmpty()) {
            Toast.makeText(this.context, "Please select Profile Pic", Toast.LENGTH_SHORT).show()
            isValidate = false

        }



        return isValidate

    }

    fun savedata(
        mCustomerName: String, mCustomerImages: String, mcustomerDateOfBirth: String, mcustomerPhoneNumber: String,
        mCustomerAddress: String, mCustomerIncome: String, mCustomerFile: String
    ) {
        var sqliteDatabase = SqliteDatabase(this.context!!)
        sqliteDatabase.addFav(
            mCustomerName,
            mCustomerImages,
            mcustomerDateOfBirth,
            mcustomerPhoneNumber,
            mCustomerAddress,
            mCustomerIncome,
            mCustomerFile
        )
        Log.i("Submitted", "Submitted")
        refreshData()
        Toast.makeText(this.context, "Successfully user profile is created", Toast.LENGTH_SHORT).show()

    }


    fun openSalary() {
        var dialogSalary = DialogLocations()
        var fragmentManager = fragmentManager
        dialogSalary.onClickListnerListen(object : DialogClicks {
            override fun onClickListner(dateTO: String) {
                input_income!!.setText(dateTO)
                dialogSalary.dismiss()
                error_income!!.visibility = View.GONE

            }

        })
        dialogSalary.show(fragmentManager, "")

    }


    fun openCalendar() {
        var datePickerDialog = DatePickerDialog()
        var fragmentManager = fragmentManager
        datePickerDialog.onClickListner(object : DialogClicks {
            override fun onClickListner(dateTO: String) {
                date_of_birth!!.setText(dateTO)
                error_date_of_birth!!.visibility = View.GONE
            }

        })
        datePickerDialog.show(fragmentManager, "")

    }

    /*
    Gallery Intent
     */
    private fun galleryIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 456)
    }

    /*
    Retreving the Camera and Gallery Image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 456)

                onSelectFromGalleryResult(data)
            else if (requestCode == 123)
                onCaptureImageResult(data)
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        val uri = data.data
        try {
            GALLERY = getPath(activity!!.applicationContext, uri)
            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.applicationContext.getContentResolver(), uri)
            val baos = ByteArrayOutputStream()
            val bm = BitmapFactory.decodeFile(GALLERY)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val byteArrayImage = baos.toByteArray()
            base64EncodedString = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
            Log.i("onSelectGalleryResult", base64EncodedString)
            profile_image!!.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Glide.with(this)
            .load(GALLERY)
            .into(profile_image)
//        sendImages(base64EncodedString)

    }

    /*
    Image Gallery Path
     */
    fun getPath(context: Context, uri: Uri): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    /*
    Capture Image
     */
    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!.get("data") as Bitmap
        val bytes = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val byteArray = bytes.toByteArray()
        base64EncodedString = Base64.encodeToString(byteArray, Base64.DEFAULT)
        Log.i("onCaptureImageResult", base64EncodedString)

        val destination = File(
            Environment.getExternalStorageDirectory(),
            System.currentTimeMillis().toString() + ".jpg"
        )
        val fo: FileOutputStream
        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Glide.with(this)
            .load(destination)
            .into(profile_image)
//
//        sendImages(base64EncodedString)
    }

    fun textChangable() {
        input_username!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0!!.length > 0) {
                    input_layout_username!!.setError(null);


                } else {
                    input_layout_username!!.setError("Please Enter Name")


                }
            }

        })
        input_phone_number!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 0) {
                    input_layout_mobile!!.setError(null);


                } else {
                    input_layout_mobile!!.setError("Please Enter Mobile Number")


                }

            }

        })
        input_address!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 0) {
                    input_layout_address!!.setError(null);


                } else {
                    input_layout_address!!.setError("Please Enter Mobile Number")


                }
            }

        })
    }

    fun refreshData() {
        input_username!!.setText("")
        input_phone_number!!.setText("")
        date_of_birth!!.setText("")
        input_address!!.setText("")
        input_income!!.setText("")
        profile_image!!.setImageDrawable(null)
    }
}
