package `in`.co.ngcapp.customaccount.adapterimport `in`.co.ngcapp.customaccount.Rimport `in`.co.ngcapp.model.UserDataimport android.content.Contextimport android.os.Parcelimport android.os.Parcelableimport android.support.v7.widget.RecyclerViewimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport android.widget.TextViewimport de.hdodenhof.circleimageview.CircleImageViewclass CustomerList(var mContext: Context, var arraList:ArrayList<UserData>) : RecyclerView.Adapter<CustomerList.CustomHolder>() {    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomerList.CustomHolder {        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.customer_adapters,parent,false)        return CustomHolder(view)    }    override fun getItemCount(): Int {        return arraList.size    }    override fun onBindViewHolder(holder: CustomerList.CustomHolder?, position: Int) {        holder!!.holdData(mContext,arraList[position])    }    class CustomHolder (itemView: View):RecyclerView.ViewHolder(itemView){        fun holdData(mContext: Context, arraList:UserData){//            var profile_image = itemView.findViewById<CircleImageView>(R.id.profile_image)            var phone_number               = itemView.findViewById<TextView>(R.id.phone_number)            var name   = itemView.findViewById<TextView>(R.id.name)//            var customer_description= itemView.findViewById<TextView>(R.id.customer_description)            phone_number.setText(arraList.mPhoneNumber)            name.setText(arraList.mName)        }    }}