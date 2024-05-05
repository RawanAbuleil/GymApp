package com.project.gymapp.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.database.DatabaseUtils
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.project.gymapp.R
import com.project.gymapp.databinding.FragmentAddMemberBinding
import com.project.gymapp.global.DB
import com.project.gymapp.global.MyFunction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FragmentAddMember : Fragment() {

    var db: DB? = null
    var oneMonth: String? = ""
    var threeMonth: String? = ""
    var sixMonth: String? = ""
    var oneYear: String? = ""
    var threeYear: String? = ""
    private var gender = "Male"
    private lateinit var binding: FragmentAddMemberBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showToast(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = activity?.let { DB(it) }
        val cal = Calendar.getInstance()
        val daraSetListener =
            DatePickerDialog.OnDateSetListener { view1, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.edtJoining.setText(sdf.format(cal.time))
            }

        binding.spMemberShip.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = binding.spMemberShip.selectedItem.toString().trim()
                if (value == "Select") {
                    binding.edtExpire.setText("")
                    calculateTotal(binding.spMemberShip, binding.edtDiscount, binding.edtAmount)
                } else {
                    if (binding.edtJoining.text.toString().trim().isNotBlank()) {
                        if (value == "1 Month") {
                            calculateExpireDate(1, binding.edtExpire)
                            calculateTotal(
                                binding.spMemberShip,
                                binding.edtDiscount,
                                binding.edtAmount
                            )
                        }
                        if (value == "3 Months") {
                            calculateExpireDate(3, binding.edtExpire)
                            calculateTotal(
                                binding.spMemberShip,
                                binding.edtDiscount,
                                binding.edtAmount
                            )

                        }
                        if (value == "6 Months") {
                            calculateExpireDate(6, binding.edtExpire)
                            calculateTotal(
                                binding.spMemberShip,
                                binding.edtDiscount,
                                binding.edtAmount
                            )

                        }
                        if (value == "1 Year") {
                            calculateExpireDate(12, binding.edtExpire)
                            calculateTotal(
                                binding.spMemberShip,
                                binding.edtDiscount,
                                binding.edtAmount
                            )

                        }
                        if (value == "3 Years") {
                            calculateExpireDate(36, binding.edtExpire)
                            calculateTotal(
                                binding.spMemberShip,
                                binding.edtDiscount,
                                binding.edtAmount
                            )

                        }

                    } else {
                        showToast("Select joining date first !")
                        binding.spMemberShip.setSelection(0)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        binding.edtDiscount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    calculateTotal(binding.spMemberShip, binding.edtDiscount, binding.edtAmount)
                }
            }

        })

        binding.radioGroub.setOnCheckedChangeListener{ radioGroub,i-> when(id){
            R.id.rdMale ->{
                gender = "Male"
            }
            R.id.rdFeMale ->{
                gender = "Female"
            }
        }
        }

        binding.btnAddMemberSave.setOnClickListener {
            if(validate()){
                saveData()
            }


        }
        binding.imgPicDate.setOnClickListener {
            activity?.let { it1 ->
                DatePickerDialog(
                    it1, daraSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
        getFee()
    }

    private fun getFee() {
        try {
            val sqlQuery = "SELECT * FROM FEE WHERE ID = '1'"
            db?.fireQuery(sqlQuery)?.use {
                oneMonth = MyFunction.getValue(it, "ONE_MONTH")
                threeMonth = MyFunction.getValue(it, "THREE_MONTH")
                sixMonth = MyFunction.getValue(it, "SIX_MONTH")
                oneYear = MyFunction.getValue(it, "ONE_YEAR")
                threeYear = MyFunction.getValue(it, "THREE_YEAR")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun calculateTotal(spMember: Spinner, edtDis: EditText, edtAmr: EditText) {
        val month = spMember.selectedItem.toString().trim()
        var discount = edtDis.text.toString().trim()
        if (edtDis.text.toString().isEmpty()) {
            discount = "0"
        }
        if (month == "Select") {
            edtAmr.setText("")
        } else if (month == "1 Month") {
            if (discount.trim().isEmpty()) {
                discount = "0"
            }
            if (oneMonth!!.trim().isNotEmpty()) {
                val discountAmount = ((oneMonth!!.toDouble() * discount.toDouble()) / 100)
                val total = oneMonth!!.toDouble() - discountAmount
                edtAmr.setText(total.toString())
            }
        } else if (month == "3 Months") {
            if (discount.trim().isEmpty()) {
                discount = "0"
            }
            if (threeMonth!!.trim().isNotEmpty()) {
                val discountAmount = ((threeMonth!!.toDouble() * discount.toDouble()) / 100)
                val total = threeMonth!!.toDouble() - discountAmount
                edtAmr.setText(total.toString())
            }
        } else if (month == "6 Months") {
            if (discount.trim().isEmpty()) {
                discount = "0"
            }
            if (sixMonth!!.trim().isNotEmpty()) {
                val discountAmount = ((sixMonth!!.toDouble() * discount.toDouble()) / 100)
                val total = sixMonth!!.toDouble() - discountAmount
                edtAmr.setText(total.toString())
            }
        } else if (month == "1 Year") {
            if (discount.trim().isEmpty()) {
                discount = "0"
            }
            if (oneYear!!.trim().isNotEmpty()) {
                val discountAmount = ((oneYear!!.toDouble() * discount.toDouble()) / 100)
                val total = oneYear!!.toDouble() - discountAmount
                edtAmr.setText(total.toString())
            }

        } else if (month == "3 Years") {
            if (discount.trim().isEmpty()) {
                discount = "0"
            }
            if (threeYear!!.trim().isNotEmpty()) {
                val discountAmount = ((threeYear!!.toDouble() * discount.toDouble()) / 100)
                val total = threeYear!!.toDouble() - discountAmount
                edtAmr.setText(total.toString())

            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun calculateExpireDate(month: Int, edtExpiry: EditText) {
        val dtState = binding.edtJoining.text.toString().trim()
        if (dtState.isNotEmpty()) {
            var format = SimpleDateFormat("dd/MM/yyyy")
            val date1 = format.parse(dtState)
            val cal = Calendar.getInstance()
            if (date1 != null) {
                cal.time = date1
            }
            cal.add(Calendar.MONTH, month)
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            edtExpiry.setText(sdf.format(cal.time))
        }
    }
    private fun validate():Boolean{
        if (binding.edtFirstName.text.toString().trim().isEmpty()){
            showToast("Enter First Name")
            return false
        } else if (binding.edtLastName.text.toString().trim().isEmpty()){
            showToast("Enter Last Name")
            return false
        }else if (binding.edtMobile.text.toString().trim().isEmpty()){
            showToast("Enter Mobile ")
            return false
        }else if (binding.edtAge.text.toString().trim().isEmpty()){
            showToast("Enter Age")
            return false
        }
        return true
    }
    private fun saveData(){
        try {
            val sqlQuery = "INSERT OR REPLACE INTO MEMBER(ID,FIRST_NAME,LAST_NAME,GENDER,AGE,"+
                    "WEIGHT,MOBILE,ADDRESS,DATE_OF_JOINING,MEMBERSHIP,EXPIRE_ON,DISCOUNT,TOTAL,STATUS) VALUES ("+
                    "'${getIncrementedId()}', ${DatabaseUtils.sqlEscapeString(binding.edtFirstName.text.toString().trim())}, "+
                    "${DatabaseUtils.sqlEscapeString(binding.edtLastName.text.toString().trim())}, '$gender', "+
                    "'${binding.edtAge.text.toString().trim()}', '${binding.edtWeight.text.toString().trim()}', "+
                    "'${binding.edtMobile.text.toString().trim()}', ${DatabaseUtils.sqlEscapeString(binding.edtAddress.text.toString().trim())}, "+
                    "'${MyFunction.returnSqlDataFormat(binding.edtJoining.text.toString().trim())}', "+
                    "'${binding.spMemberShip.selectedItem.toString().trim()}', "+
                    "'${MyFunction.returnSqlDataFormat(binding.edtExpire.text.toString().trim())}', "+
                    "'${binding.edtDiscount.text.toString().trim()}', "+
                    "'${binding.edtAmount.text.toString().trim()}', 'A')"


            db?.executeQuery(sqlQuery)
            showToast("Data Saved Successfully")
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun getIncrementedId():String{
        var incrementId=""
        try {
            val sqlQuery = "SELECT IFNULL(MAX(ID)+1,'1') AS ID FROM MEMBER"
            db?.fireQuery(sqlQuery)?.use {
                if (it.count>0){
                    incrementId = MyFunction.getValue(it ,"ID")
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return incrementId
    }
}



