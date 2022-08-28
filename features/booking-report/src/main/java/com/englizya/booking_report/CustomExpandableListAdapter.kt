package com.englizya.booking_report

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.englizya.booked_seats.R
import com.englizya.model.BookingOffice
import com.englizya.model.Trip


class CustomExpandableListAdapter internal constructor(
    private val context: Context,
    private val bookedSeatsList: List<Int>,
    private val tripNameList: List<String>,
    private val officeList: List<String>
) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any? {
        return officeList[listPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    private fun getBookedSeats(listPosition: Int): Any {
        return this.bookedSeatsList[listPosition]
    }
    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listBookedSeats = getBookedSeats(listPosition) as Int

        val expandedListText = getChild(listPosition, expandedListPosition)
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.booking_office_item, null)
        }
        val bookedSeatsTextView = convertView!!.findViewById<TextView>(R.id.booked_seats)
        val listBookingOfficeNameTextView = convertView!!.findViewById<TextView>(R.id.booking_office_name)

        listBookingOfficeNameTextView.text = context.getString(R.string.booking_office) + " " + expandedListText
        bookedSeatsTextView.text = context.getString(R.string.seats) + " " + listBookedSeats

        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.officeList.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.tripNameList[listPosition]
    }



    override fun getGroupCount(): Int {
        return this.officeList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.trip_item, null)
        }
        val listTripNameTextView = convertView!!.findViewById<TextView>(R.id.tripName)
        listTripNameTextView.setTypeface(listTripNameTextView.typeface, Typeface.BOLD)
        listTripNameTextView.text = listTitle
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}