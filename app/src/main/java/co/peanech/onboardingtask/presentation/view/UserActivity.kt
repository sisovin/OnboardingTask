package co.peanech.onboardingtask.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import co.peanech.onboardingtask.Constants
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.databinding.ActivityUserBinding
import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.model.User
import co.peanech.onboardingtask.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        getUserData()
        initViewModel()
    }

    private fun getUserData() = with(binding) {
        val email = intent.getStringExtra(Constants.EXTRA_EMAIL)
        if (email.isNullOrBlank()) {
            Toast.makeText(this@UserActivity, getString(R.string.error_access), Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }
        userViewModel.getUserByEmail(email)
    }

    private fun initViewModel() = with(userViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userResult.collect {
                    when (it) {
                        is Result.Loading -> {}
                        is Result.Success -> initUserData(it.data)
                        is Result.Error -> Toast.makeText(
                            this@UserActivity, it.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initUserData(user: User) = with(binding) {
        tvUserGreetings.text = getString(R.string.user_greetings, user.username)
        tvUserEmail.text = getString(R.string.user_email, user.email)
        btnUserWithdraw.setOnClickListener {
            userViewModel.deleteUser(user)
            returnToLoginActivity()
        }
    }

    private fun initView() = with(binding) {
        btnUserLogout.setOnClickListener { returnToLoginActivity() }
    }

    private fun returnToLoginActivity() {
        val intent = Intent(this@UserActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
