import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.widget.Toast
import com.example.xpendy.MainActivity
import com.example.xpendy.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var colorTextView: TextView
    private val colors = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
    private var selectedColor = Color.RED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switch: Switch = view.findViewById(R.id.switch2)
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
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

        logoutButton.setOnClickListener {
            logout()
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

    private fun logout() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
