package ro.upt.ac.chiuitter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.view_compose.*


class ComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_compose)

        fab_finish.setOnClickListener {
            // Prepare the intent to be returned as result.
            val returnIntent = Intent().apply {
                val content = etx_content.editableText.toString()
                putExtra(EXTRA_TEXT, content)
            }

            // Mark the result as success and attach text data.
            setResult(Activity.RESULT_OK, returnIntent)

            // Finish the activity, we do not need it anymore after we got the text input.
            finish()
        }
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }

}
