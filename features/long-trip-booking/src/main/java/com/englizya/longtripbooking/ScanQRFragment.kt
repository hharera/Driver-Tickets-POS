package com.englizya.longtripbooking

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.permission.PermissionUtils
import com.englizya.longtripbooking.databinding.FragmentScanQrBinding
import com.englizya.model.response.WalletDetails
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ScanQRFragment : BaseFragment(
) {

    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 3005
        private const val TAG = "ScanQrLongFragment"
    }

    private val bookingViewModel: LongTripBookingViewModel by sharedViewModel()

    private lateinit var binding: FragmentScanQrBinding
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentScanQrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingViewModel._printingOperationCompleted.value = false
        setupQrDetector()
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
                    bookingViewModel.setQrContent(detections.detectedItems.valueAt(0).displayValue)
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
        bookingViewModel.qrContent.observe(viewLifecycleOwner) {
            cameraSource.stop()
            Log.d(TAG, "setupObservers: $it")
            updateQR(it)
            bookingViewModel.loadWalletDetails()
        }

        bookingViewModel.walletDetails.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        bookingViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
        bookingViewModel.longTickets.observe(viewLifecycleOwner) {

            if(it!=null){
                Log.d("LongTicket" , it.toString())

                bookingViewModel.printLongTickets(it)

            }
        }

        bookingViewModel.printingOperationCompleted.observe(viewLifecycleOwner) {
            when (it) {
                true -> findNavController().popBackStack()
                else -> {}
            }
        }
    }

    private fun updateUI(walletDetails: WalletDetails) {
        binding.walletDetailsFrame.visibility = View.VISIBLE.also {
            binding.name.text = walletDetails.name
            binding.phone.text = walletDetails.phoneNumber
            binding.balance.text = walletDetails.balance.toString()
        }
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.INVISIBLE
    }

    private fun updateQR(data: String) {
        BarcodeEncoder().encodeBitmap(data, BarcodeFormat.QR_CODE, 150, 150).let {
            binding.qr.setImageBitmap(it)
        }
    }

    private fun setupListeners() {
        binding.scan.setOnClickListener {
            cameraSource.start(binding.scannerView.holder)
        }

        binding.pay.setOnClickListener {
            bookingViewModel.requestLongTicketsWithWallet()
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