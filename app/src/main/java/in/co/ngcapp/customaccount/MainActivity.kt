package `in`.co.ngcapp.customaccount

import `in`.co.ngcapp.customaccount.fragments.CreateCustomer
import `in`.co.ngcapp.customaccount.fragments.ShoeCustomer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {


    var create_contact: TextView? = null
    var show_contact: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        create_contact = findViewById(R.id.create_contact)
        show_contact = findViewById(R.id.show_contact)
        create_contact!!.setOnClickListener(this)
        show_contact!!.setOnClickListener(this)
        callCreateContact()
        create_contact!!.setTextColor(resources.getColor(R.color.colorAccent))
        show_contact!!.setTextColor(resources.getColor(R.color.black))
    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {
            R.id.create_contact -> {
                callCreateContact()
                create_contact!!.setTextColor(resources.getColor(R.color.colorAccent))
                show_contact!!.setTextColor(resources.getColor(R.color.black))

            }

            R.id.show_contact -> {
                callProfile()
                show_contact!!.setTextColor(resources.getColor(R.color.colorAccent))
                create_contact!!.setTextColor(resources.getColor(R.color.black))
            }
        }
    }


    fun callCreateContact() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, CreateCustomer())
        ft.commit()


    }


    fun callProfile() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, ShoeCustomer())
        ft.commit()


    }
}
