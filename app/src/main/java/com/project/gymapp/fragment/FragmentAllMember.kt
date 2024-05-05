package com.project.gymapp.fragment

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.gymapp.R
import com.project.gymapp.adapter.AdapterLoadMember
import com.project.gymapp.databinding.FragmentAllMemberBinding
import com.project.gymapp.global.DB
import com.project.gymapp.global.MyFunction
import com.project.gymapp.model.AllMember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class FragmentAllMember : BaseFragment() {
    private val TAG = "FragmentAllMember"
    var db: DB? = null
    var adapter: AdapterLoadMember? = null
    var arrayList: ArrayList<AllMember> = ArrayList()

    private lateinit var binding: FragmentAllMemberBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAllMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize db
        db = activity?.let { DB(it) }

        // Set up rdGroupMember listener
        binding.rdGroupMember.setOnCheckedChangeListener { radioGroup, i ->
            when (id) {
                R.id.rdActiveMember -> {
                    // Handle active member
                }
                R.id.rdInActiveMember -> {
                    // Handle inactive member
                }
            }
        }

        // Load data
        loadData("A")
    }

    fun <R> CoroutineScope.executeAsyncTask(
        onPreExecute: () -> Unit,
        doInBackground: () -> R,
        onPostExecute: (R) -> Unit
    ) = launch {
        onPreExecute()
        val result = withContext(Dispatchers.IO) {
            doInBackground()
        }
        onPostExecute(result)
    }

    private fun loadData(memberStatus: String) {
        lifecycleScope.executeAsyncTask(
            onPreExecute = {

            },
            doInBackground = {
                val sqlQuery = "SELECT * FROM MEMBER WHERE STATUS = '$memberStatus'"
                db?.fireQuery(sqlQuery)?.let { cursor ->
                    if (cursor.count > 0) {
                        cursor.moveToFirst()
                        do {
                            val list = AllMember(
                                id = MyFunction.getValue(cursor, "ID"),
                                firstname = MyFunction.getValue(cursor, "FIRST_NAME"),
                                LastName = MyFunction.getValue(cursor, "LAST_NAME"),
                                age = MyFunction.getValue(cursor, "AGE"),
                                gender = MyFunction.getValue(cursor, "GENDER"),
                                weight = MyFunction.getValue(cursor, "WEIGHT"),
                                mobile = MyFunction.getValue(cursor, "MOBILE"),
                                address = MyFunction.getValue(cursor, "ADDRESS"),
                                dateOfJoining = MyFunction.returnUserDataFormat(MyFunction.getValue(cursor, "DATE_OF_JOINING")),
                                expiryDate = MyFunction.returnUserDataFormat(MyFunction.getValue(cursor, "EXPIRE_ON"))
                            )
                            arrayList.add(list)
                        } while (cursor.moveToNext())
                    }
                    // Reset cursor position
                    cursor.moveToFirst()
                }
            },
            onPostExecute = {
                if (arrayList.isNotEmpty()) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.txtAllMemberNDF.visibility = View.GONE
                    adapter = AdapterLoadMember(arrayList)
                    binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.recyclerView.adapter = adapter
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.txtAllMemberNDF.visibility = View.VISIBLE
                }
                ClosedDialog()
            }
        )
    }

}


