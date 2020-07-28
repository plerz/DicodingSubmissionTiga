package com.ifreshmart.githubsearch2020.view.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ifreshmart.githubsearch2020.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var userPreferences: UserPrefer
    private lateinit var dailyReminder: DailyReminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        if (supportActionBar != null) {
            supportActionBar?.title = "Reminder Setting"
        }

        dailyReminder = DailyReminder()
        userPreferences =
            UserPrefer(
                this
            )
        sw_reminder.isChecked= dailyReminder.isAlarmSet(this)
        sw_reminder.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                dailyReminder.setRepeatingAlarm(this)
                userPreferences.setDailyReminder(true)
                Toast.makeText(this, getString(R.string.sw_on), Toast.LENGTH_SHORT).show()
            }else{
                dailyReminder.cancelRepeatingAlarm(this)
                userPreferences.setDailyReminder(false)
                Toast.makeText(this, getString(R.string.sw_off),Toast.LENGTH_SHORT).show()
            }
        }

        btn_test.setOnClickListener(this)
    }

    override fun onClick( v : View) {
        when(v.id){
            R.id.btn_test->{
                dailyReminder.showNotification(this)
                Toast.makeText(this, getString(R.string.tx_test_ok), Toast.LENGTH_SHORT).show()
            }
        }
    }

}