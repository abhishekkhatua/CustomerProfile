package `in`.co.ngcapp.dialog

import `in`.co.ngcapp.customaccount.R
import `in`.co.ngcapp.customaccount.interfaces.DialogClicks
import `in`.co.ngcapp.model.DataLocations
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView


class DialogLocations : DialogFragment() {

    private var listView: ListView? = null
    var clickListner: DialogClicks? = null

    var salaryIcome = arrayOf(
        "1 lakhs - 5 lakhs ",
        "5 lakhs - 10 lakhs ",
        "10 lakhs - 15laks",
        "15 lakhs- 20lakhs",
        "20 lakhs - 25 lakhs"
    )
    var salary: DataLocations? = null
    var arrayListSalary: ArrayList<DataLocations>? = null
    var iv_header: TextView? = null
    fun onClickListnerListen(dateClickListners: DialogClicks) {
        clickListner = dateClickListners
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragmentdialog_locations, container, false)

        listView = view.findViewById(R.id.list_view)
        iv_header = view.findViewById(R.id.iv_header)
        iv_header!!.text = " Income slab"
        arrayListSalary = ArrayList<DataLocations>()

        bindData()

        return view
    }

    fun bindData() {
        for (i in 0 until salaryIcome.size) {
            val getSalary = salaryIcome[i]
            salary = DataLocations(getSalary)

            arrayListSalary!!.add(salary!!)
        }
        val mySimpleArrayAdapter =
            MySimpleArrayAdapter(activity!!.applicationContext, this!!.arrayListSalary!!, object : DialogClicks {
                override fun onClickListner(dateTO: String) {
                    clickListner!!.onClickListner(dateTO)
                }

            })
        listView!!.adapter = mySimpleArrayAdapter
    }

}

class MySimpleArrayAdapter(
    context: Context,
    private var item: ArrayList<DataLocations>,
    var dialogClicks: DialogClicks
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Any {
        return item[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getCount(): Int {
        return item.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = this.inflater.inflate(R.layout.location_adapter, parent, false)
        val mLocaionName = rowView.findViewById(R.id.text) as TextView
        mLocaionName.text = item.get(position).mName
        mLocaionName.setOnClickListener {
            var getName = item[position].mName
            dialogClicks.onClickListner(getName)
        }
        return rowView

    }

}