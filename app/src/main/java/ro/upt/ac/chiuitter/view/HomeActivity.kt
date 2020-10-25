package ro.upt.ac.chiuitter.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_home.*
import ro.upt.ac.chiuitter.R
import ro.upt.ac.chiuitter.data.database.ChiuitDbStore
import ro.upt.ac.chiuitter.data.database.RoomDatabase
import ro.upt.ac.chiuitter.domain.Chiuit
import ro.upt.ac.chiuitter.domain.ChiuitRepository
import ro.upt.ac.chiuitter.viewmodel.HomeViewModel
import ro.upt.ac.chiuitter.viewmodel.HomeViewModelFactory


class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_home)

        fab_add.setOnClickListener { composeChiuit() }

        val factory = HomeViewModelFactory(ChiuitDbStore(RoomDatabase.getDb(this)))
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        initList()
    }

    private fun initList() {
        rv_chiuit_list.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }

        viewModel.chiuitsLiveData.observe(this, Observer { chiuits ->
            rv_chiuit_list.apply {
                adapter = ChiuitRecyclerViewAdapter(chiuits,
                                                    { shareChiuit(it.description) },
                                                    { onDeleteChiuit(it) })
            }
        })

        viewModel.retrieveChiuits()
    }

    /*
    Defines text sharing/sending *implicit* intent, opens the application chooser menu
    and then starts a new activity which supports sharing/sending text.
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
        startActivityForResult(intent, COMPOSE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractText(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractText(data: Intent?) {
        data?.let {
            val text = data.getStringExtra(ComposeActivity.EXTRA_TEXT)
            if (!text.isNullOrBlank()) {
                viewModel.addChiuit(text)
            }
        }
    }

    private fun onDeleteChiuit(chiuit: Chiuit) {
        viewModel.removeChiuit(chiuit)
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }

}
