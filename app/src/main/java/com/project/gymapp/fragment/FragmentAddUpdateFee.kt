package com.project.gymapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.gymapp.R
import com.project.gymapp.databinding.FragmentAddUpdateFeeBinding
import com.project.gymapp.databinding.FragmentFeePendingBinding
import com.project.gymapp.global.DB
import com.project.gymapp.global.MyFunction

class FragmentAddUpdateFee : Fragment() {

    private lateinit var binding: FragmentAddUpdateFeeBinding
    var db:DB?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentAddUpdateFeeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db= activity?.let { DB(it) }
        binding.btnAddMemberShip.setOnClickListener {
            if(validate()){
                saveData()
            }
        }
        fillData()
    }
    private fun validate():Boolean{
        if (binding.edtOnemonth.text.toString().trim().isEmpty()){
            showToast("Enter one month fee")
        }
        if (binding.edtThreeemonth.text.toString().trim().isEmpty()){
            showToast("Enter three month fee")
        }
        if (binding.edtSixmonth.text.toString().trim().isEmpty()){
            showToast("Enter six month fee")
        }
        if (binding.edt1Year.text.toString().trim().isEmpty()){
            showToast("Enter one year fee")
        }
        if (binding.edtThreeYear.text.toString().trim().isEmpty()){
            showToast("Enter three year fee")
        }
        return true
    }
    private fun saveData(){
        try {
            val sqlQuery = "INSERT OR REPLACE INTO FEE(ID,ONE_MONTH,THREE_MONTH,SIX_MONTH,ONE_YEAR,THREE_YEAR)VALUES"+
                    "('1','"+binding.edtOnemonth.text.toString().trim()+"','"+binding.edtThreeemonth.text.toString().trim()+"',"+
                    "'"+binding.edtOnemonth.text.toString().trim()+"','"+binding.edt1Year.text.toString().trim()+"',"+"'"+binding.edtThreeYear.text.toString().trim()+"')"
            db?.executeQuery(sqlQuery)
            showToast("MemberShip data saved successfully")

        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    private fun fillData(){
        try {
            val sqlQuery = "SELECT * FROM FEE WHERE ID='1'"
            db?.fireQuery(sqlQuery)?.use {
                if (it.count > 0) {
                    it.moveToFirst()
                    binding.edtOnemonth.setText((MyFunction.getValue(it, "ONE_MONTH")))
                    binding.edtThreeemonth.setText((MyFunction.getValue(it, "THREE_MONTH")))
                    binding.edtSixmonth.setText((MyFunction.getValue(it, "SIX_MONTH")))
                    binding.edt1Year.setText((MyFunction.getValue(it, "ONE_YEAR")))
                    binding.edtThreeYear.setText((MyFunction.getValue(it, "THREE_YEAR")))

                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }



    private fun showToast(value:String){
        Toast.makeText(activity,value,Toast.LENGTH_LONG).show()

    }
    }
