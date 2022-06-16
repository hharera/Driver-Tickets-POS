package com.example.scan_reserved_ticket

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.permission.PermissionUtils
import com.englizya.model.response.ReservedTicketResponse
import com.example.scan_reserved_ticket.databinding.FragmentScanReservedTicketBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScanReservedTicketFragment : BaseFragment() {
    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 3005
        private const val TAG = "ScanReservedTicketFragment"
    }

    private lateinit var binding: FragmentScanReservedTicketBinding
    private val scanReservedTicketViewModel: ScanReservedTicketViewModel by sharedViewModel()

    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentScanReservedTicketBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_350)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupQrDetector() {
        barcodeDetector = BarcodeDetector.Builder(requireContext())
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        cameraSource = CameraSource
            .Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.scannerView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                checkCameraPermission()
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                if (detections.detectedItems.isNotEmpty())
                    scanReservedTicketViewModel.setQrContent(detections.detectedItems.valueAt(0).displayValue)
            }
        })
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            PermissionUtils.requestPermission(
                requireActivity() as AppCompatActivity,
                CAMERA_PERMISSION_REQUEST_CODE,
                Manifest.permission.CAMERA,
                false
            )
        } else {
            cameraSource.start(binding.scannerView.holder)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        checkCameraPermission()
    }

    private fun setupObservers() {
        scanReservedTicketViewModel.qrContent.observe(viewLifecycleOwner) {
            scanReservedTicketViewModel.requestPayedTicket()
        }

        scanReservedTicketViewModel.reservedTicketResponse.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        scanReservedTicketViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
        scanReservedTicketViewModel.printingOperationCompleted.observe(viewLifecycleOwner) {
            when (it) {
                true -> activity?.onBackPressed()

                else -> {}
            }
        }

    }

    private fun updateUI(reservedTicketResponse: ReservedTicketResponse) {
        binding.ticketDetailsFrame.visibility = View.VISIBLE.also {
            binding.ticketId.text = reservedTicketResponse.data?.ticketId.toString()
            binding.tripId.text = reservedTicketResponse.data?.tripId.toString()
            binding.seatNo.text = reservedTicketResponse.data?.seatNo.toString()
            binding.print.isEnabled = reservedTicketResponse.data!!.isActive

            if (reservedTicketResponse.data?.isActive == true) {
                binding.qr.setBackgroundResource(R.drawable.ic_ok)
            } else {
                binding.qr.setBackgroundResource(R.drawable.ic_error2)
            }

        }
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.INVISIBLE
    }


    private fun setupListeners() {
        binding.scan.setOnClickListener {
            cameraSource.start(binding.scannerView.holder)
        }

        binding.print.setOnClickListener {
            scanReservedTicketViewModel.whenPrintClicked()
        }
    }

    override fun onPause() {
        super.onPause()
        cameraSource.stop()
    }

    override fun onResume() {
        super.onResume()
        setupQrDetector()
    }
}