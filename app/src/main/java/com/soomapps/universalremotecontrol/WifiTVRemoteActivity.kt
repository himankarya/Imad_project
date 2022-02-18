package com.soomapps.universalremotecontrol

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.connectsdk.discovery.DiscoveryManager
import pub.devrel.easypermissions.EasyPermissions
import android.content.DialogInterface
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.connectsdk.core.TextInputStatusInfo
import com.connectsdk.device.ConnectableDevice
import com.connectsdk.device.DevicePicker
import com.connectsdk.service.DeviceService.PairingType
import com.connectsdk.service.DeviceService
import com.connectsdk.service.command.ServiceCommandError
import com.connectsdk.device.ConnectableDeviceListener
import com.connectsdk.service.capability.*
import com.connectsdk.service.command.ServiceSubscription
import com.connectsdk.service.sessions.LaunchSession
import com.connectsdk.core.TextInputStatusInfo.TextInputType;
import com.connectsdk.service.capability.ExternalInputControl;
import com.connectsdk.service.capability.KeyControl;
import com.connectsdk.service.capability.KeyControl.KeyCode;
import com.connectsdk.service.capability.Launcher;
import com.connectsdk.service.capability.Launcher.AppLaunchListener;
import com.connectsdk.service.capability.MediaControl;
import com.connectsdk.service.capability.MediaPlayer;
import com.connectsdk.service.capability.MouseControl;
import com.connectsdk.service.capability.PowerControl;
import com.connectsdk.service.capability.TVControl;
import com.connectsdk.service.capability.TextInputControl;
import com.connectsdk.service.capability.TextInputControl.TextInputStatusListener;
import com.connectsdk.service.capability.ToastControl;
import com.connectsdk.service.capability.VolumeControl;
import com.connectsdk.service.capability.VolumeControl.MuteListener;
import com.connectsdk.service.capability.VolumeControl.VolumeListener;
import com.connectsdk.service.capability.WebAppLauncher;
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.soomapps.universalremotecontrol.utils.ConnectionDetector
import com.soomapps.universalremotecontrol.wifiremoteutils.C1352a
import java.util.*
import kotlin.collections.ArrayList

class WifiTVRemoteActivity : AppCompatActivity() {

    var mDevicePicker: DevicePicker? = null//f3826p
    var mAlertDialog: AlertDialog? = null//f3823m
    var mAlertDialogR: AlertDialog? = null//f3824n
    var paringCodeDialog: AlertDialog? = null//f3825o
    var mediaControl: MediaControl? = null//f3827q
    var mVolumeControl: VolumeControl? = null//f3828r
    private var mTVControl: TVControl? = null//at
    private var mPowerControl: PowerControl? = null//f3829s
    private var mLauncher: Launcher? = null//au
    // private var mKeyControl: KeyControl? = null
    private var mMediaControl: MediaPlayer? = null//`as`
    private var mMouseControl: MouseControl? = null//az
    private var mTextInputControl: TextInputControl? = null//aw
    private var mToasetControl: ToastControl? = null
    private var mKeyControl: KeyControl? = null//f3830t
    private var mWebAppLauncher: WebAppLauncher? = null//av
    private var mView: View? = null//f3807W
    private var mExInputControl: ExternalInputControl? = null
    private var mConnectableDevices: ConnectableDevice? = null//ar

    var utilCall: C1352a? = null//f3833w

    //ads
    private var mAdView: AdView? = null
    private var adBRequest: AdRequest? = null

    var boolean1 = false//f3809Y
    var boolean2 = false//f3810Z
    var boolean3 = false//f3808X
    var boolean5 = false
    var longValue: Long = 0//ag
    var floatValue: Float = 0.toFloat()//aa
    // var floatValue2: Float = 0.toFloat()//aa
    var floatValue2: Float = 0.toFloat()//ab
    var floatValue3 = java.lang.Float.NaN//ac
    var floatValue4 = java.lang.Float.NaN//ad
    var int1: Int = 0//ae
    var int2: Int = 0//af

    var mTimer = Timer()//ah
    var mTimerTask: TimerTask? = null//ai

    var ao: ArrayList<String>? = null
    var an: Array<String>? = null

    var ak: Button? = null//ak
    var al: Button? = null//al
    var am: Button? = null//am

    var mRightButton: Button? = null//f3785A

    var mRelativeLayout1: RelativeLayout? = null//f3817g

    var mRelativeLayout2: RelativeLayout? = null//f3816f

    var mRelativeLayout3: RelativeLayout? = null//f3818h

    var mRelativeLayout4: RelativeLayout? = null//f3819i

    var mRelativeLayout5: RelativeLayout? = null//f3820j

    var mRelativeLayout6: RelativeLayout? = null//f3821k

    var mLinearLayout: LinearLayout? = null//f3815e

    var mBackButton: Button? = null//f3786B

    var mMouseBTN: Button? = null//f3814d

    var mLeftButton: Button? = null//f3836z
    //  var openKeybordBTN: Button? = null//f3805U
    var mDownButton: Button? = null//f3787C

    var mHomeButton: Button? = null//f3788D

    var mExternalInputControlButton: Button? = null//f3789E

    var mLaunchSession: LaunchSession? = null//f3790F

    var mChannelUpBTN: Button? = null//f3791G

    var mChannelDownlBTN: Button? = null//f3792H

    var mPowerOffBTN: Button? = null//f3793I

    var noButtonsArray = arrayOfNulls<Button>(10)//f3794J

    var mControlUPBTN: Button? = null//f3795K

    var mMuteButton: CheckBox? = null//f3796L

    var mVolumeControlBTN1: Button? = null//f3797M

    var mVolumeControlBTN2: Button? = null//f3798N

    var mPlayButton: Button? = null//f3799O

    var mKeyControlUp: Button? = null//f3835y

    var f3800P: Button? = null

    var f3801Q: Button? = null

    var mDialPadBTN: Button? = null//f3814d

