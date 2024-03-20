import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import android.content.res.Configuration
import android.graphics.Color
import com.example.xpendy.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var colorTextView: TextView
    private val colors = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW) // Add more colors as needed
    private var selectedColor = Color.RED // Default selected color

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switch: Switch = view.findViewById(R.id.switch2)
        colorTextView = view.findViewById(R.id.colorTextView)


        updateColorText()

        switch.isChecked = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                updateColorText()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                updateColorText()
            }
        }

        colorTextView.setOnClickListener {
            showColorPickerDialog()
        }
    }

    private fun updateColorText() {
        val currentMode = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> "Dark"
            else -> "Light"
        }
        colorTextView.text = "Color ($currentMode)"
        colorTextView.setTextColor(selectedColor)
    }

    private fun showColorPickerDialog() {
        val colorPickerDialog = AlertDialog.Builder(requireContext())
        colorPickerDialog.setTitle("Select Color")
        colorPickerDialog.setItems(arrayOf("Red", "Blue", "Green", "Yellow")) { _, which ->
            selectedColor = colors[which]
            updateColorText()
        }
        colorPickerDialog.show()
    }
}
