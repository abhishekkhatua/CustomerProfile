package `in`.co.ngcapp.customaccount.fragments


import `in`.co.ngcapp.customaccount.R
import `in`.co.ngcapp.customaccount.adapter.CustomerList
import `in`.co.ngcapp.customaccount.database.SqliteDatabase
import `in`.co.ngcapp.customaccount.util.Utils
import `in`.co.ngcapp.model.UserData
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.*
import java.util.*
import kotlin.Comparator


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ShoeCustomer : Fragment(), View.OnClickListener {


    var arragList = ArrayList<UserData>()
    var recycler_view_details: RecyclerView? = null
    var header: TextView? = null
    var more: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_shoe_customer, container, false)
        recycler_view_details = view.findViewById<RecyclerView>(R.id.recycler_view_details)
        header = view.findViewById(R.id.header)
        more = view.findViewById(R.id.more)
        header!!.setText("Manage Users ")
        more!!.setOnClickListener(this)
        getSavedData()
        return view
    }


    fun getSavedData() {

        arragList = ArrayList()

        var sq = SqliteDatabase(context!!.applicationContext)
        var data: List<UserData> = sq.getALLDATA()
        for (i in 0 until data.size) {
            var getIds = data[i].keyIds
            var getName = data[i].mName
            var getPhoneNumber = data[i].mPhoneNumber
            var getDOB = data[i].mDateofBirth
            var getAddress = data[i].mAddress
            var getIcomeSlab = data[i].inComeSlab
            var getCustomerImages = data[i].imagesCustomer
            Log.i(
                "Details",
                "ids $getIds   name = $getName phoneNumber =$getPhoneNumber  dateof birth$getDOB  address $getAddress  income$getIcomeSlab Images $getCustomerImages"
            )

            var userData =
                UserData(getIds, getName, getPhoneNumber, getDOB, getAddress, getIcomeSlab, getCustomerImages)
            arragList.add(userData)
        }
        var customerList = CustomerList(context!!.applicationContext, arragList)
        val layoutManager = LinearLayoutManager(context)
        recycler_view_details?.layoutManager = layoutManager as RecyclerView.LayoutManager?
        recycler_view_details?.adapter = customerList
    }

    override fun onClick(v: View?) {
        getFilterPopup().showAsDropDown(v)

    }

    fun getFilterPopup(): PopupWindow {

        val popupWindow = PopupWindow(context)
        // inflate your layout or dynamically add view
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.poppup_filter, null)

        var sort_name = view.findViewById<TextView>(R.id.sort_name)
        var sort_by_salary = view.findViewById<TextView>(R.id.sort_by_salary)
        var sort_address = view.findViewById<TextView>(R.id.sort_address)
        var sort_key = view.findViewById<TextView>(R.id.sort_key)
        sort_name.setOnClickListener {
            Toast.makeText(this.context, "Sort by name", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            sort("name")
        }
        sort_by_salary.setOnClickListener {
            Toast.makeText(this.context, "Sort by Salary", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss();
            sort("salary")
        }
        sort_address.setOnClickListener {
            Toast.makeText(this.context, "Sort by address", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss();
            sort("address")
        }
        sort_key.setOnClickListener {
            Toast.makeText(this.context, "Sort by key", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss();
            sort("key")
        }
        popupWindow.isFocusable = true
        popupWindow.width = Utils.dpToPx(280.0f, context)
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.contentView = view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 0f
            popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.abc_popup_background_mtrl_mult, null))
        }
        popupWindow.isClippingEnabled = true

        return popupWindow
    }


    private fun sort(sort: String){
        when (sort) {
            "name" -> Collections.sort(arragList, NameComparator())
            "salary" -> Collections.sort(arragList, SalaryComparator())
            "address" -> Collections.sort(arragList, AddressComparator())
            "key" -> Collections.sort(arragList, KeyComparator())
        }
        recycler_view_details!!.adapter.notifyDataSetChanged()
    }

    class SalaryComparator: Comparator<UserData>{
        override fun compare(o1: UserData?, o2: UserData?): Int {
            return o1!!.inComeSlab.compareTo(o2!!.inComeSlab)
        }
    }

    class NameComparator: Comparator<UserData>{
        override fun compare(o1: UserData?, o2: UserData?): Int {
            return o1!!.mName.compareTo(o2!!.mName)
        }
    }

    class AddressComparator: Comparator<UserData>{
        override fun compare(o1: UserData?, o2: UserData?): Int {
            return o1!!.mAddress.compareTo(o2!!.mAddress)
        }
    }

    class KeyComparator: Comparator<UserData>{
        override fun compare(o1: UserData?, o2: UserData?): Int {
            return o1!!.keyIds.compareTo(o2!!.keyIds)
        }
    }
}