    var mRewindBTN: Button? = null//f3802R

    var mFastFwdBTN: Button? = null//f3803S

    var mOKBTN: Button? = null//f3804T

    var mTextInputBTN: Button? = null//f3805U

    var mEditText: EditText? = null//f3806V


    private var serviceSubsVolume: ServiceSubscription<VolumeControl.VolumeListener>? = null//f3831u
    private var serviceSubsMute: ServiceSubscription<VolumeControl.MuteListener>? = null //f3832v
    var buttonArray: Array<Button>? = null//f3834x

    private val mConnectableDevicesListener = object : ConnectableDeviceListener {

        override fun onCapabilityUpdated(connectableDevice: ConnectableDevice, list: List<String>, list2: List<String>) {
        }

        override fun onConnectionFailed(connectableDevice: ConnectableDevice, serviceCommandError: ServiceCommandError) {
            disconnectNRemoveListener(mConnectableDevices)//m4326c
        }

        override fun onDeviceDisconnected(connectableDevice: ConnectableDevice) {
            isDisconnected(mConnectableDevices!!)
            Toast.makeText(getApplicationContext(), "Device Disconnected", Toast.LENGTH_SHORT).show()
        }

        override fun onDeviceReady(connectableDevice: ConnectableDevice) {
            if (mAlertDialogR!!.isShowing()) {
                mAlertDialogR!!.dismiss()
            }
            if (mAlertDialogR!!.isShowing()) {
                mAlertDialogR!!.dismiss()
            }
            isConnectedDone(mConnectableDevices!!)//isConnectedDone
        }

        override fun onPairingRequired(connectableDevice: ConnectableDevice, deviceService: DeviceService, pairingType: PairingType) {
            val alertDialog: AlertDialog
            when (pairingType) {
                DeviceService.PairingType.FIRST_SCREEN -> alertDialog = mAlertDialogR!!
                DeviceService.PairingType.PIN_CODE, DeviceService.PairingType.MIXED -> alertDialog = paringCodeDialog!!
                else -> return
            }
            alertDialog.show()
        }
    }

    internal inner class C13253(wifiTv: Context) : View.OnClickListener {

        override fun onClick(view: View) {
            if (mKeyControl != null) {
                mKeyControl!!.ok(null)
                utilCall = C1352a(true, 200, "Clicked")
            }
        }
    }


    internal inner class C13231(c13242: C13242) : TimerTask() {

        override fun run() {
            if (mMouseControl != null) {
                mMouseControl!!.scroll(int1.toDouble(), int2.toDouble())
            }
        }
    }


    internal inner class C13286(wifiTv: Context) : View.OnClickListener {

        override fun onClick(view: View) {
            if (mKeyControl != null) {
                mKeyControl!!.up(null)
                utilCall = C1352a(true, 200, "UpClicked")
            }
        }
    }


    internal inner class C13297(wifiTv: Context) : View.OnClickListener {

        override fun onClick(view: View) {
            if (mKeyControl != null) {
                mKeyControl!!.left(null)
                utilCall = C1352a(true, 200, "LeftClicked")
            }
        }
    }


    /*  internal inner class C42761(anonymousClass28: Context) : Launcher.AppLaunchListener {
          override fun onSuccess(`object`: LaunchSession?) {
              m21765a(`object` as LaunchSession)
          }

          fun m21765a(launchSession: LaunchSession) {
              mLaunchSession = launchSession
              utilCall = C1352a(true, 200, "InputPickerShowing")
          }

          override fun onError(serviceCommandError: ServiceCommandError) {}

      }*/

    internal inner class C13319(wifiTv: Context) : View.OnClickListener {

        override fun onClick(view: View) {
            if (mKeyControl != null) {
                mKeyControl!!.down(null)
                utilCall = C1352a(true, 200, "DownClicked")
            }
        }
    }

    internal inner class C13308(wifiTv: Context) : View.OnClickListener {

        override fun onClick(view: View) {
            if (mKeyControl != null) {
                mKeyControl!!.right(null)
                utilCall = C1352a(true, 200, "RightClicked")
            }
        }
    }

