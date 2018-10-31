package `in`.co.ngcapp.customaccount.fragments


import `in`.co.ngcapp.customaccount.R
import `in`.co.ngcapp.customaccount.database.SqliteDatabase
import `in`.co.ngcapp.model.UserData
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ShoeCustomer : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_shoe_customer, container, false)
        var recycler_view_details = view.findViewById<RecyclerView>(R.id.recycler_view_details)
        getSavedData()
        return view
    }


    fun getSavedData() {

        var arrayList: ArrayList<UserData> = ArrayList();

        var sq = SqliteDatabase(context!!.applicationContext)
        var data: List<UserData> = sq.getALLDATA()
        for (i in 0 until data.size) {
            var getIds = data[i].keyIds
            var getName = data[i].mName
            var getPhoneNumber = data[i].mPhoneNumber
            var getDOB = data[i].mDateofBirth
            var getAddress = data[i].mAddress
            var getIcomeSlab = data[i].inComeSlab
            Log.i("Details", "$getIds   $getName $getPhoneNumber $getDOB $getAddress $getIcomeSlab")


        }
    }


}
