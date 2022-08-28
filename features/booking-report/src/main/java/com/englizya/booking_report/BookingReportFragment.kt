package com.englizya.booking_report

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.englizya.booked_seats.databinding.FragmentBookingReportBinding
import com.englizya.booking_report.ExpandableListData.setData
import com.englizya.common.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingReportFragment : BaseFragment() {

    private val bookingReportViewModel: BookingReportViewModel by viewModel()
    private lateinit var binding: FragmentBookingReportBinding

    private var adapter: ExpandableListAdapter? = null
    private var officeList: List<String>? = null
    private var ticketsCountList: List<Int>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBookingReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookingReportViewModel.getBookingReport()
        setUpObservers()
        setUpListeners()
    }
    private fun setUpListeners() {
        binding.externalSwipeLayout.setOnRefreshListener {
            bookingReportViewModel.getBookingReport()
            binding.externalSwipeLayout.isRefreshing = false


        }
    }
    private fun setUpObservers() {
        bookingReportViewModel.loading.observe(viewLifecycleOwner){
            handleLoading(it)
        }
        bookingReportViewModel.error.observe(viewLifecycleOwner){
            handleFailure(it)
        }

        bookingReportViewModel.bookingReport.observe(viewLifecycleOwner) {
            Log.d("Report", it.toString())
            ExpandableListData.setData(it)
            setUpAdapter()
        }
    }

    private fun setUpAdapter() {
        val tripNameList = ExpandableListData.tripName
        ticketsCountList = ExpandableListData.ticketsCount
        officeList = ExpandableListData.officeName

//        titleList = ExpandableListData.ticketsCount
        adapter =
            CustomExpandableListAdapter(
                context!!,
                ticketsCountList as ArrayList<Int>,
                tripNameList,
                officeList as ArrayList<String>

            )
        binding.tripsLV.setAdapter(adapter)
        binding.tripsLV.setOnGroupExpandListener { groupPosition ->

        }
        binding.tripsLV.setOnGroupCollapseListener { groupPosition ->

        }
        binding.tripsLV.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->

            false
        }
    }

    override fun onDestroyView() {
        binding.externalSwipeLayout.removeAllViews()
        super.onDestroyView()
    }

}