package `in`.co.ngcapp.customaccount.fragments


import `in`.co.ngcapp.customaccount.R
import `in`.co.ngcapp.customaccount.Utility.PermissionCheck
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    var click_imageview:ImageView?= null
    private var userChoosenTask: String? = ""
    private var GALLERY: String? = null
    private var base64EncodedString = ""
    private var profile_image:CircleImageView?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_create_customer, container, false)
        click_imageview = view.findViewById(R.id.click_imageview)
        profile_image   = view.findViewById(R.id.profile_image)
        click_imageview!!.setOnClickListener(this)
        return  view
    }

    override fun onClick(p0: View?) {
        when (p0!!.id){
            R.id.click_imageview ->{
                val result = PermissionCheck.checkPermission(this.context!!)

                if (result == false) {
                    PermissionCheck.requestPermission(this.context!!)
                    return
                }
                else{
                    SelectImages()


                }

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

        val destination = File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg")
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

}
