package pl.polsl.lab6przykladkotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.webkit.JavascriptInterface
import android.widget.Toast
import java.util.*
import android.webkit.WebView

class MainActivity : AppCompatActivity() {

    var arrayGifts = ArrayList<String>()

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
        page.loadUrl("file:///android_asset/page.html")

        //wstawienie kontrolki WebView jako calej fasady aktywnosci
        setContentView(page)
    }

    @JavascriptInterface
    fun addGifts() {
        val add = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        add.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        add.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is your desire?")

        try {
            startActivityForResult(add, 0)
        }
        catch(XD: ActivityNotFoundException){
            Toast.makeText(applicationContext,
                "What did you say, lil punk?",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK) {
            val addList = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            Toast.makeText(applicationContext, addList?.get(0), Toast.LENGTH_LONG).show()
            addList?.get(0)?.let { arrayGifts.add(it) }
        }
    }

    @JavascriptInterface
    fun generate() {
        arrayGifts.add("Kon")
        arrayGifts.add("Pies")
        arrayGifts.add("Jaszczurka")
        arrayGifts.add("Papuga")
        arrayGifts.add("Krulik")
        arrayGifts.add("Nosororzec")
        arrayGifts.add("Peppa")
        arrayGifts.add("Adidas")
        arrayGifts.add("Jaszczomop")
        arrayGifts.add("Semp")
        arrayGifts.add("Wiurka")
        arrayGifts.add("Toperz")
        arrayGifts.add("Dronka")

        startActivity(Intent(this, PostCard::class.java).apply {
            putExtra("GIFTS_LIST", arrayGifts)
        })
    }

    @JavascriptInterface //adnotacja sygnalizujaca ze metoda bedzie dostepna z poziomu JS
    fun sayHello(name: String) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun getDate(): String {
        return Date().toString()
    }
}


