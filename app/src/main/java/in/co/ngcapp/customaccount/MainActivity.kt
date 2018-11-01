package `in`.co.ngcapp.customaccount

import `in`.co.ngcapp.customaccount.fragments.CreateCustomer
import `in`.co.ngcapp.customaccount.fragments.ShoeCustomer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {


    var create_contact: TextView? = null
    var show_contact: TextView? = null
    var create_contact_image: ImageView? = null
    var profile: ImageView? = null
    var header: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        create_contact = findViewById(R.id.create_contact)
        show_contact = findViewById(R.id.show_contact)
        create_contact_image = findViewById(R.id.create_contact_image)
        profile = findViewById(R.id.profile)
        header = findViewById(R.id.header)
        create_contact!!.setOnClickListener(this)
        show_contact!!.setOnClickListener(this)
        callCreateContact()
//        create_contact!!.setTextColor(resources.getColor(R.color.colorAccent))
//        show_contact!!.setTextColor(resources.getColor(R.color.black))


    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {
            R.id.create_contact -> {
                callCreateContact()


            }

            R.id.show_contact -> {
                callProfile()

            }
        }
    }


    fun callCreateContact() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, CreateCustomer())
        ft.commit()
        create_contact!!.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        show_contact!!.setTextColor(resources.getColor(R.color.black))
        header!!.setText("Create Account ")
        create_contact_image!!.setColorFilter(this.getResources().getColor(R.color.colorPrimaryDark))
        profile!!.setColorFilter(this.getResources().getColor(R.color.black))

    }


    fun callProfile() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, ShoeCustomer())
        ft.commit()
        show_contact!!.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        create_contact!!.setTextColor(resources.getColor(R.color.black))
        header!!.setText("Manage Users ")

        create_contact_image!!.setColorFilter(this.getResources().getColor(R.color.black))
        profile!!.setColorFilter(this.getResources().getColor(R.color.colorPrimaryDark))

    }
}
