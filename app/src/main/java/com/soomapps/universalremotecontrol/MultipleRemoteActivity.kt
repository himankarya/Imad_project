package com.soomapps.universalremotecontrol

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.soomapps.universalremotecontrol.adapters.ViewPagerAdapter
import com.soomapps.universalremotecontrol.dto.DataModel
import com.soomapps.universalremotecontrol.fragments.*
import com.soomapps.universalremotecontrol.utils.ConnectionDetector
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MultipleRemoteActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private lateinit var remoteName: String
    private lateinit var dataModel: DataModel
    private lateinit var dataModelAL: Array<DataModel>

    private var mAdView: AdView? = null
    private var adBRequest: AdRequest? = null
    private var mAdMobInterstitialAd: InterstitialAd? = null
    private var frameLayout: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_remote)

        //removing status bar
       // requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        frameLayout  = findViewById<FrameLayout>(R.id.fl_adplaceholder)

//        val scrollView = findViewById(R.id.nest_scrollview) as NestedScrollView
//        scrollView.isFillViewport = true

        refreshAd()
        //code for ads
       /* mAdView = findViewById<AdView>(R.id.adView) as AdView
        adBRequest = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build()
        mAdView!!.loadAd(adBRequest)

        if (ConnectionDetector.checkInternetConnection(this@MultipleRemoteActivity)) {
            mAdView!!.visibility=View.VISIBLE
        } else {
            mAdView!!.visibility=View.GONE
        }*/
        // val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        //  setSupportActionBar(toolbar)
       // dataModelAL = intent.getSerializableExtra("Object") as Array<DataModel>
        dataModel = intent.getSerializableExtra("Single") as DataModel
        remoteName = dataModel.title

        viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        //total 61 multi remotes
        when (remoteName) {

            "Aiwa" -> {
                setupViewPagerAiwa(viewPager!!)
            }
            "Akai" -> {
                setupViewPagerAkai(viewPager!!)
            }
            "Alba" -> {

                setupViewPagerAlba(viewPager!!)
            }
            "AOC" -> {
                setupViewPagerAOC(viewPager!!)
            }
            "Apex" -> {
                setupViewPagerApex(viewPager!!)
            }
            "Atlanta DTH/STB" -> {
                setupViewPagerAtlanta(viewPager!!)
            }
            "AudioVox" -> {
                setupViewPagerAudioVox(viewPager!!)
            }
            "Bahun" -> {
                setupViewPagerBahun(viewPager!!)
            }
            "CCE" -> {
                setupViewPagerCCE(viewPager!!)
            }
            "Changhong" -> {
                setupViewPagerChanghong(viewPager!!)
            }
            "Coby" -> {
                setupViewPagerCoby(viewPager!!)
            }
            "Daewoo" -> {
                setupViewPagerDaewoo(viewPager!!)
            }
            "Dick Smith" -> {
                setupViewPagerDickSmith(viewPager!!)
            }
            "Durabrand" -> {
                setupViewPagerDurabrand(viewPager!!)
            }
            "Dynex" -> {
                setupViewPagerDynex(viewPager!!)
            }
            "Element" -> {
                setupViewPagerElement(viewPager!!)
            }
            "Emerson" -> {setupViewPagerEmerson(viewPager!!)
            }
            "Funai" -> {setupViewPagerFunai(viewPager!!)
            }
            "GoldMaster STB" -> {setupViewPagerGoldMaster(viewPager!!)
            }
            "GoldStar" -> {setupViewPagerGoldStar(viewPager!!)
            }
            "Grundig" -> {setupViewPagerGrundig(viewPager!!)
            }
            "Haier" -> {setupViewPagerHaier(viewPager!!)
            }
            "Hitachi" -> {setupViewPagerHitachi(viewPager!!)
            }
            "Hyundai" -> {setupViewPagerHyundai(viewPager!!)
            }
            "Insignia" -> {setupViewPagerInsignia(viewPager!!)
            }
            "Jensen" -> {setupViewPagerJensen(viewPager!!)
            }
            "JVC" -> {setupViewPagerJVC(viewPager!!)
            }
            "Logik" -> {setupViewPagerLogik(viewPager!!)
            }
            "Loewe" -> {setupViewPagerLoewe(viewPager!!)
            }
            "Magnavox" -> {setupViewPagerMagnavox(viewPager!!)
            }
            "Mascom" -> {setupViewPagerMascom(viewPager!!)
            }
            "Medion STB" -> {setupViewPagerMedionSTB(viewPager!!)
            }
            "Medion TV" -> {setupViewPagerMedionTV(viewPager!!)
            }
            "Mitsubishi" -> {setupViewPagerMitsubishi(viewPager!!)
            }
            "Mystery" -> {setupViewPagerMystery(viewPager!!)
            }
            "NEC" -> {setupViewPagerNEC(viewPager!!)
            }
            "Next STB" -> {setupViewPagerNext(viewPager!!)
            }
            "Nexus" -> {setupViewPagerNexus(viewPager!!)
            }
            "Niko" -> {setupViewPagerNiko(viewPager!!)
            }
            "Olevia" -> {setupViewPagerOlevia(viewPager!!)
            }
            "Orion" -> {setupViewPagerOrion(viewPager!!)
            }
            "Palsonic" -> {setupViewPagerPalsonic(viewPager!!)
            }
            "Philco" -> {
                setupViewPagerPhilco(viewPager!!)
            }
            "Pioneer" -> {
                setupViewPagerPioneer(viewPager!!)
            }
            "Polaroid" -> {setupViewPagerPolaroid(viewPager!!)
            }
            "Prima" -> {setupViewPagerPrima(viewPager!!)
            }
            "RCA" -> {setupViewPagerRCA(viewPager!!)
            }
            "SEG" -> {setupViewPagerSEG(viewPager!!)
            }
            "Sinotec" -> {setupViewPagerSinotec(viewPager!!)
            }
            "Skyworth" -> {setupViewPagerSkyworth(viewPager!!)
            }
            "Soniq" -> {setupViewPagerSoniq(viewPager!!)
            }
            "SONY" -> {setupViewPagerSONY(viewPager!!)
            }
            "Supra" -> {setupViewPagerSupra(viewPager!!)
            }
            "Sylvania" -> {setupViewPagerSylvania(viewPager!!)
            }
            "Technika" -> {setupViewPagerTechnika(viewPager!!)
            }
            "Thomson" -> {setupViewPagerThomson(viewPager!!)
            }
            "Videocon STB" -> {setupViewPagerVideocon(viewPager!!)
            }
            "Viore" -> {setupViewPagerViore(viewPager!!)
            }
            "Westinghouse" -> {setupViewPagerWestinghouse(viewPager!!)
            }
            "Wharfedale" -> {setupViewPagerWharfedale(viewPager!!)
            }
            "Zenith" -> {setupViewPagerZenith(viewPager!!)
            }

        }
        // }
        tabLayout = findViewById<View>(R.id.tab_layout) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)

    }

    private fun setupViewPagerAiwa(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Aiwa_1(), "Aiwa Generic")
        adapter.addFragment(Aiwa_2(), "Aiwa LCT2060")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }

    private fun setupViewPagerAkai(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Akai_1(), "Akai-1")
        adapter.addFragment(Akai_2(), "Akai-2")
        adapter.addFragment(Akai_3(), "Akai-3")
        adapter.addFragment(Akai_4(), "Akai-4")
        adapter.addFragment(Akai_5(), "Akai-5")
        adapter.addFragment(Akai_6(), "Akai-6")
        adapter.addFragment(Akai_7(), "Akai-7")
        adapter.addFragment(Akai_8(), "Akai-8")
        adapter.addFragment(Akai_9(), "Akai-9")
        adapter.addFragment(Akai_10(), "Akai-10")
        adapter.addFragment(Akai_11(), "Akai-11")
        adapter.addFragment(Akai_12(), "Akai-12")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=12
    }
    private fun setupViewPagerAlba(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Alba_1(), "Alba Generic")
        adapter.addFragment(Alba_2(), "Alba-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerAOC(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AOC_1(), "AOC-Generic")
        adapter.addFragment(AOC_2(), "AOC-1")
        adapter.addFragment(AOC_3(), "AOC-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerApex(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Apex_1(), "Apex-Generic")
        adapter.addFragment(Apex_2(), "Apex-1")
        adapter.addFragment(Apex_3(), "Apex-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerAtlanta(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Atlanta_DTH_STB_1(), "Atlanta-Generic")
        adapter.addFragment(Atlanta_DTH_STB_2(), "Atlanta-1")
        adapter.addFragment(Atlanta_DTH_STB_3(), "Atlanta-2")
        adapter.addFragment(Atlanta_DTH_STB_4(), "Atlanta-3")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=4
    }
    private fun setupViewPagerAudioVox(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AudioVox_1(), "AudioVox-Generic")
        adapter.addFragment(AudioVox_2(), "AudioVox-1")
        adapter.addFragment(AudioVox_3(), "AudioVox-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerBahun(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Bahun_1(), "Bahun Generic")
        adapter.addFragment(Bahun_2(), "Bahun-0")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerCCE(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CCE_1(), "CCE Generic")
        adapter.addFragment(CCE_2(), "CCE-0")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerChanghong(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Changhong_1(), "Changhong Generic")
        adapter.addFragment(Changhong_2(), "Changhong-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerCoby(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Coby_1(), "Coby-Generic")
        adapter.addFragment(Coby_2(), "Coby-1")
        adapter.addFragment(Coby_3(), "Coby-2")
        adapter.addFragment(Coby_4(), "Coby-3")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=4
    }
    private fun setupViewPagerDaewoo(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Daewoo_1(), "Daewoo Generic")
        adapter.addFragment(Daewoo_2(), "Daewoo CTS2090")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerDickSmith(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Dick_Smith_1(), "DickSmith Generic")
        adapter.addFragment(Dick_Smith_2(), "DickSmith 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerDurabrand(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Durabrand_1(), "Durabrand Generic")
        adapter.addFragment(Durabrand_2(), "Durabrand 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerDynex(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Dynex_1(), "Dynex Generic")
        adapter.addFragment(Dynex_2(), "Dynex 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerElement(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Element_1(), "Element_FLX_O")
        adapter.addFragment(Element_2(), "Element_FLX_E")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerEmerson(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Emerson_1(), "Emerson-Generic")
        adapter.addFragment(Emerson_2(), "Emerson-1")
        adapter.addFragment(Emerson_3(), "Emerson-13in")
        adapter.addFragment(Emerson_4(), "Emerson-TC-1375")
        adapter.addFragment(Emerson_5(), "Emerson-65")
        adapter.addFragment(Emerson_6(), "Emerson-134")
        adapter.addFragment(Emerson_7(), "Emerson-LC195A")
        adapter.addFragment(Emerson_8(), "Emerson-LC195B")
        adapter.addFragment(Emerson_9(), "Emerson-LC320A")
        adapter.addFragment(Emerson_10(), "Emerson-LC320B")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=10
    }
    private fun setupViewPagerFunai(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Funai_1(), "Funai Generic")
        adapter.addFragment(Funai_2(), "Funai 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerGoldMaster(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GoldMaster_STB_1(), "GoldMaster-1")
        adapter.addFragment(GoldMaster_STB_2(), "GoldMaster-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerGoldStar(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GoldStar_1(), "GoldStar-1")
        adapter.addFragment(GoldStar_2(), "GoldStar-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerGrundig(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Grundig_1(), "Elegance")
        adapter.addFragment(Grundig_2(), "GT-1401")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerHaier(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Haier_1(), "Haier-1")
        adapter.addFragment(Haier_2(), "Haier-2")
        adapter.addFragment(Haier_3(), "Haier-3")
        adapter.addFragment(Haier_4(), "Haier-4")
        adapter.addFragment(Haier_5(), "Haier-5")
        adapter.addFragment(Haier_6(), "Haier-6")
        adapter.addFragment(Haier_7(), "Haier-7")
        adapter.addFragment(Haier_8(), "Haier-8")
        adapter.addFragment(Haier_9(), "Haier-L32b1120c")
        adapter.addFragment(Haier_10(), "Haier-TN201AUV")
        adapter.addFragment(Haier_11(), "Haier-TV131AUV")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=11
    }
    private fun setupViewPagerHitachi(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Hitachi_1(), "Hitachi HDTV")
        adapter.addFragment(Hitachi_2(), "Hitachi UK")
        adapter.addFragment(Hitachi_3(), "Hitachi Ultravision")
        adapter.addFragment(Hitachi_4(), "Hitachi LCD")
        adapter.addFragment(Hitachi_5(), "Hitachi LE32H405")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
    }
    private fun setupViewPagerHyundai(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Hyundai_1(), "Hyundai-Generic")
        adapter.addFragment(Hyundai_2(), "Hyundai-1")
        adapter.addFragment(Hyundai_3(), "Hyundai-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerInsignia(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Insignia_1(), "Insignia")
        adapter.addFragment(Insignia_2(), "Insignia_NS-Series")
        adapter.addFragment(Insignia_3(), "Insignia_IS-Series")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerJensen(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Jensen_1(), "Jensen-Generic")
        adapter.addFragment(Jensen_2(), "Jensen-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerJVC(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(JVC_1(), "JVC-Generic")
        adapter.addFragment(JVC_2(), "JVC-RM-C542")
        adapter.addFragment(JVC_3(), "JVC-RM-C670")
        adapter.addFragment(JVC_4(), "JVC-RM-C728")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=4
    }
    private fun setupViewPagerLogik(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Logik_1(), "Logik-Generic")
        adapter.addFragment(Logik_2(), "Logik-1")
        adapter.addFragment(Logik_3(), "Logik-2")
        adapter.addFragment(Logik_4(), "Logik-3")
        adapter.addFragment(Logik_5(), "Logik-4")
        adapter.addFragment(Logik_6(), "Logik-5")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=6
    }
    private fun setupViewPagerLoewe(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Loewe_1(), "Loewe Generic")
        adapter.addFragment(Loewe_2(), "Loewe 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerMagnavox(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Magnavox_1(), "Generic")
        adapter.addFragment(Magnavox_2(), "15Mf050v17")
        adapter.addFragment(Magnavox_3(), "15MF605T")
        adapter.addFragment(Magnavox_4(), "42MF230A37")
        adapter.addFragment(Magnavox_5(), "37MF337B")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
    }
    private fun setupViewPagerMascom(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Mascom_1(), "Mascom-Generic")
        adapter.addFragment(Mascom_2(), "Mascom-1")
        adapter.addFragment(Mascom_3(), "Mascom-2")
        adapter.addFragment(Mascom_4(), "Mascom-3")
        adapter.addFragment(Mascom_5(), "Mascom-4")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
    }
    private fun setupViewPagerMedionSTB(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Medion_STB_1(), "MedionSTB-Generic")
        adapter.addFragment(Medion_STB_2(), "MedionSTB-1")
        adapter.addFragment(Medion_STB_3(), "MedionSTB-2")
        adapter.addFragment(Medion_STB_4(), "MedionSTB-3")
        adapter.addFragment(Medion_STB_5(), "MedionSTB-4")
        adapter.addFragment(Medion_STB_6(), "MedionSTB-5")
        adapter.addFragment(Medion_STB_7(), "MedionSTB-6")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=7
    }
    private fun setupViewPagerMedionTV(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Medion_TV_1(), "Medion-Generic")
        adapter.addFragment(Medion_TV_2(), "Medion-1")
        adapter.addFragment(Medion_TV_3(), "Medion-2")
        adapter.addFragment(Medion_TV_4(), "Medion-3")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=4
    }
    private fun setupViewPagerMitsubishi(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Mitsubishi_1(), "Mitsubishi_E")
        adapter.addFragment(Mitsubishi_2(), "Mitsubishi_O")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerMystery(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Mystery_1(), "Mystery Generic")
        adapter.addFragment(Mystery_2(), "Mystery 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerNEC(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NEC_1(), "NEC-1")
        adapter.addFragment(NEC_2(), "NEC-2")
        adapter.addFragment(NEC_3(), "NEC-3")
        adapter.addFragment(NEC_4(), "NEC-4")
        adapter.addFragment(NEC_5(), "NEC-5")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
    }
    private fun setupViewPagerNext(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Next_STB_1(), "Next-Generic")
        adapter.addFragment(Next_STB_2(), "Next-1")
        adapter.addFragment(Next_STB_3(), "Next-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerNexus(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Nexus_1(), "Nexus-Generic")
        adapter.addFragment(Nexus_2(), "Nexus-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerNiko(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Niko_1(), "Niko Generic")
        adapter.addFragment(Niko_2(), "Niko 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerOlevia(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Olevia_1(), "Olevia-Generic")
        adapter.addFragment(Olevia_2(), "Olevia-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerOrion(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Orion_1(), "Orion-1")
        adapter.addFragment(Orion_2(), "Orion-2")
        adapter.addFragment(Orion_3(), "Orion-3")
        adapter.addFragment(Orion_4(), "Orion-4")
        adapter.addFragment(Orion_5(), "Orion-5")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
    }
    private fun setupViewPagerPalsonic(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Palsonic_1(), "Palsonic Generic")
        adapter.addFragment(Palsonic_2(), "Palsonic-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerPhilco(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Philco_1(), "Generic")
        adapter.addFragment(Philco_2(), "Philco-1Series")
        adapter.addFragment(Philco_3(), "Philco-2Series")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerPioneer(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Pioneer_1(), "Elite-Pro107")
        adapter.addFragment(Pioneer_2(), "Elite-Pro117")
        adapter.addFragment(Pioneer_3(), "Elite-Pro119")
        adapter.addFragment(Pioneer_4(), "Elite-Pro510")
        adapter.addFragment(Pioneer_5(), "Elite-Pro610")
        adapter.addFragment(Pioneer_6(), "Elite-Pro620")
        adapter.addFragment(Pioneer_7(), "Elite-Pro1000")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=7
    }
    private fun setupViewPagerPolaroid(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Polaroid_1(), "Polaroid TLXB")
        adapter.addFragment(Polaroid_2(), "Polaroid FLM1511")
        adapter.addFragment(Polaroid_3(), "Polaroid FLM 3701")
        adapter.addFragment(Polaroid_4(), "Polaroid FLM1514B")
        adapter.addFragment(Polaroid_5(), "Polaroid TTM2401")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
    }
    private fun setupViewPagerPrima(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Prima_1(), "Prima Generic")
        adapter.addFragment(Prima_2(), "Prima-0")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerRCA(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(RCA_1(), "RCA-Generic")
        adapter.addFragment(RCA_2(), "RCA-TV")
        adapter.addFragment(RCA_3(), "RCA-DTV")
        adapter.addFragment(RCA_4(), "RCA-42PA30RQ")
        adapter.addFragment(RCA_5(), "RCA-RLDED3258A-B")
        adapter.addFragment(RCA_5(), "RCA-RLDED3258A-C")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=6
    }
    private fun setupViewPagerSEG(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SEG_1(), "SEG Generic")
        adapter.addFragment(SEG_2(), "SEG 1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerSinotec(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Sinotec_1(), "Sinotec Generic")
        adapter.addFragment(Sinotec_2(), "Sinotec-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerSkyworth(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Skyworth_1(), "Skyworth E")
        adapter.addFragment(Skyworth_2(), "Skyworth O")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerSONY(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Sony_1(), "Universal")
        adapter.addFragment(Sony_2(), "RM-Y118")
        adapter.addFragment(Sony_3(), "RM-921")
        adapter.addFragment(Sony_4(), "RM-724")
        adapter.addFragment(Sony_5(), "RM-Y128A")
        adapter.addFragment(Sony_6(), "RM-Y128B")
        adapter.addFragment(Sony_7(), "RM-Y114A")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=7
    }
    private fun setupViewPagerSupra(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Supra_1(), "Supra-Generic")
        adapter.addFragment(Supra_2(), "Supra-1")
        adapter.addFragment(Supra_3(), "Supra-2")
        adapter.addFragment(Supra_4(), "Supra-3")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=4
    }
    private fun setupViewPagerSoniq(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Soniq_1(), "Soniq Generic")
        adapter.addFragment(Soniq_2(), "Soniq 2142")
        adapter.addFragment(Soniq_3(), "Soniq E")
        adapter.addFragment(Soniq_4(), "Soniq O")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=4
    }
    private fun setupViewPagerSylvania(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Sylvania_1(), "Universal")
        adapter.addFragment(Sylvania_2(), "TV-0150")
        adapter.addFragment(Sylvania_3(), "STR-2127S")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerTechnika(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Technika_1(), "Technika-Generic")
        adapter.addFragment(Technika_2(), "Technika-1")
        adapter.addFragment(Technika_3(), "Technika-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerThomson(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Thomson_1(), "Thomson-Generic")
        adapter.addFragment(Thomson_2(), "Thomson-1")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerVideocon(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Videocon_Stb_1(), "VideoCon-STB 1")
        adapter.addFragment(Videocon_Stb_2(), "STB 2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerViore(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Viore_1(), "Viore 08")
        adapter.addFragment(Viore_2(), "Viore 15")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=2
    }
    private fun setupViewPagerWestinghouse(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Westinghouse_1(), "Westinghouse-L32W1")
        adapter.addFragment(Westinghouse_2(), "Westinghouse-RMT15")
        adapter.addFragment(Westinghouse_3(), "Westinghouse-Generic")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerWharfedale(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Wharfedale_1(), "Wharfedale-Generic")
        adapter.addFragment(Wharfedale_2(), "Wharfedale-1")
        adapter.addFragment(Wharfedale_3(), "Wharfedale-2")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=3
    }
    private fun setupViewPagerZenith(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Zenith_1(), "Zenith-1")
        adapter.addFragment(Zenith_2(), "Zenith-2")
        adapter.addFragment(Zenith_3(), "Zenith-3")
        adapter.addFragment(Zenith_4(), "Zenith-4")
        adapter.addFragment(Zenith_5(), "Zenith-5")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun refreshAd() {
        // refresh.setEnabled(false);

        val builder = AdLoader.Builder(this, getString(R.string.native_ad))

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            val adView = layoutInflater
                    .inflate(R.layout.ad_unified_main_2, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            frameLayout!!.removeAllViews()
            frameLayout!!.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()

        val adOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {

            override fun onAdLoaded() {
                Handler().postDelayed(Runnable {
                    frameLayout!!.visibility = View.VISIBLE
                },1000)
                super.onAdLoaded()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                frameLayout!!.visibility = View.GONE
                // refresh.setEnabled(true);
                /* Toast.makeText(ScanResultActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();*/
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

    }

    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline is guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.GONE
        } else {
            adView.priceView.visibility = View.GONE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.GONE
        } else {
            adView.storeView.visibility = View.GONE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.GONE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.GONE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.GONE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.GONE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val vc = nativeAd.videoController

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            /* videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));*/

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(object : VideoController.VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    /* refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");*/
                    super.onVideoEnd()
                }
            })
        } else {
            /*videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);*/
        }
    }
}
