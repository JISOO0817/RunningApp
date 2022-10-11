package com.jisoo.runningapp.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jisoo.runningapp.R
import com.jisoo.runningapp.databinding.FragmentRunBinding
import com.jisoo.runningapp.other.Constant.REQUEST_CODE_LOCATION_PERMISSION
import com.jisoo.runningapp.other.TrackingUtility
import com.jisoo.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment: Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: FragmentRunBinding

//
//    private val qPermissions = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION
//    )
//
    private val upQPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

//    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>

    /**
     * dagger-hilt가 뷰에 대한 뷰모델 팩토리를 뒤에서 관리함
     * */
    private val viewModel: MainViewModel by viewModels()

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
            if(it.all { permission -> permission.value }) {

            } else {
                Toast.makeText(requireActivity(),"권한 거부",Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRunBinding.inflate(inflater,container,false)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }


        ActivityCompat.requestPermissions(requireActivity(),upQPermissions, REQUEST_CODE_LOCATION_PERMISSION)
        requestPermissions()
//
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//            if(checkPermissions(permissions = qPermissions)) {
//                activityResultLauncher.launch(qPermissions)
//            }
//        } else {
//            if(checkPermissions(permissions = upQPermissions)) {
//                activityResultLauncher.launch(upQPermissions)
//            }
//        }


        return binding.root
    }

//    private fun checkPermissions(permissions: Array<String>): Boolean {
//        return permissions.all {
//            ContextCompat.checkSelfPermission(requireContext(),it) == PackageManager.PERMISSION_GRANTED
//        }
//    }


    private fun requestPermissions() {
        Log.d("permission","requestPermission 호출")
        //허가 되었으면 return

        if(TrackingUtility.hasLocationPermission(requireContext())) {
            Log.d("permission","return")
            return
        }

        //허가 되지 않았으면 다시 권한을 요청한다.

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                requireContext(),
                "You need to accept location permissions to use this app.",
//                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION

            )
        } else {
            EasyPermissions.hasPermissions(
                requireContext(),
                "You need to accept location permissions to use this app.",
//                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION

            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        //일부 권한이 영구적으로 거부됐을 경우 앱 설정 으로 갈 수 있는 다이얼로그를 띄워준다.
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } //아닐 경우 다시 권한 요청
        else {
            requestPermissions()
        }
    }



//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
//    }

}