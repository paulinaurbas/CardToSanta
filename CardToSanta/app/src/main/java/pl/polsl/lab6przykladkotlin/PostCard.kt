package pl.polsl.lab6przykladkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.util.ArrayList

class PostCard : AppCompatActivity() {
    var arrayGifts = ArrayList<String>()
    @SuppressLint("NewApi")
    var photo = File(Environment.DIRECTORY_DCIM, "photoForSanta.jpg")
    var path = String()


    @SuppressLint("JavascriptInterface", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //WebView - kontrolka wyswietlajaca html
        val page = WebView(this)

        //wlaczenie obslugi JS
        page.settings.javaScriptEnabled=true

        //dodanie interfejsu pomiędzy Kotlinem a JS
        //this - obiekt tej klasy dostarcza metody JSInterface, activity - nazwa widoczna w JS
        page.addJavascriptInterface(this, "activity")

        //zaladowanie zawartosci kontroli WebView - pliki z katalogu assests w projekcie
        page.loadUrl("file:///android_asset/background.html")

        //wstawienie kontrolki WebView jako calej fasady aktywnosci
        setContentView(page)

        arrayGifts = intent.getStringArrayListExtra("GIFTS_LIST")

        photo = File(Environment.DIRECTORY_DCIM, "photoForSanta.jpg")
        var photoPath = Uri.fromFile(photo)
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath)
        startActivity(intent)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK) {
            var uri = data!!.data
            path = uri!!.encodedPath!!
        }
    }*/

    @JavascriptInterface
    fun loadGifts():String {
        var str = String()
        for(text in arrayGifts) {
            str += (text + "\n")
        }

        return str
    }

    @JavascriptInterface
    fun loadPhoto():String = path
}