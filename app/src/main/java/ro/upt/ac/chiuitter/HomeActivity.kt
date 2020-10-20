package ro.upt.ac.chiuitter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.view_home.*

class HomeActivity : AppCompatActivity() {

    private val dummyChiuitStore = DummyChiuitStore()

    private lateinit var listAdapter: ChiuitRecyclerViewAdapter

    private var chiuits: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_home)
        fab_add.setOnClickListener {composeChiuit()}

        initList()
    }

    private fun initList() {
        val chiuitList = dummyChiuitStore.getAllData()

        listAdapter = ChiuitRecyclerViewAdapter(chiuitList = chiuitList.toMutableList(),
                onClick = fun(chiuit: Chiuit) {
                    shareChiuit(chiuit.description)
                })
        val manager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rv_chiuit_list).apply {
            layoutManager = manager
            adapter = listAdapter
        }
    }

    /*
    Defines text sharing/sending *implicit* intent, opens the application chooser menu,
    and starts a new activity which supports sharing/sending text.
     */
    private fun shareChiuit(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        val intentChooser = Intent.createChooser(sendIntent, "")

        startActivity(intentChooser)
    }

    /*
    Defines an *explicit* intent which will be used to start ComposeActivity.
     */
    private fun composeChiuit() {
        val intent = Intent(this, ComposeActivity::class.java)

        // We start a new activity that we expect to return the acquired text as the result.
        startActivityForResult(intent, 1213)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractText(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractText(data: Intent?) {
        data?.let {
            val text = it.getStringExtra(ComposeActivity.EXTRA_TEXT)

            if (!text.isNullOrEmpty()) {
                listAdapter.addItem(Chiuit(text))
            }

        }
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }

}
