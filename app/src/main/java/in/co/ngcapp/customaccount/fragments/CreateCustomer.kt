package `in`.co.ngcapp.customaccount.fragments


import `in`.co.ngcapp.customaccount.R
import `in`.co.ngcapp.customaccount.database.SqliteDatabase
import `in`.co.ngcapp.customaccount.interfaces.DialogClicks
import `in`.co.ngcapp.dialog.DialogLocations
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CreateCustomer : Fragment(), View.OnClickListener {


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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create_customer, container, false)
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
        submit!!.setOnClickListener(this)
        input_income!!.setOnClickListener(this)
        date_of_birth!!.setOnClickListener(this)
        input_address!!.setOnClickListener(this)
        input_income!!.setOnClickListener(this)
        textChangable()
        return view
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
            input_layout_mobile!!.setError("Please Enter Mobile Number")

            isValidate = false
        }
        if (getDateOfBirth.isNullOrEmpty()) {
            error_date_of_birth!!.visibility = View.VISIBLE
            isValidate = false
        }
        if (getAddress.isNullOrEmpty()) {
            input_layout_address!!.visibility = View.VISIBLE

            isValidate = false
        }
        if (getInCome.isNullOrEmpty()) {
            error_income!!.visibility = View.VISIBLE
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


    }


    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.input_income -> {
                openSalary()
            }
            R.id.submit -> {
                if (getData() == false) {
                    Log.i("Error", "Error")

                } else {
                    savedata(
                        getname.toString(),
                        "",
                        getDateOfBirth.toString(),
                        "",
                        getPhoneNumber.toString(),
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
        }


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
}