    internal inner class C13242(wifiTv: Context) : View.OnTouchListener {


        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            val z: Boolean
            val f: Float
            val f2: Float
            val abs: Float
            val abs2: Float
            val x: Float
            val y: Float
            val stringBuilder: StringBuilder
            val z2 = boolean1
            val z3 = boolean2

            if (!boolean2) {
                if (motionEvent.getPointerCount() <= 1) {
                    z = false
                    boolean2 = z
                    when (motionEvent.getActionMasked()) {
                        0 -> {
                            boolean3 = true
                            longValue = motionEvent.getEventTime()
                            floatValue = motionEvent.getX()
                            floatValue2 = motionEvent.getY()
                        }
                        1 -> {
                            boolean3 = false
                            boolean1 = false
                            boolean2 = false
                            floatValue3 = java.lang.Float.NaN
                            floatValue4 = java.lang.Float.NaN
                        }
                        else -> {
                        }
                    }
                    if (floatValue3 === java.lang.Float.NaN) {
                        if (floatValue4 !== java.lang.Float.NaN) {
                            f = 0.0f
                            f2 = f
                            floatValue3 = motionEvent.getX()
                            floatValue4 = motionEvent.getY()
                            abs = Math.abs(motionEvent.getX() - floatValue)
                            abs2 = Math.abs(motionEvent.getY() - floatValue2)
                            if (boolean3 && !boolean1 && abs > 10.0f && abs2 > 10.0f) {
                                boolean1 = true
                            }
                            if (boolean3 || !boolean1) {
                                if (!boolean3 || z2) {
                                    if (!boolean3 && z2 && z3) {
                                        x = motionEvent.getX() - floatValue
                                        y = motionEvent.getY() - floatValue2
                                        if (mMouseControl != null) {
                                            mMouseControl!!.scroll(x.toDouble(), y.toDouble())
                                        }
                                        stringBuilder = StringBuilder()
                                        stringBuilder.append("sending scroll ")
                                        stringBuilder.append(x)
                                        stringBuilder.append(" ,")
                                        stringBuilder.append(x)
                                        Log.d("main", stringBuilder.toString())
                                    }
                                } else if (mMouseControl != null) {
                                    mMouseControl!!.click()
                                }
                            } else if (!(f == 0.0f || f2 == 0.0f)) {
                                var i = -1
                                val i2 = if (f >= 0.0f) 1 else -1
                                if (f2 >= 0.0f) {
                                    i = 1
                                }
                                x = ((i2.toLong()) * Math.round(Math.pow(Math.abs(f).toDouble(), 1.1))).toFloat()
                                val round = ((i.toLong()) * Math.round(Math.pow(Math.abs(f2).toDouble(), 1.1))).toFloat()
                                if (boolean2) {
                                    val uptimeMillis = SystemClock.uptimeMillis()
                                    int1 = (motionEvent.getX() - floatValue) as Int
                                    int2 = (motionEvent.getY() - floatValue2) as Int
                                    if (uptimeMillis - longValue > 1000 && mTimerTask == null) {
                                        Log.d("main", "starting autoscroll")
                                        mTimerTask = C13231(this)
                                        mTimer.schedule(mTimerTask, 100, 750)
                                    }
                                } else if (mMouseControl != null) {
                                    mMouseControl!!.move(x.toDouble(), round.toDouble())
                                }
                            }
                            if (!boolean3) {
                                boolean1 = false
                                if (mTimerTask != null) {
                                    mTimerTask!!.cancel()
                                    mTimerTask = null
                                    Log.d("main", "ending autoscroll")
                                }
                            }
                            return true
                        }
                    }
                    f = Math.round(motionEvent.getX() - floatValue3) as Float
                    f2 = Math.round(motionEvent.getY() - floatValue4) as Float
                    floatValue3 = motionEvent.getX()
                    floatValue4 = motionEvent.getY()
                    abs = Math.abs(motionEvent.getX() - floatValue)
                    abs2 = Math.abs(motionEvent.getY() - floatValue2)
                    boolean1 = true
                    if (boolean3) {
                    }
                    if (boolean3) {
                    }
                    x = motionEvent.getX() - floatValue
                    y = motionEvent.getY() - floatValue2
                    if (mMouseControl != null) {
                        mMouseControl!!.scroll(x.toDouble(), y.toDouble())
                    }
                    stringBuilder = StringBuilder()
                    stringBuilder.append("sending scroll ")
                    stringBuilder.append(x)
                    stringBuilder.append(" ,")
                    stringBuilder.append(x)
                    Log.d("main", stringBuilder.toString())
                    if (boolean3) {
                        boolean1 = false
                        if (mTimerTask != null) {
                            mTimerTask!!.cancel()
                            mTimerTask = null
                            Log.d("main", "ending autoscroll")
                        }
                    }
                    return true
                }
            }
            z = true
            boolean2 = z
            when (motionEvent.getActionMasked()) {
                0 -> {
                    boolean3 = true
                    longValue = motionEvent.getEventTime()
                    floatValue = motionEvent.getX()
                    floatValue2 = motionEvent.getY()
                }
                1 -> {
                    boolean3 = false
                    boolean1 = false
                    boolean2 = false
                    floatValue3 = java.lang.Float.NaN
                    floatValue4 = java.lang.Float.NaN
                }
                else -> {
                }
            }
            if (floatValue3 === java.lang.Float.NaN) {
                if (floatValue4 !== java.lang.Float.NaN) {
                    f = 0.0f
                    f2 = f
                    floatValue3 = motionEvent.getX()
                    floatValue4 = motionEvent.getY()
                    abs = Math.abs(motionEvent.getX() - floatValue)
                    abs2 = Math.abs(motionEvent.getY() - floatValue2)
                    boolean1 = true
                    if (boolean3) {
                    }
                    if (boolean3) {
                    }
                    x = motionEvent.getX() - floatValue
                    y = motionEvent.getY() - floatValue2
                    if (mMouseControl != null) {
                        mMouseControl!!.scroll(x.toDouble(), y.toDouble())
                    }
                    stringBuilder = StringBuilder()
                    stringBuilder.append("sending scroll ")
                    stringBuilder.append(x)
                    stringBuilder.append(" ,")
                    stringBuilder.append(x)
                    Log.d("main", stringBuilder.toString())
                    if (boolean3) {
                        boolean1 = false
                        if (mTimerTask != null) {
                            mTimerTask!!.cancel()
                            mTimerTask = null
                            Log.d("main", "ending autoscroll")
                        }
                    }
                    return true
                }
            }
            f = Math.round(motionEvent.getX() - floatValue3) as Float
            f2 = Math.round(motionEvent.getY() - floatValue4) as Float
            floatValue3 = motionEvent.getX()
            floatValue4 = motionEvent.getY()
            abs = Math.abs(motionEvent.getX() - floatValue)
            abs2 = Math.abs(motionEvent.getY() - floatValue2)
            boolean1 = true
            if (boolean3) {
            }
            if (boolean3) {
            }
            x = motionEvent.getX() - floatValue
            y = motionEvent.getY() - floatValue2
            if (mMouseControl != null) {
                mMouseControl!!.scroll(x.toDouble(), y.toDouble())
            }
            stringBuilder = StringBuilder()
            stringBuilder.append("sending scroll ")
            stringBuilder.append(x)
            stringBuilder.append(" ,")
            stringBuilder.append(x)
            Log.d("main", stringBuilder.toString())
            if (boolean3) {
                boolean1 = false
                if (mTimerTask != null) {
                    mTimerTask!!.cancel()
                    mTimerTask = null
                    Log.d("main", "ending autoscroll")
                }
            }
            return true
        }
    }

    private val aC = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            acCall()
        }

    }

    internal inner class acCall : Launcher.AppLaunchListener {
        override fun onSuccess(`object`: LaunchSession?) {
            m21765a(`object` as LaunchSession)
        }

        override fun onError(error: ServiceCommandError?) {
        }

        fun m21765a(launchSession: LaunchSession) {
            mLaunchSession = launchSession
            utilCall = C1352a(true, 200, "InputPickerShowing")
        }
    }

    private val aE = object : View.OnClickListener {

        override fun onClick(view: View) {
            val c1352a: C1352a
            when (view.getId()) {
                R.id.volume_DOWN -> {
                    mVolumeControl!!.volumeDown(null)

                    c1352a = C1352a(true, 200, "VolumeDecreased")
                }
                R.id.volume_UP -> {
                    mVolumeControl!!.volumeUp(null)
                    c1352a = C1352a(true, 200, "VolumeIncreased")
                }
                else -> return
            }
            utilCall = c1352a
        }
    }

    private val aD = object : View.OnClickListener {

        override fun onClick(view: View) {

            val c1352a: C1352a
            mVolumeControl!!.setMute(mMuteButton!!.isChecked(), null)
            if (mMuteButton!!.isChecked()) {

                c1352a = C1352a(true, 200, "MuteMedia")
            } else if (!mMuteButton!!.isChecked()) {

                c1352a = C1352a(true, 200, "UnMuteMedia")
            } else {
                return
            }
            utilCall = c1352a
        }
    }


    private val aF = object : View.OnClickListener {

        override fun onClick(view: View) {
            mediaControl!!.play(null)
        }
    }


    private val aG = object : View.OnClickListener {

        override fun onClick(view: View) {
            mediaControl!!.rewind(null)
        }
    }

    private val aH = object : View.OnClickListener {

        override fun onClick(view: View) {
            mediaControl!!.fastForward(null)
        }
    }

    private val aK = object : View.OnClickListener {

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onClick(view: View) {
            callGallery()
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun callGallery(): Boolean {

        if (hasStoragePermissions()) {
            // startActivity(Intent(this, BrowseGallery::class.java))

        } else {
            storageTask()
        }

        return true
    }

    private val aL = object : View.OnClickListener {

        override fun onClick(view: View) {
            // startActivity(Intent(this@WifiTVRemoteActivity, AppsList::class.java))
        }
    }

    private val aM = object : View.OnClickListener {

        override fun onClick(view: View) {
            //  startActivity(Intent(this@WifiTVRemoteActivity, ChannelList::class.java))
        }
    }

    private val aB = object : VolumeControl.MuteListener {
        override fun onSuccess(`object`: Boolean?) {
            m21764a(`object` as Boolean)
        }

        fun m21764a(bool: Boolean) {
            mMuteButton!!.setChecked(bool)
        }

        override fun onError(serviceCommandError: ServiceCommandError) {
            val stringBuilder = StringBuilder()
            stringBuilder.append("Error subscribing to mute: ")
            stringBuilder.append(serviceCommandError)
            Log.d("LG", stringBuilder.toString())
        }

    }

    private val aO = object : View.OnClickListener {

        override fun onClick(view: View) {
            if (mRelativeLayout3!!.getVisibility() == View.VISIBLE) {
                mLinearLayout!!.visibility = View.GONE
                mRelativeLayout6!!.visibility = View.VISIBLE
                mRelativeLayout5!!.visibility = View.VISIBLE
                mRelativeLayout3!!.visibility = View.GONE
                mRelativeLayout1!!.visibility = View.VISIBLE
                return
            }
            mRelativeLayout3!!.visibility = View.VISIBLE
            mLinearLayout!!.visibility = View.GONE
            mRelativeLayout6!!.visibility = View.GONE
            mRelativeLayout5!!.visibility = View.GONE
            mRelativeLayout1!!.visibility = View.GONE
        }
    }

    private val aN = object : View.OnClickListener {

        override fun onClick(view: View) {
            if (mLinearLayout!!.getVisibility() == View.VISIBLE) {
                mLinearLayout!!.visibility = View.GONE
                mRelativeLayout1!!.visibility = View.VISIBLE
                mRelativeLayout3!!.visibility = View.GONE
            } else {
                mLinearLayout!!.visibility = View.VISIBLE
                mRelativeLayout1!!.visibility = View.GONE
                mRelativeLayout3!!.visibility = View.GONE
                mRelativeLayout6!!.visibility = View.VISIBLE
                mRelativeLayout5!!.visibility = View.VISIBLE
            }
            if (mRelativeLayout3!!.getVisibility() == View.VISIBLE) {
                mLinearLayout!!.visibility = View.GONE
                mRelativeLayout6!!.visibility = View.VISIBLE
                mRelativeLayout5!!.visibility = View.VISIBLE
                mRelativeLayout3!!.visibility = View.GONE
                mRelativeLayout1!!.visibility = View.VISIBLE
            }
        }
    }

    private val aJ = object : TextInputControl.TextInputStatusListener {
        override fun onSuccess(`object`: TextInputStatusInfo?) {
            callTestInputMethod(`object` as TextInputStatusInfo)
        }

        fun callTestInputMethod(textInputStatusInfo: TextInputStatusInfo) {
            val isFocused = textInputStatusInfo.isFocused()
            val textInputType = textInputStatusInfo.getTextInputType()
            val isPredictionEnabled = textInputStatusInfo.isPredictionEnabled()
            val isCorrectionEnabled = textInputStatusInfo.isCorrectionEnabled()
            val isAutoCapitalization = textInputStatusInfo.isAutoCapitalization()
            val isHiddenText = textInputStatusInfo.isHiddenText()
            val isFocusChanged = textInputStatusInfo.isFocusChanged()
            if (textInputType !== TextInputStatusInfo.TextInputType.DEFAULT) {
                var i: Int
                if (textInputType === TextInputStatusInfo.TextInputType.NUMBER) {
                    i = 2
                } else if (textInputType === TextInputStatusInfo.TextInputType.PHONE_NUMBER) {
                    i = 3
                } else {
                    var i2 = 1
                    if (textInputType === TextInputStatusInfo.TextInputType.URL) {
                        i2 = 17
                    } else if (textInputType === TextInputStatusInfo.TextInputType.EMAIL) {
                        i2 = 33
                    }
                    if (isPredictionEnabled) {
                        i2 = i2 or 65536
                    }
                    if (isCorrectionEnabled) {
                        i2 = i2 or 32768
                    }
                    if (isAutoCapitalization) {
                        i2 = i2 or 16384
                    }
                    i = if (isHiddenText) i2 or 128 else i2
                    if (!boolean5) {
                        i = i or 524288
                    }
                }
                if (mEditText!!.getInputType() !== i) {
                    mEditText!!.setInputType(i)
                }
            }
            if (isFocused) {
                if (isFocusChanged) {
                    setEmpty()
                }
                mEditText!!.requestFocus()
                (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(getCurrentFocus(), 2)
                return
            }
            boolean5 = false
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(mEditText!!.getWindowToken(), 0)
            setEmpty()
        }

        override fun onError(serviceCommandError: ServiceCommandError) {
        }

    }

    fun setEmpty() {
        this.mEditText!!.setText("​")
    }

    internal inner class C13275(wifiTv: Context) : View.OnClickListener {

        override fun onClick(view: View) {
            mKeyControl!!.sendKeyCode(KeyControl.KeyCode.ENTER, null)
        }
    }

    internal inner class C13264(wifiTv: Context) : View.OnClickListener {

        override fun onClick(view: View) {
            if (mTextInputBTN!!.isSelected()) {
                mTextInputBTN!!.setSelected(false)
                (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(mEditText!!.getWindowToken(), 0)
                return
            }
            mTextInputBTN!!.setSelected(true)
            mEditText!!.requestFocus()
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(getCurrentFocus(), 2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            DiscoveryManager.init(this@WifiTVRemoteActivity)

        } catch (e: Exception) {
            Log.d("EAEAARR789", "$e")
        }
        // Handler().postDelayed(Runnable {

        // }, 10)

        setContentView(R.layout.wifi_tv_newlikesony)
        //removing status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        storageTask()

        //code for ads
        mAdView = findViewById<AdView>(R.id.adView) as AdView
        adBRequest = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build()
        mAdView!!.loadAd(adBRequest)

        val connManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWifi: NetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!;

        if (mWifi.isConnected()) {
            // Do whatever

            // if (ConnectionDetector.checkInternetConnection(this@WifiTVRemoteActivity)) {

            showDevicePicker()

            Handler().postDelayed(Runnable {
                try {

                    DiscoveryManager.getInstance().setPairingLevel(DiscoveryManager.PairingLevel.ON)
                    DiscoveryManager.getInstance().start()
                } catch (e: Exception) {
                    Log.d("EAEAARR098", "$e")
                }

                Log.d("TARAAR", "${DiscoveryManager.getInstance().setPairingLevel(DiscoveryManager.PairingLevel.ON)}")
                Log.d("TARAAR6", "${DiscoveryManager.getInstance().start()}")

            }, 10)

            dismissConnectivity()
        } else {
            Toast.makeText(applicationContext, "No Internet Connection Available", Toast.LENGTH_SHORT).show()
        }
        utilCall = C1352a()

        this.mDialPadBTN = findViewById(R.id.btn_dialpad2) as Button
        this.mMouseBTN = findViewById(R.id.btn_mouse) as Button
        this.mMouseBTN!!.setOnClickListener(this.aO)
        this.mDialPadBTN!!.setOnClickListener(this.aN)
        //this.mRelativeLayout4 = findViewById(R.id.llmain1) as RelativeLayout
        this.mRelativeLayout1 = findViewById(R.id.ll_circle) as RelativeLayout
        this.mRelativeLayout2 = findViewById(R.id.ll_bar2) as RelativeLayout
        this.mRelativeLayout3 = findViewById(R.id.ll_mouse) as RelativeLayout
        this.mLinearLayout = findViewById(R.id.ll_dialpad) as LinearLayout
        this.mRelativeLayout5 = findViewById(R.id.ll_right) as RelativeLayout
        this.mRelativeLayout6 = findViewById(R.id.ll_left) as RelativeLayout
        this.mPowerOffBTN = findViewById(R.id.powerOnOff) as Button
        this.mChannelUpBTN = findViewById(R.id.channel_UP) as Button
        this.mChannelDownlBTN = findViewById(R.id.channel_DOWN) as Button
        this.mControlUPBTN = findViewById(R.id.Ok) as Button
        this.mKeyControlUp = findViewById(R.id.Ok_Up) as Button
        this.mLeftButton = findViewById(R.id.Ok_left) as Button
        this.mRightButton = findViewById(R.id.Ok_right) as Button
        this.mBackButton = findViewById(R.id.back_Button) as Button
        this.mDownButton = findViewById(R.id.OK_Down) as Button
        this.mHomeButton = findViewById(R.id.buttonHome) as Button
        this.mExternalInputControlButton = findViewById(R.id.buttonInput) as Button
        this.mVolumeControlBTN1 = findViewById(R.id.volume_UP) as Button
        this.mVolumeControlBTN2 = findViewById(R.id.volume_DOWN) as Button
        this.mPlayButton = findViewById(R.id.play_Button) as Button
        this.f3801Q = findViewById<Button>(R.id.previous)
        this.f3800P = findViewById<Button>(R.id.next)
        this.mRewindBTN = findViewById(R.id.freverse) as Button
        this.mFastFwdBTN = findViewById(R.id.fforward) as Button
        this.mOKBTN = findViewById(R.id.clickButton) as Button
        this.mView = findViewById(R.id.trackpadView)
        this.mTextInputBTN = findViewById<Button>(R.id.openKeyboardButton)
        this.mTextInputBTN!!.isSelected = false
        this.mEditText = findViewById<EditText>(R.id.editField)
        setEmpty()
        this.mEditText!!.setInputType(524289)
        this.ak = findViewById<Button>(R.id.browse_gallery)
        this.al = findViewById<Button>(R.id.all_apps)
        this.am = findViewById<Button>(R.id.all_channels)
        this.mPowerOffBTN = findViewById<Button>(R.id.powerOnOff)
        this.noButtonsArray[0] = findViewById(R.id.button0) as Button
        this.noButtonsArray[1] = findViewById(R.id.button1) as Button
        this.noButtonsArray[2] = findViewById(R.id.button2) as Button
        this.noButtonsArray[3] = findViewById(R.id.button3) as Button
        this.noButtonsArray[4] = findViewById(R.id.button4) as Button
        this.noButtonsArray[5] = findViewById(R.id.button5) as Button
        this.noButtonsArray[6] = findViewById(R.id.button6) as Button
        this.noButtonsArray[7] = findViewById(R.id.button7) as Button
        this.noButtonsArray[8] = findViewById(R.id.button8) as Button
        this.noButtonsArray[9] = findViewById(R.id.button9) as Button
        this.mMuteButton = findViewById(R.id.mute) as CheckBox

        connectWithMedia(mConnectableDevices)


        mEditText!!.addTextChangedListener(object : TextWatcher {
            var f3738a = ""

            override fun afterTextChanged(editable: Editable?) {
                if (editable!!.length == 0) {
                    editable.append("​")
                }
                this.f3738a = editable.toString().replace("​", ""); }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun showDevicePicker() {
        this.mDevicePicker = DevicePicker(this)

        this.mAlertDialog = this.mDevicePicker!!.getPickerDialog("Smart Devices List", object : AdapterView.OnItemClickListener {

            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, j: Long) {
                mConnectableDevices = adapterView.getItemAtPosition(i) as ConnectableDevice
                mConnectableDevices!!.addListener(mConnectableDevicesListener)
                mConnectableDevices!!.setPairingType(null)
                mConnectableDevices!!.connect()
                mDevicePicker!!.pickDevice(mConnectableDevices)
            }
        })
        this.mAlertDialog!!.setCanceledOnTouchOutside(true)
        this.mAlertDialog!!.setCancelable(true)
        this.mAlertDialogR = AlertDialog.Builder(this).setTitle("Pair with TV").setMessage("Please confirm the code on your TV").setPositiveButton("Okay", null).setNegativeButton("Cancel", object : DialogInterface.OnClickListener {


            override fun onClick(dialogInterface: DialogInterface, i: Int) {
                mDevicePicker!!.cancelPicker()
                dismissConnectivity()
            }
        }).create()

        val editText = EditText(this)
        editText.inputType = 1
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        this.paringCodeDialog = AlertDialog.Builder(this).setTitle("Enter Pairing Code on TV").setView(editText).setPositiveButton("OK", object : DialogInterface.OnClickListener {

            override fun onClick(dialogInterface: DialogInterface, i: Int) {
                if (mConnectableDevices != null) {
                    mConnectableDevices!!.sendPairingKey(editText.text.toString().trim())
                    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
                }
            }
        }).setNegativeButton("Cancel", object : DialogInterface.OnClickListener {

            override fun onClick(dialogInterface: DialogInterface, i: Int) {
                mDevicePicker!!.cancelPicker()
                dismissConnectivity()
                inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
            }
        }).create()
    }


    fun disconnectNRemoveListener(connectableDevice: ConnectableDevice?) {
        if (connectableDevice != null) {
            Toast.makeText(applicationContext, "Failed to Connected", Toast.LENGTH_SHORT).show()
        }
        if (mConnectableDevices != null) {
            mConnectableDevices!!.removeListener(mConnectableDevicesListener)
            mConnectableDevices!!.disconnect()
            mConnectableDevices = null
        }
    }

    fun isDisconnected(connectableDevice: ConnectableDevice) {
        if (this.mAlertDialogR!!.isShowing()) {
            this.mAlertDialogR!!.dismiss()
        }
        if (this.paringCodeDialog!!.isShowing()) {
            this.paringCodeDialog!!.dismiss()
        }
        mConnectableDevices!!.removeListener(mConnectableDevicesListener)
        mConnectableDevices = null
    }


    fun isConnectedDone(connectableDevice: ConnectableDevice) {
        Toast.makeText(applicationContext, "Device Connected", Toast.LENGTH_SHORT).show()
        connectWithMedia(connectableDevice)
    }

    fun connectWithMedia(connectableDevice: ConnectableDevice?) {
        mConnectableDevices = connectableDevice
        if (connectableDevice == null) {
            this.mediaControl = null
            mTVControl = null
            this.mVolumeControl = null
            this.mPowerControl = null
            this.mKeyControl = null
            mLauncher = null
            mMediaControl = null
            this.mExInputControl = null
            this.mMouseControl = null
            this.mTextInputControl = null
            this.mToasetControl = null
            mWebAppLauncher = null
            enableButton()
            return
        }
        this.mediaControl = mConnectableDevices!!.getCapability(MediaControl::class.java) as MediaControl
        Log.d("TARAAR", "${this.mediaControl}")

        mMediaControl = mConnectableDevices!!.getCapability(MediaPlayer::class.java) as MediaPlayer
        this.mMouseControl = mConnectableDevices!!.getCapability(MouseControl::class.java) as MouseControl
        mTVControl = mConnectableDevices!!.getCapability(TVControl::class.java) as TVControl
        this.mVolumeControl = mConnectableDevices!!.getCapability(VolumeControl::class.java) as VolumeControl
        this.mPowerControl = mConnectableDevices!!.getCapability(PowerControl::class.java) as PowerControl
        this.mKeyControl = mConnectableDevices!!.getCapability(KeyControl::class.java) as KeyControl
        this.mExInputControl = mConnectableDevices!!.getCapability(ExternalInputControl::class.java) as ExternalInputControl
        this.mTextInputControl = mConnectableDevices!!.getCapability(TextInputControl::class.java) as TextInputControl
        this.mToasetControl = mConnectableDevices!!.getCapability(ToastControl::class.java) as ToastControl
        mWebAppLauncher = mConnectableDevices!!.getCapability(WebAppLauncher::class.java) as WebAppLauncher
        mLauncher = mConnectableDevices!!.getCapability(Launcher::class.java) as Launcher
        keyControlCall()
    }

    fun keyControlCall() {
        if (this.buttonArray != null) {
            for (enabled in this.buttonArray!!) {
                enabled.setEnabled(true)
            }
        }
        if (this.mMouseControl != null) {
            this.mMouseControl!!.connectMouse()
        }
        this.mView!!.setOnTouchListener(C13242(this))
        if (mConnectableDevices!!.hasCapability(KeyControl.OK)) {
            mOKBTN!!.setOnClickListener(C13253(this))
        } else {
            disableBTN(this.mOKBTN!!)
        }
        if (callTextInput() == null) {
            disableBTN(this.mTextInputBTN!!)
        } else if (callConnectebleDevice().hasCapability(TextInputControl.Subscribe)) {
            disableBTN(this.mTextInputBTN!!)
            callTextInput().subscribeTextInputStatus(this.aJ)
        } else {
            this.mTextInputBTN!!.setOnClickListener(C13264(this))
        }
        this.mControlUPBTN!!.setOnClickListener(C13275(this))
        if (mConnectableDevices!!.hasCapability(KeyControl.Up)) {
            this.mKeyControlUp!!.setOnClickListener(C13286(this))
        } else {
            disableBTN(this.mKeyControlUp!!)
        }
        if (mConnectableDevices!!.hasCapability(KeyControl.Left)) {
            this.mLeftButton!!.setOnClickListener(C13297(this))
        } else {
            disableBTN(this.mLeftButton!!)
        }
        if (mConnectableDevices!!.hasCapability(KeyControl.Right)) {
            this.mRightButton!!.setOnClickListener(C13308(this))
        } else {
            disableBTN(this.mRightButton!!)
        }
        if (mConnectableDevices!!.hasCapability(KeyControl.Down)) {
            this.mDownButton!!.setOnClickListener(C13319(this))
        } else {
            disableBTN(this.mDownButton!!)
        }
        if (mConnectableDevices!!.hasCapability(KeyControl.Back)) {
            this.mBackButton!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    if (mKeyControl != null) {
                        mKeyControl!!.back(null)
                    }
                }
            })
        } else {
            disableBTN(this.mBackButton!!)
        }
        if (mConnectableDevices!!.hasCapability(KeyControl.Home)) {
            this.mHomeButton!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    if (mKeyControl != null) {
                        mKeyControl!!.home(null)
                        utilCall = C1352a(true, 200, "HomeClicked")
                    }
                }
            })
        } else {
            disableBTN(this.mHomeButton!!)
        }
        if (mConnectableDevices!!.hasAnyCapability(KeyControl.KeyCode)) {
            this.noButtonsArray[0]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_0, null)
                }
            })
            this.noButtonsArray[1]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_1, null)
                }
            })
            this.noButtonsArray[2]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_2, null)
                }
            })
            this.noButtonsArray[3]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_3, null)
                }
            })
            this.noButtonsArray[4]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_4, null)
                }
            })
            this.noButtonsArray[5]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_5, null)
                }
            })
            this.noButtonsArray[6]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_6, null)
                }
            })
            this.noButtonsArray[7]!!.setOnClickListener(object : View.OnClickListener {


                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_7, null)
                }
            })
            this.noButtonsArray[8]!!.setOnClickListener(object : View.OnClickListener {
                /* renamed from: a */

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_8, null)
                }
            })
            this.noButtonsArray[9]!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mKeyControl!!.sendKeyCode(KeyControl.KeyCode.NUM_9, null)
                }
            })
        } else {
            disableBTN(this.noButtonsArray[0]!!)
            disableBTN(this.noButtonsArray[1]!!)
            disableBTN(this.noButtonsArray[2]!!)
            disableBTN(this.noButtonsArray[3]!!)
            disableBTN(this.noButtonsArray[4]!!)
            disableBTN(this.noButtonsArray[5]!!)
            disableBTN(this.noButtonsArray[6]!!)
            disableBTN(this.noButtonsArray[7]!!)
            disableBTN(this.noButtonsArray[8]!!)
            disableBTN(this.noButtonsArray[9]!!)
        }
        if (mConnectableDevices!!.hasCapability(TVControl.Channel_Up)) {
            this.mChannelUpBTN!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View) {
                    mTVControl!!.channelUp(null)
                }
            })
        } else {
            disableBTN(this.mChannelUpBTN!!)
        }
        if (mConnectableDevices!!.hasCapability(TVControl.Channel_Down)) {
            this.mChannelDownlBTN!!.setOnClickListener(object : View.OnClickListener {


                override fun onClick(view: View) {
                    mTVControl!!.channelDown(null)
                }
            })
        } else {
            disableBTN(this.mChannelDownlBTN!!)
        }
        if (mConnectableDevices!!.hasCapability(PowerControl.Off)) {
            this.mPowerOffBTN!!.setOnClickListener(object : View.OnClickListener {


                override fun onClick(view: View) {
                    utilCall = C1352a(true, 200, "PowerOFF")
                    mPowerControl!!.powerOff(null)
                }
            })
        } else {
            disableBTN(this.mPowerOffBTN!!)
        }
        if (mConnectableDevices!!.hasCapability(VolumeControl.Mute_Subscribe)) {
            this.serviceSubsMute = this.mVolumeControl!!.subscribeMute(this.aB)
        }
        this.mMuteButton!!.setEnabled(mConnectableDevices!!.hasCapability(VolumeControl.Mute_Set))
        this.mVolumeControlBTN1!!.setEnabled(mConnectableDevices!!.hasCapability(VolumeControl.Volume_Up_Down))
        this.mVolumeControlBTN2!!.setEnabled(mConnectableDevices!!.hasCapability(VolumeControl.Volume_Up_Down))
        this.mPlayButton!!.setEnabled(mConnectableDevices!!.hasCapability(MediaControl.Play))
        this.mRewindBTN!!.setEnabled(mConnectableDevices!!.hasCapability(MediaControl.Rewind))
        this.mFastFwdBTN!!.setEnabled(mConnectableDevices!!.hasCapability(MediaControl.FastForward))
        this.mExternalInputControlButton!!.setEnabled(mConnectableDevices!!.hasCapability(ExternalInputControl.Picker_Launch))
        this.mExternalInputControlButton!!.setOnClickListener(this.aC)
        this.mVolumeControlBTN1!!.setOnClickListener(this.aE)
        this.mVolumeControlBTN2!!.setOnClickListener(this.aE)
        this.mMuteButton!!.setOnClickListener(this.aD)
        this.mPlayButton!!.setOnClickListener(this.aF)
        this.mRewindBTN!!.setOnClickListener(this.aG)
        this.mFastFwdBTN!!.setOnClickListener(this.aH)
        this.ak!!.setOnClickListener(this.aK)
        this.al!!.setOnClickListener(this.aL)
        this.am!!.setOnClickListener(this.aM)
    }


    //m4321a
    fun disableBTN(button: Button) {
        button.isEnabled = false
    }

    //m4330j
    fun callTextInput(): TextInputControl {
        return this.mTextInputControl!!;
    }

    //m4311h
    fun callConnectebleDevice(): ConnectableDevice {
        return mConnectableDevices!!
    }

    //m4323b
    fun dismissConnectivity() {
        if (!isFinishing) {
            Log.d("TATATAT12", "CAlled: ${mConnectableDevices != null}")

            if (mConnectableDevices != null) {

                if (mConnectableDevices!!.isConnected) {

                    mConnectableDevices!!.disconnect()
                }
                mConnectableDevices!!.removeListener(mConnectableDevicesListener)
                mConnectableDevices = null
                return
            }
            // this.mAlertDialog!!.setCancelable(true)
            this.mAlertDialog!!.show()
            // this.mAlertDialog!!.setCancelable(true)
            listOfConnectableDevices()
            /*  var connectableDevices: ArrayList<ConnectableDevice> = listOfConnectableDevices()
              if (connectableDevices.isEmpty()) {
                  if (this.mAlertDialog != null) {
                      this.mAlertDialog!!.dismiss()
                  }
                  if (mConnectableDevices != null) {
                      mConnectableDevices!!.disconnect()
                  }
              }*/

            Log.d("TATATAT1", "CAlled: ${listOfConnectableDevices()}")
            //  Log.d("TATATAT12", "CAlled: ${mConnectableDevices!!.isConnected}")
        }
    }


    fun listOfConnectableDevices(): ArrayList<ConnectableDevice> {
        val arrayList = ArrayList<ConnectableDevice>()
        for (connectableDevice in DiscoveryManager.getInstance().getCompatibleDevices().values) {
            if (connectableDevice.hasAnyCapability(TVControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(VolumeControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(MediaControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(KeyControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(KeyControl.KeyCode)) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(TextInputControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(ExternalInputControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(ToastControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(MouseControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
        }

        // Log.d("TATATAT1", "${arrayList[0]}")
        return arrayList
    }
    /* fun listOfConnectableDevices():List<ConnectableDevice> {
        val arrayList:ArrayList<ConnectableDevice>  =  ArrayList()
        for (connectableDevice:ConnectableDevice  in DiscoveryManager.getInstance().getCompatibleDevices().values) {
            if (connectableDevice.hasAnyCapability(TVControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(VolumeControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(MediaControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(KeyControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability("KeyControl.KeyCode")) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(TextInputControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(ExternalInputControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(ToastControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
            if (connectableDevice.hasAnyCapability(MouseControl.Capabilities.toString())) {
                arrayList.add(connectableDevice)
            }
        }
        return arrayList
    }*/


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun storageTask() {
        if (!hasStoragePermissions()) {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to save your  pictures in storage.",
                    111,
                    Manifest.permission.TRANSMIT_IR,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    fun enableButton() {
        this.mView!!.setOnTouchListener(null)
        if (this.buttonArray != null) {
            for (button in this.buttonArray!!) {
                button.setOnClickListener(null)
                button.setEnabled(false)
            }
        }
        if (this.serviceSubsVolume != null) {
            this.serviceSubsVolume!!.unsubscribe()
            this.serviceSubsVolume = null
        }
        if (this.serviceSubsMute != null) {
            this.serviceSubsMute!!.unsubscribe()
            this.serviceSubsMute = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun hasStoragePermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.TRANSMIT_IR)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            111 ->
                // Toast.makeText(getApplicationContext(), "ponPermissionsGranted", Toast.LENGTH_LONG).show();
                EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onBackPressed() {
        DiscoveryManager.destroy()
        if (this.mAlertDialog != null) {
            this.mAlertDialog!!.dismiss()
        }
        if (mConnectableDevices != null) {
            mConnectableDevices!!.disconnect()
        }
        /* if (this.mAlertDialog != null) {
             this.mAlertDialog!!.dismiss()
         }
         if (mConnectableDevices != null) {
             mConnectableDevices!!.disconnect()
         }
         finish()*/
        // this.mDevicePicker!!.cancelPicker()
        // this.mAlertDialog!!.dismiss()
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        DiscoveryManager.destroy()
        if (this.mAlertDialog != null) {
            this.mAlertDialog!!.dismiss()
        }
        if (mConnectableDevices != null) {
            mConnectableDevices!!.disconnect()
        }
    }

}
