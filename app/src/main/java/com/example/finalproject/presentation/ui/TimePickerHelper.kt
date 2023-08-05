import android.app.AlertDialog
import android.content.Context
import android.widget.NumberPicker
import com.example.finalproject.R

class TimePickerHelper(private val context: Context) {

    fun showTimePeriodPicker(onTimePeriodSelected: (Int) -> Unit) {
        val maxWeeks = 3 // Maksimal 3 minggu
        val secondsInOneWeek = 7 * 24 * 60 * 60 // Jumlah detik dalam satu minggu

        // Setup NumberPicker
        val numberPicker = NumberPicker(context)
        numberPicker.minValue = 1
        numberPicker.maxValue = maxWeeks
        numberPicker.value = 1 // Nilai default saat dialog dibuka (diubah dari 1 menjadi maxWeeks)

        // Tampilkan dialog dengan menggunakan AlertDialog
        val alertDialog = AlertDialog.Builder(context, R.style.CustomDialogTheme)
            .setTitle("Pilih Time Periode Skala Mingguan") // Judul dialog
            .setView(numberPicker)
            .setPositiveButton("Pilih") { _, _ ->
                val selectedWeeks = numberPicker.value
                val timePeriodInSeconds = selectedWeeks * secondsInOneWeek
                onTimePeriodSelected(timePeriodInSeconds)
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
