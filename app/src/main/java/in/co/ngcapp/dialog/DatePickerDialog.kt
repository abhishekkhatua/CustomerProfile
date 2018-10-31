package `in`.co.ngcapp.customaccount.fragments


import `in`.co.ngcapp.customaccount.interfaces.DialogClicks
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DatePickerDialog : DialogFragment() {

  var dateClick : DialogClicks?= null
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    return DatePickerDialog(activity!!, dateSetListener, year, month, day)
  }
  private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->

    var  date = ""+view.dayOfMonth+"-"+(view.month+1)+"-"+view.year
    if (dateClick!= null) {
      dateClick!!.onClickListner(date)
    }
  }
  fun onClickListner(dateClickListners:DialogClicks){
    dateClick = dateClickListners
  }
}